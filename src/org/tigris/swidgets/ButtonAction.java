// $Id$

/*
 * ButtonAction.java
 *
 * Created on 02 March 2003, 00:23
 */

package org.tigris.swidgets;

/**
 *
 * @author ButtonAction
 */
public interface ButtonAction {
   
    /**
     * @param modal the modal of the action
     */
    public void setModal(boolean modal);
    
    /**
     * @return true if the action is modal
     */
    public boolean isModal();
    
    /**
     * @param lockMethod  purpose: action buttons can remain depressed 
     *                    so that they can be used multiple times
     */
    public void setLockMethod(int lockMethod);
    
    /**
     * @return the int lockMethod
     */
    public int getLockMethod();
}
