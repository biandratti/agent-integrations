import io.gatling.core.Predef.*
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef.*
import java.util.UUID
import scala.concurrent.duration.*
import scala.language.postfixOps

class TraceScenario extends Simulation {

  val httpProtocol =
    http.baseUrl(
      "http://localhost:9003"
    ) // .contentTypeHeader("application/json")
  val users = 10

  private def execTrace() = {
    val contextId = UUID.randomUUID().toString
    exec {
      http(s"context $contextId")
        .get("/api/v1/trace")
        .header("Content-Type", "application/json")
        .header("context-id", contextId)
        .check(status is 200)
        .requestTimeout(1 minute)
    }
  }

  setUp(
    scenario("Simulate traffic")
      .exec(execTrace)
      .inject(atOnceUsers(users))
      .protocols(httpProtocol)
  )
}
