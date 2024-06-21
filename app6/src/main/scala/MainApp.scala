import cats.effect.*
import controllers.AppController
import org.http4s.blaze.server.BlazeServerBuilder
import org.http4s.implicits.*
import utils.OtelResource

object MainApp extends IOApp {

  override def run(args: List[String]): IO[ExitCode] = {
    val serverResource: Resource[IO, Unit] = for {
      _ <- OtelResource.apply[IO]
      appController = new AppController[IO]
      httpApp = appController.routes.orNotFound
      _ <- BlazeServerBuilder[IO]
        .bindHttp(9006, "0.0.0.0")
        .withHttpApp(httpApp)
        .resource
    } yield ()

    serverResource.use(_ => IO.never).as(ExitCode.Success)
  }
}
