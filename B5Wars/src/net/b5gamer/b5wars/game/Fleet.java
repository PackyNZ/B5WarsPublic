package net.b5gamer.b5wars.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.unit.Unit;

public class Fleet implements Serializable {

	private static final long serialVersionUID = -6716527717451653848L;
	
	private       String     name;                          // name of the fleet
	private final List<Unit> units = new ArrayList<Unit>(); // units in the fleet
	
	public Fleet(String name) {
        if ((name == null) || !(name.trim().length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }
        
        this.name = name;
	}

	/**
	 * the name of the fleet
	 * 
	 * @return the name of the fleet
	 */
	public String getName() {
		return name;
	}

	/**
	 * the name of the fleet
	 * 
	 * @param name the name of the fleet
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * the units in the fleet
	 * 
	 * @return the units in the fleet
	 */	
	public List<Unit> getUnits() {
		return units; // TODO make protected?
	}
	 
	/**
	 * iterator of all units in the fleet
	 * 
	 * @return iterator of all units in the fleet
	 */
	public Iterator<Unit> getUnitIterator() {
		return getUnits().iterator();
	}
	
    /**
     * add a unit to the fleet
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
     * remove a unit from the fleet
     * 
     * @param unit unit to remove
     */
    public void removeUnit(final Unit unit) {
    	getUnits().remove(unit);
    }
    
	@Override
	public String toString() {
		return getName();
	}
	
}
