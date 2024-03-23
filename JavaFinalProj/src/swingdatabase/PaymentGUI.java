package swingdatabase;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import databases.DBConfig;

public class PaymentGUI extends JFrame {

    DBConfig dbConfig = new DBConfig();
    private JComboBox<String> customerNameComboBox;
    private JTextField emailField, cardNumberField, expirationDateField, cvvField;
    private JButton makePaymentButton;

    public PaymentGUI() {
        setTitle("Payment Form");
        setSize(600, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel customerNameLabel = new JLabel("Select Customer Name:");
        customerNameLabel.setBounds(50, 50, 200, 25);
        add(customerNameLabel);

        customerNameComboBox = new JComboBox<>();
        customerNameComboBox.setBounds(250, 50, 200, 25);
        fetchNotPaidCustomers();
        add(customerNameComboBox);

        JLabel emailLabel = new JLabel("Email:");
        emailLabel.setBounds(50, 100, 200, 25);
        add(emailLabel);

        emailField = new JTextField();
        emailField.setBounds(250, 100, 200, 25);
        add(emailField);

        JLabel cardNumberLabel = new JLabel("Card Number:");
        cardNumberLabel.setBounds(50, 150, 200, 25);
        add(cardNumberLabel);

        cardNumberField = new JTextField();
        cardNumberField.setBounds(250, 150, 200, 25);
        add(cardNumberField);

        JLabel expirationDateLabel = new JLabel("Expiration Date:");
        expirationDateLabel.setBounds(50, 200, 200, 25);
        add(expirationDateLabel);

        expirationDateField = new JTextField();
        expirationDateField.setBounds(250, 200, 200, 25);
        add(expirationDateField);

        JLabel cvvLabel = new JLabel("CVV:");
        cvvLabel.setBounds(50, 250, 200, 25);
        add(cvvLabel);

        cvvField = new JTextField();
        cvvField.setBounds(250, 250, 200, 25);
        add(cvvField);

        makePaymentButton = new JButton("Make Payment");
        makePaymentButton.setBounds(200, 300, 150, 30);
        add(makePaymentButton);

        makePaymentButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                String selectedCustomer = (String) customerNameComboBox.getSelectedItem();
                updatePaymentStatus(selectedCustomer);
                JOptionPane.showMessageDialog(makePaymentButton, "Payment made successfully!");
                dispose(); // Close the current window
                new PaymentGUI(); // Refreshes PaymentGUI window
            }
        });

        setVisible(true);
    }

    //Fetches the list of customers who have not paid for their booking yet
    private void fetchNotPaidCustomers() {
        try {
            String url = dbConfig.getURL();
            String user = dbConfig.getUser();
            String password = dbConfig.getPassword();

            Connection connection = DriverManager.getConnection(url, user, password);
            Statement statement = connection.createStatement();

            String query = "SELECT customername FROM bookings WHERE paymentstatus = 'not paid' ORDER BY bookingid DESC";
            ResultSet resultSet = statement.executeQuery(query);

            List<String> customerNames = new ArrayList<>();
            while (resultSet.next()) {
                String customerName = resultSet.getString("customername");
                customerNames.add(customerName);
            }

            customerNameComboBox.setModel(new DefaultComboBoxModel<>(customerNames.toArray(new String[0])));

            resultSet.close();
            statement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updatePaymentStatus(String selectedCustomer) {
        try {
            String url = dbConfig.getURL();
            String user = dbConfig.getUser();
            String password = dbConfig.getPassword();

            Connection connection = DriverManager.getConnection(url, user, password);

            // Prepare the SQL query
            String updateQuery = "UPDATE bookings SET paymentstatus = 'paid' WHERE customername = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(updateQuery);

            preparedStatement.setString(1, selectedCustomer);

            preparedStatement.executeUpdate();

            preparedStatement.close();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new PaymentGUI());
    }
}
