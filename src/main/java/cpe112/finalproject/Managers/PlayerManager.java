package cpe112.finalproject.Managers;

/*
 * PlayerManager.java
 * สำหรับจัดการข้อมูลของผู้เล่น
 * 
 * เป็น Singleton Pattern เพื่อให้สามารถเข้าถึงได้จากที่อื่นๆ
 * โดยไม่ต้องสร้าง Instance ใหม่ทุกครั้ง
 */

public final class PlayerManager {

    // สร้าง Instance ของ PlayerManager
    private static final PlayerManager instance = new PlayerManager();

    // Getter สำหรับการเข้าถึง Instance ของ PlayerManager
    public static PlayerManager getInstance() {
        return instance;
    }

    // สร้างตัวแปรสำหรับเก็บข้อมูลของผู้เล่น
    private String playerName; // ชื่อผู้เล่น
    private String className; // ชื่อคลาสของผู้เล่น
    private double health; // ค่าพลังชีวิตของผู้เล่น
    private double attack; // ค่าพลังโจมตีของผู้เล่น
    private double defense; // ค่าพลังป้องกันของผู้เล่น

    // Method สำหรับตั้งค่าข้อมูลของผู้เล่น
    public void setPlayerData(String playerName, String className, double health, double attack, double defense) {
        this.playerName = playerName;
        this.className = className;
        this.health = health;
        this.attack = attack;
        this.defense = defense;
    }

    // Method สำหรับตั้งค่าพลังชีวิตของผู้เล่น โดยไม่ให้ต่ำกว่า 0
    public void setHealth(double newHealth) {
        this.health = Math.max(0, newHealth);
    }

    // Method สำหรับ reset ข้อมูลของผู้เล่น
    public void reset() {
        playerName = null;
        className = null;
        health = 0;
        attack = 0;
        defense = 0;
    }

    // ============================================================
    // ------------ Getter สำหรับการเข้าถึง Object ต่างๆ ---------------
    // ============================================================
    public String getPlayerName() {
        return playerName;
    }

    public String getClassName() {
        return className;
    }

    public double getHealth() {
        return health;
    }

    public double getAttack() {
        return attack;
    }

    public double getDefense() {
        return defense;
    }
    // ============================================================
}
