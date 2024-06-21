import cats.effect.*
import controllers.HealthCheckController
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits.*
import utils.OtelResource

object MainApp extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val serverResource: Resource[IO, Unit] = for {
      _ <- OtelResource.apply[IO]
      _ <- BlazeServerBuilder[IO]
        .bindHttp(9006, "0.0.0.0")
        .withHttpApp(HealthCheckController.routes.orNotFound)
        .resource
    } yield ()

    serverResource.use(_ => IO.never).as(ExitCode.Success)
  }
}
