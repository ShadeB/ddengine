package com.todc.ddengine.world.fov;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public interface FieldOfViewCalculator {

    void compute(int x, int y, int rangeLimit);

}
