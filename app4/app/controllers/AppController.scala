package controllers

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.StatusCode
import models.TraceResponse
import play.api.Logging
import play.api.MarkerContext
import play.api.libs.json.Json
import play.api.mvc.AbstractController
import play.api.mvc.Action
import play.api.mvc.AnyContent
import play.api.mvc.ControllerComponents
import play.api.mvc.Headers
import play.twirl.api.Html
import utils.RequestMarkerContext.getCtxId
import utils.RequestMarkerContext.requestHeaderToMarkerContext

class AppController(
    cc: ControllerComponents
)(using ec: ExecutionContext)
    extends AbstractController(cc)
    with Logging {

  def trace: Action[AnyContent] = Action.async { request =>
    given mc: MarkerContext =
      requestHeaderToMarkerContext(request.headers)
    logger.info("trace request")
    Future(
      Ok(
        Json.toJson(
          TraceResponse(getSpan(request.headers).getSpanContext.getTraceId)
        )
      )
    )
  }

  private def getSpan(requestHeader: Headers): Span = {
    val span = Span.current()
    if (getCtxId(requestHeader).equalsIgnoreCase("error")) {
      logger.error("simulating an unexpected error")
      span.setStatus(StatusCode.ERROR, "Unexpected error")
      // span.end()
    }
    span
  }

  def index: Action[AnyContent] = Action {
    Ok(Html("<h1>Welcome</h1><p>APP3 is ready.</p>"))
  }

}
