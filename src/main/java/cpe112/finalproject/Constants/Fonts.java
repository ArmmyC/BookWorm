package cpe112.finalproject.Constants;

import javafx.scene.text.Font;

/*
 * Fonts.java
 * สำหรับเก็บ Font ที่ใช้ในโปรเจคนี้
 * Font ที่มี ได้แก่ KnightWarrior, Nexora
 */

public class Fonts {

        // 'KNIGHT WARRIOR'
        public static final Font KNIGHTWARRIOR = Font.loadFont(
                        Fonts.class.getResourceAsStream(Path.KNIGHTWARRIOR_FONT),
                        20);

        // 'NEXORA'
        public static final Font NEXORA = Font.loadFont(
                        Fonts.class.getResourceAsStream(Path.NEXORA_FONT),
                        20);

        // 'SERPENTINE'
        public static final Font SERPENTINE = Font.loadFont(Fonts.class.getResourceAsStream(Path.SERPENTINE_FONT), 20);

        // 'Rokkitt'
        public static final Font ROKKITT = Font.loadFont(Fonts.class.getResourceAsStream(Path.ROKKIT_FONT), 20);

        // 'COMEBACK HOME'
        public static final Font COMEBACKHOME = Font.loadFont(Fonts.class.getResourceAsStream(Path.COMEBACKHOME_FONT),
                        20);

        // 'Axubies'
        public static final Font AXUBIES = Font.loadFont(Fonts.class.getResourceAsStream(Path.AXUBIES_FONT), 20);

        // 'Liger'
        public static final Font LIGER = Font.loadFont(Fonts.class.getResourceAsStream(Path.LIGER_FONT), 20);

        static {
                // System.out.println(Fonts.KNIGHTWARRIOR.getName());
                // System.out.println(Fonts.KNIGHTWARRIOR.getFamily());

                // System.out.println(Fonts.NEXORA.getName());
                // System.out.println(Fonts.NEXORA.getFamily());

                // System.out.println(Fonts.ROKKITT.getName());
                // System.out.println(Fonts.ROKKITT.getFamily());

                // System.out.println(Fonts.SERPENTINE.getName());
                // System.out.println(Fonts.SERPENTINE.getFamily());

                // System.out.println(Fonts.COMEBACKHOME.getName());
                // System.out.println(Fonts.COMEBACKHOME.getFamily());

                // System.out.println(Fonts.AXUBIES.getName());
                // System.out.println(Fonts.AXUBIES.getFamily());

                // System.out.println(Fonts.LIGER.getName());
                // System.out.println(Fonts.LIGER.getFamily());
        }
}
