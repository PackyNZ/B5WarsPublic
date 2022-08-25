/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.hitlocation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.dice.Dice;

/**
 * A HitLocationChart contains the possible hit locations for one or more (in the case of 
 * general hit location charts) sections on a unit, denoting where incoming attacks hit
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public final class HitLocationChart implements Serializable {

	private static final long serialVersionUID = 2065954554345800188L;

	private final String name; // name of the hit location chart
	private final List<HitLocation> hitLocations = new ArrayList<HitLocation>(0); // all hit locations
	
	/**
	 * @param name name of the hit location chart
	 */
	public HitLocationChart(final String name) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        }  

        this.name = name;
	}

	/**
	 * the name of the hit location chart
	 * 
	 * @return the name of the hit location chart
	 */
	public String getName() {
		return name;
	}

	/**
	 * a list of all hit locations
	 * 
	 * @return a list of all hit locations
	 */
	protected final List<HitLocation> getHitLocations() {
		return hitLocations;
	}
	
	/**
	 * the number of hit locations in the chart
	 * 
	 * @return the number of hit locations in the chart
	 */
	public final int getHitLocationCount() {
		return getHitLocations().size();
	}
	
    /**
     * an iterator of all hit locations
     * 
	 * @return an iterator of all hit locations
	 */
	public final Iterator<HitLocation> getHitLocationIterator() {
		return getHitLocations().iterator();
	}
	
	/**
	 * add a hit location
	 * 
	 * @param hitLocation hit location to add
	 */	
	public final void addHitLocation(final HitLocation hitLocation) {
		if (hitLocation == null) {
            throw new IllegalArgumentException("hitLocation cannot be null");
        } 
		if (doesRangeOverlap(hitLocation)) {
            throw new IllegalArgumentException(HitLocation.class.getSimpleName() + 
            		" range cannot overlap with an existing " + HitLocation.class.getSimpleName());
		}

    	if (!getHitLocations().contains(hitLocation)) {
    		getHitLocations().add(hitLocation);
    	}
	}
	
	/**
	 * return whether the range for the specified hit location overlaps that of an existing hit location
	 * 
	 * @param  hitLocation hit location to check the range of
	 * @return             whether the range for the specified hit location overlaps that of an existing hit location 
	 */	
	private final boolean doesRangeOverlap(final HitLocation hitLocation) {
		boolean result = false;
		
		if (hitLocation == null) {
            throw new IllegalArgumentException("hitLocation cannot be null");
        } 

		for (Iterator<HitLocation> iterator = getHitLocations().iterator(); iterator.hasNext();) {
			if (hitLocation.overlaps(iterator.next())) {
				result = true;
				break;
			}
		}
		
		return result;
	}
	
	/**
	 * determine which location was hit by a given number
	 * 
	 * @param  number the number to check whether it's contained in a hit locations range denoting
	 *                that location is hit, this number may be altered for special hit locations
	 * @return        the location to hit
	 */	
	public final HitLocation determineLocationHit(int number) {
		return getHitLocation(number);
	}
	
	/**
	 * find and return the location whose range contains the given number
	 * 
	 * @return the location whose range contains the given number
	 */	
	private final HitLocation getHitLocation(int number) {
		HitLocation result = null;
		
		for (Iterator<HitLocation> iterator = getHitLocations().iterator(); iterator.hasNext();) {
			HitLocation hitLocation = iterator.next();
			
			if (hitLocation.contains(number)) {
				// TODO handle within RerollHitLocation??
				if (hitLocation instanceof RerollHitLocation) {
					RerollHitLocation rerollLocation = (RerollHitLocation) hitLocation;
					number = Dice.d20.roll();
					
					if (rerollLocation.getHitLocation().contains(number)) {
						result = rerollLocation.getHitLocation();
						break;
					} else {
						result = getHitLocation(number);
						break;
					}					
				} else {					
					result = hitLocation;
					break;
				}
			}
		}
		
		return result;
	}
	
}
