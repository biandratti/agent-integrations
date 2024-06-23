import controllers.AppController
import play.api.Configuration
import play.api.libs.ws.WSClient
import play.api.mvc.ControllerComponents
import services.TraceService

import scala.concurrent.ExecutionContext

trait TraceModule {

  import com.softwaremill.macwire.*

  lazy val traceService = wire[TraceService]
  lazy val traceController = wire[AppController]

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global
  def ws: WSClient
  def controllerComponents: ControllerComponents
  def configuration: Configuration

}
