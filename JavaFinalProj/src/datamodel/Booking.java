package datamodel;

import java.time.LocalDate;

public class Booking {
    private int bookingId;
    private Customer customer; // Reference to Customer object

    private Hotel hotel;
    private Payment payment; // Reference to Payment object
    private LocalDate checkInDate;
    private LocalDate checkOutDate;

    public Booking(int bookingId, Customer customer, Payment payment, Hotel hotel, LocalDate checkInDate, LocalDate checkOutDate) {
        this.bookingId = bookingId;
        this.customer = customer;
        this.hotel = hotel;
        this.payment = payment;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
    }


    public Booking() {
        // Constructor
    }
    // Getters and setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public Customer getCustomer() { return customer; }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public Hotel getHotel() { return hotel; }

    public void setHotel(Hotel hotel) { this.hotel = hotel; }

    public Payment getPayment() {
        return payment;
    }

    public void setPayment(Payment payment) {
        this.payment = payment;
    }


    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    // toString method
    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", customer=" + customer.getCustomerName() + // Access customer details
                ", hotelName=" + hotel.getName() +
                ", checkInDateTime=" + checkInDate +
                ", checkOutDateTime=" + checkOutDate +
                ", payment=" + payment.getPaymentStatus() + // Access payment details
                '}';
    }
}