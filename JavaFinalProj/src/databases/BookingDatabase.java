package databases;

import java.sql.*;
import java.time.LocalDate;
import java.util.List;

import datamodel.Booking;


public class BookingDatabase {
    public Connection connection;

    //I have a config.properties file which contains the database information like password and user
    //Please change the filepath in DBConfig Class to be able to connect to the Database
    //Only did this to keep things clean

    public BookingDatabase() {
        DBConfig dbConfig = new DBConfig();
        String url = dbConfig.getURL();
        String user = dbConfig.getUser();
        String password = dbConfig.getPassword();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected to Database for BookingDB");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void insertBookings(List<Booking> bookings) {
        String query = "INSERT IGNORE INTO bookings (bookingid, customername, hotelname, checkindate, checkoutdate, paymentstatus) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Booking booking : bookings) {
                preparedStatement.setInt(1, booking.getBookingId());
                preparedStatement.setString(2, booking.getCustomer().getCustomerName());
                preparedStatement.setString(3, booking.getHotel().getName());

                Date checkInDate = Date.valueOf(booking.getCheckInDate());
                preparedStatement.setDate(4, checkInDate);

                Date checkOutDate = Date.valueOf(booking.getCheckOutDate());
                preparedStatement.setDate(5, checkOutDate);

                preparedStatement.setString(6, booking.getPayment().getPaymentStatus());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



    //UPDATE DESTINATION DATE METHOD!!!!!!!!!!!!!!!!
    public void updateBookingDestinationDate(String checkInDate) {
        String updateQuery = "UPDATE bookings SET checkoutdate = ? WHERE checkindate = ?";
        String selectQuery = "SELECT * FROM bookings WHERE checkoutdate = ?";

        try (PreparedStatement updateStatement = connection.prepareStatement(updateQuery);
             PreparedStatement selectStatement = connection.prepareStatement(selectQuery)) {

            LocalDate currentDate = LocalDate.now();

            updateStatement.setDate(1, Date.valueOf(currentDate)); // New checkout date (today's date)
            updateStatement.setDate(2, Date.valueOf(checkInDate)); // Target check-in date to update

            int updatedRows = updateStatement.executeUpdate();
            System.out.println("Updated " + updatedRows + " bookings");

            selectStatement.setDate(1, Date.valueOf(currentDate)); // Updated checkout date (today's date)

            ResultSet resultSet = selectStatement.executeQuery();
            System.out.println("Booking ID - Customer Name - Hotel Name - Check In Date - Check Out Date - Payment Status");
            while (resultSet.next()) {
                System.out.println(resultSet.getInt("bookingid") + " - "
                        + resultSet.getString("customername") + " - "
                        + resultSet.getString("hotelname") + " - "
                        + resultSet.getDate("checkindate") + " - "
                        + resultSet.getDate("checkoutdate") + " - "
                        + resultSet.getString("paymentstatus"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }



}