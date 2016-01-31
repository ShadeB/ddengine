package com.todc.ddengine.world;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class StageBuilder {


    // -------------------------------------------------------------- Constants


    public static final int MAP_MAX_COLS = 1000;
    public static final int MAP_MAX_ROWS = 1000;

    public static final java.util.Map<Character,Integer> terrainMapping = new java.util.HashMap<Character,Integer>() {{
        put('#', Terrain.WALL);
        put('.', Terrain.FLOOR);
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
                tiles[r][c] = fromChar(tileChar);
            }
        }

        return new Stage(tiles);
    }


    public static Tile fromChar(Character c) {
        Integer terrainType = terrainMapping.get(c);
        return new Tile(terrainType);
    }

}
