package no.uib.inf112.core.ui;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import no.uib.inf112.core.player.*;
import no.uib.inf112.core.ui.cards.CardSlot;
import no.uib.inf112.core.ui.cards.SlotType;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

public class CardContainerTest extends TestGraphics {
    private Deck deck;
    CardContainer container;

    @Before
    public void setUp() throws Exception {
        deck = new ProgramDeck(true);
        container = new CardContainer(new Player(1, 1, Direction.NORTH, true), deck);

        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {
            CardSlot cardSlot = new CardSlot(i, container, new DragAndDrop());
            container.handCard[i] = cardSlot;
        }
    }

    @Test
    public void randomizeHand() {
        fail("Not implemented yet because of missing javadoc");
    }


    @Test
    public void setCard() {
        Card card = deck.draw(1)[0];
        container.setCard(SlotType.HAND, 0, card);
        assertEquals(card, container.getCard(SlotType.HAND, 0));

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