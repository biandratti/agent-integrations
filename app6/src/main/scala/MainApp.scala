import cats.effect.*
import controllers.AppController
import org.http4s.blaze.server.BlazeServerBuilder
import utils.{OtelResource, ServerMiddleware}
object MainApp extends IOApp with ServerMiddleware {

  override def run(args: List[String]): IO[ExitCode] = {
    val serverResource: Resource[IO, Unit] = for {
      otel <- OtelResource.apply[IO]
      tracer <- Resource.eval(otel.tracerProvider.get("my-tracer"))
      appController = new AppController[IO](tracer)
      httpApp = appController.httpApp
      _ <- BlazeServerBuilder[IO]
        .bindHttp(9006, "0.0.0.0")
        .withHttpApp(httpApp)
        .resource
    } yield ()

    serverResource.use(_ => IO.never).as(ExitCode.Success)
  }
}
