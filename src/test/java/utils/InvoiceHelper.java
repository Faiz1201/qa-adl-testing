package utils;

import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class InvoiceHelper {

    public static String getFirstInvoiceId(String token){

        Response response =
                given()
                        .header("Authorization","Bearer " + token)
                        .when()
                        .get("/invoices");

        return response
                .jsonPath()
                .getString("[0].id");
    }
}