package no.uib.inf112.core;

import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Deck;
import no.uib.inf112.core.map.cards.MovementDeck;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.player.IPlayerHandler;
import no.uib.inf112.core.round.DefaultGameRule;
import org.jetbrains.annotations.NotNull;

public class RoboRally {
    private MapHandler map;

    private IPlayerHandler playerHandler;
    private Deck deck;

    public RoboRally(@NotNull MapHandler map, @NotNull IPlayerHandler playerHandler) {
        this.map = map;
        deck = new MovementDeck();
        this.playerHandler = playerHandler;
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
    public IPlayerHandler getPlayerHandler() {
        return playerHandler;
    }
}