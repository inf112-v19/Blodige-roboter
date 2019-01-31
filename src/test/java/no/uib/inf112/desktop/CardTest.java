package no.uib.inf112.desktop;

import no.uib.inf112.player.Card;
import no.uib.inf112.player.Movement;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class CardTest {
    @Test
    public void makeCardTest() {
        Card card = new Card(Movement.LEFT_TURN, 3);
        assertEquals(3, card.getPriority());
        assertEquals(Movement.LEFT_TURN, card.getAction());
    }

    @Test
    public void cardPriorityTest() {
        for (int i = 0; i < 100; i++) {
           Card card = new Card(Movement.BACK_UP, i);
           assertEquals(i, card.getPriority());
        }

    }
}
