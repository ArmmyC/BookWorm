package cpe112.finalproject.Layout;

import cpe112.finalproject.Constants.Path;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

/*
 * CombatUI.java
 * สำหรับสร้าง UI ของหน้า Combat หรือหน้าต่อสู้
 */

public class CombatUI {

    // JavaFX Components ของ CombatUI
    private final StackPane rootPane = new StackPane();
    private final HBox avatarPane = new HBox();
    private final BorderPane statusPane = new BorderPane();
    private final StackPane enemyCount = new StackPane();
    private final StackPane wordFormed = new StackPane();
    private final StackPane backgroundPane = new StackPane();
    private final HBox combatAttPane = new HBox();
    private final HBox statusAttPane = new HBox();
    private final VBox playerStatus = new VBox();
    private final VBox enemyStatus = new VBox();
    private final HBox playerStats = new HBox();
    private final HBox enemyStats = new HBox();
    private final StackPane playerAvatar = new StackPane();
    private final StackPane enemyAvatar = new StackPane();
    private final Region spacer = new Region();

    // Constructor สำหรับ CombatUI
    public CombatUI() {
        setupLayout();
    }

    // Method สำหรับตั้งค่า Layout ของ CombatUI
    private void setupLayout() {

        // ตั้งค่า Layout ของ UI
        rootPane.getChildren().addAll(backgroundPane, avatarPane, statusPane, enemyCount, wordFormed);
        statusPane.setTop(combatAttPane);
        statusPane.setBottom(statusAttPane);
        avatarPane.getChildren().addAll(playerAvatar, enemyAvatar);
        statusAttPane.getChildren().addAll(playerStatus, spacer, enemyStatus);
        combatAttPane.getChildren().addAll(playerStats, enemyStats);

        // สร้าง Background Image สำหรับ UI
        Image backgroundImage = new Image(getClass().getResource(Path.BACKGROUND_PATH).toExternalForm());
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(1, 1, true, true, false, false));
        backgroundPane.setBackground(new Background(bgImage));

        VBox.setVgrow(rootPane, Priority.ALWAYS); // ทำให้ rootPane ขยายเต็มพื้นที่
        HBox.setHgrow(spacer, Priority.ALWAYS); // ทำให้ spacer ขยายเต็มพื้นที่

        // ตั้งค่าขนาดและการจัดเรียงตำแหน่งของ UI
        avatarPane.prefHeightProperty().bind(rootPane.heightProperty());
        avatarPane.prefWidthProperty().bind(rootPane.widthProperty());

        statusPane.prefHeightProperty().bind(rootPane.heightProperty());
        statusPane.prefWidthProperty().bind(rootPane.widthProperty());
        StackPane.setAlignment(statusPane, Pos.BOTTOM_CENTER);

        combatAttPane.prefHeightProperty().bind(rootPane.heightProperty().multiply(0.08));
        combatAttPane.prefWidthProperty().bind(rootPane.widthProperty());

        statusAttPane.prefHeightProperty().bind(rootPane.heightProperty().multiply(0.2));
        statusAttPane.prefWidthProperty().bind(rootPane.widthProperty());

        playerAvatar.prefWidthProperty().bind(avatarPane.widthProperty().multiply(0.5));
        playerAvatar.prefHeightProperty().bind(avatarPane.heightProperty());
        playerAvatar.setAlignment(Pos.BOTTOM_LEFT);

        playerStats.prefWidthProperty().bind(statusAttPane.widthProperty().multiply(0.5));
        playerStats.prefHeightProperty().bind(statusAttPane.heightProperty());
        playerStats.setAlignment(Pos.CENTER);
        playerStats.setSpacing(20);

        playerStatus.prefWidthProperty().bind(statusAttPane.widthProperty().multiply(0.4));
        playerStatus.prefHeightProperty().bind(statusAttPane.heightProperty());
        playerStatus.setAlignment(Pos.BOTTOM_LEFT);

        enemyStatus.prefWidthProperty().bind(statusAttPane.widthProperty().multiply(0.4));
        enemyStatus.prefHeightProperty().bind(statusAttPane.heightProperty());
        enemyStatus.setAlignment(Pos.BOTTOM_RIGHT);

        enemyStats.prefWidthProperty().bind(statusAttPane.widthProperty().multiply(0.5));
        enemyStats.prefHeightProperty().bind(statusAttPane.heightProperty());
        enemyStats.setAlignment(Pos.CENTER);
        enemyStats.setSpacing(20);

        enemyAvatar.prefWidthProperty().bind(avatarPane.widthProperty().multiply(0.5));
        enemyAvatar.prefHeightProperty().bind(avatarPane.heightProperty());
        enemyAvatar.setAlignment(Pos.BOTTOM_RIGHT);

        enemyCount.maxHeightProperty().bind(rootPane.heightProperty().multiply(0.1));
        enemyCount.maxWidthProperty().bind(rootPane.widthProperty().multiply(0.1));
        StackPane.setAlignment(enemyCount, Pos.TOP_CENTER);

        combatAttPane.setStyle("-fx-background-color:rgb(25, 25, 25); ");
    }

    // ============================================================
    // -------------- Getter สำหรับการเข้าถึง Object ต่างๆ -------------
    // ============================================================
    public StackPane getRootPane() {
        return rootPane;
    }

    public StackPane getBackgroundPane() {
        return backgroundPane;
    }

    public HBox getCombatAttPane() {
        return combatAttPane;
    }

    public HBox getStatusAttPane() {
        return statusAttPane;
    }

    public HBox getAvatarPane() {
        return avatarPane;
    }

    public VBox getPlayerStatus() {
        return playerStatus;
    }

    public VBox getEnemyStatus() {
        return enemyStatus;
    }

    public HBox getPlayerStats() {
        return playerStats;
    }

    public HBox getEnemyStats() {
        return enemyStats;
    }

    public StackPane getPlayerAvatar() {
        return playerAvatar;
    }

    public StackPane getEnemyAvatar() {
        return enemyAvatar;
    }

    public StackPane getEnemyCount() {
        return enemyCount;
    }

    public StackPane getWordFormed() {
        return wordFormed;
    }
    // ============================================================
}
