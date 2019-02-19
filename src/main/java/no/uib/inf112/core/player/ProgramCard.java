package no.uib.inf112.core.player;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import no.uib.inf112.core.ui.UIHandler;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ProgramCard implements Card {

    private final Movement action;
    private final int priority;
    private TextureRegion textureRegion;

    /**
     * @param action   The movement the card will impose
     * @param priority The unique priority of the card
     */
    public ProgramCard(@NotNull Movement action, int priority, boolean headless) {
        this.action = action;
        this.priority = priority;
        if (!headless) {
            this.textureRegion = UIHandler.CARDS_TEXTURE;
        }
    }

    /**
     * @return The action (movement) imposed by this card
     */
    @NotNull
    @Override
    public Movement getAction() {
        return this.action;
    }

    /**
     * @return The priority of this card
     */
    @Override
    public int getPriority() {
        return this.priority;
    }

    @NotNull
    @Override
    public TextureRegion getRegionTexture() {
        return textureRegion;
    }

    @NotNull
    @Override
    public String getTooltip() {
        return getAction().getTooltip() + "\nPriority: " + priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        ProgramCard that = (ProgramCard) o;
        return priority == that.priority &&
                action == that.action;
    }

    @Override
    public int hashCode() {
        return Objects.hash(action, priority);
    }
}

