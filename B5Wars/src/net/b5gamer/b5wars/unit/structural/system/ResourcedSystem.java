/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;

/**
 * This class provides a base implementation of a 
 * {@link net.b5gamer.b5wars.unit.structural.system.ResourceConsumingSystem} 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public abstract class ResourcedSystem extends System implements ResourceConsumingSystem {

	private static final long serialVersionUID = 8668191330840454403L;

	private int     resourceRequirement; // amount of resource required
	private boolean allocated  = true;   // whether the system has been allocated resource
//	private int     extraResouce = 0;    // amount of extra resource allocated to the system
	
	/**
	 * @param damageBoxes         amount of damage the system can sustain before being destroyed
	 * @param armor               amount of armor protecting the system
	 * @param arc                 arc for incoming fire or outgoing effects, may be null
	 * @param name                name of the system, may be null
	 * @param resourceRequirement amount of resource required
	 */
	protected ResourcedSystem(final int damageBoxes, final int armor, final Arc arc, final String name,
			final int resourceRequirement) {
		super(damageBoxes, armor, arc, name);
		
        if (resourceRequirement < 0) {
            throw new IllegalArgumentException("ResourceRequirement cannot be a negative number");
        } 

        this.resourceRequirement = resourceRequirement;
	}
	
	public int getResourceRequirement() {
		return resourceRequirement;
	}

	/**
	 * the amount of resource required
	 * 
	 * @param resourceRequirement the amount of resource required
	 */
	protected void setResourceRequirement(final int resourceRequirement) {
		this.resourceRequirement = resourceRequirement < 0 ? 0 : resourceRequirement;
	}

	public boolean isAllocated() {
		return allocated;
	}

	public void setAllocated(final boolean allocated) {
		this.allocated = allocated;
	}
	
//	public int getExtraResourceGrouping() {
//		return 0;
//	}
//	
//	public int getExtraResouce() {
//		return extraResouce;
//	}
//
//	public void setExtraResouce(final int extraResouce) {
//		this.extraResouce = extraResouce < 0 ? 0 : extraResouce;
//	}	

}
