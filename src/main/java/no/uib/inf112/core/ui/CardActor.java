package no.uib.inf112.core.ui;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.DragAndDrop;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import no.uib.inf112.core.player.Card;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * @author Elg
 */
public class CardActor extends ImageTextButton {

    private final TextureRegion textureRegion;
    @NotNull
    private final CardContainer container;
    @NotNull
    private final SlotType slotType;

    private Card card;
    private int slotId;


    public CardActor(TextureRegion textureRegion, @NotNull DragAndDrop dad, @NotNull CardContainer container, @NotNull SlotType slotType, int id) {
        super("", createSkin(textureRegion));
        this.textureRegion = textureRegion;

        this.container = container;
        this.slotType = slotType;
        this.slotId = id;

        dad.addSource(new SlotSource(this));
        dad.addTarget(new SlotTarget(this));
    }

    private static ImageTextButtonStyle createSkin(TextureRegion textureRegion) {
        ImageTextButtonStyle style = new ImageTextButtonStyle();
        style.imageUp = new TextureRegionDrawable(textureRegion);
        style.font = new BitmapFont();
        return style;
    }

    public void updateCard() {
        if (card == null) {
            setText("");
        } else {
            setText("pri " + card.getPriority() + "\n" + card.getAction().toString());
            getLabelCell().padLeft(-textureRegion.getRegionWidth()); //make sure the tex is with in the card
        }
    }

    public int getSlotId() {
        return slotId;
    }

    @NotNull
    public SlotType getSlotType() {
        return slotType;
    }

    @Nullable
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
        updateCard();
    }

    public CardContainer getContainer() {
        return container;
    }
}
