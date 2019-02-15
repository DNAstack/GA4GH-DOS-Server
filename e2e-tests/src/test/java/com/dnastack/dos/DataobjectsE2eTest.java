package com.dnastack.dos;

import com.dnastack.dos.testutil.RestAssuredConfig;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.time.Instant;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.isA;

public class DataobjectsE2eTest extends BaseE2eTest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class CreateObjectRequest {
        DrsObject Object;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class DrsObject {
        String id;
        List<String> urls;
        String size;
        List<Checksum> checksums;
        Instant created;
        Instant updated;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Checksum {
        String type;
        String checksum;
    }

    private static DrsObject exampleObject() {
        long unique = System.currentTimeMillis();
        Instant now = Instant.now();
        return new DrsObject(
                "bid-" + unique,
                List.of("https://example.com/file-" + unique + ".dat"),
                "" + unique,
                List.of(new Checksum("md5", "68b329da9893e34099c7d8ad5cb9c940")),
                now,
                now);
    }

    @Test
    public void getDatasetsShouldRequireAuth() {
        //@formatter:off
        given()
            .log().method()
            .log().uri()
        .when()
            .get("/dataobjects")
        .then()
            .log().ifValidationFails()
            .statusCode(401);
        //@formatter:on
    }

    @Test
    public void postDatasetsShouldRequireAuth() {
        //@formatter:off
        given()
            .log().method()
            .log().uri()
        .when()
            .body(new CreateObjectRequest(exampleObject()), RestAssuredConfig.DRS_MAPPER)
            .post("/dataobjects")
        .then()
            .log().ifValidationFails()
            .statusCode(401);
        //@formatter:on
    }

    @Test
    public void datasetsResourceShouldReturnAList() {
        //@formatter:off
        given()
            .log().method()
            .log().uri()
            .auth().basic(requiredEnv("E2E_REGULAR_USERNAME"), requiredEnv("E2E_REGULAR_PASSWORD"))
        .when()
            .get("/dataobjects")
        .then()
            .log().ifValidationFails()
            .statusCode(200)
            .body("data_objects", isA(List.class));
        //@formatter:on
    }

}