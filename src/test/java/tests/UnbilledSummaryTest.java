package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AuthHelper;

import static io.restassured.RestAssured.given;

public class UnbilledSummaryTest extends BaseTest {

    @Test
    public void validToken(){
        String token = AuthHelper.getToken();
        Response response =
                given()
                        .header("Authorization","Bearer "+token)
                        .when()
                        .get("/invoices/unbilled/summary");
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test
    public void noToken(){
        Response response =
                given()
                        .when()
                        .get("/invoices/unbilled/summary");
        Assert.assertEquals(response.getStatusCode(),401);
    }

    @Test
    public void invalidToken(){
        Response response =
                given()
                        .header("Authorization","Bearer invalid123")
                        .when()
                        .get("/invoices/unbilled/summary");
        Assert.assertEquals(response.getStatusCode(),401);
    }
}