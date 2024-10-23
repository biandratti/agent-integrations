package utils

import java.util.UUID

import play.api.Logging
import play.api.mvc.Headers
import utils.ContextId.cId

object ContextId {
  lazy val cId = "context-id"
}

trait ContextId extends Logging {

  def getCtxId(requestHeader: Headers): String = {
    requestHeader
      .get(cId)
      .getOrElse({
        logger.warn(s"$cId is missing")
        UUID.randomUUID().toString
      })
  }
}
