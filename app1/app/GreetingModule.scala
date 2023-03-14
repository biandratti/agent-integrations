import controllers.App1Controller
import play.api.i18n.Langs
import play.api.mvc.ControllerComponents

trait GreetingModule {

  import com.softwaremill.macwire._

  lazy val greeterController = wire[App1Controller]

  def langs: Langs

  def controllerComponents: ControllerComponents
}
