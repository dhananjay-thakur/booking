package com.auto.booking.tests;

import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import com.auto.booking.constants.ResponseCode;
import com.auto.booking.services.BookingService;

/**
 * This test class is designed to validate the API responses of different requests i.e create/update/delete.
 * Also, we have validated all the different rest assured methods used to handle the API i.e GET/POST/PUT/PATCH/DELETE.
 * @author dannymac
 *
 */
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class HotelBookingTest {
	private BookingService bookingService;
	
	public  HotelBookingTest() {
		bookingService = new BookingService();
	}
	
	@Test
	public void test_01_bookingIds() {
		bookingService.getAllBookingIds(ResponseCode.HTTP_SUCCESS);
	}
	
	@Test
	public void test_02_createBooking() {
		bookingService.createBooking(ResponseCode.HTTP_SUCCESS);
	}
	
	@Test
	public void test_03_createdBookingWithParam() {
		bookingService.getBookingIdWithParam(ResponseCode.HTTP_SUCCESS);
	}
	
	@Test
	public void test_04_updateBooking() {
		bookingService.updateBooking(ResponseCode.HTTP_SUCCESS);
	}
	
	@Test
	public void test_05_PartialUpdateBooking() {
		bookingService.partialUpdateBooking(ResponseCode.HTTP_SUCCESS);
	}

	@Test
	public void test_06_GetBooking() {
		bookingService.validateBooking(ResponseCode.HTTP_SUCCESS);
	}
	
	@Test
	public void test_07_DeleteBooking() {
		bookingService.deleteBooking(ResponseCode.HTTP_CREATED);
	}
	
	@Test
	public void test_08_bookingIdNotAvailable() {
		bookingService.validateBooking(ResponseCode.HTTP_NOT_FOUND);
	}
}
