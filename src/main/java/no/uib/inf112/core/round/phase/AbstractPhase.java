package no.uib.inf112.core.round.phase;

/**
 * @author Elg
 */
public abstract class AbstractPhase implements Phase {

    private final long totalRunTime;

    /**
     * @param totalRunTime The total run time in milliseconds
     */
    public AbstractPhase(long totalRunTime) {
        this.totalRunTime = totalRunTime;
    }

    @Override
    public long getRunTime() {
        return totalRunTime;
    }
}
