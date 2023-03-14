package controllers

import play.api.libs.json.Json
import play.api.mvc.{
  AbstractController,
  Action,
  AnyContent,
  ControllerComponents
}
import play.twirl.api.Html
import services.TraceService
import scala.concurrent.ExecutionContext

class App1Controller(
    traceService: TraceService,
    cc: ControllerComponents
) extends AbstractController(cc) {

  implicit val ec: ExecutionContext =
    scala.concurrent.ExecutionContext.global // TODO...

  def trace(): Action[AnyContent] =
    Action.async {
      traceService
        .getApp2Trace()
        .map(response => {
          Ok(Json.toJson(response))
        })
    }

  def index: Action[AnyContent] = Action {
    Ok(Html("<h1>Welcome</h1><p>APP1 is ready.</p>"))
  }

}
