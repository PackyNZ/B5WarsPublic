/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * !!--- work in progress ---!!
 * A Catapult is a special type of external hangar designed to hold and service super-heavy 
 * fighters 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Catapult extends System {

	// TODO fields 
	// TODO fighters present
	// TODO launch direction - arc??
	// TODO fighter type restriction
	
	private static final long serialVersionUID = -7850050085646795722L;

	/**
	 * @param damageBoxes amount of damage the catapult can sustain before being destroyed
	 * @param armor       amount of armor protecting the catapult
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the catapult, may be null
	 * @param properties  additional properties 
	 */
	public Catapult(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name);
		
		// hull allowed
	}

	/**
	 * @param damageBoxes amount of damage the catapult can sustain before being destroyed
	 * @param armor       amount of armor protecting the catapult
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the catapult, may be null
	 */
	public Catapult(final int damageBoxes, final int armor, final Arc arc, final String name) {
		super(damageBoxes, armor, arc, name);
	}
	
	public String getType() {
		return "Catapult";
	}

	protected void resolveCriticalHit(final int roll) {
		// no critical hits for catapults
	}	
	
	protected void adjustSystem() {
		// TODO
	}
	
	public int getRecognitionOrder() {
		return 710;
	}
	
}
