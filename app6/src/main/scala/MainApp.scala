import cats.effect.*
import controllers.HealthCheckController
import io.opentelemetry.api.GlobalOpenTelemetry
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits.*
import org.typelevel.otel4s.Otel4s
import org.typelevel.otel4s.oteljava.OtelJava

object MainApp extends IOApp {

  private def otelResource[F[_]: Async: LiftIO]: Resource[F, Otel4s[F]] = {
    Resource
      .eval(Async[F].delay(GlobalOpenTelemetry.get))
      .evalMap(OtelJava.forAsync[F])
  }

  override def run(args: List[String]): IO[ExitCode] = {
    otelResource[IO].use { _ =>
      val serverResource = BlazeServerBuilder[IO]
        .bindHttp(9006, "0.0.0.0")
        .withHttpApp(HealthCheckController.routes.orNotFound)
        .resource

      serverResource.use(_ => IO.never).as(ExitCode.Success)
    }
  }
}
