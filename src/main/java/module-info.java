module cpe112finalproject {
    requires javafx.fxml;
    requires transitive javafx.graphics;
    requires transitive javafx.controls;
    requires java.logging;
    requires transitive javafx.media;

    opens cpe112.finalproject to javafx.fxml;

    exports cpe112.finalproject;
}
