package cpe112.finalproject.Logic;

import cpe112.finalproject.Constants.Path;
import cpe112.finalproject.Layout.GameUI;
import cpe112.finalproject.Managers.EnemyManager;
import cpe112.finalproject.Managers.ImageManager;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

/*
 * HealthControllerLogic.java
 * สำหรับจัดการ Logic ของ HP ของศัตรูและผู้เล่น
 */

public class HealthControllerLogic {

    // สร้างตัวแปร enemyHealthController สำหรับเก็บ HealthControllerLogic ของศัตรู
    private static HealthControllerLogic enemyHealthController;

    // Method สำหรับตั้งค่า enemyHealthController
    public static void setEnemyHealthController(HealthControllerLogic controller) {
        enemyHealthController = controller;
    }

    // สร้างตัวแปร playerHealthController สำหรับเก็บ HealthControllerLogic
    // ของผู้เล่น
    private static HealthControllerLogic playerHealthController;

    // Method สำหรับตั้งค่า playerHealthController
    public static void setPlayerHealthController(HealthControllerLogic controller) {
        playerHealthController = controller;
    }

    private Runnable onDeathCallback; // Callback สำหรับเมื่อศัตรูตาย

    // Method สำหรับตั้งค่า callback เมื่อศัตรูตาย
    public void setOnDeath(Runnable callback) {
        this.onDeathCallback = callback;
    }

    // Method สำหรับลด HP ของศัตรู
    public static void damageEnemy(double damage) {
        // ตรวจสอบว่ามี enemyHealthController
        if (enemyHealthController != null) {
            // ลด HP ของศัตรู
            enemyHealthController.takeDamage(damage);
            ImageManager.getPlayerImageManager().changeImage(Path.PLAYER_ATTACK_IMAGE);

        }
        PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
        pause.setOnFinished(e -> {
            ImageManager.getPlayerImageManager().changeImage(Path.PLAYER_IDLE_IMAGE);
        });
        pause.play();
    }

    // Method สำหรับลด HP ของผู้เล่น
    public static void damagePlayer(double damage) {
        // ตรวจสอบว่ามี playerHealthController
        if (playerHealthController != null) {
            // ลด HP ของผู้เล่น
            playerHealthController.takeDamage(damage);

            // ถ้า HP ของผู้เล่น <= 0 ให้แสดงข้อความ "Player defeated!"
            if (playerHealthController.isDead()) {
                EnemyManager.getInstance().stopEnemyAttackLoop(); // หยุดการโจมตีของศัตรู

                // Playform.runLater ใช้สำหรับรันหลังจากสร้าง UI เสร็จ
                Platform.runLater(() -> {
                    GameUI.getInstance().getGameOverUI().GameOver(); // แสดง Game Over UI
                    EnemyManager.getInstance().resetEnemyCount(); // รีเซ็ตจำนวนศัตรูที่ถูกสร้างขึ้น
                });

            }
        }
    }

    private final double maxHealth; // ค่า HP สูงสุด
    private double currentHealth; // ค่า HP ปัจจุบัน
    private final double defense; // ค่า Defense

    private final StackPane healthBar; // StackPane สำหรับแสดง HP
    private final Region foreground; // Region สำหรับแสดง HP ที่ลดลง
    private final Label healthLabel; // Label สำหรับแสดงค่า HP

    // Constructor สำหรับ HealthControllerLogic
    public HealthControllerLogic(double maxHealth, double defense, StackPane healthBar, Region foreground,
            Label healthLabel) {
        this.maxHealth = maxHealth;
        this.currentHealth = maxHealth;
        this.defense = defense;
        this.healthBar = healthBar;
        this.foreground = foreground;
        this.healthLabel = healthLabel;

        // เตรียมค่าเริ่มต้นให้กับ healthBar และ foreground
        healthBar.widthProperty().addListener((obs, oldWidth, newWidth) -> {
            if (newWidth.doubleValue() > 0) {
                updateHealth(false); // update ความกว้าง health bar
            }
        });
    }

    // Method สำหรับลด HP
    public void takeDamage(double rawDamage) {
        // ลดเลือดโดยการลบค่าที่รับเข้ามา และไม่ให้ต่ำกว่า 0
        double reducedDamage = Math.max(0, rawDamage - defense);
        currentHealth = Math.max(0, currentHealth - reducedDamage);
        updateHealth(true); // update ความกว้าง health bar

        // ถ้า HP <= 0 ให้เล่น animation ตาย
        if (isDead()) {
            playDeathAnimation(() -> {
                if (onDeathCallback != null) {
                    onDeathCallback.run();
                }
            });
        }
    }

    public static void healPlayer(double amount) {
        // ตรวจสอบว่ามี playerHealthController
        if (playerHealthController != null) {
            // เพิ่ม HP ของผู้เล่น
            playerHealthController.heal(amount);
        }
    }

    // Method สำหรับเพิ่ม HP
    public void heal(double amount) {
        currentHealth = Math.min(maxHealth, currentHealth + amount);
        updateHealth(true);
    }

    // Method สำหรับเช็คว่า HP <= 0 หรือไม่
    public boolean isDead() {
        return currentHealth <= 0;
    }

    // Getter สำหรับค่า HP
    public double getCurrentHealth() {
        return currentHealth;
    }

    // Method สำหรับอัพเดทความกว้างของ health bar
    private void updateHealth(boolean animated) {
        double percentage = (currentHealth / maxHealth) * 100; // คำนวณเปอร์เซ็นต์ HP
        double barWidth = healthBar.getWidth(); // ความกว้างของ health bar

        // ถ้าความกว้างของ health bar = 0 ให้ return ออกไป
        if (barWidth == 0) {
            return;
        }

        double newWidth = (percentage / 100) * barWidth; // คำนวณความกว้างใหม่ของ health bar

        // ถ้า animated เป็น true ให้เล่น animation
        // ถ้า animated เป็น false ให้เปลี่ยนความกว้างทันที
        if (animated) {
            Timeline timeline = new Timeline(
                    new KeyFrame(Duration.millis(300),
                            new KeyValue(foreground.maxWidthProperty(), newWidth, Interpolator.EASE_BOTH)));
            timeline.play();
        } else {
            foreground.setMaxWidth(newWidth);
        }

        // อัพเดทค่า HP ที่แสดงใน Label
        healthLabel.setText((int) currentHealth + "");
    }

    // Method สำหรับเล่น animation ตาย
    private void playDeathAnimation(Runnable onFinished) {
        foreground.setStyle("-fx-background-color: red;"); // เปลี่ยนสี health bar เป็นสีแดง

        // สร้าง Timeline สำหรับ animation ตาย
        Timeline deathAnim = new Timeline(
                new KeyFrame(Duration.millis(200),
                        new KeyValue(foreground.opacityProperty(), 0, Interpolator.EASE_OUT)));

        // ตั้งค่าให้เมื่อ animation เสร็จสิ้นให้เรียกใช้ onFinished
        deathAnim.setOnFinished(e -> {
            foreground.setOpacity(1.0);
            foreground.setStyle("");
            onFinished.run();
        });

        deathAnim.play();
    }
}
