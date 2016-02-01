package com.todc.ddengine.util;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Direction {


    // -------------------------------------------------------------- Constants


    public static final Direction N  = new Direction( 0, -1);
    public static final Direction NE = new Direction(+1, -1);
    public static final Direction E  = new Direction(+1,  0);
    public static final Direction SE = new Direction(+1, +1);
    public static final Direction S  = new Direction( 0, +1);
    public static final Direction SW = new Direction(-1, +1);
    public static final Direction W  = new Direction(-1,  0);
    public static final Direction NW = new Direction(-1, -1);

    public static final Direction[] CARDINAL = new Direction[] { N, E, S, W };

    // ----------------------------------------------------- Instance Variables


    public final int x;
    public final int y;


    // ----------------------------------------------------------- Constructors


    public Direction(int x, int y) {
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
}
