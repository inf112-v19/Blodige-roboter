package no.uib.inf112.core;

import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.MapInteractOnUser;
import no.uib.inf112.core.map.TiledMapHandler;
import no.uib.inf112.core.map.cards.Deck;
import no.uib.inf112.core.map.cards.MovementDeck;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.player.PlayerHandler;
import no.uib.inf112.core.testUtils.HeadlessMapHandler;
import no.uib.inf112.desktop.Main;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class RoboRally {

    public static final String MAP_FOLDER = "maps";
    public static final int PHASES_PER_ROUND = 5;
    //DO NOT PUT ASSET HERE!!! only this directory should be specified in the in the working directory
    //see https://github.com/inf112-v19/Blodtorstige-robotet/wiki/Run-with-IntelliJ
    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "test.tmx";

    private MapHandler map;

    private PlayerHandler playerHandler;
    public MapInteractOnUser mapInteractOnUser;
    private Deck deck;

    public RoboRally() {
        if (Main.HEADLESS) {
            map = new HeadlessMapHandler(FALLBACK_MAP_FILE_PATH);
        } else {
            map = new TiledMapHandler(FALLBACK_MAP_FILE_PATH);
        }

        mapInteractOnUser = new MapInteractOnUser();
        deck = new MovementDeck();
        playerHandler = new PlayerHandler(4);
        for (IPlayer player : playerHandler.getPlayers()) {
            map.addEntity(player.getRobot());
        }
    }

    public void round() {
        for (int i = 0; i < PHASES_PER_ROUND; i++) {
            playerHandler.startTurn();
            // End of robot movement

            // Activate lasers

            // Move rotation gears

            // Move assembly lines

            //Should wait some time
        }
        deck.shuffle();
        //User plans next round
    }

    /**
     * Get cards which is currently in the game
     *
     * @return cards
     */
    public Deck getDeck() {
        return deck;
    }


    /**
     * @return The current map in play
     */

    @NotNull
    public MapHandler getCurrentMap() {
        return map;
    }


    @NotNull
    public PlayerHandler getPlayerHandler() {
        return playerHandler;
    }
}
