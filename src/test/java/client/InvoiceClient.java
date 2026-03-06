package client;

import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class InvoiceClient {

    public Response getAllInvoices(String token){

        return given()
                .filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/invoices");

    }

    public Response getInvoiceDetail(String token, String id){

        return given()
                .filter(new AllureRestAssured())
                .header("Authorization", "Bearer " + token)
                .when()
                .get("/invoices/" + id);

    }
}
