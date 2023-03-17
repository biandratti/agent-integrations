package controllers

import io.opentelemetry.api.trace.Tracer
import models.TraceResponse
import play.api.libs.json.Json
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}
import play.twirl.api.Html
import utils.LoggerUtils

class AppController(
    tracer: Tracer,
    cc: ControllerComponents
) extends AbstractController(cc)
    with LoggerUtils {

  def trace: Action[AnyContent] = Action {
    runWithTrace(
      slf4jLogger.info("trace request"),
      tracer
    )
    runWithTrace(
      slf4jLogger.info("trace request"),
      tracer,
      true
    )
    Ok(Json.toJson(TraceResponse("Ok")))
  }

  def index: Action[AnyContent] = Action {
    Ok(Html("<h1>Welcome</h1><p>APP3 is ready.</p>"))
  }

}
