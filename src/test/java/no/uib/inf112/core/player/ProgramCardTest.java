package no.uib.inf112.core.player;

import no.uib.inf112.core.player.ProgramCard;
import no.uib.inf112.core.player.Movement;
import org.junit.Test;
import static org.junit.Assert.assertEquals;


public class ProgramCardTest {
    @Test
    public void makeCardTest() {
        ProgramCard card = new ProgramCard(Movement.LEFT_TURN, 3);
        assertEquals(3, card.getPriority());
        assertEquals(Movement.LEFT_TURN, card.getAction());
    }

    @Test
    public void cardPriorityTest() {
        for (int i = 0; i < 100; i++) {
           ProgramCard card = new ProgramCard(Movement.BACK_UP, i);
           assertEquals(i, card.getPriority());
        }

    }
}
