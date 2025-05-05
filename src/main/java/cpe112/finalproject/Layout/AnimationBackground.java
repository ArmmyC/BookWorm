package cpe112.finalproject.Layout;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.geometry.Insets;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.util.Duration;

/*
 * BackgroundAnimator.java 
 * สำหรับการสร้าง Animation ของ Background ของ PuzzleUI
 */

public class AnimationBackground {

        // Method สำหรับการสร้าง Animation ของ Background
        // โดยจะใช้ LinearGradient เพื่อสร้าง gradient ที่เคลื่อนไหวได้
        public static void applyAnimatedGradient(Pane pane) {

                // สร้าง Stop สำหรับ gradient
                Stop[] stop1 = new Stop[] {
                                new Stop(0, Color.rgb(194, 233, 251)),
                                new Stop(1, Color.rgb(161, 196, 253))
                };

                // สร้าง Stop สำหรับ gradient
                Stop[] stop2 = new Stop[] {
                                new Stop(0, Color.rgb(161, 196, 253)),
                                new Stop(1, Color.rgb(194, 233, 251)),
                };

                // สร้าง LinearGradient
                LinearGradient gradient1 = new LinearGradient(
                                0, 0, 1, 1, true, CycleMethod.NO_CYCLE, stop1);

                // สร้าง LinearGradient
                LinearGradient gradient2 = new LinearGradient(
                                1, 0, 0, 1, true, CycleMethod.NO_CYCLE, stop2);

                // สร้าง BackgroundFill ด้วย gradient1 เพื่อเป็น background เริ่มต้น
                BackgroundFill initialFill = new BackgroundFill(
                                gradient1, CornerRadii.EMPTY, Insets.EMPTY);
                pane.setBackground(new Background(initialFill));

                // สร้าง timeline สำหรับทำ animation
                // ค่อยๆเปลี่ยนจาก gradient1 -> gradient2 -> gradient1
                Timeline timeline = new Timeline(
                                new KeyFrame(Duration.ZERO, event -> {
                                        BackgroundFill fill = new BackgroundFill(
                                                        gradient1, CornerRadii.EMPTY, Insets.EMPTY);
                                        pane.setBackground(new Background(fill));
                                }),
                                new KeyFrame(Duration.seconds(2.5), event -> {
                                        BackgroundFill fill = new BackgroundFill(
                                                        gradient2, CornerRadii.EMPTY, Insets.EMPTY);
                                        pane.setBackground(new Background(fill));
                                }),
                                new KeyFrame(Duration.seconds(5), event -> {
                                        BackgroundFill fill = new BackgroundFill(
                                                        gradient1, CornerRadii.EMPTY, Insets.EMPTY);
                                        pane.setBackground(new Background(fill));
                                }));

                // ตั้งค่าให้ timeline ทำงานวนซ้ำไปเรื่อยๆ
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
        }
}
