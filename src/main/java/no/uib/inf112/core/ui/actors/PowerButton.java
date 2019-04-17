package no.uib.inf112.core.ui.actors;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.screens.GameScreen;
import no.uib.inf112.core.ui.UIHandler;
import no.uib.inf112.core.ui.event.events.PowerDownEvent;

/**
 * @author Elg
 */
public class PowerButton extends ImageTextButton {

    public PowerButton() {
        super("", createStyle());

        addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                TextureRegion buttonTexture = GameGraphics.getRoboRally().getPlayerHandler().mainPlayer().willPowerDown() ? UIHandler.NOT_POWER_DOWN_TEXTURE : UIHandler.POWER_DOWN_TEXTURE;
                getStyle().imageUp = new TextureRegionDrawable(buttonTexture);
                updateImage();

                GameScreen.getCPEventHandler().fireEvent(new PowerDownEvent());

            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                super.enter(event, x, y, pointer, fromActor);
                //darken when hovering over to signal this object can be clicked
                getColor().a -= 0.25f;
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor toActor) {
                super.exit(event, x, y, pointer, toActor);
                //reset color when leaving
                getColor().a += 0.25;
            }
        });
    }

    private static ImageTextButton.ImageTextButtonStyle createStyle() {
        ImageTextButton.ImageTextButtonStyle style = new ImageTextButton.ImageTextButtonStyle();
        style.imageUp = new TextureRegionDrawable(UIHandler.NOT_POWER_DOWN_TEXTURE);
        style.font = new BitmapFont();
        return style;
    }

    public void resetAlpha() {
        getColor().a = 1f;
    }
}
