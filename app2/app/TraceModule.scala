import controllers.AppController
import play.api.libs.ws.WSClient
import play.api.mvc.ControllerComponents
import service.TraceService

import scala.concurrent.ExecutionContext

trait TraceModule {

  import com.softwaremill.macwire._

  lazy val traceService = wire[TraceService]
  lazy val traceController = wire[AppController]

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global
  def ws: WSClient
  def controllerComponents: ControllerComponents
}
