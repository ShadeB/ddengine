package com.todc.ddengine.world;


import java.awt.Color;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Glyph {


    // -------------------------------------------------------- Private Methods


    private String character;
    private Color foreground;
    private Color background;


    // --------------------------------------------------------------- Mutators


    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public String getCharacter() {
        return character;
    }

    public void setCharacter(String character) {
        this.character = character;
    }

    public Color getForeground() {
        return foreground;
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }


    // ----------------------------------------------------------- Constructors


    public Glyph(String character, Color fg, Color bg) {
        this.character = character;
        this.foreground = fg;
        this.background = bg;
    }

    public Glyph(String character, String fgHex, String bgHex) {
        this.character = character;
        this.foreground = Color.decode(fgHex);
        this.background = Color.decode(bgHex);
    }
}
