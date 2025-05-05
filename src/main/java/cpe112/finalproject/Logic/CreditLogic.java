package cpe112.finalproject.Logic;

import cpe112.finalproject.Layout.CreditUI;
import cpe112.finalproject.Managers.SceneManager;
import cpe112.finalproject.Managers.SoundManager;
import cpe112.finalproject.Scenes.MainMenuScene;

public class CreditLogic {

    private final CreditUI ui;

    public CreditLogic(CreditUI ui) {
        this.ui = ui;
    }

    public void initialize() {
        ui.getBackButton().setOnAction(e -> backAction());
    }

    private void backAction() {
        SoundManager.playClickSound();
        SceneManager.getInstance().switchRoot(new MainMenuScene().getRootPane());
    }
}
