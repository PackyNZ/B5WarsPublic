/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

/**
 * This interface is used to denote a {@link net.b5gamer.b5wars.unit.structural.system.System} as being 
 * defensive in nature, which when incoming fire is within the systems arc may provide a defensive 
 * bonus and/or provide a reduction to the amount of damage physically connecting with the hull of 
 * the vessel. However smaller craft such as fighters and shuttles can get close enough to bypass 
 * these defenses. 
 *  
 * A DefensiveSystem relies on a specific arc of coverage, therefore should also implement the
 * {@link net.b5gamer.b5wars.unit.structural.system.DirectionalSystem} interface
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public interface DefensiveSystem {
	
	/**
	 * the defensive bonus applied to incoming fire
	 * 
	 * @return the defensive bonus applied to incoming fire
	 */
	public int getDefensiveBonus();
	
	/**
	 * the remaining capacity of the system to reduce incoming damage 
	 * 
	 * @return the remaining capacity of the system to reduce incoming damage   
	 */
	public int getDamageReducingCapacity();
	
	/**
	 * given the amount of incoming damage, return the amount of damage that remains after damage
	 * reduction has been applied
	 * 
	 * @param  damage the amount of incoming damage
	 * @return        the amount of damage that remains after damage reduction has been applied
	 */
	public int applyDamageReduction(final int damage);	
	
}
