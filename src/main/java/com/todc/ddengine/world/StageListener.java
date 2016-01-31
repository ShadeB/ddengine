package com.todc.ddengine.world;


import com.todc.ddengine.util.Coordinate;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public interface StageListener {

    void onStageChange(Actor actor, Coordinate oldPosition, Coordinate newPosition);

}
