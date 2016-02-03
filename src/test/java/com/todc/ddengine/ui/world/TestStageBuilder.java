package com.todc.ddengine.ui.world;


import com.todc.ddengine.world.Stage;
import com.todc.ddengine.world.StageBuilder;
import com.todc.ddengine.world.Tile;
import com.todc.ddengine.data.Tiles;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;

import static org.junit.Assert.*;

/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class TestStageBuilder {

    private static final String WALL = Tiles.WALL_TILE.getName();
    private static final String FLOOR = Tiles.FLOOR_TILE.getName();

    @Test
    public void testFromStringValid() {
        String s = "#####\n" +
                   "#...#\n" +
                   "#...#\n" +
                   "#####\n";

        Stage gameMap = StageBuilder.fromString(s);
        Tile[][] tiles = gameMap.getTiles();

        assertEquals(WALL, tiles[0][0].getName());
        assertEquals(WALL, tiles[0][1].getName());
        assertEquals(WALL, tiles[0][2].getName());
        assertEquals(WALL, tiles[0][3].getName());
        assertEquals(WALL, tiles[0][4].getName());

        assertEquals(WALL,  tiles[1][0].getName());
        assertEquals(FLOOR, tiles[1][1].getName());
        assertEquals(FLOOR, tiles[1][2].getName());
        assertEquals(FLOOR, tiles[1][3].getName());
        assertEquals(WALL,  tiles[1][4].getName());

        assertEquals(WALL,  tiles[2][0].getName());
        assertEquals(FLOOR, tiles[2][1].getName());
        assertEquals(FLOOR, tiles[2][2].getName());
        assertEquals(FLOOR, tiles[2][3].getName());
        assertEquals(WALL,  tiles[2][4].getName());

        assertEquals(WALL, tiles[3][0].getName());
        assertEquals(WALL, tiles[3][1].getName());
        assertEquals(WALL, tiles[3][2].getName());
        assertEquals(WALL, tiles[3][3].getName());
        assertEquals(WALL, tiles[3][4].getName());
    }

}
