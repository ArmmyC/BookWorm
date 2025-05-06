package cpe112.finalproject.Layout;

import cpe112.finalproject.Constants.Name;
import cpe112.finalproject.Constants.Style;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/*
 * MainMenuUI.java
 * สำหรับสร้าง UI ของ Main Menu หรือหน้าเริ่มต้นของเกม
 */

public class MainMenuUI {

    private final TutorialUI tutorialUI = new TutorialUI(); // สร้าง TutorialUI

    // JavaFX Components ของ MainMenuUI
    private final StackPane rootPane = new StackPane();
    private final BorderPane borderPane = new BorderPane();
    private final VBox centerPane = new VBox();
    private final VBox titlePane = new VBox();
    private final VBox mainMenuPane = new VBox();
    private final HBox settingsPane = new HBox();
    private final HBox musicControl = new HBox();

    // Label สำหรับชื่อเกม
    private final Label titleLabel = new Label(Name.TITLE_NAME);

    // ปุ่มต่างๆบนหน้า Main Menu
    private final Button playButton = new Button(Name.PLAY_BUTTON);
    private final Button TutorialButton = new Button("How to Play");
    private final Button exitButton = new Button(Name.QUIT_BUTTON);

    // ปุ่มและ Slider สำหรับการตั้งค่าเสียงและโหมดเต็มหน้าจอ
    private final Button fullscreenButton = new Button(Name.FULLSCREEN_BUTTON);
    private final Label sliderLabel = new Label(Name.VOLUME_LABEL);
    private final Slider volumeSlider = new Slider(0, 0.5, 0.05);

    // Constructor สำหรับ MainMenuUI
    public MainMenuUI() {
        setupLayout();
    }

    // Method สำหรับสร้างและตั้งค่า UI ของ Main Menu
    private void setupLayout() {

        // ตั้งค่า Layout ของ UI
        rootPane.getChildren().addAll(borderPane, tutorialUI.getRootPane());
        borderPane.setCenter(centerPane);
        borderPane.setTop(settingsPane);
        centerPane.getChildren().addAll(titlePane, mainMenuPane);
        titlePane.getChildren().add(titleLabel);
        mainMenuPane.getChildren().addAll(playButton, TutorialButton, exitButton);
        settingsPane.getChildren().addAll(musicControl, fullscreenButton);
        musicControl.getChildren().addAll(sliderLabel, volumeSlider);

        // ตั้งค่าขนาดของ UI
        titlePane.prefHeightProperty().bind(borderPane.heightProperty().multiply(0.3)); // 30%
        mainMenuPane.prefHeightProperty().bind(borderPane.heightProperty().multiply(0.6)); // 60%
        settingsPane.prefHeightProperty().bind(borderPane.heightProperty().multiply(0.08)); // 8%
        playButton.prefWidthProperty().bind(mainMenuPane.widthProperty().multiply(0.3)); // 30%
        TutorialButton.prefWidthProperty().bind(mainMenuPane.widthProperty().multiply(0.3));
        exitButton.prefWidthProperty().bind(mainMenuPane.widthProperty().multiply(0.3)); // 30%
        tutorialUI.getRootPane().maxWidthProperty().bind(rootPane.widthProperty().multiply(0.7)); // 70%
        tutorialUI.getRootPane().maxHeightProperty().bind(rootPane.heightProperty().multiply(0.7)); // 70%

        // ตั้งค่าการจัดตำแหน่งของ UI
        titlePane.setAlignment(Pos.CENTER);
        mainMenuPane.setAlignment(Pos.TOP_CENTER);
        settingsPane.setAlignment(Pos.CENTER_RIGHT);
        musicControl.setAlignment(Pos.CENTER);

        // ตั้งค่า Style เบื้องต้นให้กับ UI
        borderPane.setStyle(Style.MAINMENU_BACKGROUND_COLOR);
        mainMenuPane.setStyle(Style.MAINMENU_SPACING);
        settingsPane.setStyle(Style.SETTINGS_SPACING);
    }

    // ============================================================
    // -------------- Getter สำหรับการเข้าถึง Object ต่างๆ -------------
    // ============================================================

    public StackPane getRootPane() {
        return rootPane;
    }

    public BorderPane getBorderPane() {
        return borderPane;
    }

    public VBox getCenterPane() {
        return centerPane;
    }

    public VBox getTitlePane() {
        return titlePane;
    }

    public VBox getMainMenuPane() {
        return mainMenuPane;
    }

    public HBox getSettingsPane() {
        return settingsPane;
    }

    public HBox getMusicControl() {
        return musicControl;
    }

    public Label getTitleLabel() {
        return titleLabel;
    }

    public Button getPlayButton() {
        return playButton;
    }

    public Button getExitButton() {
        return exitButton;
    }

    public Button getFullscreenButton() {
        return fullscreenButton;
    }

    public Button getTutorialButton() {
        return TutorialButton;
    }

    public Label getSliderLabel() {
        return sliderLabel;
    }

    public Slider getVolumeSlider() {
        return volumeSlider;
    }

    public TutorialUI getTutorialUI() {
        return tutorialUI;
    }
    // ============================================================
}
