package utils

import zhttp.http.Request
import zio.*

object LogAspect {

  def logSpan(
      label: String
  ): ZIOAspect[Nothing, Any, Nothing, Any, Nothing, Any] =
    new ZIOAspect[Nothing, Any, Nothing, Any, Nothing, Any] {
      override def apply[R, E, A](zio: ZIO[R, E, A])(implicit
          trace: Trace
      ): ZIO[R, E, A] =
        ZIO.logSpan(label)(zio)
    }

  def logAnnotateCorrelationId(
      req: Request
  ): ZIOAspect[Nothing, Any, Nothing, Any, Nothing, Any] =
    new ZIOAspect[Nothing, Any, Nothing, Any, Nothing, Any] {
      override def apply[R, E, A](
          zio: ZIO[R, E, A]
      )(implicit trace: Trace): ZIO[R, E, A] =
        correlationId(req).flatMap(id => ZIO.logAnnotate("context-id", id)(zio))

      def correlationId(req: Request): UIO[String] =
        ZIO
          .succeed(req.header("context-id").map(_._2.toString))
          .flatMap(x => Random.nextUUID.map(uuid => x.getOrElse(uuid.toString)))
    }
}
