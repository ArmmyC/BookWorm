package cpe112.finalproject.Layout;

import cpe112.finalproject.Constants.Fonts;
import cpe112.finalproject.Constants.Name;
import cpe112.finalproject.Constants.Style;
import cpe112.finalproject.Handlers.KeyEventHandler;
import cpe112.finalproject.Managers.EnemyManager;
import cpe112.finalproject.Managers.SceneManager;
import cpe112.finalproject.Managers.SoundManager;
import cpe112.finalproject.Responsives.ResponsiveButton;
import cpe112.finalproject.Scenes.MainMenuScene;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
 * SideMenuUI.java
 * สำหรับสร้าง UI ของ Side Menu บน GameUI
 */

public class SideMenuUI {

    DevUI devUI = new DevUI();

    // JavaFX Components ของ SideMenuUI
    private final StackPane rootPane = new StackPane();
    private final HBox overlayPane = new HBox();
    private final VBox menuPane = new VBox();
    private final Region overlayBackground = new Region();

    // ปุ่ม Toggle สำหรับเปิด/ปิด Side Menu
    private final Button menuToggleButton = new Button("\u2630");

    // ปุ่มต่างๆใน Side Menu
    private final Button fullscreenButton = new Button(Name.FULLSCREEN_BUTTON);
    private final Button backButton = new Button("Back to Menu");
    private final Button exitButton = new Button("Exit");
    private final Button DeveloperButton = new Button("Developer Mode");

    // Boolean สำหรับเช็คว่า Side Menu เปิดอยู่หรือไม่
    private boolean isMenuOpen = false;

    // Constructor สำหรับ SideMenuUI
    public SideMenuUI() {
        initialize();
    }

    // Method สำหรับสร้างและตั้งค่า UI ของ Side Menu
    private void initialize() {
        setupLayout();
        setupMenuToggleButton();
        setupSideMenuButton(fullscreenButton);
        setupSideMenuButton(backButton);
        setupSideMenuButton(exitButton);
        setupSideMenuButton(DeveloperButton);
        setupExitButton();
        setupBackButton();
        setupFullscreenButton(SceneManager.getInstance().getStage());
        setupDeveloperButton();
    }

    // Method สำหรับสร้างและตั้งค่า UI ของ Side Menu

    private void setupLayout() {
        // ตั้งค่า Layout ของ UI
        rootPane.getChildren().addAll(overlayBackground, overlayPane);
        overlayPane.getChildren().add(menuPane);
        menuPane.getChildren().addAll(fullscreenButton, backButton, exitButton, DeveloperButton, menuToggleButton);

        // ตั้งค่าขนาดของ UI
        overlayPane.prefWidthProperty().bind(rootPane.widthProperty());
        overlayPane.prefHeightProperty().bind(rootPane.heightProperty());
        menuPane.prefHeightProperty().bind(overlayPane.heightProperty());
        menuPane.prefWidthProperty().bind(overlayPane.widthProperty().multiply(0.3));

        // ตั้งค่าการจัดตำแหน่งของ UI
        overlayPane.setAlignment(Pos.CENTER_LEFT);
        menuPane.setAlignment(Pos.CENTER);
        StackPane.setAlignment(menuToggleButton, Pos.TOP_LEFT);

        // ตั้งค่า Style เบื้องต้นของ UI
        menuPane.setStyle("-fx-background-color: rgba(19, 19, 19, 0.8);");

        // ตั้งค่าให้ menuPane ขยับไปทางซ้าย (ซ่อน)
        overlayPane.widthProperty().addListener((obs, oldVal, newVal) -> {
            if (!isMenuOpen) {
                menuPane.setTranslateX(-newVal.doubleValue() * 0.3);
            }
        });

        rootPane.setMouseTransparent(true);
        setupOverlayBackground();
    }

    // Method สำหรับตั้งค่า Background ของ Overlay
    private void setupOverlayBackground() {
        overlayBackground.setStyle("-fx-background-color: rgb(0,0,0);");
        overlayBackground.setOpacity(0);
        overlayBackground.setMouseTransparent(true);
    }

    // Method สำหรับตั้งค่า Toggle Button ของ Side Menu
    private void setupMenuToggleButton() {

        // ตั้งค่า Style ของ Toggle Button
        menuToggleButton.setStyle(Style.TOGGLE_BUTTON_STYLE);
        menuToggleButton.setOnMouseEntered(e -> menuToggleButton.setStyle(Style.TOGGLE_BUTTON_HOVER_STYLE));
        menuToggleButton.setOnMouseExited(e -> menuToggleButton.setStyle(Style.TOGGLE_BUTTON_STYLE));

        // ตั้งค่า Event เมื่อคลิก Toggle Button
        menuToggleButton.setOnAction(event -> toggleSideMenu());

        // ตั้งค่าให้ Escape Key สามารถเปิด ปิด Side Menu ได้
        menuToggleButton.sceneProperty().addListener((obs, oldScene, newScene) -> {
            if (newScene != null) {
                newScene.setOnKeyPressed(event -> {
                    KeyEventHandler.escapeKey(event, this::toggleSideMenu);
                });
            }
        });

        // ต้้งค่าให้ Toggle Button มีขนาดที่เหมาะสมกับหน้าจอ
        overlayPane.widthProperty()
                .addListener((obs, oldVal, newVal) -> ResponsiveButton.updateButton(menuToggleButton,
                        newVal.doubleValue(), overlayPane.getHeight(), 40, 80));
        overlayPane.heightProperty().addListener(
                (obs, oldVal, newVal) -> ResponsiveButton.updateButton(menuToggleButton, overlayPane.getWidth(),
                        newVal.doubleValue(), 40, 80));
    }

    // Method สำหรับตั้งค่า Side Menu Button
    private void setupSideMenuButton(Button button) {

        // ตั้งค่า Style ของ Side Menu Button
        button.setStyle(Style.SIDEMENU_BUTTON_STYLE);
        button.setOnMouseEntered(e -> button.setStyle(Style.SIDEMENU_BUTTON_HOVER_STYLE));
        button.setOnMouseExited(e -> button.setStyle(Style.SIDEMENU_BUTTON_STYLE));

        // ตั้งค่าขนาดของ Side Menu Button
        button.prefHeightProperty().bind(menuPane.heightProperty().divide(10));
        button.prefWidthProperty().bind(menuPane.widthProperty());

        // ตั้งค่าการจัดตำแหน่งของ Side Menu Button
        button.setAlignment(Pos.CENTER);

        // ตั้งค่าขนาดของ font และ padding ของ Side Menu Button
        overlayPane.widthProperty()
                .addListener((obs, oldVal, newVal) -> ResponsiveButton.updateButton(button,
                        newVal.doubleValue(), overlayPane.getHeight(), 40, 80, Fonts.NEXORA));
        overlayPane.heightProperty().addListener(
                (obs, oldVal, newVal) -> ResponsiveButton.updateButton(button,
                        overlayPane.getWidth(), newVal.doubleValue(), 40, 80, Fonts.NEXORA));
    }

    // Method สำหรับเปิด/ปิด Side Menu
    private void toggleSideMenu() {

        // คำนวณตำแหน่งที่ Side Menu จะเลื่อนเข้าไปหรือออกมา
        double targetX = isMenuOpen ? -menuPane.getWidth() : 0;

        // ตั้งค่าให้ rootPane สามารถคลิกได้เมื่อ Side Menu เปิดอยู่
        rootPane.setMouseTransparent(isMenuOpen);

        // ตั้งค่า Opacity ของ Overlay Background
        // ถ้า Side Menu เปิดอยู่ จะ fade จาก 0.5 ไป 0.0
        // ถ้า Side Menu ปิดอยู่ จะ fade จาก 0.0 ไป 0.5
        double fromOpacity = isMenuOpen ? 0.5 : 0.0;
        double toOpacity = isMenuOpen ? 0.0 : 0.5;

        if (isMenuOpen) {
            // ถ้า Side Menu ปิดอยู่ จะหยุดการโจมตีของศัตรู
            EnemyManager.getInstance().stopEnemyAttackLoop();
        } else {
            // ถ้า Side Menu เปิดอยู่ จะเริ่มการโจมตีของศัตรู
            EnemyManager.getInstance().startEnemyAttackLoop();
        }
        EnemyManager.getInstance().startEnemyAttackLoop();

        // ตั้งค่า Fade Transition สำหรับ Overlay Background
        FadeTransition fade = new FadeTransition(Duration.millis(300), overlayBackground);
        fade.setFromValue(fromOpacity);
        fade.setToValue(toOpacity);
        fade.play();

        // ตั้งค่า Timeline สำหรับ transition เลื่อน Side Menu
        Timeline timeline = new Timeline();
        KeyValue keyValue = new KeyValue(menuPane.translateXProperty(), targetX, Interpolator.EASE_BOTH);
        KeyFrame keyFrame = new KeyFrame(Duration.millis(300), keyValue);
        timeline.getKeyFrames().add(keyFrame);
        timeline.play();

        // เปลี่ยนค่า isMenuOpen จากเปิดเป็นปิด หรือจากปิดเป็นเปิด
        isMenuOpen = !isMenuOpen;
    }

    // Method สำหรับตั้งค่า Fullscreen Button
    private void setupFullscreenButton(Stage stage) {

        // ตั้งค่าให้ปุ่ม ScreenButton ปรับหน้าจอเป็น Fullscreen หรือ Windowed เมื่อกด
        fullscreenButton.setOnAction(e -> {
            stage.setFullScreen(!stage.isFullScreen());
        });

        // ตั้งค่าให้ปุ่ม ScreenButton แสดงข้อความ "Fullscreen" หรือ "Windowed"
        // ใช้ listener เช็คสถานะของหน้าจอ (FullScreen หรือ Windowed)
        // ถ้าเป็น FullScreen จะให้แสดงข้อความ "Windowed"
        // ถ้าเป็น Windowed จะให้แสดงข้อความ "Fullscreen"
        stage.fullScreenProperty().addListener((obs, wasFullScreen, isNowFullScreen) -> {
            if (isNowFullScreen) {
                fullscreenButton.setText("Windowed");
            } else {
                fullscreenButton.setText("Fullscreen");
            }
            SoundManager.playClickSound();
        });
    }

    // Method สำหรับตั้งค่า Exit Button
    private void setupExitButton() {
        exitButton.setOnAction(e -> {
            SoundManager.playClickSound();
            SceneManager.getInstance().getStage().close();
        });
    }

    // Method สำหรับตั้งค่า Back Button
    private void setupBackButton() {
        backButton.setOnAction(e -> {
            SoundManager.playClickSound();
            MainMenuScene mainMenuScene = new MainMenuScene();
            SceneManager.getInstance().switchRoot(mainMenuScene.getRootPane());
        });
    }

    // Method สำหรับตั้งค่า Developer Button
    private void setupDeveloperButton() {
        SoundManager.playClickSound();
        DeveloperButton.setOnAction(e -> devUI.showDevWindow());
    }

    // ============================================================
    // -------------- Getter สำหรับการเข้าถึง Object ต่างๆ -------------
    // ============================================================
    public StackPane getRootPane() {
        return rootPane;
    }

    public Button getMenuToggleButton() {
        return menuToggleButton;
    }
    // ============================================================
}
