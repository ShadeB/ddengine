package com.todc.ddengine.world.dungeon;

import com.todc.ddengine.world.Tile;

/**
 * @author Tim O'Donnell (tim.odonnell@imperva.com)
 */
public interface DungeonGenerator {

    Tile[][] generate(int x, int y);

}
