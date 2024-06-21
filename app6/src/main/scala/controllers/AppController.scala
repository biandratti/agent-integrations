package controllers

import cats.effect.Async
import cats.implicits.*
import io.circe.generic.auto.*
import io.circe.syntax.*
import org.http4s.*
import org.http4s.implicits.*
import org.http4s.circe.*
import org.http4s.dsl.Http4sDsl
import org.log4s.getLogger
import org.typelevel.ci.CIString
import org.typelevel.otel4s.trace.Tracer
import utils.ServerMiddleware._

case class TraceResponse(traceId: String)

object TraceResponse {
  implicit def traceResponseEncoder[F[_]]: EntityEncoder[F, TraceResponse] =
    jsonEncoderOf[F, TraceResponse]
}

class AppController[F[_]: Async](tracer: Tracer[F]) extends Http4sDsl[F] {
  implicit val t: Tracer[F] = tracer
  private[this] val logger = getLogger

  def trace: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / "api" / "v1" / "trace" =>
      logger.info("trace request...")
      Ok(TraceResponse("WIP traceId").asJson)
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
