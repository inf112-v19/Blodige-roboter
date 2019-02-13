package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import no.uib.inf112.core.ui.UIHandler;

public class ProgramCard implements Card {

    private final Movement ACTION;
    private final int PRIORITY;
    private TextureRegion textureRegion;

    /**
     * @param action   The movement the card will impose
     * @param priority The unique priority of the card
     */
    public ProgramCard(Movement action, int priority, boolean headless) {
        this.ACTION = action;
        this.PRIORITY = priority;
        if (!headless) {
            this.textureRegion = UIHandler.CARDS_TEXTURE;
        }
    }

    /**
     * @return The action (movement) imposed by this card
     */
    @Override
    public Movement getAction() {
        return this.ACTION;
    }

    /**
     * @return The priority of this card
     */
    @Override
    public int getPriority() {
        return this.PRIORITY;
    }

    @Override
    public TextureRegion getRegionTexture() {
        return textureRegion;
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }
}

