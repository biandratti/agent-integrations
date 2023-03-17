import com.softwaremill.macwire.*
import io.opentelemetry.api.trace.Tracer
import play.api.*
import play.api.ApplicationLoader.Context
import play.api.routing.Router
import router.Routes

import scala.concurrent.Future

/** Application loader that wires up the application dependencies using Macwire
  */
class TraceApplicationLoader extends ApplicationLoader {
  def load(context: Context): Application = new TraceComponents(
    context
  ).application
}

class TraceComponents(context: Context)
    extends BuiltInComponentsFromContext(context)
    with TraceModule
    with play.filters.HttpFiltersComponents {

  // set up Opentelemetry
  val openTelemetry =
    OpenTelemetryLoader("http://localhost:4318", "http://localhost:4317")
  context.lifecycle.addStopHook { () =>
    Future(openTelemetry.close())
  }

  override def tracer: Tracer = openTelemetry.getTracer("app3")

  /*for (_ <- 0 until 10) {
    // Generate a span
    val span: Span =
      tracer.spanBuilder("Start my wonderful Maxi use case").startSpan
    span.addEvent("Event 0")
    // execute my use case - here we simulate a wait
    try Thread.sleep(1000)
    catch {
      case e: InterruptedException =>

      // do the right thing here
    }
    span.addEvent("Event 1")
    span.end()
  }*/

  // set up logger
  LoggerConfigurator(context.environment.classLoader).foreach {
    _.configure(context.environment, context.initialConfiguration, Map.empty)
  }
  lazy val router: Router = {
    // add the prefix string in local scope for the Routes constructor
    val prefix: String = "/"
    wire[Routes]
  }
}
