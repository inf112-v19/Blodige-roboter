package no.uib.inf112.core.map.tile;

import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

import static org.junit.Assert.fail;

public class TileGraphicTest {

    @Test
    public void idsOnlyAppearOnce() {
        TileGraphic[] values = TileGraphic.values();
        Set<Integer> known = new HashSet<>();

        for (TileGraphic value : values) {
            if (known.contains(value.getId())) {
                fail("id " + value.getId() + " found twice");
            } else {
                known.add(value.getId());
            }
        }

    }
}
