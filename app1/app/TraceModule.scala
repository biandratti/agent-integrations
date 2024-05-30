import scala.concurrent.ExecutionContext

import controllers.AppController
import play.api.libs.ws.WSClient
import play.api.mvc.ControllerComponents
import services.TraceService

trait TraceModule {

  import com.softwaremill.macwire.*

  lazy val traceService = wire[TraceService]
  lazy val traceController = wire[AppController]

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global
  def ws: WSClient
  def controllerComponents: ControllerComponents
}
