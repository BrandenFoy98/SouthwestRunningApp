package start.application.RunningApp;

import androidx.annotation.NonNull;


public class Workout {
    private String day;
    private int distance;
    private String description;

    public Workout() {
    }

    public Workout(String day, String description, int distance) {
        this.day = day;
        this.description = description;
        this.distance = distance;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }
    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getDistance() {
        return distance;
    }

    public void setDistance(int distance) {
        this.distance = distance;
    }

    @NonNull
    @Override
    public String toString() {
        return this.description + " " + this.day + " " + this.distance;
    }
}