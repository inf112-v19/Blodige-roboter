package no.uib.inf112.core.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * @author Elg
 */
public abstract class ControlPanelElement extends ImageButton implements DisabledVisualizer {

    public ControlPanelElement(TextureRegion textureRegion) {
        super(new TextureRegionDrawable(textureRegion));
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        act();
    }
}
