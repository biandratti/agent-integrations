import controllers.GreetingApp
import zhttp.service.Server
import zio.*

object MainApp extends ZIOAppDefault {
  def run: ZIO[Any, Throwable, Nothing] =
    Server
      .start(
        port = 9005,
        http = GreetingApp()
      )
      .provide()
}
