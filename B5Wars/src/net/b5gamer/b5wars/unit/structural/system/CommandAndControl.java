/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.awt.Color;

import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * !!--- work in progress ---!!
 * This system contains the command & control systems for a vessel
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class CommandAndControl extends System {
	
	private static final long serialVersionUID = 2655261916427830695L;

	private static final Color OUTLINE_FILL_COLOR = new Color(147, 149, 152);
	
	private int     initPenalty        = 0;     // penalty to initiative due to critical hits
	private int     tempInitPenalty    = 0;     // penalty to initiative next turn due to a critical hit
	private int     eWPenalty          = 0;     // penalty to EW due to critical hits	
	private boolean sensorsDisrupted   = false; // whether the sensors are disrupted for a turn due to a critical hit
	private int     fireControlPenalty = 0;     // penalty to fire control due to critical hits	
	private boolean severePowerLoss    = false; // whether there is severe power loss for a turn due to a critical hit

	/**
	 * @param damageBoxes amount of damage the C&C can sustain before being destroyed
	 * @param armor       amount of armor protecting the C&C
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the C&C, may be null
	 * @param properties  additional properties 
	 */
	public CommandAndControl(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(damageBoxes, armor, arc, name);
	}
	
	/**
	 * @param damageBoxes amount of damage the C&C can sustain before being destroyed
	 * @param armor       amount of armor protecting the C&C
	 * @param arc         arc for incoming fire, may be null
	 * @param name        name of the C&C, may be null
	 */
	public CommandAndControl(final int damageBoxes, final int armor, final Arc arc, final String name) {
		super(damageBoxes, armor, arc, name);
	}
	
	public String getType() {
		return "C & C";
	}
	
	/**
	 * the total initiative penalty
	 * 
	 * @return the total initiative penalty
	 */
	public int getInitiativePenalty() {
		return getInitPenalty() + getTempInitPenalty();
	}

	/**
	 * the penalty to initiative due to critical hits
	 * 
	 * @return the penalty to initiative due to critical hits
	 */
	private int getInitPenalty() {
		return initPenalty;
	}

	/**
	 * the penalty to initiative due to critical hits
	 * 
	 * @param initPenalty the penalty to initiative due to critical hits
	 */
	private void setInitPenalty(final int initPenalty) {
		this.initPenalty = initPenalty < 0 ? 0 : initPenalty;
	}

	/**
	 * the penalty to initiative next turn due to a critical hit
	 * 
	 * @return the penalty to initiative next turn due to a critical hit
	 */
	private int getTempInitPenalty() {
		return tempInitPenalty;
	}

	/**
	 * the penalty to initiative next turn due to a critical hit
	 * 
	 * @param tempInitPenalty the penalty to initiative next turn due to a critical hit
	 */
	private void setTempInitPenalty(final int tempInitPenalty) {
		this.tempInitPenalty = tempInitPenalty < 0 ? 0 : tempInitPenalty;
	}
	
	/**
	 * the penalty to EW due to critical hits
	 * 
	 * @return the penalty to EW due to critical hits
	 */
	public int getEWPenalty() {
		return eWPenalty;
	}

	/**
	 * the penalty to EW due to critical hits
	 * 
	 * @param eWPenalty the penalty to EW due to critical hits
	 */
	private void setEWPenalty(final int eWPenalty) {
		this.eWPenalty = eWPenalty < 0 ? 0 : eWPenalty;
	}

	/**
	 * whether the sensors are disrupted for a turn due to a critical hit
	 * 
	 * @return whether the sensors are disrupted for a turn due to a critical hit
	 */
	public boolean isSensorsDisrupted() {
		return sensorsDisrupted;
	}

	/**
	 * whether the sensors are disrupted for a turn due to a critical hit
	 * 
	 * @param sensorsDisrupted whether the sensors are disrupted for a turn due to a critical hit
	 */
	private void setSensorsDisrupted(final boolean sensorsDisrupted) {
		this.sensorsDisrupted = sensorsDisrupted;
	}

	/**
	 * the penalty to fire control due to critical hits
	 * 
	 * @return the penalty to fire control due to critical hits
	 */
	public int getFireControlPenalty() {
		return fireControlPenalty;
	}

	/**
	 * the penalty to fire control due to critical hits
	 * 
	 * @param fireControlPenalty the penalty to fire control due to critical hits
	 */
	private void setFireControlPenalty(final int fireControlPenalty) {
		this.fireControlPenalty = fireControlPenalty < 0 ? 0 : fireControlPenalty;
	}
	
	/**
	 * whether there is severe power loss for a turn due to a critical hit
	 * 
	 * @return whether there is severe power loss for a turn due to a critical hit
	 */
	public boolean isSeverePowerLoss() {
		return severePowerLoss;
	}

	/**
	 * whether there is severe power loss for a turn due to a critical hit
	 * 
	 * @param severePowerLoss whether there is severe power loss for a turn due to a critical hit
	 */
	private void setSeverePowerLoss(final boolean severePowerLoss) {
		this.severePowerLoss = severePowerLoss;
	}

	protected void resolveCriticalHit(final int roll) {
		Logger.info(roll + " rolled for " + getFullName() + "...");

		if (roll <= 8) {
			// sensors disrupted, cannot alter EW allocation next round
			setSensorsDisrupted(true);
			Logger.info("    - sensors disrupted, cannot alter EW allocation next round");
		} else if (roll <= 11) {
			// communications disrupted, -1 initiative
			setInitPenalty(getInitPenalty() + 1);
			Logger.info("    - communications disrupted, -1 initiative");
		} else if (roll <= 14) {
			// fire control computers scrambled, all weapons -1 to hit
			setFireControlPenalty(getFireControlPenalty() + 1);
			Logger.info("    - fire control computers scrambled, all weapons -1 to hit");
		} else if (roll <= 17) {
			// sensor controls damaged, -2 EW and no more than half EW offensive
			setEWPenalty(getEWPenalty() + 2);
			Logger.info("    - sensor controls damaged, -2 EW and no more than half EW offensive");
		} else if (roll <= 20) {
			// major communications disruption, -4 initiative next turn, -2 initiative thereafter
			setTempInitPenalty(getTempInitPenalty() + 2);
			setInitPenalty(getInitPenalty() + 2);
			Logger.info("    - major communications disruption, -4 initiative next turn, -2 initiative thereafter");
		} else if (roll <= 23) {
			// communication severely scrambled, combine previous two criticals
			setEWPenalty(getEWPenalty() + 2);
			Logger.info("    - sensor controls damaged, -2 EW and no more than half EW offensive");
			setTempInitPenalty(getTempInitPenalty() + 2);
			setInitPenalty(getInitPenalty() + 2);
			Logger.info("    - major communications disruption, -4 initiative next turn, -2 initiative thereafter");
		} else {
			// severe power loss, cannot maneuver/use sensors/fire weapons next turn, also suffers 14/17/20
			setFireControlPenalty(getFireControlPenalty() + 1);		
			Logger.info("    - fire control computers scrambled, all weapons -1 to hit");
			setEWPenalty(getEWPenalty() + 2);
			Logger.info("    - sensor controls damaged, -2 EW and no more than half EW offensive");
			setTempInitPenalty(getTempInitPenalty() + 2);
			setInitPenalty(getInitPenalty() + 2);
			Logger.info("    - major communications disruption, -4 initiative next turn, -2 initiative thereafter");
		}
	}

	protected void adjustSystem() {
		setTempInitPenalty(0);
		setSensorsDisrupted(false);
		setSeverePowerLoss(false);
	}
	
	@Override
	public Color getOutlineFillColor() {
		return OUTLINE_FILL_COLOR;
	}
	
	public int getRecognitionOrder() {
		return 200;
	}
	
}
