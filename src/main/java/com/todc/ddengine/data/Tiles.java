package com.todc.ddengine.data;


import com.todc.ddengine.util.Colors;
import com.todc.ddengine.world.Glyph;
import com.todc.ddengine.world.Tile;
import com.todc.ddengine.world.TileBuilder;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Tiles {

    public static final Tile WALL_TILE;
    public static final Tile FLOOR_TILE;
    public static final Tile OPENED_DOOR_TILE;
    public static final Tile CLOSED_DOOR_TILE;

    static {
        WALL_TILE = TileBuilder.getInstance()
                .name("Wall")
                .glyph(new Glyph("#", Colors.fromHex("#ffffff"), Colors.fromHex("#000000")))
                .visible(true)
                .passable(false)
                .build();

        FLOOR_TILE = TileBuilder.getInstance()
                .name("Floor")
                .glyph(new Glyph(".", Colors.fromHex("#ffffff"), Colors.fromHex("#000000")))
                .visible(true)
                .passable(true)
                .build();

        OPENED_DOOR_TILE = TileBuilder.getInstance()
                .name("OpenedDoor")
                .glyph(new Glyph("'", Colors.fromHex("#a06e3c"), Colors.fromHex("#000000")))
                .visible(true)
                .passable(true)
                .build();

        CLOSED_DOOR_TILE = TileBuilder.getInstance()
                .name("ClosedDoor")
                .glyph(new Glyph("+", Colors.fromHex("#a06e3c"), Colors.fromHex("#000000")))
                .visible(true)
                .passable(true)
                .build();

        // need to set these after creation since it's a cyclical reference
        OPENED_DOOR_TILE.setClosesTo(CLOSED_DOOR_TILE);
        CLOSED_DOOR_TILE.setOpensTo(OPENED_DOOR_TILE);
    }

}
