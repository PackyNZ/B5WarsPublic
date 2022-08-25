/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.hitlocation;

import java.io.Serializable;

import net.b5gamer.util.Properties;

/**
 * A HitLocation represents a possible target location for incoming attacks
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public abstract class HitLocation implements Serializable {
	
	private static final long serialVersionUID = 1916521140070440209L;

	private final int from; // from value for the range used to determine whether this location is hit
	private final int to;   // to value for the range used to determine whether this location is hit
	
	/**
	 * @param from from value for the range used to determine whether this location is hit
	 * @param to   to value for the range used to determine whether this location is hit
	 */
	protected HitLocation(final int from, final int to) {
        if (!((from >= 1) && (from <= 20))) {
            throw new IllegalArgumentException("from must be between 1 and 20 inclusive");
        } 
        if (to < from) {
            throw new IllegalArgumentException("to must be equal to or greater than from");
        } 
        if (!((to >= 1) && (to <= 20))) {
            throw new IllegalArgumentException("to must be between 1 and 20 inclusive");
        } 

        this.from = from;
		this.to   = to;
	}
		
	/**
	 * the properties for the hit location
	 * 
	 * @return the properties for the hit location
	 */
	public Properties getProperties() {
		return null;
	}
	
	/**
	 * the from value for the range used to determine whether the location is hit
	 * 
	 * @return the from value for the range used to determine whether the location is hit
	 */
	public final int getFrom() {
		return from;
	}

	/**
	 * the to value for the range used to determine whether the location is hit
	 * 
	 * @return the to value for the range used to determine whether the location is hit
	 */
	public final int getTo() {
		return to;
	}
	
	/**
	 * return whether the given number is contained within the hit locations range
	 * 
	 * @param  number number to check whether it's contained within the hit locations range
	 * @return        whether the given number is contained within the hit locations range
	 */	
	public final boolean contains(final int number) {
		return (number >= getFrom() && number <= getTo());
	}
	
	/**
	 * return whether the range of the given hit location overlaps with the range of the hit location
	 * 
	 * @param  hitLocation hit location to check the range of
	 * @return             whether the range of the given hit location overlaps with the range of the hit location
	 */	
	public final boolean overlaps(final HitLocation hitLocation) {
        if (hitLocation == null) {
            throw new IllegalArgumentException("hitLocation cannot be null");
        } 

        return ((hitLocation.getFrom() >= getFrom() && hitLocation.getFrom() <= getTo()) ||
				(hitLocation.getTo() >= getFrom() && hitLocation.getTo() <= getTo()) ||
				(hitLocation.getFrom() < getFrom() && hitLocation.getTo() > getTo()));
	}
	
	/**
	 * formatted range for the hit location
	 * 
	 * @return formatted range for the hit location
	 */	
	public String getRange() {
		StringBuilder description = new StringBuilder();
		
		description.append(getFrom());
		if (getTo() != getFrom()) {
			description.append("-").append(getTo());
		}		
		
		return description.toString();
	}

	/**
	 * description of the target location(s) for this hit location
	 * 
	 * @return description of the target location(s) for this hit location
	 */
	public abstract String getLocationDescription();
	
	@Override
	public String toString() {
		StringBuilder description = new StringBuilder();
		
		description.append(getRange());
		description.append(": ").append(getLocationDescription());
		
		return description.toString();
	}
	
}
