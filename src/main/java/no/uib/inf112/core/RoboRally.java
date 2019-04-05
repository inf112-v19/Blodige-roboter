package no.uib.inf112.core;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.TiledMapHandler;
import no.uib.inf112.core.map.cards.Deck;
import no.uib.inf112.core.map.cards.MovementDeck;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.player.PlayerHandler;
import no.uib.inf112.core.round.DefaultGameRule;
import no.uib.inf112.core.testutils.HeadlessMapHandler;
import org.jetbrains.annotations.NotNull;

public class RoboRally {
    private MapHandler map;

    private PlayerHandler playerHandler;
    private Deck deck;
    private Music backgroundMusic;

    public RoboRally(String mapPath, int playerCount) {
        if (GameGraphics.HEADLESS) {
            map = new HeadlessMapHandler(mapPath);
        } else {
            map = new TiledMapHandler(mapPath);
        }

        backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("sound/backgroundMusic.wav"));
        backgroundMusic.setLooping(true);
        backgroundMusic.setVolume(0.1f);
        backgroundMusic.play();

        deck = new MovementDeck();
        playerHandler = new PlayerHandler(playerCount, map);
        for (IPlayer player : playerHandler.getPlayers()) {
            map.addEntity(player);
        }

    }

    public void round() {
        DefaultGameRule.generate().startRound();
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
