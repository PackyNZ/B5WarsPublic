package net.b5gamer.b5wars.game;

import java.util.ArrayList;
import java.util.List;

import net.b5gamer.b5wars.unit.Unit;

/**
 * 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public final class Player {

	// -!-

	private final String  name;       // name of this player
	private final Faction faction;    // faction this player may select units from, may be null for no restriction
	private final int     pointLimit; // number of combat points this player may build a fleet with
	private final ArrayList<Unit> units = new ArrayList<Unit>(0); // all this players units

	/**
	 * @param name       name of this player
	 * @param faction    faction this player may select units from, may be null for no restriction
	 * @param pointLimit number of combat points this player may build a fleet with
	 */
	public Player(final String name, final Faction faction, final int pointLimit) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        } 
        if (pointLimit < 0) {
            throw new IllegalArgumentException("Point Limit cannot be a negative number");
        } 
		
		this.name       = name;
		this.faction    = faction;
		this.pointLimit = pointLimit;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}
	
	/**
	 * @return the faction
	 */
	public final Faction getFaction() {
		return faction;
	}
	
	/**
	 * @return the pointLimit
	 */
	public final int getPointLimit() {
		return pointLimit;
	}

	/**
	 * @return the units
	 */
	public final List<Unit> getUnits() {
		return units;
	}

}
