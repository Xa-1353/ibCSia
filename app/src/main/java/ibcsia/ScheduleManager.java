package ibcsia;
//import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ScheduleManager {
    private HashMap<String, ArrayList<Event>> schedule; 
    private Random random;

    public HashMap<String, ArrayList<Event>> getSchedule() {
        return schedule;
    }
    

    public ScheduleManager() {
        schedule = new HashMap<>();
        random = new Random();
    }

    public void addEvent(Event event) {
        schedule.putIfAbsent(event.getDate(), new ArrayList<>());
        schedule.get(event.getDate()).add(event);
    }

    public void displayEvents() {
      for (String date : schedule.keySet()){
        System.out.println("\nSchedule for "+ date + ":");
        for (Event event : schedule.get(date)){
            System.out.println(event.getTitle() + " - " + event.getCategory() + "from " + event.getStartTime() + " to " + event.getEndTime());
        }
      }    
    }

    public void autoScheduleStudy(String[] daysOfWeek) {
        for (String date : daysOfWeek) {
            ArrayList<Event> events = schedule.getOrDefault(date, new ArrayList<>());
    
            // ❌ Skip scheduling if the day already has 3 or more events
            if (events.size() >= 3) {
                System.out.println("Skipping auto-scheduling for " + date + " (too busy)");
                continue;
            }
    
            // ✅ Set a preferred study time that is not too late
            String[] possibleStartTimes = {"14:00", "15:00", "16:00", "17:00"};
            String startTime = possibleStartTimes[random.nextInt(possibleStartTimes.length)];
            
            // ✅ Calculate end time (30 or 60 minutes later)
            int duration = random.nextBoolean() ? 30 : 60;
            String endTime = calculateEndTime(startTime, duration);
    
            Event studySession = new Event("Auto-Study", "Study", date, startTime, endTime);
            addEvent(studySession);
            System.out.println("Scheduled study session on " + date + " from " + startTime + " to " + endTime);
        }
    }
    
    // ✅ Helper method to calculate end time
    private String calculateEndTime(String startTime, int duration) {
        String[] parts = startTime.split(":");
        int hours = Integer.parseInt(parts[0]);
        int minutes = Integer.parseInt(parts[1]);
    
        minutes += duration;
        if (minutes >= 60) {
            hours += 1;
            minutes -= 60;
        }
    
        return String.format("%02d:%02d", hours, minutes);
    }
    
    
}
