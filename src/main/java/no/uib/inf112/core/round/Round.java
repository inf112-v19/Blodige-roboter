package no.uib.inf112.core.round;

import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.MapHandler;
import no.uib.inf112.core.round.phase.Phase;
import no.uib.inf112.core.screens.GameScreen;

import java.util.List;

/**
 * @author Elg
 */
public class Round {

    private final int phasesAmount;
    private List<Phase> registerPhases;
    private List<Phase> cleanupPhases;

    Round(int phasesAmount, List<Phase> registerPhases, List<Phase> cleanupPhases) {
        this.phasesAmount = phasesAmount;
        this.registerPhases = registerPhases;
        this.cleanupPhases = cleanupPhases;
    }

    public void startRound() {
        GameGraphics.getRoboRally().getDeck().shuffle();
        MapHandler map = GameGraphics.getRoboRally().getCurrentMap();

        long totalDelay = 0;

        for (int i = 0; i < phasesAmount; i++) {
            for (Phase phase : registerPhases) {

                final long finalTotalDelay = totalDelay;
                GameScreen.scheduleSync(() -> phase.startPhase(map), finalTotalDelay);

                totalDelay += phase.getRunTime();

            }
        }
        GameScreen.scheduleSync(() -> GameGraphics.getRoboRally().getPlayerHandler().checkGameOver(), totalDelay + 10);

        for (Phase phase : cleanupPhases) {
            GameScreen.scheduleSync(() -> {
                phase.startPhase(map);
            }, totalDelay + 10);
        }

        GameScreen.scheduleSync(() -> GameGraphics.getRoboRally().getPlayerHandler().startTurn(), totalDelay + 20);
    }
}
