package no.uib.inf112.core.round;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.round.phase.Phase;

import java.util.List;

/**
 * @author Elg
 */
public class Round {

    private final int phasesAmount;
    private List<Phase> phases;

    Round(int phasesAmount, List<Phase> phases) {
        this.phasesAmount = phasesAmount;
        this.phases = phases;
    }

    public void startRound() {

        GameGraphics.getRoboRally().getDeck().shuffle();
        MapHandler map = GameGraphics.getRoboRally().getCurrentMap();

        long totalDelay = 0;

        for (int i = 0; i < phasesAmount; i++) {
            for (Phase phase : phases) {

                final long finalTotalDelay = totalDelay;
                GameGraphics.scheduleSync(() -> {
                            phase.startPhase(map);
                            System.out.println("new phase " + phase + " begun after " + finalTotalDelay + " ms");
                        }
                        , finalTotalDelay);

                totalDelay += phase.getRunTime();
            }
        }
        System.out.println("totalDelay = " + totalDelay);
        GameGraphics.scheduleSync(() -> GameGraphics.getRoboRally().getPlayerHandler().startTurn(), totalDelay);

    }
}
