package controllers

import org.scalatestplus.play.*

class SeleniumSpec
    extends PlaySpec
    with BaseOneServerPerTest
    with OneBrowserPerTest
    with GreeterApplicationFactory
    with HtmlUnitFactory {

  "SeleniumSpec" should {

    "work from within a browser" in {

      go to ("http://localhost:" + port)

      pageSource must include("Your new application is ready.")
    }
  }
}
