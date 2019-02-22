package no.uib.inf112.core.ui;

import no.uib.inf112.core.player.Card;
import no.uib.inf112.core.player.Deck;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.ui.actors.cards.CardSlot;
import no.uib.inf112.core.ui.actors.cards.SlotType;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * @author Elg
 */
public class CardContainer {

    @NotNull
    private final Player holder;
    private final Deck deck;
    private Random random;

    final CardSlot[] handCard;
    final CardSlot[] drawnCard;

    /**
     * Before using any of the methods in this class you need to initiate {@link #handCard} and {@link #drawnCard} with {@link CardSlot}s.
     * <p>
     * When running with Lwjgl (ie normally) this is done in {@link UIHandler#create()}, but when testing this must be done.
     *
     * @param holder The player whos cards these are
     * @param deck   The deck used to draw new cards
     * @see no.uib.inf112.core.ui.CardContainerTest#setUp()
     */
    @SuppressWarnings("JavadocReference")
    public CardContainer(@NotNull Player holder, Deck deck) {
        this.holder = holder;
        this.deck = deck;
        random = new Random();

        handCard = new CardSlot[Player.MAX_PLAYER_CARDS];
        drawnCard = new CardSlot[Player.MAX_DRAW_CARDS];
    }

    /**
     * Draw cards for the players drawn cards and removes any non-disabled cards from the players hand
     */
    public void draw() {
        for (CardSlot actor : handCard) {
            if (!actor.isDisabled()) {
                actor.setCard(null);
            }
        }

        int amount = holder.getHealth();
        Card[] draw = deck.draw(amount);

        for (int i = 0; i < Player.MAX_DRAW_CARDS; i++) {
            if (i >= amount) {
                drawnCard[i].setCard(null);
                drawnCard[i].getColor().a = 0.70f;
            } else {
                drawnCard[i].setCard(draw[i]);
            }
        }
    }


    //FIXME probably broken

    /**
     * Set the players hand to a random selection of the drawn cards.
     * Any disabled cards should not be updated
     */
    public void randomizeHand() {
        //make sure all previously picked cards are back in the drawn cards array
        for (CardSlot handCard : handCard) {
            if (handCard.getCard() != null && !handCard.isDisabled()) {

                for (CardSlot drawnCard : drawnCard) {
                    if (drawnCard.getCard() == null) {
                        drawnCard.setCard(handCard.getCard());
                    }
                }
            }
        }

        //TODO assert handCards is empty/disabled

        for (int i = 0; i < Player.MAX_PLAYER_CARDS; i++) {
            int randomCard = random.nextInt(drawnCard.length);
            if (drawnCard[randomCard].getCard() == null) {
                //if there are no more valid cards return
                if (Arrays.stream(drawnCard).noneMatch(cardActor -> Objects.nonNull(cardActor.getCard()))) {
                    return;
                }
                i--;
                continue;
            }
            handCard[i].setCard(drawnCard[randomCard].getCard());
            drawnCard[randomCard].setCard(null);
        }
    }

    /**
     * TODO test if this return the correct cards
     *
     * @param slotType Where to put the card
     * @param id       The id(index) of the card
     * @return The card at the given id
     */
    public Card getCard(@NotNull SlotType slotType, int id) {
        switch (slotType) {
            case HAND:
                return handCard[id].getCard();
            case DRAWN:
                return drawnCard[id].getCard();
            default:
                throw new IllegalArgumentException("Failed to find cards of type " + slotType);
        }
    }

    /**
     * TODO use this combined to test if we should start/stop timer for randomizing players hand
     *
     * @return If any of the player hand cards are {@code null}
     */
    public boolean hasInvalidHand() {
        for (CardSlot actor : handCard) {
            if (actor.getCard() == null) {
                return true;
            }
        }
        return false;
    }

    /**
     * @return The player who owns this container
     */
    public Player getPlayer() {
        return holder;
    }
}