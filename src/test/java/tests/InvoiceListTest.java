package tests;

import base.BaseTest;
import client.InvoiceClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AuthHelper;

import static io.restassured.RestAssured.given;

public class InvoiceListTest extends BaseTest {

    @Test
    public void getListValidToken(){
        String token = AuthHelper.getToken();
        InvoiceClient client = new InvoiceClient();
        Response response = client.getAllInvoices(token);
        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test
    public void getListNoToken(){
        Response response =
                given()
                        .when()
                        .get("/invoices");
        Assert.assertEquals(response.getStatusCode(),401);
    }

    @Test
    public void getListInvalidToken(){
        Response response =
                given()
                        .header("Authorization","Bearer invalid123")
                        .when()
                        .get("/invoices");

        Assert.assertEquals(response.getStatusCode(),401);
    }
}