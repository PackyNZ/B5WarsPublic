/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

/**
 * !!--- work in progress ---!!
 * This interface is used to denote a {@link net.b5gamer.b5wars.unit.structural.system.System}
 * as being able to provide power for a 
 * {@link net.b5gamer.b5wars.unit.structural.system.PowerRequiringSystem} to function
 * 
 * All systems implementing this interface are handled in the Ship Power Segment in order from lowest
 * to highest order
 *  
 * @author Alex Packwood (aka PackyNZ)
 */
public interface PowerProvidingSystem {

	/**
	 * the order at which this system is handled, with the lowest number being handled first
	 * 
	 * @return the order at which this system is handled, with the lowest number being handled first
	 */
	public int getOrder();
	
//	/**
//	 * whether power must be allocated to systems in the Ship Power Segment (true), or is used on an ad hoc
//	 * basis whenever a system or weapon requiring power is used 
//	 * 
//	 * @return whether power must be allocated to systems in the Ship Power Segment (true), or is used on an
//	 *         ad hoc basis whenever a system or weapon requiring power is used
//	 */
//	public boolean isPreAllocated();
	
	/**
	 * the base amount of power available
	 * 
	 * @return the base amount of power available
	 */
	public int getBaseAvailablePower();
	
	/**
	 * the amount of power available
	 * 
	 * @return the amount of power available
	 */
	public int getAvailablePower();
	
	/**
	 * the amount of power available
	 * 
	 * @param availablePower the amount of power available
	 */
	public void setAvailablePower(final int availablePower);
	
}
