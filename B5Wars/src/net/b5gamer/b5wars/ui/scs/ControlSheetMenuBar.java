package net.b5gamer.b5wars.ui.scs;

import java.awt.event.ActionEvent;
import java.awt.event.KeyEvent;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import net.b5gamer.b5wars.io.DataAccessMode;
import net.b5gamer.b5wars.io.DataManager;

public class ControlSheetMenuBar extends JMenuBar {
	
	private static final long serialVersionUID = 8960801071470133941L;

	public ControlSheetMenuBar(final ControlSheetInterface controlSheetInterface) {
		super();
     
		setupMenu(controlSheetInterface);
	}
	
	private void setupMenu(final ControlSheetInterface controlSheetInterface) {
        // file menu
        JMenu fileMenu = new JMenu("File");
        fileMenu.setMnemonic(KeyEvent.VK_F);
        add(fileMenu);

        if ((controlSheetInterface.getUsageMode() == UsageMode.EDIT) || (controlSheetInterface.getUsageMode() == UsageMode.ALL)) {
	        JMenu newMenu = new JMenu("New");
	        newMenu.setMnemonic(KeyEvent.VK_N);
	        fileMenu.add(newMenu);
			for (int index = 0; index < NewAction.UNIT_TYPES.length; index++) {
				JMenuItem newMenuItem = new JMenuItem(new NewAction(NewAction.UNIT_TYPES[index], controlSheetInterface));
				newMenu.add(newMenuItem);	    	
		    }
        }
        
//      openMenuItem = new JMenuItem("Open...");
//      openMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, ActionEvent.CTRL_MASK));
//      openMenuItem.addActionListener(this);
//      fileMenu.add(openMenuItem);	    

//      saveMenuItem = new JMenuItem("Save As...");
//      saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, ActionEvent.CTRL_MASK));
//      saveMenuItem.addActionListener(this);
//      fileMenu.add(saveMenuItem);	
        
        JMenuItem unitDefinitionItem = new JMenuItem(new UnitDefinitionAction(controlSheetInterface));
        unitDefinitionItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_U, ActionEvent.CTRL_MASK));
        fileMenu.add(unitDefinitionItem);
        
        JMenu exportMenu = new JMenu("Export To");
        exportMenu.setMnemonic(KeyEvent.VK_E);
        fileMenu.add(exportMenu);

//        JMenuItem exportMenuItem = new JMenuItem(new LocalExportAction("PDF", this, controlSheetInterface.getControlSheetPanel()));
//        exportMenu.add(exportMenuItem);
//        exportMenuItem = new JMenuItem(new LocalExportAction("XML", this, controlSheetInterface.getControlSheetPanel()));
//        exportMenu.add(exportMenuItem);
//        
//        JMenuItem exportMenuItem = new JMenuItem(new RemoteExportAction("PDF", this, DataManager.getServerUrl(), getUnit()));
//        exportMenu.add(exportMenuItem);
//        exportMenuItem = new JMenuItem(new RemoteExportAction("XML", this, DataManager.getServerUrl(), getUnit()));
//        exportMenu.add(exportMenuItem);        
        
        if (DataManager.getDataAccessMode() == DataAccessMode.LOCAL_CLIENT) {
            fileMenu.addSeparator();
            
            JMenuItem exitMenuItem = new JMenuItem(new ExitAction());
            exitMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, ActionEvent.CTRL_MASK));
            fileMenu.add(exitMenuItem);	
        }
        	
        
        // view menu
        JMenu viewMenu = new JMenu("View");
        viewMenu.setMnemonic(KeyEvent.VK_V);
        add(viewMenu);

//        viewMenu.add(createToggleDataMenuItem(controlSheetPanel));
//	    viewMenu.addSeparator();	    
	    viewMenu.add(createZoomToMenu(controlSheetInterface.getControlSheetPanel()));   
	    
	    
	    // actions menu
        if ((controlSheetInterface.getUsageMode() == UsageMode.PLAY) || (controlSheetInterface.getUsageMode() == UsageMode.ALL)) {
	        JMenu actionsMenu = new JMenu("Actions");
	        actionsMenu.setMnemonic(KeyEvent.VK_A);
	        add(actionsMenu);
	
	        JMenuItem adjustSystemsMenuItem = new JMenuItem(new AdjustSystemsAction(controlSheetInterface.getControlSheetPanel()));
	        adjustSystemsMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_A, ActionEvent.CTRL_MASK));
	        actionsMenu.add(adjustSystemsMenuItem);	    
        }
	}
	
	public static JMenuItem createToggleDataMenuItem(final ControlSheetPanel controlSheetPanel) {
	    JCheckBoxMenuItem toggleDataMenuItem = new JCheckBoxMenuItem(new ToggleDataAction(controlSheetPanel));
	    toggleDataMenuItem.setSelected(controlSheetPanel.isShowData());
	    
	    return toggleDataMenuItem;
	}
	
	public static JMenu createZoomToMenu(final ControlSheetPanel controlSheetPanel) {
	    JMenu zoomToMenu = new JMenu("Zoom To");
	    zoomToMenu.setMnemonic(KeyEvent.VK_Z);
		zoomToMenu.add(new JMenuItem(new FitWindowAction(controlSheetPanel)));

		zoomToMenu.addSeparator();
	    
		for (int index = 0; index < ZoomToAction.ZOOM_LEVELS.length; index++) {
			JMenuItem zoomToMenuItem = new JMenuItem(new ZoomToAction(ZoomToAction.ZOOM_LEVELS[index], controlSheetPanel));
			zoomToMenu.add(zoomToMenuItem);	    	
	    }
	    
	    return zoomToMenu;
	}
	
}
