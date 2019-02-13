package com.dnastack.dos;

import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.isA;

public class DataobjectsE2eTest extends BaseE2eTest {

    @Test
    public void datasetsResourceShouldReturnAList() {
        //@formatter:off
        given()
            .log().method()
            .log().uri()
            .auth().basic(requiredEnv("E2E_BASIC_USERNAME"), requiredEnv("E2E_BASIC_PASSWORD"))
        .when()
            .get("/dataobjects")
        .then()
            .log().ifValidationFails()
            .statusCode(200)
            .body("data_objects", isA(List.class));
        //@formatter:on
    }

}