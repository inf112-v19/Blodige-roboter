package no.uib.inf112.core.ui.actors.cards;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
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
    private String offset = "\n\n\n\n\n\n";

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
        FreeTypeFontGenerator generator = new FreeTypeFontGenerator(Gdx.files.internal("font2.ttf"));
        FreeTypeFontGenerator.FreeTypeFontParameter parameter = new FreeTypeFontGenerator.FreeTypeFontParameter();
        parameter.size = 20;
        parameter.padRight = 5;
        parameter.padTop = 3;
        parameter.color = Color.valueOf("#f1c232");
        return generator.generateFont(parameter);
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

            // TODO fix this, this is ugly
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
