package org.tigris.swidgets;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JToolBar;

import org.tigris.toolbar.layouts.DockBorderLayout;

/**
 * A frame containing toolbar and component window support
 *
 * @author Bob Tarling
 */
public class WorkbenchFrame extends JFrame {
    
    public static final int TABBED_MODE = 0;
    public static final int INTERNAL_FRAME_MODE = 1;
    
    public static final String NORTHEAST = BorderSplitPane.NORTHEAST;
    public static final String NORTHWEST = BorderSplitPane.NORTHWEST;
    public static final String SOUTHEAST = BorderSplitPane.SOUTHEAST;
    public static final String SOUTHWEST = BorderSplitPane.SOUTHWEST;
    public static final String EAST = BorderSplitPane.EAST;
    public static final String WEST = BorderSplitPane.WEST;
    public static final String NORTH = BorderSplitPane.NORTH;
    public static final String SOUTH = BorderSplitPane.SOUTH;
    public static final String CENTER = BorderSplitPane.CENTER;
    
    private BorderSplitPane workarea = new BorderSplitPane();
    private JPanel toolbarBoundry;
    private boolean constructed = false;
    private Map workbenchPanelByPosition = new HashMap(9);

    private Map panelDataMap = new HashMap(9);
    
    public WorkbenchFrame(String name) {
        super(name);
        init();
    }
        
    public WorkbenchFrame() {
        super();
        init();
    }

    public void addToolBar(JToolBar toolbar) {
        toolbarBoundry.add(toolbar, DockBorderLayout.NORTH);
    }

    private void init() {
        panelDataMap.put(NORTHEAST, new PanelData(WorkbenchPanel.TABBED_MODE));
        panelDataMap.put(NORTHWEST, new PanelData(WorkbenchPanel.TABBED_MODE));
        panelDataMap.put(SOUTHEAST, new PanelData(WorkbenchPanel.TABBED_MODE));
        panelDataMap.put(SOUTHWEST, new PanelData(WorkbenchPanel.TABBED_MODE));
        panelDataMap.put(EAST, new PanelData(WorkbenchPanel.TABBED_MODE));
        panelDataMap.put(WEST, new PanelData(WorkbenchPanel.TABBED_MODE));
        panelDataMap.put(NORTH, new PanelData(WorkbenchPanel.TABBED_MODE));
        panelDataMap.put(SOUTH, new PanelData(WorkbenchPanel.TABBED_MODE));
        panelDataMap.put(CENTER, new PanelData(WorkbenchPanel.TABBED_MODE));
    
        toolbarBoundry = new JPanel();
        toolbarBoundry.setLayout(new DockBorderLayout());
        toolbarBoundry.add(workarea, DockBorderLayout.CENTER);
        getContentPane().setLayout(new BorderLayout());
        getContentPane().add(toolbarBoundry);
        constructed = true;
    }
        
    /**
     *  Delegate any added components to the workarea
     */
    public Component add(Component arg0, int arg1) {
        if (constructed) {
            return workarea.add(arg0, arg1);
        } else {
            return super.add(arg0, arg1);
        }
    }

    /**
     * Delegate any added components to the workarea
     */
    public void add(Component comp, Object position) {
        if (constructed) {
            // Wrap the component in a WorkbenchPanel
            WorkbenchPanel wp = (WorkbenchPanel)workbenchPanelByPosition.get(position);
            if (wp == null) {
                System.out.println(position);
                PanelData pd = (PanelData)panelDataMap.get(position);
                System.out.println(pd);
                int style = pd.style;
                wp = new WorkbenchPanel(style);
                wp.setPreferredSize(new Dimension(90,90));
                workarea.add(wp, position);
                workbenchPanelByPosition.put(position, wp);
            }
            wp.add(comp);
        } else {
            super.add(comp, position);
        }
    }

    /**
     * Delegate any added components to the workarea
     */
    public void setMode(int mode, String position) {
        panelDataMap.put(position, new PanelData(mode));
    }

    /**
     * Delegate any added components to the workarea
     */
    public Component add(Component comp) {
        if (constructed) {
            // Wrap the component in a WorkbenchPanel
            add(comp, BorderSplitPane.CENTER);
            return comp;
        } else {
            return super.add(comp);
        }
    }

    private class PanelData {
        int style;
        PanelData(int style) {
            this.style = style;
        }
    }
}
