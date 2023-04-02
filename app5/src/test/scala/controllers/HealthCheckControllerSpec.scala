package controllers

import zhttp.http._
import zio.test.*

object HealthCheckControllerSpec extends ZIOSpecDefault {
  def spec =
    suite("HealthCheckControllerSpec")(
      test("ok status") {
        HealthCheckController
          .routes(
            Request(method = Method.GET, url = URL(!! / "health"))
          )
          .map(result =>
            assertTrue(
              result.data == Response
                .html("<h1>Welcome</h1><p>APP5 is ready.</p>")
                .data
            )
          )
      }
    )
}
