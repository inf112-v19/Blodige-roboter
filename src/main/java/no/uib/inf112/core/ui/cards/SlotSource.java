package no.uib.inf112.core.ui.cards;

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

    private final CardSlot sourceSlot;

    public SlotSource(final CardSlot actor) {
        super(actor);
        sourceSlot = actor;
    }

    @Override
    public Payload dragStart(final InputEvent event, final float x, final float y, final int pointer) {
        if (sourceSlot == null || sourceSlot.getCard() == null || !RoboRally.getUiHandler().canMoveCards() || sourceSlot.isDisabled()) {
            return null;
        }

//        System.out.println("Starting to drag card " + sourceSlot.getSlotId() + " from " + sourceSlot.getSlotType());

        RoboRally.getUiHandler().getDad().setDragActorPosition(sourceSlot.getCard().getRegionTexture().getRegionWidth() - x, -y);

        final CardActor dragActor = new CardActor(true);
        dragActor.setCard(sourceSlot.getCard());

        final Payload payload = new Payload();
        payload.setObject(sourceSlot);
        payload.setDragActor(dragActor);
        payload.setValidDragActor(dragActor);
        payload.setInvalidDragActor(dragActor);

        return payload;
    }

    @Override
    public void dragStop(final InputEvent event, final float x, final float y, final int pointer, final Payload payload,
                         final DragAndDrop.Target target) {
        final CardSlot payloadSlot = (CardSlot) payload.getObject();
        if (target != null) {
            final CardSlot targetSlot = (CardSlot) target.getActor();

            if (targetSlot.isDisabled()) {
                //do not allow dropping on disabled slots (both drawn and hand)
                return;
            }

//            System.out.println("Dropping card on card " + targetSlot.getSlotId() + " from " + targetSlot.getSlotType());

            if (targetSlot.getCard() == null) {
                //move the payload to the target slot
                targetSlot.setCard(payloadSlot.getCard());
                payloadSlot.setCard(null);
            } else {
                //swap the two items
                final Card payloadCard = payloadSlot.getCard();

                payloadSlot.setCard(targetSlot.getCard());
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
