package com.todc.ddengine.data;


import com.todc.ddengine.util.Colors;
import com.todc.ddengine.world.Glyph;
import com.todc.ddengine.world.Tile;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Tiles {


    private static Map<String,Tile> tiles = new HashMap<>();


    public static boolean isLoaded() {
        return !tiles.isEmpty();
    }

    public static void load(String filename) throws IOException {
        load(Tiles.class.getClassLoader().getResourceAsStream(filename));
    }

    public static void load(InputStream in) throws IOException {
        Yaml yaml = new Yaml();
        List<Map<String,Object>> tileData = (List<Map<String,Object>>)yaml.load(in);

        for (Map<String,Object> def : tileData) {
            String name = (String)def.get("name");

            Tile tile = new Tile();
            tile.setName(name);
            tile.setPassable((Boolean)def.get("passable"));
            tile.setGlyph(new Glyph(
                (String)def.get("glyph"),
                Colors.fromHex((String)def.get("foreground")),
                Colors.fromHex((String)def.get("background"))
            ));

            tiles.put(name, tile);
        }
    }

    public static Tile getTileByGlyph(String glyph) {
        if (!Tiles.isLoaded()) return null;

        for (Map.Entry<String,Tile> tileEntry : tiles.entrySet()) {
            Tile tile = tileEntry.getValue();
            if (tile.getGlyph().getCharacter().equals(glyph)) {
                return tile;
            }
        }

        return null;
    }

}
