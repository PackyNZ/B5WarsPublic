package net.b5gamer.b5wars.test;

import java.util.List;
import java.util.Random;

import net.b5gamer.b5wars.ui.logging.Logger;
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

public class HitLocationTester {

	private static final Random random = new Random();
	
	public static void main(String[] args) {
		if (args.length < 4) {
			Logger.info("Usage: HitLocationTester <number of volleys> <damage per volley> <target unit class> <angle of attack>");
			java.lang.System.exit(0);
		}
		int numberOfVolleys = Integer.parseInt(args[0]);
		int damagePerVolley = Integer.parseInt(args[1]);	
		StructuralUnit targetUnit = null;
		try {
			Object object = Class.forName(args[2]).getDeclaredConstructor().newInstance();
			if (object instanceof StructuralUnit) {
				targetUnit = (StructuralUnit) object;

//				if (targetUnit.hasTrait(Trait.SAUCER)) {
//		            throw new IllegalArgumentException("Target unit cannot have the " + Trait.SAUCER + " " + Trait.class.getSimpleName());
//				}
			} else {
	            throw new IllegalArgumentException("Target unit must be a valid " + StructuralUnit.class.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		int angle = Integer.parseInt(args[3]);

		Logger.info("Resolving " + numberOfVolleys + " volleys of " + damagePerVolley + " damage each against a " + targetUnit.getName() + " from a " + angle + " degree angle");
		List<Section> targetSections = targetUnit.getSectionsWithinArc(angle);
		if (targetSections.size() > 1) {
			Logger.info("<-- found " + targetSections.size() + " sections, user selection not implemented yet so selecting at random -->");
		}
		Section targetSection = targetSections.get(random.nextInt(targetSections.size()));
		Logger.info("Resolving attacks against " + targetSection.getName());

		Logger.info("");

		Logger.info("-------------------------------- Hit resolution --------------------------------");
		
		for (int index = 0; index < numberOfVolleys; index++) {
			Logger.info("Volley " + (index + 1) + " of " + damagePerVolley + " damage...");
			hitLocation(targetUnit, targetSection, damagePerVolley);
			
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
	
	public static final void hitLocation(final StructuralUnit targetUnit, final Section targetSection, int damage) {
		int roll = Dice.d20.roll();
		HitLocation hitLocation = targetSection.getHitLocationChart().determineLocationHit(roll);

		if (hitLocation instanceof SystemHitLocation) {
			Logger.info("Location " + roll + " rolled for " + targetSection.getName() + ", " + ((SystemHitLocation) hitLocation).getSystemType() + " hit");

			List<System> targetSystems = targetSection.getTargetSystemsOfType(((SystemHitLocation) hitLocation).getSystemType());
			// damage system
			if ((targetSystems != null && targetSystems.size() > 0)) {
				if (targetSystems.size() > 1) {
					Logger.info("<-- found " + targetSystems.size() + " systems, user selection not implemented yet so selecting at random -->");
				}

				damage = applyDamage(targetSystems.get(random.nextInt(targetSystems.size())), damage);
			} else {
				Logger.info("No " + ((SystemHitLocation) hitLocation).getSystemType() + " remains!");
			}
			
			// damage sections structure
			if ((damage > 0) && (targetSection instanceof StructuralSection)) {
				System sectionStructure = ((StructuralSection) targetSection).getStructure();
				Logger.info("Applying overkill to " + targetSection.getName() + " structure");
				damage = applyDamage(sectionStructure, damage);

				if (sectionStructure.isDestroyed()) {
					Logger.info(targetSection.getName() + " destroyed!");
				}
			}
			
			// damage primary structure
			if (damage > 0) {
				// TODO check primary hit or overkill
				System primaryStructure = ((StructuralSection) targetUnit.getSection(Section.PRIMARY)).getStructure();
				Logger.info("Applying overkill to " + Section.PRIMARY + " structure");
				applyDamage(primaryStructure, damage);
				
				if (primaryStructure.isDestroyed()) {
					Logger.info(targetUnit.getGivenName() + " destroyed!");
				}
			}				
		} else if (hitLocation instanceof WeaponHitLocation) {
			Logger.info("Location " + roll + " rolled for " + targetSection.getName() + ", " + ((WeaponHitLocation) hitLocation).getWeaponType() + " hit");

			List<Weapon> targetSystems = targetSection.getTargetWeaponsOfType(((WeaponHitLocation) hitLocation).getWeaponType());
			// damage weapon system
			if ((targetSystems != null && targetSystems.size() > 0)) {
				if (targetSystems.size() > 1) {
					Logger.info("<-- found " + targetSystems.size() + " weapons, user selection not implemented yet so selecting at random -->");
				}

				damage = applyDamage(targetSystems.get(random.nextInt(targetSystems.size())), damage);
			} else {
				Logger.info("No " + ((WeaponHitLocation) hitLocation).getWeaponType() + " remains!");
			}
			
			// damage sections structure
			if ((damage > 0) && (targetSection instanceof StructuralSection)) {
				System sectionStructure = ((StructuralSection) targetSection).getStructure();
				Logger.info("Applying overkill to " + targetSection.getName() + " structure");
				damage = applyDamage(sectionStructure, damage);

				if (sectionStructure.isDestroyed()) {
					Logger.info(targetSection.getName() + " destroyed!");
				}
			}
			
			// damage primary structure
			if (damage > 0) {
				// TODO check primary hit or overkill
				System primaryStructure = ((StructuralSection) targetUnit.getSection(Section.PRIMARY)).getStructure();
				Logger.info("Applying overkill to " + Section.PRIMARY + " structure");
				applyDamage(primaryStructure, damage);
				
				if (primaryStructure.isDestroyed()) {
					Logger.info(targetUnit.getGivenName() + " destroyed!");
				}
			}				
		} else if (hitLocation instanceof SectionHitLocation) {
			Logger.info("Location " + roll + " rolled for " + targetSection.getName() + ", " + ((SectionHitLocation) hitLocation).getSectionName() + " hit");

			Section section = targetUnit.getSection(((SectionHitLocation) hitLocation).getSectionName());
			if (section != null) {
				hitLocation(targetUnit, section, damage);				 
			} else {
				Logger.error("Unknown section");
			}			
		} else {
			Logger.error("Unknown hit location class");
		}		
	}
	
	public static final int applyDamage(final System system, final int damage) {
		int result = 0;
		
        if (system == null) {
            throw new IllegalArgumentException("System cannot be null");
        } 
        
        if (system.isDestroyed()) {
        	result = damage;
        } else {
			result = system.applyDamage(damage - system.getArmor());
        	int damageTaken = damage - (result + system.getArmor());

    		Logger.info(system.getFullName() + " takes " + damageTaken + " damage (Armor " + system.getArmor() + "/" + system.getBaseArmor() + ", Damage " + system.getDamageBoxes() + "/" + system.getBaseDamageBoxes() + ")" + (system.isDestroyed() ? " and is destroyed! " : ", ") + result + " overkill damage");
        }		
        
        return result;
	}

}
