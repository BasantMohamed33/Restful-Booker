package restfulBooker;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.http.Header;
import io.restassured.response.Response;
import org.testng.annotations.Test;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Random;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CreateBookingTest {


    public static final String[] firstNames = {"John", "Jane", "Alice", "Bob", "Michael", "Sara", "David", "Emily", "Daniel", "Sophia"};
    public static final String[] lastNames = {"Smith", "Johnson", "Brown", "Davis", "Wilson", "Taylor", "Clark", "Hall", "Young", "Walker"};
    public static final String[] needs = {"Pancakes", "Oatmeal", "Eggs", null, "Toast", "Bacon", "Fruit Salad"};
    public static final Random random = new Random();

    @Test
    public void createBookingTest() {


        // Set base URI
        RestAssured.baseURI = "https://restful-booker.herokuapp.com"; // Replace with your API endpoint

        Header Auth =new Header("Authorization","Basic YWRtaW46cGFzc3dvcmQxMjM=");


        // Generate random booking data
        String firstName = firstNames[random.nextInt(firstNames.length)];
        String lastName = lastNames[random.nextInt(lastNames.length)];
        int totalPrice = random.nextInt(201) + 50; // Random price between 50 and 250
        boolean depositPaid = random.nextBoolean();

        // Set check-in and check-out dates
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, random.nextInt(180) + 1); // Random check-in date within 180 days
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String checkIn = dateFormat.format(calendar.getTime());

        calendar.add(Calendar.DAY_OF_YEAR, random.nextInt(14) + 1); // Random check-out date within 14 days of check-in
        String checkOut = dateFormat.format(calendar.getTime());

        String additionalNeeds = needs[random.nextInt(needs.length)];

        // Create request body using the generated variables
        String requestBody = String.format("{\"firstname\":\"%s\",\"lastname\":\"%s\",\"totalprice\":%d,\"depositpaid\":%b,\"bookingdates\":{\"checkin\":\"%s\",\"checkout\":\"%s\"},\"additionalneeds\":\"%s\"}",
                firstName, lastName, totalPrice, depositPaid, checkIn, checkOut, additionalNeeds);

        // Send POST request and validate response
        Response response = given()
                .contentType(ContentType.JSON)
                .header(Auth)
                .body(requestBody)
                .when()
                .post("/booking"); // Update with your endpoint

        // Validate status code
        response.then().statusCode(200);

        // Validate response time
        response.then().time(lessThan(3000L));
        response.then().log().all();
        // Validate response body data types and values
        response.then()
                .body("bookingid", notNullValue())
                .body("booking.firstname", is(firstName)) // Validate that the firstname in response matches the one sent
                .body("booking.lastname", is(lastName)) // Validate that the lastname in response matches the one sent
                .body("booking.totalprice", is(totalPrice)) // Validate that the totalprice in response matches the one sent
                .body("booking.depositpaid", is(depositPaid)) // Validate that the depositpaid in response matches the one sent
                .body("booking.bookingdates.checkin", is(checkIn)) // Validate that the checkin date in response matches the one sent
                .body("booking.bookingdates.checkout", is(checkOut)) // Validate that the checkout date in response matches the one sent
                .body("additionalneeds", is(additionalNeeds)); // Validate that the additionalneeds in response matches the one sent
    }
}