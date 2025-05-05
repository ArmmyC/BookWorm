package cpe112.finalproject.Scenes;

import cpe112.finalproject.Layout.MainMenuUI;
import cpe112.finalproject.Logic.MainMenuLogic;
import javafx.scene.layout.BorderPane;

/*
 * MainMenuScene.java
 * สำหรับสร้าง Scene ของหน้าเมนูหลัก / หน้าแรกตอนเริ่มเกม
 */

public class MainMenuScene {

    // สร้างตัวแปรสำหรับ UI และ Logic ของหน้าเมนูหลัก
    private final MainMenuUI ui;
    private final MainMenuLogic logic;

    // Constructor ของ MainMenuScene
    public MainMenuScene() {
        // สร้าง UI ของหน้าเมนูหลัก
        ui = new MainMenuUI();

        // สร้าง Logic ของหน้าเมนูหลัก
        logic = new MainMenuLogic(ui);
        logic.initialize();
    }

    // Method สำหรับเรียก RootPane ของหน้าเมนูหลัก
    // เพื่อใช้ในการแสดงผลใน Scene
    public BorderPane getRootPane() {
        return ui.getRootPane();
    }
}
