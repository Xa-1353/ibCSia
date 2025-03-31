package ibcsia;
import javax.swing.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;

public class ScheduleManager {
    private HashMap<String, ArrayList<Event>> schedule; 
    private Random random;

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

    public void autoScheduleStudy(String[] daysOfWeek){
        for (String date : daysOfWeek){
            if(!schedule.containsKey(date)){
                int duration = random.nextBoolean() ? 30 : 60;
                String startTime = "16:00";
                String endTime = (duration == 30) ? "16:30" : "17:00";

                Event studySession = new Event("Auto-Study", "Study", date, startTime, endTime);
                addEvent(studySession);
                System.out.println("Recommended study session on " + date + " from " + startTime + " to " + endTime);
            }
        }
    }
}
