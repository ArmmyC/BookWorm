package cpe112.finalproject.Layout;

import cpe112.finalproject.Logic.HealthControllerLogic;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

/*
 * DevUI.java
 * สำหรับสร้าง UI ของ Developer Tools
 */

public class DevUI {

    // Method สำหรับสร้างและตั้งค่า UI ของ Developer Tools
    public void showDevWindow() {
        Stage devStage = new Stage(); // สร้าง Stage ใหม่สำหรับ Developer Tools
        devStage.setTitle("Developer Tools"); // ตั้งชื่อ Stage

        VBox layout = new VBox(10); // สร้าง VBox สำหรับจัดเรียง UI
        layout.setPadding(new Insets(15)); // ตั้งค่าระยะห่างระหว่าง UI
        layout.setAlignment(Pos.CENTER); // ตั้งค่าการจัดตำแหน่งของ UI ให้อยู่ตรงกลาง

        // สร้าง Label และ Button สำหรับ Developer Tools
        Label debugLabel = new Label("Debug Controls");
        Button spawnEnemyButton = new Button("Kill Enemy");
        Button killPlayerButton = new Button("Kill Player");

        // ตั้งค่า action ของ Button
        spawnEnemyButton.setOnAction(e -> {
            HealthControllerLogic.damageEnemy(Double.MAX_VALUE);
        });
        killPlayerButton.setOnAction(e -> {
            HealthControllerLogic.damagePlayer(Double.MAX_VALUE);
        });

        // ตั้งค่า Layout ของ UI
        layout.getChildren().addAll(debugLabel, spawnEnemyButton, killPlayerButton);

        // สร้าง Scene
        Scene scene = new Scene(layout, 300, 200);
        devStage.setScene(scene);
        devStage.initModality(Modality.NONE);
        devStage.show();
    }
}
