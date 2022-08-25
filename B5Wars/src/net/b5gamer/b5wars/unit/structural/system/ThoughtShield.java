/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & © Warner Bros.
 * Java design and implementation © Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

public class ThoughtShield extends Weapon {

	private static final long serialVersionUID = 8265521594467801580L;

	public ThoughtShield(final int damageBoxes, final int armor, final Arc arc, final String number,
			final Properties properties) {
		this(damageBoxes, armor, arc, Integer.parseInt(number), PropertyReader.getInteger(properties, "power"));
	}
	
	public ThoughtShield(final int damageBoxes, final int armor, final Arc arc, final int number,
			final int power) {
		super(damageBoxes, armor, arc, String.valueOf(number), power);
	}

	public String getType() {
		return "Thought Shield";
	}
	
	@Override
	public int getRecognitionOrder() {
		return 1110;
	}
	
}
