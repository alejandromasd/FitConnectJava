package fitconnect.activities;

public class Activities {
    private String name;
    private String description;
    private String date;
    private String time;
    private String coachId;


    public Activities(String name, String description, String date, String time) {
        this.name = name;
        this.description = description;
        this.date = date;
        this.time = time;
    }
    public Activities(String name, String description, String date, String time, String coachId) {
        this(name, description, date, time);  // Call the original constructor
        this.coachId = coachId;
    }
    public String getCoachId() {
        return coachId;
    }

    public void setCoachId(String coachId) {
        this.coachId = coachId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    @Override
    public String toString() {
        return "Activity: " + getName() + "\n" +
                "Description: " + getDescription() + "\n" +
                "Date: " + getDate() + "\n" +
                "Time: " + getTime() + "\n";
    }
}

