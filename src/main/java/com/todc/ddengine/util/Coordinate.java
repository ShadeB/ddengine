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


    public Coordinate add(Direction dir) {
        return new Coordinate(this.x + dir.x, this.y + dir.y);
    }

    public Coordinate add(Coordinate pos) {
        return new Coordinate(this.x + pos.x, this.y + pos.y);
    }

    public Coordinate sub(Direction dir) {
        return new Coordinate(this.x - dir.x, this.y - dir.y);
    }

    public Coordinate sub(Coordinate pos) {
        return new Coordinate(this.x - pos.x, this.y - pos.y);
    }

    public int lengthSquared() {
        return (this.x * this.x) +
               (this.y * this.y);
    }

    public String toString() {
        return String.valueOf(x) + "," + y;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;

        if (obj instanceof Coordinate) {
            Coordinate coord = (Coordinate)obj;
            return coord.x == this.x && coord.y == this.y;
        }

        return false;
    }
}
