package com.todc.ddengine.ui.terminal;


import java.awt.*;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Cell {


    // -------------------------------------------------------------- Constants


    public static final Cell EMPTY_CELL = new Cell(" ", Color.WHITE, Color.BLACK);


    // ----------------------------------------------------- Instance Variables


    private String glyph;
    private Color foreground;
    private Color background;
    private boolean dirty = true;


    // --------------------------------------------------------------- Mutators


    public Color getBackground() {
        return background;
    }

    public void setBackground(Color background) {
        this.background = background;
    }

    public Color getForeground() {
        return foreground;
    }

    public void setForeground(Color foreground) {
        this.foreground = foreground;
    }

    public String getGlyph() {
        return glyph;
    }

    public void setGlyph(String glyph) {
        this.glyph = glyph;
    }

    public boolean isDirty() {
        return dirty;
    }

    public void setDirty(boolean dirty) {
        this.dirty = dirty;
    }


    // ----------------------------------------------------------- Constructors


    public Cell() {
        this.glyph = " ";
        this.foreground = Color.BLACK;
        this.background = Color.WHITE;
    }

    public Cell(String glyph, Color fg, Color bg) {
        this.glyph = glyph;
        this.foreground = fg;
        this.background = bg;
    }
}
