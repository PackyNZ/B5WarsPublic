/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.small;

import net.b5gamer.b5wars.unit.ServiceHistory;
import net.b5gamer.b5wars.unit.Trait;
import net.b5gamer.util.PropertyReader;
import net.b5gamer.util.Properties;

/**
 * !!--- work in progress ---!!
 * A SuperHeavyFighter represents a single super heavy fighter 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class SuperHeavyFighter extends FighterFlight {

	private static final long serialVersionUID = -3429689945976620961L;

	/**
	 * @param name               unit name
	 * @param model              unit model
	 * @param hull               base hull the unit is built on
	 * @param version            version number
	 * @param author             author of the unit
	 * @param source             source of the unit
	 * @param pointValue         combat point cost of the unit
	 * @param rammingFactor      factor for ramming other units
	 * @param fwdAftDefense      defense when attacked from fore or aft
	 * @param stbPortDefense     defense when attacked from starboard or port
	 * @param initiativeModifier modifier to initiative
	 * @param serviceHistory     the units service history 
	 * @param traits             the units traits
	 * @param properties         additional properties 
	 */
	public SuperHeavyFighter(final String name, final String model, final String hull, final int version, 
			final String author, final String source, final int pointValue, final int rammingFactor, 
			final int fwdAftDefense, final int stbPortDefense, final int initiativeModifier, 
			final ServiceHistory serviceHistory, final Trait[] traits, final Properties properties) {
		this(name, model, hull, version, author, source, pointValue, rammingFactor, fwdAftDefense, 
				stbPortDefense, initiativeModifier, serviceHistory, traits,
				properties.getProperty("turnCost"), 
				properties.getProperty("turnDelay"), 
				PropertyReader.getInteger(properties, "accelDecelCost"),
				properties.getProperty("pivotCost"), 
				properties.getProperty("rollCost"), 	
				PropertyReader.getInteger(properties, "freeThrust"),	
				PropertyReader.getInteger(properties, "offensiveModifier"),	
				10, // PropertyReader.getInteger(properties, "damageBoxes"),	
				1,  // PropertyReader.getDouble(properties, "hangarBoxes"),	
				PropertyReader.getInteger(properties, "dropoutModifier"),	
				0);	
	}
	
	/**
	 * @param name               unit name
	 * @param model              unit model
	 * @param hull               base hull the unit is built on
	 * @param version            version number
	 * @param author             author of the unit
	 * @param source             source of the unit
	 * @param pointValue         combat point cost of the unit
	 * @param rammingFactor      factor for ramming other units
	 * @param fwdAftDefense      defense when attacked from fore or aft
	 * @param stbPortDefense     defense when attacked from starboard or port
	 * @param initiativeModifier modifier to initiative
	 * @param serviceHistory     the units service history 
	 * @param traits             the units traits
	 * @param turnCostFormatted  raw/unparsed thrust cost to make a turn 
	 * @param turnDelayFormatted raw/unparsed mandatory movement delay between making turns  
	 * @param accelDecelCost     thrust required to alter speed
	 * @param pivotCostFormatted raw/unparsed thrust required to make a pivot
	 * @param rollCostFormatted  raw/unparsed thrust required to make a roll
	 * @param freeThrust         available thrust per turn
	 * @param offensiveModifier  attack modifier 
	 * @param damageBoxes        amount of damage a fighter can sustain before being destroyed 
	 * @param hangarBoxes        number of boxes a single fighter occupies in a hangar
	 * @param dropoutModifier    modifier to dropout rolls
	 * @param numberOfFighters   number of fighters in this flight 
	 */
	public SuperHeavyFighter(final String name, final String model, final String hull, final int version, 
			final String author, final String source, final int pointValue, final int rammingFactor, 
			final int fwdAftDefense, final int stbPortDefense, final int initiativeModifier, 
			final ServiceHistory serviceHistory, final Trait[] traits, final String turnCostFormatted, 
			final String turnDelayFormatted, final int accelDecelCost, final String pivotCostFormatted, 
			final String rollCostFormatted, final int freeThrust, final int offensiveModifier, 
			final int damageBoxes, final double hangarBoxes, final int dropoutModifier, final int numberOfFighters) {
		super(name, model, hull, version, author, source, pointValue, rammingFactor, fwdAftDefense, stbPortDefense,
				initiativeModifier, serviceHistory, traits, turnCostFormatted, turnDelayFormatted, accelDecelCost, 
				pivotCostFormatted, rollCostFormatted, freeThrust, offensiveModifier, damageBoxes, hangarBoxes, 
				dropoutModifier, numberOfFighters);
	}

	public String getType() {
		return "Super-Heavy Fighters";
	}
		
	public int getMaximumNumberPerFlight() {
		return 1;
	}

	public int getJinkingLimit() {
		return 4;
	}
	
}
