package no.uib.inf112.core.round;

import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Elg
 */
public class RoundFactory {

    private List<Phase> phases;
    private int rounds;

    public RoundFactory() {
        phases = new ArrayList<>();
    }

    @Nullable
    public Round generate() {
        if (rounds <= 0) {
            System.out.println("There must at least be 1 round");
            return null;
        }
        if (phases.isEmpty()) {
            System.out.println("No phases added");
            return null;
        }
        return new Round(rounds, phases);
    }

    public void addPhase(Phase phase) {
        phases.add(phase);
    }

    public List<Phase> getPhases() {
        return phases;
    }

    public int getRounds() {
        return rounds;
    }

    public void setRounds(int roundNumber) {
        this.rounds = roundNumber;
    }
}
