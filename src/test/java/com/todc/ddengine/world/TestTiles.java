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
    public void testLoad() {
        InputStream is = this.getClass().getClassLoader().getResourceAsStream("tiles.yaml");
        try {
            Tiles.load(is);
            Tile tile = Tiles.getTileByGlyph(".");
            assertEquals("Floor", tile.getName());
            assertEquals(".", tile.getGlyph().getCharacter());
            assertEquals(Color.WHITE, tile.getGlyph().getForeground());
            assertEquals(Color.BLACK, tile.getGlyph().getBackground());
        } catch (IOException ex) {
            fail("Unexpected IOException: " + ex.getMessage());
        }
    }

    @Test
    public void testGetFloorTileByName() {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("tiles.yaml");
            Tiles.load(is);
        } catch (IOException ex) {
            fail("Unexpected IOException: " + ex.getMessage());
        }

        Tile tile = Tiles.getTileByName(Tiles.FLOOR_NAME);
        assertEquals("Floor", tile.getName());
        assertEquals(".", tile.getGlyph().getCharacter());
        assertEquals(Color.WHITE, tile.getGlyph().getForeground());
        assertEquals(Color.BLACK, tile.getGlyph().getBackground());
    }

    @Test
    public void testGetWallTileByName() {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("tiles.yaml");
            Tiles.load(is);
        } catch (IOException ex) {
            fail("Unexpected IOException: " + ex.getMessage());
        }

        Tile tile = Tiles.getTileByName(Tiles.WALL_NAME);
        assertEquals("Wall", tile.getName());
        assertEquals("#", tile.getGlyph().getCharacter());
        assertEquals(Color.WHITE, tile.getGlyph().getForeground());
        assertEquals(Color.BLACK, tile.getGlyph().getBackground());
    }

    @Test
    public void testGetOpenedDoorTileByName() {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("tiles.yaml");
            Tiles.load(is);
        } catch (IOException ex) {
            fail("Unexpected IOException: " + ex.getMessage());
        }

        Tile tile = Tiles.getTileByName(Tiles.OPEN_DOOR_NAME);
        assertEquals("OpenedDoor", tile.getName());
        assertEquals("'", tile.getGlyph().getCharacter());
        assertEquals(Color.decode("#a06e3c"), tile.getGlyph().getForeground());
        assertEquals(Color.BLACK, tile.getGlyph().getBackground());
    }

    @Test
    public void testGetClosedDoorTileByName() {
        try {
            InputStream is = this.getClass().getClassLoader().getResourceAsStream("tiles.yaml");
            Tiles.load(is);
        } catch (IOException ex) {
            fail("Unexpected IOException: " + ex.getMessage());
        }

        Tile tile = Tiles.getTileByName(Tiles.CLOSED_DOOR_NAME);
        assertEquals("ClosedDoor", tile.getName());
        assertEquals("+", tile.getGlyph().getCharacter());
        assertEquals(Color.decode("#a06e3c"), tile.getGlyph().getForeground());
        assertEquals(Color.BLACK, tile.getGlyph().getBackground());
    }

}
