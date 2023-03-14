import controllers.App1Controller
import play.api.mvc.ControllerComponents

trait TraceModule {

  import com.softwaremill.macwire._

  lazy val traceController = wire[App1Controller]

  def controllerComponents: ControllerComponents
}
