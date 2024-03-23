package services;

import datamodel.Booking;
import datamodel.Customer;
import datamodel.Hotel;
import datamodel.Payment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Comparator;


public class BookingService {

    public List<Booking> readBookingData() {
        List<Booking> bookings = new ArrayList<>();
        //My filepath, didn't work when I put only the file name even though the file is in the same folder
        final String fileName = "C:\\Users\\alago\\OneDrive\\Desktop\\School\\L2\\JavaDev\\Projects\\JavaFinalProj\\src\\booking.csv";

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean headerSkipped = false;

            while ((line = br.readLine()) != null) {
                if (!headerSkipped) {
                    headerSkipped = true;
                    continue; // Skip header row
                }

                String[] data = line.split(",");
                if (data.length == 6) {
                    int bookingId = Integer.parseInt(data[0]);
                    String customerName = data[1];
                    String hotelName = data[2];
                    LocalDate checkInDateTime = LocalDate.parse(data[3]);
                    LocalDate checkOutDateTime = LocalDate.parse(data[4]);
                    String paymentStatus = data[5];

                    //Since my booking.csv contains attributes from various classes,
                    //And these attributes are not in the booking class
                    //I have to call the classes with their setters to use them
                    Customer customer = new Customer();
                    customer.setCustomerName(customerName);

                    Hotel hotel = new Hotel();
                    hotel.setName(hotelName);

                    Payment payment = new Payment();
                    payment.setPaymentStatus(paymentStatus);

                    Booking booking = new Booking();
                    booking.setBookingId(bookingId);
                    booking.setCustomer(customer);
                    booking.setHotel(hotel);
                    booking.setCheckInDate(checkInDateTime);
                    booking.setCheckOutDate(checkOutDateTime);
                    booking.setPayment(payment);

                    bookings.add(booking);
                }
            }
        } catch (IOException | NumberFormatException | DateTimeParseException e) {
            e.printStackTrace();
            return null;
        }
        return bookings;
    }

    //Filters the data from all bookings that meet the condition "paid"
    public List<Booking> filterPaidBookings(List<Booking> bookings) {
        List<Booking> paidBookings = new ArrayList<>();
        for (Booking booking : bookings) {
            if ("paid".equalsIgnoreCase(booking.getPayment().getPaymentStatus())) {
                paidBookings.add(booking);
            }
        }
        // Sorting paid bookings by customer name
        paidBookings.sort(Comparator.comparing(b -> b.getCustomer().getCustomerName()));

        return paidBookings;
    }

    //Method for my display style in the console
    public void displayBookings(List<Booking> bookings) {
        if (bookings.isEmpty()) {
            System.out.println("No bookings found.");
        } else {
            System.out.println("All Paid Bookings:");
            System.out.println("Booking ID - Customer Name - Hotel Name - Payment Status");
            for (Booking booking : bookings) {
                System.out.println(booking.getBookingId() + " - " +
                        booking.getCustomer().getCustomerName() + " - " +
                          booking.getHotel().getName()+ " - " + booking.getPayment().getPaymentStatus());
            }
        }
    }
}
