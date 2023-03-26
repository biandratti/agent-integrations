package service

import models.TraceResponse
import play.api.libs.ws.WSClient
import play.api.{Logging, MarkerContext}
import utils.ContextId

import scala.concurrent.{ExecutionContext, Future}

class TraceService(ws: WSClient) extends Logging {

  private lazy val app2URL = "http://app3:9002/api3/v1/trace"

  def getApp3Trace(ctxId: String)(implicit
      mc: MarkerContext,
      ex: ExecutionContext
  ): Future[TraceResponse] = {
    ws.url(app2URL)
      .withHttpHeaders(headers = (ContextId.cId, ctxId))
      .get()
      .map(response => {
        logger.info(s"APP2 response: ${response.body}")
        response.json.as[TraceResponse]
      })
      .recover { case ex: Exception =>
        logger.error("app2 response", ex)
        throw ex
      }
  }
}
