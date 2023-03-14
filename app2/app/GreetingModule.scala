import controllers.App2Controller
import play.api.mvc.ControllerComponents

trait GreetingModule {

  import com.softwaremill.macwire._

  lazy val greeterController = wire[App2Controller]

  def controllerComponents: ControllerComponents
}
