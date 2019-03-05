package no.uib.inf112.core.map.cards;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.jetbrains.annotations.NotNull;

public interface Card extends Comparable<Card> {


    /**
     * @return The action (movement) imposed by this card
     */
    @NotNull
    Movement getAction();

    /**
     * @return The priority of this card
     */
    int getPriority();

    /**
     * @return The texture of this card
     */
    @NotNull
    TextureRegion getRegionTexture();

    /**
     * @return A tooltip on what this card does
     */
    @NotNull
    String getTooltip();

}
