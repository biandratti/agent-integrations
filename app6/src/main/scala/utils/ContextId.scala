package utils

import org.http4s.Headers
import org.log4s.getLogger
import org.typelevel.ci.CIString
import utils.ContextId.cId

import java.util.UUID

object ContextId {
  lazy val cId = CIString("context-id") // TODO: choose correlation-id in all the solution
}

trait ContextId {

  private[this] val logger = getLogger
  def getCtxId(requestHeader: Headers): String = {
    requestHeader
      .get(cId).map(_.head.value)
      .getOrElse({
        logger.warn(s"$cId is missing")
        UUID.randomUUID().toString
      })
  }
}
