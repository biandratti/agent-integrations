package controllers

import models.TraceResponse
import play.api.Logging
import play.api.libs.json.Json
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}
import play.twirl.api.Html

class AppController(
    cc: ControllerComponents
) extends AbstractController(cc)
    with Logging {

  def trace: Action[AnyContent] = Action {
    logger.info("trace request")
    Ok(Json.toJson(TraceResponse("Ok")))
  }

  def index: Action[AnyContent] = Action {
    Ok(Html("<h1>Welcome</h1><p>APP2 is ready.</p>"))
  }

}
