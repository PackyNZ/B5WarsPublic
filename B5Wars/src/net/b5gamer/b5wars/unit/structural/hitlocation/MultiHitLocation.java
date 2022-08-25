/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.hitlocation;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.util.Properties;

/**
 * A MultiHitLocation indicates several possible locations are hit
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class MultiHitLocation extends HitLocation {

	private static final long serialVersionUID = -6809979968248893202L;

	private final List<HitLocation> hitLocations = new ArrayList<HitLocation>(0); // all hit locations

	/**
	 * @param from       from value for the range used to determine whether this location is hit
	 * @param to         to value for the range used to determine whether this location is hit
	 * @param properties additional properties 
	 */
	public MultiHitLocation(final int from, final int to, final Properties properties) {
		this(from, to);
	}
	
	/**
	 * @param from from value for the range used to determine whether this location is hit
	 * @param to   to value for the range used to determine whether this location is hit
	 */
	public MultiHitLocation(final int from, final int to) {
		super(from, to);
	}
	
	/**
	 * all hit locations
	 * 
	 * @return all hit locations
	 */
	protected List<HitLocation> getHitLocations() {
		return hitLocations;
	}
	
    /**
     * an iterator of all hit locations
     * 
	 * @return an iterator of all hit locations
	 */
	public Iterator<HitLocation> getHitLocationIterator() {
		return getHitLocations().iterator();
	}
	
	/**
	 * add a hit location
	 * 
	 * @param hitLocation hit location to add
	 */	
	public void addHitLocation(final HitLocation hitLocation) {
		if (hitLocation == null) {
            throw new IllegalArgumentException("hitLocation cannot be null");
        } 

    	if (!getHitLocations().contains(hitLocation)) {
    		getHitLocations().add(hitLocation);
    	}
	}
	
	public String getLocationDescription() {
		StringBuilder description = new StringBuilder();
		
		for (Iterator<HitLocation> iterator = getHitLocations().iterator(); iterator.hasNext();) {
			description.append(iterator.next().getLocationDescription());
			
			if (iterator.hasNext()) {
				description.append("/");
			}
		}
		
		return description.toString();
	}
	
}
