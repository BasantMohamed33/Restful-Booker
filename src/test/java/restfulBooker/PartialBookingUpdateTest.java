package restfulBooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;


public class PartialBookingUpdateTest {

    @Test
    public void partialUpdateBooking(){

        RestAssured.baseURI = "https://restful-booker.herokuapp.com"; // Replace with your API endpoint

        File requestBody = new File("src/test/resources/partialUpdateData.json");

        Header Auth =new Header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=");


        // Send PATCH request and validate response
        Response response = given()
                .contentType(ContentType.JSON)
                .header(Auth)
                .body(requestBody)
                .when()
                .patch("/booking/1"); // Replace with your endpoint and booking ID

        // Validate status code
       response.then().statusCode(200);

        // Validate response body
        String responseBody = response.getBody().asString();
        System.out.println(responseBody);
    }
}

