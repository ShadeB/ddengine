package com.todc.ddengine.util;

import java.awt.*;
import java.util.Iterator;
import java.util.function.Consumer;

/**
 * @author Tim O'Donnell (tim.odonnell@imperva.com)
 */
public class Rect extends Rectangle implements Iterable<Coordinate> {


    // ----------------------------------------------------- Instance Variables


    public final int top;
    public final int right;
    public final int bottom;
    public final int left;


    // ----------------------------------------------------------- Constructors


    public Rect(Rect r) {
        this(r.x, r.y, r.width, r.height);
    }

    public Rect(int x, int y, int width, int height) {
        super(x, y, width, height);

        this.left = x;
        this.top = y;
        this.right = x + width;
        this.bottom = y + height;
    }


    // --------------------------------------------------------- Public Methods


    public int distanceTo(Rect other) {
        int vertical;
        if (top >= other.bottom) {
            vertical = top - other.bottom;
        } else if (bottom <= other.top) {
            vertical = other.top - bottom;
        } else {
            vertical = -1;
        }

        int horizontal;
        if (left >= other.right) {
            horizontal = left - other.right;
        } else if (right <= other.left) {
            horizontal = other.left - right;
        } else {
            horizontal = -1;
        }

        if ((vertical == -1) && (horizontal == -1)) return -1;
        if (vertical == -1) return horizontal;
        if (horizontal == -1) return vertical;
        return horizontal + vertical;
    }

    public Rect inflate(int distance) {
        return new Rect(
            x - distance,
            y - distance,
            width + (distance * 2),
            height + (distance * 2)
        );
    }

    @Override
    public Iterator<Coordinate> iterator() {
        return new RectIterator();
    }


    private class RectIterator implements Iterator<Coordinate> {


        // ----------------------------------------------------- Instance Variables


        private int x;
        private int y;


        // ----------------------------------------------------------- Constructors


        public RectIterator() {
            this.x = Rect.this.x - 1;
            this.y = Rect.this.y;
        }


        // --------------------------------------------------------- Public Methods


        @Override
        public boolean hasNext() {
            int nextY = y;
            int nextX = x+1;
            if (nextX >= Rect.this.right) {
                nextX = Rect.this.x;
                nextY++;
            }

            return nextY < Rect.this.bottom;
        }

        @Override
        public Coordinate next() {
            x++;
            if (x >= Rect.this.right) {
                x = Rect.this.x;
                y++;
            }

            return new Coordinate(x, y);
        }
    }
}
