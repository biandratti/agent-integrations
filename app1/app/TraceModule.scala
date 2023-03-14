import controllers.App1Controller
import play.api.libs.ws.WSClient
import play.api.mvc.ControllerComponents
import services.TraceService

trait TraceModule {

  import com.softwaremill.macwire.*

  lazy val traceService = wire[TraceService]
  lazy val traceController = wire[App1Controller]

  def ws: WSClient
  def controllerComponents: ControllerComponents
}
