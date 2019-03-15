package no.uib.inf112.core.map;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import no.uib.inf112.core.GameGraphics;
import no.uib.inf112.core.player.Robot;

import static com.badlogic.gdx.graphics.g2d.Batch.*;
import static com.badlogic.gdx.maps.tiled.TiledMapTileLayer.Cell.*;

/**
 * Class implemented to be able to render robots with different color. Only line 73-79 is our own logic rest is the
 * same implementation from OrthogonalTiledMapRenderer
 *
 * @Author Elg and Kristian
 */
public class ColorfulOrthogonalTiledMapRenderer extends OrthogonalTiledMapRenderer {

    ColorfulOrthogonalTiledMapRenderer(TiledMap map) {
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
                    TextureRegion region = tile.getTextureRegion();

                    float x1 = x + tile.getOffsetX() * unitScale;
                    float y1 = y + tile.getOffsetY() * unitScale;
                    float x2 = x1 + region.getRegionWidth() * unitScale;
                    float y2 = y1 + region.getRegionHeight() * unitScale;

                    float color1 = color;
                    if (TileType.fromTiledId(tile.getId()).getGroup() == TileType.Group.ROBOT) {
                        Robot robot = (Robot) GameGraphics.getRoboRally().getCurrentMap().getEntity(col, row);
                        if (robot != null) {
                            Color c = robot.getColor();
                            color1 = Color.toFloatBits(c.r, c.g, c.b, c.a);
                        }
                    }

                    // Logic breaks for V getV2 is paired with y1 and getV is with y2
                    assignVerticeValues(x1, y1, region.getU(), region.getV2(), color1, X1, Y1, C1, U1, V1);
                    assignVerticeValues(x1, y2, region.getU(), region.getV(), color1, X2, Y2, C2, U2, V2);
                    assignVerticeValues(x2, y2, region.getU2(), region.getV(), color1, X3, Y3, C3, U3, V3);
                    assignVerticeValues(x2, y1, region.getU2(), region.getV2(), color1, X4, Y4, C4, U4, V4);

                    if (cell.getFlipHorizontally()) {
                        flipVerticeValues(U1, U3);
                        flipVerticeValues(U2, U4);
                    }
                    if (cell.getFlipVertically()) {
                        flipVerticeValues(V1, V3);
                        flipVerticeValues(V2, V4);
                    }

                    final int rotations = cell.getRotation();
                    if (rotations != 0) {
                        switch (rotations) {
                            case ROTATE_90: {
                                rotate90();
                                break;
                            }
                            case ROTATE_180: {
                                rotate180();
                                break;
                            }
                            case ROTATE_270: {
                                rotate270();
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
     * Changes the vertice values to rotate the tile 270 degrees
     */
    private void rotate270() {
        flipVerticeValues(V1, V3);

        vertices[U1] = vertices[U4];
        vertices[U3] = vertices[U2];
    }

    /**
     * Changes the vertice values to rotate the tile 180 degrees
     */
    private void rotate180() {
        flipVerticeValues(U1, U3);
        flipVerticeValues(U2, U4);
        flipVerticeValues(V1, V3);
        flipVerticeValues(V2, V4);
    }

    /**
     * Changes the vertice values to rotate the tile 90 degrees
     */
    private void rotate90() {
        vertices[V1] = vertices[V2];
        vertices[V3] = vertices[V4];
        flipVerticeValues(U1, U3);
    }

    /**
     * Exchanges values at first and second
     */
    private void flipVerticeValues(int firstIndex, int secondIndex) {
        float temp = vertices[firstIndex];
        vertices[firstIndex] = vertices[secondIndex];
        vertices[secondIndex] = temp;
    }

    /**
     * Assigns the values to the vertice
     *
     * @param xValue
     * @param yValue
     * @param uValue
     * @param vValue
     * @param value
     * @param xIndex
     * @param yIndex
     * @param colorIndex
     * @param uIndex
     * @param vIndex
     */
    private void assignVerticeValues(float xValue, float yValue, float uValue, float vValue,
                                     float value, int xIndex, int yIndex, int colorIndex, int uIndex, int vIndex) {
        vertices[xIndex] = xValue;
        vertices[yIndex] = yValue;
        vertices[colorIndex] = value;
        vertices[uIndex] = uValue;
        vertices[vIndex] = vValue;
    }
}
