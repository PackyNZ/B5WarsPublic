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
 * A JumpEngine is a large generator capable of opening a jump point into hyperspace
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class JumpEngine extends HyperspaceDrive {

	private static final long serialVersionUID = -3380583524317806396L;

	/**
	 * @param damageBoxes amount of damage the jump engine can sustain before being destroyed
	 * @param armor       amount of armor protecting the jump engine
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the jump engine, may be null
	 * @param properties  additional properties 
	 */
	public JumpEngine(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "power"),
				PropertyReader.getInteger(properties, "jumpDelay"));
	}

	/**
	 * @param damageBoxes amount of damage the jump engine can sustain before being destroyed
	 * @param armor       amount of armor protecting the jump engine
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the jump engine, may be null
	 * @param power       amount of power required for the jump engine to function
	 * @param jumpDelay   number of turns delay between closing and opening a jump point
	 */
	public JumpEngine(final int damageBoxes, final int armor, final Arc arc, final String name,
			final int power, final int jumpDelay) {
		super(damageBoxes, armor, arc, name, power, jumpDelay);
	}
	
	public String getType() {
		return "Jump Engine";
	}

	protected void resolveCriticalHit(final int roll) {
		// no critical hits for jump drives
	}		

	@Override
	public int getRecognitionOrder() {
		return 500;
	}
	
}
