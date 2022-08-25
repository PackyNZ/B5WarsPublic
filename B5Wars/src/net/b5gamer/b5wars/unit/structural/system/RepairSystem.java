/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.util.List;

/**
 * !!--- work in progress ---!!
 * This interface is used to denote a {@link net.b5gamer.b5wars.unit.structural.system.System} 
 * as being able to repair other systems 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public interface RepairSystem {

	/**
	 * the priority at which this system is handled during the Adjust Ship Systems Segment, with the
	 * lowest priority being handled first
	 * 
	 * @return the priority at which this system is handled during the Adjust Ship Systems Segment
	 */
	public int getPriority();
	
	/**
	 * all systems that the repair system can repair
	 * 
	 * @return all systems that the repair system can repair
	 */	
	public List<System> getEligibleSystems();
	
	public int getRepairCapacity();
	
	/**
	 * repair a specified system by a specified amount
	 * 
	 * @param  system the system to repair
	 * @param  amount the amount to repair
	 * @return        the amount that cannot be repaired
	 */
	public int repair(System system, int amount);
	
}
