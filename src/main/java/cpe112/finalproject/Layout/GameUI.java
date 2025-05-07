package cpe112.finalproject.Layout;

import cpe112.finalproject.Entities.CreatePlayer;
import cpe112.finalproject.Handlers.MouseEventHandler;
import cpe112.finalproject.Logic.CombatLogic;
import cpe112.finalproject.Logic.PuzzleLogic;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/*
 * GameUI.java
 * สำหรับจัดการ Layout ของเกม
 */

public class GameUI {

    private static GameUI instance; // สร้าง Instance ของ GameUI

    // JavaFX Components ของ GameUI
    private final StackPane rootPane = new StackPane();
    private final VBox mainPane = new VBox();

    // สร้าง UI ของ Combat, Puzzle, SideMenu และ GameOver
    // และ Logic ของ Combat และ Puzzle
    // รวมถึง MouseEventHandler สำหรับจัดการเหตุการณ์ต่างๆ
    private final CombatUI combatUI = new CombatUI();
    private final SideMenuUI sideMenuUI = new SideMenuUI();
    private final GameOverUI gameOverUI = new GameOverUI();
    private final CombatLogic combatLogic = new CombatLogic(combatUI);
    private final PuzzleLogic puzzleLogic = new PuzzleLogic();
    private MouseEventHandler mouseEventHandler = new MouseEventHandler(puzzleLogic, combatLogic);
    private final PuzzleUI puzzleUI = new PuzzleUI(puzzleLogic, mouseEventHandler);

    // สร้างตัวแปรสำหรับกำหนดอัตราส่วนของพื้นที่ด้านบนและด้านล่าง
    private static final double TOP_AREA_HEIGHT_MULTIPLIER = 0.5;
    private static final double BOTTOM_AREA_HEIGHT_MULTIPLIER = 1 - TOP_AREA_HEIGHT_MULTIPLIER;

    // Constructor สำหรับ GameUI
    public GameUI() {
        instance = this;
        setupLayout();
    }

    // Method สำหรับสร้าง Player
    public void createPlayer() {
        new CreatePlayer(this);
    }

    // Method สำหรับสร้าง Layout ของ UI
    private void setupLayout() {

        // ตั้งค่า Layout ของ UI
        mainPane.getChildren().addAll(combatUI.getRootPane(), puzzleUI.getRootPane());
        rootPane.getChildren().addAll(mainPane, sideMenuUI.getRootPane(), sideMenuUI.getMenuToggleButton(),
                gameOverUI.getRootPane());

        // ตั้งค่าขนาดของ UI
        mainPane.prefHeightProperty().bind(rootPane.heightProperty());
        mainPane.prefWidthProperty().bind(rootPane.widthProperty());
        combatUI.getRootPane().prefHeightProperty()
                .bind(mainPane.heightProperty().multiply(TOP_AREA_HEIGHT_MULTIPLIER));
        combatUI.getRootPane().prefWidthProperty().bind(mainPane.widthProperty());
        puzzleUI.getRootPane().prefHeightProperty()
                .bind(mainPane.heightProperty().multiply(BOTTOM_AREA_HEIGHT_MULTIPLIER));
        puzzleUI.getRootPane().prefWidthProperty().bind(mainPane.widthProperty());
        gameOverUI.getRootPane().maxHeightProperty().bind(rootPane.heightProperty());
        gameOverUI.getRootPane().maxWidthProperty().bind(rootPane.widthProperty());

        rootPane.setStyle("-fx-background-color: rgb(25, 25, 25);"); // ตั้งค่าพื้นหลังของ UI
    }

    // ============================================================
    // -------------- Getter สำหรับการเข้าถึง Object ต่างๆ -------------
    // ============================================================
    public StackPane getRootPane() {
        return rootPane;
    }

    public VBox getMainPane() {
        return mainPane;
    }

    public CombatUI getCombatUI() {
        return combatUI;
    }

    public PuzzleUI getPuzzleUI() {
        return puzzleUI;
    }

    public SideMenuUI getSideMenuUI() {
        return sideMenuUI;
    }

    public StackPane getTopPane() {
        return combatUI.getRootPane();
    }

    public StackPane getBottomPane() {
        return puzzleUI.getRootPane();
    }

    public HBox getCombatAttPane() {
        return combatUI.getCombatAttPane();
    }

    public HBox getStatusAttPane() {
        return combatUI.getStatusAttPane();
    }

    public HBox getAvatarPane() {
        return combatUI.getAvatarPane();
    }

    public static GameUI getInstance() {
        return instance;
    }

    public GameOverUI getGameOverUI() {
        return gameOverUI;
    }
    // ============================================================

}
