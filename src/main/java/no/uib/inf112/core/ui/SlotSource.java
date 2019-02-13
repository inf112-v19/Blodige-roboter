package no.uib.inf112.core.ui;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import no.uib.inf112.core.RoboRally;
import no.uib.inf112.core.player.Card;

/**
 * @author Elg
 */
public class SlotSource extends DragAndDrop.Source {

    private final CardActorSlot sourceSlot;

    public SlotSource(final CardActorSlot actor) {
        super(actor);
        sourceSlot = actor;
    }

    @Override
    public Payload dragStart(final InputEvent event, final float x, final float y, final int pointer) {
        if (sourceSlot == null || sourceSlot.getCard() == null || !RoboRally.getUiHandler().canMoveCards()) {
            return null;
        }

        final Payload payload = new Payload();

        payload.setObject(sourceSlot);

        final CardActor dragActor = new CardActor();
        dragActor.setCard(sourceSlot.getCard());

        payload.setDragActor(dragActor);
        payload.setValidDragActor(dragActor);
        payload.setInvalidDragActor(dragActor);

        return payload;
    }

    @Override
    public void dragStop(final InputEvent event, final float x, final float y, final int pointer, final Payload payload,
                         final DragAndDrop.Target target) {
        final CardActorSlot payloadSlot = (CardActorSlot) payload.getObject();
        if (target != null) {
            final CardActorSlot targetSlot = (CardActorSlot) target.getActor();

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

            //fire an enter event to display tooltip when dropping the card
            Vector2 tempCoords = new Vector2();
            RoboRally.getUiHandler().getStage().screenToStageCoordinates(tempCoords.set(Gdx.input.getX(), Gdx.input.getY()));
            InputEvent inputEvent = new InputEvent();
            inputEvent.setType(InputEvent.Type.enter);
            inputEvent.setPointer(-1);
            inputEvent.setStageX(tempCoords.x);
            inputEvent.setStageY(tempCoords.y);
            targetSlot.fire(inputEvent);
        }
    }
}
