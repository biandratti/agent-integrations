package utils

import kamon.Kamon
import play.api.libs.json.{Format, Json}
import play.api.mvc.Headers

trait KamonState {
  def span(): String
  def trace(): String
}

case class TraceExecutionContext(contextId: String, span: String, trace: String)
    extends KamonState

object TraceExecutionContext extends ContextId {
  implicit val format: Format[TraceExecutionContext] = Json.format

  def apply(requestHeader: Headers): TraceExecutionContext = {
    TraceExecutionContext(
      contextId = getCtxId(requestHeader),
      span = Kamon.currentSpan().id.string,
      trace = Kamon.currentSpan().trace.id.string
    )
  }
}
