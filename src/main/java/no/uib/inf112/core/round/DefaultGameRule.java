package no.uib.inf112.core.round;

import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.round.phase.*;

/**
 * @author Elg
 */
public class DefaultGameRule {

    /**
     * Generate a default game round where the last phase will start after totalTime
     *
     * @param totaltime
     *     time until last round should start
     *
     * @return the generated round
     */
    public static Round generate(long totaltime) {
        RoundFactory round = new RoundFactory();

        round.setRounds(5);

        round.addRegisterPhase(new PlayerPhase((int) (totaltime * 0.33)));

        round.addRegisterPhase(new ConveyorPhase((long) (totaltime * 0.22)));
        round.addRegisterPhase(new PusherPhase((long) (totaltime * 0.22)));
        round.addRegisterPhase(new ActionPhase(TileType.GEAR, (int) (totaltime * 0.1)));

        round.addRegisterPhase(new LaserPhase((long) (totaltime * 0.27)));

        round.addRegisterPhase(new ActionPhase(TileType.WRENCH, (int) (totaltime * 0.05)));
        round.addRegisterPhase(new ActionPhase(TileType.HAMMER_AND_WRENCH, 0));
        round.addRegisterPhase(new ActionPhase(TileType.FLAG, (int) (totaltime * 0.01)));

        round.addCleanupPhase(new CleanupPhase(TileType.HAMMER_AND_WRENCH));
        round.addCleanupPhase(new CleanupPhase(TileType.ROBOT));

        return round.generate();
    }
}
