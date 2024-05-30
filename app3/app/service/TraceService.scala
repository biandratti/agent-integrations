package service

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import models.TraceResponse
import play.api.Logging
import play.api.MarkerContext
import play.api.libs.ws.WSClient
import utils.ContextId

class TraceService(ws: WSClient) extends Logging {

  private lazy val api4URL = "http://app4:9004/api/v1/trace"
  private lazy val api5URL = "http://app5:9005/api/v1/trace"

  def getTrace(ctxId: String)(implicit
      mc: MarkerContext,
      ex: ExecutionContext
  ): Future[TraceResponse] = {
    for {
      _ <- getTrace(ctxId, api4URL)
      result <- getTrace(ctxId, api5URL)
    } yield result
  }

  private def getTrace(ctxId: String, url: String)(implicit
      mc: MarkerContext,
      ex: ExecutionContext
  ): Future[TraceResponse] = {
    ws.url(url)
      .withHttpHeaders(headers = (ContextId.cId, ctxId))
      .get()
      .map(response => {
        logger.info(s"$url response: ${response.body}")
        response.json.as[TraceResponse]
      })
      .recover { case ex: Exception =>
        logger.error(s"$url response", ex)
        throw ex
      }
  }
}
