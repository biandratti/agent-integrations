import controllers.AppController
import zhttp.service.Server
import zio.*
import zio.logging.LogFormat
import zio.logging.backend.SLF4J

object MainApp extends ZIOAppDefault {

  override val bootstrap = SLF4J.slf4j(LogLevel.All, LogFormat.colored)

  override def run: ZIO[Any, Throwable, Nothing] =
    Server
      .start(
        port = 9005,
        http = AppController()
      )
      .provide()
}
