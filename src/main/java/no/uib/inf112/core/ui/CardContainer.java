package no.uib.inf112.core.ui;

import no.uib.inf112.core.player.Card;
import no.uib.inf112.core.player.Deck;
import no.uib.inf112.core.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

/**
 * @author Elg
 */
public class CardContainer {

    @NotNull
    private final Player holder;
    @NotNull
    private final Deck deck;
    private Random random;

    CardActor[] handCard;
    CardActor[] drawnCard;

    public CardContainer(@NotNull Player holder, @NotNull Deck deck) {
        this.holder = holder;
        this.deck = deck;
        this.random = new Random();

        handCard = new CardActor[Player.MAX_PLAYER_CARDS];
        drawnCard = new CardActor[Player.MAX_HEALTH];
    }

    void draw() {

        for (CardActor actor : handCard) {
            actor.setCard(null);
        }

        int amount = holder.getHealth();
        System.out.println("drawnCard.l = " + drawnCard.length);
        System.out.println("amount = " + amount);
        Card[] draw = deck.draw(amount);

        for (int i = 0; i < Player.MAX_HEALTH; i++) {
            if (i >= amount) {
                drawnCard[i].setCard(null);
                drawnCard[i].getColor().a = 0.70f;
            } else {
                drawnCard[i].setCard(draw[i]);
            }
        }
    }

    //TODO test
    public void randomizeHand() {
        //make sure all previously picked cards are back in the drawn cards array
        for (CardActor handCard : handCard) {
            if (handCard.getCard() != null) {
                for (CardActor drawnCard : drawnCard) {
                    if (drawnCard.getCard() == null) {
                        drawnCard.setCard(handCard.getCard());
                    }
                }
            }
        }

        //TODO assert handCards is empty

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


    //TODO test
    boolean setCard(@NotNull CardActor cardActor, @Nullable Card card) {
        return setCard(cardActor.getSlotType(), cardActor.getSlotId(), card);
    }

    //TODO test

    /**
     * @param slotType
     * @param id
     * @param card
     * @return If the card at given location was updated
     */
    boolean setCard(@NotNull SlotType slotType, int id, @Nullable Card card) {
        switch (slotType) {
            case HAND:
                handCard[id].setCard(card);
                return true;
            case DRAWN:
                if (id < holder.getHealth()) {
                    drawnCard[id].setCard(card);
                    return true;
                } else {
                    return false;
                }
            default:
                throw new IllegalArgumentException("Failed to find cards of type " + slotType);
        }
    }


    //TODO test

    /**
     * @return The card at the given id
     */
    public Card getCard(@NotNull CardActor cardActor) {
        return getCard(cardActor.getSlotType(), cardActor.getSlotId());
    }

    /**
     * @param id The local id of the card
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

    public boolean hasInvalidHand() {
        for (CardActor actor : handCard) {
            if (actor.getCard() == null) {
                return true;
            }
        }
        return false;
    }
}
