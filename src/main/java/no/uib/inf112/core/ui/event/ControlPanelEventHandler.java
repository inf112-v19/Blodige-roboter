package no.uib.inf112.core.ui.event;

import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Elg
 */
public class ControlPanelEventHandler {

    private HashMap<Class<? extends ControlPanelEvent>, Set<ControlPanelEventListener>> listeners;

    public ControlPanelEventHandler() {
        listeners = new HashMap<>();
    }

    /**
     * @param eventType The type of event to listen to
     * @param listener  The listener to be called when an event of type {@code eventType} is fired
     */
    public void registerListener(@NotNull Class<? extends ControlPanelEvent> eventType, @NotNull ControlPanelEventListener<? extends ControlPanelEvent> listener) {

        listeners.computeIfAbsent(eventType, clazz -> new HashSet<>());
        Set<ControlPanelEventListener> set = listeners.get(eventType);
        set.add(listener);
    }

    /**
     * Call {@link ControlPanelEventListener#clicked(ControlPanelEvent)} for all listeners of the correct event type
     *
     * @param event The event to fire
     */
    public void fireEvent(@NotNull ControlPanelEvent event) {
        Set<ControlPanelEventListener> eventListeners = listeners.get(event.getClass());
        if (eventListeners == null) {
            return;
        }
        for (ControlPanelEventListener listener : eventListeners) {
            //noinspection unchecked
            listener.clicked(event);
        }
    }
}
