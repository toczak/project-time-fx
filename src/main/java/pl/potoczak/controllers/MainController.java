package pl.potoczak.controllers;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Cursor;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.*;
import java.util.Optional;
import java.util.Timer;

import javafx.util.Callback;
import javafx.util.Duration;
import pl.potoczak.models.*;

public class MainController {
    @FXML
    private BorderPane root;
    @FXML
    public TableView<ProjectModel> table;
    @FXML
    private TableColumn<ProjectModel, String> title;
    @FXML
    private TableColumn<ProjectModel, String> progress;
    @FXML
    private TableColumn<ProjectModel, String> time;
    @FXML
    private TableColumn<ProjectModel, String> action;
    @FXML
    private TableColumn<ProjectModel, String> description;

    private boolean active=false;
    private Timer timer = new Timer();
    private Timeline timeline;
    private int minutes = 0, seconds = 0, hours = 0;
    //DataSource dataSource = new DataSource();

    public void initialize() {
        title.setCellValueFactory(new PropertyValueFactory<>("title"));
        time.setCellValueFactory(new PropertyValueFactory<>("time"));
        description.setCellValueFactory(new PropertyValueFactory<>("description"));

        title.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        progress.prefWidthProperty().bind(table.widthProperty().multiply(0.55));
        time.prefWidthProperty().bind(table.widthProperty().multiply(0.15));
        action.prefWidthProperty().bind(table.widthProperty().multiply(0.15));

        addButtonToColumn();
        table.setItems(getTableData());

        try {
            autoLoad();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private void addButtonToColumn(){
        action.setSortable(false);

        Image StartImage = new Image("/icons/start.png",15, 15,false,true);
        Image PauseImage = new Image("/icons/pause.png",15, 15,false,true);
        Image ViewImage = new Image("/icons/view.png",15, 15,false,true);
        Image EditImage = new Image("/icons/edit.png",20, 20,false,true);
        Image RemoveImage = new Image("/icons/remove.png",15, 15,false,true);

        Callback<TableColumn<ProjectModel, String>, TableCell<ProjectModel,String>> cellFactory
        = //
        new Callback<TableColumn<ProjectModel, String>, TableCell<ProjectModel, String>>() {
            @Override
            public TableCell call(final TableColumn<ProjectModel, String> param) {
                final TableCell<ProjectModel,String> cell = new TableCell<ProjectModel, String>() {
                    Button start = new Button("",new ImageView(StartImage));
                    Button pause = new Button("",new ImageView(PauseImage));
                    Button view = new Button("",new ImageView(ViewImage));
                    Button edit = new Button("",new ImageView(EditImage));
                    Button remove = new Button("",new ImageView(RemoveImage));

                    @Override
                    public void updateItem(String item, boolean empty) {
                        super.updateItem(item, empty);
                        if (empty) {
                            setGraphic(null);
                            setText(null);
                        } else {
                            start.setOnAction(event -> {
                                if(!active) {
                                    //Old function timeline
                                    /*timeline = new Timeline(
                                            new KeyFrame(Duration.seconds(0),
                                                    e -> advanceDuration(getIndex())),
                                            new KeyFrame(Duration.seconds(1)));
                                    timeline.setCycleCount(Animation.INDEFINITE);
                                    timeline.play();
                                    ProjectModel projectModel = getTableView().getItems().get(getIndex());


                                    //String time = String.valueOf(hours) + ":" + String.valueOf(minutes) + ":" + String.valueOf(seconds);
                                    //projectModel.setTime((((hours / 10) == 0) ? "0" : "") + hours + ":" + (((minutes / 10) == 0) ? "0" : "") + minutes + ":" + (((seconds / 10) == 0) ? "0" : "") + seconds);
                                    //table.refresh();
                                    System.out.println(time);
                                    System.out.println(projectModel.getTitle()
                                            + "   " + projectModel.getDescription());*/
                                    ProjectModel projectModel = getTableView().getItems().get(getIndex());
                                    String timeField = projectModel.getTime();
                                    System.out.println(timeField);
                                    String[] time = timeField.split ( ":" );
                                    hours = Integer.parseInt ( time[0].trim() );
                                    minutes = Integer.parseInt ( time[1].trim() );
                                    seconds = Integer.parseInt ( time[2].trim() );
                                    timeline = new Timeline(
                                            new KeyFrame(
                                                    Duration.millis(1000),
                                                    e -> {
                                                        if (seconds < 59) {
                                                            seconds++;
                                                        } else {
                                                            seconds = 0;
                                                            if (minutes < 59) {
                                                                minutes++;
                                                            }else{
                                                                minutes = 0;
                                                                hours++;
                                                            }
                                                        }
                                                        projectModel.setTime((((hours / 10) == 0) ? "0" : "") + hours + ":" + (((minutes / 10) == 0) ? "0" : "") + minutes + ":" + (((seconds / 10) == 0) ? "0" : "") + seconds);
                                                        table.refresh();
                                                    }
                                            )
                                    );
                                    timeline.setCycleCount( Animation.INDEFINITE );
                                    timeline.play();
                                    active = true;
                                }

                            });
                            pause.setOnAction(event -> {
                                active = false;
                                timeline.stop();
                            });
                            view.setOnAction(event -> {
                                System.out.println("Klikasz view");
                            });
                            remove.setOnAction(event -> {
                                Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this row?");
                                Optional<ButtonType> result = alert.showAndWait();
                                if (result.get() == ButtonType.OK) {
                                    ProjectModel row = getTableView().getItems().get(getIndex());
                                    table.getItems().remove(row);
                                    System.out.println(row.getTitle()
                                            + "   " + row.getDescription());
                                }

                            });
                            HBox buttons = new HBox(start, pause, view, edit, remove);
                            buttons.setCursor(Cursor.HAND);
                            buttons.setSpacing(5);
                            setGraphic(buttons);
                            setText(null);
                        }
                    }

                };
                return cell;
            }

        };
        action.setCellFactory(cellFactory);
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
                        ProjectModel projectModel = new ProjectModel(fields[0], fields[1], fields[2]);
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
            ProjectModel projectModel = new ProjectModel(fields[0], fields[1], fields[2]);
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
        timer.cancel();
        Platform.exit();
    }

    private ObservableList<ProjectModel> getTableData(){
        return FXCollections.observableArrayList();
    }


}


