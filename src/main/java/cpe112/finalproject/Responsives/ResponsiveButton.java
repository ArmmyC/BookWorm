package cpe112.finalproject.Responsives;

import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.text.Font;

/*
 * ResponsiveButton.java
 * สำหรับการจัดการขนาดของ Font และ Padding ของ Button
 */

public class ResponsiveButton {
        // รวม Method สำหรับการจัดการขนาดของ Font และ Padding ของ Button
        // โดยจะมี Parameter ที่แตกต่างกันไปตามการใช้งาน

        // Method หลักสำหรับการจัดการขนาดของ Font และ Padding ของ Button
        // Parameter = Button, width, height, fontDivider, paddingDivider, font
        public static void updateButton(Button button, double width, double height, double fontDivider,
                        double paddingDivider, Font font) {

                // หาค่าขนาดที่น้อยที่สุดระหว่าง width และ height
                double minSize = Math.min(width, height);
                double fontSize = minSize / fontDivider; // คำนวณขนาดของ font

                // ตั้งค่าขนาดของ padding ถ้ามีการกำหนด paddingDivider
                if (paddingDivider != -1) {
                        double paddingSize = minSize / paddingDivider; // คำนวณขนาดของ padding
                        button.setPadding(new Insets(paddingSize));
                } else {
                        button.setPadding(new Insets(0));
                }

                // ตั้งค่าขนาดของ font และ font family ถ้ามีการกำหนด font
                if (font != null) {
                        button.setFont(Font.font(font.getFamily(), fontSize));
                } else {
                        button.setFont(new Font(fontSize));
                }
        }

        // Method สำหรับ Parameter ที่ไม่มีการกำหนด font
        public static void updateButton(Button button, double width, double height, double fontDivider,
                        double paddingDivider) {
                updateButton(button, width, height, fontDivider, paddingDivider, null);
        }
}
