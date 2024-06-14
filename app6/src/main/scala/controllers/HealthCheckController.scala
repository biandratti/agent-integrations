package controllers

import cats.effect.*
import org.http4s.*
import org.http4s.dsl.io.*
import org.typelevel.ci.CIString

object HealthCheckController {
  val routes: HttpRoutes[IO] = HttpRoutes.of[IO] {
    case GET -> Root / "health" =>
      Ok(
        "<h1>Welcome</h1><p>APP6 is ready.</p>",
        Header.Raw.apply(CIString("Content-Type"), "text/html")
      )
  }
}
