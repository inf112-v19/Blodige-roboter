package no.uib.inf112.core.ui;

import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import no.uib.inf112.core.player.Card;

/**
 * @author Elg
 */
public class SlotSource extends DragAndDrop.Source {

    private final CardActor sourceSlot;

    public SlotSource(final CardActor actor) {
        super(actor);
        sourceSlot = actor;
    }

    @Override
    public Payload dragStart(final InputEvent event, final float x, final float y, final int pointer) {
        if (sourceSlot == null || sourceSlot.getCard() == null) {
            return null;
        }

        final Payload payload = new Payload();

        payload.setObject(sourceSlot);

        final TextureRegion icon = sourceSlot.getCard().getRegionTexture();

        final Actor dragActor = new Image(icon);
        payload.setDragActor(dragActor);

        final Actor validDragActor = new Image(icon);
        // validDragActor.setColor(0, 1, 0, 1);
        payload.setValidDragActor(validDragActor);

        final Actor invalidDragActor = new Image(icon);
        // invalidDragActor.setColor(1, 0, 0, 1);
        payload.setInvalidDragActor(invalidDragActor);

        return payload;
    }

    @Override
    public void dragStop(final InputEvent event, final float x, final float y, final int pointer, final Payload payload,
                         final DragAndDrop.Target target) {
        final CardActor payloadSlot = (CardActor) payload.getObject();
        if (target != null) {
            final CardActor targetSlot = (CardActor) target.getActor();

            final CardContainer cont = targetSlot.getContainer();

            if (targetSlot.getCard() == null) {
                cont.setCard(targetSlot, payloadSlot.getCard());
                cont.setCard(payloadSlot, null);
            } else {
                //swap the two items
                final Card payloadCard = cont.getCard(payloadSlot);

                payloadSlot.setCard(cont.getCard(targetSlot));
                targetSlot.setCard(payloadCard);
            }
        }
    }
}
