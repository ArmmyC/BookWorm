package cpe112.finalproject.Logic;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Stack;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurveTo;

/*
* PuzzleLogic.java 
* สำหรับจัดการ Logic ของ Puzzle
*/

public class PuzzleLogic {

    // ค่าคงที่สำหรับ Damage ของแต่ละตัวอักษร
    private static final Map<String, Integer> LETTER_DMG = new HashMap<>();
    static {
        LETTER_DMG.put("A", 1);
        LETTER_DMG.put("B", 3);
        LETTER_DMG.put("C", 3);
        LETTER_DMG.put("D", 2);
        LETTER_DMG.put("E", 1);
        LETTER_DMG.put("F", 4);
        LETTER_DMG.put("G", 2);
        LETTER_DMG.put("H", 4);
        LETTER_DMG.put("I", 1);
        LETTER_DMG.put("J", 8);
        LETTER_DMG.put("K", 5);
        LETTER_DMG.put("L", 1);
        LETTER_DMG.put("M", 3);
        LETTER_DMG.put("N", 1);
        LETTER_DMG.put("O", 1);
        LETTER_DMG.put("P", 3);
        LETTER_DMG.put("Q", 10);
        LETTER_DMG.put("R", 1);
        LETTER_DMG.put("S", 1);
        LETTER_DMG.put("T", 1);
        LETTER_DMG.put("U", 1);
        LETTER_DMG.put("V", 4);
        LETTER_DMG.put("W", 4);
        LETTER_DMG.put("X", 8);
        LETTER_DMG.put("Y", 4);
        LETTER_DMG.put("Z", 10);
    }

    // selectedLetters สำหรับเก็บปุ่มที่ถูกเลือกในรูปแบบ Stack
    private final Stack<Button> selectedLetters;

    // lastSelectedButton สำหรับเก็บปุ่มที่เลือกล่าสุด
    private Button lastSelectedButton;

    // isSelecting สำหรับเช็คว่าปุ่มถูกเลือกอยู่หรือไม่
    private boolean isSelecting = false;

    // กำหนด Constructor
    // ใช้ใน GUI.java
    public PuzzleLogic() {
        this.selectedLetters = new Stack<>();
        this.lastSelectedButton = null;
    }

    // Method สำหรับเรียกค่า Damage ของตัวอักษร
    public int getLetterDamage(String letter) {
        return LETTER_DMG.getOrDefault(letter.toUpperCase(), 1);
    }

    // Method สำหรับการสร้าง String จากปุ่มที่ถูกเลือก
    public String getSelectedWord() {
        StringBuilder word = new StringBuilder();
        for (Button button : getSelectedLetters()) {
            StackPane content = (StackPane) button.getGraphic();
            Label mainLabel = (Label) content.getChildren().get(0);
            String letter = mainLabel.getText();
            word.append(letter);
        }
        return word.toString();
    }

    // Method สำหรับเช็คว่าปุ่มที่ถูกเลือกอยู่ติดกันหรือไม่
    // โดยจะเช็คจากตำแหน่ง Row และ Column ของปุ่มที่ถูกเลือก
    private boolean isAdjacent(Button button1, Button button2) {
        int row1 = GridPane.getRowIndex(button1);
        int col1 = GridPane.getColumnIndex(button1);
        int row2 = GridPane.getRowIndex(button2);
        int col2 = GridPane.getColumnIndex(button2);

        return Math.abs(row1 - row2) <= 1 && Math.abs(col1 - col2) <= 1;
    }

    // Method สำหรับดึงปุ่มสุดท้ายที่ถูกเลือก
    public void setLastSelectedButton(Button button) {
        lastSelectedButton = button;
    }

    // Method สำหรับ เช็คว่ากำลังลาก Mouse กลับไปที่ปุ่มที่เลือกไว้แล้วหรือไม่
    // (Backtracking)
    public boolean isBacktracking(Button button) {
        return selectedLetters.size() > 1 && selectedLetters.get(selectedLetters.size() - 2) == button;
    }

    // Method สำหรับ เช็คว่าปุ่มที่ Mouse ลากไปนั้นเป็นปุ่มที่สามารถเลือกได้หรือไม่
    // (เป็นปุ่มที่อยู่ติดกัน)
    public boolean canSelect(Button button) {
        return button != lastSelectedButton &&
                !selectedLetters.contains(button) &&
                isAdjacent(lastSelectedButton, button);
    }

    // Method สำหรับหาค่าตำแหน่งตรงกลางของปุ่มที่ถูกเลือก
    // ใช้ใน method getButtonCoordinates
    private double[] getCenterCoords(Button button) {
        Bounds bounds = button.localToParent(button.getBoundsInLocal());
        double centerX = bounds.getMinX() + bounds.getWidth() / 2;
        double centerY = bounds.getMinY() + bounds.getHeight() / 2;
        return new double[] { centerX, centerY };
    }

    // Method สำหรับเก็บค่า Coordinates ของปุ่มที่ถูกเลือก
    // ใช้ใน method updatePath
    private List<double[]> getButtonCoordinates() {
        List<double[]> coords = new ArrayList<>();
        for (Button b : selectedLetters) {
            coords.add(getCenterCoords(b));
        }
        return coords;
    }

    // Method สำหรับสร้างเส้นให้มีความโค้งที่จุดโค้ง
    // ใช้ใน method updatePath
    private void addRoundedCorner(double[] prev, double[] curr, double[] next, Path path, double offset) {

        // นำค่าที่จดปัจจุบันลบกับจุดก่อนหน้า
        double dx1 = curr[0] - prev[0];
        double dy1 = curr[1] - prev[1];
        double dx2 = next[0] - curr[0];
        double dy2 = next[1] - curr[1];

        double len1 = Math.sqrt(dx1 * dx1 + dy1 * dy1); // หารากที่สองของ (dx1^2 + dy1^2)
        double len2 = Math.sqrt(dx2 * dx2 + dy2 * dy2); // หารากที่สองของ (dx2^2 + dy2^2)

        // คำนวณจุดก่อนและหลังโค้ง
        double[] beforeCorner = { curr[0] - dx1 / len1 * offset,
                curr[1] - dy1 / len1 * offset };
        double[] afterCorner = { curr[0] + dx2 / len2 * offset,
                curr[1] + dy2 / len2 * offset };

        // สร้าง path ที่มีความโค้ง โดยใช้ Quadratic Curve
        path.getElements().add(new LineTo(beforeCorner[0], beforeCorner[1]));
        path.getElements().add(new QuadCurveTo(curr[0], curr[1], afterCorner[0], afterCorner[1]));
    }

    // Method สำหรับอัพเดตเส้นทางเพื่อให้เส้นเชื่อมโยงปุ่มที่ถูกเลือก
    public void updatePath(Path path, double offset, Runnable setPathStyle) {
        // เรียกใช้ method setPathStyle เพื่อตั้งค่าความสวยงามของเส้น
        setPathStyle.run();

        // ลบเส้นเก่าออกจาก Path เพื่อให้ Path ใหม่ถูกวาดขึ้นมาแทนที่เส้นเก่า
        path.getElements().clear();

        // ถ้าเลือกแค่ปุ่มเดียว จะไม่วาดเส้น
        if (selectedLetters.size() < 2) {
            return;
        }

        List<double[]> coords = getButtonCoordinates();

        // สร้างเส้นเริ่มต้นที่ปุ่มแรก
        path.getElements().add(new MoveTo(coords.get(0)[0],
                coords.get(0)[1]));

        // Loop ผ่านจุดต่างๆใน coords เพื่อสร้างเส้นตรงหรือเส้นโค้งตามปุ่มที่ถูกเลือก
        for (int i = 1; i < coords.size(); i++) {
            double[] prev = coords.get(i - 1);
            double[] curr = coords.get(i);

            if (i < coords.size() - 1) {
                double[] next = coords.get(i + 1);
                addRoundedCorner(prev, curr, next, path, offset);
            } else {
                path.getElements().add(new LineTo(curr[0], curr[1]));
            }
        }
    }

    // Method สำหรับดึงตัวอักษรจากปุ่มที่ถูกเลือก
    public String getLetterFromButton(Button button) {
        if (button != null && button.getGraphic() instanceof StackPane) {
            StackPane stack = (StackPane) button.getGraphic();
            if (!stack.getChildren().isEmpty() && stack.getChildren().get(0) instanceof Label) {
                Label label = (Label) stack.getChildren().get(0);
                return label.getText();
            }
        }
        return "";
    }

    // Method สำหรับเปลี่ยนตัวอักษรในปุ่มที่ถูกเลือก
    public void replaceLettersInButtons() {

        for (Button button : getSelectedLetters()) {

            String newLetter = getRandomLetter();

            if (button.getGraphic() instanceof StackPane) {
                StackPane stack = (StackPane) button.getGraphic();
                if (!stack.getChildren().isEmpty() && stack.getChildren().get(0) instanceof Label) {
                    Label letter = (Label) stack.getChildren().get(0);
                    Label dmg = (Label) stack.getChildren().get(1);
                    letter.setText(newLetter);
                    dmg.setText(String.valueOf(getLetterDamage(newLetter)));
                }
            }
        }
    }

    // Method สำหรับสุ่มตัวอักษร A-Z โดยมีน้ำหนักของตัวอักษร
    public String getRandomLetter() {
        String weightedLetters = "AAAAAAEEEEEEEIIIIIOOOOOUUUUNNNRRRRSSSSTTTTLLLCDDMMGHAPBBFVWYJKXQZ";
        int index = (int) (Math.random() * weightedLetters.length());
        return String.valueOf(weightedLetters.charAt(index));
    }

    // Method สำหรับตั้งค่า isSelecting
    public void setSelecting(boolean value) {
        isSelecting = value;
    }

    // Method สำหรับเคลียร์คำใน selectedLetters
    public void clearSelection() {
        selectedLetters.clear();
    }

    // Method สำหรับ push ปุ่มที่ถูกเลือกเข้าไปใน Stack
    // และตั้งค่า lastSelectedButton เป็นปุ่มที่ถูกเลือกล่าสุด
    public void pushLetter(Button button) {
        selectedLetters.push(button);
        lastSelectedButton = button;
    }

    // Method สำหรับ pop ปุ่มที่ถูกเลือกออกจาก Stack
    // และตั้งค่า lastSelectedButton เป็นปุ่มที่บนสุด
    public void popLetter() {
        selectedLetters.pop();
        lastSelectedButton = selectedLetters.peek();
    }

    // Method สำหรับดึงปุ่มที่ถูกเลือกล่าสุด
    public Button getLastSelectedButton() {
        return lastSelectedButton;
    }

    // Method สำหรับเช็คว่ากำลังเลือกปุ่มอยู่หรือไม่
    public boolean isSelecting() {
        return isSelecting;
    }

    // Method สำหรับดึง Stack ของ selected letters
    public Stack<Button> getSelectedLetters() {
        return selectedLetters;
    }

}
