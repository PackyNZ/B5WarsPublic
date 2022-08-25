/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.small;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.unit.ServiceHistory;
import net.b5gamer.b5wars.unit.Size;
import net.b5gamer.b5wars.unit.Trait;
import net.b5gamer.dice.Dice;

/**
 * !!--- work in progress ---!!
 * A FighterFlight is a group of one of more identical fighters
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public abstract class FighterFlight extends SmallUnit {

	// TODO armor - fighter
	// TODO weapons
	// TODO missiles - fighter
	// TODO navigator - fighter
	
	private static final long serialVersionUID = 876152232111750545L;
	
	private final int    freeThrust;        // available thrust per turn 
	private final int    offensiveModifier; // attack modifier 	
	private final int    damageBoxes;       // amount of damage a fighter can sustain before being destroyed 
	private final double hangarBoxes;       // number of boxes a single fighter occupies in a hangar
	private final int    dropoutModifier;   // modifier to dropout rolls 
	private       int    jinking  = 0;      // amount of jinking being performed by the flight
	private final List<Fighter> fighters = new ArrayList<Fighter>(0); // all fighters in this flight
	
	/**
	 * @param name               unit name
	 * @param model              unit model
	 * @param hull               base hull the unit is built on
	 * @param version            version number
	 * @param author             author of the unit
	 * @param source             source of the unit
	 * @param pointValue         combat point cost of the unit
	 * @param rammingFactor      factor for ramming other units
	 * @param fwdAftDefense      defense when attacked from fore or aft
	 * @param stbPortDefense     defense when attacked from starboard or port
	 * @param initiativeModifier modifier to initiative
	 * @param serviceHistory     the units service history 
	 * @param traits             the units traits
	 * @param turnCostFormatted  raw/unparsed thrust cost to make a turn 
	 * @param turnDelayFormatted raw/unparsed mandatory movement delay between making turns  
	 * @param accelDecelCost     thrust required to alter speed
	 * @param pivotCostFormatted raw/unparsed thrust required to make a pivot
	 * @param rollCostFormatted  raw/unparsed thrust required to make a roll
	 * @param freeThrust         available thrust per turn
	 * @param offensiveModifier  attack modifier 
	 * @param damageBoxes        amount of damage a fighter can sustain before being destroyed 
	 * @param hangarBoxes        number of boxes a single fighter occupies in a hangar
	 * @param dropoutModifier    modifier to dropout rolls 
	 * @param numberOfFighters   number of fighters in this flight 
	 */
	protected FighterFlight(final String name, final String model, final String hull, final int version, 
			final String author, final String source, final int pointValue, final int rammingFactor, 
			final int fwdAftDefense, final int stbPortDefense, final int initiativeModifier, 
			final ServiceHistory serviceHistory, final Trait[] traits, final String turnCostFormatted,
			final String turnDelayFormatted, final int accelDecelCost, final String pivotCostFormatted, 
			final String rollCostFormatted, final int freeThrust, final int offensiveModifier, 
			final int damageBoxes, final double hangarBoxes, final int dropoutModifier, 
			final int numberOfFighters) {
		super(name, model, hull, version, author, source, pointValue, rammingFactor, 
				fwdAftDefense, stbPortDefense, initiativeModifier, serviceHistory, traits, 
				turnCostFormatted, turnDelayFormatted, accelDecelCost, pivotCostFormatted, 
				rollCostFormatted);

        if (!(freeThrust > 0)) {
            throw new IllegalArgumentException("freeThrust must be a positive number");
        } 
        if (!(damageBoxes > 0)) {
            throw new IllegalArgumentException("damageBoxes must be a positive number");
        } 
        if (!(hangarBoxes > 0)) {
            throw new IllegalArgumentException("hangarBoxes must be a positive number");
        } 
        if ((numberOfFighters < 0) || (numberOfFighters > getMaximumNumberPerFlight())) {
        	throw new IllegalArgumentException("numberOfFighters must be between 0 and " + getMaximumNumberPerFlight() + " inclusive");
        } 

		this.freeThrust        = freeThrust;
		this.offensiveModifier = offensiveModifier;
		this.damageBoxes       = damageBoxes;
		this.hangarBoxes       = hangarBoxes;
		this.dropoutModifier   = dropoutModifier;
		
		for (int index = 0; index < numberOfFighters; index++) {
			fighters.add(new Fighter(damageBoxes));
		}
	}

	public Size getSize() {
		return Size.SMALL;
	}
	
	/**
	 * the maximum number of fighters per flight
	 * 
	 * @return the maximum number of fighters per flight
	 */
	public int getMaximumNumberPerFlight() {
		return 6;
	}
	
	/**
	 * the available thrust per turn
	 * 
	 * @return the available thrust per turn
	 */
	public int getFreeThrust() {
		return freeThrust;
	}

	/**
	 * the attack modifier
	 * 
	 * @return the attack modifier  
	 */
	public int getOffensiveModifier() {
		return offensiveModifier;
	}

	/**
	 * the amount of damage a fighter can sustain before being destroyed
	 * 
	 * @return the amount of damage a fighter can sustain before being destroyed
	 */
	public int getDamageBoxes() {
		return damageBoxes;
	}
	
	/**
	 * the number of boxes a single fighter occupies in a hangar
	 * 
	 * @return the number of boxes a single fighter occupies in a hangar
	 */
	public double getHangarBoxes() {
		return hangarBoxes;
	}

	/**
	 * the modifier to dropout rolls
	 * 
	 * @return the modifier to dropout rolls
	 */
	public int getDropoutModifier() {
		return dropoutModifier;
	}

	/**
	 * the maximum amount of jinking that may be performed by the flight
	 * 
	 * @return the maximum amount of jinking that may be performed by the flight
	 */
	public abstract int getJinkingLimit();
	
	/**
	 * the amount of jinking being performed by the flight
	 * 
	 * @return the amount of jinking being performed by the flight
	 */
	public int getJinking() {
		return jinking;
	}

	/**
	 * the amount of jinking being performed by the flight
	 * 
	 * @param jinking the amount of jinking being performed by the flight
	 */
	public void setJinking(final int jinking) {
        if (jinking > getJinkingLimit()) {
            throw new IllegalArgumentException("jinking cannot exceed Jinking Limit");
        } 

		this.jinking = jinking < 0 ? 0 : jinking;
	}
		
	/**
	 * list of all fighters in this flight
	 * 
	 * @return list of all fighters in this flight
	 */
	protected List<Fighter> getFighters() {
		return fighters;
	}
	
	/**
	 * determine and resolve fighter drop outs
	 */
	protected void determineAndResolveDropOut() {
		for (Iterator<Fighter> iterator = getFighters().iterator(); iterator.hasNext();) {
			Fighter fighter = iterator.next();

			if (fighter.isValidTarget() && fighter.isDamagedThisTurn() && !fighter.isDroppedOut()) {
				fighter.setDroppedOut((Dice.d10.roll() + getDropoutModifier()) > fighter.getDamageBoxes() ? true : false);
			}
		}
	}
	
	public boolean isDestroyed() {
		boolean result = true;
		
		for (Iterator<Fighter> iterator = getFighters().iterator(); iterator.hasNext();) {
			Fighter fighter = iterator.next();
			
			if (!fighter.isDestroyed() && (!fighter.isDroppedOut())) {
				result = false;
				break;
			}
		}
		
		return result;
	}
	
	public void handleEndOfTurnActions() {
		for (Iterator<Fighter> iterator = getFighters().iterator(); iterator.hasNext();) {
			Fighter fighter = iterator.next();
			
			if (!fighter.isDestroyed() && !(fighter.getDamageBoxes() > 0)) {
				fighter.setDestroyed(true);
			}

			fighter.setDamagedThisTurn(false);
		}
		
		// TODO Hangar Operations Segment
		// TODO     Fighters/shuttles attempt to escape from destroyed ships
		// TODO     Launch/land fighters or shuttles
		// TODO     Hangar bay operations started earlier this turn are completed
		
		// TODO Adjust Ship Systems Segment
		// TODO     Adaptive armor points are released due to damage received this turn
		// TODO     Self-repair systems perform repairs
	}
	
}
