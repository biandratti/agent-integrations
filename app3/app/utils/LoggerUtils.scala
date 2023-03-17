package utils

import io.opentelemetry.api.trace.Tracer
import org.slf4j.{Logger, LoggerFactory}

trait LoggerUtils {

  lazy val slf4jLogger: Logger = LoggerFactory.getLogger("slf4j-logger")

  def runWithTrace(
      log: => Unit,
      tracer: Tracer,
      withSpan: Boolean = false
  ): Unit = {
    if (!withSpan) {
      log
    } else {
      val span = tracer.spanBuilder("/api3/v1/trace").startSpan
      val unused = span.makeCurrent
      log
      span.end()
      unused.close()
    }
  }
  /*  def maybeRunWithSpan(runnable: Runnable, withSpan: Boolean): Unit = {
    if (!withSpan) {
      runnable.run()
      return
    }
    val span = GlobalOpenTelemetry
      .getTracer("app3")
      .spanBuilder("/api3/v1/trace")
      .startSpan
    try {
      val unused = span.makeCurrent
      try runnable.run()
      finally {
        span.end()
        if (unused != null) unused.close()
      }
    }
  }*/
}
