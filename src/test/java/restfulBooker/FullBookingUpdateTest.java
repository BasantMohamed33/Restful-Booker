package restfulBooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.io.File;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;

public class FullBookingUpdateTest {

    @Test
    public void updateBookingTest() {
        // Set base URI
        RestAssured.baseURI = "https://restful-booker.herokuapp.com"; // Replace with your API endpoint

        // Define the booking ID to update (ensure this ID exists in your testing environment)
        int bookingId = 1; // Replace with an actual booking ID

        Header Auth =new Header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=");
        File requestBody =new File("src/test/resources/fullUpdateData.json");


        // Send PUT request
        Response response = given()
                .contentType(ContentType.JSON)
                .header(Auth)
                .body(requestBody)
                .when()
                .put("/booking/"+bookingId); // Update with your endpoint

        // Validate status code
        response.then().statusCode(200);

        // Validate header value
        String contentType = response.header("Content-Type");
        assert contentType != null && contentType.contains("application/json");

        // Validate presence of Content-Length header
        response.then().header("Content-Length", notNullValue());

        // Validate response body
        response.then()
                .body("firstname", equalTo("James"))
                .body("lastname", equalTo("Brown"))
                .body("totalprice", equalTo(111))
                .body("depositpaid", equalTo(false))
                .body("bookingdates.checkin", equalTo("2018-01-01"))
                .body("bookingdates.checkout", equalTo("2019-01-01"))
                .body("additionalneeds", equalTo("Breakfast"));
    }
}
