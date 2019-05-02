package no.uib.inf112.core.ui.actors.cards;

import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.ui.CardContainer;
import no.uib.inf112.core.ui.actors.DisabledVisualizer;
import org.jetbrains.annotations.NotNull;

/**
 * @author Elg
 */
public class CardSlot extends CardActor implements DisabledVisualizer {

    @NotNull
    private final SlotType type;
    @NotNull
    private final CardContainer container;

    private int slotId;


    public CardSlot(int id, @NotNull SlotType type, @NotNull CardContainer container, @NotNull DragAndDrop dad) {
        super();

        this.type = type;
        this.container = container;
        slotId = id;

        dad.addSource(new SlotSource(this));
        dad.addTarget(new SlotTarget(this));
        addListener(new TooltipListener(this));
    }

    @Override
    public boolean isDisabled() {
        //noinspection ConstantConditions container must be checked to be not null as isDisabled is called in the super constructor
        if (container == null) {
            return false;
        }
        return slotId > (IPlayer.MAX_DRAW_CARDS - container.getPlayer().getDamageTokens() - 1);
    }

    @Override
    public void act(float delta) {
        super.act(delta);
        act();
    }

    @Override
    public String toString() {
        return "CardSlot{" +
                "type=" + type +
                ", slotId=" + slotId + super.toString() +
                "} ";
    }

    /**
     * Method to copy the current card actor
     *
     * @return A copy of this card actor
     */
    public CardSlot copy() {
        CardSlot copy = new CardSlot(slotId, type, container, GameGraphics.getUiHandler().getDad());
        copy.setCard(getCard());
        copy.updateCard();
        return copy;
    }
}
