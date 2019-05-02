package no.uib.inf112.core.screens.setupscreens;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.screens.GameScreen;

public class SinglePlayerSetup extends AbstractSetupScreen {

    private GameGraphics game;

    public SinglePlayerSetup(GameGraphics game) {
        super(game);
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    protected void startGame(String mainPlayerName) {
        GameGraphics.mainPlayerName = mainPlayerName;
        GameGraphics.createRoboRally(
                GameGraphics.MAP_FOLDER + GameGraphics.mapFileName + GameGraphics.MAP_EXTENSION,
                GameGraphics.players);
        game.setScreen(new GameScreen(game));
    }

    @Override
    public void render(float v) {
        super.render(v);

        if (startGame) {
            startGame(GameGraphics.mainPlayerName);
        }
    }
}
