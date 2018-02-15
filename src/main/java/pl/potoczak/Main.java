package pl.potoczak;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import static javafx.application.Application.launch;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(this.getClass().getResource("/fxml/mainScreen.fxml"));
        Pane pane = loader.load();
        Scene scene = new Scene(pane);
        //scene.getStylesheets().add("");
        Stage stage = new Stage();
        stage.setScene(scene);
        stage.setTitle("Project time");
        stage.setResizable(false);
        stage.initStyle(StageStyle.UTILITY);
        stage.show();
    }
}
