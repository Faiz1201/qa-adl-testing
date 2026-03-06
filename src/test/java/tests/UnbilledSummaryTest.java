package tests;

import base.BaseTest;
import io.qameta.allure.restassured.AllureRestAssured;
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
                        .filter(new AllureRestAssured())
                        .header("Authorization","Bearer "+token)
                        .when()
                        .get("/invoices/unbilled/summary");
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test
    public void noToken(){
        Response response =
                given()
                        .filter(new AllureRestAssured())
                        .when()
                        .get("/invoices/unbilled/summary");
        Assert.assertEquals(response.getStatusCode(),401);
    }

    @Test
    public void invalidToken(){
        Response response =
                given()
                        .filter(new AllureRestAssured())
                        .header("Authorization","Bearer invalid123")
                        .when()
                        .get("/invoices/unbilled/summary");
        Assert.assertEquals(response.getStatusCode(),401);
    }
}