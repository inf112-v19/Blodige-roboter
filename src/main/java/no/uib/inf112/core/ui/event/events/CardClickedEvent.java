package no.uib.inf112.core.ui.event.events;

import no.uib.inf112.core.ui.event.ControlPanelEvent;

/**
 * @author Elg
 */
public class CardClickedEvent implements ControlPanelEvent {

    private final int id;

    public CardClickedEvent(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
