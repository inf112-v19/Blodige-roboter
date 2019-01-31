package no.uib.inf112.desktop;

import no.uib.inf112.player.Card;
import no.uib.inf112.player.Deck;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class DeckTest {

    @Test
    public void initDeckTest() {
        Deck deck = new Deck();
        assertEquals();

    }
    @Test
    public void drawDeckTest() {
        Deck deck = new Deck();
        Card[] draw = deck.draw(10);
        assertEquals(10, draw.length);
    }
}
