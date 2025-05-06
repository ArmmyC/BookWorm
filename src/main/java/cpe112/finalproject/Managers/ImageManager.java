package cpe112.finalproject.Managers;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

/*
 * ImageManager.java
 * สำหรับจัดการภาพในเกม
 */

public class ImageManager {

    // สร้าง ImageManager สำหรับ Player และ Enemy
    private static ImageManager playerImageManager;
    private static ImageManager enemyImageManager;

    private final ImageView imageView; // ImageView ที่จะใช้ในการแสดงภาพ

    // Constructor สำหรับสร้าง ImageManager
    public ImageManager(ImageView imageView) {
        this.imageView = imageView;
    }

    // Method สำหรับเปลี่ยนภาพใน ImageView
    public void changeImage(String imagePath) {
        imageView.setImage(new Image(getClass().getResource(imagePath).toExternalForm()));
    }

    // Method สำหรับตั้งค่า playerImageManager
    public static void setPlayerImageManager(ImageManager manager) {
        playerImageManager = manager;
    }

    // Method สำหรับตั้งค่า enemyImageManager
    public static void setEnemyImageManager(ImageManager manager) {
        enemyImageManager = manager;
    }

    // Getter สำหรับเข้าถึง playerImageManager
    public static ImageManager getPlayerImageManager() {
        return playerImageManager;
    }

    // Getter สำหรับเข้าถึง enemyImageManager
    public static ImageManager getEnemyImageManager() {
        return enemyImageManager;
    }
}
