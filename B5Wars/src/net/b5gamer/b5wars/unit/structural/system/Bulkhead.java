/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * A Bulkhead represents a protective barrier of some sort within a vessel that can be 
 * closed to prevent damage spreading to more vital systems 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Bulkhead extends System implements DamageAbsorbingSystem {

	private static final long serialVersionUID = 8745378594697520963L;

	/**
	 * @param damageBoxes amount of damage the bulkhead can sustain before being destroyed
	 * @param armor       not used
	 * @param arc         arc for incoming fire, may be null 
	 * @param name        name of the bulkhead, may be null
	 * @param properties  additional properties 
	 */
	public Bulkhead(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, arc, name);
	}
	
	/**
	 * @param damageBoxes amount of damage the bulkhead can sustain before being destroyed
	 * @param arc         arc for incoming fire, may be null 
	 * @param name        name of the bulkhead, may be null
	 */
	public Bulkhead(final int damageBoxes, final Arc arc, final String name) {
		super(damageBoxes, 0, arc, name);
	}
	
	public String getType() {
		return "Bulkhead";
	}
	
	public boolean isTargetArmorApplied() {
		return true;
	}
	
	public int getAbsorptionCapacity() {
		return getDamageBoxes();
	}
	
	public int absorbDamage(final int damage) {
		return super.applyDamage(damage);
	}
	
	protected void resolveCriticalHit(final int roll) {
		// no critical hits
	}	
	
	protected void adjustSystem() {
	}

	@Override
	public int getRecognitionOrder() {
		return 1200;
	}	
	
}
