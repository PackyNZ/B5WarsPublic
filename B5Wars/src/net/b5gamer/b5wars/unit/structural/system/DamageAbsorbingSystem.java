/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

/**
 * This interface is used to denote a {@link net.b5gamer.b5wars.unit.structural.system.System} as being 
 * able to absorb or prevent the spreading of damage once an attack hits the vessel (after the 
 * effects of any {@link net.b5gamer.b5wars.unit.structural.system.DefensiveSystem} has been applied), 
 * thus protecting more vital systems
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public interface DamageAbsorbingSystem {

	/**
	 * whether to subtract the target systems armor from incoming damage prior to attempting to 
	 * absorb damage
	 * 
	 * @return whether to subtract the target systems armor from incoming damage prior to 
	 *         attempting to absorb damage
	 */
	public boolean isTargetArmorApplied();
	
	/**
	 * the remaining capacity of the system to absorb damage 
	 * 
	 * @return the remaining capacity of the system to absorb damage  
	 */
	public int getAbsorptionCapacity();
	
	/**
	 * attempt to absorb a given amount of damage, returning the amount of damage that cannot 
	 * be absorbed 
	 * 
	 * @param  damage the amount of damage to be absorbed
	 * @return        the amount of damage that cannot be absorbed
	 */
	public int absorbDamage(final int damage);
	
}
