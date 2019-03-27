package no.uib.inf112.core.round;

import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.round.phase.ActionPhase;
import no.uib.inf112.core.round.phase.CleanupPhase;
import no.uib.inf112.core.round.phase.ConveyorPhase;
import no.uib.inf112.core.round.phase.PlayerPhase;

/**
 * @author Elg
 */
public class DefaultGameRule {

    public static Round generate() {
        RoundFactory round = new RoundFactory();

        round.setRounds(5);

        round.addRegisterPhase(new PlayerPhase(400));

        round.addRegisterPhase(new ConveyorPhase(100));
//        round.addRegisterPhase(new ActionPhase(TileType.PUSHER, 100));
//        round.addRegisterPhase(new ActionPhase(TileType.GEAR, 100));

//        round.addRegisterPhase(new ActionPhase(TileType.LASER, 250));

        round.addRegisterPhase(new ActionPhase(TileType.WRENCH, 50));
        round.addRegisterPhase(new ActionPhase(TileType.HAMMER_AND_WRENCH, 0));
        round.addRegisterPhase(new ActionPhase(TileType.FLAG, 10));

        round.addCleanupPhase(new CleanupPhase(TileType.HAMMER_AND_WRENCH));

        return round.generate();
    }
}
