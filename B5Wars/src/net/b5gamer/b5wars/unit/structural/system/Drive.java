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
public class Drive extends Engine {

	private static final long serialVersionUID = -4259849568169051101L;
	
	/**
	 * @param damageBoxes amount of damage the engine can sustain before being destroyed
	 * @param armor       amount of armor protecting the engine
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the engine, may be null
	 * @param properties  additional properties 
	 */
	public Drive(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "freeThrust"),
				parseEfficiencyPower(properties.getProperty("efficiency")), 
				parseEfficiencyThrust(properties.getProperty("efficiency")));
	}

	/**
	 * @param damageBoxes      amount of damage the engine can sustain before being destroyed
	 * @param armor            amount of armor protecting the engine
	 * @param arc              arc for incoming fire, may be null
	 * @param name             name of the engine, may be null
	 * @param freeThrust       amount of free thrust generated per turn
	 * @param efficiencyPower  amount of power required to generate extra thrust
	 * @param efficiencyThrust amount of extra thrust generated using additional power  
	 */
	public Drive(final int damageBoxes, final int armor, final Arc arc, final String name,
			final int freeThrust, final int efficiencyPower, final int efficiencyThrust) {
		super(damageBoxes, armor, arc, name, freeThrust, efficiencyPower, efficiencyThrust);
	}
		
	public String getType() {
		return "Drive";
	}
	
	@Override
	public int getRecognitionOrder() {
		return 410;
	}
	
}
