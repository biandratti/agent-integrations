package utils

import kamon.Kamon
import play.api.Logging
import play.api.libs.json.{Format, Json}
import play.api.mvc.Headers

trait KamonState {
  def span(): String
  def trace(): String
}

case class TraceExecutionContext(contextId: String, span: String, trace: String)
    extends KamonState

object TraceExecutionContext extends Logging {
  val contextId = "context-id"
  implicit val format: Format[TraceExecutionContext] = Json.format
  def apply(requestHeader: Headers): TraceExecutionContext = {
    TraceExecutionContext(
      requestHeader
        .get(contextId)
        .getOrElse({
          logger.error(s"$contextId is missing")
          throw new RuntimeException(s"$contextId is missing")
        }),
      span = Kamon.currentSpan().id.string,
      trace = Kamon.currentSpan().trace.id.string
    )
  }
}
