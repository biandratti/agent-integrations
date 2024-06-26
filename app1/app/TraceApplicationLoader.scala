import com.softwaremill.macwire.*
import kamon.Kamon
import play.api.*
import play.api.ApplicationLoader.Context
import play.api.libs.ws.WSClient
import play.api.libs.ws.ahc.AhcWSComponents
import play.api.routing.Router
import router.Routes

/** Application loader that wires up the application dependencies using Macwire
  */
class TraceApplicationLoader extends ApplicationLoader {
  def load(context: Context): Application = new TraceComponents(
    context
  ).application
}

class TraceComponents(context: Context)
    extends BuiltInComponentsFromContext(context)
    with AhcWSComponents
    with TraceModule
    with play.filters.HttpFiltersComponents {

  // set up Kamon
  Kamon.init(context.initialConfiguration.underlying)
  context.lifecycle.addStopHook { () =>
    Kamon.stop()
  }

  // set up logger
  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }

  // add the prefix string in local scope for the Routes constructor
  lazy val prefix: String = "/"
  lazy val router: Router = {
    wire[Routes]
  }

  override def ws: WSClient = wsClient
  override def configuration: Configuration = context.initialConfiguration
}
