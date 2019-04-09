package no.uib.inf112.core.ui.actors.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop.Payload;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.cards.Card;

/**
 * @author Elg
 */
public class SlotSource extends DragAndDrop.Source {

    private final CardSlot sourceSlot;

    SlotSource(final CardSlot actor) {
        super(actor);
        sourceSlot = actor;
    }

    @Override
    public Payload dragStart(final InputEvent event, final float x, final float y, final int pointer) {
        if (sourceSlot == null || sourceSlot.getCard() == null || !GameGraphics.getUiHandler().isDrawnCardsVisible() || sourceSlot.isDisabled()) {
            return null;
        }
        GameGraphics.getUiHandler().getDad().setDragActorPosition(sourceSlot.getCard().getRegionTexture().getRegionWidth() - x, -y);

//        final CardActor dragActor = new CardActor();
//        dragActor.setCard(sourceSlot.getCard());

        final CardSlot dragActor = sourceSlot.copy();
        //dragActor.setCard(sourceSlot.getCard());
        sourceSlot.setCard(null);
        sourceSlot.updateCard();


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
        final CardSlot payloadSlot = (CardSlot) payload.getDragActor();
        if (target != null) {
            final CardSlot targetSlot = (CardSlot) target.getActor();

            if (targetSlot.isDisabled()) {
                //do not allow dropping on disabled slots (both drawn and hand)
                CardSlot source = (CardSlot) payload.getObject();
                source.setCard(payloadSlot.getCard());
                return;
            }

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
            GameGraphics.getUiHandler().getStage().screenToStageCoordinates(tempCoords.set(Gdx.input.getX(), Gdx.input.getY()));
            InputEvent inputEvent = new InputEvent();
            inputEvent.setType(InputEvent.Type.enter);
            inputEvent.setPointer(-1);
            inputEvent.setStageX(tempCoords.x);
            inputEvent.setStageY(tempCoords.y);
            targetSlot.fire(inputEvent);
        }

        // If card is dropped outside a card slot we put it back to its original slot
        CardSlot source = (CardSlot) payload.getObject();
        source.setCard(payloadSlot.getCard());
    }
}
