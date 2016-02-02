package com.todc.ddengine.action;

import com.todc.ddengine.util.Coordinate;
import com.todc.ddengine.world.Stage;
import com.todc.ddengine.world.Tile;

/**
 * @author Tim O'Donnell (tim.odonnell@imperva.com)
 */
public class OpenDoorAction extends Action {


    // ----------------------------------------------------- Instance Variables


    private Stage stage;
    private Coordinate closedDoorPos;


    // ----------------------------------------------------------- Constructors


    public OpenDoorAction(Stage stage, Coordinate closedDoorPos) {
        this.stage = stage;
        this.closedDoorPos = closedDoorPos;
    }


    // --------------------------------------------------------- Public Methods


    @Override
    public ActionResult perform() {
        Tile closedDoorTile = stage.getTileAt(closedDoorPos);
        stage.setTileAt(closedDoorPos, closedDoorTile.opensTo());

        stage.setDirty();

        return SUCCESS;
    }
}
