package com.todc.ddengine.world.dungeon;


import com.sun.javaws.exceptions.InvalidArgumentException;
import com.todc.ddengine.data.Tiles;
import com.todc.ddengine.util.Coordinate;
import com.todc.ddengine.util.Direction;
import com.todc.ddengine.util.RNG;
import com.todc.ddengine.util.Rect;
import com.todc.ddengine.world.Stage;
import com.todc.ddengine.world.Tile;

import java.util.*;
import java.util.stream.Collectors;


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

    {
        if (!Tiles.isLoaded()) {
            try {
                Tiles.load("tiles.yaml");
            } catch (Exception ex) {}
        }
    }

    private Tile WALL_TILE = Tiles.getTileByName(Tiles.WALL_NAME);
    private Tile FLOOR_TILE = Tiles.getTileByName(Tiles.FLOOR_NAME);
    private Tile OPEN_DOOR_TILE = Tiles.getTileByName(Tiles.OPEN_DOOR_NAME);
    private Tile CLOSED_DOOR_TILE = Tiles.getTileByName(Tiles.CLOSED_DOOR_NAME);


    private int numRoomTries = 30;

    /// The inverse chance of adding a connector between two regions that have
    /// already been joined. Increasing this leads to more redundantly connected
    /// dungeons.
    private int extraConnectorChance = 20;

    /// Increasing this allows rooms to be larger.
    private int roomExtraSize = 0;

    private int windingPercent = 0;

    private List<Rect> _rooms = new ArrayList<>();

    /// For each open position in the dungeon, the index of the connected region
    /// that that position is a part of.
    private Map<Coordinate,Integer> _regions = new HashMap<>();

    /// The index of the current region being carved.
    private int _currentRegion = -1;

    private Tile[][] tiles;
    private int width;
    private int height;
    private Rect bounds;

    private RNG rng = new RNG();

    public Tile[][] generate(int width, int height) {
        if (width % 2 == 0 || height % 2 == 0) {
            throw new IllegalArgumentException("The stage must be odd-sized.");
        }

        this.width = width;
        this.height = height;
        this.bounds = new Rect(0, 0, width, height);

        tiles = new Tile[height][width];

        fill(WALL_TILE);

        System.out.println("\n---------- ADDING ROOMS ----------");
        _addRooms();

        printDungeon(this.tiles);

        // Fill in all of the empty space with mazes.
        System.out.println("\n---------- ADDING MAZES ----------");
        for (int y=1; y<height; y+=2) {
            for (int x=1; x<width; x+=2) {
                Coordinate pos = new Coordinate(x, y);
                if (getTile(pos) != WALL_TILE) continue;
                _growMaze(pos);
            }
        }

        printDungeon(this.tiles);

        _connectRegions();
        _removeDeadEnds();

        return tiles;
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

    private void setTile(Coordinate coord, Tile tile) {
        tiles[coord.y][coord.x] = tile;
    }

    /// Implementation of the "growing tree" algorithm from here:
    /// http://www.astrolog.org/labyrnth/algrithm.htm.
    void _growMaze(Coordinate start) {
        //List<Coordinate> cells = new ArrayList<Coordinate>();
        Stack<Coordinate> cells = new Stack<>();
        Direction lastDir = null;

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

            if (!unmadeCells.isEmpty()) {
                // Based on how "windy" passages are, try to prefer carving in the
                // same direction.
                Direction dir;
                if (unmadeCells.contains(lastDir) && rng.nextInt(100) > windingPercent) {
                    dir = lastDir;
                } else {
                    dir = (Direction)rng.item(unmadeCells);
                }

                _carve(cell.add(dir));
                _carve(new Coordinate(cell.x+dir.x*2, cell.y+dir.y*2));

                cells.add(new Coordinate(cell.x+dir.x*2, cell.y+dir.y*2));
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
            if (rng.oneIn(2)) {
                width += rectangularity;
            } else {
                height += rectangularity;
            }

            int x = rng.nextInt((this.width - width) / 2) * 2 + 1;
            int y = rng.nextInt((this.height - height) / 2) * 2 + 1;

            Rect room = new Rect(x, y, width, height);
            //System.out.println("#" + i + ") Trying room: x=" + room.x + ", y=" + room.y + ", w=" + room.width + ", h=" + room.height);

            boolean overlaps = false;
            for (Rect other : _rooms) {
                if (room.distanceTo(other) <= 0) {
                    overlaps = true;
                    break;
                }
            }

            if (overlaps) {
                //System.out.println("  Overlaps another room. Abandoning.");
                continue;
            }

            _rooms.add(room);

            //System.out.println("  Carving room...");
            _startRegion();
            List<Coordinate> coords = getCoordinatesInRect(room);
            coords.forEach(this::_carve);
        }
    }

    void _connectRegions() {
        // Find all of the tiles that can connect two (or more) regions.
        Map<Coordinate,Set<Integer>> connectorRegions = new HashMap<>();

        List<Coordinate> coordsInRect = getCoordinatesInRect(bounds.inflate(-1));
        for (Coordinate pos : coordsInRect) {
            // Can't already be part of a region.
            if (getTile(pos) != WALL_TILE) continue;

            Set<Integer> regions = new HashSet<>();
            for (Direction dir : Direction.CARDINAL) {
                Integer region = _regions.get(pos.add(dir));
                if (region != null) {
                    regions.add(region);
                }
            }

            if (regions.size() < 2) continue;

            connectorRegions.put(pos, regions);
        }

        Set<Coordinate> connectorsSet = connectorRegions.keySet();
        List<Coordinate> connectors = new ArrayList<>(connectorsSet);

        // Keep track of which regions have been merged. This maps an original
        // region index to the one it has been merged to.
        int[] merged = new int[_currentRegion+1];
        List<Integer> openRegions = new ArrayList<>();
        for (int i=0; i<=_currentRegion; i++) {
            merged[i] = i;
            openRegions.add(i);
        }

        // Keep connecting regions until we're down to one.
        while (openRegions.size() > 1) {
            Coordinate connector = (Coordinate)rng.item(connectors);

            // Carve the connection.
            _addJunction(connector);

            // Merge the connected regions. We'll pick one region (arbitrarily) and
            // map all of the other regions to its index.
            List<Integer> regions = connectorRegions.get(connector).stream().map(region -> merged[region]).collect(Collectors.toList());
            int dest = regions.get(0);

            List<Integer> sources = new ArrayList<>(regions);
            sources.remove(0);

            // Merge all of the affected regions. We have to look at *all* of the
            // regions because other regions may have previously been merged with
            // some of the ones we're merging now.
            for (int i=0; i<=_currentRegion; i++) {
                if (sources.contains(merged[i])) {
                    merged[i] = dest;
                }
            }

            // The sources are no longer in use.
            openRegions.removeAll(sources);

            // Remove any connectors that aren't needed anymore.
            connectors.removeIf(pos -> {
                // Don't allow connectors right next to each other.
                if (connector.sub(pos).lengthSquared() < 2) return true;

                // If the connector no longer spans different regions, we don't need it.
                //Integer[] mergedRegions = (Integer[])connectorRegions.get(pos).stream().map(region -> merged[region]).toArray();
                List<Integer> mergedRegions = connectorRegions.get(pos).stream().map(region -> merged[region]).collect(Collectors.toList());

                if (mergedRegions.size() > 1) return false;

                // This connecter isn't needed, but connect it occasionally so that the
                // dungeon isn't singly-connected.
                if (rng.oneIn(extraConnectorChance)) _addJunction(pos);

                return true;
            });
        }
    }

    void _addJunction(Coordinate pos) {
        if (rng.oneIn(4)) {
            this.tiles[pos.y][pos.x] = (rng.oneIn(3)) ? OPEN_DOOR_TILE : FLOOR_TILE;
        } else {
            this.tiles[pos.y][pos.x] = CLOSED_DOOR_TILE;
        }
    }

    void _removeDeadEnds() {
        boolean done = false;

        while (!done) {
            done = true;

            Rect shrunkBounds = bounds.inflate(-1);
            List<Coordinate> coords = getCoordinatesInRect(shrunkBounds);

            for (Coordinate pos : coords) {
                if (getTile(pos) == WALL_TILE) continue;

                // If it only has one exit, it's a dead end.
                int exits = 0;
                for (Direction dir : Direction.CARDINAL) {
                    if (getTile(pos.add(dir)) != WALL_TILE) exits++;
                }

                if (exits != 1) continue;

                done = false;
                setTile(pos, WALL_TILE);
            }
        }
    }

    private List<Coordinate> getCoordinatesInRect(Rect rect) {
        List<Coordinate> coords = new ArrayList<>();

        for (int row=rect.y; row<rect.bottom; row++) {
            for (int col=rect.x; col<rect.right; col++) {
                coords.add(new Coordinate(col, row));
            }
        }

        return coords;
    }

    /// Gets whether or not an opening can be carved from the given starting
    /// [Cell] at [pos] to the adjacent Cell facing [direction]. Returns `true`
    /// if the starting Cell is in bounds and the destination Cell is filled
    /// (or out of bounds).</returns>
    private boolean _canCarve(Coordinate pos, Direction direction) {
        // Must end in bounds.
        if (!bounds.contains(pos.x+direction.x*3, pos.y+direction.y*3)) return false;

        // Destination must not be open.
        return getTile(new Coordinate(pos.x+direction.x*2, pos.y+direction.y*2)) == WALL_TILE;
    }

    private void _startRegion() {
        _currentRegion++;
    }

    private void _carve(Coordinate pos) {
        this.tiles[pos.y][pos.x] = FLOOR_TILE;
        _regions.put(pos, _currentRegion);

        //System.out.println("\nSetting " + pos + " = FLOOR");
        //System.out.println("*************");
        //printDungeon(this.tiles);
    }


    public static void main(String... args) throws Exception {
        DungeonGenerator generator = new DungeonGenerator();
        Tile[][] tiles = generator.generate(35, 35);

        System.out.println("\n---------- FINISHED ----------");
        printDungeon(tiles);
    }


    private static void printDungeon(Tile[][] tiles) {
        for (int y=0; y<tiles.length; y++) {
            for (int x=0; x<tiles[y].length; x++) {
                String c = tiles[y][x] == null ? " " : tiles[y][x].getGlyph().getCharacter();
                System.out.print(c);
                if (x+1 >= tiles[y].length) {
                    System.out.print("\n");
                }
            }
        }
    }
}
