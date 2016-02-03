package com.todc.ddengine.world;


import com.todc.ddengine.data.Tiles;
import org.junit.Test;

import java.awt.Color;
import java.io.IOException;
import java.io.InputStream;

import static org.junit.Assert.*;

/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class TestTiles {

    @Test
    public void testGetFloorTileByName() {
        Tile tile = Tiles.FLOOR_TILE;
        assertEquals("Floor", tile.getName());
        assertEquals(".", tile.getGlyph().getCharacter());
        assertEquals(Color.WHITE, tile.getGlyph().getForeground());
        assertEquals(Color.BLACK, tile.getGlyph().getBackground());
    }

    @Test
    public void testGetWallTileByName() {
        Tile tile = Tiles.WALL_TILE;
        assertEquals("Wall", tile.getName());
        assertEquals("#", tile.getGlyph().getCharacter());
        assertEquals(Color.WHITE, tile.getGlyph().getForeground());
        assertEquals(Color.BLACK, tile.getGlyph().getBackground());
    }

    @Test
    public void testGetOpenedDoorTileByName() {
        Tile tile = Tiles.OPENED_DOOR_TILE;
        assertEquals("OpenedDoor", tile.getName());
        assertEquals("'", tile.getGlyph().getCharacter());
        assertEquals(Color.decode("#a06e3c"), tile.getGlyph().getForeground());
        assertEquals(Color.BLACK, tile.getGlyph().getBackground());
    }

    @Test
    public void testGetClosedDoorTileByName() {
        Tile tile = Tiles.CLOSED_DOOR_TILE;
        assertEquals("ClosedDoor", tile.getName());
        assertEquals("+", tile.getGlyph().getCharacter());
        assertEquals(Color.decode("#a06e3c"), tile.getGlyph().getForeground());
        assertEquals(Color.BLACK, tile.getGlyph().getBackground());
    }

}
