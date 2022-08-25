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
 * A Trait indicates an inherent ability a unit has to act in a different way or perform a
 * specific function
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Trait implements Serializable {

	private static final long serialVersionUID = -925549693990747105L;

	private static final ArrayList<Trait> traits = new ArrayList<Trait>(0); // all traits
	
	/**
	 * unit is an agile ship
	 */
	public static final Trait AGILE = new Trait("Agile Ship", "Agile Ship");

	/**
	 * unit is atmospheric capable
	 */
	public static final Trait ATMOSPHERIC = new Trait("Atmospheric Capable", "Atmospheric Capable");

	/**
	 * unit is an ELINT Ship
	 */
	public static final Trait ELINT = new Trait("ELINT Ship", "ELINT Ship");

	/**
	 * unit has a gravitic drive system
	 */
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
	
	private final String name;        // name of the trait
	private final String description; // description of the trait

	/**
	 * a list of all traits
	 * 
	 * @return a list of all traits
	 */
	private static final List<Trait> getTraits() {
		return traits;
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

        for (Iterator<Trait> iterator = traits.iterator(); iterator.hasNext();) {
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
	public Trait(final String name) {
		this(name, null);
	}
	
	/**
	 * @param name        name of the trait
	 * @param description description of the trait, may be null
	 */
	public Trait(final String name, final String description) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 
        if (getTrait(name) != null) {
            throw new IllegalArgumentException("Trait with same name already exists!");
        }
		
		this.name        = name;
		this.description = description;

		getTraits().add(this);
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
	 * the description of the trait
	 * 
	 * @return the description of the trait
	 */	
	public String getDescription() {
		return description;
	}
	
	@Override
	public final String toString() {
        if ((getDescription() != null) && (getDescription().length() > 0)) {
        	return getDescription();
        } else {
        	return getName();
        }
	}	
	
}
