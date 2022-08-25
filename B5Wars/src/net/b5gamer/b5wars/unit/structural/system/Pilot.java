/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.awt.Color;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * !!--- work in progress ---!!
 * A living pilot that has been merged with the vessel
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Pilot extends System {

	private static final long serialVersionUID = 6463491817252056087L;

	private static final Color OUTLINE_FILL_COLOR = new Color(147, 149, 152);
	
	/**
	 * @param damageBoxes amount of damage the pilot can sustain before being destroyed
	 * @param armor       amount of armor protecting the pilot
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the pilot, may be null
	 * @param properties  additional properties 
	 */
	public Pilot(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name);
	}
	
	/**
	 * @param damageBoxes amount of damage the pilot can sustain before being destroyed
	 * @param armor       amount of armor protecting the pilot
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the pilot, may be null
	 */	
	public Pilot(final int damageBoxes, final int armor, final Arc arc, final String name) {
		super(damageBoxes, armor, arc, name);
	}

	public String getType() {
		return "Pilot";
	}
	
	protected void resolveCriticalHit(final int roll) {
		// no critical hits
	}	
	
	protected void adjustSystem() {
	}

	@Override
	public Color getOutlineFillColor() {
		return OUTLINE_FILL_COLOR;
	}
	
	@Override
	public int getRecognitionOrder() {
		return 220;
	}
	
}
