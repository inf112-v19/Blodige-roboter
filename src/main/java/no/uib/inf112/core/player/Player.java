package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.Color;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.screens.GameScreen;
import no.uib.inf112.core.ui.CardContainer;
import no.uib.inf112.core.ui.actors.cards.SlotType;
import no.uib.inf112.core.ui.event.ControlPanelEventHandler;
import no.uib.inf112.core.ui.event.ControlPanelEventListener;
import no.uib.inf112.core.ui.event.events.PowerDownEvent;
import no.uib.inf112.core.util.ComparableTuple;
import no.uib.inf112.core.util.Direction;
import org.jetbrains.annotations.NotNull;

public class Player extends AbstractPlayer {

    private CardContainer cards;

    public Player(int x, int y, @NotNull Direction direction, MapHandler map) {
        super(x, y, direction, map, Color.RED);

        cards = new CardContainer(this);
        if (!GameGraphics.HEADLESS) {
            ControlPanelEventHandler eventHandler = GameScreen.getCPEventHandler();
            eventHandler.registerListener(PowerDownEvent.class, (ControlPanelEventListener<PowerDownEvent>) event -> {

                if (this != GameGraphics.getRoboRally().getPlayerHandler().mainPlayer()) {
                    //This is not optimal, references both ways but since its a get i have not given a lot of thought
                    // trying to change it
                    return;

                }

                if (isPoweredDown()) {
                    setPoweredDown(false);
                }
                setWillPowerDown(!willPowerDown());
                System.out.println("Power down next round? " + willPowerDown());
            });
        }
    }

    /**
     * Show which cards can be picked
     */
    public void beginDrawCards() {
        cards.draw();
        GameScreen.getUiHandler().showDrawnCards();
    }

    /**
     * End drawing cards, if invalid do random cards.
     * End turn
     */
    public void endDrawCards() {
        GameScreen.getUiHandler().hideDrawnCards();

        if (cards.hasInvalidHand()) {
            cards.randomizeHand();
        }
        GameGraphics.getRoboRally().getPlayerHandler().endTurn();
    }


    @Override
    public ComparableTuple<Card, IPlayer> getNextCard(int id) {
        return new ComparableTuple<>(cards.getCard(SlotType.HAND, id), this);
    }

    @NotNull
    public CardContainer getCards() {
        return cards;
    }

    @Override
    public String toString() {
        return "Player{" +
                "x=" + getY() +
                ", y=" + getX() +
                ", dir=" + getDirection() +
                ", flags=" + flags +
                ", poweredDown=" + isPoweredDown() +
                '}';
    }
}
