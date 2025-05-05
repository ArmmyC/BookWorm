package cpe112.finalproject.Scenes;

import cpe112.finalproject.Layout.CreditUI;
import cpe112.finalproject.Logic.CreditLogic;
import javafx.scene.layout.StackPane;

/* 
 * CreditScene.java
 * สำหรับสร้าง Scene ของหน้า Credit
 */

public class CreditScene {

    // สร้างตัวแปรสำหรับ UI และ Logic ของหน้า Credit
    private final CreditUI ui;
    private final CreditLogic logic;

    public CreditScene() {
        // สร้าง UI ของหน้า Credit
        ui = new CreditUI();

        // สร้าง Logic ของหน้า Credit
        logic = new CreditLogic(ui);
        logic.initialize();
    }

    // Method สำหรับเรียก RootPane ของหน้า Credit
    // เพื่อใช้ในการแสดงผลใน Scene
    public StackPane getRootPane() {
        return ui.getRootPane();
    }
}