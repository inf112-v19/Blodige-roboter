package no.uib.inf112.core;

import no.uib.inf112.core.map.HeadlessMapHandler;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.MapInteractOnUser;
import no.uib.inf112.core.map.TiledMapHandler;
import no.uib.inf112.core.player.PlayerHandler;
import no.uib.inf112.desktop.Main;
import org.jetbrains.annotations.NotNull;

import java.io.File;

public class RoboRally  {

    public static final String MAP_FOLDER = "maps";
    public static final int PHASES_PER_ROUND = 5;
    //DO NOT PUT ASSET HERE!!! only this directory should be specified in the in the working directory
    //see https://github.com/inf112-v19/Blodtorstige-robotet/wiki/Run-with-IntelliJ
    public static final String FALLBACK_MAP_FILE_PATH = MAP_FOLDER + File.separatorChar + "test.tmx";

    private MapHandler map;

    private PlayerHandler playerHandler;
    public MapInteractOnUser mapInteractOnUser;

    public RoboRally() {
        if (Main.HEADLESS) {
            map = new HeadlessMapHandler(FALLBACK_MAP_FILE_PATH);
        } else {
            map = new TiledMapHandler(FALLBACK_MAP_FILE_PATH);
        }

        playerHandler = new PlayerHandler(3);
        mapInteractOnUser = new MapInteractOnUser();
    }

    public void round() {
        for (int i = 0; i < PHASES_PER_ROUND; i++) {
            playerHandler.doTurn();
            // End of robot movement

            // Activate lasers

            // Move rotation gears

            // Move assembly lines

            //Should wait some time
        }
        //User plans next round
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
