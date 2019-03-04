package no.uib.inf112.core.player;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MovementCardTest {
    @Test
    public void makeCardTest() {
        MovementCard card = new MovementCard(Movement.LEFT_TURN, 3);
        assertEquals(3, card.getPriority());
        assertEquals(Movement.LEFT_TURN, card.getAction());
    }

    @Test
    public void cardPriorityTest() {
        for (int i = 0; i < 100; i++) {
            MovementCard card = new MovementCard(Movement.BACK_UP, i);
            assertEquals(i, card.getPriority());
        }

    }
}
