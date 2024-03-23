package services;

import datamodel.Hotel;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HotelService {

    public List<Hotel> readHotelData() {
        List<Hotel> hotels = new ArrayList<>();
        //My filepath, didn't work when I put only the file name even though the file is in the same folder
        final String fileName = "C:\\Users\\alago\\OneDrive\\Desktop\\School\\L2\\JavaDev\\Projects\\JavaFinalProj\\src\\hotel.csv";

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
                    int hotelId = Integer.parseInt(data[0]);
                    String name = data[1];
                    String location = data[2];
                    double pricePerDay = Double.parseDouble(data[3]);
                    double rating = Double.parseDouble(data[4]);
                    int availableRooms = Integer.parseInt(data[5]);

                    hotels.add(new Hotel(hotelId, name, location, pricePerDay, rating, availableRooms));
                }
            }
        } catch (IOException | NumberFormatException e) {
            e.printStackTrace();
        }
        return hotels;
    }

    //filters the fetched data into a new list
    public List<Hotel> filterHotels(List<Hotel> hotels) {
        List<Hotel> filteredHotels = new ArrayList<>();

        for (Hotel hotel : hotels) {
            double rating = hotel.getRating();
            double pricePerDay = hotel.getPricePerDay();

            // Condition to filter hotels based on rating and price
            if (rating >= 4.5 && pricePerDay <= 100) {
                filteredHotels.add(hotel);
            }
        }

        return filteredHotels;
    }



    //Method used to achieve my desired style of displaying the data in the console
    public void displayHotels(List<Hotel> hotels) {
        if (hotels.isEmpty()) {
            System.out.println("No hotels match the criteria.");
        } else {
            System.out.println("Filtered Hotels:");
            System.out.println("Hotel Name" + " - " + "Location" + " - " + "Rating" + " - " + "Price/Day");
            for (Hotel hotel : hotels) {

                System.out.println(hotel.getName() + " - " + hotel.getLocation()  + " - " +
                        hotel.getRating() + " - " + hotel.getPricePerDay() + "€");
            }
        }
    }

}


