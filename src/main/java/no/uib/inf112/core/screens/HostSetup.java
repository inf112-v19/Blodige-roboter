package no.uib.inf112.core.screens;

import no.uib.inf112.core.GameGraphics;

public class HostSetup extends AbstractSetupScreen {

    public HostSetup(GameGraphics game) {
        super(game);
    }

    @Override
    protected void startGame(String mainPlayerName) {
        GameGraphics.mainPlayerName = mainPlayerName;
        game.setScreen(new LobbyScreen(game, true, "localHost", 1100));
        stage.clear();
    }

}

