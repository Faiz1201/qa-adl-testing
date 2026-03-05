package tests;

import base.BaseTest;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import utils.AuthHelper;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;

public class UnbilledBusinessLogicTest extends BaseTest {

    @Test
    public void validateUnbilledSummaryCalculation(){
        String token = AuthHelper.getToken();

        Response invoiceResponse =
                given()
                        .header("Authorization","Bearer " + token)
                        .when()
                        .get("/invoices");

        Assert.assertEquals(invoiceResponse.getStatusCode(),200);

        List<Map<String,Object>> invoices =
                invoiceResponse.jsonPath().getList("$");

        double calculatedTotalUnbilled = 0;

        for(Map<String,Object> invoice : invoices){

            String status = (String) invoice.get("status");

            if("PENDING".equals(status)){

                Object amountObj = invoice.get("totalAmount");

                if(amountObj == null){
                    continue;
                }

                double amount =
                        Double.parseDouble(amountObj.toString());

                calculatedTotalUnbilled += amount;
            }
        }

        System.out.println("Calculated Total Unbilled: " + calculatedTotalUnbilled);

        Response summaryResponse =
                given()
                        .header("Authorization","Bearer " + token)
                        .when()
                        .get("/invoices/unbilled/summary");

        Assert.assertEquals(summaryResponse.getStatusCode(),200);

        double apiTotalUnbilled =
                summaryResponse.jsonPath().getDouble("totalUnbilled");

        double apiAfterTax =
                summaryResponse.jsonPath().getDouble("unbilledAfterTax");

        if(apiTotalUnbilled != calculatedTotalUnbilled){

            System.out.println("TOTAL UNBILLED MISMATCH");
            System.out.println("Expected: " + calculatedTotalUnbilled);
            System.out.println("Actual: " + apiTotalUnbilled);

            Assert.fail("Mismatch totalUnbilled calculation");
        }

        double expectedAfterTax =
                calculatedTotalUnbilled * 1.1;

        if(Math.abs(apiAfterTax - expectedAfterTax) > 0.01){

            System.out.println("TAX CALCULATION MISMATCH");
            System.out.println("Expected After Tax: " + expectedAfterTax);
            System.out.println("Actual After Tax: " + apiAfterTax);

            Assert.fail("Mismatch unbilledAfterTax calculation");
        }

        System.out.println("Business logic validation passed");
    }
}