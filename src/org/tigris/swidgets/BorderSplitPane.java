/*
 * BorderSplitPane.java
 */
package org.tigris.swidgets;

import javax.swing.JComponent;
import java.awt.*;

/**
 * A component designed to display its child components in a similar
 * fashion to a component implementing the standard java class
 * <code>BorderLayout</code> but with each child component seperated
 * by a positional splitterbar.<p>
 *
 * The compass points have been expanded from those available in
 * <code>BorderLayout</code> to the diaganal compass points,
 * NORTHWEST, NORTHEAST, SOUTHEAST, SOUTHWEST.
 *
 * @author Bob Tarling
 */
public class BorderSplitPane extends JComponent {
    
    private static final long serialVersionUID = 8342062546421254771L;
    /**
     * The north layout constraint (top of container).
     */
    public static final String NORTH = "North";
    /**
     * The south layout constraint (bottom of container).
     */
    public static final String SOUTH = "South";
    /**
     * The east layout constraint (right of container).
     */
    public static final String EAST = "East";
    /**
     * The west layout constraint (left of container).
     */
    public static final String WEST = "West";
    /**
     * The center layout constraint (middle of container).
     */
    public static final String CENTER = "Center";
    /**
     * The southwest layout constraint (bottom left of container).
     */
    public static final String SOUTHWEST = "SouthWest";
    /**
     * The southeast layout constraint (bottom right of container).
     */
    public static final String SOUTHEAST = "SouthEast";
    /**
     * The northwest layout constraint (top left of container).
     */
    public static final String NORTHWEST = "NorthWest";
    /**
     * The northeast layout constraint (top right of container).
     */
    public static final String NORTHEAST = "NorthEast";

    private MultipleSplitPane outerSplitPane;
    private MultipleSplitPane topSplitPane;
    private MultipleSplitPane middleSplitPane;
    private MultipleSplitPane bottomSplitPane;

    /** Construct a new BorderSplitPane
     */    
    public BorderSplitPane() {
        outerSplitPane =
	    new MultipleSplitPane(3, MultipleSplitPane.VERTICAL_SPLIT);
        setLayout(new BorderLayout());
        super.add(outerSplitPane, BorderLayout.CENTER);

        topSplitPane = new MultipleSplitPane(3);
        middleSplitPane = new MultipleSplitPane(3);
        bottomSplitPane = new MultipleSplitPane(3);

        outerSplitPane.add(middleSplitPane, "100", 1);
    }

    /** Add a new component to the center of the
     * BorderSplitPane
     *
     * @param comp The component to be added
     * @return the component argument
     */    
    public Component add(Component comp) {
        middleSplitPane.add(comp, "100", 1);
        return comp;
    }

     /** 
      * Add a component at the specified compass point or center.
      * The constraint can be one of the constants defined in BorderSPlitPane
      * being, NORTH, SOUTH, NORTHWEST, NORTHEAST, SOUTHWEST, SOTHEAST or
      * CENTER
      * @param comp The component to be added
      * @param constraints The position to place the component
      */    
    public void add(Component comp, Object constraints) {
        if (constraints == null || constraints.toString().equals(CENTER)) {
            add(comp);
        }
        else if (constraints.toString().equals(NORTH)) {
            if (comp instanceof Orientable) {
                ((Orientable) comp).setOrientation(Horizontal.getInstance());
            }
            topSplitPane.add(comp, "100", 1);
            if (topSplitPane.getParent() != outerSplitPane)
		outerSplitPane.add(topSplitPane, 0);
        }
        else if (constraints.toString().equals(NORTHWEST)) {
            if (comp instanceof Orientable) {
                ((Orientable) comp).setOrientation(Horizontal.getInstance());
            }
            topSplitPane.add(comp, 0);
            if (topSplitPane.getParent() != outerSplitPane)
		outerSplitPane.add(topSplitPane, 0);
        }
        else if (constraints.toString().equals(NORTHEAST)) {
            if (comp instanceof Orientable) {
                ((Orientable) comp).setOrientation(Horizontal.getInstance());
            }
            topSplitPane.add(comp, 2);
            if (topSplitPane.getParent() != outerSplitPane)
		outerSplitPane.add(topSplitPane, 0);
        }
        else if (constraints.toString().equals(SOUTH)) {
            if (comp instanceof Orientable) {
                ((Orientable) comp).setOrientation(Horizontal.getInstance());
            }
            bottomSplitPane.add(comp, "100", 1);
            if (bottomSplitPane.getParent() != outerSplitPane)
		outerSplitPane.add(bottomSplitPane, 2);
        }
        else if (constraints.toString().equals(SOUTHWEST)) {
            if (comp instanceof Orientable) {
                ((Orientable) comp).setOrientation(Horizontal.getInstance());
            }
            bottomSplitPane.add(comp, 0);
            if (bottomSplitPane.getParent() != outerSplitPane)
		outerSplitPane.add(bottomSplitPane, 2);
        }
        else if (constraints.toString().equals(SOUTHEAST)) {
            if (comp instanceof Orientable) {
                ((Orientable) comp).setOrientation(Horizontal.getInstance());
            }
            bottomSplitPane.add(comp, 2);
            if (bottomSplitPane.getParent() != outerSplitPane)
		outerSplitPane.add(bottomSplitPane, 2);
        }
        else if (constraints.toString().equals(WEST)) {
            if (comp instanceof Orientable) {
                ((Orientable) comp).setOrientation(Vertical.getInstance());
            }
            middleSplitPane.add(comp, 0);
        }
        else if (constraints.toString().equals(EAST)) {
            if (comp instanceof Orientable) {
                ((Orientable) comp).setOrientation(Vertical.getInstance());
            }
            middleSplitPane.add(comp, 2);
        }
        else {
            add(comp);
        }
    }
    
    public void remove(Component comp) {
        topSplitPane.remove(comp);
        middleSplitPane.remove(comp);
        bottomSplitPane.remove(comp);
    }

    

    /** 
     * This method is only implemented to satisfy the superclass declaration.
     * add(Component comp) should be used in preference
     * 
     * @param comp The component to be added
     * @param index The index value is ignored
     * @return the component argument
     */    
    public Component add(Component comp, int index) {
        return add(comp);
    }

    /**
     * This method is only implemented to satisfy the superclass declaration.
     * add(Component comp, Object constraints) should be used in preference
     * 
     * @param comp The component to be added
     * @param constraints The position to place the component
     * @param index The index value is ignored
     */    
    public void add(Component comp, Object constraints, int index) {
        add(comp, constraints);
    }
}
