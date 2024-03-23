package datamodel;

public class Hotel {
    private int hotelId; // Added hotelId field
    private String name;
    private String location;
    private double pricePerDay;
    private double rating;
    private int availableRooms;

    public Hotel(int hotelId, String name, String location, double pricePerDay, double rating, int availableRooms) {
        this.hotelId = hotelId;
        this.name = name;
        this.location = location;
        this.pricePerDay = pricePerDay;
        this.rating = rating;
        this.availableRooms = availableRooms;
    }

    public Hotel() {

    }
    // Getters
    public int getHotelId() {
        return hotelId;
    }

    public String getName() {
        return name;
    }

    public String getLocation() {
        return location;
    }

    public double getPricePerDay() {
        return pricePerDay;
    }

    public double getRating() {
        return rating;
    }

    public int getAvailableRooms() {
        return availableRooms;
    }

    // Setters
    public void setHotelId(int hotelId) {
        this.hotelId = hotelId;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPricePerDay(double pricePerDay) {
        this.pricePerDay = pricePerDay;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public void setAvailableRooms(int availableRooms) {
        this.availableRooms = availableRooms;
    }

    // toString() method
    @Override
    public String toString() {
        return "Hotel{" +
                "hotelId=" + hotelId +
                ", name='" + name + '\'' +
                ", location='" + location + '\'' +
                ", pricePerDay=" + pricePerDay +
                ", rating=" + rating +
                ", availableRooms=" + availableRooms +
                '}';
    }
}
