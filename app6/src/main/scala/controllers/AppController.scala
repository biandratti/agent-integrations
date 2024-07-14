package controllers

import cats.effect.Async
import cats.implicits.*
import io.circe.generic.auto.*
import io.circe.syntax.*
import io.opentelemetry.api.trace.{Span, StatusCode}
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.Http4sDsl
import org.http4s.implicits.*
import org.log4s.getLogger
import org.typelevel.ci.CIString
import org.typelevel.otel4s.trace.Tracer
import utils.ContextId
import utils.ServerMiddleware.*

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

  def trace: HttpRoutes[F] = HttpRoutes.of[F] {
    case request @ GET -> Root / "api" / "v1" / "trace" =>
      logger.info(s"trace request - headers ${request.headers.headers}")
      Ok(
        TraceResponse(getSpan(request.headers).getSpanContext.getTraceId).asJson
      )
  }

  private def getSpan(requestHeader: Headers): Span = {
    val span = Span.current()
    if (getCtxId(requestHeader).equalsIgnoreCase("error")) {
      logger.error("simulating an unexpected error")
      span.setStatus(StatusCode.ERROR, "Unexpected error")
      // span.end()
    }
    span
  }

  def index: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root / "health" =>
    Ok(
      "<h1>Welcome</h1><p>APP6 is ready.</p>",
      Header.Raw(CIString("Content-Type"), "text/html")
    )
  }

  private val routes: HttpRoutes[F] = trace <+> index
  val httpApp: HttpApp[F] = routes.orNotFound.traced
}
