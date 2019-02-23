package no.uib.inf112.core.ui;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import no.uib.inf112.core.player.*;
import no.uib.inf112.core.ui.actors.cards.CardActor;
import no.uib.inf112.core.ui.actors.cards.CardSlot;
import no.uib.inf112.core.ui.actors.cards.SlotType;
import no.uib.inf112.desktop.TestGraphics;
import org.junit.Before;
import org.junit.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import static org.junit.Assert.*;

public class CardContainerTest extends TestGraphics {

    private ProgramDeck deck = new ProgramDeck(true);
    private CardContainer container;

    @Before
    public void setUp() {
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

        container.draw();
    }

    @Test
    public void drawingForFullHealthPlayerShouldReturn9Cards() {
        int nCards = 9;

        for (int i = 0; i < nCards; i++) {
            assertNotNull("Missing card on" + i + ", all drawn cards should be 0",
                    container.getCard(SlotType.DRAWN, i));
        }
    }

    @Test
    public void drawingForDamagedPlayerShouldReturn9MinusDamageAmountOfCards() {
        int nCards = 9;
        int damageAmount = 3;
        container.getPlayer().damage(damageAmount);
        container.draw();
        System.out.println("container = " + Arrays.toString(Arrays.stream(container.drawnCard).map(CardActor::getCard).toArray()));
        for (int i = 0; i < nCards - damageAmount; i++) {
            assertNotNull("Found null card (nr " + i + "), all drawn cards below " + (nCards - damageAmount) + " should not be null",
                    container.getCard(SlotType.DRAWN, i));
        }

        assertNull("player should not have more cards then health",
                container.getCard(SlotType.DRAWN, nCards - damageAmount));
    }

    @Test
    public void drawingFor1HealthPlayerShouldGetNothing() {
        Player player = container.getPlayer();
        player.damage(player.getHealth() - 1);
        container.draw();

        // We might want to instead make it so that a player with 0 health is still alive.
        assertNull("player should not have any drawn cards when on last hp point",
                container.getCard(SlotType.DRAWN, 0));
    }

    @Test
    public void randomizingHandOfPlayerShouldHaveAllCardsOnHand() {
        container.draw();
        container.randomizeHand();

        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {
            assertNotNull(container.getCard(SlotType.HAND, i));
        }

        assertFalse(container.hasInvalidHand());
    }

    @Test
    public void randomizingAlreadyFullHandShouldMakeHandStayFull() {

        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {
            container.handCard[i].setCard(new ProgramCard(Movement.MOVE_1, 1, true));
        }
        container.randomizeHand();
        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {
            assertNotNull(container.getCard(SlotType.HAND, i));
        }

        assertFalse(container.hasInvalidHand());
    }


    @Test
    public void randomizeHandOnlyDrawCardsFromDrawn() {
        Movement action = Movement.MOVE_1;

        for (int i = 0; i < Player.MAX_DRAW_CARDS; i++) {
            container.drawnCard[i].setCard(new ProgramCard(action, i, true));
        }
        //all hand cards should be null
        assertTrue(Arrays.stream(container.handCard).map(CardActor::getCard).allMatch(Objects::isNull));
        container.randomizeHand();

        assertTrue(Arrays.stream(container.handCard).allMatch(cardSlot -> cardSlot.getCard().getPriority() < Player.MAX_DRAW_CARDS));
        assertTrue(Arrays.stream(container.handCard).allMatch(cardSlot -> cardSlot.getCard().getAction() == action));

        //no duplicates
        Set<Integer> ids = new HashSet<>();
        for (CardSlot cardSlot : container.handCard) {
            assertFalse(ids.contains(cardSlot.getCard().getPriority()));
            ids.add(cardSlot.getCard().getPriority());
        }
    }

    @Test
    public void randomizeHandPutsCardsBackToDrawn() {

        //loop unrolled
        container.handCard[0].setCard(new ProgramCard(Movement.LEFT_TURN, 0, true));
        container.handCard[1].setCard(new ProgramCard(Movement.LEFT_TURN, 1, true));
        container.handCard[2].setCard(new ProgramCard(Movement.LEFT_TURN, 2, true));
        container.handCard[3].setCard(new ProgramCard(Movement.LEFT_TURN, 3, true));
        container.handCard[4].setCard(null);

        for (int i = 0; i < Player.MAX_DRAW_CARDS; i++) {
            if (i < Player.MAX_PLAYER_CARDS - 1) {
                container.drawnCard[i].setCard(null);
            } else {
                container.drawnCard[i].setCard(new ProgramCard(Movement.RIGHT_TURN, i, true));
            }
        }

        container.randomizeHand();

        Set<Integer> ids = new HashSet<>();
        for (CardSlot cardSlot : container.handCard) {
            if (cardSlot.getCard() == null) {
                continue;
            }
            assertFalse("duplicate card found! id=" + cardSlot.getCard().getPriority() + " known ids=" + ids, ids.contains(cardSlot.getCard().getPriority()));
            ids.add(cardSlot.getCard().getPriority());
        }

        for (CardSlot cardSlot : container.drawnCard) {
            if (cardSlot.getCard() == null) {
                continue;
            }
            assertFalse("duplicate card found! id=" + cardSlot.getCard().getPriority() + " known ids=" + ids, ids.contains(cardSlot.getCard().getPriority()));
            ids.add(cardSlot.getCard().getPriority());
        }

        assertEquals(new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8)), ids);


    }

    @Test
    public void gettingNonExistingCardShouldReturnNull() {
        assertNull(container.getCard(SlotType.HAND, 1));
    }

    @Test
    public void settingThenGettingCardAtId1ShouldReturnSameCard() {
        Card card = new ProgramCard(Movement.LEFT_TURN, 100, true);
        container.handCard[0].setCard(card);
        assertEquals(card, container.getCard(SlotType.HAND, 0));
    }

    @Test
    public void overridingCardShouldReturnNewCard() {
        assertNotNull(container.getCard(SlotType.DRAWN, 1));
        Card card = new ProgramCard(Movement.LEFT_TURN, 100, true);
        container.drawnCard[1].setCard(card);

        assertEquals(card, container.getCard(SlotType.DRAWN, 1));
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void gettingNonValidIdShouldThrowException() {
        container.getCard(SlotType.HAND, 5);
    }

    @Test
    public void havingNullOnHandShouldReturnTrueInvalidHand() {
        assertTrue(container.hasInvalidHand());
    }

    @Test
    public void havingNotNullOnAllHandSlotsShouldReturnFalseInvalidHand() {
        assertTrue(container.hasInvalidHand());
        container.randomizeHand();
        assertFalse(container.hasInvalidHand());
    }

    @Test
    public void lockedSlotsShouldBeDisabled() {
        Card card = new ProgramCard(Movement.LEFT_TURN, 100, true);

        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {
            container.handCard[i].setCard(card);
        }
        assertFalse(container.hasInvalidHand());
        final int damage = 6;
        final int hp = Player.MAX_HEALTH - damage; //4
        container.getPlayer().damage(damage);
        assertEquals(hp, container.getPlayer().getHealth());
        assertTrue(container.handCard[hp].isDisabled());
    }
}