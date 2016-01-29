package com.todc.ddengine.ui;

import javax.swing.*;
import java.awt.*;

/**
 * @author Tim O'Donnell (tim.odonnell@imperva.com)
 */
public class Terminal extends JPanel {

    private Font font = new Font("Menlo", Font.BOLD, 13);

    @Override
    public void paintComponent(Graphics g) {
        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D)g;

            g2.setBackground(Color.BLUE);

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(font);
            g2.setColor(Color.WHITE);
            g2.drawString("@", 50, 50);

            FontMetrics metrics = g2.getFontMetrics(this.font);
            int fontHeight = metrics.getHeight();
            int fontWidth = metrics.charWidth('W');

            System.out.println("Font height: " + fontHeight);
            System.out.println("Font width:  " + fontWidth);
        }
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(100, 100);
    }

    /*
    private int refreshedCount = 0;
    private int repaintedCount = 0;

    @Override
    protected void paintComponent(Graphics g) {
//        synchronized (this) {
//            // only repaint component when refresh() is called
//            if (refreshedCount == repaintedCount) {
//                System.out.println("Dropped an paintComponent update -- didn't come through refresh()");
//                return;
//            }
//            refreshedCount = repaintedCount;
//        }

        if (g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D)g;

            getTopLevelAncestor().setBackground(Color.BLACK);

            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setFont(font);
            g2.setColor(Color.WHITE);
            g2.drawString("@", 50, 50);

            FontMetrics metrics = g2.getFontMetrics(this.font);
            int fontHeight = metrics.getHeight();
            int fontWidth = metrics.charWidth('W');

            System.out.println("Font height: " + fontHeight);
            System.out.println("Font width:  " + fontWidth);
        }
    }

    public void refresh() {
        synchronized(this) {
            refreshedCount++;

            repaint();
        }
    }
    */
}
