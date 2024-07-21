package utils

import cats.effect.Sync
import org.http4s.Request
import org.http4s.Response
import org.http4s.server.middleware.RequestId
import java.util.UUID

final case class RequestContext(requestId: String)

object HttpRequestContextual {
  type Contextual[T] = RequestContext => T

  def contextual[F[_]: Sync](request: Request[F])(f: Contextual[F[Response[F]]]): F[Response[F]] = {
    val context: RequestContext = request.attributes.lookup(RequestId.requestIdAttrKey)
      .fold(RequestContext(UUID.randomUUID().toString))(RequestContext.apply)
    f(context)
  }
}