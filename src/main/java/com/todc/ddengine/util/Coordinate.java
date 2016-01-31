package com.todc.ddengine.util;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Coordinate {


    // ----------------------------------------------------- Instance Variables


    public final int x;
    public final int y;


    // ----------------------------------------------------------- Constructors


    public Coordinate(int x, int y) {
        this.x = x;
        this.y = y;
    }


    // --------------------------------------------------------------- Mutators


    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }


    // --------------------------------------------------------- Public Methods


    public String toString() {
        return String.valueOf(x) + "," + y;
    }
}
