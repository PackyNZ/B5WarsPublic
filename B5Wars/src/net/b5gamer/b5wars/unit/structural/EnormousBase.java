/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural;

import net.b5gamer.b5wars.unit.ServiceHistory;
import net.b5gamer.b5wars.unit.Size;
import net.b5gamer.b5wars.unit.Trait;
import net.b5gamer.util.Properties;

/**
 * An EnormousBase is an immobile outpost such as a logistics center, docking port, or 
 * defensive installation. 
 * <p>
 * <B>Note:</B> Normally the armor and arc of weapons and systems (excluding gravitic shields),
 * would be the same as the side/section of the base they belong to. However since I 
 * gather this was originally done due to space restrictions on the SCS, these rules 
 * have not been automatically enforced in this implementation. Instead it is left up to
 * unit designers to comply or not as they see fit
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class EnormousBase extends StructuralUnit {

	private static final long serialVersionUID = 7476665962115992838L;

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
	public EnormousBase(final String name, final String model, final String hull, final int version, 
			final String author, final String source, final int pointValue, final int rammingFactor, 
			final int fwdAftDefense, final int stbPortDefense, final int initiativeModifier, 
			final ServiceHistory serviceHistory, final Trait[] traits,	final Properties properties) {
		this(name, model, hull, version, author, source, pointValue, rammingFactor, fwdAftDefense, 
				stbPortDefense, initiativeModifier, serviceHistory, traits);	
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
	 */
	public EnormousBase(final String name, final String model, final String hull, final int version, 
			final String author, final String source, final int pointValue, final int rammingFactor, 
			final int fwdAftDefense, final int stbPortDefense, final int initiativeModifier, 
			final ServiceHistory serviceHistory, final Trait[] traits) {
		super(name, model, hull, version, author, source, pointValue, rammingFactor, fwdAftDefense, 
				stbPortDefense, initiativeModifier, serviceHistory, traits);	
	}
		
	public String getType() {
		return "Enormous Base";
	}
	
	public Size getSize() {
		return Size.ENORMOUS;
	}

	protected int getMaximumNumberOfSections() {
		return 13; // e.g. Vree Tyllz Sector Trading Post
	}
		
}
