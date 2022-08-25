package net.b5gamer.b5wars.weapon.type;

import net.b5gamer.b5wars.unit.structural.system.Weapon;

/**
 * !!--- work in progress ---!!
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public final class Ionic extends WeaponType {

	// -!-

	/**
	 *
	 */
	protected Ionic() {
		super("Ionic", "I");
	}
	
	public final boolean isInterceptable() {
		return true;
	}

	@Override
	public void resolveHit(Weapon weapon, System target) {
		// TODO Auto-generated method stub
		
	}
	
}
