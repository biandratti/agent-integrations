package controllers

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import kamon.Kamon
import models.TraceResponse
import play.api.Logging
import play.api.MarkerContext
import play.api.libs.json.Json
import play.api.mvc.AbstractController
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.ControllerComponents
import play.twirl.api.Html
import utils.RequestMarkerContext.requestHeaderToMarkerContext

class AppController(
    cc: ControllerComponents
)(using ec: ExecutionContext)
    extends AbstractController(cc)
    with Logging {

  def trace(): Action[AnyContent] =
    Action.async { request =>
      given mc: MarkerContext =
        requestHeaderToMarkerContext(request.headers)
      logger.info(s"trace request with headers: ${request.headers}")
      Future(
        Ok(Json.toJson(TraceResponse(Kamon.currentSpan().trace.id.string)))
      )
    }

  def index: Action[AnyContent] = Action {
    Ok(Html("<h1>Welcome</h1><p>APP2 is ready.</p>"))
  }

}
