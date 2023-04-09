import io.gatling.core.Predef.*
import io.gatling.core.scenario.Simulation
import io.gatling.http.Predef.*
import java.util.UUID;
import scala.concurrent.duration.*
import scala.language.postfixOps

class TraceScenario extends Simulation {

  private val logger = com.typesafe.scalalogging.Logger(getClass)

  val port = java.lang.Long.getLong("port", 9003)
  val httpProtocol = http.baseUrl(s"http://localhost:$port")
  val nbUsers = Integer.getInteger("users", 1)
  val myRamp = java.lang.Long.getLong("ramp", 0)

  private def execTrace() = {
    val contextId = UUID.randomUUID().toString
    exec {
      http(s"context $contextId")
        .get("/api/v1/trace")
        .header("Content-Type", "application/json")
        .header("context-id", contextId)
        .check(status is 200)
        .transformResponse { (response, _) =>
          logger.info(response.body.string)
          response
        }
        .requestTimeout(1 minute)
    }
  }

  setUp(
    scenario("Simulate traffic")
      .exec(execTrace)
      .inject(rampUsers(nbUsers).during(myRamp))
      .protocols(httpProtocol)
  )
}
