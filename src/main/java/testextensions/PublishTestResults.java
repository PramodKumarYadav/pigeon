package testextensions;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class PublishTestResults {
    private static final String TEST_RUN = String.format("RUN-%s", System.currentTimeMillis());

    public static Response toElastic() {
        return RestAssured
                .given()
                .spec(getElasticBasicSpecification())
                .body(getTestRunMap())
                .when()
                .post("/testruns/run")
                .then().log().ifError().extract().response();
    }

    private static Map<String, String> getTestRunMap() {
        Map<String, String> testResult = new HashMap<>();
        testResult.put("project", "pigeon");
        testResult.put("testRun", TEST_RUN);
        testResult.put("testClass", TestExecutionLifecycle.getTestClassName());
        testResult.put("testName", TestExecutionLifecycle.getTestName());
        testResult.put("status", TestExecutionLifecycle.getTestResult());
        testResult.put("reason", TestExecutionLifecycle.getReason());
        testResult.put("executionTime", LocalDateTime.now().toString());
        testResult.put("triggeredBy", System.getProperty("user.name")); //platform independent
        return testResult;
    }

    public static RequestSpecification getElasticBasicSpecification() {
        return new RequestSpecBuilder()
                .setAccept(ContentType.JSON)
                .setContentType(ContentType.JSON)
                .setBaseUri("http://localhost:9200")
                .build();
    }
}
