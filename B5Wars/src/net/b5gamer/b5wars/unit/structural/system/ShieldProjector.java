/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * !!--- work in progress ---!!
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class ShieldProjector extends GraviticShield {

	private static final long serialVersionUID = -4518652834159789203L;

	/**
	 * @param damageBoxes not used
	 * @param armor       amount of armor protecting the shield
	 * @param arc         arc for incoming fire and arc covered by the shield
	 * @param number      the weapon number
	 * @param properties  additional properties 
	 */
	public ShieldProjector(final int damageBoxes, final int armor, final Arc arc, final String number,
			final Properties properties) {
		this(armor, arc, Integer.parseInt(number), PropertyReader.getInteger(properties, "shieldFactor"));
	}
	
	/**
	 * @param armor        amount of armor protecting the shield
	 * @param arc          arc for incoming fire and arc covered by the shield  
	 * @param number       the weapon number
	 * @param shieldFactor strength of the shield
	 */
	public ShieldProjector(final int armor, final Arc arc, final int number, final int shieldFactor) {
		super(9, armor, arc, number, 4, shieldFactor);
	}
	
	public String getType() {
		return "Shield Projector";
	}
	
	protected void resolveCriticalHit(final int roll) {
		// TODO
	}	
	
	protected void adjustSystem() {
		// TODO
	}

}
