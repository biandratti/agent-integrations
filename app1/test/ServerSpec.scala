import org.scalatest.concurrent.IntegrationPatience
import org.scalatest.concurrent.ScalaFutures
import org.scalatestplus.play._
import play.api.mvc.Results
import play.api.test.Helpers._
import play.api.test.WsTestClient

class ServerSpec
    extends PlaySpec
    with BaseOneServerPerSuite
    with TraceApplicationFactory
    with Results
    with ScalaFutures
    with IntegrationPatience {

  "Server query should" should {
    "work" in {
      WsTestClient.withClient { client =>
        whenReady(wsUrl("/").get()) { response =>
          response.status mustBe OK
        }
      }
    }
  }
}
