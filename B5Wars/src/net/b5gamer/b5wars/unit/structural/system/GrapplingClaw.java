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
public class GrapplingClaw extends Weapon {

	private static final long serialVersionUID = 8055550500354884780L;

	/**
	 * @param damageBoxes not used
	 * @param armor       amount of armor protecting the grappling claw
	 * @param arc         arc for incoming fire and field of fire for the grappling claw
	 * @param name        name of the grappling claw, may be null
	 * @param properties  additional properties 
	 */
	public GrapplingClaw(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(armor, arc, name);
	}
	
	/**
	 * @param armor amount of armor protecting the grappling claw
	 * @param arc   arc for incoming fire and field of fire for the grappling claw
	 * @param name  name of the grappling claw, may be null
	 */
	public GrapplingClaw(final int armor, final Arc arc, final String name) {
		super(5, armor, arc, name, 0);
	}

	public String getType() {
		return "Grappling Claw";
	}
	
	protected void resolveCriticalHit(final int roll) {
		// TODO
	}	
	
	protected void adjustSystem() {
		// TODO
	}

	@Override
	public int getRecognitionOrder() {
		return 1220;
	}
	
}
