package org.tigris.swidgets;

import java.util.Collection;
import java.util.Iterator;

import javax.swing.Action;
import javax.swing.JMenu;

/**
 * @author Bob Tarling
 */
public class MenuFactory {

    public static final JMenu makeMenu(String name, Object[] actions) {
        JMenu menu = new JMenu(name);
        for (int i=0; i < actions.length; ++i) {
            if (actions[i] == null) {
                menu.addSeparator();
            } else if (actions[i] instanceof Action) {
                menu.add((Action)actions[i]);
            }
        }
        return menu;
    }
    
    public static final JMenu makeMenu(String name, Collection actions) {
        JMenu menu = new JMenu(name);
        Iterator it = actions.iterator();
        while (it.hasNext()) {
            Object item = it.next();
            if (item == null) {
                menu.addSeparator();
            } else if (item instanceof Action) {
                menu.add((Action)item);
            }
        }
        return menu;
    }
}
