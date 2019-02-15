package com.dnastack.dos;

import com.dnastack.dos.testutil.RestAssuredConfig;
import io.restassured.internal.mapping.Jackson2Mapper;
import io.restassured.mapper.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.junit.Test;

import java.time.Instant;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.*;

public class DatabundlesE2eTest extends BaseE2eTest {

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class CreateBundleRequest {
        Bundle bundle;
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    private static class Bundle {
        String id;
        String version;
        List<String> objectIds;
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

    private static Bundle exampleDatabundle() {
        long unique = System.currentTimeMillis();
        Instant now = Instant.now();
        return new Bundle(
                "bid-" + unique,
                "v" + unique,
                List.of("oid-" + unique),
                List.of(new Checksum("md5", "68b329da9893e34099c7d8ad5cb9c940")),
                now,
                now);
    }

    @Test
    public void getDatabundlesShouldRequireAuth() {
        //@formatter:off
        given()
            .log().method()
            .log().uri()
        .when()
            .get("/databundles")
        .then()
            .log().ifValidationFails()
            .statusCode(401);
        //@formatter:on
    }

    @Test
    public void postDatabundlesShouldRequireAuth() {
        //@formatter:off
        given()
            .log().method()
            .log().uri()
        .when()
            .body(new CreateBundleRequest(exampleDatabundle()), RestAssuredConfig.DRS_MAPPER)
            .post("/databundles")
        .then()
            .log().ifValidationFails()
            .statusCode(401);
        //@formatter:on
    }

    @Test
    public void getDatabundlesShouldReturnAList() {
        //@formatter:off
        given()
            .log().method()
            .log().uri()
            .auth().basic(requiredEnv("E2E_REGULAR_USERNAME"), requiredEnv("E2E_REGULAR_PASSWORD"))
        .when()
            .get("/databundles")
        .then()
            .log().ifValidationFails()
            .statusCode(200)
            .body("data_bundles", isA(List.class));
        //@formatter:on
    }

}