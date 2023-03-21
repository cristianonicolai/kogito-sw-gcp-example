package org.acme;

import java.time.Duration;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.awaitility.Awaitility.await;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;

@QuarkusIntegrationTest
public class ComputeInstanceIT {

    static {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
    }

    @Test
    void testWorkflow() {
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post("/ComputeInstanceCreate")
                .then()
                .log().everything()
                .statusCode(201)
                .body("workflowdata.status", startsWith("RUNNING"));

        Duration timeout = Duration.ofMinutes(5);

        await().atMost(timeout).untilAsserted(() -> given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post("/ComputeInstanceGet")
                .then()
                .log().everything()
                .statusCode(201)
                .body("workflowdata.status", startsWith("RUNNING")));

        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post("/ComputeInstanceDelete")
                .then()
                .log().everything()
                .statusCode(201);

        await().atMost(timeout).untilAsserted(() -> given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post("/ComputeInstanceGet")
                .then()
                .log().everything()
                .statusCode(201)
                .body("workflowdata.status", is(nullValue())));
    }

}
