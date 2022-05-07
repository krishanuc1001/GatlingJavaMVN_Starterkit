package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import java.time.Duration;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.OpenInjectionStep.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class CheckPauseTimeAndResponse extends Simulation {

    String baseURI = "https://reqres.in";

    HttpProtocolBuilder httpProtocolBuilder = http.baseUrl(baseURI)
            .header("Accept", "application/json")
            .header("content-type", "application/json");


    ScenarioBuilder scenarioBuilder = scenario("Add User Scenario")

            .exec(http("Get List Of Users Request")
                    .get("/api/users?page=2")
                    .check(status().is(200)))

            .pause(5)

            .exec(http("Get User Request")
                    .get("/api/users/2")
                    .check(status().in(200, 210)))

            .pause(1, 10)

            .exec(http("Single User Not found Request")
                    .get("/api/users/23")
                    .check(status().not(400), status().not(500)))

            .pause(Duration.ofMillis(3000));


    {
        setUp(scenarioBuilder.injectOpen(atOnceUsers(100)).protocols(httpProtocolBuilder));
    }

}
