package cpe112.finalproject.Layout;

import cpe112.finalproject.Constants.Name;
import cpe112.finalproject.Constants.Style;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/*
 * ClassSelectorUI.java
 * สำหรับสร้าง UI ของ Class Selector หรือหน้าเลือกตัวละคร
 */

public class ClassSelectorUI {

    // JavaFX Components ของ ClassSelectorUI
    private final BorderPane rootPane = new BorderPane();
    private final VBox centerPane = new VBox();
    private final HBox classSelectorPane = new HBox();
    private final HBox inputPane = new HBox();
    private final HBox buttonsPane = new HBox();

    // Label สำหรับบอกให้ผู้เล่นกรอกชื่อ
    // Field สำหรับให้ผู้เล่นกรอกชื่อ
    private final Label nameLabel = new Label(Name.CLASS_SELECTOR_NAME_INPUT_LABEL);
    private final TextField nameInput = new TextField();

    // ปุ่มต่างๆบนหน้า Class Selector
    private final Button confirmButton = new Button(Name.CLASS_SELECTOR_CONFIRM_BUTTON);
    private final Button backButton = new Button(Name.CLASS_SELECTOR_BACK_BUTTON);

    // Constructor สำหรับ ClassSelectorUI
    public ClassSelectorUI() {
        setupLayout();
    }

    // Method สำหรับสร้างและตั้งค่า UI ของ Class Selector
    private void setupLayout() {

        // ตั้งค่า Layout ของ UI
        rootPane.setCenter(centerPane);
        centerPane.getChildren().addAll(classSelectorPane, inputPane, buttonsPane);
        inputPane.getChildren().addAll(nameLabel, nameInput);
        buttonsPane.getChildren().addAll(backButton, confirmButton);

        // ตั้งค่าขนาดของ UI
        // โดยใช้การ bind ขนาดของ Child Pane กับขนาดของ Parent Pane
        // เพื่อให้ UI ปรับขนาดสัมพันธ์กับขนาดของหน้าจอ
        centerPane.prefWidthProperty().bind(rootPane.widthProperty().multiply(0.7)); // 70%
        centerPane.prefHeightProperty().bind(rootPane.heightProperty().multiply(0.7)); // 70%
        classSelectorPane.prefWidthProperty().bind(centerPane.widthProperty()); // 100%
        classSelectorPane.prefHeightProperty().bind(centerPane.heightProperty().multiply(0.8)); // 80%
        inputPane.prefWidthProperty().bind(centerPane.widthProperty()); // 100%
        inputPane.prefHeightProperty().bind(centerPane.heightProperty().multiply(0.1)); // 10%
        buttonsPane.prefWidthProperty().bind(centerPane.widthProperty()); // 100%
        buttonsPane.prefHeightProperty().bind(centerPane.heightProperty().multiply(0.1)); // 10%

        // ตั้งค่าการจัดตำแหน่งของ UI
        inputPane.setAlignment(Pos.CENTER);
        centerPane.setAlignment(Pos.CENTER);
        buttonsPane.setAlignment(Pos.CENTER);
        classSelectorPane.setAlignment(Pos.CENTER);

        // ตั้งค่า Style เบื้องต้นของ UI
        rootPane.setStyle(Style.CLASS_SELECTOR_BACKGROUND_COLOR);
        inputPane.setSpacing(20);
        buttonsPane.setSpacing(20);
        BorderPane.setMargin(centerPane, new Insets(50));
    }

    // ============================================================
    // ------ Getter สำหรับเข้าถึง Object ต่างๆใน ClassSelectorUI ------
    // ============================================================
    public BorderPane getRootPane() {
        return rootPane;
    }

    public VBox getCenterPane() {
        return centerPane;
    }

    public HBox getClassSelectorPane() {
        return classSelectorPane;
    }

    public HBox getInputPane() {
        return inputPane;
    }

    public HBox getButtonsPane() {
        return buttonsPane;
    }

    public Label getNameLabel() {
        return nameLabel;
    }

    public TextField getNameInput() {
        return nameInput;
    }

    public Button getConfirmButton() {
        return confirmButton;
    }

    public Button getBackButton() {
        return backButton;
    }

    // ============================================================
}
