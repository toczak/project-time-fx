package pl.potoczak.controllers;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

import pl.potoczak.models.*;

public class AddRowController {

    @FXML private Button addButton;
    @FXML private TextField titleText;
    @FXML private TextArea descriptionText;
    private TableView<ProjectModel> table;
    private ObservableList<ProjectModel> tableData;

    public void initialize() {
    }

    @FXML protected void addRow(ActionEvent event){
        tableData.add(new ProjectModel(titleText.getText(), descriptionText.getText()));
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();

    }


    public void setTableData(ObservableList<ProjectModel> tableData){
        this.tableData = tableData;
    }



}
