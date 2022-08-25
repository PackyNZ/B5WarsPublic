/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.game;

import java.util.Collections;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.unit.MobileUnit;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.dice.Dice;

/**
 * This class contains methods for handling initiative for units
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public final class InitiativeHandler {
	
	private static final InitiativeComparator INITIATIVE_COMPARATOR = new InitiativeComparator();
	
	private InitiativeHandler() {
	}
	
	/**
	 * determine initiative for the given units
	 * 
	 * @param units units to determine initiative for
	 */		
	public static void determineInitiative(List<Unit> units) {
		for (Iterator<Unit> iterator = units.iterator(); iterator.hasNext();) {
			determineInitiative(iterator.next());						
		}		
	}
		
	/**
	 * determine initiative for the given unit
	 * 
	 * @param unit unit to determine initiative for
	 */		
	public static void determineInitiative(Unit unit) {
		// roll for initiative
		int initiative = Dice.d20.roll(); 
		
		// adjust for built-in initiative modifier
		initiative += unit.getInitiativeModifier();

		// TODO adjust for land/launch procedures and being launched
				
		// adjust for slowness
		if (unit instanceof MobileUnit) {
			int speed = ((MobileUnit) unit).getSpeed();
			speed = (speed >= 0) ? speed : speed * -1;
			if (speed < 5) { 
				initiative -= 2 * (5 - speed);
			}
		}
		
		// TODO adjust for command & control
		// TODO check active command and control
		// TODO init penalty if all C&Cs destroyed?
//		CommandAndControl commandAndControl = ((StructuralUnit) unit).getSystemsOfClass(CommandAndControl.class);
//		if (commandAndControl != null) {
//			initiative -= commandAndControl.getInitiativePenalty();
//		}
		
		// TODO adjust for combat effects

		// adjust for voluntary penalty - ignoring!
		
		unit.setInitiative(initiative);		
	}
	
	/**
	 * organise the given list of units by order of ascending initiative, with ties being 
	 * resolved by initiative modifier (highest wins), or randomly if both have the same 
	 * initiative modifier
	 * 
	 * @param units units to order by ascending initiative
	 */		
	public static void orderByInitiative(List<Unit> units) {
		Collections.sort(units, INITIATIVE_COMPARATOR);
	}	
	
}
