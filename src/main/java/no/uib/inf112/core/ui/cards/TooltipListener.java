package no.uib.inf112.core.ui.cards;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;

/**
 * @author Elg
 */
public class TooltipListener extends Tooltip<Label> {

    private final CardSlot parent;
    private Label label;

    public TooltipListener(CardSlot parent) {
        super(null);
        this.parent = parent;

        Label.LabelStyle style = new Label.LabelStyle();
        style.font = new BitmapFont();
        style.fontColor = Color.LIGHT_GRAY;

        label = new Label("", style);
        setActor(label);

        //configure how the tooltip act
        setInstant(true);
        getManager().animations = false;

        getManager().hideAll();

    }

    @Override
    public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
        if (parent.getCard() == null) {
            return;
        }
        label.setText(parent.getCard().getTooltip());
        super.enter(event, x, y, pointer, fromActor);
    }
}
