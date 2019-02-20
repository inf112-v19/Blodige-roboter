package no.uib.inf112.core.ui.cards;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import no.uib.inf112.core.ui.CardContainer;
import no.uib.inf112.core.ui.DisabledVisualizer;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public class CardSlot extends CardActor implements DisabledVisualizer {

    @NotNull
    private final CardContainer container;

    private int slotId;


    public CardSlot(int id, @NotNull CardContainer container, @NotNull DragAndDrop dad, boolean headless) {
        super(headless);
        this.container = container;
        slotId = id;

        dad.addSource(new SlotSource(this));
        dad.addTarget(new SlotTarget(this));
        addListener(new TooltipListener(this));
    }

    @Override
    public boolean isDisabled() {
        //noinspection ConstantConditions container must be checked to be not null as isDisabled is called in the super constructor
        return container != null && container.getPlayer().getHealth() <= slotId;
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        act();
    }
}
