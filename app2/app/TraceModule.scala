import controllers.AppController
import play.api.mvc.ControllerComponents

import scala.concurrent.ExecutionContext

trait TraceModule {

  import com.softwaremill.macwire.*

  lazy val traceController = wire[AppController]

  implicit val ec: ExecutionContext = scala.concurrent.ExecutionContext.global
  def controllerComponents: ControllerComponents
}
