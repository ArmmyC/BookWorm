package cpe112.finalproject.Scenes;

import cpe112.finalproject.Layout.ClassSelectorUI;
import cpe112.finalproject.Logic.ClassSelectorLogic;
import javafx.scene.layout.BorderPane;

/*
 * ClassSelectorScene.java
 * สำหรับสร้าง Scene ของหน้าสร้างตัวละคร
 */

public class ClassSelectorScene {

    // สร้างตัวแปรสำหรับ UI และ Logic ของหน้าสร้างตัวละคร
    private final ClassSelectorUI ui;
    private final ClassSelectorLogic logic;

    // Constructor ของ ClassSelectorScene
    public ClassSelectorScene() {
        // สร้าง UI ของหน้าสร้างตัวละคร
        ui = new ClassSelectorUI();

        // สร้าง Logic ของหน้าสร้างตัวละคร
        logic = new ClassSelectorLogic(ui);
        logic.initialize();
    }

    // Method สำหรับเรียก RootPane ของหน้าสร้างตัวละคร
    // เพื่อใช้ในการแสดงผลใน Scene
    public BorderPane getRootPane() {
        return ui.getRootPane();
    }
}