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
    private final SimpleStringProperty time;

    public ProjectModel(String title, String time) {
        this.title = new SimpleStringProperty(title);
        this.time = new SimpleStringProperty(time);
    }

    public String getTitle(){
        return title.get();
    }

    public String getTime(){
        return time.get();
    }

    public void setTitle(String value){
        title.set(value);
    }

    public void setTime(String value){
        time.set(value);
    }

}

