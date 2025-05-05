package cpe112.finalproject.Responsives;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.text.Font;

/*
 * ResponsiveLabel.java
 * สำหรับการจัดการขนาดของ Font และ Padding ของ Label
 */

public class ResponsiveLabel {
        // รวม Method สำหรับการจัดการขนาดของ Font และ Padding ของ Label
        // โดยจะมี Parameter ที่แตกต่างกันไปตามการใช้งาน

        // Method หลักสำหรับการจัดการขนาดของ Font และ Padding ของ Label
        // Parameter = Label, width, height, fontDivider, paddingDivider, font
        public static void updateLabel(Label label, double width, double height, double fontDivider,
                        double paddingDivider, Font font, String style) {

                // หาค่าขนาดที่น้อยที่สุดระหว่าง width และ height
                double minSize = Math.min(width, height);
                double fontSize = minSize / fontDivider; // คำนวณขนาดของ font

                // ตั้งค่าขนาดของ padding ถ้ามีการกำหนด paddingDivider
                if (paddingDivider != -1) {
                        double paddingSize = minSize / paddingDivider; // คำนวณขนาดของ padding
                        label.setPadding(new Insets(paddingSize));
                } else {
                        label.setPadding(new Insets(0));
                }

                // ตั้งค่าขนาดของ font และ font family ถ้ามีการกำหนด font
                if (font != null) {
                        label.setFont(Font.font(font.getFamily(), fontSize));
                } else {
                        label.setFont(new Font(fontSize));
                }
        }

        // Method สำหรับ Parameter ที่ไม่มีการกำหนด paddingDivider และ style
        public static void updateLabel(Label label, double width, double height, double fontDivider,
                        Font font) {
                updateLabel(label, width, height, fontDivider, -1, font, null);
        }

        // Method สำหรับ Parameter ที่ไม่มีการกำหนด font และ style
        public static void updateLabel(Label label, double width, double height, double fontDivider,
                        double paddingDivider) {
                updateLabel(label, width, height, fontDivider, paddingDivider, null, null);
        }

        // Method สำหรับ Parameter ที่ไม่มีการกำหนด font
        public static void updateLabel(Label label, double width, double height, double fontDivider,
                        double paddingDivider, String style) {
                // หาค่าขนาดที่น้อยที่สุดระหว่าง width และ height
                double minSize = Math.min(width, height);
                double fontSize = minSize / fontDivider; // คำนวณขนาดของ font

                // ตั้งค่าขนาดของ padding ถ้ามีการกำหนด paddingDivider
                if (paddingDivider != -1) {
                        double paddingSize = minSize / paddingDivider; // คำนวณขนาดของ padding
                        label.setPadding(new Insets(paddingSize));
                } else {
                        label.setPadding(new Insets(0));
                }

                label.setStyle(style + "-fx-font-size: " + fontSize + ";"); // ตั้งค่าขนาดของ font
        }

        // Method สำหรับ Parameter ที่ไม่มีการกำหนด font และ paddingDivider
        public static void updateLabel(Label label, double width, double height, double fontDivider, String style) {
                updateLabel(label, width, height, fontDivider, -1, null, style);
        }

}
