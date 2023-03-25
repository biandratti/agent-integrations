package service

import models.TraceResponse
import play.api.libs.ws.WSClient
import play.api.{Logging, MarkerContext}
import utils.ContextId

import scala.concurrent.{ExecutionContext, Future}

class TraceService(ws: WSClient) extends Logging {

  private lazy val apiURL = "http://app4:9004/api/v1/trace"

  def getTrace(ctxId: String)(implicit
      mc: MarkerContext,
      ex: ExecutionContext
  ): Future[TraceResponse] = {
    ws.url(apiURL)
      .withHttpHeaders(headers = (ContextId.cId, ctxId))
      .get()
      .map(response => {
        logger.info(s"app4 response: ${response.body}")
        response.json.as[TraceResponse]
      })
      .recover { case ex: Exception =>
        logger.error("app4 response", ex)
        throw ex
      }
  }
}
