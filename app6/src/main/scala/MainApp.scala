import cats.effect.*
import controllers.HealthCheckController
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits.*

object MainApp extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val httpApp = HealthCheckController.routes.orNotFound

    BlazeServerBuilder[IO]
      .bindHttp(9006, "0.0.0.0")
      .withHttpApp(httpApp)
      .serve
      .compile
      .drain
      .as(ExitCode.Success)
  }
}
