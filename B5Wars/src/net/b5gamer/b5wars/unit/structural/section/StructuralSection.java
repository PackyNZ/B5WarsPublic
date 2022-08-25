/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.section;

import net.b5gamer.b5wars.unit.structural.system.Structure;

/**
 * This interface is used to denote a {@link net.b5gamer.b5wars.unit.structural.section.Section} 
 * as being a structural section on a vessel, and as such has a structure block that if destroyed
 * renders the whole section destroyed
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public interface StructuralSection {

	/**
	 * the sections structure block
	 * 
	 * @return the sections structure block
	 */
	public Structure getStructure();
	
}
