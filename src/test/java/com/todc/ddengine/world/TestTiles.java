package com.todc.ddengine.world;


import com.todc.ddengine.data.Tiles;
import org.junit.Test;

import java.awt.Color;

import static org.junit.Assert.*;

/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class TestTiles {

    @Test
    public void testGetFloorTileByName() {
        TileType tile = Tiles.FLOOR_TILE;
        assertEquals("Floor", tile.getName());
        assertEquals(".", tile.getGlyph().getCharacter());
        assertEquals(Color.WHITE, tile.getGlyph().getForeground());
        assertEquals(Color.BLACK, tile.getGlyph().getBackground());
    }

    @Test
    public void testGetWallTileByName() {
        TileType tile = Tiles.WALL_TILE;
        assertEquals("Wall", tile.getName());
        assertEquals("#", tile.getGlyph().getCharacter());
        assertEquals(Color.WHITE, tile.getGlyph().getForeground());
        assertEquals(Color.BLACK, tile.getGlyph().getBackground());
    }

    @Test
    public void testGetOpenedDoorTileByName() {
        TileType tile = Tiles.OPENED_DOOR_TILE;
        assertEquals("OpenedDoor", tile.getName());
        assertEquals("'", tile.getGlyph().getCharacter());
        assertEquals(Color.decode("#a06e3c"), tile.getGlyph().getForeground());
        assertEquals(Color.BLACK, tile.getGlyph().getBackground());
    }

    @Test
    public void testGetClosedDoorTileByName() {
        TileType tile = Tiles.CLOSED_DOOR_TILE;
        assertEquals("ClosedDoor", tile.getName());
        assertEquals("+", tile.getGlyph().getCharacter());
        assertEquals(Color.decode("#a06e3c"), tile.getGlyph().getForeground());
        assertEquals(Color.BLACK, tile.getGlyph().getBackground());
    }

}
