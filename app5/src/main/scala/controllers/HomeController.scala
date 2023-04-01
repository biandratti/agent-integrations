package controllers

import zhttp.http._

object HomeController {
  def apply(): Http[Any, Nothing, Request, Response] =
    Http.collect[Request] { case Method.GET -> !! / "home" =>
      Response.html("<h1>Welcome</h1><p>APP5 is ready.</p>")

    }

}
