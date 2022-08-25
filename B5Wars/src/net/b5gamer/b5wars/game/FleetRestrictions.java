package net.b5gamer.b5wars.game;

/**
 * This class represents a period of service for a particular unit being employed by
 * a specific faction, and it's availability/deployment limitations during that time
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class FleetRestrictions {

	private final int     combatPointLimit; // the combat point limit for the fleet
	private final boolean softPointLimit;   // whether the combat point limit is a soft limit and may be exceeded by 1% 

	private final int     fromYear; // historical year to restrict units by in service date, may be 0 for no restriction 
	private final int     toYear;   // historical year to restrict units by in service date, may be 0 for no restriction 
	private final Faction faction;  // historical year to restrict units by in service date, may be 0 for no restriction 

	// points
	// year
	// faction
	
	// fleet composition
	
	/**
	 * @param pointLimit     the combat point limit for the fleet
	 * @param softPointLimit whether the combat point limit is a soft limit and may be exceeded by 1%
	 */
	public FleetRestrictions(final int pointLimit, final boolean softPointLimit) {
        if (pointLimit <= 0) {
            throw new IllegalArgumentException("pointLimit must be a positive number");
        } 

        this.combatPointLimit = pointLimit;
        this.softPointLimit   = softPointLimit;
        
        // TODO
		this.fromYear = 0;
		this.toYear   = 0;
		this.faction = null;
	}

	/**
	 * the combat point limit for the fleet
	 * 
	 * @return the combat point limit for the fleet
	 */
	public final int getPointLimit() {
		return combatPointLimit;
	}

	/**
	 * whether the combat point limit is a soft limit and may be exceeded by 1%
	 * 
	 * @return whether the combat point limit is a soft limit and may be exceeded by 1%
	 */
	public final boolean isSoftPointLimit() {
		return softPointLimit;
	}


	
	
	
	/**
	 * whether the specified fleet adheres to these restrictions
	 */
	public boolean isValid(final Fleet fleet) {
		return false;
	}
	
}
