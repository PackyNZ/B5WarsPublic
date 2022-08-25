/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * A shield based on gravitic technology which creates a gravity well around the vessel
 * to shift incoming fire, and due to being very power-hungry requires a special 
 * {@link net.b5gamer.b5wars.unit.structural.system.ShieldGenerator} to function
 * 
 * Note: reduction of shield strength for nebulas is not yet implemented
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class GraviticShield extends Shield implements ResourceConsumingSystem {

	private static final long serialVersionUID = -4920512446290976231L;

	private boolean allocated = false; // whether the shield has been allocated power from a shield generator

	/**
	 * @param damageBoxes not used
	 * @param armor       amount of armor protecting the shield
	 * @param arc         arc for incoming fire and arc covered by the shield
	 * @param number      the weapon number
	 * @param properties  additional properties 
	 */
	public GraviticShield(final int damageBoxes, final int armor, final Arc arc, final String number,
			final Properties properties) {
		this(armor, arc, Integer.parseInt(number), PropertyReader.getInteger(properties, "shieldFactor"));
	}
	
	/**
	 * @param damageBoxes  amount of damage the shield can sustain before being destroyed
	 * @param armor        amount of armor protecting the shield
	 * @param arc          arc for incoming fire and arc covered by the shield 
	 * @param number       the weapon number
	 * @param power        amount of power required for the shield to function
	 * @param shieldFactor strength of the shield
	 */	
	protected GraviticShield(final int damageBoxes, final int armor, final Arc arc, final int number, 
			final int power, final int shieldFactor) {
		super(damageBoxes, armor, arc, number, power, shieldFactor);
	}
	
	/**
	 * @param armor        amount of armor protecting the shield
	 * @param arc          arc for incoming fire and arc covered by the shield  
	 * @param number       the weapon number
	 * @param shieldFactor strength of the shield
	 */
	public GraviticShield(final int armor, final Arc arc, final int number, final int shieldFactor) {
		super(6, armor, arc, number, 0, shieldFactor);
	}
	
	public String getType() {
		return "Gravitic Shield";
	}

	public int getDefensiveBonus() {
		// reduce shield strength for armor
		// TODO also adjust for nebulas
		int defensiveBonus = super.getDefensiveBonus() - getArmor();
		return (defensiveBonus < 0) ? 0 : defensiveBonus;
	}
	
	public int getDamageReducingCapacity() {
		// reduce shield strength for armor
		// TODO also adjust for nebulas
		int damageReduction = super.getDamageReducingCapacity() - getArmor();
		return (damageReduction < 0) ? 0 : damageReduction;
	}
	
	public Resource getResourceConsumed() {
		return Resource.SHIELDS;
	}
	
	public int getResourceRequirement() {
		return 1;
	}
		
	public boolean isAllocated() {
		return allocated;
	}

	public void setAllocated(final boolean allocated) {
		this.allocated = allocated;
	}
	
}
