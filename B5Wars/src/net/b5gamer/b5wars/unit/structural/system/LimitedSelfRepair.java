package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

public class LimitedSelfRepair extends SelfRepair {

	private static final long serialVersionUID = 1802300508095538835L;

	/**
	 * @param damageBoxes amount of damage the self repair can sustain before being destroyed
	 * @param armor       amount of armor protecting the self repair
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the self repair, may be null
	 * @param properties  additional properties 
	 */
	public LimitedSelfRepair(final int damageBoxes, final int armor, final Arc arc, final String name,
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
	public LimitedSelfRepair(final int damageBoxes, final int armor, final Arc arc, final String name, 
			final int repairRate) {
		super(damageBoxes, armor, arc, name, repairRate);
	}
	
	protected String getIconFilenamePrefix() {
		return "Limited Self-Repair";
	}
	
	protected String getRecognitionIconFilename() {
		return "Limited Self-Repair";
	}
	
}
