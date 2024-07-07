package services

import models.TraceResponse
import play.api.libs.ws.WSClient
import play.api.{Configuration, Logging, MarkerContext}
import utils.ContextId

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class TraceService @Inject() (ws: WSClient, config: Configuration)
    extends Logging {

  private lazy val app2URL = config.get[String]("app2.url")

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
