package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.RawFileBody;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.OpenInjectionStep.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class AddUserSimulation extends Simulation {

    String baseURI = "https://reqres.in";

    HttpProtocolBuilder httpProtocolBuilder = http.baseUrl(baseURI)
            .header("Accept", "application/json")
            .header("content-type", "application/json");

    ScenarioBuilder scenarioBuilder = scenario("Add User Scenario")

            .exec(http("Add User Request")
                    .post("/api/users")
                    .body(RawFileBody("src/test/resources/bodies/AddUser.json")).asJson()
                    .header("content-type", "application/json")
                    .check(status().is(201)))

            .pause(5)

            .exec(http("Get User Request")
                    .get("/api/users/2")
                    .check(status().is(200)))

            .pause(5)

            .exec(http("Get List Of Users Request")
                    .get("/api/users?page=2")
                    .check(status().is(200)));

    {
        setUp(scenarioBuilder.injectOpen(atOnceUsers(1000)).protocols(httpProtocolBuilder));
    }


}
