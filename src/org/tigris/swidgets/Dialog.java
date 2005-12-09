// $Id$

/*
 * Dialog.java
 *
 * Created on 11 June 2003, 22:54
 */

package org.tigris.swidgets;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JPanel;

/**
 * Base class for all dialogs, setting borders and component spacing.
 *
 * @author Bob Tarling
 * @author Jeremy Jones
 */
public abstract class Dialog extends JDialog implements ActionListener {
    
    // The set of available optionTypes
    /** Option type: CLOSE_OPTION */
    public static final int CLOSE_OPTION              = 0;
    /** Option type: YES_NO_OPTION */
    public static final int YES_NO_OPTION             = 1;
    /** Option type: YES_NO_HELP_OPTION */
    public static final int YES_NO_HELP_OPTION        = 2;
    /** Option type: YES_NO_CANCEL_OPTION */
    public static final int YES_NO_CANCEL_OPTION      = 3;
    /** Option type: YES_NO_CANCEL_HELP_OPTION */
    public static final int YES_NO_CANCEL_HELP_OPTION = 4;
    /** Option type: OK_CANCEL_OPTION */
    public static final int OK_CANCEL_OPTION             = 5;
    /** Option type: OK_CANCEL_HELP_OPTION */
    public static final int OK_CANCEL_HELP_OPTION     = 6;
    /** Option type: DEFAULT_OPTION */
    public static final int DEFAULT_OPTION            = CLOSE_OPTION;
    
    //TODO: These should be overridden on ArgoDialog to populate from
    //the config file
    private int leftBorder = 10;
    private int rightBorder = 10;
    private int topBorder = 10;
    private int bottomBorder = 10;
    /** The gap between components. */
    private int componentGap = 10;
    /** The gap between labels. */
    private int labelGap = 5;
    private int buttonGap = 5;
    
    private JButton okButton = null;
    private JButton cancelButton = null;
    private JButton closeButton = null;
    private JButton yesButton = null;
    private JButton noButton = null;
    private JButton helpButton = null;
    
    private JPanel mainPanel;
    private JComponent content;
    private JPanel buttonPanel;
    
    private int optionType;

    /**
     * Creates a new Dialog with no content component. The default set of
     * button(s) will be displayed. After creating the Dialog, call setContent()
     * to configure the dialog before calling show() to display it.
     *
     * @param owner the owning Frame
     * @param title the title String for the dialog
     * @param modal true if the dialog is modal
     */
    public Dialog(Frame owner, String title, boolean modal) {
        this(owner, title, DEFAULT_OPTION, modal);
    }
            
    /**
     * Creates a new Dialog with no content component, using the specified
     * optionType to determine the set of available buttons.
     * After creating the Dialog, call setContent()
     * to configure the dialog before calling show() to display it.
     *
     * @param owner the owning Frame
     * @param title the title String for the dialog
     * @param theOptionType defines which buttons will be 
     *                      available on the dialog
     * @param modal true if the dialog is modal
     */
    public Dialog(Frame owner, String title, int theOptionType, boolean modal) {
        super(owner, title, modal);
        
        optionType = theOptionType;
        
        JButton[] buttons = createButtons();

        nameButtons();

        content = null;      
            
        mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout(0, bottomBorder));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(topBorder,
							     leftBorder,
							     bottomBorder,
							     rightBorder));
        getContentPane().add(mainPanel);

        buttonPanel = new JPanel(new SerialLayout(Horizontal.getInstance(),
						   SerialLayout.EAST, 
						   SerialLayout.LEFTTORIGHT,
						   SerialLayout.TOP,
						   buttonGap));
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        for (int i = 0; i < buttons.length; ++i) {
            buttonPanel.add(buttons[i]);
            buttons[i].addActionListener(this);
        }

        getRootPane().setDefaultButton(buttons[0]);
    }

    /**
     * Returns the main component that is displayed within the dialog.
     * 
     * @return  main component displayed in dialog
     **/
    public JComponent getContent() {
        return content;
    }

    /**
     * Sets the main component to be displayed within the dialog.
     * Note: this method is final because it is most likely to be used
     * in subclass constructors, and calling a class's overridable methods in
     * its own constructor is not good practice.
     *
     * @param theContent   main component to display in dialog
     **/
    public final void setContent(JComponent theContent) {
        if (content != null) {
            mainPanel.remove(content);
        }
        content = theContent;
        mainPanel.add(content, BorderLayout.CENTER);
        
        pack();
        centerOnParent();
    }
    
    /**
     * Adds a new button to the set of available option buttons on the dialog.
     * The button will appear after the buttons specified by the optionType.
     * 
     * @param button the button to add to the dialog.
     **/
    public void addButton(JButton button) {
        buttonPanel.add(button);
    }
    
    /**
     * Adds a new button to the set of available option buttons on the dialog.
     * The button will appear at the specified index.
     * 
     * @param button the button to add to the dialog.
     * @param index  index at which to insert new button (0 for first button)
     **/
    public void addButton(JButton button, int index) {
        buttonPanel.add(button, index);
    }    
    
    /**
     * @return the requested button
     */
    protected JButton getOkButton() {
        return okButton;
    }

    /**
     * @return the requested button
     */
    protected JButton getCancelButton() {
        return cancelButton;
    }

    /**
     * @return the requested button
     */
    protected JButton getCloseButton() {
        return closeButton;
    }

    /**
     * @return the requested button
     */
    protected JButton getYesButton() {
        return yesButton;
    }

    /**
     * @return the requested button
     */
    protected JButton getNoButton() {
        return noButton;
    }

    /**
     * @return the requested button
     */
    protected JButton getHelpButton() {
        return helpButton;
    }
    
    /**
     * Default implementation simply closes the dialog when
     * any of the standard buttons is pressed except the Help button.
     *
     * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
     */
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == okButton
            || e.getSource() == cancelButton
            || e.getSource() == closeButton
            || e.getSource() == yesButton
            || e.getSource() == noButton) {
            setVisible(false);
            dispose();
        }
    }
    
    /**
     * Creates the set of JButtons for the current optionType.
     **/
    private JButton[] createButtons() {
        JButton[] buttons;       
        switch(optionType) {
	case YES_NO_OPTION:
	    yesButton = new JButton();
	    noButton = new JButton();
	    buttons = new JButton[] {
		yesButton, noButton 
	    };
	    break;
        
	case YES_NO_HELP_OPTION:
	    yesButton = new JButton();
	    noButton = new JButton();
	    helpButton = new JButton();
	    buttons = new JButton[] {
		yesButton, noButton, helpButton 
	    };
	    break;
        
	case YES_NO_CANCEL_OPTION:
	    yesButton = new JButton();
	    noButton = new JButton();
	    cancelButton = new JButton();
	    buttons = new JButton[] {
		yesButton, noButton, cancelButton 
	    };
	    break;
        
	case YES_NO_CANCEL_HELP_OPTION:
	    yesButton = new JButton();
	    noButton = new JButton();
	    cancelButton = new JButton();
	    helpButton = new JButton();
	    buttons = new JButton[] {
		yesButton, noButton, cancelButton, helpButton 
	    };
	    break;
        
	case OK_CANCEL_OPTION:
	    okButton = new JButton();
	    cancelButton = new JButton();
	    buttons = new JButton[] {
		okButton, cancelButton 
	    };
	    break;
        
	case OK_CANCEL_HELP_OPTION:
	    okButton = new JButton();
	    cancelButton = new JButton();
	    helpButton = new JButton();
	    buttons = new JButton[] {
		okButton, cancelButton, helpButton 
	    };
	    break;
                
	case CLOSE_OPTION:
	default:
	    closeButton = new JButton();
	    buttons = new JButton[] {
		closeButton 
	    };
	    break;
        }
        return buttons;
    }
        
    /**
     * Moves the dialog to be centered on its parent's location on the screen.
     **/
    private void centerOnParent() {
        Dimension size = getSize();
        Dimension p = getParent().getSize();
        int x = (getParent().getX() - size.width)
	    + (int) ((size.width + p.width) / 2d);
        int y = (getParent().getY() - size.height)
	    + (int) ((size.height + p.height) / 2d);
        setLocation(x, y);
    }
        
    /**
     * Subclasses may override this method to change the names and mnemonics of
     * the various JButtons which appear at the bottom of the dialog.
     **/
    protected abstract void nameButtons();

    /**
     * @return Returns the componentGap.
     */
    protected int getComponentGap() {
        return componentGap;
    }

    /**
     * @return Returns the labelGap.
     */
    protected int getLabelGap() {
        return labelGap;
    }
}
