package tarik.inc.teachersapp;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
import tarik.inc.teachersapp.repository.Repository;

import java.io.IOException;

public class HelloApplication extends Application {
    Repository repository = new Repository();
    @Override
    public void start(Stage stage) throws IOException {
        repository.load();
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("Scene1.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 439);
        stage.setTitle("Application");
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    @Override
    public void stop(){
        repository.save();
        Platform.exit();
    }

    public static void main(String[] args) {
        launch();
    }
}