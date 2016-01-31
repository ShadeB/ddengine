package com.todc.ddengine.world;


import com.todc.ddengine.util.Coordinate;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Actor {


    // ----------------------------------------------------- Instance Variables


    private Coordinate position = new Coordinate(2, 2);


    // --------------------------------------------------------- Public Methods


    public Coordinate getPosition() {
        return position;
    }

    public void setPosition(Coordinate position) {
        Coordinate prevPosition = this.position;

        this.position = position;
    }
}
