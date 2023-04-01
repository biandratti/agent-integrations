package utils

import play.api.Logging
import play.api.mvc.Headers
import utils.ContextId.cId

object ContextId {
  lazy val cId = "context-id" // TODO: choose correlation-id in all the solution
}

trait ContextId extends Logging {

  def getCtxId(requestHeader: Headers): String = {
    requestHeader
      .get(cId)
      .getOrElse({
        logger.error(s"$cId is missing")
        throw new RuntimeException(s"$cId is missing")
      })
  }
}
