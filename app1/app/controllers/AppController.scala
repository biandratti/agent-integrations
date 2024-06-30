package controllers

import scala.concurrent.ExecutionContext

import play.api.MarkerContext
import play.api.libs.json.Json
import play.api.mvc.AbstractController
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.ControllerComponents
import play.twirl.api.Html
import services.TraceService
import utils.ContextId
import utils.RequestMarkerContext.requestHeaderToMarkerContext

class AppController(
    traceService: TraceService,
    cc: ControllerComponents
)(using ec: ExecutionContext)
    extends AbstractController(cc)
    with ContextId {

  def trace(): Action[AnyContent] =
    Action.async { request =>
      given mc: MarkerContext = requestHeaderToMarkerContext(request.headers)
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
