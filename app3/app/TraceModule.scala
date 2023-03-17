import controllers.AppController
import io.opentelemetry.api.trace.Tracer
import play.api.mvc.ControllerComponents

trait TraceModule {

  import com.softwaremill.macwire._

  lazy val traceController = wire[AppController]

  def tracer: Tracer
  def controllerComponents: ControllerComponents
}
