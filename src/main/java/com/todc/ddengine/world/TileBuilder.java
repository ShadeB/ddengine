package com.todc.ddengine.world;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class TileBuilder {


    // ----------------------------------------------------- Instance Variables


    private Tile tile = new Tile();


    // --------------------------------------------------------- Public Methods


    public static TileBuilder getInstance() {
        return new TileBuilder();
    }

    public TileBuilder name(String name) {
        tile.setName(name);
        return this;
    }

    public TileBuilder glyph(Glyph glyph) {
        tile.setGlyph(glyph);
        return this;
    }

    public TileBuilder visible(boolean visible) {
        tile.setVisible(visible);
        return this;
    }

    public TileBuilder passable(boolean passable) {
        tile.setPassable(passable);
        return this;
    }

    public TileBuilder opensTo(Tile tile) {
        tile.setOpensTo(tile);
        return this;
    }

    public TileBuilder closesTo(Tile tile) {
        tile.setClosesTo(tile);
        return this;
    }

    public Tile build() {
        return tile;
    }

}
