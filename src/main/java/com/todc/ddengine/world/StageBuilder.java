package com.todc.ddengine.world;


import java.util.HashMap;
import java.util.Map;

import static com.todc.ddengine.data.Tiles.*;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class StageBuilder {


    // -------------------------------------------------------------- Constants


    public static final int MAP_MAX_COLS = 1000;
    public static final int MAP_MAX_ROWS = 1000;


    private static Map<String, TileType> tiles = new HashMap<String,TileType>() {{
        put(WALL_TILE.getGlyph().getCharacter(),        WALL_TILE);
        put(FLOOR_TILE.getGlyph().getCharacter(),       FLOOR_TILE);
        put(OPENED_DOOR_TILE.getGlyph().getCharacter(), OPENED_DOOR_TILE);
        put(CLOSED_DOOR_TILE.getGlyph().getCharacter(), CLOSED_DOOR_TILE);
    }};


    public static Stage fromString(String s) {
        if (s == null || s.length() == 0) {
            throw new IllegalArgumentException("No map data found in String");
        }

        String[] rows = s.split("\n");
        int numRows = rows.length;
        int numCols = rows[0].length();

        // validate size of map
        if (numRows > MAP_MAX_ROWS) {
            throw new IllegalArgumentException("Number of rows in map exceeds max of " + MAP_MAX_ROWS);
        }
        if (numCols > MAP_MAX_COLS) {
            throw new IllegalArgumentException("Number of columns in map exceeds max of " + MAP_MAX_COLS);
        }

        Tile[][] tiles = new Tile[numRows][numCols];

        for (int r=0; r<rows.length; r++) {
            for (int c=0; c<rows[r].length(); c++) {
                Character tileChar = rows[r].charAt(c);
                tiles[r][c] = fromChar(tileChar.toString());
            }
        }

        return new Stage(tiles);
    }


    public static Tile fromChar(String glyph) {
        return new Tile(tiles.get(glyph));
    }

}
