package controllers

import zhttp.http._
import zio.test.*

object HealthCheckControllerSpec extends ZIOSpecDefault {
  def spec: Spec[Any, Serializable] =
    suite("HealthCheckControllerSpec")(
      test("ok status") {
        for {
          response <- HealthCheckController
            .routes(
              Request(method = Method.GET, url = URL(!! / "health"))
            )
          body <- response.body.asString
        } yield {
          assertTrue(
            body == "<!DOCTYPE html><h1>Welcome</h1><p>APP5 is ready.</p>",
            response.status == Status.Ok
          )
        }
      }
    )
}
