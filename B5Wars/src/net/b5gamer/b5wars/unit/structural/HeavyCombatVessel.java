/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural;

import net.b5gamer.b5wars.unit.ServiceHistory;
import net.b5gamer.b5wars.unit.Size;
import net.b5gamer.b5wars.unit.Trait;
import net.b5gamer.util.PropertyReader;
import net.b5gamer.util.Properties;

/**
 * A HeavyCombatVessel is a intermediate unit such as small destroyers, escorts, light
 * carriers, large frigates, and miscellaneous warships
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class HeavyCombatVessel extends Ship {

	private static final long serialVersionUID = -6207415168439960672L;

	/**
	 * DWR use only!
	 */
//	public HeavyCombatVessel() {
//		super();
//	}

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
	public HeavyCombatVessel(final String name, final String model, final String hull, final int version, 
			final String author, final String source, final int pointValue, final int rammingFactor,
			final int fwdAftDefense, final int stbPortDefense, final int initiativeModifier,
			final ServiceHistory serviceHistory, final Trait[] traits,	final Properties properties) {
		this(name, model, hull, version, author, source, pointValue, rammingFactor, fwdAftDefense, 
				stbPortDefense, initiativeModifier, serviceHistory, traits,
				properties.getProperty("turnCost"), 
				properties.getProperty("turnDelay"), 
				PropertyReader.getInteger(properties, "accelDecelCost"),
				properties.getProperty("pivotCost"), 
				properties.getProperty("rollCost"));	
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
	 */
	public HeavyCombatVessel(final String name, final String model, final String hull, final int version, 
			final String author, final String source, final int pointValue, final int rammingFactor, 
			final int fwdAftDefense, final int stbPortDefense,	final int initiativeModifier, 
			final ServiceHistory serviceHistory, final Trait[] traits,	final String turnCostFormatted, 
			final String turnDelayFormatted, final int accelDecelCost, final String pivotCostFormatted, 
			final String rollCostFormatted) {
		super(name, model, hull, version, author, source, pointValue, rammingFactor, fwdAftDefense, 
				stbPortDefense, initiativeModifier, serviceHistory, traits, turnCostFormatted, 
				turnDelayFormatted, accelDecelCost, pivotCostFormatted, rollCostFormatted);	
	}
		
	public String getType() {
		return "Heavy Combat Vessel";
	}

	public Size getSize() {
		return Size.LARGE;
	}
		
}
