package databases;

import java.sql.*;
import java.util.List;

import datamodel.Hotel;

public class HotelDatabase {
    public Connection connection;

    //I have a config.properties file which contains the database information like password and user
    //Please change the filepath in DBConfig Class to be able to connect to the Database
    //Only did this to keep things clean

    public HotelDatabase() {
        DBConfig dbConfig = new DBConfig();
        String url = dbConfig.getURL();
        String user = dbConfig.getUser();
        String password = dbConfig.getPassword();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(url, user, password);
            System.out.println("Successfully connected to Database for HotelDB");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //This uses the hotel list that is fetched from the hotel.csv file in the HotelService class, you'll see in the Launcher
    public void insertHotels(List<Hotel> hotels) {
        String query = "INSERT IGNORE INTO hotels (hotelid, name, location, priceperday, rating, availablerooms) VALUES (?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            for (Hotel hotel : hotels) {
                preparedStatement.setInt(1, hotel.getHotelId());
                preparedStatement.setString(2, hotel.getName());
                preparedStatement.setString(3, hotel.getLocation());
                preparedStatement.setDouble(4, hotel.getPricePerDay());
                preparedStatement.setDouble(5, hotel.getRating());
                preparedStatement.setInt(6, hotel.getAvailableRooms());
                preparedStatement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


//To display the hotels that cost more than 500 dollars per night
    public void displayExpensiveHotels() {
        String query = "SELECT name, priceperday, location, rating FROM hotels WHERE priceperday > 500";

        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet resultSet = preparedStatement.executeQuery();
            System.out.println("Hotel Name" + " - " + "Price" + " - " + "Location" + " - " + "Rating");
            while (resultSet.next()) {
                String name = resultSet.getString("name");
                double price = resultSet.getDouble("priceperday");
                String location = resultSet.getString("location");
                double rating = resultSet.getDouble("rating");

                System.out.println(name + " - " + price + "euros" + " - " + location + " - " + rating);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
