package no.uib.inf112.core.ui.event.events;

import no.uib.inf112.core.ui.event.ControlPanelEvent;

/**
 * @author Elg
 */
public class CardClickedEvent implements ControlPanelEvent {

    private final int id;

    /**
     * @param id id of card on screen
     */
    public CardClickedEvent(int id) {
        this.id = id;
    }

    /**
     * @return The identification of the card clicked
     */
    public int getId() {
        return id;
    }
}
