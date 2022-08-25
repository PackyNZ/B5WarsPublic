package net.b5gamer.b5wars.game;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public final class Side {

	// -!-

	private final String name; // name of this side
	private final ArrayList<Player> players = new ArrayList<Player>(0); // all players in this side

	/**
	 * @param name name of this side
	 */
	public Side(final String name) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("Name cannot be null or blank");
        } 
		
		this.name = name;
	}

	/**
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * @return the players
	 */
	public final List<Player> getPlayers() {
		return players;
	}	

}
