/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.small;

/**
 * !!--- work in progress ---!!
 * This class represents a single fighter within a {@link net.b5gamer.b5wars.unit.small.FighterFlight}
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Fighter {

	// TODO armor?
	// TODO missiles
	// TODO navigator
	
	private final int     baseDamageBoxes;         // base amount of damage the fighter can sustain before being destroyed
	private       int     damageBoxes;             // amount of damage the fighter can sustain before being destroyed
	private       boolean damagedThisTurn = false; // whether this fighter has suffered damage this turn
	private       boolean droppedOut      = false; // whether the fighter has dropped out
	private       boolean destroyed       = false; // whether the fighter is destroyed
	
	/**
	 * @param damageBoxes amount of damage fighter can sustain before being destroyed
	 */
	protected Fighter(final int damageBoxes) {
        if (damageBoxes < 0) {
            throw new IllegalArgumentException("damageBoxes cannot be a negative number");
        } 
		
        this.baseDamageBoxes = damageBoxes;
        this.damageBoxes     = damageBoxes;
	}

	/**
	 * the base amount of damage the fighter can sustain before being destroyed
	 * 
	 * @return the base amount of damage the fighter can sustain before being destroyed
	 */
	public int getBaseDamageBoxes() {
		return baseDamageBoxes;
	}
	
	/**
	 * the amount of damage the fighter can sustain before being destroyed
	 * 
	 * @return the amount of damage the fighter can sustain before being destroyed
	 */
	public int getDamageBoxes() {
		return damageBoxes;
	}

	/**
	 * the amount of damage the fighter can sustain before being destroyed
	 * 
	 * @param damageBoxes the amount of damage the fighter can sustain before being destroyed
	 */
	public void setDamageBoxes(final int damageBoxes) {
		if (damageBoxes < getDamageBoxes()) {
			setDamagedThisTurn(true);
		}
		if (damageBoxes >= getBaseDamageBoxes()) {
			setDestroyed(false);
		}
			
		this.damageBoxes = damageBoxes < 0 ? 0 : damageBoxes;
	}	
	
	/**
	 * whether this fighter has suffered damage this turn
	 * 
	 * @return whether this fighter has suffered damage this turn
	 */
	public boolean isDamagedThisTurn() {
		return damagedThisTurn;
	}

	/**
	 * whether this fighter has suffered damage this turn
	 * 
	 * @param damagedThisTurn whether this fighter has suffered damage this turn
	 */
	public void setDamagedThisTurn(final boolean damagedThisTurn) {
		this.damagedThisTurn = damagedThisTurn;
	}
	
	/**
	 * whether the fighter has dropped out
	 * 
	 * @return whether the fighter has dropped out
	 */
	public boolean isDroppedOut() {
		return droppedOut;
	}

	/**
	 * whether the fighter has dropped out
	 * 
	 * @param droppedOut whether the fighter has dropped out
	 */
	public void setDroppedOut(final boolean droppedOut) {
		this.droppedOut = droppedOut;
	}
	
	/**
	 * whether the fighter is a valid target for incoming fire
	 * 
	 * @return whether the fighter is a valid target for incoming fire
	 */
	public boolean isValidTarget() {
		return (getDamageBoxes() > 0);
	}
	
	/**
	 * whether the fighter is destroyed
	 * 
	 * @return whether the fighter is destroyed
	 */
	public boolean isDestroyed() {
		return destroyed;
	}
	
	/**
	 * whether the fighter is destroyed
	 * 
	 * @param destroyed whether the fighter is destroyed
	 */
	public void setDestroyed(final boolean destroyed) {
		if (destroyed) {
			setDamageBoxes(0);
		}
		
		this.destroyed = destroyed;
	}
		
}
