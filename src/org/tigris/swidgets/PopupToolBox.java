// $Id$

/*
 * PopupToolBox.java
 *
 * Created on 23 February 2003, 09:59
 */

package org.tigris.swidgets;

import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Iterator;
import javax.swing.Action;
import javax.swing.JButton;

/**
 *
 * @author  Bob Tarling
 */
public class PopupToolBox extends Toolbox {

    private ArrayList actions = new ArrayList();
    private MouseListener mouseListener;

    /** 
     * Creates a new instance of PopupToolBox.
     * 
     * @param rows the number of rows
     * @param cols the number of columns
     */
    public PopupToolBox(int rows, int cols) {
        super(rows, cols);
    }

    /**
     * @see javax.swing.JToolBar#add(javax.swing.Action)
     */
    public JButton add(Action action) {
        JButton button = super.add(action);
        actions.add(action);
        
        return button;
    }

    /**
     * @param theMouseListener the new mouse listener
     */
    public void setButtonMouseListener(MouseListener theMouseListener) {
        mouseListener = theMouseListener;
    }

    /**
     * Occasionally the ToolBox gets in a state where a button
     * shows rollover status at the wrong time.
     * The only way to get around this is to rebuild the ToolBox.
     */
    public void rebuild() {
        super.removeAll();
        Iterator it = actions.iterator();
        while (it.hasNext()) {
            Action a = (Action) it.next();
            JButton button = super.add(a);
            if (mouseListener != null) {
                button.addMouseListener(mouseListener);
            }
        }
    }
}
