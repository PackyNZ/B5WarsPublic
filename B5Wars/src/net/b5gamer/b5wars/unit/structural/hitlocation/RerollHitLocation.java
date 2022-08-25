/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.hitlocation;

import net.b5gamer.util.Properties;

/**
 * A RerollHitLocation gives a specific hit location a second chance to avoid being hit, by
 * only allowing the location to be hit if a second roll falls within a new range, otherwise
 * the second roll is compared against hit location chart 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class RerollHitLocation extends MultiHitLocation {

	private static final long serialVersionUID = 3513946370971913058L;

	private HitLocation hitLocation; // hit location to reroll against

	/**
	 * @param from       from value for the range used to determine whether this location is hit
	 * @param to         to value for the range used to determine whether this location is hit
	 * @param properties additional properties 
	 */
	public RerollHitLocation(final int from, final int to, final Properties properties) {
		super(from, to);
	}
	
	/**
	 * @param from        from value for the range used to determine whether this location is rerolled
	 * @param to          to value for the range used to determine whether this location is rerolled
	 * @param hitLocation hit location to reroll against
	 */
	public RerollHitLocation(final int from, final int to, final HitLocation hitLocation) {
		super(from, to);
		
		setHitLocation(hitLocation);
	}
	
	/**
	 * the hit location to reroll against
	 * 
	 * @return the hit location to reroll against
	 */
	public HitLocation getHitLocation() {
		return hitLocation;
	}
	
	/**
	 * the hit location to reroll against
	 * 
	 * @param hitLocation the hit location to reroll against
	 */		
	public void setHitLocation(HitLocation hitLocation) {
		if (hitLocation == null) {
            throw new IllegalArgumentException("hitLocation cannot be null");
        } 

		this.hitLocation = hitLocation;
		
		getHitLocations().clear();
		getHitLocations().add(hitLocation);
	}

	/**
	 * the hit location to reroll against
	 * 
	 * @param hitLocation the hit location to reroll against
	 */	
	@Override
	public void addHitLocation(final HitLocation hitLocation) {
		if (getHitLocation() != null) {
            throw new IllegalArgumentException("Only a single reroll " + HitLocation.class.getSimpleName() + " is permitted");
        } 		
		
		setHitLocation(hitLocation);
	}	
	
	public String getLocationDescription() {
		StringBuilder description = new StringBuilder();
		
		description.append("Roll Again (On ").append(getHitLocation().toString()).append(")");
		
		return description.toString();
	}
	
}
