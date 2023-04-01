package controllers

import models.TraceResponse
import utils.LogAspect.{logAnnotateCorrelationId, logSpan}
import zhttp.http.*
import zio.ZIO
import zio.json.*

object AppController {
  def apply(): Http[Any, Throwable, Request, Response] =
    Http.collectZIO[Request] {

      case req @ (Method.GET -> !! / "api" / "v1" / "trace") =>
        {
          for {
            _ <- ZIO.logInfo(s"trace request")
          } yield Response.json(TraceResponse("???").toJson)
        } @@ logSpan("get-user") @@ logAnnotateCorrelationId(req)

    }
}
