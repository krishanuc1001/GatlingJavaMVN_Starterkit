package simulations;

import io.gatling.javaapi.core.ScenarioBuilder;
import io.gatling.javaapi.core.Simulation;
import io.gatling.javaapi.http.HttpProtocolBuilder;

import static io.gatling.javaapi.core.CoreDsl.jsonPath;
import static io.gatling.javaapi.core.CoreDsl.scenario;
import static io.gatling.javaapi.core.OpenInjectionStep.atOnceUsers;
import static io.gatling.javaapi.http.HttpDsl.http;
import static io.gatling.javaapi.http.HttpDsl.status;

public class CorrelationSimulation extends Simulation {

    final String baseURI = "https://gorest.co.in";
    final String bearerToken = "028e11e37364fd93601f1132555562815fbe92252d38b8e0314d983abb6cbcd1";

    HttpProtocolBuilder httpProtocolBuilder = http.baseUrl(baseURI)
            .header("Authorization", "Bearer " + bearerToken);

    ScenarioBuilder scenarioBuilder = scenario("Correlation and Extract data Scenario")

            .exec(http("Get all Users")
                    .get("/public/v2/users")
                    .check(jsonPath("$[0].id").saveAs("userID"))
                    .check(status().is(200)))

            .pause(5)

            .exec(http("Get Specific User")
                    .get("/public/v2/users/#{userID}")
                    .check(jsonPath("$.id").is("8437"))
                    .check(jsonPath("$.name").is("Krishanu"))
                    .check(jsonPath("$.email").is("kc@xyz.com"))
                    .check(status().is(200)));


    {
        setUp(scenarioBuilder.injectOpen(atOnceUsers(1)).protocols(httpProtocolBuilder));
    }


}
