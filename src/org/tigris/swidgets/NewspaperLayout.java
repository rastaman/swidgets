// $Id$

package org.tigris.swidgets;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Insets;
import java.awt.Rectangle;

import javax.swing.JComponent;

/**
 * Similar to {@link GridLayout2} but once the components fill
 * the height of the container they flow into another grid on the
 * right until the full width of the container is filled. Once the
 * containers width is full it flows to the right no more, the grid
 * depths increase instead so that the user scrolls up/down instead of
 * left/right.
 *
 * @author Bob Tarling
 */
public class NewspaperLayout extends GridLayout2 {

    private int gridGap = 0;
    private int preferredX;
    private int preferredY;

    private int gridWidth;

    /**
     * The constructor.
     */
    public NewspaperLayout() {
        this(1, 0, 0, 0, 0);
    }

    /**
     * The constructor.
     * 
     * @param rows the number of rows
     * @param cols the number of columns
     */
    public NewspaperLayout(int rows, int cols) {
        this(rows, cols, 0, 0, 0);
    }

    /**
     * The constructor.
     * 
     * @param rows the number of rows
     * @param cols the number of columns
     * @param hgap the horizontal gap
     * @param vgap the vertical gap
     * @param gg the grid gap
     */
    public NewspaperLayout(int rows, int cols, int hgap, int vgap, int gg)
    {
        super(rows, cols, hgap, vgap, ROWCOLPREFERRED, NONE, NORTHWEST);
        this.gridGap = gg;
    }

    /**
     * @see java.awt.LayoutManager#addLayoutComponent(java.lang.String, 
     * java.awt.Component)
     */
    public void addLayoutComponent(String name, Component comp) {
    }

    /**
     * @see java.awt.LayoutManager#removeLayoutComponent(java.awt.Component)
     */
    public void removeLayoutComponent(Component comp) {
    }

    /**
     * @see java.awt.LayoutManager#preferredLayoutSize(java.awt.Container)
     */
    public Dimension preferredLayoutSize(Container parent) {
        JComponent comp = (JComponent) parent;
        Rectangle rect = comp.getVisibleRect();
        //preferredX = (int) rect.getWidth();
        Insets insets = parent.getInsets();
        layoutContainer(parent);
        if (preferredX < insets.right + gridWidth + insets.left)
	    preferredX = insets.right + gridWidth + insets.left;
        return new Dimension(preferredX, preferredY);
    }

    /**
     * @see java.awt.LayoutManager#minimumLayoutSize(java.awt.Container)
     */
    public Dimension minimumLayoutSize(Container parent) {
        Insets insets = parent.getInsets();
        return new Dimension(insets.right + gridWidth + insets.left, 0);
    }

    /**
     * TODO: This is never used, and not part of the interface LayoutContainer.
     * @param parent the container
     * @return the dimension
     */
    public Dimension maximumLayoutSize(Container parent) {
        return new Dimension(Integer.MAX_VALUE, Integer.MAX_VALUE);
    }

    /**
     * @see java.awt.LayoutManager#layoutContainer(java.awt.Container)
     */
    public void layoutContainer(Container parent) {
        synchronized (parent.getTreeLock()) {
            int ncomponents = parent.getComponentCount();
            if (ncomponents == 0) {
                return;
            }
            Insets insets = parent.getInsets();
            int nrows = this.getRows();
            int ncols = this.getColumns();

            if (nrows > 0) {
                ncols = (ncomponents + nrows - 1) / nrows;
            } else {
                nrows = (ncomponents + ncols - 1) / ncols;
            }

            // Determine the width for each column and the height for each row.
            setColWidth(new int[ncols]);
            setRowHeight(new int[nrows]);
            setLargestWidth(0);
            setLargestHeight(0);

            if (getCellSizing() == FITPARENT) {
                int availableWidth =
		    parent.getWidth()
		    - (insets.left + insets.right + (ncols - 1) * getHgap());
                int availableHeight =
		    parent.getHeight()
		    - (insets.top + insets.bottom + (nrows - 1) * getVgap());
                setLargestWidth(availableWidth / ncols);
                setLargestHeight(availableHeight / nrows);
            }
            else {

                for (int c = 0; c < ncols; ++c) {
                    for (int r = 0; r < nrows; ++r) {
                        int i = r * ncols + c;
                        if (parent.getComponent(i).getPreferredSize().getWidth()
                                > getColWidth()[c]) {
                            getColWidth()[c] = (int) parent.getComponent(i)
				    .getPreferredSize().getWidth();
                            if (getColWidth()[c] > getLargestWidth())
				setLargestWidth(getColWidth()[c]);
                        }
                        if ((parent.getComponent(i).getPreferredSize()
                                .getHeight()) > getRowHeight()[r]) {
                            getRowHeight()[r] = (int) parent.getComponent(i)
				    .getPreferredSize().getHeight();
                            if (getRowHeight()[r] > getLargestHeight()) {
                                setLargestHeight(getRowHeight()[r]);
			    }
                        }
                    }
                }
            }

            // Calculate width
            gridWidth = (ncols - 1) * getHgap();
            for (int c = 0; c < ncols; ++c) {
                gridWidth += getColWidth()[c];
            }

            // Calculate Height
            int gridHeight = (nrows - 1) * getVgap();
            for (int r = 0; r < nrows; ++r) {
                gridHeight += getRowHeight()[r];
            }

            int numberOfGrids =
		positionComponentsInternal(parent, getColWidth(), 
		    getRowHeight(), gridHeight, nrows, ncols);
            if (numberOfGrids > 0) {
                positionComponentsExternal(parent, getColWidth(), 
                    getRowHeight(), gridHeight, nrows, ncols, numberOfGrids);
            }
        }
    }

    private int positionComponentsInternal(Container parent,
					   int colWidth[], int rowHeight[],
					   int gridHeight, int nrows, int ncols)
    {
        JComponent parentComp = (JComponent) parent;
        int visibleHeight = (int) parentComp.getVisibleRect().getHeight();
        int visibleWidth = (int) parentComp.getVisibleRect().getWidth();
        int ncomponents = parent.getComponentCount();
        Insets insets = parent.getInsets();
        int newsColumn = 0;
        int highestY = 0;
        int y = insets.top;
        int cellHeight;
        int cellWidth;
        for (int r = 0; r < nrows; ++r) {

            cellHeight = getComponentCellHeight(r);

            if (y + cellHeight + insets.bottom > visibleHeight ) {
                y = insets.top;
                newsColumn++;
                if ((insets.left
		     + insets.right
		     + newsColumn * (gridWidth + gridGap)
		     + gridWidth)
		    > visibleWidth)
		    return newsColumn;
            }

            int x = insets.left + newsColumn * (gridWidth + gridGap);
            for (int c = 0; c < ncols; ++c) {
                cellWidth = getComponentCellWidth(c);

                int i = r * ncols + c;
                if (i < ncomponents) {
                    positionComponentInCell(parent.getComponent(i), x, y,
					    cellWidth, cellHeight);
                    if (y + cellHeight > highestY) highestY = y + cellHeight;
                }
                x += cellWidth + getHgap();
            }
            y += cellHeight + getVgap();
        }
        preferredY = highestY + insets.bottom;
        return -1;
    }


    private boolean positionComponentsExternal(Container parent,
					       int colWidth[], int rowHeight[],
					       int gridHeight,
					       int nrows, int ncols,
					       int maxGrids) {
        int ncomponents = parent.getComponentCount();
        Insets insets = parent.getInsets();
        int newsColumn = 0;
        int targetHeight = gridHeight / maxGrids;
        int highestY = 0;
        int y = insets.top;
        int componentCellHeight;
        int componentCellWidth;
        for (int r = 0; r < nrows; ++r) {
            if (getCellSizing() != ROWCOLPREFERRED)
		componentCellHeight = getLargestHeight();
            else componentCellHeight = rowHeight[r];

            int x = insets.left + newsColumn * (gridWidth + gridGap);
            for (int c = 0; c < ncols; ++c) {
                if (getCellSizing() != ROWCOLPREFERRED)
		    componentCellWidth = getLargestWidth();
                else componentCellWidth = colWidth[c];

                int i = r * ncols + c;
                if (i < ncomponents) {
                    positionComponentInCell(parent.getComponent(i),
					    x, y,
					    componentCellWidth,
					    componentCellHeight);
                    if (y + componentCellHeight > highestY)
			highestY = y + componentCellHeight;
                }
                x += componentCellWidth + getHgap();
            }
            y += componentCellHeight + getVgap();
            if (y >= targetHeight + insets.top) {
                y = insets.top;
                newsColumn++;
            }
        }
        preferredY = highestY + insets.bottom;

        return true;
    }
}

