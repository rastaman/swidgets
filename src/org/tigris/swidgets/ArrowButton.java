/*
 * ArrowButton.java
 */
package org.tigris.swidgets;

import javax.swing.border.Border;
import java.awt.*;

/**
 * A simple arrow button that can be created to point to
 * a compass point.
 *
 * @author Bob Tarling
 */
public class ArrowButton extends javax.swing.JButton {

    private static final long serialVersionUID = -8897576333357419116L;

    /** Construct an ArrowButton pointing in the given direction
     *
     * @param direction the direction the arrow will point, this being
     * one of the constants NORTH, SOUTH, EAST, WEST
     */    
    public ArrowButton(int direction, Border border) {
        this(direction);
        setBorder(border);
    }

    /** Construct an ArrowButton pointing in the given direction
     *
     * @param direction the direction the arrow will point, this being
     * one of the constants NORTH, SOUTH, EAST, WEST
     */    
    public ArrowButton(int direction) {
        super();
        ArrowIcon arrowIcon = new ArrowIcon(direction);
        setIcon(arrowIcon);
        super.setFocusPainted(false);
        setPreferredSize(new Dimension(arrowIcon.getIconWidth(),
				       arrowIcon.getIconHeight()));
        setMinimumSize(new Dimension(arrowIcon.getIconWidth(),
				     arrowIcon.getIconHeight()));
        setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
    }
    
    public Dimension getPreferredSize() {
        return new Dimension(getIcon().getIconWidth(),
			     getIcon().getIconHeight());
    }
}
