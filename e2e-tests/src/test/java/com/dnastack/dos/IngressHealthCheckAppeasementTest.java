package com.dnastack.dos;

import org.junit.Test;

import java.util.stream.Stream;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class IngressHealthCheckAppeasementTest extends BaseE2eTest {

    @Test
    public void getRootResourceShouldReturn200() {
        //@formatter:off
        given()
            .log().method()
            .log().uri()
        .when()
            .get("/")
        .then()
            .log().ifValidationFails()
            .statusCode(200);
        //@formatter:on
    }
}