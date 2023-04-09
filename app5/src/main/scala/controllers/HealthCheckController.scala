package controllers

import zhttp.http._

object HealthCheckController {
  val routes: Http[Any, Nothing, Request, Response] =
    Http.collect[Request] { case Method.GET -> !! / "health" =>
      Response.html("<h1>Welcome</h1><p>APP5 is ready.</p>")

    }

}
