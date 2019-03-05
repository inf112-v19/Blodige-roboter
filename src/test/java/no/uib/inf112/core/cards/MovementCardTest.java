package no.uib.inf112.core.cards;

import no.uib.inf112.core.map.cards.Movement;
import no.uib.inf112.core.map.cards.MovementCard;
import no.uib.inf112.desktop.Main;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class MovementCardTest {

    @BeforeClass
    public static void setUp() {
        Main.HEADLESS = true;
    }

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
