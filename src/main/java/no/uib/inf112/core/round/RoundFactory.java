package no.uib.inf112.core.round;

import no.uib.inf112.core.round.phase.Phase;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Elg
 */
public class RoundFactory {

    private List<Phase> registerPhases;
    private List<Phase> cleanupPhases;
    private int rounds;

    public RoundFactory() {
        registerPhases = new ArrayList<>();
        cleanupPhases = new ArrayList<>();
    }

    @Nullable
    public Round generate() {
        if (rounds <= 0) {
            System.err.println("There must at least be 1 round");
            return null;
        }
        if (registerPhases.isEmpty()) {
            System.err.println("No phases added");
            return null;
        }
        return new Round(rounds, registerPhases, cleanupPhases);
    }

    public void addRegisterPhase(Phase phase) {
        registerPhases.add(phase);
    }

    public void addCleanupPhase(Phase phase) {
        cleanupPhases.add(phase);
    }

    public List<Phase> getPhases() {
        return registerPhases;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int roundNumber) {
        this.rounds = roundNumber;
    }
}
