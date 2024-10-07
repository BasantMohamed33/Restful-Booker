package restfulBooker;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;

public class GetBookingByNameTest {

    @Test
    public void testGetBookingByName(){

        RestAssured.baseURI="https://restful-booker.herokuapp.com";

        Response response =
                given()
                        .header("Accept","application/json")
                        .when()
                        .get("/booking?firstname=John&lastname=Smith")
                        .then()
                        .assertThat()
                        .statusCode(200)
                        .extract()
                        .response();
        System.out.println("Response:"+response.getBody().asString());
    }
}

