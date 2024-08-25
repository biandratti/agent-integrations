package utils

import cats.effect.{Async, LiftIO, Resource}
import io.opentelemetry.api.GlobalOpenTelemetry
import org.typelevel.otel4s.Otel4s
import org.typelevel.otel4s.oteljava.OtelJava

object OtelResource {
  def apply[F[_]: Async: LiftIO]: Resource[F, Otel4s[F]] = {
    Resource
      .eval(Async[F].delay(GlobalOpenTelemetry.get))
      .evalMap(OtelJava.forAsync[F])
  }
}
