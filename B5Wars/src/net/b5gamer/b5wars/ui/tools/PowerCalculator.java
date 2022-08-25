package net.b5gamer.b5wars.ui.tools;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.io.unit.UnitXMLReader;
import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.system.PowerProvidingSystem;
import net.b5gamer.b5wars.unit.structural.system.PowerRequiringSystem;
import net.b5gamer.b5wars.unit.structural.system.System;

public class PowerCalculator {

	public static void main(String[] args) throws Exception {
		Logger.info("");		

		java.lang.System.out.print("Enter Unit filename: ");
		BufferedReader br = new BufferedReader(new InputStreamReader(java.lang.System.in));
		String filename = null;
		try {
			filename = br.readLine();
		} catch (IOException e) {
			java.lang.System.out.println("Failed obtaining unit filename");
			java.lang.System.exit(1);
		}
	
		List<Unit> units = UnitXMLReader.read(UnitXMLReader.DEFAULT_DIRECTORY + File.separator + filename);
		StructuralUnit unit = (StructuralUnit) units.get(0);

		Logger.info("");		
		Logger.info(unit.getDescription());
		Logger.info("");		
		
		int totalPowerRequirement = 0;		
		int totalAvailablePower   = 0;
		for (Iterator<Section> iterator = unit.getSectionIterator(); iterator.hasNext();) {
			Section section = iterator.next();
			
			Logger.info(section.getName() + ":");		
			Logger.info("    Powered Systems:");		

			List<System> poweredSystems = section.getSystemsOfClass(PowerRequiringSystem.class, true);

			int powerRequirement = 0;
			for (Iterator<System> iter = poweredSystems.iterator(); iter.hasNext();) {
				System system = iter.next();
				powerRequirement += ((PowerRequiringSystem) system).getPowerRequirement();

				Logger.info("        " + system.getFullName() + " - requires " + ((PowerRequiringSystem) system).getPowerRequirement() + " power");
			}
			
			Logger.info("    Power Requirement: " + powerRequirement);	

			Logger.info("    Powering Systems:");		

			int availablePower = 0;
			List<System> poweringSystems = section.getSystemsOfClass(PowerProvidingSystem.class, true);
			for (Iterator<System> iter = poweringSystems.iterator(); iter.hasNext();) {
				System system = iter.next();
				availablePower += ((PowerProvidingSystem) system).getAvailablePower();

				Logger.info("        " + system.getFullName() + " - provides " + ((PowerProvidingSystem) system).getAvailablePower() + " power");
			}			

			Logger.info("    Power Available: " + availablePower);	
			Logger.info("    " + ((availablePower - powerRequirement < 0) ? "Power Shortage: " : "Extra Power: ") + (availablePower - powerRequirement));	
			
			totalPowerRequirement += powerRequirement;
			totalAvailablePower   += availablePower;
		}
		
		Logger.info("Total Power requirement: " + totalPowerRequirement);	
		Logger.info("Total Power Available: " + totalAvailablePower);	
		Logger.info("Total " + ((totalAvailablePower - totalPowerRequirement < 0) ? "Power Shortage: " : "Extra Power: ") + (totalAvailablePower - totalPowerRequirement));	
	}

}
