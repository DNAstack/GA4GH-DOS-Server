package com.dnastack.dos;

import org.junit.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class CookielessTest extends BaseE2eTest {

    @Test
    public void serverShouldNotSetCookies() {
        //@formatter:off
        given()
            .log().method()
            .log().uri()
        .when()
            .get("/")
        .then()
            .log().ifValidationFails()
            .header("set-cookie", is(nullValue()));
        //@formatter:on
    }
}