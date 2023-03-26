package controllers

import play.api.libs.json.Json
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}
import play.api.{Logging, MarkerContext}
import play.twirl.api.Html
import service.TraceService
import utils.RequestMarkerContext.{getCtxId, requestHeaderToMarkerContext}

import scala.concurrent.ExecutionContext

class AppController(
    traceService: TraceService,
    cc: ControllerComponents
)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with Logging {

  def trace(): Action[AnyContent] =
    Action.async { implicit request =>
      implicit val mc: MarkerContext =
        requestHeaderToMarkerContext(request.headers)
      traceService
        .getApp3Trace(getCtxId(request.headers))
        .map(response => {
          Ok(Json.toJson(response))
        })
    }

  def index: Action[AnyContent] = Action {
    Ok(Html("<h1>Welcome</h1><p>APP2 is ready.</p>"))
  }

}
