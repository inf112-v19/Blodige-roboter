package no.uib.inf112.core.ui.actors.cards;

import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.ui.ImageTextButton;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.map.cards.Card;
import no.uib.inf112.core.ui.UIHandler;
import org.jetbrains.annotations.Nullable;

/**
 * @author Elg
 */
public class CardActor extends ImageTextButton {

    private Card card;
    private String offset = "\n\n\n\n\n\n"; // This is to push the text up (by default it is in the middle of the card)

    CardActor() {
        super("", createSkin());
    }

    private static ImageTextButtonStyle createSkin() {
        ImageTextButtonStyle style = new ImageTextButtonStyle();
        style.font = createCardFont();
        style.imageUp = new TextureRegionDrawable(UIHandler.CARDS_SLOT_TEXTURE);
        return style;
    }

    /**
     * Setup of the font for the cards.
     *
     * @return The perfect card font
     */
    private static BitmapFont createCardFont() {
        UIHandler.card_font_parameter.size = 20;

        // Centering text in the display window
        UIHandler.card_font_parameter.padRight = 5;
        UIHandler.card_font_parameter.padTop = 3;

        return UIHandler.card_font_generator.generateFont(UIHandler.card_font_parameter);
    }

    /**
     * Update the texture of the card
     */
    public void updateCard() {
        if (GameGraphics.HEADLESS) {
            return;
        }
        if (card == null) {
            setText("");
            getStyle().imageUp = new TextureRegionDrawable(UIHandler.CARDS_SLOT_TEXTURE); // Empty slot
        } else {
            setText(card.getPriority() + offset);
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
