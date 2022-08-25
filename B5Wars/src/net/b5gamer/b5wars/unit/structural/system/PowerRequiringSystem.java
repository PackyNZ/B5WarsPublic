/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

/**
 * !!--- work in progress ---!!
 * This interface is used to denote a {@link net.b5gamer.b5wars.unit.structural.system.System} as
 * requiring power from a {@link net.b5gamer.b5wars.unit.structural.system.PowerProvidingSystem} 
 * to function, and without access to power (even if the power requirement is 0) will not function at all
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public interface PowerRequiringSystem {

//	/**
//	 * whether power must be allocated to the system in the Ship Power Segment for it to function for the 
//	 * turn (regardless of whether a {@link net.b5gamer.b5wars.virtualb5wars.unit.structural.system.PowerProducingSystem}
//	 * must be pre-allocated), or whether the system may be powered on an ad hoc basis when it is used
//	 * 
//	 * @return whether power must be allocated to the system in the Ship Power Segment for it to function
//	 *         for the turn
//	 */
//	public boolean isPreAllocationRequired();
	
	/**
	 * the amount of power required for the system to function
	 * 
	 * @return the amount of power required for the system to function
	 */
	public int getPowerRequirement();
	
	/**
	 * whether the system is activated
	 * 
	 * @return whether the system is activated
	 */
	public boolean isActivated();

	/**
	 * whether the system is activated
	 * 
	 * @param activated whether the system is activated
	 */
	public void setActivated(final boolean activated);

	/**
	 * if greater than 0 denotes the amount to group extra power allocation by, otherwise extra power is
	 * not used
	 * 
	 * @return if greater than 0 denotes the amount to group extra power allocation by, otherwise extra 
	 *         power is not used
	 */
	public int getExtraPowerGrouping();
	
	/**
	 * the amount of extra power allocated to the system
	 * 
	 * @return the amount of extra power allocated to the system
	 */
	public int getExtraPower();

	/**
	 * the amount of extra power allocated to the system
	 * 
	 * @param extraPower the amount of extra power allocated to the system
	 */
	public void setExtraPower(final int extraPower);
	
}
