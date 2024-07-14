package controllers

import io.opentelemetry.api.trace.Span
import models.TraceResponse
import utils.LogAspect.{logAnnotateCorrelationId, logSpan, logTrace}
import zhttp.http.*
import zio.ZIO
import zio.json.*

object AppController {
  val routes: Http[Any, Throwable, Request, Response] =
    Http.collectZIO[Request] {

      case req @ (Method.GET -> !! / "api" / "v1" / "trace") =>
        {
          for {
            _ <- ZIO.logInfo(s"trace request - headers ${req.headersAsList}")
          } yield Response.json(
            TraceResponse(Span.current().getSpanContext.getTraceId).toJson
          )
        } @@ logSpan() @@ logTrace() @@ logAnnotateCorrelationId(req)

    }
}
