// $Id$

/*
 * DimensionUtilities.java
 *
 * Created on 15 July 2002, 22:08
 */
package org.tigris.swidgets;

import java.awt.*;

/**
 * A collection of utility methods for Dimensions.
 *
 * @author Eugenio Alvarez
 * @stereotype utility
 */
public class DimensionUtilities {
    
    /**
     * Create a new <code>Dimension</code> from an existing
     * <code>Dimension</code> with its width and height increased by
     * the width and height of another <code>Dimension</code>.
     *
     * @param original The <code>Dimension</code> to be added to.
     * @param add The <code>Dimension</code> whose length and
     * breadth are to be taken as the added values.
     * @return The resulting <code>Dimension</code>.
     */
    public static Dimension add(Dimension original, Dimension add) {
        return new Dimension((int) (original.getWidth() + add.getWidth()),
			     (int) (original.getHeight() + add.getHeight()));
    }

    /**
     * Create a new <code>Dimension</code> from an existing
     * <code>Dimension</code> with its width and height increased by
     * the width and height of an <code>Insets</code> object.
     *
     * @param original The <code>Dimension</code> to be added to.
     * @param add The <code>Insets</code> object whose width and
     * height are to be taken as the added values.
     * @return The resulting <code>Dimension</code>.
     */
    public static Dimension add(Dimension original, Insets add) {
        return new Dimension((int) original.getWidth() + add.right + add.left,
			     (int) original.getHeight() + add.top + add.bottom);
    }
}
