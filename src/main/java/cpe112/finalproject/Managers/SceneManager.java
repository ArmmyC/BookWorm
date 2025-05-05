package cpe112.finalproject.Managers;

import javafx.animation.FadeTransition;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Duration;

/*
 * SceneManager.java
 * สำหรับจัดการ Scene ต่างๆ ในโปรแกรม
 * 
 * เป็น Singleton Pattern เพื่อให้สามารถเข้าถึงได้จากที่อื่นๆ
 * โดยไม่ต้องสร้าง Instance ใหม่ทุกครั้ง
 */

public class SceneManager {

    private static SceneManager instance; // สร้าง Instance ของ SceneManager
    private Stage primaryStage; // สร้างตัวแปร Stage สำหรับเก็บ Stage หลักของโปรแกรม

    private SceneManager() {
        // ทำให้ Constructor เป็น private
        // เพื่อไม่ให้สามารถสร้าง Instance ของ SceneManager ได้จากที่อื่น
        // (Singleton Pattern)
    }

    // Method สำหรับสร้าง Instance ของ SceneManager
    // สามารถสร้างได้เพียงครั้งเดียว (Singleton Pattern)
    public static SceneManager getInstance() {
        // เช็คว่า Instance ของ SceneManager ถูกสร้างขึ้นหรือยัง
        // ถ้ายัง ให้สร้าง Instance ใหม่ และเก็บไว้ในตัวแปร instance
        // ถ้าถูกสร้างขึ้นแล้ว ให้คืนค่า Instance ที่มีอยู่
        if (instance == null) {
            instance = new SceneManager();
        }
        return instance;
    }

    // Method สำหรับตั้งค่า Stage หลักของโปรแกรม
    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    // Method สำหรับเปลี่ยน Scene ของ Stage หลัก
    public void switchRoot(Parent newRoot) {
        // สร้างตัวแปร currentScene สำหรับเก็บ Scene ปัจจุบัน
        Scene currentScene = primaryStage.getScene();

        // เช็คว่ามี Scene ปัจจุบันหรือไม่
        // ถ้าไม่มี ให้ตั้งค่า Scene ใหม่เป็น Stage หลัก
        if (currentScene == null) {
            primaryStage.setScene(new Scene(newRoot, 800, 600));
            return;
        }

        // สร้างตัวแปร oldRoot สำหรับเก็บ Root ของ Scene ปัจจุบัน
        Node oldRoot = currentScene.getRoot();

        // สร้าง FadeTransition สำหรับการเปลี่ยน Scene
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), oldRoot);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // ตั้งค่าให้ Scene ใหม่ Fade In หลังจาก Scene ปัจจุบัน Fade Out เสร็จแล้ว
        fadeOut.setOnFinished(event -> {
            newRoot.setOpacity(0);
            currentScene.setRoot(newRoot);

            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), newRoot);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);
            fadeIn.play();
        });
        fadeOut.play();
    }

    // Getter สำหรับการเข้าถึง Stage หลักของโปรแกรม
    public Stage getStage() {
        return primaryStage;
    }

}