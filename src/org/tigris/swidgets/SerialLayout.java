/*
 * SerialLayout.java
 */
package org.tigris.swidgets;

import java.awt.*;

/**
 * Lays out components in a single row or column starting from any
 * side and aligning components to eachother.<p>
 *
 * Components can be set to start draw from, LEFTTORIGHT, TOPTOBOTTOM,
 * RIGHTTOLEFT or BOTTOMTOTOP.<p>
 *
 * Components will line up with eachother by edge or follow a common
 * central line.<p>
 *
 * The gap to leave before the first component and the following gaps
 * between each component can be set.
 *
 * @author Bob Tarling
 */
public class SerialLayout extends LineLayout {
    
    public static final int LEFTTORIGHT = 10;
    public static final int TOPTOBOTTOM = 10;
    public static final int RIGHTTOLEFT = 11;
    public static final int BOTTOMTOTOP = 11;
		  		 
    public static final String NORTH = "North";
    public static final String SOUTH = "South";
    public static final String EAST = "East";
    public static final String WEST = "West";
    public static final String NORTHEAST = "NorthEast";
    public static final String NORTHWEST = "NorthWest";
    public static final String SOUTHEAST = "SouthEast";
    public static final String SOUTHWEST = "SouthWest";
		  		 
    public static final int LEFT = 20;
    public static final int RIGHT = 21;
    public static final int TOP = 20;
    public static final int BOTTOM = 21;
    public static final int CENTER = 22;
    public static final int FILL = 23;

    private String position = WEST;
    private int direction = LEFTTORIGHT;
    private int alignment = TOP;

    public SerialLayout() {
        this(Horizontal.getInstance(), WEST, LEFTTORIGHT, TOP);
    }
    public SerialLayout(Orientation orientation) {
        this(orientation, WEST, LEFTTORIGHT, TOP);
    }
    public SerialLayout(Orientation orientation, String position) {
        this(orientation, position, LEFTTORIGHT, TOP);
    }
    public SerialLayout(Orientation orientation, String position,
			int direction) {
        this(orientation, position, direction, TOP);
    }
    
    public SerialLayout(Orientation orientation, String position,
			int direction, int alignment) {
        super(orientation);
        this.position = position;
        this.direction = direction;
        this.alignment = alignment;
    }

    public SerialLayout(Orientation orientation, String position,
			int direction, int alignment, int gap) {
        super(orientation, gap);
        this.position = position;
        this.direction = direction;
        this.alignment = alignment;
    }

    public void layoutContainer(Container parent) {
        Insets insets = parent.getInsets();

        Point loc;
        int preferredBreadth =
	    _orientation.getBreadth(parent.getPreferredSize());
        if (direction == LEFTTORIGHT) {
            if (position.equals(EAST)) {
                loc =
		    new Point(parent.getWidth()
			      - (insets.right
				 + preferredLayoutSize(parent).width),
			      insets.top);
            } else {
                loc = new Point(insets.left, insets.top);
            }
            int nComps = parent.getComponentCount();
            for (int i = 0; i < nComps; i++) {
                Component comp = parent.getComponent(i);
                if (comp != null && comp.isVisible()) {
                    Dimension size = comp.getPreferredSize();
                    if (alignment == FILL) {
                        _orientation.setBreadth(size, preferredBreadth);
                    }
                    comp.setSize(size);
                    comp.setLocation(loc);
                    loc = _orientation.addToPosition(loc, comp);
                    loc = _orientation.addToPosition(loc, _gap);
                }
            }
        }
        else {
            int lastUsablePosition = _orientation.getLastUsablePosition(parent);
            int firstUsableOffset = _orientation.getFirstUsableOffset(parent);
            loc = _orientation.newPoint(lastUsablePosition, firstUsableOffset);

            int nComps = parent.getComponentCount();
            for (int i = 0; i < nComps; i++) {
                Component comp = parent.getComponent(i);
                if (comp != null && comp.isVisible()) {
                    loc = _orientation.subtractFromPosition(loc, comp);
                    Dimension size = comp.getPreferredSize();
                    if (alignment == FILL) {
                        _orientation.setBreadth(size, preferredBreadth);
                    }
                    comp.setSize(size);
                    comp.setLocation(loc);
                    loc = _orientation.subtractFromPosition(loc, _gap);
                }
            }
        }
    }
}
