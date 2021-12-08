package start.application.RunningApp;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;


public class Workout implements Serializable{

    @Exclude private String id;

    private String name, description;

    public Workout() {

    }

    public Workout(String name, String description) {
        this.name = name;
        this.description = description;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }
}
