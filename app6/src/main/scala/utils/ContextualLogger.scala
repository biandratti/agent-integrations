package utils

import cats.effect.{Async, Sync}
import org.typelevel.log4cats.Logger

class ContextualLogger[F[_]: Sync](logger: Logger[F]) {

  private def contextual(message: => String)(implicit
      ctx: RequestContext
  ): String =
    s"${ctx.requestId} - $message"

  def error(message: => String)(implicit ctx: RequestContext): F[Unit] =
    logger.error(contextual(message))

  def warn(message: => String)(implicit ctx: RequestContext): F[Unit] =
    logger.warn(contextual(message))

  def info(message: => String)(implicit ctx: RequestContext): F[Unit] =
    logger.info(contextual(message))

  def debug(message: => String)(implicit ctx: RequestContext): F[Unit] =
    logger.debug(contextual(message))

  def trace(message: => String)(implicit ctx: RequestContext): F[Unit] =
    logger.trace(contextual(message))

  def error(t: Throwable)(message: => String)(implicit
      ctx: RequestContext
  ): F[Unit] =
    logger.error(t)(contextual(message))

  def warn(t: Throwable)(message: => String)(implicit
      ctx: RequestContext
  ): F[Unit] =
    logger.warn(t)(contextual(message))

  def info(t: Throwable)(message: => String)(implicit
      ctx: RequestContext
  ): F[Unit] =
    logger.info(t)(contextual(message))

  def debug(t: Throwable)(message: => String)(implicit
      ctx: RequestContext
  ): F[Unit] =
    logger.debug(t)(contextual(message))

  def trace(t: Throwable)(message: => String)(implicit
      ctx: RequestContext
  ): F[Unit] =
    logger.trace(t)(contextual(message))
}
