package no.uib.inf112.core.player;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.ui.CardContainer;
import no.uib.inf112.core.ui.actors.cards.SlotType;
import no.uib.inf112.core.ui.event.ControlPanelEventHandler;
import no.uib.inf112.core.ui.event.ControlPanelEventListener;
import no.uib.inf112.core.ui.event.events.PowerDownEvent;
import no.uib.inf112.desktop.Main;
import org.jetbrains.annotations.NotNull;

public class UserPlayer extends Player {

    private CardContainer cards;

    public UserPlayer(int x, int y, @NotNull Direction direction) {
        super(x, y, direction);

        cards = new CardContainer(this);
        if (!Main.HEADLESS) {
            ControlPanelEventHandler eventHandler = GameGraphics.getCPEventHandler();
            eventHandler.registerListener(PowerDownEvent.class, (ControlPanelEventListener<PowerDownEvent>) event -> {

                if (this != GameGraphics.getRoboRally().getPlayerHandler().mainPlayer()) {
                    //This is not optimal, references both ways but since its a get i have not given a lot of thought
                    // trying to change it
                    return;

                }
                poweredDown = !poweredDown;
                System.out.println("Powered down? " + isPoweredDown());
            });
        }
    }

    public void beginDrawCards() {
        cards.draw();
        GameGraphics.getUiHandler().showDrawnCards();
    }

    public void endDrawCards() {

        GameGraphics.getUiHandler().hideDrawnCards();

        if (cards.hasInvalidHand()) {
            cards.randomizeHand();
        }
        GameGraphics.getRoboRally().getPlayerHandler().nextPlayer();
    }


    @Override
    public PlayerCard getNextCard(int id) {
        return new PlayerCard(cards.getCard(SlotType.HAND, id), this);
    }

    @NotNull
    public CardContainer getCards() {
        return cards;
    }

}
