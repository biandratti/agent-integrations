package controllers

import io.opentelemetry.api.trace.Span
import models.TraceResponse
import utils.LogAspect.logAnnotateCorrelationId
import utils.LogAspect.logSpan
import utils.LogAspect.logTrace
import zhttp.http._
import zio.ZIO
import zio.json._

object AppController {
  val routes: Http[Any, Throwable, Request, Response] =
    Http.collectZIO[Request] {

      case req @ (Method.GET -> !! / "api" / "v1" / "trace") =>
        {
          for {
            _ <- ZIO.logInfo(s"trace request")
          } yield Response.json(
            TraceResponse(Span.current().getSpanContext.getTraceId).toJson
          )
        } @@ logSpan() @@ logTrace() @@ logAnnotateCorrelationId(req)

    }
}
