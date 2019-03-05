package no.uib.inf112.core.ui.actors.cards;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.ui.UIHandler;
import no.uib.inf112.desktop.Main;
import org.jetbrains.annotations.Nullable;

/**
 * @author Elg
 */
public class CardActor extends ImageTextButton {

    private Card card;

    CardActor() {
        super("", createSkin());
    }

    private static ImageTextButtonStyle createSkin() {
        ImageTextButtonStyle style = new ImageTextButtonStyle();
        style.font = new BitmapFont();
        style.imageUp = new TextureRegionDrawable(UIHandler.CARDS_TEXTURE);
        return style;
    }

    /**
     * Update the texture of the card
     */
    public void updateCard() {
        if (Main.HEADLESS) {
            return;
        }
        if (card == null) {
            setText("");
            getStyle().imageUp = new TextureRegionDrawable(UIHandler.CARDS_TEXTURE);
        } else {
            setText("pri " + card.getPriority() + "\n" + card.getAction().toString());
            getStyle().imageUp = new TextureRegionDrawable(card.getRegionTexture());
            getLabelCell().padLeft(-card.getRegionTexture().getRegionWidth()); //make sure the text is within the card
        }
    }

    @Nullable
    public Card getCard() {
        return card;
    }

    public void setCard(Card card) {
        this.card = card;
        updateCard();
    }

    @Override
    public String toString() {
        return "CardActor{" +
                "card=" + card +
                '}';
    }
}
