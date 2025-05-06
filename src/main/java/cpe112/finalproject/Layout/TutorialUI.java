package cpe112.finalproject.Layout;

import cpe112.finalproject.Constants.Name;
import cpe112.finalproject.Managers.SoundManager;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.geometry.Insets;

/*
 * TutorialUI.java
 * สำหรับสร้าง UI ของ Tutorial หรือหน้าแนะนำการเล่นเกม
 */

public class TutorialUI {

    // JavaFX Components ของ TutorialUI
    private final StackPane rootPane = new StackPane();
    private final StackPane tutorialPane = new StackPane();
    private final VBox textPane = new VBox();

    // ปุ่มปิด
    private final Button closeButton = new Button("Close");

    // Label สำหรับชื่อ Tutorial และคำอธิบาย
    private final Label tutorialTitle = new Label("How to Play");
    private final Label tutorialLabel = new Label(Name.TUTORIAL_DESCRIPTION);

    // Constructor สำหรับ TutorialUI
    public TutorialUI() {
        setupLayout();
        setupButton();
        setupLabel();
    }

    // Method สำหรับสร้างและตั้งค่า UI ของ Tutorial
    private void setupLayout() {
        rootPane.getChildren().addAll(tutorialPane, textPane, closeButton);
        textPane.getChildren().addAll(tutorialTitle, tutorialLabel);
        rootPane.setAlignment(Pos.CENTER);
        textPane.setAlignment(Pos.TOP_CENTER);
        StackPane.setAlignment(closeButton, Pos.TOP_RIGHT);

        rootPane.setStyle("-fx-background-color:transparent");
        rootPane.setMouseTransparent(true);
        rootPane.setVisible(false);

        textPane.setPadding(new Insets(20));
        tutorialPane.setStyle(
                "-fx-background-color:rgba(25,25,25); -fx-padding: 20; -fx-border-color: white; -fx-border-width: 2; -fx-background-radius: 10; -fx-border-radius: 10;");
    }

    // Method สำหรับตั้งค่าขนาดและสไตล์ของ Label
    private void setupLabel() {
        tutorialTitle.setStyle(
                "-fx-font-family: 'NEXORA'; -fx-text-fill: white; -fx-font-size: 30px; -fx-padding: 10;");
        tutorialLabel.setStyle(
                "-fx-font-family: 'NEXORA'; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 10;");
        textPane.setSpacing(10);

        tutorialLabel.setWrapText(true);
    }

    // Method สำหรับตั้งค่าการทำงานของปุ่ม Close
    private void setupButton() {
        closeButton.setStyle(
                "-fx-font-family: 'NEXORA'; -fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 20px;");
        closeButton.setOnMouseEntered(e -> closeButton.setStyle(
                "-fx-font-family: 'NEXORA'; -fx-background-color: transparent; -fx-text-fill: #FF0000; -fx-font-size: 20px;"));
        closeButton.setOnMouseExited(e -> closeButton.setStyle(
                "-fx-font-family: 'NEXORA'; -fx-background-color: transparent; -fx-text-fill: white; -fx-font-size: 20px;"));
        closeButton.setOnMouseClicked(e -> closeButtonAction());
    }

    // Method สำหรับตั้งค่าการทำงานของปุ่ม Close
    private void closeButtonAction() {
        rootPane.setVisible(false);
        rootPane.setMouseTransparent(true);
        SoundManager.playClickSound();
    }

    // Getter สำหรับเข้าถึง rootPane ของ TutorialUI
    public StackPane getRootPane() {
        return rootPane;
    }

}
