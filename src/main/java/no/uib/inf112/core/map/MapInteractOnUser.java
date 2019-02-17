package no.uib.inf112.core.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.map.MapAction.MapAction;
import no.uib.inf112.core.player.Entity;
import no.uib.inf112.core.player.Robot;
import no.uib.inf112.core.util.Vector2Int;

import java.util.*;

public class MapInteractOnUser {

    public boolean scan(Collection<Entity> entitiesOnMap) {

        findAndDoMovement(entitiesOnMap);

        shootLasers(entitiesOnMap);

        registerSpecialTiles(entitiesOnMap);
        return true;
    }

    private void registerSpecialTiles(Collection<Entity> entitiesOnMap) {
        ArrayList<MapAction> queue = new ArrayList<>(); //Not a queue but using it as a queue
        for (Entity entity : entitiesOnMap) {
            MapAction mapAction = getAction(entity);
            if (mapAction != null) {//And mapaction is some special tile
                queue.add(mapAction);
            }
        }

        for (MapAction mapAction : queue) {
            mapAction.doAction();
        }
    }

    private void shootLasers(Collection<Entity> entitiesOnMap) {
        ArrayList<MapAction> queue = new ArrayList<>(); //Not a queue but using it as a queue
        for (Entity entity : entitiesOnMap) {
            MapAction mapAction = getAction(entity);
            if (mapAction != null) {//And mapaction is laser
                queue.add(mapAction); // Add every action thats needed
            }
        }

        for (MapAction mapAction : queue) {
            mapAction.doAction();
        }
    }

    private void findAndDoMovement(Collection<Entity> entitiesOnMap) {
        Map<Vector2Int, Entity> posRobotMap = new HashMap<>(entitiesOnMap.size());
        for (Entity entity : entitiesOnMap) {
            posRobotMap.put(new Vector2Int(entity.getX(), entity.getY()), entity);
        }
        ArrayList<MapAction> queue = new ArrayList<>(); //Not a queue but using it as a queue
        for (Entity entity : entitiesOnMap) {
            MapAction mapAction = getAction(entity);
            if (mapAction != null) {//And mapaction is conveyor/gear
                queue.add(mapAction); // Add every action thats needed
            }
        }

        Map<Entity, Entity> entityConflictWithEntity = new HashMap<>();
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
                    // This is dangerous, for what if a robot moved to this position?
                    for (MapAction mapAction1 : queue) {
                        if (mapAction1.getParent() == conflictingRobot) {
                            queue.remove(conflictingRobot);
                            Vector2Int pos = new Vector2Int(conflictingRobot.getX(), conflictingRobot.getY());
                            if (!posRobotMap.containsKey(pos)) {
                                posRobotMap.remove(mapAction1.getResultOfMovement());
                                posRobotMap.put(pos, conflictingRobot);
                            } else {
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

    private MapAction getAction(Entity entity) {
        TiledMapTile tile = entity.getTile();
        //Switch on tile
        return null;
    }

}
