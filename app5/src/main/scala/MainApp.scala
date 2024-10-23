import controllers.AppController
import controllers.HealthCheckController
import io.opentelemetry.api.GlobalOpenTelemetry
import zhttp.service.Server
import zio._
import zio.logging.LogFormat
import zio.logging.backend.SLF4J

object MainApp extends ZIOAppDefault {

  val globalOpenTelemetry = GlobalOpenTelemetry.get()

  override val bootstrap: ZLayer[ZIOAppArgs, Any, Any] =
    SLF4J.slf4j(LogFormat.colored)

  override def run: ZIO[Any, Throwable, Nothing] =
    Server
      .start(
        port = 9005,
        http = AppController.routes ++ HealthCheckController.routes
      )
}
