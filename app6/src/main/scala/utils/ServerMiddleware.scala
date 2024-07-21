package utils

import cats.data.Kleisli
import cats.effect.{Async, Sync}
import cats.implicits.*
import org.http4s.{Header, Headers, HttpApp, Request}
import org.typelevel.ci.CIString
import org.typelevel.otel4s.Attribute
import org.typelevel.otel4s.trace.{SpanKind, StatusCode, Tracer}

trait ServerMiddleware {
  implicit class ServerMiddlewareOps[F[_]: Sync: Async: Tracer](
      service: HttpApp[F]
  ) {
    def traced: HttpApp[F] = {
      Kleisli { (req: Request[F]) =>
        Tracer[F]
          .spanBuilder("handle-incoming-request")
          .addAttribute(Attribute("http.method", req.method.name))
          .addAttribute(Attribute("http.url", req.uri.renderString))
          .withSpanKind(SpanKind.Server)
          .build
          .use { span =>
            val spanCorrelationIdHeader =
              Header.Raw(CIString("span_id"), span.context.spanIdHex)
            val traceIdHeader =
              Header.Raw(CIString("trace_id"), span.context.traceIdHex)

            // Add trace headers to the request
            val newReq = req.withHeaders(
              req.headers.put(spanCorrelationIdHeader, traceIdHeader)
            )

            val contextId = req.headers
              .get(CIString("context-id"))
              .map(_.head.value)
              .getOrElse("missing context")

            for {
              _ <- span.addAttribute(
                Attribute("span_id", span.context.spanIdHex)
              )
              _ <- span.addAttribute(
                Attribute("trace_id", span.context.traceIdHex)
              )
              _ <- span.addAttribute(Attribute("context-id", contextId))
              response <- service(newReq)
              _ <- span.addAttribute(
                Attribute("http.status-code", response.status.code.toLong)
              )
              _ <- {
                if (response.status.isSuccess) span.setStatus(StatusCode.Ok)
                else span.setStatus(StatusCode.Error)
              }
            } yield {
              // Add trace headers to the response
              response.putHeaders(spanCorrelationIdHeader, traceIdHeader)
            }
          }
      }
    }
  }
}

object ServerMiddleware extends ServerMiddleware
