/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * !!--- work in progress ---!!
 * A Jammer provides protection for a vessel against electronic warfare
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Jammer extends PoweredSystem {

	private static final long serialVersionUID = 3939883553161745871L;

	private JammerStatus jammerStatus = JammerStatus.WORKING; // the functional status of the jammer

	/**
	 * @param damageBoxes amount of damage the jammer can sustain before being destroyed
	 * @param armor       amount of armor protecting the jammer
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the jammer, may be null
	 * @param properties  additional properties 
	 */
	public Jammer(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name, PropertyReader.getInteger(properties, "power"));
	}	
	
	/**
	 * @param damageBoxes amount of damage the jammer can sustain before being destroyed
	 * @param armor       amount of armor protecting the jammer
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the jammer, may be null
	 * @param power       amount of power consumed by the jammer
	 */
	public Jammer(final int damageBoxes, final int armor, final Arc arc, final String name,
			final int power) {
		super(damageBoxes, armor, arc, name, power);
	}
	
	public String getType() {
		return "Jammer";
	}

	/**
	 * the functional status of the jammer
	 * 
	 * @return the functional status of the jammer
	 */
	public JammerStatus getJammerStatus() {
		return jammerStatus;
	}

	/**
	 * the functional status of the jammer
	 * 
	 * @param status the functional status of the jammer
	 */
	private void setJammerStatus(final JammerStatus status) {
		this.jammerStatus = status;
	}

	protected void resolveCriticalHit(final int roll) {
		if (getJammerStatus() != JammerStatus.COMPLETELY_BURNT_OUT) {
			Logger.info(roll + " rolled for " + getFullName() + "...");

			if (roll <= 15) {
				// no critical hit
				Logger.info("    - no critical hit");
			} else if (roll <= 22) {
				// partial burnout, jammer only partially blocks lock-ons
				setJammerStatus(JammerStatus.PARTIALLY_BURNT_OUT);
				Logger.info("    - partial burnout, jammer only partially blocks lock-ons");
			} else {
				// complete burnout, jammer has no effect
				setJammerStatus(JammerStatus.COMPLETELY_BURNT_OUT);
				Logger.info("    - complete burnout, jammer has no effect");
			}
		}
	}	
	
	protected void adjustSystem() {
	}
	
	@Override
	public int getRecognitionOrder() {
		return 840;
	}
	
}
