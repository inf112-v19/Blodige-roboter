package no.uib.inf112.core.screens.menuscreens;

import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import no.uib.inf112.core.GameGraphics;

public class CreditScreen extends AbstractMenuScreen {

    public CreditScreen(GameGraphics game) {
        super(game);

    }

    @Override
    public void show() {
        String credits = "- Click and drag cards into your chosen registers\n" +
                "- Press enter to lock in your card selection.\n" +
                "    > You can not change cards after pressing enter\n" +
                "- Click on power down button to announce power down.\n" +
                "    > This will be effectuated the next game round\n" +
                "- Scroll or press +/- to zoom in and out on map\n" +
                "- Drag the map around to change the map view\n" +
                "- Konami code is fun";
        Label instruction = game.createLabel(credits, stage.getWidth() / 8, stage.getHeight() / 3, 30);

        TextButton returnButton = createReturnButton(70);
        returnButton.setPosition(stage.getWidth() / 2 - (returnButton.getWidth() / 2), stage.getHeight() / 20);

        stage.addActor(instruction);
        stage.addActor(returnButton);
    }
}
