package cpe112.finalproject.Handlers;

import cpe112.finalproject.Data.WordDictionary;
import cpe112.finalproject.Logic.CombatLogic;
import cpe112.finalproject.Logic.HealthControllerLogic;
import cpe112.finalproject.Logic.ParameterHelper;
import cpe112.finalproject.Logic.PuzzleLogic;
import cpe112.finalproject.Managers.PlayerManager;
import cpe112.finalproject.Managers.SoundManager;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

/*
 * MouseEventHandler.java
 * สำหรับจัดการ Event ของ Mouse
 */

public class MouseEventHandler {

    // สร้างตัวแปร puzzleLogic เพื่อใช้ในการจัดการ Logic ของเกม
    private final PuzzleLogic puzzleLogic;
    // สร้างตัวแปร combatLogic เพื่อใช้ในการจัดการ Logic ของการต่อสู้
    private final CombatLogic combatLogic;

    // สร้างตัวแปร playerManager เพื่อใช้ในการจัดการผู้เล่น
    PlayerManager playerManager = PlayerManager.getInstance();

    // Constructor สำหรับ MouseEventHandler
    public MouseEventHandler(PuzzleLogic puzzleLogic, CombatLogic combatLogic) {
        this.puzzleLogic = puzzleLogic;
        this.combatLogic = combatLogic;
    }

    // Mouse Event สำหรับ การคลิกแล้วลากไปตามปุ่มของ Mouse
    // โดยใช้ Behavior onMouseDragEntered
    public void onMouseDragEntered(MouseEvent event, ParameterHelper context) {
        Button enteredButton = (Button) event.getSource();

        // เรียกใช้ method applyHoverEffect
        // เพื่อให้ปุ่มมี effect เมื่อ Mouse เลื่อนไปบนปุ่ม
        context.hoverEffect.accept(enteredButton);

        if (!puzzleLogic.isSelecting()) {
            // เช็คว่าเป็นการเลือกปุ่มครั้งแรกหรือไม่
            // ถ้าใช่ ให้ isSelecting = true แปลว่า เรากำลังเลือกปุ่มแล้ว
            // หลังจากนั้น push ตัวอักษรตัวแรกเข้าสู่ stack selectedLetters
            puzzleLogic.setSelecting(true);
            puzzleLogic.clearSelection();
            puzzleLogic.pushLetter(enteredButton);
            puzzleLogic.setLastSelectedButton(enteredButton);
            enteredButton.setStyle(context.selectedStyle);
            context.DropShadow.accept(enteredButton);
            String word = puzzleLogic.getSelectedWord();
            combatLogic.setWordFormed(word);
            SoundManager.playClassSelectSound();
            System.out.println("Selected: " + puzzleLogic.getLetterFromButton(enteredButton));
        } else {
            if (puzzleLogic.isBacktracking(enteredButton)) {
                // เช็คว่าเป็นการ Backtrack หรือไม่
                // ถ้า backtrack ก็ทำการ pop ออกจาก stack seletecdLetters
                Button removedButton = puzzleLogic.getSelectedLetters().pop();
                removedButton.setStyle(context.unselectedStyle);
                removedButton.setEffect(null);
                puzzleLogic.setLastSelectedButton(enteredButton);
                puzzleLogic.updatePath(context.path, context.offset, context.pathStyle);
                String word = puzzleLogic.getSelectedWord();
                combatLogic.setWordFormed(word);
                SoundManager.playClassSelectSound();
                System.out.println("Backtracking: " + puzzleLogic.getLetterFromButton(removedButton));

            } else if (puzzleLogic.canSelect(enteredButton)) {
                // เช็คว่าปุ่มถัดไป สามารถเลือกได้ หรือไม่
                // ถ้าเลือกได้ ก็ทำการ push เข้าสู่ stack selectedLetters
                puzzleLogic.pushLetter(enteredButton);
                enteredButton.setStyle(context.selectedStyle);
                puzzleLogic.setLastSelectedButton(enteredButton);
                context.DropShadow.accept(enteredButton);
                puzzleLogic.updatePath(context.path, context.offset, context.pathStyle);
                String word = puzzleLogic.getSelectedWord();
                combatLogic.setWordFormed(word);
                SoundManager.playClassSelectSound();
                System.out.println("Selected: " + puzzleLogic.getLetterFromButton(enteredButton));
            }
        }
    }

    // Mouse Event สำหรับ การปล่อยคลิกของ Mouse
    // โดยใช้ Behavior onMouseReleased
    public void onMouseReleased(MouseEvent event, ParameterHelper context) {
        if (puzzleLogic.isSelecting()) {
            // เมื่อปล่อย Mouse แล้ว จะเช็คว่าปุ่มกำลังถูกเลือกหรือไม่
            // ถ้าใช่ จะกำลัง สร้าง String มารองรับคำที่ได้จากการเลือก
            double totalDamage = 0;
            String word = puzzleLogic.getSelectedWord();

            // เช็คว่าคำที่ได้มีความยาวมากกว่า 1 ตัวอักษร และ ถูกต้องตาม Dictionary หรือไม่
            if (word.length() > 1 && WordDictionary.isValidWord(word)) {
                // คำนวณ damage ที่ได้จากคำๆนั้น
                for (char c : word.toCharArray()) {
                    totalDamage += puzzleLogic.getLetterDamage(String.valueOf(c)) * playerManager.getAttack();
                }
                System.out.println("Word: " + word + " Damage: " + totalDamage);

                // ทำการเล่นเสียงโจมตี
                SoundManager.playHitSound();

                // ทำการลด HP ของศัตรูลงตาม damage ที่ได้จากคำที่เลือก
                HealthControllerLogic.damageEnemy(totalDamage);

                // ทำการเปลี่ยนตัวอักษรในปุ่มที่เลือกให้เป็นตัวอักษรที่ถูกต้อง
                puzzleLogic.replaceLettersInButtons();

            } else {
                SoundManager.playErrorSound();
            }

            // ทำการตั้งค่าปุ่มให้กลับไปเป็น Default
            for (Button button : puzzleLogic.getSelectedLetters()) {
                button.setStyle(context.unselectedStyle);
                button.setEffect(null);
                context.resetHoverEffect.accept(button);
            }

            // ลบเส้น path ที่ลากตามปุ่มทั้งหมด
            context.path.getElements().clear();
            puzzleLogic.clearSelection();
            puzzleLogic.setSelecting(false);
            combatLogic.setWordFormed("");
        }
    }

}
