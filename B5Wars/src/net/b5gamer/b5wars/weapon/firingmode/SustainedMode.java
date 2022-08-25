package net.b5gamer.b5wars.weapon.firingmode;

import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.structural.system.Weapon;
import net.b5gamer.map.Direction;

/**
 * !!--- work in progress ---!!
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public final class SustainedMode extends FiringMode {

	// -!-

	/**
	 *
	 */
	protected SustainedMode() {
		super("Sustained", "S");
	}

	@Override
	public void resolveHit(Weapon weapon, Direction direction, Unit target) {
		// TODO Auto-generated method stub
		
	}
	
}
