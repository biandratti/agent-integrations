import scala.concurrent.ExecutionContext

import controllers.AppController
import play.api.Configuration
import play.api.libs.ws.WSClient
import play.api.mvc.ControllerComponents
import service.TraceService

trait TraceModule {

  import com.softwaremill.macwire._

  lazy val traceService = wire[TraceService]
  lazy val traceController = wire[AppController]

  given ec: ExecutionContext = scala.concurrent.ExecutionContext.global
  def ws: WSClient
  def controllerComponents: ControllerComponents
  def configuration: Configuration

}
