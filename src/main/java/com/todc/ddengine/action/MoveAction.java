package com.todc.ddengine.action;


import com.todc.ddengine.world.Actor;
import com.todc.ddengine.util.Coordinate;
import com.todc.ddengine.util.Direction;
import com.todc.ddengine.world.Stage;
import com.todc.ddengine.world.Tile;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class MoveAction implements Action {


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
    public void perform() {
        Coordinate currentPosition = this.actor.getPosition();
        Coordinate destination = new Coordinate(
            currentPosition.x + this.direction.x,
            currentPosition.y + this.direction.y
        );

        // can actor move to the destination?
        if (destination.x > stage.getWidth()) {
            System.out.println("Can't move outside bounds of stage (max width=" + stage.getWidth() + ", requested = " + destination.x + ")");
            return;
        }
        if (destination.x < 0) {
            System.out.println("Can't move outside bounds of stage (requested x = " + destination.x + ")");
            return;
        }
        if (destination.y > stage.getHeight()) {
            System.out.println("Can't move outside bounds of stage (max height=" + stage.getHeight() + ", requested = " + destination.y + ")");
            return;
        }
        if (destination.y < 0) {
            System.out.println("Can't move outside bounds of stage (requested y = " + destination.y + ")");
            return;
        }

        Tile destTile = stage.getTileAt(destination.x, destination.y);

        if (!destTile.isPassable()) {
            System.out.println("Ran into a wall at " + destination.toString());
            return;
        }

        stage.moveActor(actor, destination);
    }
}
