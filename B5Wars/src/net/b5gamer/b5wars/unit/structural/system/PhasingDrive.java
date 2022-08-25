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
public class PhasingDrive extends HyperspaceDrive {

	private static final long serialVersionUID = 3732274376280622533L;

	/**
	 * @param damageBoxes amount of damage the phasing drive can sustain before being destroyed
	 * @param armor       amount of armor protecting the phasing drive
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the phasing drive, may be null
	 * @param properties  additional properties 
	 */
	public PhasingDrive(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "power"),
				PropertyReader.getInteger(properties, "jumpDelay"));
	}

	/**
	 * @param damageBoxes amount of damage the phasing drive can sustain before being destroyed
	 * @param armor       amount of armor protecting the phasing drive
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the phasing drive, may be null
	 * @param power       amount of power required for the phasing drive to function
	 * @param jumpDelay   number of turns delay between closing and opening a jump point
	 */
	public PhasingDrive(final int damageBoxes, final int armor, final Arc arc, final String name,
			final int power, final int jumpDelay) {
		super(damageBoxes, armor, arc, name, power, jumpDelay);
	}

	public String getType() {
		return "Phasing Drive";
	}
	
	protected void resolveCriticalHit(final int roll) {
		// TODO
	}	

	@Override
	public int getRecognitionOrder() {
		return 510;
	}
	
}
