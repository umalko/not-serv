package hyatt.loadtest

import io.gatling.core.Predef._
import io.gatling.core.structure.{ChainBuilder, ScenarioBuilder}
import io.gatling.http.Predef._
import io.gatling.http.protocol.HttpProtocolBuilder

import scala.concurrent.duration._

class LoadTest extends Simulation {

  val httpProtocol: HttpProtocolBuilder = http
    .baseUrl("http://localhost:8092")

  object NewChatEvent {
    val post: ChainBuilder = exec(http("Post NEW chat event")
      .post("/api/v1/chats/new")
      .header("contentType", "application/json;charset=UTF-8")
      .body(StringBody("""{"chatId":"myHardCodedValue","departmentId":"myHardCodedValue"}""")).asJson)
  }

  val myScenario: ScenarioBuilder = scenario("RampUpUsers")
    .exec(NewChatEvent.post)

  setUp(myScenario.inject(
    incrementUsersPerSec(20) // increase the number of users per second by twenty =>
      .times(5) // => five times
      .eachLevelLasting(5 seconds) // holding at that concurrency for five seconds
      .separatedByRampsLasting(5 seconds)
      .startingFrom(20) // starting with 20 users
  )).protocols(httpProtocol)
    .assertions(global.successfulRequests.percent.is(100))
}