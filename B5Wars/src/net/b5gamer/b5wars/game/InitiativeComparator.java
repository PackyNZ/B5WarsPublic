package net.b5gamer.b5wars.game;

import java.util.Comparator;
import java.util.Random;

import net.b5gamer.b5wars.unit.Unit;

/**
 * This class is used to compare the initiative of two units, with ties being resolved by 
 * initiative modifier (highest wins), or randomly if both have the same initiative modifier
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class InitiativeComparator implements Comparator<Unit> {

	private static final Random RANDOM = new Random();
	
	/**
	 * compare the initiative of two units, with ties being resolved by initiative modifier 
	 * (highest wins), or randomly if both have the same initiative modifier
	 * 
	 * @param  unit1 first unit to compare
	 * @param  unit2 second unit to compare
	 * @return       negative integer if unit1 has a lower initiative, positive integer if 
	 *               unit2 has a lower initiative, there are no ties
	 */	
	public int compare(Unit unit1, Unit unit2) {
		if (unit1.getInitiative() != unit2.getInitiative()) {
			return (unit1.getInitiative() < unit2.getInitiative()) ? -1 : 1;
		} else {
			if (unit1.getInitiativeModifier() != unit2.getInitiativeModifier()) {
				return (unit1.getInitiativeModifier() < unit2.getInitiativeModifier()) ? -1 : 1;
			} else {
				// make sure there is a 50/50 chance (not -1 or 0 vs. 1)
				int number = 0;
				while (number == 0) {
					number = RANDOM.nextInt(3) - 1;
				}
				return number;
			}	
		}	
	}

}
