package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.util.ComparableTuple;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Elg
 */
public class PlayerPhase extends AbstractPhase {

    private final int delayPerPlayer;
    private List<List<ComparableTuple<Card, IPlayer>>> cards;
    private int lastIndex;

    public PlayerPhase(int delayPerPlayer) {
        super((GameGraphics.getRoboRally().getPlayerHandler().getPlayers().size() + 1) * delayPerPlayer);
        this.delayPerPlayer = delayPerPlayer;
        List<IPlayer> players = GameGraphics.getRoboRally().getPlayerHandler().getPlayers();

        cards = new ArrayList<>();
        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {

            List<ComparableTuple<Card, IPlayer>> roundList = new ArrayList<>();
            for (IPlayer p : players) {
                roundList.add(p.getNextCard(i));
            }
            Collections.sort(roundList);
            cards.add(roundList);
        }
    }

    @Override
    public void startPhase(MapHandler map) {
        List<ComparableTuple<Card, IPlayer>> phaseCards = cards.get(lastIndex);
        lastIndex++;

        for (int i = 0; i < phaseCards.size(); i++) {
            ComparableTuple<Card, IPlayer> tuple = phaseCards.get(i);
            int finalI = i;
            GameGraphics.scheduleSync(() -> {
                System.out.println("card (" + tuple + ") played after relative (to phase) " + (delayPerPlayer * (finalI + 1)) + " ms");
                tuple.value.move(tuple.key.getAction(), delayPerPlayer);
            }, delayPerPlayer * (i + 1));
        }
    }
}