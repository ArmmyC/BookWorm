package cpe112.finalproject.Data;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/*
 * WordDictionary.java สำหรับเก็บคำศัพท์จาก dictionary file ไว้บน local
 * ใช้ AVL Tree ในการเก็บคำศัพท์
 */

public class WordDictionary {

    // ตำแหน่งของไฟล์ Dictionary ที่ใช้เก็บคำศัพท์
    private static final String DICTIONARY_FILE = "/cpe112/finalproject/words.txt";

    // สร้าง AVL Tree สำหรับเก็บคำศัพท์
    private static final AVLTree dictionaryTree = new AVLTree();

    // สร้าง List สำหรับเก็บคำศัพท์ที่สุ่มได้
    private static final List<String> wordList = new ArrayList<>();

    // อ่านคำศัพท์จากไฟล์ Dictionary และเก็บลงใน AVL Tree
    static {
        // สร้าง Logger สำหรับ log ข้อความต่างๆ ลงใน jar หลังจากที่ build project แล้ว
        Logger logger = Logger.getLogger(WordDictionary.class.getName());
        // InputStream เลือกไฟล์คำศัพท์ โดยมี path ของไฟล์อยู่ใน resources ของโปรเจค
        // BufferedReader สำหรับอ่านไฟล์คำศัพท์แต่ละบรรทัด
        try (InputStream stream = WordDictionary.class.getResourceAsStream(DICTIONARY_FILE);
                BufferedReader reader = new BufferedReader(new InputStreamReader(stream))) {

            String word; // ตัวแปรสำหรับเก็บคำศัพท์ที่อ่านจากไฟล์

            // ถ้าอ่านไฟล์คำศัพท์ได้สำเร็จ จะทำการอ่านคำศัพท์ทีละบรรทัดจ จนกว่าจะจบไฟล์
            // ทำการ trim คำศัพท์เพื่อเอา whitespace ออก แล้ว insert คำศัพท์ลงใน AVL Tree
            while ((word = reader.readLine()) != null) {
                dictionaryTree.insert(word.trim());
                wordList.add(word.trim().toUpperCase()); // เพิ่มคำศัพท์ลงใน List
            }
        } catch (Exception e) {
            // ถ้าเกิดข้อผิดพลาดในการอ่านไฟล์คำศัพท์ ลง log เป็น String
            // เช่น ไฟล์ไม่พบ หรืออ่านไฟล์ไม่ได้
            logger.severe(String.format("Error loading dictionary: %s", e.getMessage()));
        }
    }

    // Method สำหรับเช็คคำว่าอยู่ใน Dictionary หรือไม่ โดยใช้การ Search ของ AVL Tree
    public static boolean isValidWord(String word) {
        return dictionaryTree.search(word);
    }

    // Method สำหรับสุ่มคำศัพท์จาก Dictionary โดยบอกความยาวของคำที่ต้องการ
    // และจำนวนคำที่ต้องการ
    public static List<String> getRandomValidWords(int count, int minLen, int maxLen) {
        List<String> filtered = wordList.stream()
                .filter(w -> w.length() >= minLen && w.length() <= maxLen)
                .collect(Collectors.toList());

        // ถ้าคำที่สุ่มได้มีน้อยกว่าจำนวนที่ต้องการ จะโยน IllegalArgumentException
        if (filtered.size() < count) {
            throw new IllegalArgumentException("Not enough valid words matching the criteria.");
        }

        // สุ่มคำศัพท์จาก List ที่กรองแล้ว โดยใช้ Random
        Collections.shuffle(filtered, new Random());
        return filtered.subList(0, count).stream()
                .map(String::toUpperCase)
                .collect(Collectors.toList());
    }
}
