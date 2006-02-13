/*
 * MultipleSplitPane.java
 */
package org.tigris.swidgets;

import javax.swing.*;
import java.awt.*;

/**
 * Similar to the standard java class <code>JSplitPane</code> but
 * allows the number of panes to be set in the constructor.
 *
 * @author Bob Tarling
 */
public class MultipleSplitPane extends JComponent {
    public static final Orientation HORIZONTAL_SPLIT = Horizontal.getInstance();
    public static final Orientation VERTICAL_SPLIT = Vertical.getInstance();

    private Splitter[] splitterArray;

    /**
     * The constructor. 
     * 
     * @param componentArray the panes to be seperated by the splitter
     */
    public MultipleSplitPane(Component componentArray[]) {
        this(componentArray.length);
    }

    /**
     * The constructor.
     * 
     * @param componentArray the panes to be seperated by the splitter
     * @param orientation the orientation
     */
    public MultipleSplitPane(Component componentArray[],
			     Orientation orientation) {
        this(componentArray.length, orientation);
    }

    /**
     * The constructor.
     * 
     * @param paneCount the number of panels to be split
     */
    public MultipleSplitPane(int paneCount) {
        this(paneCount, HORIZONTAL_SPLIT);
    }

    /**
     * @param paneCount the number of panes
     * @param orientation the orientation
     */
    public MultipleSplitPane(int paneCount, Orientation orientation) {
        this.setLayout(new SplitterLayout(orientation));
        int splitterCount = paneCount - 1;
        if (splitterCount >= 0) {
            splitterArray = new Splitter[paneCount - 1];
            for (int i = 0; i < splitterCount; ++i) {
                splitterArray[i] = new Splitter(orientation);
                add(splitterArray[i]);
            }
        }
        if (splitterCount > 1) {
            splitterArray[0].setQuickHide(Splitter.WEST);
            splitterArray[splitterCount - 1].setQuickHide(Splitter.EAST);
        }
    }
    
    /**
     * @see java.awt.Container#add(java.awt.Component, int)
     */
    public Component add(Component comp, int index) {
        if (!(comp instanceof Splitter)) {
            SplitterLayout splitterLayout = (SplitterLayout) getLayout();
            if (index > 0) {
                index =
		    splitterLayout
		    .getComponentPosition(splitterArray[index - 1]) + 1;
            }
        }
        if (index < this.getComponentCount()
	    && !(this.getComponent(index) instanceof Splitter))
	{
            super.remove(index);
        }
        return super.add(comp, index);
    }

    /**
     * @see java.awt.Container#add(java.awt.Component, java.lang.Object, int)
     */
    public void add(Component comp, Object constraints, int index) {
        if (!(comp instanceof Splitter)) {
            SplitterLayout splitterLayout = (SplitterLayout) getLayout();
            if (index > 0) {
                index =
		    splitterLayout.
		    getComponentPosition(splitterArray[index - 1]) + 1;
            }
        }
        if (index < this.getComponentCount()
	    && !(this.getComponent(index) instanceof Splitter))
	{
            super.remove(index);
        }
        super.add(comp, constraints, index);
    }

    /**
     * @see java.awt.Container#remove(int)
     */
    public void remove(int index) {
        SplitterLayout splitterLayout = (SplitterLayout) getLayout();
        if (index >= splitterArray.length) {
            index =
		splitterLayout.getComponentPosition(splitterArray[index - 1])
		+ 1;
        }
        else {
            index =
		splitterLayout.getComponentPosition(splitterArray[index])
		- 1;
        }
        if (index >= 0
	    && !(this.getComponent(index) instanceof Splitter))
	    super.remove(index);
    }
    
    /**
     * @see java.awt.Container#remove(int)
     */
    public void remove(Component comp) {
        Component previous = null;
        int count = getComponents().length;
        for (int i=0; i < count; ++i) {
            Component component = getComponents()[i];
            if (component == comp) {
                super.remove(i);
                if (previous instanceof Splitter) {
                    super.remove(i-1);
                } else if (i + 1 < count && getComponents()[i] instanceof Splitter) {
                    super.remove(i);
                }
                return;
            }
            previous = component;
        }
    }
    
    
}
