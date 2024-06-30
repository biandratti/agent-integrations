import scala.concurrent.ExecutionContext

import controllers.AppController
import play.api.mvc.ControllerComponents

trait TraceModule {

  import com.softwaremill.macwire.*

  lazy val traceController = wire[AppController]

  given ec: ExecutionContext = scala.concurrent.ExecutionContext.global
  def controllerComponents: ControllerComponents
}
