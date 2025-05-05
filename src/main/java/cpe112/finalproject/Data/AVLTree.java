package cpe112.finalproject.Data;

import java.util.ArrayList;
import java.util.List;

/*
 * AVLTree.java ไว้เป็นโครงสร้าง AVL Tree 
 * ที่จะใช้สำหรับเก็บคำศัพท์จาก dictionary file
 */

// สร้างโนด AVLTree
// โดยจะเก็บคำศัพท์ในรูปแแบบ String
class AVLTreeNode {
    String word;
    int height;
    AVLTreeNode left, right;

    // Constructor สำหรับสร้างโนด AVLTree
    AVLTreeNode(String word) {
        this.word = word;
        this.height = 1;
    }
}

public class AVLTree {

    // สร้างตัวแปร root สำหรับเก็บ root ของ AVLTree
    private AVLTreeNode root;

    // Method สำหรับหาความสูงของโนด
    // ถ้าโนดเป็น null จะ return 0 ถ้าไม่ return node.height
    private int height(AVLTreeNode node) {
        return node == null ? 0 : node.height;
    }

    // Method สำหรับหาค่าของ balance factor ของโนด
    // ถ้าโนดเป็น null จะ return 0 ถ้าไม่ return node.left - node.right
    private int getBalanceFactor(AVLTreeNode node) {
        return node == null ? 0 : height(node.left) - height(node.right);
    }

    // Method สำหรับหาค่าที่มากที่สุดระหว่างสองค่า
    private int max(int a, int b) {
        return Math.max(a, b);
    }

    // Method สำหรับการหมุน tree ไปทางขวา
    private AVLTreeNode rightRotate(AVLTreeNode oldRoot) {
        AVLTreeNode newRoot = oldRoot.left;
        AVLTreeNode tempRoot = newRoot.right;

        newRoot.right = oldRoot;
        oldRoot.left = tempRoot;

        oldRoot.height = max(height(oldRoot.left), height(oldRoot.right)) + 1;
        newRoot.height = max(height(newRoot.left), height(newRoot.right)) + 1;

        return newRoot;
    }

    // Method สำหรับการหมุน tree ไปทางซ้าย
    private AVLTreeNode leftRotate(AVLTreeNode oldRoot) {
        AVLTreeNode newRoot = oldRoot.right;
        AVLTreeNode tempRoot = newRoot.left;

        newRoot.left = oldRoot;
        oldRoot.right = tempRoot;

        oldRoot.height = max(height(oldRoot.left), height(oldRoot.right)) + 1;
        newRoot.height = max(height(newRoot.left), height(newRoot.right)) + 1;

        return newRoot;
    }

    // Method สำหรับการแทรกคำศัพท์ใหม่ลงใน AVL Tree
    private AVLTreeNode insert(AVLTreeNode node, String word) {
        if (node == null) {
            return new AVLTreeNode(word);
        }

        // ใช้ compareTo ในการเปรียบเทียบคำสองคำ ตามพจนานุกรม (lexicographically)
        // ถ้าคำนั้นมีค่าเท่ากัน จะ return 0
        // ถ้าคำนั้นมีค่าน้อยกว่า จะ return < 0
        // ถ้าคำนั้นมีค่ามากกว่า จะ return > 0
        int value = word.compareTo(node.word);
        if (value < 0) {
            node.left = insert(node.left, word);
        } else if (value > 0) {
            node.right = insert(node.right, word);
        } else {
            return node;
        }

        node.height = max(height(node.left), height(node.right)) + 1;

        int balance = getBalanceFactor(node);
        if (balance > 1 && word.compareTo(node.left.word) < 0) {
            return rightRotate(node);
        }
        if (balance < -1 && word.compareTo(node.right.word) > 0) {
            return leftRotate(node);
        }
        if (balance > 1 && word.compareTo(node.left.word) > 0) {
            node.left = leftRotate(node.left);
            return rightRotate(node);
        }
        if (balance < -1 && word.compareTo(node.right.word) < 0) {
            node.right = rightRotate(node.right);
            return leftRotate(node);
        }

        return node;
    }

    // Method สำหรับการค้นหาคำศัพท์ใน AVL Tree
    // โดยใช้เป็น boolean เพือเช็คว่า มี หรือ ไม่มี คำศัพท์นี้ใน tree
    private boolean search(AVLTreeNode node, String word) {
        if (node == null) {
            return false;
        }
        int value = word.compareTo(node.word);
        if (value == 0) {
            return true;
        } else if (value < 0) {
            return search(node.left, word);
        } else {
            return search(node.right, word);
        }
    }

    // Method insert สำหรับเรียกใช้จากภายนอก
    public void insert(String word) {
        root = insert(root, word.toLowerCase());
    }

    // Method search สำหรับเรียกใช้จากภายนอก
    public boolean search(String word) {
        return search(root, word.toLowerCase());
    }

    // Method สำหรับเพิ่มคำศัพท์จาก AVL Tree ลงใน List<String>
    // โดยจะทำการเรียงลำดับคำศัพท์จากน้อยไปมาก (lexicographically)
    public List<String> getAllWords() {
        List<String> result = new ArrayList<>();
        inOrderTraversal(root, result);
        return result;
    }

    // Method สำหรับการเรียงลำดับคำศัพท์จากน้อยไปมาก (lexicographically)
    private void inOrderTraversal(AVLTreeNode node, List<String> result) {
        if (node != null) {
            inOrderTraversal(node.left, result);
            result.add(node.word);
            inOrderTraversal(node.right, result);
        }
    }
}
