import controllers.App2Controller
import play.api.mvc.ControllerComponents

trait TraceModule {

  import com.softwaremill.macwire._

  lazy val traceController = wire[App2Controller]

  def controllerComponents: ControllerComponents
}
