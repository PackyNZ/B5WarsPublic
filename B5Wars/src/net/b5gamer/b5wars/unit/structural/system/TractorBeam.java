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
public class TractorBeam extends Weapon {

	private static final long serialVersionUID = 8123653910050584032L;

	public TractorBeam(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, 0);
	}
	
	public TractorBeam(final int damageBoxes, final int armor, final Arc arc, final String name, final int power) {
		super(damageBoxes, armor, arc, name, power);
	}

	public String getType() {
		return "Tractor Beam";
	}

	@Override
	public boolean isDamageBoxesFixed() {
		return false;
	}
	
	protected void resolveCriticalHit(final int roll) {
		// TODO
	}	
	
	protected void adjustSystem() {
		// TODO
	}
		
	@Override
	public int getRecognitionOrder() {
		return 910;
	}
	
}
