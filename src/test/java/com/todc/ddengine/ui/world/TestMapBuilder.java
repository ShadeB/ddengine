package com.todc.ddengine.ui.world;


import com.todc.ddengine.world.Map;
import com.todc.ddengine.world.MapBuilder;
import com.todc.ddengine.world.Tile;
import org.junit.Test;

import static org.junit.Assert.*;
import static com.todc.ddengine.world.Terrain.*;

/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class TestMapBuilder {

    @Test
    public void testFromStringValid() {
        String s = "#####\n" +
                   "#...#\n" +
                   "#...#\n" +
                   "#####\n";

        Map gameMap = MapBuilder.fromString(s);
        Tile[][] tiles = gameMap.getTiles();

        assertEquals(WALL, tiles[0][0].getTerrainType());
        assertEquals(WALL, tiles[0][1].getTerrainType());
        assertEquals(WALL, tiles[0][2].getTerrainType());
        assertEquals(WALL, tiles[0][3].getTerrainType());
        assertEquals(WALL, tiles[0][4].getTerrainType());

        assertEquals(WALL,  tiles[1][0].getTerrainType());
        assertEquals(FLOOR, tiles[1][1].getTerrainType());
        assertEquals(FLOOR, tiles[1][2].getTerrainType());
        assertEquals(FLOOR, tiles[1][3].getTerrainType());
        assertEquals(WALL,  tiles[1][4].getTerrainType());

        assertEquals(WALL,  tiles[2][0].getTerrainType());
        assertEquals(FLOOR, tiles[2][1].getTerrainType());
        assertEquals(FLOOR, tiles[2][2].getTerrainType());
        assertEquals(FLOOR, tiles[2][3].getTerrainType());
        assertEquals(WALL,  tiles[2][4].getTerrainType());

        assertEquals(WALL, tiles[3][0].getTerrainType());
        assertEquals(WALL, tiles[3][1].getTerrainType());
        assertEquals(WALL, tiles[3][2].getTerrainType());
        assertEquals(WALL, tiles[3][3].getTerrainType());
        assertEquals(WALL, tiles[3][4].getTerrainType());
    }

}
