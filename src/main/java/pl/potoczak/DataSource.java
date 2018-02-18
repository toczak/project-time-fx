package pl.potoczak;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import pl.potoczak.models.*;

public class DataSource {
    //private static ObservableList<ProjectModel> tableData = FXCollections.observableArrayList();

    /*public static ObservableList<ProjectModel> getTableData() {
        ProjectModel pro1 = new ProjectModel("x","t");
        ProjectModel pro2 = new ProjectModel("fefesfds","tfdsfdsfdsfsdfds");
        ProjectModel pro3 = new ProjectModel("xtfdsfdsfdsfsdfds","ttfdsfdsfdsfsdfdstfdsfdsfdsfsdfds");
        return FXCollections.observableArrayList(pro1,pro2,pro3);
    }*/

    public ObservableList<ProjectModel> getTableData(){
        ObservableList<ProjectModel> data = FXCollections.observableArrayList();
        data.add(new ProjectModel("x","t"));
        data.add(new ProjectModel("xdxzc","tre"));
        data.add(new ProjectModel("xddddddddddddxzc","tredddddddddddd"));
        return data;
    }

}
