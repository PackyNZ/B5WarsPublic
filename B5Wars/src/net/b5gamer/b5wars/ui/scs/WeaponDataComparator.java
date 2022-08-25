package net.b5gamer.b5wars.ui.scs;

import java.util.Comparator;

import net.b5gamer.b5wars.unit.structural.system.Weapon;

public class WeaponDataComparator implements Comparator<Weapon> {

	public int compare(Weapon weapon1, Weapon weapon2) {
		int number1 = getNumber(weapon1);
		int number2 = getNumber(weapon2);
			
		if (number1 != number2) {
			return (number1 < number2) ? -1 : 1;
		} else if (weapon1.getPowerRequirement() != weapon2.getPowerRequirement()) {
			return (weapon1.getPowerRequirement() > weapon2.getPowerRequirement()) ? -1 : 1;
		} else {
			return (weapon1.getType().compareTo(weapon2.getType()) <= 0) ? -1 : 1;
		}
	}
	
	private int getNumber(Weapon weapon) {
		return ((weapon.getName() != null) && (weapon.getName().trim().length() > 0)) ? 
				Integer.parseInt(weapon.getName()) : 1000000;
	}

}
