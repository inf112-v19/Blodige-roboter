package no.uib.inf112.player;


import java.util.Random;

public class Deck {

    private Card[] cards;
    private int nextCard = 0;
    private int size = 84; //Standard deck size from rulebook
    private int shufflereps = 5; //Default repetitions for shuffle-method.


    /**
     * Create new standard deck.
     * Deck structure (differt card types and priovrities etc) explicit in initialize-method
     */
    public Deck() {
        this.cards = new Card[size];
        this.initialize();
        this.shuffle();
    }

    /**
     * Lets you draw the n next cards in the deck. Class keeps track of the index of the next card.
     * Since deck is reshuffled after each player has drawn once the number of cards in deck will never be exceeded.
     *
     * @param amount How many cards you want to draw from the deck
     * @return A list of the card-objects you drew from the deck
     */
    public Card[] draw(int amount) {
        Card[] drawn = new Card[amount];
        for (int i = 0; i < amount; i++) {
            drawn[i] = cards[nextCard++];
        }
        return drawn;
    }


    /**
     *  Method will shuffle all cards in deck and reset the index of the next card to be drawn to 0.
     * Shufflereps is how many times you want to go through the deck to shuffle. (Higher rep -> more randomness)
     * Nb: to change shufflereps use method setShufflereps()
     */
    public void shuffle(){
        Random rnd = new Random();
        for (int i = 0; i < shufflereps; i++) {
            for (int j = 0; j < size; j++) {
                int randIndex = rnd.nextInt(84);
                Card temp = cards[j];
                cards[j] = cards[randIndex];
                cards[randIndex] = temp;
            }
        }
        nextCard = 0;
    }


    /**
     * Changes the number of repetitions that the shuffle-method does.
     *
     * @param shufflereps How many times you want to go through the deck while shuffling. Higher rep -> more random.
     * Default value is 5
     */
    public void setShufflereps(int shufflereps) {
        this.shufflereps = shufflereps;
    }


    /**
     * Initializes the deck with standard cards from rulebook
     */
    private void initialize() {
        for (int i = 0; i < 18; i++) {
            cards[i] = new Card(Movement.LEFT_TURN, (80 + 20*i));
        }
        for (int i = 18; i < 36; i++) {
            cards[i] = new Card(Movement.RIGHT_TURN, (70 + 20*(i-18)));
        }
        for (int i = 36; i < 42; i++) {
            cards[i] = new Card(Movement.U_TURN, (10 + 10*(i-36)));
        }
        for (int i = 42; i < 60; i++) {
            cards[i] = new Card(Movement.MOVE_1, (490 + 10*(i-42)));
        }
        for (int i = 60; i < 72; i++) {
            cards[i] = new Card(Movement.MOVE_2, (670 + 10*(i-60)));
        }
        for (int i = 72; i < 78; i++) {
            cards[i] = new Card(Movement.MOVE_3, (790 + 10*(i-72)));
        }
        for (int i = 78; i < 84; i++) {
            cards[i] = new Card(Movement.BACK_UP, (430 + 10*(i-78)));
        }
    }



}
