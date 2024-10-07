package restfulBooker;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.testng.annotations.Test;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.notNullValue;
public class AuthenticationTest {
    @Test
    public void testAuthentication() {

        RestAssured.baseURI = "https://restful-booker.herokuapp.com";


        Response response =
                given()
                        .header("Content-Type" ,"application/json")
                        .body("{ \"username\": \"admin\", \"password\": \"password123\" }")
                .when()
                        .post("/auth")
                .then()
                        .assertThat()
                        .statusCode(200)
                        .body("token" ,notNullValue())
                        .extract()
                        .response();

        System.out.println("Response Body: " + response.getBody().asString());
    }

}
