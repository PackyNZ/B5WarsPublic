/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * A Trait indicates an inherent ability a unit has to act in a specific way or perform a
 * specific function
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class TraitNew implements Serializable {

	private static final long serialVersionUID = -8245845860836925688L;

	private static final ArrayList<Trait> traitNames = new ArrayList<Trait>(0); // all names of known traits
	
	//Atmospheric Capable (if both sides intact) - Drazi Sleekbird Assault Cruiser
	
	// Advanced Armor
	// Advanced Weapons (negates advanced armor)
//	-Advanced Armor - Shadow Scout
//	Hardened Adv. Armor - Kirishiac Lordship pg 248
	
	// 10 Marine Contingents - Raider Assault Sloop (V4)
	// Low Crew Training - Drazi Taileagle Escort Frigate
	// Minesweeper Bonus: +3 - Centauri Leevan Mine Sweeper
	// +4 Ramming Bonus

// Unreliable Ship
	
//	Agile Ship (without pod) - Llort Daggaden Penetrators
//	Accel Cost 3 without pods - Torata Heltaka Logistics Cruiser
//	*Turning in Reverse: add +1/3 to turn cost - Hyach Alichi Kav Stealth Cruiser
//	Continuously Pivoting - Mindrider Wheel of Thought
//	May Skin Dance - Torvalus Black Rapier
//	May Jink 2 levels (at pivot cost per level) - Torvalus Shrouded Saber
	
//	Triad Capital Ship - The Triad: Chaos: Demon
//	Walker Jump Ship - Walker Guideship
//	Primordial Shadow Ship - Shadow Battle Cruiser
//	Vorlon Ship Petals - Vorlon Strike Cruiser
	
//	Crew Specialists: 1 - Hyach Alichi Kav Stealth Cruiser
//	Other Specialists: 2
//	Hyach crew specialists (L2 pg 7, S7 pg 2)
//		Defense Specialist - Hyach Okath Kur Escort Frigate (V5)
//		Intelligence Specialist - Hyach Irokai Kal Command Gunship
//		Other Specialists: 2 - Hyach Irokai Kal Command Gunship
//		Stealth Specialist - Hyach Alichi Tal Infiltrator
	
	
	public static final Trait AGILE = new Trait("Agile Ship", "Agile Ship"); 
	public static final Trait ATMOSPHERIC = new Trait("Atmospheric Capable", "Atmospheric Capable"); 
	public static final Trait ELINT = new Trait("ELINT Ship", "ELINT Ship");
	public static final Trait GRAVITIC = new Trait("Gravitic Drive System", "Gravitic Drive System");
	/**
	 * unit has a jammer, however this trait should only be used for a 
	 * {@link net.b5gamer.b5wars.unit.small.SmallUnit} (e.g.&nbsp;fighters) 
	 * where the jammer cannot be damaged and/or cease functioning, otherwise the 
	 * {@link net.b5gamer.b5wars.unit.structural.system.Jammer} 
	 * {@link net.b5gamer.b5wars.unit.structural.system.System} should be used
	 * 
     * @see net.b5gamer.b5wars.unit.structural.system.System
     * @see net.b5gamer.b5wars.unit.structural.system.Jammer
	 */
	public static final Trait JAMMER = new Trait("Jammer");
//	/**
//	 * unit uses a general {@link net.b5gamer.b5wars.unit.structural.hitlocation.HitLocationChart}
//	 * and may select any {@link net.b5gamer.b5wars.unit.structural.system.System} of the targeted 
//	 * type regardless of {@link net.b5gamer.b5wars.unit.structural.section.Section} (excluding
//	 * the PRIMARY section), so long as the system is within arc of the incoming fire
//	 */
//	public static final Trait SAUCER = new Trait("Saucer");
	
	private final String name;  // name of the trait
	private final String value; // value for the trait, may be null

	/**
	 * a list of all traits
	 * 
	 * @return a list of all traits
	 */
	private static final List<Trait> getTraits() {
		return traitNames;
	}

    /**
     * an iterator of all traits
     * 
	 * @return an iterator of all traits
	 */
	public static final Iterator<Trait> getTraitIterator() {
		return getTraits().iterator();
	}	

    /**
     * find and return a trait with a given name
     * 
     * @param  name name of the trait to return
     * @return      the trait, or null if a trait with the given name doesn't exist
     */
    public static final Trait getTrait(final String name) {
    	Trait result = null;

        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 

        for (Iterator<Trait> iterator = traitNames.iterator(); iterator.hasNext();) {
    		Trait trait = iterator.next();
    		
            if (trait.getName().equalsIgnoreCase(name)) {
                result = trait;
                break;
            }                
    	} 
    	    	
        // TODO add trait??
        
    	return result;
    }
        
	/**
	 * @param name name of the trait
	 */
	public TraitNew(final String name) {
		this(name, null);
	}
	
	/**
	 * @param name  name of the trait
	 * @param value value for the trait, may be null
	 */
	public TraitNew(final String name, final String value) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 
		
		this.name  = name;
		this.value = value;
	}
	
	/**
	 * the name of the trait
	 * 
	 * @return the name of the trait
	 */
	public final String getName() {
		return name;
	}

	/**
	 * the value for the trait, may be null
	 * 
	 * @return the value for the trait, may be null
	 */	
	public String getValue() {
		return value;
	}
	
	@Override
	public final String toString() {
//        if ((getValue() != null) && (getValue().length() > 0)) {
//        	return getValue();
//        } else {
        	return getName();
//        }
	}	
	
}
