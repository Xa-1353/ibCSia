//import ibcsia.BookingManager;
import ibcsia.ScheduleManager;
import ibcsia.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BookingUI {
    private JTextField dateField, labelField;
    private JComboBox<String> timeBox, endTimeBox, amPmBox, endAmPmBox, categoryBox;
    private JTextArea bookingArea;
    private ScheduleManager manager;

    public BookingUI() {
        manager = new ScheduleManager(); // Use ScheduleManager instead

        // Create the main frame
        JFrame frame = new JFrame("Booking Calendar");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        // Create input panel
        JPanel inputPanel = new JPanel(new FlowLayout());

        // Date input
        JLabel dateLabel = new JLabel("Enter Date (YYYY-MM-DD):");
        dateField = new JTextField(10); // Use instance variable

        // Time selection
        JLabel timeLabel = new JLabel("Start Time:");
        String[] times = {
            "01:00", "01:15", "01:30", "01:45",
            "02:00", "02:15", "02:30", "02:45",
            "03:00", "03:15", "03:30", "03:45", 
            "04:00", "04:15", "04:30", "04:45", 
            "05:00", "05:15", "05:30", "05:45",
            "06:00", "06:15", "06:30", "06:45",
            "07:00", "07:15", "07:30", "07:45",
            "08:00", "08:15", "08:30", "08:45",
            "09:00", "09:15", "09:30", "09:45",
            "10:00", "10:15", "10:30", "10:45",
            "11:00", "11:15", "11:30", "11:45",
            "12:00", "12:15", "12:30", "12:45"
        };
        timeBox = new JComboBox<>(times); // Use instance variable

        // AM/PM selection for start time
        String[] amPmOptions = {"AM", "PM"};
        amPmBox = new JComboBox<>(amPmOptions);

        // End Time selection
        JLabel endTimeLabel = new JLabel("End Time:");
        endTimeBox = new JComboBox<>(times);

        // AM/PM selection for end time
        String[] endAmPmOptions = {"AM", "PM"};
        endAmPmBox = new JComboBox<>(endAmPmOptions); // Use instance variable

        // Event Name input
        JLabel labelLabel = new JLabel("Event Name:");
        labelField = new JTextField(10); // Use instance variable

        // Category selection
        JLabel categoryLabel = new JLabel("Category:");
        String[] categories = {"Study", "Work", "Leisure"};
        categoryBox = new JComboBox<>(categories); // Use instance variable

        // Add button
        JButton bookButton = new JButton("Add");

        // Add components to panel in the correct order
        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        inputPanel.add(timeLabel);
        inputPanel.add(timeBox);
        inputPanel.add(amPmBox); // 🔹 Add AM/PM for start time
        inputPanel.add(endTimeLabel);
        inputPanel.add(endTimeBox);
        inputPanel.add(endAmPmBox); // 🔹 Add AM/PM for end time
        inputPanel.add(labelLabel);
        inputPanel.add(labelField);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryBox);
        inputPanel.add(bookButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Booking display area
        bookingArea = new JTextArea(10, 30);
        bookingArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(bookingArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Attach Action Listener to Book Button
        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBooking();
            }
        });

        // Show the Frame
        frame.setVisible(true);
    }

    // Method to handle booking logic
    private void handleBooking() {
        String date = dateField.getText();
        String time = (String) timeBox.getSelectedItem();
        String endTime = (String) endTimeBox.getSelectedItem();
        String amPm = (String) amPmBox.getSelectedItem();
        String endAmPm = (String) endAmPmBox.getSelectedItem(); // ✅ Get AM/PM for end time
        String category = (String) categoryBox.getSelectedItem();
        String eventLabel = labelField.getText().trim();

        System.out.println("User input - Date: " + date + ", Start Time: " + time + " " + amPm + ", End Time: " + endTime + " " + endAmPm +
        ", Category: " + category + ", Label: " + eventLabel);
            
        if (eventLabel.isEmpty()) {
            eventLabel = "No Label"; // Default if empty
        }

        if (!date.isEmpty()) {
            String startTime = time + " " + amPm;
            String formattedendTime = endTime + " " + (String) endAmPmBox.getSelectedItem();


            // Create the Event object
            Event event = new Event(eventLabel, category, date, startTime, formattedendTime);
            manager.addEvent(event); // Add it to the schedule

            // If it's a Study event, trigger auto-scheduling
            if (category.equals("Study")) {
                String[] daysOfWeek = {date}; // You can expand this if needed
                manager.autoScheduleStudy(daysOfWeek);
            }
        }  
        updateBookings();        
    }

    // Updates the display area with bookings
    private void updateBookings() {
        bookingArea.setText(""); // Clear previous entries
    
        for (String date : manager.getSchedule().keySet()) { 
            bookingArea.append("\n📅 Schedule for " + date + ":\n");
            
            for (Event event : manager.getSchedule().get(date)) {
                bookingArea.append("   🕒 " + event.getStartTime() + " - " + event.getEndTime() + 
                    " | " + event.getCategory() + " | " + event.getTitle() + "\n");
            }
        }
    }
    

    public static void main(String[] args) {
        new BookingUI();
    }
}
