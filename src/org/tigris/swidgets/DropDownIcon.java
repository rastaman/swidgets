// $Id$

/*
 * DropDownIcon.java
 *
 * Created on 25 February 2003, 22:12
 */

package org.tigris.swidgets;

import javax.swing.ImageIcon;

/**
 *
 * @author Bob Tarling
 */
public class DropDownIcon extends DecoratedIcon {

    private static final int[][] STANDARDBUFFER = {
	{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	{0, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0},
	{0, 0, 1, 1, 1, 1, 1, 1, 3, 3, 0},
	{0, 0, 0, 1, 1, 1, 1, 3, 3, 0, 0},
	{0, 0, 0, 0, 1, 1, 3, 3, 0, 0, 0},
	{0, 0, 0, 0, 0, 3, 3, 0, 0, 0, 0},
	{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
	{0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
    };


    /** Creates a new instance of DropDownIcon */
    DropDownIcon(ImageIcon imageIcon) {
        super(imageIcon);
        init(STANDARDBUFFER);
    }
}
