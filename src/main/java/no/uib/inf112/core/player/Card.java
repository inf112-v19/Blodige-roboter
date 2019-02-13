package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public interface Card {


    /**
     * @return The action (movement) imposed by this card
     */
    Movement getAction();

    /**
     * @return The priority of this card
     */
    int getPriority();

    /**
     * @return The texture of this card
     */
    TextureRegion getRegionTexture();

}
