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
        String credits = "Programming by\n" +
                "    Kristian Akslan (leader, test responsible)\n" +
                "    Daniel Berge (customer contact)\n" +
                "    Karl Henrik Elg Barlinn\n" +
                "    Rikke Ass\n" +
                "    Rune Almåsbakk\n" +
                "Background music by Eric Matyas";
        Label instruction = game.createLabel(credits, stage.getWidth() / 3.5f, stage.getHeight() / 3, 30);

        TextButton returnButton = createReturnButton(70);
        returnButton.setPosition(stage.getWidth() / 2 - (returnButton.getWidth() / 2), stage.getHeight() / 20);

        stage.addActor(instruction);
        stage.addActor(returnButton);
    }
}
