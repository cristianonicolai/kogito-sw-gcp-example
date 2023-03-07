package org.acme;

import org.junit.jupiter.api.Test;

import io.quarkus.test.junit.QuarkusIntegrationTest;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.CoreMatchers.startsWith;

@QuarkusIntegrationTest
public class ServiceAccountIT {

    @Test
    void testWorkflow() {
        String uniqueId = given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .post("/ServiceAccountCreate")
                .then()
                .log().everything()
                .statusCode(201)
                .body("workflowdata.email", startsWith("kogito-sw-gcp@"))
                .body("workflowdata.name", containsString("serviceAccounts/kogito-sw-gcp@"))
                .extract().body().path("workflowdata.uniqueId");
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{ \"serviceAccountId\": \"" + uniqueId + "\" }")
                .post("/ServiceAccountGet")
                .then()
                .log().everything()
                .statusCode(201)
                .body("workflowdata.email", startsWith("kogito-sw-gcp@"));
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{ \"serviceAccountId\": \"" + uniqueId + "\" }")
                .post("/ServiceAccountUpdate")
                .then()
                .log().everything()
                .statusCode(201)
                .body("workflowdata.displayName", is("Kogito SW SA"))
                .body("workflowdata.description", is("Kogito SW GCP Example"));
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{ \"serviceAccountId\": \"" + uniqueId + "\" }")
                .post("/ServiceAccountDelete")
                .then()
                .log().everything()
                .statusCode(201);
        given()
                .contentType(ContentType.JSON)
                .accept(ContentType.JSON)
                .body("{ \"serviceAccountId\": \"" + uniqueId + "\" }")
                .post("/ServiceAccountGet")
                .then()
                .log().everything()
                .statusCode(201)
                .body("workflowdata.email", is(nullValue()));
    }

}
