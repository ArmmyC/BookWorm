package cpe112.finalproject.Managers;

import cpe112.finalproject.Constants.Path;
import cpe112.finalproject.Logic.HealthControllerLogic;
import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.util.Duration;

/*
 * EnemyManager.java
 * สำหรับจัดการข้อมูลของศัตรู
 * 
 * เป็น Singleton Pattern เพื่อให้สามารถเข้าถึงได้จากที่อื่นๆ
 * โดยไม่ต้องสร้าง Instance ใหม่ทุกครั้ง
 */

public final class EnemyManager {

    // Class สำหรับเก็บข้อมูลของศัตรู
    public static class EnemyData {
        public final String name; // ชื่อศัตรู
        public final String className; // ชื่อคลาสของศัตรู
        public final double health; // ค่าพลังชีวิตของศัตรู
        public final double attack; // ค่าพลังโจมตีของศัตรู
        public final double defense; // ค่าพลังป้องกันของศัตรู
        public final String imagePath; // Path ของภาพศัตรู

        // Constructor สำหรับสร้าง EnemyData
        public EnemyData(String name, String className, double health, double attack, double defense,
                String imagePath) {
            this.name = name;
            this.className = className;
            this.health = health;
            this.attack = attack;
            this.defense = defense;
            this.imagePath = imagePath;
        }
    }

    // สร้าง Instance ของ EnemyManager
    private static final EnemyManager instance = new EnemyManager();

    // Getter สำหรับเข้าถึง Instance ของ EnemyManager
    public static EnemyManager getInstance() {
        return instance;
    }

    // สร้างตัวแปรสำหรับเก็บข้อมูลของศัตรู
    private EnemyData currentEnemy;

    // สร้างตัวแปรสำหรับเก็บจำนวนศัตรูที่ถูกสร้างขึ้นเป็น IntegerProperty
    // เพื่อให้สามารถใช้กับ JavaFX Binding ได้
    private final IntegerProperty enemyCount = new SimpleIntegerProperty(0);

    // Method สำหรับ spawn ศัตรูตัวถัดไป
    public EnemyData nextEnemy() {
        enemyCount.set(enemyCount.get() + 1); // เพิ่มจำนวนศัตรูที่ถูกสร้างขึ้น
        int count = enemyCount.get();

        // ถ้าศัตรูที่ถูกสร้างขึ้นเป็นตัวที่ 5, 10, 15,... ให้สร้าง Boss
        if (count % 5 == 0) {
            currentEnemy = generateBoss(count / 5);
        } else {
            currentEnemy = generateEnemy(count);
        }

        HealthControllerLogic.healPlayer(5 * count); // เพิ่ม HP ของผู้เล่น 5 * จำนวนศัตรูที่ถูกสร้างขึ้น
        return currentEnemy;
    }

    // Method สำหรับสร้างศัตรูทั่วไป
    public EnemyData generateEnemy(int number) {
        double health = 100 + number * 20;
        double attack = 10 + number * 2.5;
        double defense = 5 + number * 1.5;

        String[] names = { "Goblin", "Orc", "Thief", "Skeleton", "Zombie" };
        String name = names[number % names.length];
        String className = "Minion";
        String imagePath = "/cpe112/finalproject/images/enemy/" + name.toLowerCase() + ".png";

        return new EnemyData(name, className, health, attack, defense, imagePath);
    }

    // Method สำหรับสร้าง Boss
    private EnemyData generateBoss(int level) {
        double health = 500 + level * 150;
        double attack = 30 + level * 15;
        double defense = 20 + level * 10;

        String[] bossNames = { "Sans", "Devil", "Bowser", "Moonlord", "Wither" };
        String baseName = bossNames[level % bossNames.length];
        String name = baseName + " [Boss Lv." + level + "]";
        String className = "Boss";
        String imagePath = "/cpe112/finalproject/images/boss/" + baseName.toLowerCase() + ".png";

        return new EnemyData(name, className, health, attack, defense, imagePath);
    }

    // Method สำหรับดึงจำนวนศัตรูที่ถูกสร้างขึ้น
    public int getEnemyCount() {
        return enemyCount.get();
    }

    // Getter สำหรับเข้าถึงข้อมูลของศัตรูปัจจุบัน
    public EnemyData getCurrentEnemy() {
        return currentEnemy;
    }

    // Getter สำหรับเข้าถึงจำนวนศัตรูที่ถูกสร้างขึ้น
    public IntegerProperty enemyCountProperty() {
        return enemyCount;
    }

    private Timeline enemyAttackTimeline; // Timeline สำหรับการโจมตีของศัตรูทุกๆ 5 วินาที

    // Method สำหรับเริ่มการโจมตีของศัตรูทุกๆ 5 วินาที
    public void startEnemyAttackLoop() {
        // ถ้ามี Timeline อยู่แล้วให้หยุดก่อน
        if (enemyAttackTimeline != null) {
            enemyAttackTimeline.stop();
        }

        // สร้าง Timeline ใหม่สำหรับการโจมตีของศัตรูทุกๆ 5 วินาที
        enemyAttackTimeline = new Timeline(
                new KeyFrame(Duration.seconds(5), event -> performEnemyAttack()));
        enemyAttackTimeline.setCycleCount(Timeline.INDEFINITE); // ตั้งค่าให้ทำงานวนลูปไม่จำกัดจำนวนครั้ง
        enemyAttackTimeline.play(); // เริ่มการทำงานของ Timeline
    }

    // Method สำหรับหยุดการโจมตีของศัตรู
    public void stopEnemyAttackLoop() {
        // ถ้ามี Timeline อยู่แล้วให้หยุด
        if (enemyAttackTimeline != null) {
            enemyAttackTimeline.stop();
        }
    }

    // Method สำหรับทำการโจมตีของศัตรู
    private void performEnemyAttack() {
        EnemyData enemy = getCurrentEnemy(); // ดึงข้อมูลของศัตรูปัจจุบัน

        // ถ้ามีศัตรูอยู่ให้ทำการโจมตี
        if (enemy != null) {
            double damage = enemy.attack; // ค่าพลังโจมตีของศัตรู
            ImageManager.getPlayerImageManager().changeImage(Path.PLAYER_DEFEND_IMAGE); // เปลี่ยนภาพของศัตรู
            HealthControllerLogic.damagePlayer(damage); // เรียกใช้ method สำหรับลด HP ของผู้เล่น
            SoundManager.playDefendSound(); // เล่น player โดนโจมตี
            PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
            pause.setOnFinished(e -> {
                ImageManager.getPlayerImageManager().changeImage(Path.PLAYER_IDLE_IMAGE);
            });
            pause.play();
        }
    }

    // Method สำหรับรีเซ็ตจำนวนศัตรูที่ถูกสร้างขึ้น
    public void resetEnemyCount() {
        enemyCount.set(0);
    }
}
