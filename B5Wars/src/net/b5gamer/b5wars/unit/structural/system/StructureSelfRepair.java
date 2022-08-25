/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * !!--- work in progress ---!!

 * @author Alex Packwood (aka PackyNZ)
 */
public class StructureSelfRepair extends SelfRepair {

	private static final long serialVersionUID = 4457364332263557977L;
	
	/**
	 * @param damageBoxes amount of damage the self repair can sustain before being destroyed
	 * @param armor       amount of armor protecting the self repair
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the self repair, may be null
	 * @param properties  additional properties 
	 */
	public StructureSelfRepair(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "repairRate"));
	}

	/**
	 * @param damageBoxes amount of damage the self repair can sustain before being destroyed
	 * @param armor       amount of armor protecting the self repair
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the self repair, may be null
	 * @param repairRate  rate at which the self repair can repair damage
	 */
	public StructureSelfRepair(final int damageBoxes, final int armor, final Arc arc, final String name, 
			final int repairRate) {
		super(damageBoxes, armor, arc, name, repairRate);
	}

	public String getType() {
		return "Structure Self-Repair";
	}
	
	protected void resolveCriticalHit(final int roll) {
		// TODO
	}	
	
	protected void adjustSystem() {
		// TODO
	}

	@Override
	public int getRecognitionOrder() {
		return 880;
	}
	
}
