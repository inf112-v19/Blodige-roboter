package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.screens.GameScreen;
import no.uib.inf112.core.ui.Sound;
import no.uib.inf112.core.util.ComparableTuple;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * The phase where each player move according to their left-most not yet played programming card.
 * They do so following the priority of their card.
 *
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
                if (!p.isPoweredDown()) {
                    roundList.add(p.getNextCard(i));
                }
            }
            Collections.sort(roundList);
            cards.add(roundList);
        }
    }

    @Override
    public void startPhase(@NotNull MapHandler map) {
        List<ComparableTuple<Card, IPlayer>> phaseCards = cards.get(lastIndex);
        lastIndex++;

        for (int i = 0; i < phaseCards.size(); i++) {
            ComparableTuple<Card, IPlayer> tuple = phaseCards.get(i);

            GameScreen.scheduleSync(() -> {
                tuple.value.move(tuple.key.getAction(), delayPerPlayer);
                Sound.ROBOT_MOVING.play();
            }, delayPerPlayer * (i + 1));
        }
    }
}
