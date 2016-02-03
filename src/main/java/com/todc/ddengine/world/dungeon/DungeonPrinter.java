package com.todc.ddengine.world.dungeon;


import com.todc.ddengine.world.Tile;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class DungeonPrinter {


    // --------------------------------------------------------- Public Methods


    public static String toString(Tile[][] tiles) {
        StringBuilder sb = new StringBuilder();
        for (Tile[] tile : tiles) {
            for (int x=0; x<tile.length; x++) {
                String c = tile[x] == null ? " " : tile[x].getGlyph().getCharacter();
                sb.append(c);
                if (x + 1 >= tile.length) {
                    sb.append("\n");
                }
            }
        }

        return sb.toString();
    }


    public static void main(String... args) throws Exception {
        HauberkDungeonGenerator generator = new HauberkDungeonGenerator();
        Tile[][] tiles = generator.generate(35, 35);

        System.out.println(DungeonPrinter.toString(tiles));
    }

}
