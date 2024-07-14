package controllers

import cats.effect.Async
import cats.implicits.*
import io.circe.generic.auto.*
import io.circe.syntax.*
import io.opentelemetry.api.trace.Span
import io.opentelemetry.api.trace.propagation.W3CTraceContextPropagator
import io.opentelemetry.context.Context
import io.opentelemetry.context.propagation.TextMapGetter
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits.*
import org.log4s.getLogger
import org.typelevel.ci.CIString
import org.typelevel.otel4s.trace.Tracer
import utils.ContextId
import utils.ServerMiddleware.*
import scala.jdk.CollectionConverters._

case class TraceResponse(id: String)

object TraceResponse {
  implicit def traceResponseEncoder[F[_]]: EntityEncoder[F, TraceResponse] =
    jsonEncoderOf[F, TraceResponse]
}

class AppController[F[_]: Async](tracer: Tracer[F])
    extends Http4sDsl[F]
    with ContextId {
  implicit val t: Tracer[F] = tracer
  private[this] val logger = getLogger

  private val propagator = W3CTraceContextPropagator.getInstance()
  private val getter = new TextMapGetter[Headers] {
    override def keys(carrier: Headers): java.lang.Iterable[String] =
      carrier.headers.map(_.name.toString).asJava

    override def get(carrier: Headers, key: String): String =
      carrier.get(CIString(key)).map(_.head.value).orNull
  }

  def trace: HttpRoutes[F] = HttpRoutes.of[F] {
    case request @ GET -> Root / "api" / "v1" / "trace" =>
      val context =
        propagator.extract(Context.current(), request.headers, getter)
      val span = Span.fromContext(context)
      logger.info(s"trace request - headers ${request.headers.headers}")
      logger.info(s"trace_id ${span.getSpanContext.getTraceId}")
      Ok(
        TraceResponse(span.getSpanContext.getTraceId).asJson
      )
  }

  /*private def getSpan(requestHeader: Headers): Span = {
    val span = Span.current()
    if (getCtxId(requestHeader).equalsIgnoreCase("error")) {
      logger.error("simulating an unexpected error")
      span.setStatus(StatusCode.ERROR, "Unexpected error")
      // span.end()
    }
    span
  }*/

  def index: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root / "health" =>
    Ok(
      "<h1>Welcome</h1><p>APP6 is ready.</p>",
      Header.Raw(CIString("Content-Type"), "text/html")
    )
  }

  private val routes: HttpRoutes[F] = trace <+> index
  val httpApp: HttpApp[F] = routes.orNotFound.traced
}
