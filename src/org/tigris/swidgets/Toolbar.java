/*
 * Toolbar.java
 *
 * Created on 29 September 2002, 21:01
 */

package org.tigris.swidgets;

import java.awt.Color;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * A toolbar class which assumes rollover effects and automatically
 * gives tooltip to any buttons created by adding an action.
 *
 * @author  Bob Tarling
 */
public class Toolbar extends JToolBar implements MouseListener {
    
    private static final Color SELECTEDBACK = new Color(153, 153, 153);
    private static final Color BUTTONBACK = new Color(204, 204, 204);
    private static Color normalBack;

    private boolean rollover;
    
    /** Creates a new instance of Toolbar
     */
    public Toolbar() {
        super();
        this.setFloatable(false);
        this.setRollover(true);
        this.setMargin(new Insets(0, 0, 0, 0));
    }
    
    /** Creates a new instance of Toolbar
     * @param title The title to display in the titlebar when toolbar
     * is floating
     */
    public Toolbar(String title) {
        this(title, true);
    }

    /** Creates a new instance of Toolbar
     * @param title The title to display in the titlebar when toolbar
     * is floating
     * @param floatable true if the toolbar can be dragged to a
     * floating position
     */
    public Toolbar(String title, boolean floatable) {
        super();
        this.setName(title);
        this.setFloatable(floatable);
        this.setRollover(true);
        this.setMargin(new Insets(0, 0, 0, 0));
    }
    
    /** Creates a new instance of Toolbar with the given orientation
     * @param orientation HORIZONTAL or VERTICAL
     */
    public Toolbar(int orientation) {
        super(orientation);
        this.setFloatable(false);
        this.setRollover(true);
        this.setMargin(new Insets(0, 0, 0, 0));
    }

    /**
     * @see javax.swing.JToolBar#setRollover(boolean)
     */
    public void setRollover(boolean r) {
        // TODO: Check for JDK1.4 before calling super class setRollover
        //super.setRollover(rollover);
        this.rollover = r;
        // TODO: Check for JDK1.4 before using
        //Boolean.valueOf(rollover)
        //this.putClientProperty("JToolBar.isRollover",
        //Boolean.valueOf(rollover));
        Boolean showRollover = Boolean.FALSE;
        if (r) showRollover = Boolean.TRUE;
        this.putClientProperty("JToolBar.isRollover",  showRollover);
    }
    
    /**
     * @see javax.swing.JToolBar#add(javax.swing.Action)
     */
    public JButton add(Action action) {
        JButton button;

        if (action instanceof ButtonAction) {
            button = new JButton(action);
            String tooltip = button.getToolTipText();
            if (tooltip == null || tooltip.trim().length() == 0) {
                tooltip = button.getText();
            }
            button = super.add(action);
            button.setToolTipText(tooltip);
        } else {
            button = super.add(action);
        }
        button.setFocusPainted(false);
        button.addMouseListener(this);

        return button;
    }
    
    ////////////////////////////////////////////////////////////////
    // MouseListener implementation

    /**
     * @see java.awt.event.MouseListener#mouseEntered(java.awt.event.MouseEvent)
     */
    public void mouseEntered(MouseEvent me) { }
    
    /**
     * @see java.awt.event.MouseListener#mouseExited(java.awt.event.MouseEvent)
     */
    public void mouseExited(MouseEvent me) { }
    
    /**
     * @see java.awt.event.MouseListener#mousePressed(java.awt.event.MouseEvent)
     */
    public void mousePressed(MouseEvent me) { }
    
    /**
     * @see java.awt.event.MouseListener#mouseReleased(java.awt.event.MouseEvent)
     */
    public void mouseReleased(MouseEvent me) { }
    
    /**
     * @see java.awt.event.MouseListener#mouseClicked(java.awt.event.MouseEvent)
     */
    public void mouseClicked(MouseEvent me) {
        Object src = me.getSource();
        if (src instanceof JButton
	    && ((JButton) src).getAction() instanceof ButtonAction)
	{
            JButton button = (JButton) src;
            ButtonAction action = (ButtonAction) button.getAction();
            if (action.isModal()) {
                Color currentBack = button.getBackground();
                if (currentBack.equals(SELECTEDBACK)) {
                    button.setBackground(normalBack);
                    button.setRolloverEnabled(rollover);
                } else {
                    button.setBackground(SELECTEDBACK);
                    button.setRolloverEnabled(false);
                    normalBack = currentBack;
                }
                if (me.getClickCount() >= 2
		    && action.getLockMethod() == AbstractButtonAction.NONE) 
		{
                    ;// FIXME Here I need to lock the button in place.
                    // The button should stay in place until it is
                    // pressed again (when it is released but not
                    // acted on) or any other key in its group is
                    // pressed.
                }
                else {
		    // Fixme, the is the rest of an empty
		    // if (me.getClickCount() == 1) {
		    me.getClickCount();
		}
            }
        }
    }
}
