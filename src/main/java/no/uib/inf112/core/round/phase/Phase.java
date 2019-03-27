package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.map.MapHandler;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public interface Phase {

    /**
     * @param map The map to run this phase on
     */
    void startPhase(@NotNull MapHandler map);

    /**
     * @return How long this phase will take
     */
    long getRunTime();
}
