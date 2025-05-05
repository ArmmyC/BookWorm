package cpe112.finalproject.Layout;

import cpe112.finalproject.Managers.EnemyManager;
import cpe112.finalproject.Managers.PlayerManager;
import cpe112.finalproject.Managers.SceneManager;
import cpe112.finalproject.Scenes.MainMenuScene;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/*
 * GameOverUI.java
 * สำหรับสร้าง UI ของหน้าจอ Game Over
 */

public class GameOverUI {

    // JavaFX Components ของ GameOverUI
    private final StackPane rootPane = new StackPane();
    private final VBox gameOverPane = new VBox();
    private final Label gameOverLabel = new Label("Game Over");
    private final Button restartButton = new Button("Restart Game");
    private final Label playerScoreLabel = new Label();
    private final Label playerNameLabel = new Label();
    private final Label playerClassLabel = new Label();
    private final Label playerAttackLabel = new Label();
    private final Label playerDefenseLabel = new Label();
    private final Region spacer1 = new Region();
    private final Region spacer2 = new Region();

    // Constructor สำหรับ GameOverUI
    public GameOverUI() {
        setupLayout();
        setupRestartButton();
    }

    // Method สำหรับสร้างและตั้งค่า UI ของ Game Over
    private void setupLayout() {

        // ตั้งค่า Layout ของ UI
        rootPane.getChildren().add(gameOverPane);
        gameOverPane.getChildren().addAll(gameOverLabel, playerScoreLabel, spacer1, playerNameLabel, playerClassLabel,
                playerAttackLabel,
                playerDefenseLabel, spacer2, restartButton);
        VBox.setVgrow(spacer1, Priority.ALWAYS); // ทำให้ spacer1 ขยายเต็มพื้นที่
        VBox.setVgrow(spacer2, Priority.ALWAYS); // ทำให้ spacer2 ขยายเต็มพื้นที่

        rootPane.setMouseTransparent(true); // ทำให้ไม่สามารถคลิกที่ Game Over UI ได้
        rootPane.setVisible(false); // ทำให้ Game Over UI ไม่แสดงผล

        // ตั้งค่าขนาดของ UI
        gameOverPane.maxWidthProperty().bind(rootPane.widthProperty().multiply(0.7));
        gameOverPane.maxHeightProperty().bind(rootPane.heightProperty().multiply(0.7));

        // ตั้งค่าการจัดตำแหน่งของ UI
        rootPane.setAlignment(Pos.CENTER);
        gameOverPane.setAlignment(Pos.TOP_CENTER);

        // ตั้งค่า Label ทั้งหมด
        setupLabel(playerScoreLabel);
        setupLabel(playerNameLabel);
        setupLabel(playerClassLabel);
        setupLabel(playerAttackLabel);
        setupLabel(playerDefenseLabel);

        // ตั้งค่า Style เบื้องต้นให้กับ UI
        gameOverPane.setStyle(
                "-fx-background-color: rgb(26, 26, 26); -fx-padding: 20; -fx-spacing: 10; -fx-border-color: rgb(255, 255, 255); -fx-border-width: 2; -fx-border-radius: 10; -fx-background-radius: 10;");
        gameOverLabel.setStyle(
                "-fx-font-family: 'NEXORA'; -fx-text-fill: rgb(255, 255, 255); -fx-font-size: 40; -fx-font-weight: bold;");
    }

    // Method สำหรับตั้งค่า Style ของ Label
    private void setupLabel(Label label) {
        label.setStyle(
                "-fx-font-family: 'NEXORA'; -fx-text-fill: rgb(255, 255, 255); -fx-font-size: 20; -fx-font-weight: bold;");
    }

    // Method สำหรับแสดง Game Over UI เมื่อผู้เล่นแพ้
    public void GameOver() {
        rootPane.setVisible(true);
        rootPane.setMouseTransparent(false);
        playerScoreLabel.setText("Score: " + EnemyManager.getInstance().getEnemyCount());
        playerNameLabel.setText("Name: " + PlayerManager.getInstance().getPlayerName());
        playerClassLabel.setText("Class: " + PlayerManager.getInstance().getClassName());
        playerAttackLabel.setText(
                "Attack: " + String.valueOf(PlayerManager.getInstance().getAttack()));
        playerDefenseLabel.setText(
                "Defense: " + String.valueOf(PlayerManager.getInstance().getDefense()));

    }

    // Method สำหรับตั้งค่า Restart Button
    private void setupRestartButton() {
        restartButton.setStyle(
                "-fx-background-color: rgb(255, 255, 255); -fx-text-fill: rgb(0, 0, 0); -fx-font-family: 'NEXORA'; -fx-font-size: 20; -fx-font-weight: bold; -fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");
        restartButton.setOnAction(event -> {
            MainMenuScene mainMenuScene = new MainMenuScene();
            SceneManager.getInstance().switchRoot(mainMenuScene.getRootPane());
        });
    }

    // ============================================================
    // -------------- Getter สำหรับการเข้าถึง Object ต่างๆ -------------
    // ============================================================
    public StackPane getRootPane() {
        return rootPane;
    }

    public Button getRestartButton() {
        return restartButton;
    }
    // ============================================================
}
