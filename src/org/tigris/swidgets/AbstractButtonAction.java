// $Id$

/*
 * AbstractButtonAction.java
 *
 * Created on 02 March 2003, 00:26
 */

package org.tigris.swidgets;

import javax.swing.AbstractAction;
import javax.swing.Icon;

/**
 *
 * @author Bob Tarling
 */
public abstract class AbstractButtonAction
    extends AbstractAction
    implements ButtonAction
{
    
    private boolean modal;
    private int lockMethod = NONE;
    
    /**
     * Possible ways in which a user can lock a button: <code>NONE</code>
     */
    public static final int NONE = 0;
    
    /**
     * Possible ways in which a user can lock a button:
     *  <code>DOUBLE_CLICK</code>
     */
    public static final int DOUBLE_CLICK = 1;
    
    /**
     * Creates a new instance of AbstractButtonAction
     *
     * @param name the name of the action
     * @param icon the icon for the action
     */
    public AbstractButtonAction(String name, Icon icon) {
        super(name, icon);
    }

    /**
     * Creates a new instance of AbstractButtonAction
     *
     * @param name the name of the action
     * @param icon the icon of the action
     * @param isModal modal = the user is obliged to answer this action 
     *              before doing anything else
     */
    public AbstractButtonAction(String name, Icon icon, boolean isModal) {
        super(name, icon);
        this.modal = isModal;
    }

    /**
     * Creates a new instance of AbstractButtonAction
     *
     * @param name the name of the action
     * @param icon the icon of the action
     * @param isModal modal = the user is obliged to answer this action 
     *              before doing anything else
     * @param theLockMethod purpose: action buttons can remain depressed 
     *                      so that they can be used multiple times
     */
    public AbstractButtonAction(String name, Icon icon,
				boolean isModal, int theLockMethod) {
        super(name, icon);
        this.modal = isModal;
        this.lockMethod = theLockMethod;
    }
    
    /**
     * @see org.argouml.swingext.ButtonAction#setModal(boolean)
     */
    public void setModal(boolean isModal) {
        this.modal = isModal;
    }
    
    /**
     * @see org.argouml.swingext.ButtonAction#isModal()
     */
    public boolean isModal() {
        return modal;
    }
    
    /**
     * @see org.argouml.swingext.ButtonAction#setLockMethod(int)
     */
    public void setLockMethod(int theLockMethod) {
        this.lockMethod = theLockMethod;
    }
    
    /**
     * @see org.argouml.swingext.ButtonAction#getLockMethod()
     */
    public int getLockMethod() {
        return lockMethod;
    }
}
