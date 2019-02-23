package no.uib.inf112.core.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.Player;
import no.uib.inf112.core.player.PlayerHandler;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import no.uib.inf112.core.map.MapAction.MapAction;
import no.uib.inf112.core.player.Entity;
import no.uib.inf112.core.util.Vector2Int;

import java.util.*;

public class MapInteractOnUser {

    /**
     * Scan the entitites and the tiles they are standing on and try to do the actions on the map
     * @param entitiesOnMap all the  entities on the map
     * @return true
     */
    public boolean scan(Collection<Entity> entitiesOnMap) {

        findAndDoMovement(entitiesOnMap);

        shootLasers(entitiesOnMap);

        registerSpecialTiles(entitiesOnMap);
        return true;
    }

    /**
     * Finds all entites standing on tiles with no mapmovement(e.g. flags, wrenches)
     * @param entitiesOnMap
     */
    private void registerSpecialTiles(@NotNull Collection<Entity> entitiesOnMap) {
        ArrayList<MapAction> queue = new ArrayList<>(); //Not a queue but using it as a queue
        /**
         for (Entity entity : entitiesOnMap) {
            int x = entity.getX();
            int y = entity.getY();
            TileType tileUnderRobot = RoboRally.getCurrentMap().getBoardLayerTile(x, y);
            if (tileUnderRobot.isInGroup(TileType.Group.FLAG)) {
                //DO STUFF
            }
         */
        for (Player player : RoboRally.getPlayerHandler().getPlayers()) {
            int x = player.getRobot().getX();
            int y = player.getRobot().getY();
            TileType tileUnderRobot = RoboRally.getCurrentMap().getBoardLayerTile(x, y);
            if (tileUnderRobot.isInGroup(TileType.Group.FLAG)) {
                switch (tileUnderRobot) {
                    case FLAG1: if (player.canGetFlag(1)) player.registerFlagVisit();
                    case FLAG2: if (player.canGetFlag(2)) player.registerFlagVisit();
                    case FLAG3: if (player.canGetFlag(3)) player.registerFlagVisit();
                    case FLAG4: if (player.canGetFlag(4)) player.registerFlagVisit();
                }
            }


            MapAction mapAction = getAction(entity);
            if (mapAction != null) {//And mapaction is some special tile
                queue.add(mapAction);
            }
        }

//Sjekk om mapAction er et flag
//If ja, finn ut hvilken rang flagget har
//Spør player om han kan registrere flagget
//Set backup (alltid??) og evt. registrer flagget -> enten her eller fiks i Player.
        for (MapAction mapAction : queue) {
            mapAction.doAction();
        }
    }

    /**
     * Finds all entities in line of a laser, (this should also shoot lasers from robots).
     * @param entitiesOnMap
     */
    private void shootLasers(Collection<Entity> entitiesOnMap) {
        ArrayList<MapAction> queue = new ArrayList<>(); //Not a queue but using it as a queue
        for (Entity entity : entitiesOnMap) {
            MapAction mapAction = getAction(entity);
            if (mapAction != null) {//And mapaction is laser
                queue.add(mapAction); // Add every action thats needed
            }
        }
        //Need additional logic since only robot closest to laser start is hit, also shoot lasers from robots

        for (MapAction mapAction : queue) {
            mapAction.doAction();
        }
    }

    /**
     * finds all enitites standing on coneyors and does logic to move those that should move
     * @param entitiesOnMap
     */
    private void findAndDoMovement(Collection<Entity> entitiesOnMap) {
        Map<Vector2Int, Entity> posRobotMap = new HashMap<>(entitiesOnMap.size());
        for (Entity entity : entitiesOnMap) {
            posRobotMap.put(new Vector2Int(entity.getX(), entity.getY()), entity);
        } //Keep track of old positions

        ArrayList<MapAction> queue = new ArrayList<>(); //Not a queue but using it as a queue
        for (Entity entity : entitiesOnMap) {
            MapAction mapAction = getAction(entity);
            if (mapAction != null) {//And mapaction is conveyor/gear
                queue.add(mapAction); // Add every action thats needed
            }
        }

        Map<Entity, Entity> entityConflictWithEntity = new HashMap<>(); //Currently not used just stores information
        ArrayList<MapAction> conflictActions = new ArrayList<>();
        for (MapAction mapAction : queue) {
            if (posRobotMap.containsKey(mapAction.getResultOfMovement())) {
                entityConflictWithEntity.put(mapAction.getParent(), posRobotMap.get(mapAction.getResultOfMovement()));
                conflictActions.add(mapAction);
                queue.remove(mapAction);
            } else {
                posRobotMap.put(mapAction.getResultOfMovement(), mapAction.getParent());
                posRobotMap.remove(new Vector2Int(mapAction.getParent().getX(), mapAction.getParent().getY()));
            }
        }

        for (MapAction mapAction : conflictActions) {
            if (posRobotMap.containsKey(mapAction.getResultOfMovement())) {
                Entity conflictingRobot = posRobotMap.get(mapAction.getResultOfMovement());
                if (new Vector2Int(conflictingRobot.getX(), conflictingRobot.getY()) == mapAction.getResultOfMovement()) {
                    //do nothing movement is not allowed on stationary other robot
                } else {
                    // Revert conflicting robot nobody moves!
                    for (MapAction mapAction1 : queue) {
                        if (mapAction1.getParent() == conflictingRobot) {
                            queue.remove(mapAction1);
                            Vector2Int pos = new Vector2Int(conflictingRobot.getX(), conflictingRobot.getY());
                            if (!posRobotMap.containsKey(pos)) { // No robot on this position
                                posRobotMap.remove(mapAction1.getResultOfMovement());
                                posRobotMap.put(pos, conflictingRobot);
                            } else {
                                // This is dangerous, for what if a robot moved to this position?
                                // Handle that someone moved to this position, recursive function call?
                            }

                        }
                    }
                }
            } else {
                //conflict solved itself
                queue.add(mapAction);
            }
        }

        for (MapAction mapAction : queue) {
            mapAction.doAction();
        }
    }

    /**
     * Returns a mapAction object according to the tile the player is standing on
     * @param entity entity standing on a tile
     * @return a mapAction if standing on a tile that should have corresponding action, null otherwise
     */
    @Nullable
    private MapAction getAction(@NotNull Entity entity) {
        TileType tile = entity.getTileType();
        //Switch on tile
        return null;
    }

}
