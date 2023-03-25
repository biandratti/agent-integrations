package controllers

import play.api.MarkerContext
import play.api.libs.json.Json
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}
import play.twirl.api.Html
import services.TraceService
import utils.ContextId
import utils.RequestMarkerContext.requestHeaderToMarkerContext

import scala.concurrent.ExecutionContext

class AppController(
    traceService: TraceService,
    cc: ControllerComponents
)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with ContextId {

  def trace(): Action[AnyContent] =
    Action.async { implicit request =>
      implicit val mc: MarkerContext =
        requestHeaderToMarkerContext(request.headers)
      traceService
        .getTrace(getCtxId(request.headers))
        .map(response => {
          Ok(Json.toJson(response))
        })
    }

  def index: Action[AnyContent] = Action {
    Ok(Html("<h1>Welcome</h1><p>APP1 is ready.</p>"))
  }

}
