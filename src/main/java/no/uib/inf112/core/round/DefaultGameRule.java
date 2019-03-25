package no.uib.inf112.core.round;

import no.uib.inf112.core.map.tile.TileType;
import no.uib.inf112.core.round.phase.ActionPhase;
import no.uib.inf112.core.round.phase.PlayerPhase;

/**
 * @author Elg
 */
public class DefaultGameRule {

    public static Round generate() {
        RoundFactory round = new RoundFactory();

        round.setRounds(5);

        round.addPhase(new PlayerPhase(500));

        round.addPhase(new ActionPhase(TileType.EXPRESS_CONVEYOR, 250)); //TODO only move those who have this
        round.addPhase(new ActionPhase(TileType.CONVEYOR, 100)); //TODO move both CONVEYOR and EXPRESS_CONVEYOR
//        round.addPhase(new ActionPhase(TileType.PUSHER, 100));
//        round.addPhase(new ActionPhase(TileType.GEAR, 100));

//        round.addPhase(new ActionPhase(TileType.LASER, 250));

        round.addPhase(new ActionPhase(TileType.HAMMER_AND_WRENCH, 100));
        round.addPhase(new ActionPhase(TileType.WRENCH, 0));

//        round.addPhase(new ActionPhase(TileType.FLAG, 0));

        return round.generate();
    }
}
