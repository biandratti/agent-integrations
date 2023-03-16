import controllers.AppController
import play.api.mvc.ControllerComponents

trait TraceModule {

  import com.softwaremill.macwire._

  lazy val traceController = wire[AppController]

  def controllerComponents: ControllerComponents
}
