package no.uib.inf112.core.ui.event;

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

    public void registerListener(Class<? extends ControlPanelEvent> eventType, ControlPanelEventListener listener) {
        listeners.computeIfAbsent(eventType, clazz -> new HashSet<>());
        Set<ControlPanelEventListener> set = listeners.get(eventType);
        set.add(listener);
    }

    public void fireEvent(ControlPanelEvent event) {
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
