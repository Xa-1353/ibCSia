package ibcsia;

public class Event {
    private String title;
    private String category; // Work, Study, Leisure
    private String date;
    private String startTime;
    private String endTime;

    public Event(String title, String category, String date, String startTime, String endTime) {
        this.title = title;
        this.category = category;
        this.date = date;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public String getTitle(){
        return title;
    }

    public String getCategory(){
        return category;
    }

    public String getDate(){
        return date;
    }

    public String getStartTime(){
        return startTime;
    }

    public String getEndTime(){
        return endTime;
    }

    public int getStartTimeAsMinutes() {
        String[] parts = startTime.split(" ");
        String time = parts[0];
        String amPm = parts[1];
    
        String[] hm = time.split(":");
        int hour = Integer.parseInt(hm[0]);
        int minute = Integer.parseInt(hm[1]);
    
        if (amPm.equals("PM") && hour != 12) hour += 12;
        if (amPm.equals("AM") && hour == 12) hour = 0;
    
        return hour * 60 + minute;
    }

    
    

    
}

