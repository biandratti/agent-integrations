package services

import models.TraceResponse
import play.api.Logging
import play.api.libs.ws.WSClient

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future

class TraceService(ws: WSClient) extends Logging {

  private lazy val app2URL = "http://localhost:9001/api2/v1/trace"

  def getApp2Trace(): Future[TraceResponse] = {
    ws.url(app2URL)
      .get()
      .map(response => {
        logger.info(s"APP2 response: ${response.body}")
        response.json.as[TraceResponse]
      })
  }
}
