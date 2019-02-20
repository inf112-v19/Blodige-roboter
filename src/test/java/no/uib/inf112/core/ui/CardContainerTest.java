package no.uib.inf112.core.ui;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import no.uib.inf112.core.player.*;
import no.uib.inf112.core.ui.cards.CardSlot;
import no.uib.inf112.core.ui.cards.SlotType;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardContainerTest extends TestGraphics {
    private Deck deck;
    CardContainer container;

    @Before
    public void setUp() throws Exception {
        deck = new ProgramDeck(true);
        container = new CardContainer(new Player(1, 1, Direction.NORTH, true), deck);

        DragAndDrop dad = new DragAndDrop();

        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {
            CardSlot cardSlot = new CardSlot(i, container, dad, true);
            container.handCard[i] = cardSlot;
        }

        for (int i = 0; i < Player.MAX_DRAW_CARDS; i++) {
            CardSlot cardSlot = new CardSlot(i, container, dad, true);
            container.drawnCard[i] = cardSlot;
        }
    }

    @Test
    public void randomizeHand() {
        fail("Not implemented yet because of missing javadoc");
    }


    @Test
    public void setCard() {
        int id = 0;
        SlotType type = SlotType.HAND;

        Card card = deck.draw(1)[id];
        assertTrue(container.setCard(type, id, card));
        assertEquals(card, container.getCard(type, id));

    }

    @Test
    public void settingCardOnHandPos1ShouldBeOnHand() {
        Card card = container.getCard(SlotType.DRAWN, 0);
        container.setCard(SlotType.HAND, 0, card);
        assertEquals(card, container.getCard(SlotType.HAND, 0));
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