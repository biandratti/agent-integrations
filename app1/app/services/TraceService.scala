package services

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import models.TraceResponse
import play.api.Logging
import play.api.MarkerContext
import play.api.libs.ws.WSClient
import utils.ContextId

class TraceService(ws: WSClient) extends Logging {

  private lazy val app2URL = "http://app2:9002/api/v1/trace"

  def getTrace(ctxId: String)(implicit
      mc: MarkerContext,
      ex: ExecutionContext
  ): Future[TraceResponse] = {
    ws.url(app2URL)
      .withHttpHeaders(headers = (ContextId.cId, ctxId))
      .get()
      .map(response => {
        logger.info(s"app2 response: ${response.body}")
        response.json.as[TraceResponse]
      })
      .recover { case ex: Exception =>
        logger.error("app2 response", ex)
        throw ex
      }
  }
}
