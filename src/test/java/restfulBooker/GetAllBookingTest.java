package restfulBooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.lessThan;

public class GetAllBookingTest {

    @Test
    public void testGetAllBookings(){

        RestAssured.baseURI="https://restful-booker.herokuapp.com";

        Response response=
                given()
                        .header("Accept","application/json")
                .when()
                        .get("/booking")
                .then()
                        .assertThat()
                        .statusCode(200)
                        .statusLine("HTTP/1.1 200 OK")
                        .time(lessThan(2000L))
                        .header("Content-Length",lessThan("7000"))
                        .extract()
                        .response();

        System.out.println("Response:"+response.getBody().asString());

    }
}
