/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit;

/**
 * Size indicates the relative size of a particular unit, and is used to determine any special 
 * rules apply for specific sized units, including which {@link net.b5gamer.b5wars.weapon.FireControl} 
 * modifier is used when attacking a given unit 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public enum Size {

	/**
	 * enormous units that are subject to special rules such as generally blocking line of sight
	 * and automatic ramming
	 */
	ENORMOUS,

	/**
	 * capital ships, heavy combat vessels, and anything larger (barring enormous units with 
	 * special rules)
	 */
	LARGE,

	/**
	 * medium ships and light combat vessels
	 */
	MEDIUM,

	/**
	 * fighters, shuttles, and anything smaller
	 */
	SMALL;	

}
