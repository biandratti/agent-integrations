package service

import models.TraceResponse
import play.api.libs.ws.WSClient
import play.api.{Configuration, Logging, MarkerContext}
import utils.ContextId

import javax.inject.Inject
import scala.concurrent.{ExecutionContext, Future}

class TraceService @Inject() (ws: WSClient, config: Configuration) extends Logging {

  private lazy val api4URL = config.get[String]("app4.url")
  private lazy val api5URL = config.get[String]("app5.url")

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
