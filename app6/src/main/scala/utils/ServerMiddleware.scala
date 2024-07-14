package utils

import cats.data.Kleisli
import cats.effect.{Async, Sync}
import cats.implicits.*
import org.http4s.{Header, HttpApp, Request}
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
            req.putHeaders(spanCorrelationIdHeader)
            req.putHeaders(traceIdHeader)
            val contextId = req.headers
              .get(CIString("context-id"))
              .map(_.head.value)
              .getOrElse("missing context") // TODO:WIP...
            for {
              _ <- span.addAttribute(
                Attribute("span_id", span.context.spanIdHex)
              )
              _ <- span.addAttribute(
                Attribute("trace_id", span.context.traceIdHex)
              )
              _ <- span.addAttribute(Attribute("context-id", contextId))
              response <- service(req)
              _ <- span.addAttribute(
                Attribute("http.status-code", response.status.code.toLong)
              )
              _ <- {
                if (response.status.isSuccess) span.setStatus(StatusCode.Ok)
                else span.setStatus(StatusCode.Error)
              }
            } yield {
              response.putHeaders(spanCorrelationIdHeader)
              response.putHeaders(traceIdHeader)
            }
          }
      }
    }
  }
}

object ServerMiddleware extends ServerMiddleware
