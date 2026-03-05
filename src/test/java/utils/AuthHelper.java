package utils;

import client.AuthClient;
import io.restassured.response.Response;
import model.LoginRequest;

public class AuthHelper {

    public static String getToken(){

        AuthClient authClient = new AuthClient();

        LoginRequest request =
                new LoginRequest("admin@example.com","password123");

        Response response = authClient.login(request);

        return response.jsonPath().getString("token");
    }
}
