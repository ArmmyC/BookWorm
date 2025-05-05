package cpe112.finalproject.Scenes;

import cpe112.finalproject.Entities.CreateEnemy;
import cpe112.finalproject.Layout.GameUI;
import cpe112.finalproject.Managers.EnemyManager;
import cpe112.finalproject.Managers.SoundManager;
import javafx.scene.layout.StackPane;

/*
 * GameScene.java
 * สำหรับสร้าง Scene ของเกม
 */

public class GameScene {

    // สร้างตัวแปรสำหรับ UI ของเกม
    private final GameUI ui;

    public GameScene() {

        // เล่นเพลงประกอบเกม
        SoundManager.playBattleTheme();

        // สร้าง UI ของเกม
        ui = new GameUI();

        // สร้าง Player
        ui.createPlayer();

        // สร้าง EnemyManager และเพิ่มศัตรูตัวแรก
        spawnFirstEnemy();
    }

    // Method สำหรับสร้างศัตรูตัวแรก
    private void spawnFirstEnemy() {
        EnemyManager manager = EnemyManager.getInstance();
        EnemyManager.EnemyData data = manager.generateEnemy(0);
        new CreateEnemy(ui, data.name, data.className, data.health, data.attack, data.defense, data.imagePath);
    }

    // Getter สำหรับเข้าถึง UI ของเกม
    public StackPane getRootPane() {
        return ui.getRootPane();
    }
}