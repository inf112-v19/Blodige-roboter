package no.uib.inf112.player;

public class Card {

    public enum Movement {
        RIGHT_TURN,
        LEFT_TURN,
        U_TURN,
        MOVE_1,
        MOVE_2,
        MOVE_3,
        BACK_UP
    }

    private final Movement ACTION;
    private final int PRIORITY;

    /**
     * @param action The movement the card will prescribe
     * @param priority The unique priority of the card
     */
    public Card(Movement action, int priority) {
        this.ACTION = action;
        this.PRIORITY = priority;
    }

    public Movement getAction() {
        return this.ACTION;
    }

    public int getPriority() {
        return this.PRIORITY;
    }
}

