package com.auto.booking.services;


import java.io.File;
import java.util.List;
import com.auto.booking.constants.Constants;
import com.auto.booking.constants.Endpoints;
import com.auto.booking.utils.ConfigManager;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ResponseBody;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertTrue;


/**
 * This class is designed to keep all the supporting methods which are used to implement the functionalities. 
 * @author dannymac
 *
 */
public class BookingService {
	private Response response;
	private RequestSpecification requestSpec;
	private static final String baseURL = ConfigManager.getInstance().baseURL();
	private static int bookingId;
	private static long existingBookingId;

	public BookingService() {
		requestSpec = RestAssured.given()
				.baseUri(baseURL)
				.basePath(Endpoints.BOOKING)
				.contentType(ContentType.JSON)
				.log()
				.all();					
	}

	/**
	 * This method is used to perform GET operation to get all booking IDs
	 * @param expectedStatusCode
	 */
	public void getAllbooking(int expectedStatusCode) {
		response = given()
				.spec(requestSpec)
				.when()
				.get()
				.andReturn();
		response.then().log().all().assertThat().statusCode(expectedStatusCode);
	}

	/**
	 * This method is used to perform the create operation on the API request
	 * @param expectedStatusCode
	 * @return
	 */
	public void createBooking(int expectedStatusCode) {
		File file = new File(System.getProperty("user.dir") + "/resources/testdata/createbooking.json");
		response = given()
				.spec(requestSpec)
				.body(file)
				.when()
				.post()
				.andReturn();
		response.then().log().all().assertThat().statusCode(expectedStatusCode);
		JsonPath jsonPath = response.jsonPath();
		bookingId = jsonPath.get("bookingid");
		System.out.println("Created BookingId : " +bookingId);
	}
	
	/**
	 * This method is used to get specific record based on parameter
	 * @param expectedStatusCode
	 */
	public void getBookingIdWithParam(int expectedStatusCode) {
		response = given()
				.spec(requestSpec)
				.param("firstname", "Test")
				.param("lastname", "Example")
				.when()
				.get()
				.andReturn();
		response.then().log().all().assertThat().statusCode(expectedStatusCode);
		ResponseBody responseBody = response.getBody();
		String res = responseBody.asString();
		System.out.println("Response Body : " +res);
		List<Integer> list = response.jsonPath().getList("bookingid");
		assertTrue("Created booking Id is not present in the API ", list.contains(bookingId));

	}

	/**
	 * This method is used to perform the update operation on the API request
	 * @param expectedStatusCode
	 * @return
	 */
	public void updateBooking(int expectedStatusCode) {
		File file = new File(System.getProperty("user.dir") + "/resources/testdata/updatebooking.json");
		System.out.println("updated bookingId : " +bookingId);
		response = given()
				.header(Constants.HEADER_CONTENT_TYPE, Constants.HEADER_CONTENT_TYPE_VALUE)
				.header(Constants.HEADER_ACCEPT, Constants.HEADER_ACCEPT_VALUE)
				.header(Constants.HEADER_AUTHORIZATION, Constants.HEADER_AUTHORIZATION_VALUE)
				.baseUri(baseURL)
				.body(file)
				.when()
				.put("/booking/"+bookingId)
				.andReturn();
		response.then().log().all().assertThat().statusCode(expectedStatusCode);
	}

	/**
	 * This method is used to perform the partial update operation on the API request
	 * @param expectedStatusCode
	 * @return
	 */
	public void partialUpdateBooking(int expectedStatusCode) {
		File file = new File(System.getProperty("user.dir") + "/resources/testdata/partialupdatebooking.json");
		System.out.println("Partial updated bookingId : " +bookingId);
		response = given()
				.header(Constants.HEADER_CONTENT_TYPE, Constants.HEADER_CONTENT_TYPE_VALUE)
				.header(Constants.HEADER_ACCEPT, Constants.HEADER_ACCEPT_VALUE)
				.header(Constants.HEADER_AUTHORIZATION, Constants.HEADER_AUTHORIZATION_VALUE)
				.baseUri(baseURL)
				.body(file)
				.when()
				.patch("/booking/"+bookingId)
				.andReturn();
		response.then().log().all().assertThat().statusCode(expectedStatusCode);		
	}

	/**
	 * This method is used get the updated booking details of the API request
	 * @param expectedStatusCode
	 * @return
	 */
	public void validateBooking(int expectedStatusCode) {
		System.out.println("Get bookingId : " +bookingId);
		response = given()
				.baseUri(baseURL)
				.when()
				.get("/booking/"+bookingId)
				.andReturn();
		response.then().log().all().assertThat().statusCode(expectedStatusCode);
	}

	/**
	 * This method is used to perform the delete operation on the API request
	 * @param expectedStatusCode
	 * @return
	 */
	public void deleteBooking(int expectedStatusCode) {
		System.out.println("Delete created bookingId : " +bookingId);
		response = given()
				.header(Constants.HEADER_CONTENT_TYPE, Constants.HEADER_CONTENT_TYPE_VALUE)
				.header(Constants.HEADER_ACCEPT, Constants.HEADER_ACCEPT_VALUE)
				.header(Constants.HEADER_AUTHORIZATION, Constants.HEADER_AUTHORIZATION_VALUE)
				.baseUri(baseURL)
				.when()
				.delete("/booking/"+bookingId)
				.andReturn();
		response.then().log().all().assertThat().statusCode(expectedStatusCode);
	}
	
	/**
	 * This method is used to get all existing booking IDs present in the API.
	 * @param expectedStatusCode
	 */
	public void getAllBookingIds(int expectedStatusCode) {
		response = given()
				.spec(requestSpec)
				.when()
				.get()
				.andReturn();
		response.then().assertThat().statusCode(expectedStatusCode);
		List<Integer> list = response.jsonPath().getList("bookingid");
		System.out.println("Booking Id Size: " + list.size());
		if(!list.isEmpty()) {
			for(int i=0; i<list.size(); i++) {
				System.out.println("Existing BookingIds : " +list.get(i));
			}
			existingBookingId = list.get(0);
			System.out.println("First Existing Booking Id: " + existingBookingId);
		}else {
			System.out.println("No booking Id available");
		}	
	}	
}
