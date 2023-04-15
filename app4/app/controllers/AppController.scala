package controllers

import io.opentelemetry.api.trace.{Span, StatusCode}
import models.TraceResponse
import play.api.{Logging, MarkerContext}
import play.api.libs.json.Json
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents,
  Headers
}
import play.twirl.api.Html
import utils.RequestMarkerContext.{getCtxId, requestHeaderToMarkerContext}

import scala.concurrent.{ExecutionContext, Future}

class AppController(
    cc: ControllerComponents
)(implicit ec: ExecutionContext)
    extends AbstractController(cc)
    with Logging {

  def trace: Action[AnyContent] = Action.async { implicit request =>
    implicit val mc: MarkerContext =
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
