package services

import models.TraceResponse
import play.api.libs.ws.WSClient
import play.api.{Logging, MarkerContext}
import utils.ContextId

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TraceService(ws: WSClient) extends Logging {

  private lazy val app2URL = "http://app2:9001/api2/v1/trace"

  def getApp2Trace(ctxId: String)(implicit
      mc: MarkerContext
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
