package no.uib.inf112.core.player;

import org.jetbrains.annotations.NotNull;

public class NonPlayer extends Player {
    public NonPlayer(int x, int y, @NotNull Direction direction) {
        super(x, y, direction);
    }
}
