package com.todc.ddengine.action;


import com.todc.ddengine.world.Actor;
import com.todc.ddengine.util.Coordinate;
import com.todc.ddengine.util.Direction;
import com.todc.ddengine.world.Stage;
import com.todc.ddengine.world.Tile;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class MoveAction extends Action {


    // ----------------------------------------------------- Instance Variables


    private Actor actor;
    private Stage stage;
    private Direction direction;


    // --------------------------------------------------------------- Mutators


    public Direction getDirection() {
        return direction;
    }

    public void setDirection(Direction direction) {
        this.direction = direction;
    }

    public Actor getActor() {
        return actor;
    }

    public void setActor(Actor actor) {
        this.actor = actor;
    }


    // ----------------------------------------------------------- Constructors


    public MoveAction(Stage stage, Actor actor, Direction dir) {
        this.stage = stage;
        this.actor = actor;
        this.direction = dir;
    }


    // --------------------------------------------------------- Public Methods


    @Override
    public ActionResult perform() {
        Coordinate currentPosition = this.actor.getPosition();
        Coordinate destination = new Coordinate(
            currentPosition.x + this.direction.x,
            currentPosition.y + this.direction.y
        );

        Tile destTile = stage.getTileAt(destination.x, destination.y);

        if (destTile.getType().opensTo() != null) {
            return alternate(new OpenDoorAction(stage, destination));
        }

        if (!destTile.isPassable()) {
            System.out.println("Ran into a wall at " + destination.toString());
            return FAILURE;
        }

        stage.moveActor(actor, destination);

        return SUCCESS;
    }
}
