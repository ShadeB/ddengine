package com.todc.ddengine.world.dungeon;


import com.todc.ddengine.data.Tiles;
import com.todc.ddengine.util.Coordinate;
import com.todc.ddengine.util.Direction;
import com.todc.ddengine.world.Stage;
import com.todc.ddengine.world.Tile;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Stack;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
/// The random dungeon generator.
///
/// Starting with a stage of solid walls, it works like so:
///
/// 1. Place a number of randomly sized and positioned rooms. If a room
///    overlaps an existing room, it is discarded. Any remaining rooms are
///    carved out.
/// 2. Any remaining solid areas are filled in with mazes. The maze generator
///    will grow and fill in even odd-shaped areas, but will not touch any
///    rooms.
/// 3. The result of the previous two steps is a series of unconnected rooms
///    and mazes. We walk the stage and find every tile that can be a
///    "connector". This is a solid tile that is adjacent to two unconnected
///    regions.
/// 4. We randomly choose connectors and open them or place a door there until
///    all of the unconnected regions have been joined. There is also a slight
///    chance to carve a connector between two already-joined regions, so that
///    the dungeon isn't single connected.
/// 5. The mazes will have a lot of dead ends. Finally, we remove those by
///    repeatedly filling in any open tile that's closed on three sides. When
///    this is done, every corridor in a maze actually leads somewhere.
///
/// The end result of this is a multiply-connected dungeon with rooms and lots
/// of winding corridors.
public class DungeonGenerator {

    private int numRoomTries = 60;

    private Tile WALL_TILE = Tiles.getTileByName(Tiles.WALL_NAME);
    private Tile FLOOR_TILE = Tiles.getTileByName(Tiles.FLOOR_NAME);

    /// The inverse chance of adding a connector between two regions that have
    /// already been joined. Increasing this leads to more redundantly connected
    /// dungeons.
    private int extraConnectorChance = 20;

    /// Increasing this allows rooms to be larger.
    private int roomExtraSize = 0;

    private int windingPercent = 0;

    private List<Rectangle> _rooms = new ArrayList<>();

    /// For each open position in the dungeon, the index of the connected region
    /// that that position is a part of.
    private int[][] _regions;

    /// The index of the current region being carved.
    private int _currentRegion = -1;

    private Tile[][] tiles;
    private int width;
    private int height;

    private Random rng = new Random();

    public Tile[][] generate(int width, int height) {
        //if (stage.width % 2 == 0 || stage.height % 2 == 0) {
        //    throw new ArgumentError("The stage must be odd-sized.");
        //}

        this.width = width;
        this.height = height;

        tiles = new Tile[height][width];

        fill(WALL_TILE);

        _regions = new int[height][width];

        _addRooms();

        // Fill in all of the empty space with mazes.
        for (int y=1; y<height; y+=2) {
            for (int x=1; x<width; x+=2) {
                Coordinate pos = new Coordinate(x, y);
                if (getTile(pos) != WALL_TILE) continue;
                _growMaze(pos);
            }
        }

        _connectRegions();
        _removeDeadEnds();

        _rooms.forEach(::onDecorateRoom);
    }

    private void fill(Tile fillTile) {
        for (int row=0; row<tiles.length; row++) {
            for (int col=0; col<tiles[row].length; col++) {
                tiles[row][col] = fillTile;
            }
        }
    }

    private Tile getTile(Coordinate coord) {
        return tiles[coord.y][coord.x];
    }

    /// Returns the distance between [first] Rect and [second]. This is minimum
    /// length that a corridor would have to be to go from one Rect to the other.
    /// If the two Rects are adjacent, returns zero. If they overlap, returns -1.
    private int distanceBetween(Rectangle first, Rectangle second) {
        int vertical;
        if (first.y >= (int)second.getMaxY()) {
            vertical = first.y - (int)second.getMaxY();
        } else if ((int)first.getMaxY() <= second.y) {
            vertical = second.y - (int)first.getMaxY();
        } else {
            vertical = -1;
        }

        int horizontal;
        if (first.x >= (int)second.getMaxX()) {
            horizontal = first.x - (int)second.getMaxX();
        } else if ((int)first.getMaxX() <= second.x) {
            horizontal = second.x - (int)first.getMaxX();
        } else {
            horizontal = -1;
        }

        if ((vertical == -1) && (horizontal == -1)) return -1;
        if (vertical == -1) return horizontal;
        if (horizontal == -1) return vertical;
        return horizontal + vertical;
    }

    /// Implementation of the "growing tree" algorithm from here:
    /// http://www.astrolog.org/labyrnth/algrithm.htm.
    void _growMaze(Coordinate start) {
        //List<Coordinate> cells = new ArrayList<Coordinate>();
        Stack<Coordinate> cells = new Stack<>();
        Direction lastDir;

        _startRegion();
        _carve(start);

        cells.push(start);
        while (!cells.isEmpty()) {
            Coordinate cell = cells.get(cells.size()-1);

            // See which adjacent cells are open.
            List<Direction> unmadeCells = new ArrayList<>();

            for (Direction dir : Direction.CARDINAL) {
                if (_canCarve(cell, dir)) {
                    unmadeCells.add(dir);
                }
            }

            if (unmadeCells.isNotEmpty) {
                // Based on how "windy" passages are, try to prefer carving in the
                // same direction.
                var dir;
                if (unmadeCells.contains(lastDir) && rng.range(100) > windingPercent) {
                    dir = lastDir;
                } else {
                    dir = rng.item(unmadeCells);
                }

                _carve(cell + dir);
                _carve(cell + dir * 2);

                cells.add(cell + dir * 2);
                lastDir = dir;
            } else {
                // No adjacent uncarved cells.
                cells.pop();

                // This path has ended.
                lastDir = null;
            }
        }
    }

    /// Places rooms ignoring the existing maze corridors.
    void _addRooms() {
        for (int i=0; i<numRoomTries; i++) {
            // Pick a random room size. The funny math here does two things:
            // - It makes sure rooms are odd-sized to line up with maze.
            // - It avoids creating rooms that are too rectangular: too tall and
            //   narrow or too wide and flat.
            // TODO: This isn't very flexible or tunable. Do something better here.
            int size = (rng.nextInt(3 + roomExtraSize)+1) * 2 + 1;
            int rectangularity = rng.nextInt(1 + size / 2) * 2;
            int width = size;
            int height = size;
            if (rng.nextInt(2) > 0) {
                width += rectangularity;
            } else {
                height += rectangularity;
            }

            int x = rng.nextInt((this.width - width) / 2) * 2 + 1;
            int y = rng.nextInt((this.height - height) / 2) * 2 + 1;

            Rectangle room = new Rectangle(x, y, width, height);

            boolean overlaps = false;
            for (Rectangle other : _rooms) {
                if (distanceBetween(room, other) <= 0) {
                    overlaps = true;
                    break;
                }
            }

            if (overlaps) continue;

            _rooms.add(room);

            _startRegion();
            for (int row=room.y; row<=room.height; row++) {
                for (int col=room.x; col<=room.width; col++) {
                    _carve(new Coordinate(col, row));
                }
            }
        }
    }

    void _connectRegions() {
        // Find all of the tiles that can connect two (or more) regions.
        var connectorRegions = <Vec, Set<int>>{};
        for (var pos in bounds.inflate(-1)) {
            // Can't already be part of a region.
            if (getTile(pos) != Tiles.wall) continue;

            var regions = new Set<int>();
            for (var dir in Direction.CARDINAL) {
                var region = _regions[pos + dir];
                if (region != null) regions.add(region);
            }

            if (regions.length < 2) continue;

            connectorRegions[pos] = regions;
        }

        var connectors = connectorRegions.keys.toList();

        // Keep track of which regions have been merged. This maps an original
        // region index to the one it has been merged to.
        var merged = {};
        var openRegions = new Set<int>();
        for (var i = 0; i <= _currentRegion; i++) {
            merged[i] = i;
            openRegions.add(i);
        }

        // Keep connecting regions until we're down to one.
        while (openRegions.length > 1) {
            var connector = rng.item(connectors);

            // Carve the connection.
            _addJunction(connector);

            // Merge the connected regions. We'll pick one region (arbitrarily) and
            // map all of the other regions to its index.
            var regions = connectorRegions[connector]
                    .map((region) => merged[region]);
            var dest = regions.first;
            var sources = regions.skip(1).toList();

            // Merge all of the affected regions. We have to look at *all* of the
            // regions because other regions may have previously been merged with
            // some of the ones we're merging now.
            for (var i = 0; i <= _currentRegion; i++) {
                if (sources.contains(merged[i])) {
                    merged[i] = dest;
                }
            }

            // The sources are no longer in use.
            openRegions.removeAll(sources);

            // Remove any connectors that aren't needed anymore.
            connectors.removeWhere((pos) {
                    // Don't allow connectors right next to each other.
            if (connector - pos < 2) return true;

            // If the connector no long spans different regions, we don't need it.
            var regions = connectorRegions[pos].map((region) => merged[region])
            .toSet();

            if (regions.length > 1) return false;

            // This connecter isn't needed, but connect it occasionally so that the
            // dungeon isn't singly-connected.
            if (rng.oneIn(extraConnectorChance)) _addJunction(pos);

            return true;
            });
        }
    }

    void _addJunction(Vec pos) {
        if (rng.oneIn(4)) {
            setTile(pos, rng.oneIn(3) ? Tiles.openDoor : Tiles.floor);
        } else {
            setTile(pos, Tiles.closedDoor);
        }
    }

    void _removeDeadEnds() {
        var done = false;

        while (!done) {
            done = true;

            for (var pos in bounds.inflate(-1)) {
                if (getTile(pos) == Tiles.wall) continue;

                // If it only has one exit, it's a dead end.
                var exits = 0;
                for (var dir in Direction.CARDINAL) {
                    if (getTile(pos + dir) != Tiles.wall) exits++;
                }

                if (exits != 1) continue;

                done = false;
                setTile(pos, Tiles.wall);
            }
        }
    }

    /// Gets whether or not an opening can be carved from the given starting
    /// [Cell] at [pos] to the adjacent Cell facing [direction]. Returns `true`
    /// if the starting Cell is in bounds and the destination Cell is filled
    /// (or out of bounds).</returns>
    private boolean _canCarve(Coordinate pos, Direction direction) {
        // Must end in bounds.
        if (!bounds.contains(pos + direction * 3)) return false;

        // Destination must not be open.
        return getTile(pos + direction * 2) == Tiles.wall;
    }

    void _startRegion() {
        _currentRegion++;
    }

    void _carve(Coordinate pos) {
        this.tiles[pos.y][pos.x] = FLOOR_TILE;
        _regions[pos.y][pos.x] = _currentRegion;
    }
}
