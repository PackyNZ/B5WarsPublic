package net.b5gamer.b5wars.test;

import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.io.DataManager;
import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.b5wars.unit.UnitID;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.system.DamageAbsorbingSystem;
import net.b5gamer.b5wars.unit.structural.system.DefensiveSystem;
import net.b5gamer.b5wars.unit.structural.system.DirectionalSystem;
import net.b5gamer.b5wars.unit.structural.system.JumpEngine;
import net.b5gamer.b5wars.unit.structural.system.PoweredSystem;
import net.b5gamer.b5wars.unit.structural.system.Sensors;
import net.b5gamer.b5wars.unit.structural.system.Shield;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.b5wars.unit.structural.system.Thruster;
import net.b5gamer.b5wars.unit.structural.system.Weapon;

public class SystemSelectionTester {

	public static void main(String[] args) throws Exception {
//		StructuralUnit unit = new TestStructuralUnit();
//		StructuralUnit unit = new LocationTestStructuralUnit();
		StructuralUnit unit = (StructuralUnit) DataManager.getUnitDao().load(new UnitID("Shadows Cruiser", null, 0));

		UnitTester.outputUnit(unit);
		
		Logger.info("---------------------------------------------------------------------------------");
		
		Logger.info("Systems of class Sensor:");
		UnitTester.outputSystems(unit.getSystemsOfClass(Sensors.class, true).iterator());
		
		Logger.info("");
		
		Logger.info("Systems of class PoweredSystem:");
		UnitTester.outputSystems(unit.getSystemsOfClass(PoweredSystem.class, true).iterator());

		Logger.info("");
		
		Logger.info("Systems of class DirectionalSystem:");
		UnitTester.outputSystems(unit.getSystemsOfClass(DirectionalSystem.class, true).iterator());

		Logger.info("");
		
		Logger.info("Systems of class DefensiveSystem:");
		UnitTester.outputSystems(unit.getSystemsOfClass(DefensiveSystem.class, true).iterator());

		Logger.info("");
		
		Logger.info("Systems of class Weapon:");
		UnitTester.outputSystems(unit.getSystemsOfClass(Weapon.class, true).iterator());

		Logger.info("");
		
		Logger.info("Systems of type Weapon:");
		UnitTester.outputSystems(unit.getSystemsOfType("Weapon", true).iterator());

		Logger.info("");
		
		Logger.info("Systems of type Molecular Slicer Beam:");
		UnitTester.outputSystems(unit.getSystemsOfType("Molecular Slicer Beam", true).iterator());
		
		Logger.info("");
		
		Logger.info("Weapons of type Gravitic Shield:");
		UnitTester.outputSystems(unit.getWeaponsOfType("Gravitic Shield", true).iterator());
		
		Logger.info("");

		Logger.info("Systems of class Shield:");
		UnitTester.outputSystems(unit.getSystemsOfClass(Shield.class, true).iterator());

		Logger.info("");

		Logger.info("Weapons of class Shield:");
		UnitTester.outputSystems(unit.getWeaponsOfClass(Shield.class, true).iterator());

		Logger.info("");

		Logger.info("Weapons of class JumpEngine:");
		UnitTester.outputSystems(unit.getWeaponsOfClass(JumpEngine.class, true).iterator());

		Logger.info("");

//		Logger.info("Systems of class Hangar in section " + Section.FORWARD + ":");
//		UnitTester.outputSystems(unit.getSection(Section.FORWARD).getSystemsOfClass(Hangar.class, true).iterator());
//
//		Logger.info("");

		Logger.info("Systems of class Thruster in section " + Section.PRIMARY + ":");
		UnitTester.outputSystems(unit.getSection(Section.PRIMARY).getSystemsOfClass(Thruster.class, true).iterator());

		Logger.info("");
		
//		Logger.info("Systems of class Thruster in section " + Section.PORT + ":");
//		UnitTester.outputSystems(unit.getSection(Section.PORT).getSystemsOfClass(Thruster.class, true).iterator());
//		
//		Logger.info("");
		
		Logger.info("Systems of class DamageAbsorbingSystem:");
		UnitTester.outputSystems(unit.getSystemsOfClass(DamageAbsorbingSystem.class, true).iterator());

		Logger.info("");
		
		Logger.info("Systems of class DamageAbsorbingSystem for Sensor:");
		List<System> sensors = unit.getSystemsOfClass(Sensors.class, true);
		for (Iterator<System> iterator = sensors.iterator(); iterator.hasNext();) {
			UnitTester.outputSystems(iterator.next().getParent().getSystemsOfClass(DamageAbsorbingSystem.class).iterator());
		}

		Logger.info("");
		
		Logger.info("Systems of class DamageAbsorbingSystem for Thruster:");
		List<System> thrusters = unit.getSystemsOfClass(Thruster.class, true);
		for (Iterator<System> iterator = thrusters.iterator(); iterator.hasNext();) {
			UnitTester.outputSystems(iterator.next().getParent().getSystemsOfClass(DamageAbsorbingSystem.class).iterator());
			Logger.info("------------------");
		}
		
		Logger.info("");

		Logger.info("Systems of type Energy Diffuser:");
		UnitTester.outputSystems(unit.getSystemsOfType("Energy Diffuser", true).iterator());		
	}

}
