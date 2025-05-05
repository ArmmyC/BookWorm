package cpe112.finalproject.Handlers;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/*
 * KeyEventHandler.java
 * สำหรับจัดการ Event ของ Keyboard
 */

public class KeyEventHandler {

    // Method สำหรับจัดการ Event ของการกดปุ่ม ESCAPE
    // Runnable คือ Method ที่ไม่มี Parameter และไม่มี Return Type (void)
    public static void escapeKey(KeyEvent event, Runnable onEscape) {
        // เช็คว่า Key ที่กดคือ ESCAPE หรือไม่
        if (event.getCode() == KeyCode.ESCAPE) {
            // ถ้าใช่ ให้ทำการ consume event เพื่อไม่ให้เกิดการทำงานอื่นๆนอกจากนี้
            event.consume();

            // เรียกใช้ method onEscape ที่ส่งเข้ามา
            onEscape.run();
        }
    }
}