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
import org.typelevel.ci.CIString
import org.typelevel.log4cats.slf4j.Slf4jLogger
import org.typelevel.otel4s.trace.Tracer
import utils.ServerMiddleware.*
import utils.{
  ContextId,
  ContextualLogger,
  HttpRequestContextual,
  RequestContext
}

import scala.jdk.CollectionConverters.*

case class TraceResponse(id: String)

object TraceResponse {
  implicit def traceResponseEncoder[F[_]]: EntityEncoder[F, TraceResponse] =
    jsonEncoderOf[F, TraceResponse]
}

class AppController[F[_]: Async](tracer: Tracer[F])
    extends Http4sDsl[F]
    with ContextId {
  implicit val t: Tracer[F] = tracer

  val logger: ContextualLogger[F] =
    new ContextualLogger[F](Slf4jLogger.getLogger[F])

  // TODO:WIP..
  private val propagator = W3CTraceContextPropagator.getInstance()
  private val getter = new TextMapGetter[Headers] {
    override def keys(carrier: Headers): java.lang.Iterable[String] =
      carrier.headers.map(_.name.toString).asJava

    override def get(carrier: Headers, key: String): String =
      carrier.get(CIString(key)).map(_.head.value).orNull
  }

  def trace: HttpRoutes[F] = HttpRoutes.of[F] {
    case request @ GET -> Root / "api" / "v1" / "trace" =>
      HttpRequestContextual.contextual(request) {
        implicit ctx: RequestContext =>
          val context =
            propagator.extract(Context.current(), request.headers, getter)
          val span = Span.fromContext(context)
          for {
            _ <- logger.info(
              s"trace request - headers ${request.headers.headers}"
            )
            _ <- logger.info(s"trace_id ${span.getSpanContext.getTraceId}")
            response <- Ok(
              TraceResponse(span.getSpanContext.getTraceId).asJson
            )
          } yield response
      }
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
