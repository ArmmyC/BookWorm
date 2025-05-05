package cpe112.finalproject.Layout;

import cpe112.finalproject.Constants.Name;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

public class CreditUI {
    private final StackPane rootPane = new StackPane();
    private final VBox creditPane = new VBox();
    private final Button BackButton = new Button(Name.CREDIT_BACK_BUTTON);
    Label creditLabel = new Label(Name.CREDIT_LABEL);

    public CreditUI() {
        setupLayout();
    }

    private void setupLayout() {
        rootPane.getChildren().addAll(creditPane, BackButton);
        StackPane.setAlignment(BackButton, Pos.BOTTOM_LEFT);
        creditPane.getChildren().addAll(creditLabel);

        creditPane.setStyle("-fx-background-color:rgb(25, 25, 25); -fx-padding: 20; -fx-spacing: 10;");
        creditPane.setAlignment(Pos.TOP_CENTER);

    }

    public Button getBackButton() {
        return BackButton;
    }

    public StackPane getRootPane() {
        return rootPane;
    }

}
