package net.b5gamer.b5wars.test.xml;

import net.b5gamer.b5wars.io.unit.LoadUnitException;
import net.b5gamer.b5wars.io.unit.UnitXMLReader;
import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.b5wars.ui.render.StatHTMLRenderer;
import net.b5gamer.b5wars.unit.Unit;

public class ImportTester {

	public static void main(String[] args) {
		try {
			Unit unit = UnitXMLReader.read(ImportTester.class.getResourceAsStream("Narn G'Quan Heavy Cruiser.xml"));

			//		for (Iterator<Unit> iterator = list.iterator(); iterator.hasNext();) {
			//			Logger.info(new StatHTMLRenderer().renderAsHtml(iterator.next()));
			//		}

			Logger.info(new StatHTMLRenderer().renderAsHtml(unit));
		} catch (LoadUnitException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
	}
	
	// units in B5W 2E
/*	
	EA Omega Destroyer (Alpha Model)
	EA Nova Dreadnought (Beta Model)
	EA Hyperion Heavy Cruiser (Theta Model)
	EA Olympus Corvette (Delta Model)
	EA Starfury Heavy Fighters
	EA Thunderbolt Assault Fighters
	
	Minbari Sharlin War Cruiser
	Minbari Tinashi War Frigate
	Minbari White Star (Standard Model)
	Minbari Nial Heavy Fighters
	
	Centauri Primus Battlecruiser
	Centauri Vorchan Warship
	Centauri Demos Heavy Warship
	Centauri Sentri Medium Fighters
	
	Narn G'Quan Heavy Cruiser
	Narn T'Loth Assault Cruiser
	Narn Frazi Heavy Fighters
	
	Civilian Fixed Jump Gate (Generic)
*/
	
}
