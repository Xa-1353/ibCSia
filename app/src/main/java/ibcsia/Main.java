package ibcsia;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        ScheduleManager manager = new ScheduleManager();
        Scanner scanner = new Scanner(System.in);

        System.out.println("How many events do you want to add?");
        int numEvents = scanner.nextInt();
        scanner.nextLine();

    for(int i = 0; i < numEvents; i++){

        System.out.println("Enter event title:");
        String title = scanner.nextLine();

        System.out.println("Enter category (Work/Study/Leisure):");
        String category = scanner.nextLine();

        System.out.println("Enter date (MM-DD-YYYY):");
        String date = scanner.nextLine();

        System.out.println("Enter start time (HH:MM):");
        String startTime = scanner.nextLine();

        System.out.println("Enter end time (HH:MM):");
        String endTime = scanner.nextLine();

        Event newEvent = new Event(title, category, date, startTime, endTime);
        manager.addEvent(newEvent);
        
        }

        String[] week = {"03-16-2025", "03-17-2025", "03-18-2025", "03-19-2025", "03-20-2025", "03-21-2025", "03-22-2025"};

        manager.autoScheduleStudy(week);

        System.out.println("\nUpdated Schedule");
        manager.displayEvents();

        scanner.close();
    }
}

