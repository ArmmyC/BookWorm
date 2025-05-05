package cpe112.finalproject.Logic;

import cpe112.finalproject.Constants.Style;
import cpe112.finalproject.Layout.CombatUI;
import cpe112.finalproject.Managers.EnemyManager;
import cpe112.finalproject.Responsives.ResponsiveLabel;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

/*
 * CombatLogic.java
 * สำหรับจัดการ Logic ของการต่อสู้
 */

public class CombatLogic {

    // สร้างตัวแปร combatUI สำหรับเก็บ CombatUI
    private final CombatUI ui;

    // สร้างตัวแปร wordLabel สำหรับแสดงตัวอักษรที่ถูกเลือก
    Label wordLabel = new Label("");

    // Constructor ของ CombatLogic
    public CombatLogic(CombatUI ui) {
        this.ui = ui;
        initialize();
    }

    // Method สำหรับการเริ่มต้นการทำงานของ CombatLogic
    private void initialize() {
        setupEnemyCount();
        setupWordFormed();
    }

    // Method สำหรับตั้งค่า UI ของเลขนับจำนวนศัตรู
    private void setupEnemyCount() {

        // ตั้งค่าความสูงและความกว้างของ UI
        ui.getEnemyCount().maxHeightProperty().bind(ui.getRootPane().heightProperty().multiply(0.1)); // 10%
        ui.getEnemyCount().maxWidthProperty().bind(ui.getRootPane().widthProperty().multiply(0.1)); // 10%

        // ตั้งค่าการจัดตำแหน่งของ UI
        StackPane.setAlignment(ui.getEnemyCount(), Pos.TOP_CENTER);
        ui.getEnemyCount().setAlignment(Pos.CENTER);

        // สร้าง Label สำหรับแสดงจำนวนศัตรู และตั้งค่า Style
        Label countLabel = new Label();
        countLabel.setStyle("-fx-text-fill: white; -fx-font-size: 18px; -fx-font-weight: bold;");

        // ตั้งค่าให้ Label แสดงจำนวนศัตรู โดยใช้ IntegerProperty จาก EnemyManager
        countLabel.textProperty().bind(
                EnemyManager.getInstance().enemyCountProperty().asString("💀 %d"));

        // เพิ่ม Label ลงใน UI และ ตั้งค่า Style
        ui.getEnemyCount().getChildren().add(countLabel);
        ui.getEnemyCount()
                .setStyle("-fx-background-color:rgb(25, 25, 25); -fx-border-color: #FFFFFF; -fx-border-width: 2px;");
    }

    // Method สำหรับตั้งค่า UI ของคำที่ถูกสร้างขึ้น
    private void setupWordFormed() {
        // ตั้งค๋า UI
        ui.getWordFormed().setAlignment(Pos.CENTER);
        ui.getWordFormed().getChildren().add(wordLabel);

        // ตั้งค่า Style ของตัวอักษรที่ถูกเลือก
        wordLabel.setStyle(Style.WORD_FORMED_STYLE);

        // เรียกใช้ ResponsiveLabel 1 ครั้งเพื่อให้ Label ปรับขนาดตามขนาดของ UI
        ResponsiveLabel.updateLabel(wordLabel, ui.getRootPane().getWidth(), ui.getRootPane().getHeight(), 40,
                Style.WORD_FORMED_STYLE);

        // ตั้งค่าการเปลี่ยนแปลงขนาดของ UI ให้ Label ปรับขนาดตามขนาดของ UI โดยอัตโนมัติ
        ui.getRootPane().widthProperty()
                .addListener((obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(wordLabel,
                        newVal.doubleValue(), ui.getRootPane().getHeight(), 40, Style.WORD_FORMED_STYLE));
        ui.getRootPane().heightProperty()
                .addListener((obs, oldVal, newVal) -> ResponsiveLabel.updateLabel(wordLabel,
                        ui.getRootPane().getWidth(), newVal.doubleValue(), 40, Style.WORD_FORMED_STYLE));
    }

    // Method สำหรับตั้งค่าให้ Label แสดงตัวอักษรที่ถูกเลือก
    public void setWordFormed(String word) {
        wordLabel.setText(word);
    }
}
