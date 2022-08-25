package net.b5gamer.b5wars.game;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import net.b5gamer.b5wars.unit.Unit;

/**
 * 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public final class Game implements Serializable {

	// sides
	//		race, points
	// year
	// starting locations, distance apart, speed
	// fleet composition
	// objective
	// ramming?
	// Crew Readiness
	
	private static final long serialVersionUID = 4277711159832933526L;

	private       String          name  = null; // name of the game
	private       int             turn  = 0;    // the current turn 
	private final ArrayList<Side> sides = new ArrayList<Side>(0); // all sides in this game

	public Game(final String name) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 

		this.name = name;
	}
	
	/**
	 * the name of the game
	 * 
	 * @return the name of the game
	 */
	public String getName() {
		return name;
	}

	/**
	 * the name of the game
	 * 
	 * @param name the name of the game
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * the current turn 
	 * 
	 * @return the current turn
	 */
	public final int getTurn() {
		return turn;
	}

	/**
	 * the current turn 
	 * 
	 * @param turn the current turn
	 */
	public void setTurn(int turn) {
		this.turn = turn;
	}
	
	/**
	 * all sides in this game
	 * 
	 * @return all sides in this game
	 */
	public final List<Side> getSides() {
		return sides;
	}
	
	public final List<Unit> getAllUnitsInPlay() {
		// TODO
		return null;
	}
	
}
