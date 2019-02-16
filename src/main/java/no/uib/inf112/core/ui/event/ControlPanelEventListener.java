package no.uib.inf112.core.ui.event;

import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public interface ControlPanelEventListener<T extends ControlPanelEvent> {

    /**
     * Method called when an event of the type {@code T} is fired
     *
     * @param event The event that was fired
     */
    void clicked(@NotNull T event);

}
