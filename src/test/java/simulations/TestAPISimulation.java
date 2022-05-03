package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.OpenInjectionStep.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class TestAPISimulation extends Simulation {
    String baseURI = "https://reqres.in";

    // 1. Create httpProtocolBuilder
    HttpProtocolBuilder httpProtocolBuilder = http.baseUrl(baseURI)
            .header("Accept", "application/json")
            .header("content-type", "application/json");


    // 2. Create scenarioBuilder
    ScenarioBuilder scenarioBuilder = scenario("Get User Scenario")
            .exec(http("Get User Request")
                    .get("/api/users/2")
                    .check(status().is(200)));

    // 3. Create setUp static block to simulate
    {
        setUp(scenarioBuilder.injectOpen(atOnceUsers(1000)).protocols(httpProtocolBuilder));
    }


}
