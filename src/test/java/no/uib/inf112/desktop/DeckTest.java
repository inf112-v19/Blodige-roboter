package no.uib.inf112.desktop;

import no.uib.inf112.core.player.Card;
import no.uib.inf112.core.player.Deck;
import no.uib.inf112.core.player.Movement;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class DeckTest {
    private Deck deck;
    private final int N = 1000;

    @Before
    public void init() {
        deck = new Deck();
    }

    @Test
    public void initDeckSizeTest() {
        assertEquals(84, deck.getCards().length);
    }

    @Test
    public void leftTurnCardsExistTest() {
        Card[] cards = deck.getCards();
        int leftTurnCount = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getAction() == Movement.LEFT_TURN) {
                leftTurnCount++;
            }
        }
        assertEquals(18, leftTurnCount);
    }

    @Test
    public void rightTurnCardsExistTest() {
        Card[] cards = deck.getCards();
        int rightTurnCount = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getAction() == Movement.RIGHT_TURN) {
                rightTurnCount++;
            }
        }
        assertEquals(18, rightTurnCount);
    }

    @Test
    public void uTurnCardsExistTest() {
        Card[] cards = deck.getCards();
        int uTurnCount = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getAction() == Movement.U_TURN) {
                uTurnCount++;
            }
        }
        assertEquals(6, uTurnCount);
    }


    @Test
    public void move1TurnCardsExistTest() {
        Card[] cards = deck.getCards();
        int moveTurnCount = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getAction() == Movement.MOVE_1) {
                moveTurnCount++;
            }
        }
        assertEquals(18, moveTurnCount);
    }

    @Test
    public void move2TurnCardsExistTest() {
        Card[] cards = deck.getCards();
        int moveTurnCount = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getAction() == Movement.MOVE_2) {
                moveTurnCount++;
            }
        }
        assertEquals(12, moveTurnCount);
    }


    @Test
    public void move3TurnCardsExistTest() {
        Card[] cards = deck.getCards();
        int moveTurnCount = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getAction() == Movement.MOVE_3) {
                moveTurnCount++;
            }
        }
        assertEquals(6, moveTurnCount);
    }

    @Test
    public void backupTurnCardsExistTest() {
        Card[] cards = deck.getCards();
        int backupTurnCount = 0;
        for (int i = 0; i < cards.length; i++) {
            if (cards[i].getAction() == Movement.BACK_UP) {
                backupTurnCount++;
            }
        }
        assertEquals(6, backupTurnCount);
    }


    @Test
    public void drawDeckTest() {
        for (int i = 1; i <= 5; i++) {
            Card[] draw = deck.draw(i);
            assertEquals(i, draw.length);
        }
    }

    @Test
    public void shuffleTest() {
        for (int i = 0; i < N; i++) {
            Deck d = new Deck();
            Card[] cards = d.getCards().clone();
            d.shuffle();
            Card[] cards1 = d.getCards().clone();

            int sameCardPos = 0;

            for (int j = 0; j < cards.length; j++) {
                if (cards[j].equals(cards1[j])) {
                    sameCardPos++;
                }
            }
            assertNotEquals(cards.length, sameCardPos);
            assertNotEquals(cards1.length, sameCardPos);
        }

    }
}