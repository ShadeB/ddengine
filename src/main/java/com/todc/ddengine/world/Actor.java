package com.todc.ddengine.world;


import com.todc.ddengine.util.Coordinate;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Actor {


    // ----------------------------------------------------- Instance Variables


    private String name;
    private Glyph glyph;
    private Coordinate position = new Coordinate(2, 2);


    // --------------------------------------------------------- Public Methods


    public Glyph getGlyph() {
        return glyph;
    }

    public void setGlyph(Glyph glyph) {
        this.glyph = glyph;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        Coordinate prevPosition = this.position;

        this.position = position;
    }
}
