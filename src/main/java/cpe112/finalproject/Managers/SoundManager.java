package cpe112.finalproject.Managers;

import cpe112.finalproject.Constants.Path;
import javafx.scene.control.Slider;
import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

/*
 * SoundManager.java
 * สำหรับจัดการเสียงต่างๆ ในเกม
 * 
 * AudioClip ใช้สำหรับเสียงสั้นๆ เช่น เสียงคลิก เสียงเลือกคลาส
 * MediaPlayer ใช้สำหรับเสียงยาวๆ เช่น เพลงประกอบเกม
 */

public class SoundManager {

    // สร้างตัวแปร Media และ MediaPlayer สำหรับเพลงประกอบเกม
    private static final Media media = new Media(
            SoundManager.class.getResource(Path.BACKGROUND_MUSIC).toExternalForm());
    private static final MediaPlayer mediaPlayer;

    // สร้างตัวแปร Media และ MediaPlayer สำหรับเพลงประกอบการต่อสู้
    private static final Media battleMedia = new Media(
            SoundManager.class.getResource(Path.BATTLE_MUSIC).toExternalForm());
    private static final MediaPlayer battleMediaPlayer = new MediaPlayer(battleMedia);

    // Static สำหรับรันครั้งเดียวเมื่อโหลด class
    static {
        mediaPlayer = new MediaPlayer(media); // สร้าง MediaPlayer จาก Media ที่โหลดมา
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE); // ตั้งค่าให้เล่นวนซ้ำตลอดเวลา
        mediaPlayer.setVolume(0.05); // ตั้งค่าให้เล่นเสียงเบาๆ
        mediaPlayer.setAutoPlay(true); // ตั้งค่าให้เล่นเสียงเมื่อเริ่มเกม
        mediaPlayer.play();
    }

    // Method สำหรับเชื่อมต่อ Slider กับ MediaPlayer เพื่อปรับระดับเสียง
    public static void setupVolumeSlider(Slider volumeSlider) {
        volumeSlider.valueProperty().addListener((obs, oldVal, newVal) -> {
            mediaPlayer.setVolume(newVal.doubleValue());
        });
    }

    // Method สำหรับหยุดเพลงประกอบเกม และเล่นเพลงประกอบการต่อสู้
    public static void playBattleTheme() {
        mediaPlayer.pause();
        battleMediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        battleMediaPlayer.setVolume(mediaPlayer.getVolume());
        battleMediaPlayer.play();
    }

    // Class สำหรับจัดการเสียง Sound Effect เช่น เสียงคลิก เสียงเลือกคลาส
    public static class SoundEffect {

        // สร้างตัวแปร AudioClip สำหรับเก็บเสียง
        // สร้างตัวแปร double สำหรับเก็บระดับเสียง
        private final AudioClip sound;
        private final double volume;

        // สร้าง Constructor สำหรับสร้างเสียง Sound Effect
        public SoundEffect(String path, double volume) {
            // โหลดเสียงจากไฟล์ที่กำหนดใน path
            this.sound = new AudioClip(SoundManager.class.getResource(path).toExternalForm());
            this.volume = volume;
        }

        // Method สำหรับเล่นเสียง Sound Effect
        public void play() {
            sound.setVolume(volume);
            sound.stop();
            sound.play();
        }
    }

    // เสียงเลือกคลาส
    private static final SoundEffect classSelectSound = new SoundEffect(Path.CLASS_SELECT_SOUND, 0.2);

    // เสียงคลิก
    private static final SoundEffect clickSound = new SoundEffect(Path.CLICK_SOUND, 0.5);

    // เสียงเมื่อกรณีมีข้อผิดพลาด เช่น กรอกชื่อไม่ถูกต้อง
    private static final SoundEffect hoverSound = new SoundEffect(Path.HOVER_SOUND, 0.5);

    // เสียงเมื่อเลื่อนเมาส์ไปบนปุ่ม
    private static final SoundEffect errorSound = new SoundEffect(Path.ERROR_SOUND, 0.1);

    // เสียงเมื่อโจมตี
    private static final SoundEffect hitSound = new SoundEffect(Path.HIT_SOUND, 0.5);

    // ============================================================
    // ------------------- Method สำหรับเล่นเสียงต่างๆ -----------------
    // ============================================================
    public static void playHoverSound() {
        hoverSound.play();
    }

    public static void playClickSound() {
        clickSound.play();
    }

    public static void playClassSelectSound() {
        classSelectSound.play();
    }

    public static void playErrorSound() {
        errorSound.play();
    }

    public static void playHitSound() {
        hitSound.play();
    }
    // ============================================================
}
