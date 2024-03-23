package swingdatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import databases.DBConfig;

public class BookingGUI extends JFrame {

    DBConfig dbConfig = new DBConfig();
    private JLabel customerNameLabel, hotelNameLabel, checkInDateLabel, checkOutDateLabel, paymentStatusLabel;
    private JTextField customerNameField;
    private JComboBox<String> hotelNameComboBox;
    private JFormattedTextField checkInDateField, checkOutDateField;
    private JLabel paymentStatusDefault, paymentInstructionLabel;
    private JButton submitButton;

    public BookingGUI() {
        setTitle("Booking Form");
        setSize(600, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        customerNameLabel = new JLabel("Customer Name:");
        customerNameLabel.setBounds(50, 50, 150, 30);
        add(customerNameLabel);

        customerNameField = new JTextField();
        customerNameField.setBounds(160, 50, 250, 30);
        add(customerNameField);

        hotelNameLabel = new JLabel("Select Hotel:");
        hotelNameLabel.setBounds(50, 100, 150, 30);
        add(hotelNameLabel);

        hotelNameComboBox = new JComboBox<>();
        hotelNameComboBox.setBounds(160, 100, 250, 30);
        fetchHotelNames();
        add(hotelNameComboBox);

        checkInDateLabel = new JLabel("Check-in Date (YYYY-MM-DD):");
        checkInDateLabel.setBounds(50, 150, 200, 30);
        add(checkInDateLabel);

        checkInDateField = new JFormattedTextField();
        checkInDateField.setBounds(250, 150, 160, 30);
        add(checkInDateField);

        checkOutDateLabel = new JLabel("Check-out Date (YYYY-MM-DD):");
        checkOutDateLabel.setBounds(50, 200, 200, 30);
        add(checkOutDateLabel);

        checkOutDateField = new JFormattedTextField();
        checkOutDateField.setBounds(250, 200, 160, 30);
        add(checkOutDateField);

        paymentStatusLabel = new JLabel("Payment Status:");
        paymentStatusLabel.setBounds(50, 250, 150, 30);
        add(paymentStatusLabel);

        paymentStatusDefault = new JLabel("not paid");//Sets payment status to not paid by default
        paymentStatusDefault.setBounds(160, 250, 80, 30);
        add(paymentStatusDefault);

        paymentInstructionLabel = new JLabel("After Booking, go to Payment Window to Update your Payment Status");
        paymentInstructionLabel.setBounds(50, 300, 500, 30);
        add(paymentInstructionLabel);

        submitButton = new JButton("Submit");
        submitButton.setBounds(150, 450, 100, 30);
        add(submitButton);

        submitButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String customerName = customerNameField.getText();
                String hotelName = (String) hotelNameComboBox.getSelectedItem();
                String checkInDate = checkInDateField.getText();
                String checkOutDate = checkOutDateField.getText();
                String paymentStatus = paymentStatusDefault.getText();

                addBooking(customerName, hotelName, checkInDate, checkOutDate, paymentStatus);
                JOptionPane.showMessageDialog(submitButton, "Booking added successfully!");
                dispose(); // Close the current window
                new BookingGUI(); //Refreshes the BookingGUI page
            }
        });

        setVisible(true);
    }


    //Fetches available hotels for selection in the booking window.
    private void fetchHotelNames() {
        try {
            String url = dbConfig.getURL();
            String user = dbConfig.getUser();
            String password = dbConfig.getPassword();

            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            String query = "SELECT name, location FROM hotels where availablerooms > 0";
            ResultSet resultSet = statement.executeQuery(query);

            List<String> hotelNames = new ArrayList<>();
            while (resultSet.next()) {
                String hotelName = resultSet.getString("name") + " " + "(" + (resultSet.getString("location")) + ")";
                hotelNames.add(hotelName);
            }

            hotelNameComboBox.setModel(new DefaultComboBoxModel<>(hotelNames.toArray(new String[0])));

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //Adds the booking to the database
    private void addBooking(String customerName, String hotelName, String checkInDate, String checkOutDate, String paymentStatus) {
        try {
            String url = dbConfig.getURL();
            String user = dbConfig.getUser();
            String password = dbConfig.getPassword();

            Connection connection = DriverManager.getConnection(url, user, password);

            // Prepare the SQL query
            String insertQuery = "INSERT INTO bookings (customername, hotelname, checkindate, checkoutdate, paymentstatus) VALUES (?, ?, ?, ?, ?)";
            PreparedStatement preparedStatement = connection.prepareStatement(insertQuery);

            preparedStatement.setString(1, customerName);
            preparedStatement.setString(2, hotelName);
            preparedStatement.setString(3, checkInDate);
            preparedStatement.setString(4, checkOutDate);
            preparedStatement.setString(5, paymentStatus);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new BookingGUI());
    }
}