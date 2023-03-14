package controllers

import models.TraceResponse
import play.api.libs.json.Json
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}
import play.twirl.api.Html

class App1Controller(
    cc: ControllerComponents
) extends AbstractController(cc) {

  def trace: Action[AnyContent] = Action {
    Ok(Json.toJson(TraceResponse("Ok")))
  }

  def index: Action[AnyContent] = Action {
    Ok(Html("<h1>Welcome</h1><p>APP1 is ready.</p>"))
  }

}
