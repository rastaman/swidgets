package org.tigris.swidgets;

import java.awt.BorderLayout;
import java.awt.Component;
import java.util.ArrayList;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.border.TitledBorder;


/**
 * <p>A container for multiple JPanels with various display modes which can be alternated.</p>
 * <p>Child panels can be presented
 * <ul>
 * <li> wrapped by Tabpages inside a TabbedPane
 * <li> wrapped by JInternalFrames inside a JDesktopPane 
 * <li> placed side by side inside a MultipleSplitPane
 * </ul></p>
 * <p>It is envisioned that the application knows nothing about the WorkbenchPanel and
 * access is only allowed via Workbench and WorkbenchFrame.</p>
 * TODO This is work in progress. At the moment this class just wraps a single JPanel.
 * @author Bob Tarling
 */
class WorkbenchPanel extends JPanel {

    public static final int TABBED_MODE = 0;
    public static final int INTERNAL_FRAME_MODE = 1;
    
    private List panelList = new ArrayList();
    private JTabbedPane tabbedPane;
    private JDesktopPane desktopPane;

    private int mode = TABBED_MODE;
        
    WorkbenchPanel() {
        super();
        setLayout(new BorderLayout());
    }

    WorkbenchPanel(int mode) {
        super();
        this.mode = mode;
        setLayout(new BorderLayout());
    }

    /**
     * Add a panel top this workbench panel. Depending on current mode the is either
     * added wrapped as a tabpage or as a JInternal
     */
    public Component add(Component arg0, int arg1) {
        return super.add(arg0, arg1);
    }

    /**
     * Add a panel top this workbench panel. Depending on current mode the is either
     * added wrapped as a tabpage or as a JInternal
     */
    public void add(Component arg0, Object arg1, int arg2) {
        super.add(arg0, arg1, arg2);
    }

    /**
     * Add a panel top this workbench panel. Depending on current mode the is either
     * added wrapped as a tabpage or as a JInternal
     */
    public void add(Component arg0, Object arg1) {
        super.add(arg0, arg1);
    }

    /**
     * Add a panel top this workbench panel. Depending on current mode the is either
     * added wrapped as a tabpage or as a JInternal
     */
    public Component add(Component comp) {
        if (panelList.contains(comp)) return null;
        
        if (mode == TABBED_MODE) {
            if (panelList.size() == 0) {
                // For first component just add directly to pane
                panelList.add(comp);
                TitledBorder tb 
                    = BorderFactory.createTitledBorder(BorderFactory.createEmptyBorder(), comp.getName());
                setBorder(tb);
                return super.add(comp);
            } else {
                if (panelList.size() == 1) {
                    // For second component component create a tabbed
                    // pane container.
                    Component existingPane = (Component)panelList.get(0);
                    super.remove(existingPane);
                    tabbedPane = new JTabbedPane();
                    tabbedPane.add(existingPane);
                    super.add(tabbedPane);
                    setBorder(null);
                }
                // For second and subsequent add new pane to tabbed panel
                panelList.add(comp);
                tabbedPane.addTab(comp.getName(), comp);
                tabbedPane.setSelectedComponent(comp);
                return comp;
            }
        } else {
            if (panelList.size() == 0) {
                // For first component we need to create the desktop
                desktopPane = new JDesktopPane();
                super.add(desktopPane);
            }

            panelList.add(comp);
            JInternalFrame jif = new JInternalFrame(comp.getName());
            jif.getContentPane().add(comp);
            jif.setVisible(true);
            jif.setSize(400,400);
            jif.setMaximizable(true);
            jif.setIconifiable(false);
            jif.setResizable(true);
            jif.setClosable(true);
            desktopPane.add(jif);
            desktopPane.setSelectedFrame(jif);
            jif.toFront();
            return comp;
        }
    }

    /**
     * Add a panel top this workbench panel. Depending on current mode the is either
     * added wrapped as a tabpage or as a JInternal
     */
    public Component add(String arg0, Component arg1) {
        return super.add(arg0, arg1);
    }
    
    public void setMode(int mode) {
        if (mode == this.mode) return;
        if (mode == TABBED_MODE) {
            
        }
    }
}
