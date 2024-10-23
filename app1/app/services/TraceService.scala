package services

import javax.inject.Inject

import scala.concurrent.ExecutionContext
import scala.concurrent.Future

import models.TraceResponse
import play.api.Configuration
import play.api.Logging
import play.api.MarkerContext
import play.api.libs.ws.WSClient
import utils.ContextId

class TraceService @Inject() (ws: WSClient, config: Configuration)
    extends Logging {

  private lazy val app2URL = config.get[String]("app2.url")

  def getTrace(ctxId: String)(using
      mc: MarkerContext,
      ex: ExecutionContext
  ): Future[TraceResponse] = {
    logger.info(s"app1 request to app1")
    ws.url(app2URL)
      .withHttpHeaders(headers = (ContextId.cId, ctxId))
      .get()
      .map(response => {
        logger.info(
          s"app2 response: ${response.body} - headers: ${response.headers}"
        )
        response.json.as[TraceResponse]
      })
      .recover { case ex: Exception =>
        logger.error("app2 response", ex)
        throw ex
      }
  }
}
