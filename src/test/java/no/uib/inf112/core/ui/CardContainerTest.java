package no.uib.inf112.core.ui;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import no.uib.inf112.core.player.Deck;
import no.uib.inf112.core.player.Direction;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.player.ProgramDeck;
import no.uib.inf112.core.ui.actors.cards.CardSlot;
import no.uib.inf112.core.ui.actors.cards.SlotType;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.fail;

public class CardContainerTest extends TestGraphics {
    private Deck deck;
    CardContainer container;

    @Before
    public void setUp() throws Exception {
        deck = new ProgramDeck(true);
        container = new CardContainer(new Player(1, 1, Direction.NORTH, true), deck);

        DragAndDrop dad = new DragAndDrop();

        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {
            CardSlot cardSlot = new CardSlot(i, SlotType.HAND, container, dad, true);
            container.handCard[i] = cardSlot;
        }

        for (int i = 0; i < Player.MAX_DRAW_CARDS; i++) {
            CardSlot cardSlot = new CardSlot(i, SlotType.DRAWN, container, dad, true);
            container.drawnCard[i] = cardSlot;
        }
    }

    @Test
    public void randomizeHand() {
        fail("Not implemented yet because of missing javadoc");
    }

    @Test
    public void setCard1() {
    }

    @Test
    public void getCard() {
    }

    @Test
    public void getCard1() {
    }

    @Test
    public void hasInvalidHand() {
    }
}