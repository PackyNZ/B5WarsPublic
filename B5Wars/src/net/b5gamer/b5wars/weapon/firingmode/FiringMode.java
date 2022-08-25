package net.b5gamer.b5wars.weapon.firingmode;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.structural.system.Weapon;
import net.b5gamer.map.Direction;

/**
 * !!--- work in progress ---!!
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public abstract class FiringMode {

	// -!-

	private static final ArrayList<FiringMode> firingModes = new ArrayList<FiringMode>(0); // all firing modes

	// TODO enum??
	public static final FiringMode FLASH     = new FlashMode();
	public static final FiringMode LINKED    = new LinkedMode();
	public static final FiringMode PIERCING  = new PiercingMode();
	public static final FiringMode PULSE     = new PulseMode();
	public static final FiringMode RAKING    = new RakingMode();
	public static final FiringMode STANDARD  = new StandardMode();
	public static final FiringMode SUSTAINED = new SustainedMode();
	
	private final String name; // name of the firing mode
	private final String code; // code of the firing mode
	
	/**
	 * a list of all firing modes
	 * 
	 * @return a list of all firing modes
	 */
	protected static final List<FiringMode> getFiringModes() {
		return firingModes;
	}
	
    /**
     * an iterator of all firing modes
     * 
	 * @return an iterator of all firing modes
	 */
	public static final Iterator<FiringMode> getFiringModeIterator() {
		return getFiringModes().iterator();
	}
	
    /**
     * find and return a firing mode with a given name
     *
     * @param  name name of the firing mode to return
     * @return      the firing mode, or null if a firing mode with the given name doesn't exist
     */
    public static final FiringMode getFiringModeByName(final String name) {
    	FiringMode result = null;

    	for (Iterator<FiringMode> iterator = getFiringModes().iterator(); iterator.hasNext();) {
    		FiringMode mode = iterator.next();

            if (mode.getName().equalsIgnoreCase(name)) {
                result = mode;
                break;
            }                
    	} 

        return result;
    }
    
    /**
     * find and return a firing mode with a given code
     *
     * @param  code code of the firing mode to return
     * @return      the firing mode, or null if a firing mode with the given code doesn't exist
     */
    public static final FiringMode getFiringModeByCode(final String code) {
    	FiringMode result = null;

    	for (Iterator<FiringMode> iterator = getFiringModes().iterator(); iterator.hasNext();) {
    		FiringMode mode = iterator.next();

            if (mode.getCode().equalsIgnoreCase(code)) {
                result = mode;
                break;
            }                
    	} 

        return result;
    }    
    
	/**
	 * @param name name of the firing mode
	 * @param code code of the firing mode
	 */
	protected FiringMode(final String name, final String code) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 
        if (getFiringModeByName(name) != null) {
            throw new IllegalArgumentException("Firing Mode with same name already exists!");
        }
        if ((code == null) || !(code.length() > 0)) {
            throw new IllegalArgumentException("code cannot be null or blank");
        } 
        if (getFiringModeByCode(code) != null) {
            throw new IllegalArgumentException("Firing Mode with same code already exists!");
        }

		this.name = name;
		this.code = code;

		firingModes.add(this);		
	}
	
	/**
	 * the name
	 * 
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * the code
	 * 
	 * @return the code
	 */
	public final String getCode() {
		return code;
	}
	
	/**
	 * ...
	 * 
	 * @param weapon    weapon to resolve fire of against the target
	 * @param direction direction of weapon fire
	 * @param target    the unit that was hit
	 */
	public abstract void resolveHit(final Weapon weapon, final Direction direction, final Unit target);

}
