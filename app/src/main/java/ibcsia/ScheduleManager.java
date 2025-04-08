package ibcsia;
//import javax.swing.*;
//import java.util.HashMap;
import java.util.*;

public class ScheduleManager {
    private TreeMap<String, List<Event>> schedule; // TreeMap keeps dates sorted
    

    public ScheduleManager() {
        schedule = new TreeMap<>();
       
    }

    public void addEvent(Event event) {
        schedule.computeIfAbsent(event.getDate(), k -> new ArrayList<>()).add(event);
        sortEventsForDate(event.getDate()); // Sort the list after adding
    }
    
    private void sortEventsForDate(String date) {
        List<Event> eventsForDate = schedule.get(date); // get list of events
        if (eventsForDate != null) {
            eventsForDate.sort(Comparator.comparingInt(Event::getStartTimeAsMinutes)); // sort using your Event method
        }
    }
    
    
    

    public TreeMap<String, List<Event>> getSchedule() {
        return schedule;
    }

    public void autoScheduleStudy(String[] daysOfWeek) {
        for (String date : daysOfWeek) {
            // Skip if too many events already
            if (schedule.containsKey(date) && schedule.get(date).size() >= 5) continue;
    
            List<Event> dayEvents = schedule.getOrDefault(date, new ArrayList<>());
            
            // Check if a study session already exists that day
            boolean hasStudy = dayEvents.stream().anyMatch(e -> e.getCategory().equals("Study"));
            if (hasStudy) continue;
    
            // Try to schedule between 6 PM and 10 PM only
            String[] candidateTimes = {"6:00", "7:00", "8:00", "9:00", "10:00"};
            for (String start : candidateTimes) {
                String end = calculateEndTime(start, 60);
                boolean overlaps = false;
    
                for (Event e : dayEvents) {
                    if (timeOverlaps(start, end, e.getStartTime(), e.getEndTime())) {
                        overlaps = true;
                        break;
                    }
                }
    
                if (!overlaps) {
                    Event study = new Event("Auto-Study", "Study", date, start, end);
                    addEvent(study);
                    System.out.println("Scheduled Auto-Study on " + date + " from " + start + " to " + end);
                    break; // only one session per day
                }
            }
        }
    }

    private boolean timeOverlaps(String start1, String end1, String start2, String end2) {
        int s1 = convertToMinutes(start1);
        int e1 = convertToMinutes(end1);
        int s2 = convertToMinutes(start2);
        int e2 = convertToMinutes(end2);
    
        return s1 < e2 && s2 < e1;
    }
    
    private int convertToMinutes(String timeStr) {
        String[] parts = timeStr.split(" ");
        String time = parts[0];
        String amPm = parts[1];
    
        String[] hm = time.split(":");
        int hour = Integer.parseInt(hm[0]);
        int minute = Integer.parseInt(hm[1]);
    
        if (amPm.equals("PM") && hour != 12) hour += 12;
        if (amPm.equals("AM") && hour == 12) hour = 0;
    
        return hour * 60 + minute;
    }
    
    
    

    
    // Method to calculate end time
    public String calculateEndTime(String startTime, int duration) {
        String[] timeParts = startTime.split(" ");
        String time = timeParts[0];
        String amPm = timeParts[1];
    
        String[] hm = time.split(":");
        int hour = Integer.parseInt(hm[0]);
        int minute = Integer.parseInt(hm[1]);
    
        // Convert to 24-hour time for calculation
        if (amPm.equals("PM") && hour != 12) hour += 12;
        if (amPm.equals("AM") && hour == 12) hour = 0;
    
        minute += duration;
        if (minute >= 60) {
            hour += minute / 60;
            minute %= 60;
        }
    
        // Wrap around if hour exceeds 24
        hour %= 24;
    
        // Convert back to 12-hour format
        String newAmPm = (hour >= 12) ? "PM" : "AM";
        int displayHour = hour % 12;
        if (displayHour == 0) displayHour = 12;
    
        return String.format("%02d:%02d %s", displayHour, minute, newAmPm);
    }
    

    public void displayEvents() {
    for (String date : schedule.keySet()) {
        System.out.println("\nSchedule for " + date + ":");
        for (Event event : schedule.get(date)) {
            System.out.println(event.getTitle() + " - " + event.getCategory() +
                " from " + event.getStartTime() + " to " + event.getEndTime());
        }
    }
}

    
}  

