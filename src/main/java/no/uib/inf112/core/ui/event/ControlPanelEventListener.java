package no.uib.inf112.core.ui.event;

/**
 * @author Elg
 */
public interface ControlPanelEventListener<T extends ControlPanelEvent> {

    void clicked(T event);

}
