package no.uib.inf112.core.player;

/**
 * All legal movements that robots can do.
 */

public enum Movement {

    RIGHT_TURN("Turn to the right"),
    LEFT_TURN("Turn to the left"),
    U_TURN("Turn around"),
    MOVE_1("Move one tile forward"),
    MOVE_2("Move two tile forward"),
    MOVE_3("Move three tile forward"),
    BACK_UP("Move one tile backward");

    private final String tooltip;

    Movement(String tooltip) {
        this.tooltip = tooltip;
    }

    public String getTooltip() {
        return tooltip;
    }

    @Override
    public String toString() {
        return name().replace("_", " ");
    }}

