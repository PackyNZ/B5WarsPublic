package net.b5gamer.b5wars.game;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.unit.Unit;

/**
 * A Faction indicates a specific race or group that can be played in a game 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Faction {

	private static final List<Faction> FACTIONS = new ArrayList<Faction>(0); // all factions
	
	private final String     name; // name of the faction
	private final List<Unit> units = new ArrayList<Unit>(0); // all units belonging to the faction

	/**
	 * list of all factions
	 * 
	 * @return list of all factions
	 */
	protected static final List<Faction> getFactions() {
		return FACTIONS;
	}
	
    /**
     * iterator of all factions
     * 
	 * @return iterator of all factions
	 */
	public static final Iterator<Faction> getFactionsIterator() {
		return getFactions().iterator();
	}

    /**
     * find and return a faction using a given name
     * 
     * @param  name name of the faction to return
     * @return      the faction, or null if a faction with the given name doesn't exist
     */
    public static final Faction getFaction(final String name) {
    	Faction result = null;

    	for (Iterator<Faction> iterator = getFactions().iterator(); iterator.hasNext();) {
    		Faction faction = iterator.next();

            if (faction.getName().equalsIgnoreCase(name)) {
                result = faction;
                break;
            }                
    	} 

        return result;
    }
	
	/**
	 * @param name name of the faction
	 */
	public Faction(final String name) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 
        if (getFaction(name) != null) {
            throw new IllegalArgumentException("Faction with same name already exists!");
        }

    	this.name = name;
    	
		getFactions().add(this);
	}

	/**
	 * the name of the faction
	 * 
	 * @return the name of the faction
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * list of all units for the faction
	 * 
	 * @return list of all units for the faction
	 */
	protected List<Unit> getUnits() {
		return units;
	}
	
	/**
	 * iterator of all units for the faction
	 * 
	 * @return iterator of all units for the faction
	 */
	public Iterator<Unit> getUnitIterator() {
		return getUnits().iterator();
	}
	
    /**
     * add a unit to the faction
     * 
     * @param unit unit to add
     */
    public void addUnit(final Unit unit) {
		if (unit == null) {
            throw new IllegalArgumentException("unit cannot be null");
        } 
    	
		getUnits().add(unit);
    }
    
    /**
     * remove a unit from the faction
     * 
     * @param unit unit to remove
     */
    public void removeUnit(final Unit unit) {
    	getUnits().remove(unit);
    }
    
	/**
     * find and return a unit by name
     * 
     * @param  name name of the unit to return
     * @return      the unit, or null if a unit with the specified name doesn't exist
     */
    public Unit getUnit(final String name) {
    	Unit result = null;

    	for (Iterator<Unit> iterator = getUnits().iterator(); iterator.hasNext();) {
    		Unit unit = iterator.next();

            if (unit.getName().equalsIgnoreCase(name)) {
                result = unit;
                break;
            }                
    	} 

        return result;
    }
       
}
