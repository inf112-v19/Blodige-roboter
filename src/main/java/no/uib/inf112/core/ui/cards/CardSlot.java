package no.uib.inf112.core.ui.cards;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import no.uib.inf112.core.ui.CardContainer;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public class CardSlot extends CardActor {

    @NotNull
    private final CardContainer container;
    @NotNull
    private final SlotType slotType;

    private int slotId;


    public CardSlot(@NotNull DragAndDrop dad, @NotNull CardContainer container, @NotNull SlotType slotType, int id) {

        this.container = container;
        this.slotType = slotType;
        this.slotId = id;

        dad.addSource(new SlotSource(this));
        dad.addTarget(new SlotTarget(this));
        addListener(new TooltipListener(this));
    }


    public int getSlotId() {
        return slotId;
    }

    @NotNull
    public SlotType getSlotType() {
        return slotType;
    }

    @NotNull
    public CardContainer getContainer() {
        return container;
    }
}