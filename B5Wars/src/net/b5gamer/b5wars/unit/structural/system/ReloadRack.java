/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * !!--- work in progress ---!!
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class ReloadRack extends System {

	private static final long serialVersionUID = 6566534832175314143L;

	/**
	 * @param damageBoxes amount of damage the reload rack can sustain before being destroyed
	 * @param armor       amount of armor protecting the reload rack
	 * @param arc         arc for incoming fire, may be null 
	 * @param name        name of the reload rack, may be null
	 * @param properties  additional properties 
	 */
	public ReloadRack(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name);
	}
	
	/**
	 * @param damageBoxes amount of damage the reload rack can sustain before being destroyed
	 * @param armor       amount of armor protecting the reload rack
	 * @param arc         arc for incoming fire, may be null 
	 * @param name        name of the reload rack, may be null
	 */
	public ReloadRack(final int damageBoxes, final int armor, final Arc arc, final String name) {
		super(damageBoxes, armor, arc, name);
	}

	public String getType() {
		return "Reload Rack";
	}
	
	protected void resolveCriticalHit(final int roll) {
		// TODO
	}	
	
	protected void adjustSystem() {
		// TODO
	}

	@Override
	public int getRecognitionOrder() {
		return 860;
	}
	
}
