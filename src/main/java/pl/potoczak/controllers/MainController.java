package pl.potoczak.controllers;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableColumn;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.control.TableView;

import java.io.*;
import java.util.Optional;

import pl.potoczak.DataSource;
import pl.potoczak.models.*;

public class MainController {
    @FXML
    private BorderPane root;
    @FXML
    public TableView<ProjectModel> table;
    @FXML
    private TableColumn<ProjectModel, String> title;
    @FXML
    private TableColumn<ProjectModel, String> time;
    @FXML
    private TableColumn<ProjectModel, String> description;

    //DataSource dataSource = new DataSource();

    public void initialize() {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));
        table.setItems(getTableData());

        try {
            autoLoad();
        } catch (IOException e) {
            e.printStackTrace();
        }
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

    @FXML
    private void importFile() throws IOException {
        ObservableList<ProjectModel> tableData = table.getItems();
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open file...");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        fileChooser.getExtensionFilters().addAll(
                new FileChooser.ExtensionFilter("CSV files (*.csv)", "*.csv")
        );
        try {
            File chosenFile = fileChooser.showOpenDialog(new Stage());
            if (chosenFile != null) {
                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Projects will be changed. Are you sure you want to import file?");
                Optional<ButtonType> result = alert.showAndWait();
                if (result.get() == ButtonType.OK) {
                    tableData.clear();
                    String path = chosenFile.toString();
                    String limiter = ";";
                    BufferedReader bufferedReader = new BufferedReader(new FileReader(path));
                    String text;
                    while ((text = bufferedReader.readLine()) != null) {
                        String[] fields = text.split(limiter, -1);
                        ProjectModel projectModel = new ProjectModel(fields[0].toString(), fields[1].toString(), fields[2].toString());
                        tableData.add(projectModel);
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Failed to import file.");
        }
    }

    @FXML
    private void exportFile() throws IOException {
        ObservableList<ProjectModel> tableData = table.getItems();
        FileWriter fileWriter;
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save as...");
        fileChooser.setInitialDirectory(new File(System.getProperty("user.home")));
        FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter("CSV files (*.csv)", ".csv", "csv");
        fileChooser.getExtensionFilters().add(extensionFilter);
        try {
            File chosenFile = fileChooser.showSaveDialog(new Stage());
            if (!chosenFile.getName().contains(".")) {
                chosenFile = new File(chosenFile.getAbsolutePath() + ".csv");
            }
            fileWriter = new FileWriter(chosenFile);
            for (ProjectModel projectModel : tableData) {
                String text = projectModel.getTitle() + ";" + projectModel.getTime() + ";" + projectModel.getDescription() + "\n";
                fileWriter.write(text);
            }
            fileWriter.close();
        } catch (Exception e) {
            System.out.println("Failed to export file.");
        }
    }

    private void autoLoad() throws IOException {
        ObservableList<ProjectModel> tableData = table.getItems();
        tableData.clear();
        String limiter = ";";
        BufferedReader bufferedReader = new BufferedReader(new FileReader("Projects.csv"));
        String text;
        while ((text = bufferedReader.readLine()) != null) {
            String[] fields = text.split(limiter, -1);
            ProjectModel projectModel = new ProjectModel(fields[0].toString(), fields[1].toString(), fields[2].toString());
            tableData.add(projectModel);
        }
    }

    private void autoSave() throws IOException {
        ObservableList<ProjectModel> tableData = table.getItems();
        FileWriter fileWriter;
        fileWriter = new FileWriter("Projects.csv");
        for (ProjectModel projectModel : tableData) {
            String text = projectModel.getTitle() + ";" + projectModel.getTime() + ";" + projectModel.getDescription() + "\n";
            fileWriter.write(text);
        }
        fileWriter.close();
    }

    @FXML
    public void closeApp(){
        try {
            autoSave();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Platform.exit();
    }

    private ObservableList<ProjectModel> getTableData(){
        ObservableList<ProjectModel> data = FXCollections.observableArrayList();
        return data;
    }
}
