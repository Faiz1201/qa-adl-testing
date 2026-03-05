package tests;

import base.BaseTest;
import client.InvoiceClient;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AuthHelper;
import utils.InvoiceHelper;

import static io.restassured.RestAssured.given;

public class InvoiceDetailTest extends BaseTest {

    @Test
    public void validInvoiceDetail(){

        String token = AuthHelper.getToken();

        String invoiceId =
                InvoiceHelper.getFirstInvoiceId(token);

        InvoiceClient client = new InvoiceClient();

        Response response =
                client.getInvoiceDetail(token, invoiceId);

        Assert.assertEquals(response.getStatusCode(),200);
    }

    @Test
    public void idNotFound(){

        String token = AuthHelper.getToken();

        Response response =
                given()
                        .header("Authorization","Bearer "+token)
                        .when()
                        .get("/invoices/inv_999");

        Assert.assertEquals(response.getStatusCode(),404);
    }

    @Test
    public void invalidIdFormat(){

        String token = AuthHelper.getToken();

        Response response =
                given()
                        .header("Authorization","Bearer "+token)
                        .when()
                        .get("/invoices/abc");

        Assert.assertTrue(
                response.getStatusCode()==400 ||
                        response.getStatusCode()==404
        );
    }
}