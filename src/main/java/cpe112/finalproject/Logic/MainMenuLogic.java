package cpe112.finalproject.Logic;

import cpe112.finalproject.Constants.Fonts;
import cpe112.finalproject.Constants.Style;
import cpe112.finalproject.Layout.MainMenuUI;
import cpe112.finalproject.Managers.SceneManager;
import cpe112.finalproject.Managers.SoundManager;
import cpe112.finalproject.Responsives.ResponsiveButton;
import cpe112.finalproject.Responsives.ResponsiveLabel;
import cpe112.finalproject.Scenes.ClassSelectorScene;
import javafx.animation.ScaleTransition;
import javafx.application.Platform;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
 * MainMenuLogic.java
 * สำหรับจัดการ Logic ของ Main Menu
 */

public class MainMenuLogic {

    // ตัวแปร ui สำหรับเก็บ MainMenuUI
    private final MainMenuUI ui;

    // Constructor สำหรับ MainMenuLogic
    public MainMenuLogic(MainMenuUI ui) {
        this.ui = ui;
    }

    // Method สำหรับการเริ่มต้นการทำงานของ MainMenuLogic
    public void initialize() {
        setupTitle();
        setupVolumeSlider();
        setupBackgroundSound();
        setupButton();
        setupFullscreenButton(SceneManager.getInstance().getStage());
    }

    // Method สำหรับการตั้งค่าเสียงเพลง Background
    private void setupBackgroundSound() {
        SoundManager.setupVolumeSlider(ui.getVolumeSlider());
    }

    // Method สำหรับการตั้งค่า Title ของเกม
    private void setupTitle() {
        // ตั้งค่าขนาดและสไตล์ของ Title
        ui.getTitleLabel().setFont(Fonts.KNIGHTWARRIOR);
        ui.getTitleLabel().setStyle(Style.TITLE_LABEL_STYLE);

        // ตั้งค่าขนาด Title ให้มีความสัมพันธ์กับทั้งความกว้างและความสูงของหน้าจอ
        ui.getRootPane().widthProperty()
                .addListener((obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(ui.getTitleLabel(),
                        newVal.doubleValue(), ui.getRootPane().getHeight(), 8, Fonts.KNIGHTWARRIOR));
        ui.getRootPane().heightProperty()
                .addListener((obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(ui.getTitleLabel(),
                        ui.getRootPane().getWidth(), newVal.doubleValue(), 8, Fonts.KNIGHTWARRIOR));
    }

    // Method สำหรับการตั้งค่าปุ่ม getFullscreenButton() สำหรับปรับหน้าจอ
    // Fullscreen/Windowed
    private void setupFullscreenButton(Stage stage) {
        // ตั้งค่าขนาดและสไตล์ของปุ่ม ScreenButton
        ui.getFullscreenButton().setPrefWidth(100);
        ui.getFullscreenButton().setStyle(Style.FULLSCREEN_BUTTON_STYLE);

        // ตั้งค่า Hover Effect
        ui.getFullscreenButton()
                .setOnMouseEntered(e -> ui.getFullscreenButton().setStyle(Style.FULLSCREEN_BUTTON_HOVER_STYLE));
        ui.getFullscreenButton()
                .setOnMouseExited(e -> ui.getFullscreenButton().setStyle(Style.FULLSCREEN_BUTTON_STYLE));

        // ตั้งค่าขนาดปุ่ม Fullscreen
        // ให้มีความสัมพันธ์กับทั้งความกว้างและความสูงของหน้าจอ
        ui.getRootPane().widthProperty()
                .addListener((obs, oldVal, newVal) -> ResponsiveButton.updateButton(ui.getFullscreenButton(),
                        newVal.doubleValue(), ui.getRootPane().getHeight(), 50, 200));
        ui.getRootPane().heightProperty()
                .addListener((obs, oldVal, newVal) -> ResponsiveButton.updateButton(ui.getFullscreenButton(),
                        ui.getRootPane().getWidth(), newVal.doubleValue(), 50, 200));

        // ตั้งค่าให้ปุ่ม ScreenButton ปรับหน้าจอเป็น Fullscreen หรือ Windowed เมื่อกด
        ui.getFullscreenButton().setOnAction(e -> {
            stage.setFullScreen(!stage.isFullScreen());
        });

        // ตั้งค่าให้ปุ่ม ScreenButton แสดงข้อความ "Fullscreen" หรือ "Windowed"
        // ใช้ listener เช็คสถานะของหน้าจอ (FullScreen หรือ Windowed)
        // ถ้าเป็น FullScreen จะให้แสดงข้อความ "Windowed"
        // ถ้าเป็น Windowed จะให้แสดงข้อความ "Fullscreen"
        stage.fullScreenProperty().addListener((obs, wasFullScreen, isNowFullScreen) -> {
            if (isNowFullScreen) {
                ui.getFullscreenButton().setText("Windowed");
            } else {
                ui.getFullscreenButton().setText("Fullscreen");
            }
        });
    }

    // Method สำหรับการตั้งค่า Slider สำหรับปรับเสียงเพลง
    // โดยใช้ Slider ที่สร้างขึ้นในตัวแปร getVolumeSlider()
    private void setupVolumeSlider() {
        // ตั้งค่าสไตล์ของตัวอักษร SliderLabel
        ui.getSliderLabel().setStyle(Style.SLIDER_LABEL_STYLE);

        // ตั้งค่าสไตล์ของ Slider
        ui.getVolumeSlider().setStyle(Style.SLIDER_STYLE);

        // ตั้งค่า Hover Effect ของ Slider
        ui.getVolumeSlider().setOnMouseEntered(e -> {
            ui.getVolumeSlider().setStyle(Style.SLIDER_HOVER_STYLE);
        });
        ui.getVolumeSlider().setOnMouseExited(e -> {
            ui.getVolumeSlider().setStyle(Style.SLIDER_STYLE);
        });

        ui.getRootPane().widthProperty()
                .addListener((obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(ui.getSliderLabel(),
                        newVal.doubleValue(), ui.getRootPane().getHeight(), 50, 100));
        ui.getRootPane().heightProperty()
                .addListener((obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(ui.getSliderLabel(),
                        ui.getRootPane().getWidth(), newVal.doubleValue(), 50, 100));
    }

    // Method สำหรับการตั้งค่าปุ่ม Menu ต่างๆ
    private void setupButton() {
        // ตั้งค่าการทำงานของปุ่ม Menu ต่างๆ เมื่อถูกคลิก
        ui.getPlayButton().setOnAction(e -> playButtonAction());
        ui.getExitButton().setOnAction(e -> exitButtonAction());
        // ตั้งค่า Hover Effect และ Responsive ของปุ่ม Menu ต่างๆ
        setupHoverAndResponsive(ui.getPlayButton());
        setupHoverAndResponsive(ui.getExitButton());
    }

    // Method สำหรับการตั้งค่า Hover และ Responsive ของปุ่ม Menu
    // สำหรับการใช้ซ้ำในปุ่ม Menu อื่นๆ
    private void setupHoverAndResponsive(Button button) {
        // ตั้งค่าฟอนต์และสไตล์ของปุ่ม
        button.setFont(Fonts.NEXORA);
        button.setStyle(Style.MENU_BUTTON_STYLE);

        // ตั้งค่า Transition สำหรับการทำ Hover Effect
        ScaleTransition scaleIn = new ScaleTransition(Duration.millis(100), button);
        scaleIn.setToX(1.3);
        scaleIn.setToY(1.3);
        ScaleTransition scaleOut = new ScaleTransition(Duration.millis(100), button);
        scaleOut.setToX(1.0);
        scaleOut.setToY(1.0);

        // ตั้งค่าการทำงานเมื่อ mouse เลื่อนเข้ามาในปุ่มหรือเลื่อนออกจากปุ่ม
        // โดยใช้ ScaleTransition ที่สร้างขึ้น
        button.setOnMouseEntered((MouseEvent e) -> {
            scaleIn.playFromStart();
            SoundManager.playHoverSound();
        });
        button.setOnMouseExited(e -> {
            scaleOut.playFromStart();
        });

        // ตั้งค่าขนาด Button ให้มีความสัมพันธ์กับทั้งความกว้างและความสูงของ
        // getRootPane()
        // ใช้ Listener เช็คทั้งความกว้างและความสูงของ getRootPane()
        ui.getRootPane().widthProperty().addListener((obs, oldVal, newVal) -> ResponsiveButton.updateButton(button,
                newVal.doubleValue(), ui.getRootPane().getHeight(), 30, 80, Fonts.NEXORA));
        ui.getRootPane().heightProperty().addListener(
                (obs, oldVal, newVal) -> ResponsiveButton.updateButton(button,
                        ui.getRootPane().getWidth(),
                        newVal.doubleValue(), 30, 80, Fonts.NEXORA));
    }

    // Method สำหรับการตั้งค่าการทำงานของปุ่ม PLAY
    // โดยเมื่อกดปุ่ม PLAY จะทำการเรียกย้ายไปยังหน้าเลือกตัวละคร (ClassSelector)
    private void playButtonAction() {
        System.out.println("Play button clicked!");
        SoundManager.playClickSound();
        ClassSelectorScene classSelectorScene = new ClassSelectorScene();
        SceneManager.getInstance().switchRoot(classSelectorScene.getRootPane());
    }

    // Method สำหรับการตั้งค่าการทำงานของปุ่ม EXIT
    // โดยเมื่อกดปุ่ม EXIT จะทำการปิดโปรแกรม
    private void exitButtonAction() {
        System.out.println("Exit button clicked!");
        SoundManager.playClickSound();
        Platform.exit();
    }
}
