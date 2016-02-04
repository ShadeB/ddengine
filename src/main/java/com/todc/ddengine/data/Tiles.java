package com.todc.ddengine.data;


import com.todc.ddengine.util.Colors;
import com.todc.ddengine.world.Glyph;
import com.todc.ddengine.world.TileType;
import com.todc.ddengine.world.TileTypeBuilder;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Tiles {

    public static final TileType WALL_TILE;
    public static final TileType FLOOR_TILE;
    public static final TileType OPENED_DOOR_TILE;
    public static final TileType CLOSED_DOOR_TILE;

    static {
        WALL_TILE = TileTypeBuilder.getInstance()
                .name("Wall")
                .glyph(new Glyph("#", Colors.fromHex("#ffffff"), Colors.fromHex("#000000")))
                .blocksLight(true)
                .passable(false)
                .build();

        FLOOR_TILE = TileTypeBuilder.getInstance()
                .name("Floor")
                .glyph(new Glyph(".", Colors.fromHex("#ffffff"), Colors.fromHex("#000000")))
                .blocksLight(false)
                .passable(true)
                .build();

        OPENED_DOOR_TILE = TileTypeBuilder.getInstance()
                .name("OpenedDoor")
                .glyph(new Glyph("'", Colors.fromHex("#a06e3c"), Colors.fromHex("#000000")))
                .blocksLight(false)
                .passable(true)
                .build();

        CLOSED_DOOR_TILE = TileTypeBuilder.getInstance()
                .name("ClosedDoor")
                .glyph(new Glyph("+", Colors.fromHex("#a06e3c"), Colors.fromHex("#000000")))
                .blocksLight(true)
                .passable(true)
                .build();

        // need to set these after creation since it's a cyclical reference
        OPENED_DOOR_TILE.setClosesTo(CLOSED_DOOR_TILE);
        CLOSED_DOOR_TILE.setOpensTo(OPENED_DOOR_TILE);
    }

}
