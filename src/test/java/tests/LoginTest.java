package tests;

import base.BaseTest;
import client.AuthClient;
import io.restassured.response.Response;
import model.LoginRequest;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    AuthClient authClient = new AuthClient();

    @Test
    public void validLogin(){
        LoginRequest request =
                new LoginRequest("admin@example.com","password123");
        Response response = authClient.login(request);
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test
    public void invalidEmailFormat(){
        LoginRequest request =
                new LoginRequest("admin","password123");
        Response response = authClient.login(request);
        Assert.assertEquals(response.getStatusCode(),400);
    }

    @Test
    public void wrongPassword(){

        LoginRequest request =
                new LoginRequest("admin@example.com","wrong");
        Response response = authClient.login(request);
        Assert.assertEquals(response.getStatusCode(),401);
    }

    @Test
    public void emptyPayload(){
        LoginRequest request =
                new LoginRequest("","");
        Response response = authClient.login(request);
        Assert.assertEquals(response.getStatusCode(),400);
    }
}