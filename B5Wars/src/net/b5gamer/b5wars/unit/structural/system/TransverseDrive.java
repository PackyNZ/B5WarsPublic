/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & © Warner Bros.
 * Java design and implementation © Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

public class TransverseDrive extends Weapon {

	private static final long serialVersionUID = -902009291383006686L;

	public TransverseDrive(final int damageBoxes, final int armor, final Arc arc, final String number,
			final Properties properties) {
		this(damageBoxes, armor, arc, Integer.parseInt(number));
	}
	
	public TransverseDrive(final int damageBoxes, final int armor, final Arc arc, final int number) {
		super(damageBoxes, armor, arc, String.valueOf(number), 5);
	}

	public String getType() {
		return "Transverse Drive";
	}
	
	protected void resolveCriticalHit(final int roll) {
		// TODO
	}	
	
	protected void adjustSystem() {
		// TODO
	}
	
	@Override
	public int getRecognitionOrder() {
		return 1230;
	}
	
}
