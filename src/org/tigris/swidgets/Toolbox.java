// $Id$

/*
 * Toolbox.java
 *
 * Created on 23 February 2003, 09:59
 */

package org.tigris.swidgets;

import java.awt.GridLayout;

/**
 * A toolbar where buttons are shown in a grid instead of a row.
 * @author  Bob Tarling
 */
public class Toolbox extends Toolbar {

    private int rows;
    private int cols;

    /** Creates a new instance of ToolBox
     * @param r the number of rows to display in the toolbox
     * @param c the number of columns to display in the toolbox
     */
    public Toolbox(int r, int c) {
        super();
        rows = r;
        cols = c;
        setLayout(new GridLayout(rows, cols));
    }
}
