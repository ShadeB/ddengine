package com.todc.ddengine.ui.terminal;

import javax.swing.*;
import java.awt.*;


/**
 * @author Tim O'Donnell (tim@timodonnell.com)
 */
public class Terminal extends JPanel {


    // ----------------------------------------------------- Instance Variables


    private Cell[][] cells;

    private Font font = new Font("Menlo", Font.BOLD, 14);
    private int fontHeight = -1;
    private int fontWidth = -1;
    private int fontAscent = -1;

    private int xInset = -1;
    private int yInset = -1;

    private int numRows;
    private int numCols;

    private TerminalKeyListener keyListener = new TerminalKeyListener();;


    // --------------------------------------------------------------- Mutators


    public int getNumCols() {
        return numCols;
    }

    public void setNumCols(int numCols) {
        this.numCols = numCols;
    }

    public int getNumRows() {
        return numRows;
    }

    public void setNumRows(int numRows) {
        this.numRows = numRows;
    }


    // ----------------------------------------------------------- Constructors


    public Terminal(int cols, int rows) {
        super();
        init();

        this.numCols = cols;
        this.numRows = rows;

        fillWith(Cell.EMPTY_CELL);
    }


    // --------------------------------------------------------- Public Methods


    public Integer getKeyPressed() {
        try {
            return keyListener.blockingKeyPressed();
        } catch (InterruptedException ex) {
            return 0x0f0000;
        }
    }

    public void setCell(int x, int y, String glyph, Color fg, Color bg) {
        this.cells[y][x] = new Cell(glyph, fg, bg);
    }

    public Cell getCell(int x, int y) {
        return this.cells[y][x];
    }

    public void fillWith(Cell cell) {
        cells = new Cell[numRows][numCols];
        for (int y=0; y<cells.length; y++) {
            for (int x=0; x<cells[y].length; x++) {
                cells[y][x] = cell;
            }
        }
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);

        System.out.println("Painting terminal");
        long startTime = System.currentTimeMillis();

        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D)g;

            g2.setFont(font);
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            calculateFontMetrics(g2);

            // draw the tiles
            for (int rowNum=0; rowNum<cells.length; rowNum++) {
                for (int colNum=0; colNum<cells[rowNum].length; colNum++) {
                    Cell currentCell = cells[rowNum][colNum];

                    int x = xInset + colNum * fontWidth;
                    int y = yInset + rowNum * fontHeight;

                    // paint background via fill since Graphics.setBackground() doesn't seem to work
                    g2.setPaint(currentCell.getBackground());
                    g2.fill(new Rectangle(x, y, fontWidth, fontHeight));

                    // paint the foreground
                    g2.setColor(currentCell.getForeground());
                    g2.drawString(currentCell.getGlyph(), x, y + fontAscent);

                    /*
                    // draw border
                    g2.setColor(Color.RED);
                    g2.drawLine(x, y, x+fontWidth-1, y); // top
                    g2.drawLine(x, y, x, y+fontHeight-1); // left
                    g2.drawLine(x, y + fontHeight-1, x+fontWidth-1, y+fontHeight-1); // bottom
                    g2.drawLine(x+fontWidth-1, y, x+fontWidth-1, y+fontHeight-1); // right
                    */
                }
            }
        }

        long endTime = System.currentTimeMillis();
        System.out.println("Painted in " + (endTime-startTime) + "ms");
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(200, 200);
    }

    public void refresh() {
        this.repaint();
    }


    // -------------------------------------------------------- Private Methods


    private void init() {
        this.setIgnoreRepaint(true);
        this.setOpaque(true);
        this.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        this.addKeyListener(keyListener);

        // The focus subsystem consumes focus traversal keys, such as Tab and Shift Tab by default.
        // See https://docs.oracle.com/javase/tutorial/uiswing/events/keylistener.html for more info.
        this.setFocusTraversalKeysEnabled(false);

        // get the top-left after any border/padding is applied
        xInset = getInsets().left;
        yInset = getInsets().top;
    }

    private void calculateFontMetrics(Graphics2D g) {
        if (fontHeight == -1 && fontWidth == -1) {
            FontMetrics metrics = g.getFontMetrics(this.font);
            fontHeight = metrics.getHeight();
            fontWidth = metrics.charWidth('W');
            fontAscent = metrics.getMaxAscent();

            System.out.println("Font height: " + fontHeight);
            System.out.println("Font width:  " + fontWidth);
        }
    }
}
