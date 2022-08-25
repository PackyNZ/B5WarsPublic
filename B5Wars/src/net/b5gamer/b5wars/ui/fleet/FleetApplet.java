/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.b5wars.ui.fleet;

import javax.swing.JApplet;
import javax.swing.SwingUtilities;

import net.b5gamer.b5wars.game.Fleet;
import net.b5gamer.b5wars.io.DataAccessMode;
import net.b5gamer.b5wars.io.DataManager;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.UnitID;

@SuppressWarnings("removal")
public class FleetApplet extends JApplet {

	private static final long serialVersionUID = -7107111980886112951L;

	private Fleet fleet;

	@SuppressWarnings("deprecation")
	public void init() {
		DataManager.setServerUrl(this.getCodeBase().toString());
    	DataManager.setDataAccessMode(DataAccessMode.REMOTE);
    	
    	// TODO set fleet		
		try {
			Fleet fleet = new Fleet("Drazi 3000pts");
			Unit unit = DataManager.getUnitDao().load(new UnitID("Drazi Sunhawk Battlecruiser", null, 0));
			unit.setGivenName("Sunhawk 1");
			fleet.addUnit(unit);            		
			unit = DataManager.getUnitDao().load(new UnitID("Drazi Sunhawk Battlecruiser", null, 0));
			unit.setGivenName("Sunhawk 2");
			fleet.addUnit(unit);            		
			unit = DataManager.getUnitDao().load(new UnitID("Drazi Warbird Cruiser", null, 0));
			unit.setGivenName("Warbird 1");
			fleet.addUnit(unit);
			unit = DataManager.getUnitDao().load(new UnitID("Drazi Warbird Cruiser", null, 0));
			unit.setGivenName("Warbird 2");
			fleet.addUnit(unit);
			unit = DataManager.getUnitDao().load(new UnitID("Drazi Warbird Cruiser", null, 0));
			unit.setGivenName("Warbird 3");
			fleet.addUnit(unit);
			unit = DataManager.getUnitDao().load(new UnitID("Drazi Warbird Cruiser", null, 0));
			unit.setGivenName("Warbird 4");
			fleet.addUnit(unit);
			setFleet(fleet);
		} catch (Exception e) {
			e.printStackTrace();
		}
    	
    	try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {                	
                	createGUI();
                }
            });
        } catch (Exception e) { 
        	e.printStackTrace();
        }
    }

	public Fleet getFleet() {
		return fleet;
	}

	protected void setFleet(Fleet fleet) {
		this.fleet = fleet;
	}
	
	@SuppressWarnings("deprecation")
	private void createGUI() {
//		// create menu
//		JMenuBar menuBar = new JMenuBar();
//        menuBar.setOpaque(true);
//        menuBar.setPreferredSize(new Dimension(getPreferredSize().width, 20));
//        menuBar.setBorderPainted(true);
//        setJMenuBar(menuBar);
//        
//        // control sheets menu
//        JMenu controlSheetsMenu = new JMenu("Control Sheets");
//        controlSheetsMenu.setMnemonic(KeyEvent.VK_C);
//        menuBar.add(controlSheetsMenu);
//        
////        JMenu displayMenu = new JMenu("Display");
////        displayMenu.setMnemonic(KeyEvent.VK_D);
////        controlSheetsMenu.add(displayMenu);
////        	    
////        JMenuItem displayInfo = new JMenuItem(new DisplayInfoAction(this));
////        displayInfo.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
////        displayMenu.add(displayInfo);
//	    
//	    JMenu menuZoom = new JMenu("Zoom");
//		JMenuItem fitToScreen = new JMenuItem(new FitWindowAction(this));
//		menuZoom.add(fitToScreen);
//	    menuZoom.addSeparator();
//	    for (int index = 0; index < ZOOM_LEVELS.length; index++) {
//			JMenuItem zoomTo = new JMenuItem(new ZoomToAction(this, ZOOM_LEVELS[index]));
//			menuZoom.add(zoomTo);	    	
//	    }
	    
        setContentPane(new FleetViewerInterface(getFleet()));
	}

}
