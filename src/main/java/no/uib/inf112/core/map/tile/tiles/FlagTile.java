package no.uib.inf112.core.map.tile.tiles;

import no.uib.inf112.core.map.tile.TileGraphic;
import no.uib.inf112.core.map.tile.api.AbstractRequirementTile;
import no.uib.inf112.core.map.tile.api.ActionTile;
import no.uib.inf112.core.map.tile.api.Tile;
import no.uib.inf112.core.player.IPlayer;
import no.uib.inf112.core.ui.Sound;
import no.uib.inf112.core.util.Vector2Int;
import org.jetbrains.annotations.NotNull;

import java.util.Collections;
import java.util.List;

/**
 * @author Elg
 */
public class FlagTile extends AbstractRequirementTile implements ActionTile<IPlayer> {

    private final int flagNr;

    public FlagTile(@NotNull Vector2Int pos, @NotNull TileGraphic tg) {
        super(pos, tg);

        flagNr = Integer.valueOf(tg.name().replace("FLAG", ""));
    }

    @Override
    public boolean action(@NotNull IPlayer player) {
        if (player.canGetFlag(flagNr)) {
            player.registerFlagVisit();
            return player.getFlags() >= flagNr;
        }
        return false;
    }

    @NotNull
    @Override
    public Sound getActionSound() {
        return Sound.FLAG;
    }

    @Override
    public List<Class<? extends Tile>> requiredSuperClasses() {
        return Collections.singletonList(IPlayer.class);
    }
}
