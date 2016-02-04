package com.todc.ddengine.ui.world;


import com.todc.ddengine.world.Stage;
import com.todc.ddengine.world.StageBuilder;
import com.todc.ddengine.world.Tile;
import com.todc.ddengine.world.TileType;
import com.todc.ddengine.data.Tiles;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class TestStageBuilder {

    private static final TileType WALL = Tiles.WALL_TILE;
    private static final TileType FLOOR = Tiles.FLOOR_TILE;

    @Test
    public void testFromStringValid() {
        String s = "#####\n" +
                   "#...#\n" +
                   "#...#\n" +
                   "#####\n";

        Stage gameMap = StageBuilder.fromString(s);
        Tile[][] tiles = gameMap.getTiles();

        assertEquals(WALL, tiles[0][0].getType());
        assertEquals(WALL, tiles[0][1].getType());
        assertEquals(WALL, tiles[0][2].getType());
        assertEquals(WALL, tiles[0][3].getType());
        assertEquals(WALL, tiles[0][4].getType());

        assertEquals(WALL,  tiles[1][0].getType());
        assertEquals(FLOOR, tiles[1][1].getType());
        assertEquals(FLOOR, tiles[1][2].getType());
        assertEquals(FLOOR, tiles[1][3].getType());
        assertEquals(WALL,  tiles[1][4].getType());

        assertEquals(WALL,  tiles[2][0].getType());
        assertEquals(FLOOR, tiles[2][1].getType());
        assertEquals(FLOOR, tiles[2][2].getType());
        assertEquals(FLOOR, tiles[2][3].getType());
        assertEquals(WALL,  tiles[2][4].getType());

        assertEquals(WALL, tiles[3][0].getType());
        assertEquals(WALL, tiles[3][1].getType());
        assertEquals(WALL, tiles[3][2].getType());
        assertEquals(WALL, tiles[3][3].getType());
        assertEquals(WALL, tiles[3][4].getType());
    }

}
