/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

/**
 * This interface is used to denote a {@link net.b5gamer.b5wars.unit.structural.system.System}
 * as being able to provide a {@link net.b5gamer.b5wars.unit.structural.system.Resource} for 
 * a {@link net.b5gamer.b5wars.unit.structural.system.ResourceConsumingSystem}
 * 
 * All systems implementing this interface are handled after power allocation in the Ship Power 
 * Segment in order from lowest to highest order
 *  
 * @author Alex Packwood (aka PackyNZ)
 */
public interface ResourceProvidingSystem {

	/**
	 * the order at which this system is handled, with the lowest number being handled first
	 * 
	 * @return the order at which this system is handled, with the lowest number being handled first
	 */
	public int getOrder();

	/**
	 * the resource provided
	 * 
	 * @return the resource provided
	 */
	public Resource getResourceProvided();
	
	/**
	 * the amount of resource available
	 * 
	 * @return the amount of resource available
	 */
	public int getAvailableResource();
	
	/**
	 * the amount of resource available
	 * 
	 * @param availableResource the amount of resource available
	 */
	public void setAvailableResource(final int availableResource);
	
}
