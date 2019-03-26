package no.uib.inf112.core.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.player.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

import static com.badlogic.gdx.graphics.g2d.Batch.*;
import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell.*;

/**
 * Class implemented to be able to render entities with different color.
 *
 * @author Elg and Kristian
 */
public class ColorfulOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer {

    public static boolean PARTY;
    private final Random r = new Random();

    ColorfulOrthogonalTiledMapRenderer(@NotNull TiledMap map) {
        super(map);
    }

    @SuppressWarnings("Duplicates")
    @Override
    public void renderTileLayer(TiledMapTileLayer layer) {
        final Color batchColor = batch.getColor();
        final float color = Color.toFloatBits(batchColor.r, batchColor.g, batchColor.b, batchColor.a * layer.getOpacity());

        final int layerWidth = layer.getWidth();
        final int layerHeight = layer.getHeight();

        final float layerTileWidth = layer.getTileWidth() * unitScale;
        final float layerTileHeight = layer.getTileHeight() * unitScale;

        final float layerOffsetX = layer.getRenderOffsetX() * unitScale;
        // offset in tiled is y down, so we flip it
        final float layerOffsetY = -layer.getRenderOffsetY() * unitScale;

        final int col1 = Math.max(0, (int) ((viewBounds.x - layerOffsetX) / layerTileWidth));
        final int col2 = Math.min(layerWidth,
                (int) ((viewBounds.x + viewBounds.width + layerTileWidth - layerOffsetX) / layerTileWidth));

        final int row1 = Math.max(0, (int) ((viewBounds.y - layerOffsetY) / layerTileHeight));
        final int row2 = Math.min(layerHeight,
                (int) ((viewBounds.y + viewBounds.height + layerTileHeight - layerOffsetY) / layerTileHeight));

        float y = row2 * layerTileHeight + layerOffsetY;
        float xStart = col1 * layerTileWidth + layerOffsetX;

        final float[] vertices = this.vertices;
        float realColor = color;

        for (int row = row2; row >= row1; row--) {
            float x = xStart;
            for (int col = col1; col < col2; col++) {
                final TiledMapTileLayer.Cell cell = layer.getCell(col, row);
                if (cell == null) {
                    x += layerTileWidth;
                    continue;
                }
                final TiledMapTile tile = cell.getTile();

                if (tile != null) {
                    final boolean flipX = cell.getFlipHorizontally();
                    final boolean flipY = cell.getFlipVertically();
                    final int rotations = cell.getRotation();

                    TextureRegion region = tile.getTextureRegion();

                    final float x1 = x + tile.getOffsetX() * unitScale;
                    final float y1 = y + tile.getOffsetY() * unitScale;
                    final float x2 = x1 + region.getRegionWidth() * unitScale;
                    final float y2 = y1 + region.getRegionHeight() * unitScale;

                    float u1 = region.getU();
                    float v1 = region.getV2();
                    float u2 = region.getU2();
                    float v2 = region.getV();

                    TileType tt = TileType.fromTiledId(tile.getId());
                    if (tt.getGroup() == TileType.Group.ROBOT) {
                        Entity entity = GameGraphics.getRoboRally().getCurrentMap().getEntity(col, row);
                        if (entity != null) {
                            Color rc = entity.getColor();
                            realColor = Color.toFloatBits(rc.r, rc.g, rc.b, rc.a * layer.getOpacity());
                        }
                    } else if (PARTY) {
                        realColor = Color.toFloatBits(r.nextFloat(), r.nextFloat(), r.nextFloat(), layer.getOpacity());
                    }

                    vertices[X1] = x1;
                    vertices[Y1] = y1;
                    vertices[C1] = realColor;
                    vertices[U1] = u1;
                    vertices[V1] = v1;

                    vertices[X2] = x1;
                    vertices[Y2] = y2;
                    vertices[C2] = realColor;
                    vertices[U2] = u1;
                    vertices[V2] = v2;

                    vertices[X3] = x2;
                    vertices[Y3] = y2;
                    vertices[C3] = realColor;
                    vertices[U3] = u2;
                    vertices[V3] = v2;

                    vertices[X4] = x2;
                    vertices[Y4] = y1;
                    vertices[C4] = realColor;
                    vertices[U4] = u2;
                    vertices[V4] = v1;

                    if (flipX) {
                        flipVerticesValues(U1, U3);
                        flipVerticesValues(U2, U4);
                    }
                    if (flipY) {
                        flipVerticesValues(V1, V3);
                        flipVerticesValues(V2, V4);
                    }
                    if (rotations != 0) {
                        switch (rotations) {
                            case ROTATE_90: {
                                vertices[V1] = vertices[V2];
                                vertices[V3] = vertices[V4];

                                float tempU = vertices[U1];
                                vertices[U2] = vertices[U3];
                                vertices[U4] = tempU;
                                break;
                            }
                            case ROTATE_180: {
                                flipVerticesValues(U1, U3);
                                float tempU;
                                tempU = vertices[U2];
                                vertices[U2] = vertices[U4];
                                vertices[U4] = tempU;
                                flipVerticesValues(V1, V3);
                                float tempV;
                                tempV = vertices[V2];
                                vertices[V2] = vertices[V4];
                                vertices[V4] = tempV;
                                break;
                            }
                            case ROTATE_270: {
                                float tempV = vertices[V1];
                                vertices[V4] = vertices[V3];
                                vertices[V2] = tempV;

                                vertices[U1] = vertices[U4];
                                vertices[U3] = vertices[U2];
                                break;
                            }
                            default:
                                throw new IllegalStateException("Unknown rotation " + rotations);
                        }
                    }
                    batch.draw(region.getTexture(), vertices, 0, NUM_VERTICES);
                }
                x += layerTileWidth;
            }
            y -= layerTileHeight;
        }
    }


    /**
     * Exchanges values at first and second
     */
    private void flipVerticesValues(int firstIndex, int secondIndex) {
        float temp = vertices[firstIndex];
        vertices[firstIndex] = vertices[secondIndex];
        vertices[secondIndex] = temp;
    }
}