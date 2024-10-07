package restfulBooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.util.List;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static jdk.internal.vm.vector.VectorSupport.extract;
import static org.hamcrest.Matchers.*;

public class GetBookingsByCheckinCheckoutTest {

    @Test
    public void testGetBookingsByCheckinCheckout() {

        RestAssured.baseURI="https://restful-booker.herokuapp.com";

        Response response = given()
                .queryParam("checkin", "2020-12-02")
                .queryParam("checkout", "2024-11-11")
                .when()
                .get("/booking");

        // Validate that the response status code is 200
        response.then().statusCode(200);

        // Validate that the response is an array
        List<?> bookingList = response.jsonPath().getList("$");
        assert bookingList instanceof List : "Response is not an array";

        // Check the data type of each bookingid
        for (Object item : bookingList) {
            Integer bookingId = (Integer) ((Map<?, ?>) item).get("bookingid");
            assert bookingId instanceof Integer : "bookingid is not of type Integer";
        }

    }
}
