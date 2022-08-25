package net.b5gamer.b5wars.ui.fleet;

import net.b5gamer.b5wars.game.Fleet;
import net.b5gamer.b5wars.io.DataManager;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.UnitID;

/**
 * Fleet Viewer application
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class FleetViewer {

	public static void main(String[] args) {
    	javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
            		// TODO sample fleet
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
            		
					new FleetViewerFrame(fleet);
				} catch (Exception e) {
					e.printStackTrace();
				}
            }
        });
	}

}