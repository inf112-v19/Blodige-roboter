package no.uib.inf112.core.player;

public interface Deck {


    /**
     * Lets you draw the n next cards in the deck. Class keeps track of the index of the next card.
     * Since deck is reshuffled after each player has drawn once the number of cards in deck will never be exceeded.
     *
     * @param amount How many cards you want to draw from the deck
     * @return A list of the card-objects you drew from the deck
     */
    Card[] draw(int amount);


    /**
     * Method will shuffle all cards in deck and reset the index of the next card to be drawn to 0.
     * Shufflereps is how many times you want to go through the deck to shuffle. (Higher rep -> more randomness)
     * Nb: to change shufflereps use method setShufflereps()
     */
    void shuffle();


    /**
     * Changes the number of repetitions that the shuffle-method does.
     *
     * @param newShuffleReps How many times you want to go through the deck while shuffling. Higher rep -> more random. Default value is 5
     */
    void setShuffleReps(int newShuffleReps);


    /**
     * Gets current cards in deck
     *
     * @return cards
     */
    Card[] getCards();
}
