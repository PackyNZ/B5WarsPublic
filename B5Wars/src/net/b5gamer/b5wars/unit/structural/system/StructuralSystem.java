/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

/**
 * This interface is used to denote a {@link net.b5gamer.b5wars.unit.structural.system.System} as being a 
 * structural part of a vessel, and will be considered structure for rules such as damage resolution 
 * and called shots. However if a system should be considered a structure block, it should extend
 * {@link net.b5gamer.b5wars.unit.structural.system.Structure} instead    
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public interface StructuralSystem {

}
