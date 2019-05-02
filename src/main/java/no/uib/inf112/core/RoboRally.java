package no.uib.inf112.core;

import com.badlogic.gdx.Gdx;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Deck;
import no.uib.inf112.core.map.cards.MovementDeck;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.player.IPlayerHandler;
import no.uib.inf112.core.round.DefaultGameRule;
import no.uib.inf112.core.util.CancellableThreadScheduler;
import org.jetbrains.annotations.NotNull;

public class RoboRally {
    private static final int STANDARD_ROUND_DURATION = 910;
    private MapHandler map;

    private IPlayerHandler playerHandler;
    private Deck deck;

    public static final CancellableThreadScheduler SECOND_THREAD = new CancellableThreadScheduler();

    public RoboRally(@NotNull MapHandler map, @NotNull IPlayerHandler playerHandler) {
        RoboRally.SECOND_THREAD.cancelTasks();
        this.map = map;
        deck = new MovementDeck();
        this.playerHandler = playerHandler;
        for (IPlayer player : playerHandler.getPlayers()) {
            map.addEntity(player);
        }
    }

    /**
     * This method will always run the runnable on the main thread
     *
     * @param runnable
     *     The code to run
     * @param msDelay
     *     How long, in milliseconds, to wait before executing the runnable
     */
    public static void scheduleSync(@NotNull Runnable runnable, long msDelay) {
        if (msDelay <= 0) {
            Gdx.app.postRunnable(runnable);
        }
        else {
            SECOND_THREAD.scheduleSync(runnable, msDelay);
        }
    }

    public void round() {
        DefaultGameRule.generate(STANDARD_ROUND_DURATION).startRound();
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
    public IPlayerHandler getPlayerHandler() {
        return playerHandler;
    }
}
