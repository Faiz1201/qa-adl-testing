package client;

import io.restassured.response.Response;
import model.LoginRequest;

import static io.restassured.RestAssured.given;

public class AuthClient {

    public Response login(LoginRequest body){
        return given()
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post("/users/login");
    }
}
