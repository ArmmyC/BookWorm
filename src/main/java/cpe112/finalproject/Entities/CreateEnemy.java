package cpe112.finalproject.Entities;

import cpe112.finalproject.Constants.Style;
import cpe112.finalproject.Layout.CombatUI;
import cpe112.finalproject.Layout.GameUI;
import cpe112.finalproject.Logic.HealthControllerLogic;
import cpe112.finalproject.Managers.EnemyManager;
import cpe112.finalproject.Responsives.ResponsiveLabel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;

/*
 * CreateEnemy.java
 * สำหรับสร้าง Enemy ในเกม
 */

public class CreateEnemy {

        GameUI gameUI; // GameUI ที่จะใช้ในการแสดงผล
        CombatUI combatUI; // CombatUI ที่จะใช้ในการแสดงผล

        // Constructor สำหรับสร้าง Enemy
        public CreateEnemy(GameUI ui, String name, String characterClass, double health, double attackPower,
                        double defensePower, String imagePath) {
                this.gameUI = ui;
                this.combatUI = ui.getCombatUI();
                setupEnemy(name, characterClass, health, attackPower, defensePower, imagePath);
        }

        // Method สำหรับลบ UI ของ Enemy ก่อนหน้า
        private void removeEnemyUI() {
                combatUI.getEnemyStatus().getChildren().clear();
                combatUI.getEnemyStats().getChildren().clear();
                combatUI.getEnemyAvatar().getChildren().clear();
        }

        // Method สำหรับตั้งค่า Enemy Health Bar
        private void setupEnemy(String name, String characterClass, double health, double attack,
                        double defense, String imagePath) {
                removeEnemyUI(); // ลบ UI ของ Enemy ก่อนหน้า
                setupEnemyHealth(name, health, defense); // ตั้งค่า Health Bar
                setupEnemyAvatar(imagePath); // ตั้งค่า Avatar
                setupEnemyStats(characterClass, attack, defense); // ตั้งค่า Stats ของ Enemy
                EnemyManager.getInstance().startEnemyAttackLoop(); // เริ่มการโจมตีของ Enemy
        }

        // Method สำหรับตั้งค่า Enemy Avatar
        private void setupEnemyHealth(String name, double health, double defense) {

                // สร้าง JavaFX Components สำหรับ Health Bar
                StackPane HealthBar = new StackPane();
                Label enemyName = new Label(name);
                Region background = new Region();
                Region foreground = new Region();
                Label healthLabel = new Label(String.valueOf((int) health));

                // ตั้งค่า Style ต่างๆ
                enemyName.setStyle(Style.NAME_LABEL_STYLE);
                background.setStyle(Style.ENEMY_HEALTHBAR_BACKGROUND_STYLE);
                foreground.setStyle(Style.ENEMY_HEALTHBAR_FOREGROUND_STYLE);
                healthLabel.setStyle(Style.HEALTH_LABEL_STYLE);

                // ตั้งค่าให้ JavaFX Components ปรับขนาดตามขนาดของหน้าจอ
                ResponsiveLabel.updateLabel(enemyName, combatUI.getRootPane().getWidth(),
                                combatUI.getRootPane().getHeight(), 15, 90, Style.NAME_LABEL_STYLE);
                combatUI.getRootPane().widthProperty()
                                .addListener((obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(enemyName,
                                                newVal.doubleValue(), combatUI.getRootPane().getHeight(), 15, 90,
                                                Style.NAME_LABEL_STYLE));
                combatUI.getRootPane().heightProperty().addListener(
                                (obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(enemyName,
                                                combatUI.getRootPane().getWidth(),
                                                newVal.doubleValue(), 15, 90, Style.NAME_LABEL_STYLE));
                ResponsiveLabel.updateLabel(healthLabel, combatUI.getRootPane().getWidth(),
                                combatUI.getRootPane().getHeight(), 15, 90, Style.HEALTH_LABEL_STYLE);

                combatUI.getRootPane().widthProperty()
                                .addListener((obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(healthLabel,
                                                newVal.doubleValue(), combatUI.getRootPane().getHeight(), 15, 100,
                                                Style.HEALTH_LABEL_STYLE));
                combatUI.getRootPane().heightProperty().addListener(
                                (obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(healthLabel,
                                                combatUI.getRootPane().getWidth(),
                                                newVal.doubleValue(),
                                                15, 100, Style.HEALTH_LABEL_STYLE));

                // ตั้งค่า Layout
                HealthBar.getChildren().addAll(background, foreground, healthLabel);
                combatUI.getEnemyStatus().getChildren().addAll(enemyName, HealthBar);
                HealthBar.setAlignment(Pos.CENTER_RIGHT);

                // สร้าง HealthControllerLogic สำหรับ Enemy
                HealthControllerLogic logic = new HealthControllerLogic(health, defense, HealthBar, foreground,
                                healthLabel);

                // ตั้งค่า enemyHealthController ใน HealthControllerLogic
                // เพื่อให้สามารถเข้าถึง enemyHealthController ได้จากที่อื่นๆ
                HealthControllerLogic.setEnemyHealthController(logic);

                // ตั้งค่าให้เมื่อ Enemy ตาย จะทำการสร้าง Enemy ใหม่
                logic.setOnDeath(() -> {
                        EnemyManager.EnemyData next = EnemyManager.getInstance().nextEnemy();
                        new CreateEnemy(gameUI, next.name, next.className, next.health, next.attack, next.defense,
                                        next.imagePath);
                });

        }

        // Method สำหรับตั้งค่า Enemy Stats
        private void setupEnemyStats(String characterClass, double attackPower, double defensePower) {
                // สร้าง JavaFX Components สำหรับ Stats ของ Enemy
                Label classLabel = new Label("Class: " + characterClass);
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

                combatUI.getEnemyStats().getChildren().addAll(classLabel, attackLabel, defenseLabel);
        }

        // Method สำหรับตั้งค่า Enemy Avatar
        private void setupEnemyAvatar(String imagePath) {
                // โหลด Avatar ของ Enemy จากไฟล์ภาพ
                // ถ้าเกิด error จะทำการพิมพ์ข้อความผิดพลาดใน Console
                try {
                        ImageView AvatarIMG = new ImageView(getClass().getResource(imagePath).toExternalForm());
                        AvatarIMG.fitWidthProperty().bind(combatUI.getEnemyAvatar().widthProperty().multiply(0.6));
                        AvatarIMG.fitHeightProperty().bind(combatUI.getEnemyAvatar().heightProperty().multiply(0.8));
                        combatUI.getEnemyAvatar().getChildren().add(AvatarIMG);
                } catch (Exception e) {
                        e.printStackTrace();
                }
        }

        // Method สำหรับตั้งค่า Responsive Label
        // โดยจะทำการตั้งค่าขนาดของ Label ให้สัมพันธ์กับขนาดของ Parent Pane
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
