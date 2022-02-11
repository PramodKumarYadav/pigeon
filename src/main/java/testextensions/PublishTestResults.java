package testextensions;

import com.typesafe.config.Config;
import config.EnvFactory;
import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PublishTestResults {
    private static Config config = EnvFactory.getInstance().getConfig();
    private static final String MONITORING_INFRA_READY_ON_ELASTIC_KIBANA = config.getString("MONITORING_INFRA_READY_ON_ELASTIC_KIBANA");

    public static void toElastic() {
        if (MONITORING_INFRA_READY_ON_ELASTIC_KIBANA.equalsIgnoreCase("true")) {
            TestRunBody testRunBody = new TestRunBody().setDefault();
            log.info("testRunBody: {}", testRunBody);
            publishResultsOnElastic(testRunBody);
        }
    }

    private static Response publishResultsOnElastic(TestRunBody testRunBody) {
        return RestAssured
                .given()
                .spec(getElasticBasicSpecification())
                .body(testRunBody)
                .when()
                .post("/testruns/run")
                .then().log().ifError().extract().response();
    }

    private static RequestSpecification getElasticBasicSpecification() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:9200")
                .build();
    }
}
