package no.uib.inf112.core.ui.actors.cards;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Tooltip;
import no.uib.inf112.core.ui.UIHandler;

/**
 * @author Elg
 */
public class TooltipListener extends Tooltip<Label> {

    private final CardSlot parent;
    private Label label;

    TooltipListener(CardSlot parent) {
        super(null);
        this.parent = parent;

        Label.LabelStyle style = new Label.LabelStyle();

        UIHandler.card_font_parameter.size = 16;
        style.font = UIHandler.card_font_generator.generateFont(UIHandler.card_font_parameter);

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
