package cpe112.finalproject.Logic;

import java.util.function.Consumer;
import javafx.scene.control.Button;
import javafx.scene.shape.Path;

/*
 * ไฟล์นี้สร้างขึ้นมาเพื่อช่วยจัดการ Parameter ทีมากเกินไป
 * ใช้ใน method onMouseDragEntered - PuzzeLogic.java
 * onMouseDragExited - PuzzeLogic.java
 * setupButtonEvents - GUI.java
 */

public class ParameterHelper {

    // ParameterHelper class สำหรับเก็บค่าต่างๆ ที่ใช้ใน method
    // เนื่องจากมีการส่ง parameter ที่มากเกินไป
    public final Path path;
    public final String selectedStyle;
    public final String unselectedStyle;
    public final double offset;
    public final Consumer<Button> hoverEffect;
    public final Consumer<Button> resetHoverEffect;
    public final Runnable pathStyle;
    public final Consumer<Button> DropShadow;

    // Constructor สำหรับสร้าง ParameterHelper
    public ParameterHelper(Path path, String selectedStyle, String unselectedStyle, double offset,
            Consumer<Button> hoverEffect, Consumer<Button> resetHoverEffect, Runnable pathStyle,
            Consumer<Button> DropShadow) {
        this.path = path;
        this.selectedStyle = selectedStyle;
        this.unselectedStyle = unselectedStyle;
        this.offset = offset;
        this.hoverEffect = hoverEffect;
        this.resetHoverEffect = resetHoverEffect;
        this.pathStyle = pathStyle;
        this.DropShadow = DropShadow;
    }
}
