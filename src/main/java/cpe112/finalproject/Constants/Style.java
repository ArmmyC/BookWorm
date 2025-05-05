package cpe112.finalproject.Constants;

/*
 * Style.java
 * สำหรับเก็บ Style ของ Object ต่างๆ ที่ใช้ในโปรเจคนี้
 * โดยจะแบ่งแยกไว้เป็นหมวดหมู่ตาม .java ที่ใช้
 */

public class Style {

        // ============================================================
        // -------------------- MainMenuUI.java -----------------------
        // ============================================================
        public static final String MAINMENU_BACKGROUND_COLOR = "-fx-background-color: rgb(25, 25, 25);";
        public static final String MAINMENU_SPACING = "-fx-padding: 20; -fx-spacing: 20;";
        public static final String SETTINGS_SPACING = "-fx-padding: 10; -fx-spacing: 10;";
        // ============================================================
        //
        // ============================================================
        // ------------------- MainMenuLogic.java ---------------------
        // ============================================================
        public static final String TITLE_LABEL_STYLE = "-fx-text-fill: white; " + "-fx-font-weight: bold;";
        public static final String MENU_BUTTON_STYLE = " -fx-background-color: transparent; " +
                        " -fx-text-fill: white; " +
                        " -fx-border-width: 1px 0px 1px 0px; " +
                        " -fx-border-color: white; ";
        public static final String FULLSCREEN_BUTTON_STYLE = "-fx-background-color: transparent;" +
                        " -fx-text-fill: white;";
        public static final String FULLSCREEN_BUTTON_HOVER_STYLE = "-fx-background-color: rgb(34, 34, 34);" +
                        " -fx-text-fill: white;" +
                        " -fx-border-color: white;" +
                        " -fx-border-width: 1px 0px 1px 0px; " +
                        " -fx-border-radius: 5;";
        public static final String SLIDER_LABEL_STYLE = "-fx-text-fill: white;" + " -fx-padding: 10px;";
        public static final String SLIDER_STYLE = "-fx-background-color: transparent;";
        public static final String SLIDER_HOVER_STYLE = "-fx-background-color: rgb(34, 34, 34);" +
                        " -fx-border-color: white;" +
                        " -fx-border-width: 1px 0px 1px 0px; " +
                        " -fx-border-radius: 5;";
        // ============================================================
        //
        //
        //
        //
        //
        // ============================================================
        // ------------------- ClassSelectorUI.java -------------------
        // ============================================================
        public static final String CLASS_SELECTOR_BACKGROUND_COLOR = "-fx-background-color: rgb(223, 223, 223);";
        public static final String CLASS_SELECTOR_NAME_INPUT_STYLE = "-fx-font-family: 'Rokkitt'; -fx-text-fill: black; -fx-font-size: 16px;";
        public static final String CLASS_SELECTOR_CLASS_DEFAULT_STYLE = "-fx-background-color: rgb(51, 51, 51); -fx-border-color: white; -fx-border-width: 2px; -fx-border-radius: 2px;";
        public static final String CLASS_SELECTOR_CLASS_SELECTED_STYLE = "-fx-background-color: #555; -fx-border-color: #00FFFF; -fx-border-width: 3px; -fx-border-radius: 2px;";
        public static final String CLASS_SELECTOR_BUTTON_STYLE = "-fx-background-color: rgb(51, 51, 51); -fx-text-fill: white;";
        public static final String CLASS_SELECTOR_BUTTON_HOVER_STYLE = "-fx-background-color: rgb(83, 83, 83); -fx-text-fill: white; ";
        // ============================================================
        //
        // ============================================================
        // ----------------- ClassSelectorLogic.java ------------------
        // ============================================================
        public static final String CLASS_SELECTOR_CLASS_LABEL_STYLE = "-fx-font-family: 'KNIGHT WARRIOR'; -fx-font-size: 24px; -fx-text-fill: white; -fx-effect: none;";
        public static final String CLASS_SELECTOR_DESC_LABEL_STYLE = "-fx-font-family: 'NEXORA';  -fx-text-fill: lightgray; -fx-font-size: 16px;";
        public static final String CLASS_SELECTOR_STATS_LABEL_STYLE = "-fx-font-family: 'Rokkitt'; -fx-text-fill: rgba(200, 200, 200, 0.56); -fx-font-size: 16px;";
        // ============================================================
        //
        //
        //
        // ============================================================
        // ------------------------ SIDE MENU -------------------------
        // ============================================================
        public static final String TOGGLE_BUTTON_STYLE = "-fx-background-color: rgba(0,0,0,0.8);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 0 0 5 0;";
        public static final String TOGGLE_BUTTON_HOVER_STYLE = "-fx-background-color: rgba(255,255,255,0.8);" +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 0 0 5 0;";

        public static final String SIDEMENU_BUTTON_STYLE = "-fx-background-color: transparent; "
                        +
                        "-fx-text-fill: white;" +
                        "-fx-background-radius: 10;";

        public static final String SIDEMENU_BUTTON_HOVER_STYLE = " -fx-background-color: rgba(255, 255, 255, 0.4);"
                        +
                        "-fx-text-fill: rgb(228, 228, 228);" +
                        "-fx-background-radius: 10;";
        // ============================================================
        //
        //
        //
        // ============================================================
        // ----------- CreatePlayer.java , CreateEnemy.java -----------
        // ============================================================
        public static final String NAME_LABEL_STYLE = "-fx-font-family: 'NEXORA'; -fx-text-fill: white; -fx-font-weight: bold;";
        public static final String HEALTH_LABEL_STYLE = "-fx-font-family: 'Rokkitt'; -fx-text-fill: white; -fx-font-weight: bold;";

        public static final String HEALTHBAR_BACKGROUND_COLOR = "-fx-background-color: rgb(65, 65, 65);";

        public static final String PLAYER_HEALTHBAR_FOREGROUND_COLOR = "-fx-background-color: limegreen;";
        public static final String PLAYER_HEALTHBAR_RADIUS = "-fx-background-radius: 0 50 0 0;";
        public static final String PLAYER_HEALTHBAR_BACKGROUND_STYLE = HEALTHBAR_BACKGROUND_COLOR +
                        PLAYER_HEALTHBAR_RADIUS;
        public static final String PLAYER_HEALTHBAR_FOREGROUND_STYLE = PLAYER_HEALTHBAR_FOREGROUND_COLOR +
                        PLAYER_HEALTHBAR_RADIUS;

        public static final String ENEMY_HEALTHBAR_FOREGROUND_COLOR = "-fx-background-color: red;";
        public static final String ENEMY_HEALTHBAR_RADIUS = "-fx-background-radius: 50 0 0 0;";
        public static final String ENEMY_HEALTHBAR_BACKGROUND_STYLE = HEALTHBAR_BACKGROUND_COLOR +
                        ENEMY_HEALTHBAR_RADIUS;
        public static final String ENEMY_HEALTHBAR_FOREGROUND_STYLE = ENEMY_HEALTHBAR_FOREGROUND_COLOR +
                        ENEMY_HEALTHBAR_RADIUS;

        public static final String CLASS_LABEL_STYLE = "-fx-font-family: 'Rokkitt'; -fx-text-fill: white; -fx-font-weight: bold;";
        public static final String ATTACK_LABEL_STYLE = "-fx-font-family: 'Rokkitt'; -fx-text-fill: white; -fx-font-weight: bold;";
        public static final String DEFENSE_LABEL_STYLE = "-fx-font-family: 'Rokkitt'; -fx-text-fill: white; -fx-font-weight: bold;";
        // ============================================================
        //
        //
        //
        // ============================================================
        // ---------------------- PuzzleUI.java -----------------------
        // ============================================================
        public static final String PUZZLE_BUTTON_STYLE = "-fx-text-fill: black;" +
                        "-fx-font-size: 16px;" +
                        "-fx-font-weight: bold;" +
                        "-fx-background-radius: 10px;" +
                        "-fx-border-radius: 10px;";
        public static final String PUZZLE_BUTTON_UNSELECTED_STYLE = PUZZLE_BUTTON_STYLE
                        + "-fx-background-color:rgb(255, 255, 255);";
        public static final String PUZZLE_BUTTON_SELECTED_STYLE = PUZZLE_BUTTON_STYLE
                        + "-fx-background-color:rgb(255, 204, 0);";
        // ============================================================

        // ============================================================
        // -------------------- CombatLogic.java ----------------------
        // ============================================================
        public static final String WORD_FORMED_STYLE = "-fx-text-fill: white;" +
                        "-fx-font-size: 40px;" +
                        "-fx-font-family:  'Liger';";
        // ============================================================

}