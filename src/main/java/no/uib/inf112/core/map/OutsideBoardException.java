package no.uib.inf112.core.map;

/**
 * Exception thrown when robot walks outside the map
 */
public class OutsideBoardException extends Exception {

    /**
     * Creates an exception with corresponding error message
     *
     * @param errorMessage error message to display
     */
    public OutsideBoardException(String errorMessage) {
        super(errorMessage);
    }

    /**
     * Creates an exception with no error message
     */
    public OutsideBoardException() {
        super();
    }

}
