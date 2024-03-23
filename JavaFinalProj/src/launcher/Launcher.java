package launcher;

import databases.BookingDatabase;
import databases.HotelDatabase;
import datamodel.Booking;
import datamodel.Hotel;
import services.BookingService;
import services.HotelService;

import java.util.List;


public class Launcher {
    public static void main(String[] args) {

        //HOTELSERVICE
        HotelService hotelService = new HotelService();
        List<Hotel> allHotels = hotelService.readHotelData(); //reading the hotel.csv file and filtering it in next line
        List<Hotel> filteredHotels = hotelService.filterHotels(allHotels);
         hotelService.displayHotels(filteredHotels); //Displays the list of filtered hotels

        System.out.println("\n===================================================\n");

        //BOOKINGSERVICE
        BookingService bookingService = new BookingService();
        List<Booking> allBookings = bookingService.readBookingData();//reading the booking.csv file
        List<Booking> filteredBookings = bookingService.filterPaidBookings(allBookings);
        bookingService.displayBookings(filteredBookings); //Displays the list of filtered bookings of paid customers


        System.out.println("\n===================================================\n");

        //HOTELDATABASE - INSERT
      HotelDatabase hotelDatabase = new HotelDatabase();
        hotelDatabase.insertHotels(allHotels);//Uses the allHotels method from the HotelService class since it already fetches the data from the db
        //HOTELDATABASE - SELECT hotels that cost > 500
        hotelDatabase.displayExpensiveHotels();

        System.out.println("\n===================================================\n");

        //BOOKINGDATABASE
        BookingDatabase bookingDatabase = new BookingDatabase();
        bookingDatabase.insertBookings(allBookings);//same as HotelDatase, uses the list from the BookingService class

        //Udates the checkout date to today's date for any customer with the checkin date entered below
        //Will be useful update booking record for customers that leave earlier than the checkout date
        bookingDatabase.updateBookingDestinationDate("2023-12-05");//Kaspers Schmeichel


    }
}



