package org.example;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import static io.restassured.module.jsv.JsonSchemaValidator.*;

import java.util.HashMap;

@DisplayName("Auth API tests")
public class AuthTest {

    //auth API
    private static final String AUTH_API = "/auth";

    // login endpoint
    private final String LOGIN_PATH = "/login";

    private HashMap<String,Object> login_body;

    private ValidatableResponse LOGIN_REQUEST(){        
        return given().log().ifValidationFails().contentType(ContentType.JSON).body(login_body)
        .when().post(LOGIN_PATH).then().log().ifValidationFails();
    }

    // me endpoint
    private final String ME_PATH = "/me";

    private ValidatableResponse ME_REQUEST(String accessToken){
        return given().log().ifValidationFails().header("Authorization", "Bearer "+accessToken)
        .when().get(ME_PATH).then().log().ifValidationFails();
    }

    // refresh endpoint
    private final String REFRESH_PATH = "/refresh";

    private ValidatableResponse REFRESH_REQUEST(String refreshToken){
        return given().log().ifValidationFails().contentType(ContentType.JSON)
        .body(String.format("{\"refreshToken\": \"%s\"}", refreshToken))
        .when().post(REFRESH_PATH).then().log().ifValidationFails();
    }


    // Tests
    @BeforeAll
    static void initAll(){
        baseURI = System.getProperty("TEST_ENV", "https://dummyjson.com");
        basePath = AUTH_API;
    }

    @BeforeEach
    void init(){        
        login_body = new HashMap<>();
        login_body.put("username", "michaelw");
        login_body.put("password", "michaelwpass");
    }

    @DisplayName("Verify user login")
    @ParameterizedTest(name = "{displayName} with {0}")
    @CsvSource(useHeadersInDisplayName = true, textBlock = """
            CASE,                   USERNAME,   PASSWORD,       STATUS CODE,    SCHEMA
            'valid credentials',    emilys,     emilyspass,     200,            'auth/successful_login_schema.json'
            'invalid user',         emilyss,    emilyspass,     400,            'auth/invalid_login_schema.json'
            'invalid password',     emilys,     emilyspasss,    400,            'auth/invalid_login_schema.json'
            """)
    void userLogIn(String case_, String username, String password, int statusCode, String schema) {
        login_body.put("username", username);
        login_body.put("password", password);
        LOGIN_REQUEST().assertThat().statusCode(statusCode).and().body(matchesJsonSchemaInClasspath(schema));        
    }

    
    @DisplayName("Verify user can be retrieved from access token")
    @Test
    void getAuthenticatedUser(){     
        final String accessToken = LOGIN_REQUEST().assertThat().statusCode(200).and().extract().path("accessToken");        
        ME_REQUEST(accessToken).assertThat().statusCode(200).and().body("username", equalTo(login_body.get("username")));
    }


    @Test
    @DisplayName("Verify user can refresh session with refresh token")
    void refreshUserToken(){

        final String refreshToken = LOGIN_REQUEST().assertThat().statusCode(200).extract().path("refreshToken");

        final ValidatableResponse resfresh_response = REFRESH_REQUEST(refreshToken);

        resfresh_response.assertThat().statusCode(200)
        .and().body("accessToken", notNullValue(), "refreshToken", notNullValue());

        final String new_access_token = resfresh_response.extract().path("accessToken");

        ME_REQUEST(new_access_token).assertThat().statusCode(200).and().body("username", equalTo(login_body.get("username")));
    }

    @Test
    @DisplayName("Verify expired access token is not accepted")
    void expiredAccessToken(){
        int DURATION = 1; 

        login_body.put("expiresInMins", DURATION);

        final String accessToken = LOGIN_REQUEST().extract().path("accessToken");

        try {
            Thread.sleep(DURATION*60000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        
        ME_REQUEST(accessToken).assertThat().statusCode(401).and().body("message", equalTo("Token Expired!"));
    }
}
