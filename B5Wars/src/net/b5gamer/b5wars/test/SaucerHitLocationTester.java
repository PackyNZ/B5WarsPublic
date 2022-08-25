package net.b5gamer.b5wars.test;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.b5wars.unit.structural.Ship;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocation;
import net.b5gamer.b5wars.unit.structural.hitlocation.SectionHitLocation;
import net.b5gamer.b5wars.unit.structural.hitlocation.SystemHitLocation;
import net.b5gamer.b5wars.unit.structural.hitlocation.WeaponHitLocation;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.section.StructuralSection;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.b5wars.unit.structural.system.Weapon;
import net.b5gamer.dice.Dice;

public class SaucerHitLocationTester {

	private static final Random random = new Random();
	
	public static void main(String[] args) {
		if (args.length < 4) {
			Logger.info("Usage: SaucerHitLocationTester <number of volleys> <damage per volley> <target unit class> <angle of attack>");
			java.lang.System.exit(0);
		}
		
		int numberOfVolleys = Integer.parseInt(args[0]);
		int damagePerVolley = Integer.parseInt(args[1]);	
		
		StructuralUnit targetUnit = null;
		try {
			Object object = Class.forName(args[2]).getDeclaredConstructor().newInstance();
			if (object instanceof StructuralUnit) {
				targetUnit = (Ship) object;
				
				if (targetUnit.getGeneralHitLocationChart() == null) {
		            throw new IllegalArgumentException("Target unit must have a general hit loction chart");
				}
			} else {
	            throw new IllegalArgumentException("Target unit must be a valid " + StructuralUnit.class.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		int angle = Integer.parseInt(args[3]);
			
		
		Logger.info("Resolving " + numberOfVolleys + " volleys of " + damagePerVolley + " damage each against a " + targetUnit.getName() + " from a " + angle + " degree angle");

		Logger.info("");

		Logger.info("-------------------------------- Hit resolution --------------------------------");
		for (int index = 0; index < numberOfVolleys; index++) {
			Logger.info("Volley " + (index + 1) + " of " + damagePerVolley + " damage...");
			hitLocation(targetUnit, damagePerVolley, angle);
			
			if (targetUnit.isDestroyed()) {
				Logger.info("");
				break;
			}
			
			Logger.info("");
		}

		// check for critical hits
		Logger.info("---------------------------- Critical Hit resolution ----------------------------");
		targetUnit.handleEndOfTurnActions();

		Logger.info("");
		
		Logger.info("---------------------------------- Ship Status ----------------------------------");
		Logger.info(targetUnit.toString());
		Logger.info("------------------------------------------------------------");
		UnitTester.outputSections(targetUnit);
	}
	
	private static final void hitLocation(final StructuralUnit targetUnit, int damage, int angle) {
		int roll = Dice.d20.roll();
		HitLocation hitLocation = targetUnit.getGeneralHitLocationChart().determineLocationHit(roll);

		if (hitLocation instanceof SystemHitLocation) {
//			Logger.info("Location " + roll + " rolled, " + ((SystemHitLocation) hitLocation).getSystemClass().getSimpleName() + " hit");

			// TODO don't use arc if primary
			List<System> targetSystems = getSystemsOfType(targetUnit, ((SystemHitLocation) hitLocation).getSystemType(), angle);
			// damage system
			if ((targetSystems != null && targetSystems.size() > 0)) {
				if (targetSystems.size() > 1) {
					Logger.info("<-- found " + targetSystems.size() + " systems within arc, user selection not implemented yet so selecting at random -->");
				}
				
				damage = HitLocationTester.applyDamage(targetSystems.get(random.nextInt(targetSystems.size())), damage);
			} else {
				Logger.info("No " + ((SystemHitLocation) hitLocation).getSystemType() + " remains!");
			}
			
			// damage sections structure
			if (damage > 0) {
				Logger.info("Applying overkill to structure");

				// TODO don't use arc if primary
				List<System> targetStructure = getSystemsOfType(targetUnit, "Structure", angle);
				
				if ((targetStructure != null && targetStructure.size() > 0)) {
					if (targetStructure.size() > 1) {
						Logger.info("<-- found " + targetStructure.size() + " structure blocks within arc, user selection not implemented yet so selecting at random -->");
					}
					
					damage = HitLocationTester.applyDamage(targetStructure.get(random.nextInt(targetStructure.size())), damage);
				} else {
					Logger.info("No Structure remains!");
				}
			}
			
			// damage primary structure
			if (damage > 0) {
				// TODO check primary hit or overkill
				System primaryStructure = ((StructuralSection) targetUnit.getSection(Section.PRIMARY)).getStructure();
				Logger.info("Applying overkill to " + Section.PRIMARY + " structure");
				HitLocationTester.applyDamage(primaryStructure, damage);
				
				if (primaryStructure.isDestroyed()) {
					Logger.info(targetUnit.getName() + " destroyed!");
				}
			}				
		} else if (hitLocation instanceof WeaponHitLocation) {
//			Logger.info("Location " + roll + " rolled, " + ((WeaponHitLocation) hitLocation).getWeaponClass().getSimpleName() + " hit");

			// TODO don't use arc if primary
			List<System> targetSystems = getWeaponsOfType(targetUnit, ((WeaponHitLocation) hitLocation).getWeaponType(), angle);
			// damage weapon system
			if ((targetSystems != null && targetSystems.size() > 0)) {
				if (targetSystems.size() > 1) {
					Logger.info("<-- found " + targetSystems.size() + " weapons within arc, user selection not implemented yet so selecting at random -->");
				}

				damage = HitLocationTester.applyDamage(targetSystems.get(random.nextInt(targetSystems.size())), damage);
			} else {
				Logger.info("No " + ((WeaponHitLocation) hitLocation).getWeaponType() + " remains!");
			}
			
			// damage sections structure
			if (damage > 0) {
				Logger.info("Applying overkill to structure");

				// TODO don't use arc if primary
				List<System> targetStructure = getSystemsOfType(targetUnit, "Structure", angle);
				
				if ((targetStructure != null && targetStructure.size() > 0)) {
					if (targetStructure.size() > 1) {
						Logger.info("<-- found " + targetStructure.size() + " structure blocks within arc, user selection not implemented yet so selecting at random -->");
					}
					
					damage = HitLocationTester.applyDamage(targetStructure.get(random.nextInt(targetStructure.size())), damage);
				} else {
					Logger.info("No Structure remains!");
				}
			}
			
			// damage primary structure
			if (damage > 0) {
				// TODO check primary hit or overkill
				System primaryStructure = ((StructuralSection) targetUnit.getSection(Section.PRIMARY)).getStructure();
				Logger.info("Applying overkill to " + Section.PRIMARY + " structure");
				HitLocationTester.applyDamage(primaryStructure, damage);
				
				if (primaryStructure.isDestroyed()) {
					Logger.info(targetUnit.getGivenName() + " destroyed!");
				}
			}				
		} else if (hitLocation instanceof SectionHitLocation) {
//			Logger.info("Location " + roll + " rolled, " + ((SectionHitLocation) hitLocation).getSectionName() + " hit");

			Section section = targetUnit.getSection(((SectionHitLocation) hitLocation).getSectionName());
			if (section != null) {
				HitLocationTester.hitLocation(targetUnit, section, damage);				 
			} else {
				Logger.error("Unknown section");
			}			
		} else {
			Logger.error("Unknown hit location class");
		}		
	}
	
	private static final List<System> getSystemsOfType(final StructuralUnit targetUnit, final String systemType, final int angle) {
		List<System> targetSystems = new ArrayList<System>(0);
		
		for (Iterator<Section> iterator = targetUnit.getSectionIterator(); iterator.hasNext();) {
			Section section = iterator.next();
			if (section != targetUnit.getPrimarySection()) {
				List<System> systems = section.getTargetSystemsOfType(systemType);
				for (Iterator<System> systemIterator = systems.iterator(); systemIterator.hasNext();) {
					System system = systemIterator.next();
					
					if ((system.getArc() != null) && (system.getArc().contains(angle))) {
						targetSystems.add(system);
					}
				}
			}
		}
		
		return targetSystems;
	}
	
	private static final List<System> getWeaponsOfType(final StructuralUnit targetUnit, final String weaponType, final int angle) {
		List<System> targetWeapons = new ArrayList<System>(0);
		
		for (Iterator<Section> iterator = targetUnit.getSectionIterator(); iterator.hasNext();) {
			Section section = iterator.next();
			if (section != targetUnit.getPrimarySection()) {
				List<Weapon> weapons = section.getTargetWeaponsOfType(weaponType);
				for (Iterator<Weapon> weaponIterator = weapons.iterator(); weaponIterator.hasNext();) {
					Weapon weapon = weaponIterator.next();
					
					if ((weapon.getArc() != null) && (weapon.getArc().contains(angle))) {
						targetWeapons.add(weapon);
					}
				}
			}
		}
		
		return targetWeapons;
	}	
	
}
