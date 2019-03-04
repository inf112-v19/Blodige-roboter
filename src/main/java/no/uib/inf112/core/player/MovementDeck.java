package no.uib.inf112.core.player;


import java.util.Random;

public class MovementDeck implements Deck {

    private MovementCard[] cards;
    private int nextCard = 0;
    private final int DECK_SIZE = 84; //Standard deck size from rulebook
    private int shufflereps = 5; //Default repetitions for shuffle-method.


    /**
     * Create new standard deck.
     * MovementDeck structure (differt card types and priovrities etc) explicit in initialize-method
     */
    public MovementDeck() {
        cards = new MovementCard[DECK_SIZE];
        initialize();
        shuffle();
    }


    @Override
    public MovementCard[] draw(int amount) {
        MovementCard[] drawn = new MovementCard[amount];
        for (int i = 0; i < amount; i++) {
            drawn[i] = cards[nextCard++];
        }
        return drawn;
    }


    @Override
    public void shuffle() {
        Random rnd = new Random();
        for (int i = 0; i < shufflereps; i++) {
            for (int j = 0; j < DECK_SIZE; j++) {
                int randIndex = rnd.nextInt(DECK_SIZE);
                MovementCard temp = cards[j];
                cards[j] = cards[randIndex];
                cards[randIndex] = temp;
            }
        }
        nextCard = 0;
    }


    @Override
    public void setShuffleReps(int shufflereps) {
        this.shufflereps = shufflereps;
    }


    /**
     * Initializes the deck with standard cards from rulebook
     */
    private void initialize() {
        for (int i = 0; i < 18; i++) {
            cards[i] = new MovementCard(Movement.LEFT_TURN, (80 + 20 * i));
        }
        for (int i = 18; i < 36; i++) {
            cards[i] = new MovementCard(Movement.RIGHT_TURN, (70 + 20 * (i - 18)));
        }
        for (int i = 36; i < 42; i++) {
            cards[i] = new MovementCard(Movement.U_TURN, (10 + 10 * (i - 36)));
        }
        for (int i = 42; i < 60; i++) {
            cards[i] = new MovementCard(Movement.MOVE_1, (490 + 10 * (i - 42)));
        }
        for (int i = 60; i < 72; i++) {
            cards[i] = new MovementCard(Movement.MOVE_2, (670 + 10 * (i - 60)));
        }
        for (int i = 72; i < 78; i++) {
            cards[i] = new MovementCard(Movement.MOVE_3, (790 + 10 * (i - 72)));
        }
        for (int i = 78; i < 84; i++) {
            cards[i] = new MovementCard(Movement.BACK_UP, (430 + 10 * (i - 78)));
        }
    }


    @Override
    public MovementCard[] getCards() {
        return cards;
    }
}
