package net.b5gamer.b5wars.test;

import net.b5gamer.b5wars.io.DataManager;
import net.b5gamer.b5wars.ui.scs.ControlSheetFrame;
import net.b5gamer.b5wars.ui.scs.UsageMode;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.UnitID;

public class SCSFrameTest {

	public static void main(String[] args) {
		javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
            	try {
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Brakiri\\Brakiri Tashkava Advanced Lance Cruiser.xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Centauri\\Centauri Demos Heavy Warship.xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Centauri\\Centauri Primus Battlecruiser.xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Centauri\\Centauri Vorchan Warship.xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Dilgar\\Dilgar Mishakur Dreadnought.xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Dilgar\\Dilgar Ochlavita Destroyer.xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Dilgar\\Dilgar Targath Strike Cruiser.xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Drazi\\Drazi Darkhawk Missile Cruiser.xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Drazi\\Drazi Stormfalcon Heavy Cruiser.xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Drazi\\Drazi Strikehawk Battle Carrier.xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Drazi\\Drazi Sunhawk Battlecruiser.xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Drazi\\Drazi Warbird Cruiser.xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "EA\\EA Artemis Escort Frigate (Zeta Model).xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "EA\\EA Artemis Heavy Frigate (Beta Model).xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "EA\\EA Avenger Heavy Carrier (Gamma Model).xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "EA\\EA Epimetheus Jump Cruiser (Epsilon Model).xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "EA\\EA Hyperion Heavy Cruiser (Theta Model).xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "EA\\EA Hyperion Patrol Cruiser (Eta Model).xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "EA\\EA Nova Dreadnought (Beta Model).xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "EA\\EA Olympus Corvette (Delta Model).xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "EA\\EA Omega Destroyer (Alpha Model).xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "EA\\EA Sagittarius Missile Cruiser (Beta Model).xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Minbari\\Minbari Tinashi War Frigate.xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Minbari\\Minbari White Star (Standard Model).xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Shadow\\Shadow Cruiser.xml").get(0);
//            		Unit unit = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + "Vree\\Vree Xorr War Saucer.xml").get(0);


            		// located
//            		Unit unit = DataManager.getUnitDao().load(new UnitID("EA Nova Dreadnought (Beta Model)", null, 0));            		
            		// located
            		Unit unit = DataManager.getUnitDao().load(new UnitID("Drazi Warbird Cruiser", null, 0));          
            		// not located
//            		Unit unit = DataManager.getUnitDao().load(new UnitID("Minbari Sharlin War Cruiser", null, 0));            		

//            		new ControlSheetFrame(unit, UsageMode.ALL);
            		new ControlSheetFrame(unit, UsageMode.PLAY);
				} catch (Exception e) {
					System.out.println("Falied to display " + ControlSheetFrame.class.getName());
					e.printStackTrace();
				}
            }
        });
	}

}
