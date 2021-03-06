/*
 * PopupToolBoxButton.java
 *
 * Created on 15 February 2003, 20:27
 */

package org.tigris.swidgets;

import java.awt.Color;
import java.awt.Component;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;

import javax.swing.Action;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JPopupMenu;
import javax.swing.UIManager;

/** An extension of JButton to which alternative actions can be added.
 * The button to trigger these actions become available when a
 * dropdown icon is pressed on this button.
 * @author Bob Tarling
 */
public class PopupToolBoxButton extends JButton {

    private JButton button;
    private PopupToolBox popupToolBox;
    private DecoratedIcon standardIcon;
    private String tooltip;
    //private PopupToolBoxButton _this; // TODO: Never used. Remove?
    private boolean showSplitter;
    
    /** Creates a new instance of PopupToolboxButton
     * @param a The default action when pressing this button
     * @param rows The number of rows of buttons to display in the
     *             popup toolbox
     * @param cols The number of columns of buttons to display in the
     *             popup toolbox
     */
    public PopupToolBoxButton(Action a, int rows, int cols) {
        super(a);
        //_this = this;
        setAction(a);
        
        popupToolBox = new PopupToolBox(rows, cols);
        
        MyMouseListener myMouseListener = new MyMouseListener();
        addMouseMotionListener(myMouseListener);
        addMouseListener(myMouseListener);
    }

    /** Provide a new default action for this button
     * @param a The new default action
     */    
    public void setAction(Action a) {
        // Create an invisible button to contain the new action.
        // We can use this button to find out various info for the
        // current plaf (eg preferred button size) so we can emulate
        // whatever plaf the user is set to.
        button = new JButton(a);
        tooltip = button.getToolTipText();
        if (tooltip == null || tooltip.trim().length() == 0) {
            tooltip = button.getText();
        }
        button.setText(null);
        
        standardIcon = new DropDownIcon((ImageIcon) button.getIcon());
        
        // Remove any knowledge of the action to perform from the ancestor
        // we take control of performing the action and displaying the icon.
        super.setAction(null);
        setIcon(standardIcon);
        setToolTipText(tooltip);
    }
    
    private void popup() {
        final JPopupMenu popup = new JPopupMenu();
        
        MouseAdapter m = (new MouseAdapter() 
	    {
		public void mouseClicked(MouseEvent e) {
		    popup.setVisible(false);
		    Component c = e.getComponent();
		    if (c instanceof JButton) {
			Action a = ((JButton) c).getAction();
			setAction(a);
		    }
		}
	    });
        popupToolBox.setButtonMouseListener(m);
        
        popupToolBox.rebuild();
        popup.add(popupToolBox);
        popup.show(this, 0, getHeight());
    }

    /** Add a new action to appear as a button on the
     * popup toolbox
     * @param a The action to be added
     * @return The button generated to trigger the action
     */    
    public JButton add(Action a) {
        return popupToolBox.add(a);
    }
    
    private int getSplitterPosn() {
        return getIconPosn() + button.getIcon().getIconWidth() + 3;
    }
    
    /**
     * Get the xy position of the icon.
     * FIXME
     * For the moment this assumes that the button has the icon centered.
     */
    private int getIconPosn() {
        int x = (this.getWidth() - standardIcon.getIconWidth()) / 2;
        return x;
    }
    

    /**
     * @see java.awt.Component#paint(java.awt.Graphics)
     */
    public void paint(Graphics g) {
        super.paint(g);
        Color[] colors = {
	    getBackground(),
	    UIManager.getColor("controlDkShadow"),
	    UIManager.getColor("infoText"),
	    UIManager.getColor("controlHighlight")};

        if (showSplitter) {
            showSplitter(colors[1], g, getSplitterPosn(),     1,
			 getHeight() - 4);
            showSplitter(colors[3], g, getSplitterPosn() + 1, 1,
			 getHeight() - 4);
        }
    }
    
    /**
     * @param c the color
     * @param g the graphics
     * @param x the x-coordinate of the vertical line that 
     *          represents the splitter
     * @param y the y-coordinate of the bottom of the splitter line
     * @param height the height of the splitter line
     */
    public void showSplitter(Color c, Graphics g, int x, int y, int height) {
        g.setColor(c);
        g.drawLine(x, y + 0, x, y + height);
    }
    
    /**
     * @param show to show or not to show
     */
    public void showSplitter(boolean show) {
        if (show && !showSplitter) {
            showSplitter = true;
            repaint();
            setToolTipText("Select Tool");
        } else if (!show && showSplitter) {
            showSplitter = false;
            repaint();
            setToolTipText(tooltip);
        }
    }

    /**
     *  It would have been easier to put a mouse listener on the arrowlabel
     *  but this seems to introduce a swing bug and the main JButton
     *  loses its rollover border. So the mouselistener is more complex
     *  and needs to determine where oon the main button the mouse is.
     */
    private class MyMouseListener
	implements MouseMotionListener, MouseListener
    {

        public void mouseDragged(MouseEvent me) {
        }

        /**
         * If the mouse movement occurs within the PopupToolBoxButton.
         * If the mouse moves in and out of the area of the button
         * that has the dropdown then change the icon.
         */
        public void mouseMoved(MouseEvent me) {
            showSplitter(me.getX() >= getSplitterPosn());
        }

        /**
         * Empty method to satisy interface only, there is no special
         * action to take place when the mouse first enters the
         * PopupToolBoxButton area
         */
        public void mouseEntered(MouseEvent me) {
        }

        /**
         * Be double sure the dropdowns rollover divider is removed
         * when we leave the button.
         */
        public void mouseExited(MouseEvent me) {
            showSplitter(false);
            //setIcon(_standardIcon);
        }
        
        public void mouseClicked(MouseEvent me) {
            if (me.getX() >= getSplitterPosn()) {
                // If the dropdown area was clicked then do the
                // dropdown action instead of the current button
                // action
                popup();
            } else {
                // If clicked elsewhere do the current button action
                button.doClick();
            }
        }
        
        /**
         * Empty method to satisy interface only, there is no special
         * action to take place when the mouse is pressed on the
         * PopupToolBoxButton area
         */
        public void mousePressed(MouseEvent me) { }
        
        /**
         * Empty method to satisy interface only, there is no special
         * action to take place when the mouse is released on the
         * PopupToolBoxButton area
         */
        public void mouseReleased(MouseEvent me) { }
    }
}
