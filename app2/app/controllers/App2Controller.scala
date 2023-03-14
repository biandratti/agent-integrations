package controllers

import models.Greeting
import play.api.libs.json.Json
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}
import play.twirl.api.Html

class App2Controller(
    cc: ControllerComponents
) extends AbstractController(cc) {

  val greetingsList = Seq(
    Greeting(1, "Hello", "sameer"),
    Greeting(2, "Messi", "sam")
  )

  def greetings: Action[AnyContent] = Action {
    Ok(Json.toJson(greetingsList))
  }

  def index: Action[AnyContent] = Action {
    Ok(Html("<h1>Welcome</h1><p>Your new application is ready.</p>"))
  }

}
