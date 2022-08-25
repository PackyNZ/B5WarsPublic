/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

/**
 * !!--- work in progress ---!!
 * JammerStatus is used to indicate the current status of a Jammer
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public enum JammerStatus {

	/**
	 * jammer is completely burnt out and not functioning at all
	 */
	COMPLETELY_BURNT_OUT,

	/**
	 * jammer is partially burnt out and not completely effective at blocking enemy 
	 * lock-ons
	 */
	PARTIALLY_BURNT_OUT,

	/**
	 * jammer is working normally
	 */
	WORKING;
	
}
