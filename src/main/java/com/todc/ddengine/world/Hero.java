package com.todc.ddengine.world;


import java.awt.Color;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Hero extends Actor {


    // ----------------------------------------------------- Instance Variables


    public Hero() {
        setGlyph(new Glyph("@", Color.WHITE, Color.BLACK));
    }

}
