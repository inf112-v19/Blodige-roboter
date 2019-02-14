package no.uib.inf112.core.ui;

import no.uib.inf112.core.player.*;
import no.uib.inf112.core.ui.cards.SlotType;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class CardContainerTest {
    private Deck deck;
    CardContainer container;

    @Before
    public void setUp() throws Exception {
        deck  = new ProgramDeck(true);
        container = new CardContainer(new Player(1, 1, Direction.NORTH, true), deck);
        container.draw();
    }

    @Test
    public void randomizeHand() {
        fail("Not implemented yet because of missing javadoc");
    }


    @Test
    public void setCard() {

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