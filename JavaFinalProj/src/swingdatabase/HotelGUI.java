package swingdatabase;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import databases.DBConfig;

public class HotelGUI extends JFrame {
    private JTable dataTable;

    public HotelGUI() {
        super("Hotels Page");

        JLabel heading = new JLabel("List of Hotels and Available Rooms");
        heading.setFont(new Font("Arial", Font.BOLD, 16));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        add(heading, BorderLayout.NORTH);

        String[] columns = {"Hotel Name", "Location", "Price Per Day", "Rating", "Available Rooms"};
        DefaultTableModel model = new DefaultTableModel(columns, 0);

        dataTable = new JTable(model);
        tableAppearance(dataTable);

        JScrollPane scrollPane = new JScrollPane(dataTable);
        scrollPane.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Add space around the table
        add(scrollPane, BorderLayout.CENTER);


        JButton bookingButton = new JButton("Open Booking Window");
        bookingButton.setBounds(100, 500, 100, 30);
        bookingButton.addActionListener(new BookingButtonListener());
        add(bookingButton, BorderLayout.AFTER_LAST_LINE);


        setSize(900, 600);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        displayHotels();
    }

    //Gets the hotels from the db which have available rooms
    private void displayHotels() {
        DBConfig dbConfig = new DBConfig();
        String url = dbConfig.getURL();
        String user = dbConfig.getUser();
        String password = dbConfig.getPassword();

        try (Connection connection = DriverManager.getConnection(url, user, password);
             Statement statement = connection.createStatement()) {

            String query = "SELECT * FROM hotels where availablerooms > 0";
            ResultSet resultSet = statement.executeQuery(query);

            DefaultTableModel model = (DefaultTableModel) dataTable.getModel();
            while (resultSet.next()) {
                Object[] row = {
                        resultSet.getString("name"),
                        resultSet.getString("location"),
                        "$" + resultSet.getInt("priceperday"),
                        resultSet.getDouble("rating"),
                        resultSet.getInt("availablerooms")
                };
                model.addRow(row);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //Mehthod for the Button to open the booking window
    private class BookingButtonListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            new BookingGUI();
        }
    }
    private void tableAppearance(JTable table) {

        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(Color.WHITE);
        header.setForeground(Color.BLACK);

        table.setGridColor(Color.LIGHT_GRAY);
        table.setSelectionBackground(Color.LIGHT_GRAY);
        table.setSelectionForeground(Color.BLACK);
        table.setFont(new Font("Arial", Font.PLAIN, 12));
        table.setRowHeight(30);

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table.setDefaultRenderer(Object.class, centerRenderer);
    }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new HotelGUI());
    }
}
