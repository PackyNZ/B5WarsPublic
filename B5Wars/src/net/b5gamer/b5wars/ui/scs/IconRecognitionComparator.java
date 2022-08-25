package net.b5gamer.b5wars.ui.scs;

import java.util.Comparator;

import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.b5wars.unit.structural.system.Weapon;

public class IconRecognitionComparator implements Comparator<System> {

	private final WeaponDataComparator WEAPON_COMPARATOR = new WeaponDataComparator();
	
	public int compare(System system1, System system2) {
		if (system1.getRecognitionOrder() != system2.getRecognitionOrder()) {
			return (system1.getRecognitionOrder() < system2.getRecognitionOrder()) ? -1 : 1;
		} else if ((system1 instanceof Weapon) && (system2 instanceof Weapon)) {
			return WEAPON_COMPARATOR.compare((Weapon) system1, (Weapon) system2);
		} else {
			return (system1.getType().compareTo(system2.getType()) <= 0) ? -1 : 1;
		}
	}

}
