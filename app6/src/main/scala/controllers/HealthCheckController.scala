package controllers

import cats.effect.Async
import cats.implicits.toSemigroupKOps
import io.circe.generic.auto.exportEncoder
import io.circe.syntax.*
import org.http4s.*
import org.http4s.circe.*
import org.http4s.dsl.Http4sDsl
import org.log4s.getLogger
import org.typelevel.ci.CIString
case class TraceResponse(traceId: String)

object TraceResponse {
  implicit def traceResponseEncoder[F[_]]: EntityEncoder[F, TraceResponse] =
    jsonEncoderOf[F, TraceResponse]
}

class AppController[F[_]: Async] extends Http4sDsl[F] {

  private[this] val logger = getLogger

  def trace: HttpRoutes[F] = HttpRoutes.of[F] {
    case GET -> Root / "api" / "v1" / "trace" =>
      logger.info("trace request...")
      Ok(TraceResponse("WIP traceId").asJson)
  }

  def index: HttpRoutes[F] = HttpRoutes.of[F] { case GET -> Root / "health" =>
    Ok(
      "<h1>Welcome</h1><p>APP6 is ready.</p>",
      Header.Raw.apply(CIString("Content-Type"), "text/html")
    )
  }

  val routes: HttpRoutes[F] = trace <+> index
}
