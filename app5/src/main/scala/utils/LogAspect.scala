package utils

import io.opentelemetry.api.trace.Span
import zhttp.http.Request
import zio._

object LogAspect {

  def logSpan(): ZIOAspect[Nothing, Any, Nothing, Any, Nothing, Any] =
    new ZIOAspect[Nothing, Any, Nothing, Any, Nothing, Any] {
      override def apply[R, E, A](zio: ZIO[R, E, A])(using
          trace: Trace
      ): ZIO[R, E, A] =
        spanCorrelationId().flatMap(id => ZIO.logAnnotate("span_id", id)(zio))

      def spanCorrelationId(): UIO[String] =
        ZIO.succeed(Span.current().getSpanContext.getSpanId)
    }

  def logTrace(): ZIOAspect[Nothing, Any, Nothing, Any, Nothing, Any] =
    new ZIOAspect[Nothing, Any, Nothing, Any, Nothing, Any] {
      override def apply[R, E, A](zio: ZIO[R, E, A])(using
          trace: Trace
      ): ZIO[R, E, A] =
        traceCorrelationId().flatMap(id => ZIO.logAnnotate("trace_id", id)(zio))

      def traceCorrelationId(): UIO[String] =
        ZIO.succeed(Span.current().getSpanContext.getTraceId)
    }

  def logAnnotateCorrelationId(
      req: Request
  ): ZIOAspect[Nothing, Any, Nothing, Any, Nothing, Any] =
    new ZIOAspect[Nothing, Any, Nothing, Any, Nothing, Any] {
      lazy val cId = "context-id"

      override def apply[R, E, A](
          zio: ZIO[R, E, A]
      )(using trace: Trace): ZIO[R, E, A] = {
        setSpanAttribute(req)
        correlationId(req).flatMap(id => ZIO.logAnnotate(cId, id)(zio))
      }

      private def correlationId(req: Request): UIO[String] =
        ZIO
          .succeed(req.header(cId).map(_._2.toString))
          .flatMap(x => Random.nextUUID.map(uuid => x.getOrElse(uuid.toString)))

      private def setSpanAttribute(req: Request): Option[Span] = {
        req
          .header(cId)
          .map(_._2.toString)
          .map(contextId => Span.current().setAttribute(cId, contextId))
      }
    }
}
