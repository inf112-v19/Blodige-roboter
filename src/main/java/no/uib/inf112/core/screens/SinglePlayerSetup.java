package no.uib.inf112.core.screens;

import no.uib.inf112.core.GameGraphics;

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
        game.setScreen(new GameScreen(game)); //TODO create new instance
    }

    @Override
    public void render(float v) {
        super.render(v);

        if (startGame) {
            startGame(GameGraphics.mainPlayerName);
        }
    }
}
