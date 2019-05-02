package no.uib.inf112.core.round.phase;

import no.uib.inf112.core.map.MapHandler;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public interface Phase {

    /**
     * The number used when the current phase is not known
     */
    int UNKNOWN_PHASE_NUMBER = -1;

    /**
     * Start this phase with the given phase number
     *
     * @param map The map to run this phase on
     * @param phaseNr The phase number. If unknown {@link #UNKNOWN_PHASE_NUMBER} is given.
     */
    void startPhase(@NotNull MapHandler map, int phaseNr);

    /**
     * Start phase where the phase number is not known
     *
     * @param map
     *     The map to run this phase on
     */
    default void startPhase(@NotNull MapHandler map) {
        startPhase(map, UNKNOWN_PHASE_NUMBER);
    }

    /**
     * @return How long this phase will take
     */
    long getRunTime();
}
