/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;

/**
 * This interface is used to denote a {@link net.b5gamer.b5wars.unit.structural.system.System} as being 
 * directional in nature, and as such has a specific arc through which the effect of the system 
 * operates. Examples of directional systems include weapons which have a field of fire, shields 
 * which protects against attacks from a certain direction, and thrusters which expel thrust in
 * certain direction
 *  
 * @author Alex Packwood (aka PackyNZ)
 */
public interface DirectionalSystem {
	
	/**
	 * the arc through which the effect of the system operates
	 * 
	 * @return the arc through which the effect of the system operates
	 */
	public Arc getArc();

	/**
	 * the arc through which the effect of the system operates
	 * 
	 * @param arc the arc through which the effect of the system operates
	 */
	public void setArc(final Arc arc);
	
}
