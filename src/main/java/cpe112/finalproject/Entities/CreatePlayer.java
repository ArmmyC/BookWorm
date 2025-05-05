package cpe112.finalproject.Entities;

import cpe112.finalproject.Constants.Path;
import cpe112.finalproject.Constants.Style;
import cpe112.finalproject.Layout.CombatUI;
import cpe112.finalproject.Layout.GameUI;
import cpe112.finalproject.Logic.HealthControllerLogic;
import cpe112.finalproject.Managers.PlayerManager;
import cpe112.finalproject.Responsives.ResponsiveLabel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/*
 * CreatePlayer.java
 * สำหรับสร้าง Player ในเกม
 */

public class CreatePlayer {

        CombatUI combatUI; // CombatUI ที่จะใช้ในการแสดงผล

        private final String name; // ชื่อของ Player
        private final String characterClass; // คลาสของ Player
        private final double health; // ค่าพลังชีวิตของ Player
        private final double attackPower; // ค่าพลังโจมตีของ Player
        private final double defensePower; // ค่าพลังป้องกันของ Player

        // Constructor สำหรับสร้าง Player
        public CreatePlayer(GameUI ui) {
                this.combatUI = ui.getCombatUI();

                // ดึงค่าต่างๆจาก PlayerManager
                PlayerManager player = PlayerManager.getInstance();
                this.name = player.getPlayerName();
                this.characterClass = player.getClassName();
                this.health = player.getHealth();
                this.attackPower = player.getAttack();
                this.defensePower = player.getDefense();

                initialize();
        }

        // Method สำหรับตั้งค่า Player
        private void initialize() {
                setupPlayerHealth();
                setupPlayerAvatar();
                setupPlayerStats();
        }

        // Method สำหรับตั้งค่า Player Health Bar
        private void setupPlayerHealth() {

                // สร้าง JavaFX Components สำหรับ Health Bar
                StackPane HealthBar = new StackPane();
                Label playerName = new Label(name);
                Region background = new Region();
                Region foreground = new Region();
                Label healthLabel = new Label(String.valueOf((int) health));

                // ตั้งค่า Style ต่างๆ
                playerName.setStyle(Style.NAME_LABEL_STYLE);
                background.setStyle(Style.PLAYER_HEALTHBAR_BACKGROUND_STYLE);
                foreground.setStyle(Style.PLAYER_HEALTHBAR_FOREGROUND_STYLE);
                healthLabel.setStyle(Style.HEALTH_LABEL_STYLE);

                // ตั้งค่าขนาดของ Component ต่างๆ
                combatUI.getRootPane().widthProperty()
                                .addListener((obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(playerName,
                                                newVal.doubleValue(), combatUI.getRootPane().getHeight(), 15, 90,
                                                Style.NAME_LABEL_STYLE));
                combatUI.getRootPane().heightProperty()
                                .addListener((obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(playerName,
                                                combatUI.getRootPane().getWidth(), newVal.doubleValue(), 15, 90,
                                                Style.NAME_LABEL_STYLE));
                combatUI.getRootPane().widthProperty()
                                .addListener((obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(healthLabel,
                                                newVal.doubleValue(), combatUI.getRootPane().getHeight(), 15, 90,
                                                Style.HEALTH_LABEL_STYLE));
                combatUI.getRootPane().heightProperty()
                                .addListener((obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(healthLabel,
                                                combatUI.getRootPane().getWidth(), newVal.doubleValue(),
                                                15, 90, Style.HEALTH_LABEL_STYLE));

                // ตั้งค่า Layout
                HealthBar.getChildren().addAll(background, foreground, healthLabel);
                combatUI.getPlayerStatus().getChildren().addAll(playerName, HealthBar);
                HealthBar.setAlignment(Pos.CENTER_LEFT);

                // สร้าง HealthControllerLogic สำหรับ Player
                HealthControllerLogic playerHealthController = new HealthControllerLogic(
                                health,
                                defensePower,
                                HealthBar,
                                foreground,
                                healthLabel);

                // ตั้งค่า playerHealthController ใน HealthControllerLogic
                // เพื่อให้สามารถเข้าถึง playerHealthController ได้จากที่อื่นๆ
                HealthControllerLogic.setPlayerHealthController(playerHealthController);

        }

        // Method สำหรับตั้งค่า Player Stats
        private void setupPlayerStats() {
                // สร้าง JavaFX Components สำหรับ Player Stats
                Label classLabel = new Label(" Class: " + characterClass);
                Label attackLabel = new Label(" Attack: " + attackPower);
                Label defenseLabel = new Label(" Defense: " + defensePower);

                // ตั้งค่า Style ต่างๆ
                classLabel.setStyle(Style.CLASS_LABEL_STYLE);
                attackLabel.setStyle(Style.ATTACK_LABEL_STYLE);
                defenseLabel.setStyle(Style.DEFENSE_LABEL_STYLE);

                // ตั้งค่าให้ Label ปรับขนาดตามขนาดของหน้าจอ
                setupResponsive(classLabel, Style.CLASS_LABEL_STYLE);
                setupResponsive(attackLabel, Style.ATTACK_LABEL_STYLE);
                setupResponsive(defenseLabel, Style.DEFENSE_LABEL_STYLE);

                combatUI.getPlayerStats().getChildren().addAll(classLabel, attackLabel, defenseLabel);
        }

        // Method สำหรับตั้งค่า Player Avatar (ภาพของ Player)
        private void setupPlayerAvatar() {
                ImageView AvatarIMG = new ImageView(getClass().getResource(Path.PLAYER_IMAGE).toExternalForm());
                AvatarIMG.fitWidthProperty().bind(combatUI.getPlayerAvatar().widthProperty().multiply(0.6));
                AvatarIMG.fitHeightProperty().bind(combatUI.getPlayerAvatar().heightProperty().multiply(0.8));
                combatUI.getPlayerAvatar().getChildren().add(AvatarIMG);
        }

        // Method สำหรับตั้งค่า Responsive Label
        // โดยจะทำการอัพเดท Label ให้มีขนาดและตำแหน่งที่เหมาะสมกับขนาดของหน้าจอ
        private void setupResponsive(Label label, String style) {
                ResponsiveLabel.updateLabel(label, combatUI.getRootPane().getWidth(),
                                combatUI.getRootPane().getHeight(), 20, 90, style);
                combatUI.getRootPane().widthProperty()
                                .addListener((obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(label,
                                                newVal.doubleValue(), combatUI.getRootPane().getHeight(), 20, 90,
                                                style));
                combatUI.getRootPane().heightProperty()
                                .addListener((obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(label,
                                                combatUI.getRootPane().getWidth(), newVal.doubleValue(), 20, 90,
                                                style));
        }
}