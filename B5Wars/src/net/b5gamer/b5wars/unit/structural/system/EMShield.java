/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * A shield based on electromagnetic technology which generates an electromagnetic screen
 * that partially surrounds the vessel
 *  
 * @author Alex Packwood (aka PackyNZ)
 */
public class EMShield extends Shield {

	private static final long serialVersionUID = 2082977283509604277L;

	/**
	 * @param damageBoxes not used
	 * @param armor       amount of armor protecting the shield
	 * @param arc         arc for incoming fire and arc covered by the shield
	 * @param number      the weapon number
	 * @param properties  additional properties 
	 */
	public EMShield(final int damageBoxes, final int armor, final Arc arc, final String number,
			final Properties properties) {
		this(armor, arc, Integer.parseInt(number), PropertyReader.getInteger(properties, "shieldFactor"));
	}
	
	/**
	 * @param damageBoxes  amount of damage the shield can sustain before being destroyed
	 * @param armor        amount of armor protecting the shield
	 * @param arc          arc for incoming fire and arc covered by the shield 
	 * @param number       the weapon number
	 * @param power        amount of power required for the shield to function
	 * @param shieldFactor strength of the shield
	 */	
	protected EMShield(final int damageBoxes, final int armor, final Arc arc, final int number, 
			final int power, final int shieldFactor) {
		super(damageBoxes, armor, arc, number, power, shieldFactor);
	}
	
	/**
	 * @param armor        amount of armor protecting the shield
	 * @param arc          arc for incoming fire and arc covered by the shield  
	 * @param number       the weapon number
	 * @param shieldFactor strength of the shield
	 */
	public EMShield(final int armor, final Arc arc, final int number, final int shieldFactor) {
		super(6, armor, arc, number, 0, shieldFactor);
	}

	public String getType() {
		return "EM Shield";
	}
	
}
