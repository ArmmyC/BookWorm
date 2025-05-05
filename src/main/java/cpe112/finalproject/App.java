package cpe112.finalproject;

import cpe112.finalproject.Managers.SceneManager;
import cpe112.finalproject.Scenes.MainMenuScene;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage primaryStage) {
        SceneManager.getInstance().setStage(primaryStage);

        MainMenuScene mainMenu = new MainMenuScene();
        Scene scene = new Scene(mainMenu.getRootPane(), 800, 600);

        primaryStage.setTitle("BookWorms");
        primaryStage.setScene(scene);

        primaryStage.setMinHeight(600);
        primaryStage.setMinWidth(800);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}