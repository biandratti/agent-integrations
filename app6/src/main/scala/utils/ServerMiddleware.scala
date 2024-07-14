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
            for {
              response <- service(req)
              _ <- span.addAttribute(
                Attribute("http.status-code", response.status.code.toLong)
              )
              _ <- {
                if (response.status.isSuccess) span.setStatus(StatusCode.Ok)
                else span.setStatus(StatusCode.Error)
              }
              //contextId = req.headers.get(CIString("context-id")).toString //TODO....
              _ <- span.addAttribute(Attribute("context-id" , "mycontextid"))
            } yield {
              val spanCorrelationIdHeader = Header.Raw(CIString("span_id"), span.context.spanIdHex)
              val traceIdHeader = Header.Raw(CIString("trace_id"), span.context.traceIdHex)
              response.putHeaders(spanCorrelationIdHeader)
              response.putHeaders(traceIdHeader)
            }
          }
      }
    }
  }
}

object ServerMiddleware extends ServerMiddleware
