package no.uib.inf112.core.map;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import no.uib.inf112.core.player.Entity;
import no.uib.inf112.core.player.Player;

import java.util.ArrayList;
import java.util.Collection;

public class MapInteractOnUser {

    public boolean scan(Collection<Entity> entitiesOnMap) {
        ArrayList<MapAction> queue = new ArrayList<>(); //Not a queue but using it as a queue
        for (Entity entity:entitiesOnMap) {
            MapAction mapAction = getAction(entity);
            if (mapAction != null) {
                queue.add(mapAction); // Add every action thats needed
            }
        }
        queue.sort(null);
        if (!queue.isEmpty()) {
            MapAction last = null;
            int indexOfLast = 0;
            for (int i = 1; i<queue.size();i++) {
                MapAction mapAction = queue.get(i);

                if  (last != null && last.compareTo(mapAction)==0) {
                    while (last.compareTo(queue.get(i))==0) {
                        queue.remove(i);
                        i++;
                    }
                    queue.remove(indexOfLast);
                    last = null;
                }
                last = queue.get(i);
                indexOfLast = i;
            }
            queue.sort(null);
        }

        for (MapAction mapAction : queue) {
            mapAction.doAction();
        }

        // Check for flag/hole

        return true;
    }


    private MapAction getAction(Entity entity) {
        TiledMapTile tile = entity.getTile();
        //Switch on tile
        return null;
    }

}
