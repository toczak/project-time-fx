package pl.potoczak.models;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ProjectModel {
    /*private String title;
    private String time;

    public ProjectModel(){
        this.title = "";
        this.time = "";
    }

    public ProjectModel(String title, String time) {
        this.title = title;
        this.time = time;
    }

    public String getTitle(){
        return title;
    }

    public String getTime(){
        return time;
    }

    public void setTitle(String value){
        this.title = value;
    }

    public void setTime(String value){
        this.time = value;
    }*/

    private final SimpleStringProperty title;
    private final SimpleStringProperty description;
    private final SimpleStringProperty time;

    public ProjectModel(String title, String time, String description) {
        this.title = new SimpleStringProperty(title);
        this.description = new SimpleStringProperty(description);
        this.time = new SimpleStringProperty(time);
    }

    public String getTitle(){
        return title.get();
    }

    public String getTime(){
        return time.get();
    }

    public String getDescription() {
        return description.get();
    }

    public void setDescription(String value) {
        description.set(value);
    }

    public void setTitle(String value){
        title.set(value);
    }

    public void setTime(String value){
        time.set(value);
    }

}

