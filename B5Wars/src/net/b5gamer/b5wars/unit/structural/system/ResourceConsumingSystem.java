/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

/**
 * This interface is used to denote a {@link net.b5gamer.b5wars.unit.structural.system.System}
 * as consuming a {@link net.b5gamer.b5wars.unit.structural.system.Resource} from a 
 * {@link net.b5gamer.b5wars.unit.structural.system.ResourceProvidingSystem} 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public interface ResourceConsumingSystem {
	
	/**
	 * the resource consumed
	 * 
	 * @return the resource consumed
	 */
	public Resource getResourceConsumed();
	
	/**
	 * the amount of resource required 
	 * 
	 * @return the amount of resource required 
	 */
	public int getResourceRequirement();
	
	/**
	 * whether the system has been allocated resource
	 * 
	 * @return whether the system has been allocated resource
	 */	
	public boolean isAllocated();

	/**
	 * whether the system has been allocated resource
	 * 
	 * @param allocated whether the system has been allocated resource
	 */	
	public void setAllocated(final boolean allocated);
	
//	/**
//	 * if greater than 0 denotes the amount to group extra resource allocation by, otherwise extra
//	 * resource is not used
//	 * 
//	 * @return if greater than 0 denotes the amount to group extra resource allocation by, otherwise
//	 *         extra resource is not used
//	 */
//	public int getExtraResourceGrouping();
//
//	/**
//	 * the amount of extra resource allocated to the system
//	 * 
//	 * @return the amount of extra resource allocated to the system
//	 */
//	public int getExtraResouce();
//
//	/**
//	 * the amount of extra resource allocated to the system
//	 * 
//	 * @param extraResouce the amount of extra resource allocated to the system
//	 */
//	public void setExtraResouce(final int extraResouce);
	
}
