package cpe112.finalproject.Logic;

import cpe112.finalproject.Constants.Fonts;
import cpe112.finalproject.Constants.Style;
import cpe112.finalproject.Layout.ClassSelectorUI;
import cpe112.finalproject.Managers.PlayerManager;
import cpe112.finalproject.Managers.SceneManager;
import cpe112.finalproject.Managers.SoundManager;
import cpe112.finalproject.Responsives.ResponsiveButton;
import cpe112.finalproject.Scenes.GameScene;
import cpe112.finalproject.Scenes.MainMenuScene;
import javafx.animation.ScaleTransition;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

/*
 * ClassSelectorLogic.java
 * สำหรับจัดการ Logic ของ Class Selector   
 */

public class ClassSelectorLogic {

    // สร้างคลาส PlayerClassStats สำหรับเก็บข้อมูลของคลาสผู้เล่น
    private static class PlayerClassStats {
        String className; // ชื่อคลาส
        double health, attack, defense; // ค่าพลังของคลาส

        // Constructor สำหรับสร้าง PlayerClassStats
        PlayerClassStats(String name, double hp, double atk, double def) {
            this.className = name;
            this.health = hp;
            this.attack = atk;
            this.defense = def;
        }
    }

    // ตัวแปร ui สำหรับเก็บ ClassSelectorUI
    private final ClassSelectorUI ui;

    // ตัวแปร selectedButton สำหรับเก็บปุ่มของคลาสที่ถูกเลือก
    private Button selectedButton = null;

    // ตัวแปร playerName สำหรับเก็บชื่อผู้เล่น
    private String playerName;

    // Constructor สำหรับ ClassSelectorLogic
    public ClassSelectorLogic(ClassSelectorUI ui) {
        this.ui = ui;
    }

    // Method สำหรับการเริ่มต้นการทำงานของ ClassSelectorLogic
    public void initialize() {
        setupInputField();
        setupClassButtons();
        setupButtons();
    }

    // Method สำหรับการตั้งค่า style ของ Input Field
    private void setupInputField() {
        ui.getNameLabel().setStyle(Style.CLASS_SELECTOR_NAME_INPUT_STYLE);
        ui.getNameInput().setStyle(Style.CLASS_SELECTOR_NAME_INPUT_STYLE);
    }

    // Method สำหรับการตั้งค่าปุ่ม Class Selector
    private void setupClassButtons() {
        ui.getClassSelectorPane().getChildren().addAll(
                createClassButton("Warrior", "A strong and brave fighter.", 150, 5, 10),
                createClassButton("Mage", "A master of the arcane arts.", 80, 20, 15),
                createClassButton("Rogue", "A stealthy and agile assassin.", 100, 25, 0));
    }

    // Method สำหรับการสร้างปุ่ม Class Selector
    private Button createClassButton(String className, String description, double health, double attack,
            double defense) {

        // สร้างชื่อคลาส, คำบรรยาย และ stat ของคลาส
        Label classLabel = new Label(className);
        Label descLabel = new Label(description);
        Label statsLabel = new Label(
                String.format("Health: %.0f\nAttack: %.0f\nDefense: %.0f", health, attack, defense));

        // ตั้งค่าสไตล์ของชื่อคลาส, คำบรรยาย และ stat ของคลาส
        classLabel.setStyle(Style.CLASS_SELECTOR_CLASS_LABEL_STYLE);
        descLabel.setStyle(Style.CLASS_SELECTOR_DESC_LABEL_STYLE);
        statsLabel.setStyle(Style.CLASS_SELECTOR_STATS_LABEL_STYLE);

        descLabel.setWrapText(true);

        Region spacer = new Region();
        VBox.setVgrow(spacer, Priority.ALWAYS);

        // สร้าง VBox สำหรับจัดเรียงชื่อคลาส, คำบรรยาย และ stat ของคลาส
        VBox content = new VBox(10, classLabel, descLabel, spacer, statsLabel);
        content.setAlignment(Pos.TOP_LEFT);
        content.setPadding(new Insets(10));

        // สร้างปุ่มและตั้งค่าต่างๆ
        Button button = new Button();
        button.setGraphic(content); // ตั้งค่าภาพกราฟิกของปุ่มเป็น VBox ที่สร้างขึ้น
        button.setStyle(Style.CLASS_SELECTOR_CLASS_DEFAULT_STYLE); // ตั้งค่า style เริ่มต้นของปุ่ม

        // ตั้งค่าขนาดของปุ่มให้สัมพันธ์กับขนาดของหน้าจอ
        button.prefHeightProperty().bind(ui.getClassSelectorPane().heightProperty());

        // ตั้งค่าข้อมูลของปุ่มเป็น PlayerClassStats
        button.setUserData(new PlayerClassStats(className, health, attack, defense));

        // ตั้งค่าการทำงานเมื่อปุ่มถูกคลิก
        button.setOnAction((ActionEvent e) -> {
            SoundManager.playClassSelectSound();
            if (selectedButton != null)
                selectedButton.setStyle(Style.CLASS_SELECTOR_CLASS_DEFAULT_STYLE); // เปลี่ยนกลับไปใช้ style เริ่มต้น
            selectedButton = button;
            button.setStyle(Style.CLASS_SELECTOR_CLASS_SELECTED_STYLE); // เปลี่ยน style ของปุ่มที่ถูกเลือก
        });

        return button;
    }

    // Method สำหรับการตั้งค่าปุ่ม Confirm และ Back
    private void setupButtons() {

        // ตั้งค่าปุ่ม Confirm และ Back
        setResponsiveHover(ui.getConfirmButton(), Style.CLASS_SELECTOR_BUTTON_STYLE,
                Style.CLASS_SELECTOR_BUTTON_HOVER_STYLE);
        setResponsiveHover(ui.getBackButton(), Style.CLASS_SELECTOR_BUTTON_STYLE,
                Style.CLASS_SELECTOR_BUTTON_HOVER_STYLE);

        // ตั้งค่าการทำงานเมื่อปุ่ม Confirm และ Back ถูกคลิก
        ui.getConfirmButton().setOnAction(e -> confirmAction());
        ui.getBackButton().setOnAction(e -> backAction());
    }

    // Method สำหรับตั้งค่า Hover และ Responsive ของปุ่ม
    private void setResponsiveHover(Button button, String base, String hover) {
        button.setFont(Fonts.KNIGHTWARRIOR);
        button.setStyle(base);

        // ตั้งค่า Transition สำหรับ hover effect
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(100), button);
        scaleIn.setToX(1.1);
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(100), button);
        scaleOut.setToX(1.0);

        // ตั้งค่า Effect สำหรับปุ่ม
        button.setOnMouseEntered(e -> {
            scaleIn.playFromStart();
            button.setStyle(hover);
        });
        button.setOnMouseExited(e -> {
            scaleOut.playFromStart();
            button.setStyle(base);
        });

        // ตั้งค่าให้ตัวอักษรในปุ่มมีขนาดสัมพันธ์กับขนาดของหน้าจอ
        ui.getRootPane().widthProperty().addListener((obs, oldVal, newVal) -> ResponsiveButton.updateButton(button,
                newVal.doubleValue(), ui.getRootPane().getHeight(), 40, 80, Fonts.NEXORA));
        ui.getRootPane().heightProperty().addListener((obs, oldVal, newVal) -> ResponsiveButton.updateButton(button,
                ui.getRootPane().getWidth(), newVal.doubleValue(), 40, 80, Fonts.NEXORA));
    }

    // Method สำหรับการตั้งค่าการทำงานของปุ่ม Back
    private void backAction() {
        SoundManager.playClickSound();
        SceneManager.getInstance().switchRoot(new MainMenuScene().getRootPane());
    }

    // Method สำหรับการตั้งค่าการทำงานของปุ่ม Confirm
    private void confirmAction() {
        SoundManager.playClickSound();
        playerName = ui.getNameInput().getText();
        // ถ้าไม่มีการกรอกชื่อผู้เล่น หรือยังไม่ได้เลือกคลาส
        // ให้แสดงข้อความเตือนและเปลี่ยนสีของกรอบข้อความ
        if (playerName.isEmpty()) {
            SoundManager.playErrorSound();
            ui.getNameInput().setStyle(Style.CLASS_SELECTOR_NAME_INPUT_STYLE + "-fx-border-color: red;");
            ui.getNameLabel().setText("Please enter a name!");
        } else if (selectedButton == null) {
            SoundManager.playErrorSound();
            ui.getNameLabel().setText("Please select a class!");
            ui.getNameInput().setStyle(Style.CLASS_SELECTOR_NAME_INPUT_STYLE);
        } else {
            // ถ้าทั้งชื่อผู้เล่นและคลาสถูกกรอกครบถ้วน
            // ให้เก็บข้อมูลของผู้เล่นและเปลี่ยนไปยังหน้า GameScene
            PlayerClassStats stats = (PlayerClassStats) selectedButton.getUserData();
            PlayerManager.getInstance().setPlayerData(playerName, stats.className, stats.health, stats.attack,
                    stats.defense);
            SceneManager.getInstance().switchRoot(new GameScene().getRootPane());
        }
    }
}
