package no.uib.inf112.core.ui.event;

import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.ui.event.events.CardClickedEvent;
import no.uib.inf112.core.ui.event.events.PowerDownEvent;

/**
 * @author Elg
 */
public class TestListener {

    public TestListener() {

        RoboRally.getControlPanelEventHandler().registerListener(CardClickedEvent.class,
                                                                 (ControlPanelEventListener<CardClickedEvent>) event -> System.out
                                                                     .println("Clicked card nr " + event.getId()));


        RoboRally.getControlPanelEventHandler().registerListener(PowerDownEvent.class,
                                                                 (ControlPanelEventListener<PowerDownEvent>) event -> System.out
                                                                     .println("Clicked power down!"));
    }
}
