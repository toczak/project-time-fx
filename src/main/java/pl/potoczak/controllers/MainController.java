package pl.potoczak.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TableView;
import java.io.IOException;

import pl.potoczak.DataSource;
import pl.potoczak.models.*;

public class MainController {
    @FXML public TableView<ProjectModel> table;
    @FXML private TableColumn<ProjectModel, String> title;
    @FXML private TableColumn<ProjectModel, String> time;

    DataSource dataSource = new DataSource();

    public void initialize() {
        System.out.println("Start");
        init(dataSource.getTableData());
    }

    private void init(ObservableList<ProjectModel> list){
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        table.setItems(list);
        System.out.println(list);
        System.out.println(table);
    }

    @FXML
    private void editAdd() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/FXML/AddRowScreen.fxml"));
        Parent parent = fxmlLoader.load();

        AddRowController addController = fxmlLoader.getController();
        addController.setTableData(table.getItems());

        Stage stage = new Stage();
        stage.initStyle(StageStyle.UTILITY);
        stage.setScene(new Scene(parent));
        stage.setTitle("Add new project");
        stage.setResizable(false);
        stage.showAndWait();
    }


}
