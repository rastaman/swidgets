// $Id$

/*
 * DecoratedIcon.java
 *
 * Created on 25 February 2003, 20:47
 */

package org.tigris.swidgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import javax.swing.ImageIcon;
import javax.swing.UIManager;

/**
 * The base class for adding simple decorations to existing icons.
 * This should be extended for each decoration style.
 * @author Bob Tarling
 */
public abstract class DecoratedIcon extends ImageIcon {
    
    /**
     * If the icon is for hoovering: <code>ROLLOVER</code>
     */
    public static final int ROLLOVER = 0;
    
    /**
     * If the icon is the normally shown one: <code>STANDARD</code>
     */
    public static final int STANDARD = 1;

    /**
     * This is the sprite buffer for the arrow image of the left button.
     */
    private int[][] imageBuffer;

    private int popupIconWidth = 11;
    private int popupIconHeight = 16;
    private int popupIconOffset = 5;

    private ImageIcon imageIcon;
    
    /** Construct a decorated icon made up of the given icon and decorated with
     * the icon defined in the descendant class.
     * @param theImageIcon The icon to decorate
     */        
    DecoratedIcon(ImageIcon theImageIcon) {
        imageIcon = theImageIcon;
    }
    
    /**
     * Initialise the icon.
     * @param buffer the buffer containing the icon definition (pixels)
     */
    protected void init(int[][] buffer) {
        imageBuffer = buffer;
        popupIconWidth = imageBuffer[0].length;
        popupIconHeight = imageBuffer.length;
        BufferedImage mergedImage =
	    new BufferedImage(imageIcon.getIconWidth()
			      + popupIconOffset + popupIconWidth,
			      imageIcon.getIconHeight(),
			      BufferedImage.TYPE_INT_ARGB);
        Graphics2D g2 = mergedImage.createGraphics();
        g2.drawImage(imageIcon.getImage(), null, null);
        setImage(mergedImage);
    }

    /** Paints the icon. The top-left corner of the icon is drawn at
     * the point (x, y) in the coordinate space of the graphics
     * context g. If this icon has no image observer, this method uses
     * the c component as the observer.
     *
     * @param c the component to be used as the observer if this icon
     * has no image observer
     * @param g the graphics context
     * @param x the X coordinate of the icon's top-left corner
     * @param y the Y coordinate of the icon's top-left corner
     */
    public void paintIcon(Component c, Graphics g, int x, int y) {
        super.paintIcon(c, g, x, y);

        int xOffset = x + imageIcon.getIconWidth() + popupIconOffset;
        // Initialize the color array
        Color[] colors = {
                c.getBackground(),
                UIManager.getColor("controlDkShadow"),
                UIManager.getColor("infoText"),
                UIManager.getColor("controlHighlight")};

        for (int i = 0; i < popupIconWidth; i++) {
            for (int j = 0; j < popupIconHeight; j++) {
                if (imageBuffer[j][i] != 0) {
                    g.setColor(colors[imageBuffer[j][i]]);
                    g.drawLine(xOffset + i, y + j, xOffset + i, y + j);
                }
            }
        }
    }
}
