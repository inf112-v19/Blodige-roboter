package no.uib.inf112.core.map.cards;

public interface Deck {


    /**
     * Lets you draw the n next cards in the cards. Class keeps track of the index of the next card.
     * Since cards is reshuffled after each player has drawn once the number of cards in cards will never be exceeded.
     *
     * @param amount How many cards you want to draw from the cards
     * @return A list of the card-objects you drew from the cards
     */
    Card[] draw(int amount);


    /**
     * Method will shuffle all cards in cards and reset the index of the next card to be drawn to 0.
     * Shufflereps is how many times you want to go through the cards to shuffle. (Higher rep -> more randomness)
     * Nb: to change shufflereps use method setShufflereps()
     */
    void shuffle();


    /**
     * Changes the number of repetitions that the shuffle-method does.
     *
     * @param newShuffleReps How many times you want to go through the cards while shuffling. Higher rep -> more random. Default value is 5
     */
    void setShuffleReps(int newShuffleReps);


    /**
     * Gets current cards in cards
     *
     * @return cards
     */
    Card[] getCards();
}
