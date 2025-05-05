package cpe112.finalproject.Layout;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpe112.finalproject.Constants.Style;
import cpe112.finalproject.Data.WordDictionary;
import cpe112.finalproject.Handlers.MouseEventHandler;
import cpe112.finalproject.Logic.ParameterHelper;
import cpe112.finalproject.Logic.PuzzleLogic;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ScaleTransition;
import javafx.animation.Timeline;
import javafx.beans.binding.Bindings;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Path;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.util.Duration;

/*
 * PuzzleUI.java
 * สำหรับสร้าง UI ของ Puzzle
 */

public class PuzzleUI {

    private final PuzzleLogic puzzleLogic; // Logic ของ Puzzle
    private final MouseEventHandler mouseHandler; // MouseEventHandler สำหรับจัดการ Event ของ Mouse

    // JavaFX Components ของ PuzzleUI
    private final StackPane rootPane = new StackPane();
    private final GridPane puzzlePane = new GridPane();
    private final Pane linePane = new Pane();
    public final Path currentPath = new Path();

    // Constants สำหรับการตั้งค่า Layout ของ PuzzleUI
    private static final int ROW = 5; // จำนวนแถวใน Puzzle
    private static final int COLUMN = 12; // จำนวนคอลัมน์ใน Puzzle
    private static final double OFFSET_ROUND_CORNER = 15; // ขนาดของมุมโค้ง
    private static final double GAP_SIZE_MULTIPLIER = 0.01; // ขนาดของช่องว่างระหว่างปุ่ม
    private static final double FONT_SIZE_MULTIPLIER = 0.4; // ขนาดของฟอนต์
    private static final double TOP_BOTTOM_PADDING_MULTIPLIER = 0.02; // ขนาดของ Padding ด้านบนและล่าง
    private static final double LEFT_RIGHT_PADDING_MULTIPLIER = 0.01; // ขนาดของ Padding ด้านซ้ายและขวา

    // Constructor สำหรับ PuzzleUI
    public PuzzleUI(PuzzleLogic puzzleLogic, MouseEventHandler mouseHandler) {
        this.mouseHandler = mouseHandler;
        this.puzzleLogic = puzzleLogic;
        setupLayout();
    }

    // Method สำหรับสร้างและตั้งค่า UI ของ Puzzle
    private void setupLayout() {
        // ตั้งค่า Background Animation
        AnimationBackground.applyAnimatedGradient(rootPane);

        // ตั้งค่า Layout ของ UI
        rootPane.getChildren().addAll(puzzlePane, linePane);
        StackPane.setAlignment(puzzlePane, Pos.CENTER);
        linePane.setMouseTransparent(true); // ทำให้ไม่สามารถคลิกได้

        // ตั้งค่าขนาดของระยะห่างระหว่างปุ่ม
        puzzlePane.hgapProperty().bind(rootPane.widthProperty().multiply(GAP_SIZE_MULTIPLIER));
        puzzlePane.vgapProperty().bind(puzzlePane.hgapProperty());

        // ตั้งค่าขนาดของ Padding ด้านบนและล่าง
        puzzlePane.paddingProperty().bind(Bindings.createObjectBinding(() -> new Insets(
                rootPane.getHeight() * TOP_BOTTOM_PADDING_MULTIPLIER,
                rootPane.getWidth() * LEFT_RIGHT_PADDING_MULTIPLIER,
                rootPane.getHeight() * TOP_BOTTOM_PADDING_MULTIPLIER,
                rootPane.getWidth() * LEFT_RIGHT_PADDING_MULTIPLIER),
                rootPane.heightProperty(), rootPane.widthProperty()));
        VBox.setVgrow(rootPane, Priority.ALWAYS);

        // วนลูปสร้าง ColumnConstraints และ RowConstraints สำหรับ GridPane
        // โดยใช้ PercentWidth และ PercentHeight
        // เพื่อให้ GridPane ปรับขนาดตามขนาดของหน้าจอ
        // และให้ปุ่มแต่ละปุ่มมีขนาดเท่ากัน
        for (int i = 0; i < COLUMN; i++) {
            ColumnConstraints col = new ColumnConstraints();
            col.setPercentWidth(100.0 / COLUMN);
            puzzlePane.getColumnConstraints().add(col);
        }
        for (int i = 0; i < ROW; i++) {
            RowConstraints row = new RowConstraints();
            row.setPercentHeight(100.0 / ROW);
            puzzlePane.getRowConstraints().add(row);
        }

        // สร้าง List ของตัวอักษรที่จะใช้ใน Puzzle
        List<String> letters = initializeGrid();

        // สร้างปุ่มสำหรับแต่ละตัวอักษรใน Puzzle
        int index = 0;
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COLUMN; col++) {
                String letter = letters.get(index++); // ดึงตัวอักษรจาก List
                Button letterButton = createLetterButton(letter); // สร้างปุ่มสำหรับตัวอักษร
                puzzlePane.add(letterButton, col, row); // เพิ่มปุ่มลงใน GridPane
                setButtonEvents(letterButton); // ตั้งค่า Event สำหรับปุ่ม
            }
        }

        linePane.getChildren().add(currentPath); // เพิ่ม Path ลงใน Pane

        // ตั้งค่าขนาดของ PuzzlePane
        puzzlePane.prefWidthProperty().bind(rootPane.widthProperty());
        puzzlePane.prefHeightProperty().bind(rootPane.heightProperty());
    }

    // Method สำหรับสร้าง List ของตัวอักษรที่จะใช้ใน Puzzle
    private List<String> initializeGrid() {
        int totalCells = ROW * COLUMN; // จำนวนเซลล์ทั้งหมดใน Puzzle
        char[][] grid = new char[ROW][COLUMN]; // สร้าง Grid 2D สำหรับเก็บตัวอักษร
        Random rand = new Random(); // สุ่มตัวอักษร
        int guaranteedWordCount = 5; // จำนวนคำเริ่มต้นอย่างน้อยที่จะมีใน Puzzle

        // สร้าง List สำหรับคำที่ต้องการให้มีใน Puzzle โดยสุ่มจาก WordDictionary
        List<String> guaranteedWords = WordDictionary.getRandomValidWords(
                guaranteedWordCount, 3, Math.min(ROW, COLUMN));

        // วนลูปเพื่อวางคำที่ต้องการใน Grid ลงให้หมดเรียบร้อยก่อน
        for (String word : guaranteedWords) {
            int wordLength = word.length();
            boolean placed = false;

            // ทดลองวางคำใน Grid โดยสุ่มตำแหน่งและทิศทาง
            // หากไม่สามารถวางได้จะลองใหม่จนกว่าจะครบ 100 ครั้ง
            for (int attempt = 0; attempt < 100 && !placed; attempt++) {
                int startRow = rand.nextInt(ROW);
                int startCol = rand.nextInt(COLUMN);

                // กำหนดทิศทางที่สามารถวางคำได้
                int[][] directions = {
                        { 0, 1 }, // ไปทางขวา
                        { 1, 0 }, // แนวตั้งลง
                        { 1, 1 }, // แนวทแยงลงขวา
                        { -1, 1 }, // แนวทแยงขึ้นขวา
                };

                // หาว่าทิศทางไหนที่สามารถวางคำได้พอดี
                List<int[]> validDirs = new ArrayList<>(); // เก็บทิศทางที่สามารถวางได้
                // วนลูปเพื่อหาทิศทางที่สามารถวางคำได้
                for (int[] dir : directions) {
                    int endRow = startRow + dir[0] * (wordLength - 1); // คำนวณตำแหน่งสุดท้ายของคำ
                    int endCol = startCol + dir[1] * (wordLength - 1); // คำนวณตำแหน่งสุดท้ายของคำ

                    // ถ้าตำแหน่งสุดท้ายที่คำ จะวางอยู่ใน Grid อยู่ในขอบเขต
                    // ก็ให้เก็บทิศทางนั้นไว้
                    if (endRow >= 0 && endRow < ROW && endCol >= 0 && endCol < COLUMN) {
                        validDirs.add(dir);
                    }
                }

                // ถ้ามีทิศทางที่สามารถวางได้ ก็สุ่มเลือกทิศทางนั้น
                if (!validDirs.isEmpty()) {
                    int[] chosenDir = validDirs.get(rand.nextInt(validDirs.size())); // สุ่มเลือกทิศทาง
                    int dRow = chosenDir[0]; // ทิศทางแถว
                    int dCol = chosenDir[1]; // ทิศทางคอลัมน์

                    boolean canPlace = true; // เช็คว่าคำสามารถวางได้หรือไม่

                    // วนลูปเพื่อตรวจสอบว่าคำสามารถวางได้หรือไม่
                    for (int i = 0; i < wordLength; i++) {
                        int r = startRow + i * dRow; // คำนวณตำแหน่งแถว
                        int c = startCol + i * dCol; // คำนวณตำแหน่งคอลัมน์

                        // ถ้าตำแหน่งนั้นมีตัวอักษรอยู่แล้ว และไม่ตรงกับตัวอักษรในคำ
                        if (grid[r][c] != '\0' && grid[r][c] != word.charAt(i)) {
                            canPlace = false; // ไม่สามารถวางคำได้
                            break;
                        }
                    }

                    // ถ้าสามารถวางคำได้ ก็ทำการวางคำใน Grid
                    if (canPlace) {
                        // วางคำใน Grid โดยใช้ตำแหน่งเริ่มต้นและทิศทางที่เลือก
                        for (int i = 0; i < wordLength; i++) {
                            int r = startRow + i * dRow; // คำนวณตำแหน่งแถว
                            int c = startCol + i * dCol; // คำนวณตำแหน่งคอลัมน์
                            grid[r][c] = word.charAt(i); // วางตัวอักษรใน Grid
                        }
                        placed = true; // ตั้งค่าว่า คำถูกวางแล้ว
                    }
                }
            }
        }

        // หลังจากวางคำที่ต้องการใน Grid เสร็จแล้ว
        // จะทำการสุ่มตัวอักษรที่เหลือใน Grid ให้เต็ม
        // โดยใช้ตัวอักษร A-Z
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COLUMN; col++) {
                // ถ้าตำแหน่งนั้นยังไม่มีตัวอักษรอยู่
                if (grid[row][col] == '\0') {
                    grid[row][col] = puzzleLogic.getRandomLetter().charAt(0); // สุ่มตัวอักษร A-Z
                }
            }
        }

        // สร้าง List ของตัวอักษรจาก Grid 2D
        List<String> letters = new ArrayList<>(totalCells);
        // วนลูปเพื่อเพิ่มตัวอักษรจาก Grid ลงใน List
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COLUMN; col++) {
                letters.add(String.valueOf(grid[row][col])); // แปลงตัวอักษรเป็น String
            }
        }

        return letters; // คืนค่า List ของตัวอักษร
    }

    // Method สำหรับสร้างปุ่มสำหรับตัวอักษร
    private Button createLetterButton(String letter) {

        Label mainLabel = new Label(letter); // สร้าง Label สำหรับแสดงตัวอักษร
        // สร้าง Label สำหรับแสดง Damage ของตัวอักษร
        Label dmgLabel = new Label(String.valueOf(puzzleLogic.getLetterDamage(letter)));
        // สร้าง StackPane สำหรับรองรับ Label ทั้งสอง
        StackPane content = new StackPane(mainLabel, dmgLabel);
        // ตั้งค่าการจัดตำแหน่งของ dmgLabel ให้อยู่มุมล่างขวา
        StackPane.setAlignment(dmgLabel, Pos.BOTTOM_RIGHT);

        // สร้างปุ่มสำหรับตัวอักษร
        Button button = new Button();
        button.setGraphic(content); // ตั้งค่า Graphic ของปุ่มเป็น StackPane
        button.setStyle(Style.PUZZLE_BUTTON_UNSELECTED_STYLE); // ตั้งค่า Style ของปุ่ม
        button.setMaxSize(Double.MAX_VALUE, Double.MAX_VALUE); // ตั้งค่าขนาดสูงสุดของปุ่ม

        GridPane.setHgrow(button, Priority.ALWAYS); // ตั้งค่าการขยายของปุ่มในแนวนอน
        GridPane.setVgrow(button, Priority.ALWAYS); // ตั้งค่าการขยายของปุ่มในแนวตั้ง

        // ตั้งค่าให้ปุ่มมีขนาดเปลี่ยนแปลงตามขนาดของหน้าจอโดยอัตโนมัติ
        button.widthProperty().addListener(
                (obs, oldVal, newVal) -> updateFontSize(mainLabel, dmgLabel, newVal.doubleValue(), button.getHeight()));
        button.heightProperty().addListener(
                (obs, oldVal, newVal) -> updateFontSize(mainLabel, dmgLabel, button.getWidth(), newVal.doubleValue()));

        return button; // คืนค่า Button ที่สร้างขึ้น
    }

    // Method สำหรับอัพเดทขนาดฟอนต์ของ Label
    private void updateFontSize(Label mainLabel, Label dmgLabel, double width, double height) {
        double fontSize = Math.min(width, height) * FONT_SIZE_MULTIPLIER; // คำนวณขนาดฟอนต์
        mainLabel.setStyle("-fx-font-size: " + fontSize + "px; -fx-font-weight: bold;"); // ตั้งค่าฟอนต์ของ Label หลัก
        dmgLabel.setStyle("-fx-font-size: " + (fontSize * 0.5) + "px; -fx-text-fill: #444;"); // ตั้งค่าฟอนต์ของ Label
                                                                                              // Damage
    }

    // Method สำหรับตั้งค่า Effect ของปุ่มเมื่อมีการ Hover
    private void applyHoverEffect(Button button) {
        // ตั้งค่า Effect ของปุ่มเมื่อมีการ Hover
        ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
        st.setToX(1.1); // ขยายขนาดปุ่มในแนวนอน
        st.setToY(1.1); // ขยายขนาดปุ่มในแนวตั้ง
        st.setAutoReverse(true); // ตั้งค่าให้มีการ Reverse
        st.setCycleCount(2); // ตั้งค่าให้ทำการขยายและย่อ 2 ครั้ง
        st.playFromStart();
    }

    // Method สำหรับตั้งค่า Effect ของปุ่มเมื่อไม่มีการ Hover
    private void resetHoverEffect(Button button) {
        ScaleTransition st = new ScaleTransition(Duration.millis(100), button);
        st.setToX(1.0); // ย่อขนาดปุ่มในแนวนอน
        st.setToY(1.0); // ย่อขนาดปุ่มในแนวตั้ง
        st.playFromStart();
    }

    // Method สำหรับตั้งค่า DropShadow Effect ของปุ่ม
    private void setDropShadowEffect(Button button) {
        DropShadow dropShadow = new DropShadow(); // สร้าง DropShadow Effect
        dropShadow.setRadius(10.0); // รัศมีของ DropShadow
        dropShadow.setOffsetX(0); // ระยะในแนวนอน
        dropShadow.setOffsetY(0); // ระยะในแนวตั้ง
        dropShadow.setSpread(0.3); // การกระจายของ DropShadow
        dropShadow.setColor(Color.rgb(255, 204, 0, 0.6));
        button.setEffect(dropShadow);
    }

    // Method สำหรับตั้งค่า Style ของ Path
    private void setPathStyle() {
        // คำนวณสีของ Path โดยคำนวณจากจำนวนตัวอักษรที่เลือก
        double progress = (double) puzzleLogic.getSelectedLetters().size() / (ROW * COLUMN);
        Color dynamicColor = Color.color(1.0 - progress, 0.3, progress);

        // ตั้งค่า Style ต่างๆของ Path
        currentPath.setStroke(dynamicColor);
        currentPath.setStrokeWidth((puzzlePane.getWidth() * puzzlePane.getHeight()) / 80000);
        currentPath.setStrokeLineJoin(StrokeLineJoin.ROUND);
        currentPath.setStrokeLineCap(StrokeLineCap.ROUND);
        currentPath.getStrokeDashArray().setAll(
                (puzzlePane.getWidth() * puzzlePane.getHeight() / 25000),
                (puzzlePane.getWidth() * puzzlePane.getHeight() / 35000));

        // ตั้งค่า Dash Animation ของ Path
        Timeline dashAnimation = new Timeline(
                new KeyFrame(Duration.ZERO, new KeyValue(currentPath.strokeDashOffsetProperty(), 15)),
                new KeyFrame(Duration.seconds(1), new KeyValue(currentPath.strokeDashOffsetProperty(), 0)));
        dashAnimation.setCycleCount(Animation.INDEFINITE);
        dashAnimation.play();
    }

    // Method สำหรับตั้งค่า Event ของปุ่ม
    private void setButtonEvents(Button button) {
        // สร้าง ParameterHelper สำหรับเก็บข้อมูลต่างๆ
        ParameterHelper context = new ParameterHelper(
                currentPath,
                Style.PUZZLE_BUTTON_SELECTED_STYLE,
                Style.PUZZLE_BUTTON_UNSELECTED_STYLE,
                OFFSET_ROUND_CORNER,
                this::applyHoverEffect,
                this::resetHoverEffect,
                this::setPathStyle,
                this::setDropShadowEffect);

        // ตั้งค่า Event ของปุ่ม
        button.setOnDragDetected(e -> button.startFullDrag());
        button.setOnMouseDragEntered(e -> mouseHandler.onMouseDragEntered(e, context));
        button.setOnMouseReleased(e -> mouseHandler.onMouseReleased(e, context));

        // ตั้งค่า Effect ของปุ่มเมื่อมีการ Hover
        ScaleTransition st = new ScaleTransition(Duration.millis(50), button);
        button.setOnMouseEntered(e -> {
            st.setToX(1.1);
            st.setToY(1.1);
            st.playFromStart();
        });
        button.setOnMouseExited(e -> {
            st.setToX(1.0);
            st.setToY(1.0);
            st.playFromStart();
        });
    }

    // ============================================================
    // -------------- Getter สำหรับการเข้าถึง Object ต่างๆ -------------
    // ============================================================
    public StackPane getRootPane() {
        return rootPane;
    }

    public GridPane getPuzzlePane() {
        return puzzlePane;
    }

    public Pane getLinePane() {
        return linePane;
    }
    // ============================================================
}
