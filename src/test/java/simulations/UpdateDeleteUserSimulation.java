package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.core.OpenInjectionStep.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.*;

public class UpdateDeleteUserSimulation extends Simulation {

    String baseURI = "https://reqres.in";

    HttpProtocolBuilder httpProtocolBuilder = http.baseUrl(baseURI)
            .header("Accept", "application/json")
            .header("content-type", "application/json");

    ScenarioBuilder scenarioBuilder = scenario("Update And Delete User Scenario")

            .exec(http("Update User Request")
                    .put("/api/users/2")
                    .body(RawFileBody("src/test/resources/bodies/UpdateUser.json")).asJson()
                    .check(status().in(200, 210)))

            .pause(5)

            .exec(http("Delete User Request")
                    .delete("/api/users/2")
                    .check(status().in(200, 204, 210)));

    {
        setUp(scenarioBuilder.injectOpen(atOnceUsers(1)).protocols(httpProtocolBuilder));
    }


}
