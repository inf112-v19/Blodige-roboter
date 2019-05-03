package no.uib.inf112.core.multiplayer.dtos;

import no.uib.inf112.core.map.cards.Movement;
import org.jetbrains.annotations.NotNull;

public class CardDto {
    public final Movement movement;
    public final int priority;

    public CardDto(@NotNull Movement movement, int priority) {
        this.movement = movement;
        this.priority = priority;
    }
}
