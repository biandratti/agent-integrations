import org.scalatestplus.play.{BaseOneAppPerTest, PlaySpec}
import play.api.test.*
import play.api.test.Helpers.*

/** */
class ApplicationSpec
    extends PlaySpec
    with BaseOneAppPerTest
    with TraceApplicationFactory {

  "Routes" should {
    "send 404 on a bad request" in {
      route(app, FakeRequest(GET, "/boum")).map(status(_)) mustBe Some(
        NOT_FOUND
      )
    }
  }

  "HomeController" should {
    "render the index page" in {
      val home = route(app, FakeRequest(GET, "/")).get

      status(home) mustBe OK
      contentType(home) mustBe Some("text/html")
      contentAsString(home) must include("APP1 is ready.")
    }
  }

}
