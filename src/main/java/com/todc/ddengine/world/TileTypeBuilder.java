package com.todc.ddengine.world;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class TileTypeBuilder {


    // ----------------------------------------------------- Instance Variables


    private TileType tile = new TileType();


    // --------------------------------------------------------- Public Methods


    public static TileTypeBuilder getInstance() {
        return new TileTypeBuilder();
    }

    public TileTypeBuilder name(String name) {
        tile.setName(name);
        return this;
    }

    public TileTypeBuilder glyph(Glyph glyph) {
        tile.setGlyph(glyph);
        return this;
    }

    public TileTypeBuilder blocksLight(boolean blocksLight) {
        tile.setBlocksLight(blocksLight);
        return this;
    }

    public TileTypeBuilder passable(boolean passable) {
        tile.setPassable(passable);
        return this;
    }

    public TileTypeBuilder opensTo(TileType tile) {
        tile.setOpensTo(tile);
        return this;
    }

    public TileTypeBuilder closesTo(TileType tile) {
        tile.setClosesTo(tile);
        return this;
    }

    public TileType build() {
        return tile;
    }

}
