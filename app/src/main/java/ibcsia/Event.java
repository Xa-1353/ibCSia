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

    // Getters and setters here
}

