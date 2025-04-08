//import ibcsia.BookingManager;
import ibcsia.ScheduleManager;
import ibcsia.Event;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


public class BookingUI {
    private JTextField dateField, labelField;
    private JComboBox<String> timeBox, endTimeBox, amPmBox, endAmPmBox, categoryBox;
    private JTextArea bookingArea;
    private ScheduleManager manager;

    public BookingUI() {
        this(new ScheduleManager());
    }

    public BookingUI(ScheduleManager existingManager) {
        this.manager = existingManager;

        JFrame frame = new JFrame("Booking Calendar");
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        JPanel inputPanel = new JPanel(new FlowLayout());

        JLabel dateLabel = new JLabel("Enter Date (YYYY-MM-DD):");
        dateField = new JTextField(10);

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
        timeBox = new JComboBox<>(times);

        String[] amPmOptions = {"AM", "PM"};
        amPmBox = new JComboBox<>(amPmOptions);

        JLabel endTimeLabel = new JLabel("End Time:");
        endTimeBox = new JComboBox<>(times);

        String[] endAmPmOptions = {"AM", "PM"};
        endAmPmBox = new JComboBox<>(endAmPmOptions);

        JLabel labelLabel = new JLabel("Event Name:");
        labelField = new JTextField(10);

        JLabel categoryLabel = new JLabel("Category:");
        String[] categories = {"Study", "Work", "Leisure"};
        categoryBox = new JComboBox<>(categories);

        JButton bookButton = new JButton("Add");
        JButton analyzeButton = new JButton("Studify Schedule");

        inputPanel.add(dateLabel);
        inputPanel.add(dateField);
        inputPanel.add(timeLabel);
        inputPanel.add(timeBox);
        inputPanel.add(amPmBox);
        inputPanel.add(endTimeLabel);
        inputPanel.add(endTimeBox);
        inputPanel.add(endAmPmBox);
        inputPanel.add(labelLabel);
        inputPanel.add(labelField);
        inputPanel.add(categoryLabel);
        inputPanel.add(categoryBox);
        inputPanel.add(bookButton);
        inputPanel.add(analyzeButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        bookingArea = new JTextArea(10, 30);
        bookingArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(bookingArea);
        frame.add(scrollPane, BorderLayout.CENTER);

        bookButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleBooking();
            }
        });

        analyzeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                analyzeSchedule();
            }
        });

        frame.setVisible(true);
    }

    private void handleBooking() {
        String date = dateField.getText();
        String startTime = (String) timeBox.getSelectedItem();
        String amPm = (String) amPmBox.getSelectedItem();
        String category = (String) categoryBox.getSelectedItem();
        String eventLabel = labelField.getText().trim();

        if (eventLabel.isEmpty()) {
            eventLabel = "No Label";
        }

        if (!date.isEmpty()) {
            String fullStartTime = startTime + " " + amPm;
            int duration = 60;
            String calculatedEndTime = manager.calculateEndTime(fullStartTime, duration);

            Event event = new Event(eventLabel, category, date, fullStartTime, calculatedEndTime);
            manager.addEvent(event);
            updateBookings();
        }
    }

    private void analyzeSchedule() {
        String[] studyRange = {
            "03-16-2025", "03-17-2025", "03-18-2025", "03-19-2025", "03-20-2025", "03-21-2025", "03-22-2025",
            "03-23-2025", "03-24-2025", "03-25-2025", "03-26-2025", "03-27-2025", "03-28-2025", "03-29-2025",
            "03-30-2025", "03-31-2025", "04-01-2025", "04-02-2025", "04-03-2025", "04-04-2025", "04-05-2025",
            "04-06-2025", "04-07-2025", "04-08-2025", "04-09-2025", "04-10-2025", "04-11-2025", "04-12-2025"
        };

        int studyCount = 0;
        for (String date : manager.getSchedule().keySet()) {
            for (Event event : manager.getSchedule().get(date)) {
                if (event.getCategory().equalsIgnoreCase("Study")) {
                    studyCount++;
                }
            }
        }

        if (studyCount >= 3) {
            JOptionPane.showMessageDialog(null, "Your schedule is already well-balanced!");
        } else {
            JOptionPane.showMessageDialog(null, "Adding more study sessions to help balance your week.");
            manager.autoScheduleStudy(studyRange);
            updateBookings();
        }
    }

    private void updateBookings() {
    bookingArea.setText(""); // clear

    List<String> sortedDates = new ArrayList<>(manager.getSchedule().keySet());
    Collections.sort(sortedDates); // make sure dates are in order

    for (String date : sortedDates) {
        bookingArea.append("\nSchedule for " + date + ":\n");
        for (Event event : manager.getSchedule().get(date)) {
            bookingArea.append(event.getTitle() + " - " + event.getCategory() +
                " from " + event.getStartTime() + " to " + event.getEndTime() + "\n");
        }
    }
}


public static void main(String[] args) {
    ScheduleManager manager = new ScheduleManager();
    new BookingUI(manager);
}

    
}
