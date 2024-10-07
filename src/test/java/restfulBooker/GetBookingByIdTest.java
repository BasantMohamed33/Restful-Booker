package restfulBooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class GetBookingByIdTest {

    @Test
    public void testGetBookingById(){

        RestAssured.baseURI="https://restful-booker.herokuapp.com";

        Response response =
                given()
                        .header("Accept","application/json")
                .when()
                        .get("/booking/?id=791")
                .then()
                        .assertThat()
                        .statusCode(200)
                        .time(lessThan(2000L))
                        .extract()
                        .response();
        System.out.println("Response:"+response.getBody().asString());
    }
}
