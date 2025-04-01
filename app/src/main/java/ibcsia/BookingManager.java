package ibcsia;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.List;

public class BookingManager {
    private HashMap<String, List<String>> bookings;

    public BookingManager() {
        bookings = new HashMap<>();
    }

    public void addBooking(String date, String details) {
        bookings.computeIfAbsent(date, k -> new ArrayList<>()).add(details);
    }

    public List<String> getBookings(String date) {
        return bookings.getOrDefault(date, new ArrayList<>());
    }
}
