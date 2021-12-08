package start.application.RunningApp;

import com.google.firebase.firestore.Exclude;

import java.io.Serializable;


public class Student implements Serializable{

    @Exclude private String id;

    private String student;

    public Student() {

    }

    public Student(String student) {
        this.student = student;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;

    }

    public String getStudent() {
        return student;
    }

}
