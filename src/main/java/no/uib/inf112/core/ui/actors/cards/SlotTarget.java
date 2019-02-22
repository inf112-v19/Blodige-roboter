package no.uib.inf112.core.ui.actors.cards;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;

/**
 * @author Elg
 */
public class SlotTarget extends DragAndDrop.Target {

    SlotTarget(final CardSlot actor) {
        super(actor);
    }

    @Override
    public boolean drag(final DragAndDrop.Source source, final Payload payload, final float x, final float y, final int pointer) {
        return true;
    }

    @Override
    public void drop(final DragAndDrop.Source source, final Payload payload, final float x, final float y, final int pointer) {
    }
}
