import org.scalatestplus.play.*

class SeleniumSpec
    extends PlaySpec
    with BaseOneServerPerTest
    with OneBrowserPerTest
    with TraceApplicationFactory
    with HtmlUnitFactory {

  "SeleniumSpec" should {

    "work from within a browser" in {

      go to ("http://localhost:" + port)

      pageSource must include("APP3 is ready.")
    }
  }
}
