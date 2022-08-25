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
 * A Shield is a defensive device that attempts to deflect or block some of the effect of incoming
 * fire from striking the hull of a vessel
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public abstract class Shield extends Weapon {

	private static final long serialVersionUID = 6250116886119655993L;

	private final int     baseShieldFactor;       // base strength of the shield
	private       int     shieldFactor;           // strength of the shield
	private       boolean damageAbsorbing = true; // whether the shield can absorb damage
	
	/**
	 * @param damageBoxes  amount of damage the shield can sustain before being destroyed
	 * @param armor        amount of armor protecting the shield
	 * @param arc          arc for incoming fire and arc covered by the shield 
	 * @param number       the weapon number
	 * @param power        amount of power required for the shield to function
	 * @param shieldFactor strength of the shield
	 */
	public Shield(final int damageBoxes, final int armor, final Arc arc, final int number, final int power, 
			final int shieldFactor) {
		super(damageBoxes, armor, arc, String.valueOf(number), power);
		
        if (!(shieldFactor > 0)) {
            throw new IllegalArgumentException("shieldFactor must be a positive number");
        } 
		
        this.baseShieldFactor = shieldFactor;
		this.shieldFactor     = shieldFactor;
	}

	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("shieldFactor", String.valueOf(getBaseShieldFactor()));
		
		return properties;
	}
	
	/**
	 * the base strength of the shield
	 * 
	 * @return the base strength of the shield
	 */
	public int getBaseShieldFactor() {
		return baseShieldFactor;
	}
	
	/**
	 * the strength of the shield
	 * 
	 * @return the strength of the shield
	 */
	public int getShieldFactor() {
		return shieldFactor;
	}

	/**
	 * the strength of the shield
	 * 
	 * @param shieldFactor the strength of the shield
	 */
	protected void setShieldFactor(final int shieldFactor) {
        if (shieldFactor > getBaseShieldFactor()) {
            throw new IllegalArgumentException("shieldFactor cannot exceed base shield factor");
        } 

		this.shieldFactor = shieldFactor < 0 ? 0 : shieldFactor;		
	}

	/**
	 * whether the shield can absorb damage
	 * 
	 * @return whether the shield can absorb damage
	 */
	public boolean isDamageAbsorbing() {
		return damageAbsorbing;
	}

	/**
	 * whether the shield can absorb damage
	 * 
	 * @param damageAbsorbing whether the shield can absorb damage
	 */
	protected void setDamageAbsorbing(final boolean damageAbsorbing) {
		this.damageAbsorbing = damageAbsorbing;
	}
	
	public int getDefensiveBonus() {
		return getShieldFactor();
	}
	
	public int getDamageReducingCapacity() {
		return isDamageAbsorbing() ? getShieldFactor() : 0;
	}
	
	public int applyDamageReduction(final int damage) {
		int remainingDamage = damage - getDamageReducingCapacity();
		return (remainingDamage < 0) ? 0 : remainingDamage;		
	}
	
	protected void resolveCriticalHit(final int roll) {
		Logger.info(roll + " rolled for " + getFullName() + "...");

		if (roll <= 15) {
			// no critical hit
			Logger.info("    - no critical hit");
		} else if (roll <= 19) {
			// strength reduced: -1 shield factor
			Logger.info("    - strength reduced: -1 shield factor");
			setShieldFactor(getShieldFactor() - 1);
		} else if (roll <= 24) {
			// effectiveness reduced: no longer absorbs damage
			Logger.info("    - effectiveness reduced: no longer absorbs damage");
			setDamageAbsorbing(false);
		} else {
			// both above effects suffered
			Logger.info("    - strength reduced: -1 shield factor");
			setShieldFactor(getShieldFactor() - 1);
			Logger.info("    - effectiveness reduced: no longer absorbs damage");
			setDamageAbsorbing(false);
		}
	}
	
	protected void adjustSystem() {
	}

	@Override
	public String getDescription() {
		StringBuilder status = new StringBuilder(getFullName());
		status.append(" (Armor ").append(getArmor()).append("/").append(getBaseArmor());
		status.append(", Damage ").append(getDamageBoxes()).append("/").append(getBaseDamageBoxes());
		status.append(", Shield Factor ").append(getShieldFactor()).append("/").append(getBaseShieldFactor()).append(")");
		
		return status.toString();
	}
	
	@Override
	public int getAnnotationCount() {
		return 1;
	}
	
	@Override
	public String getAnnotation(int index) {
		return String.valueOf(getShieldFactor());
	}
	
	@Override
	public Color getAnnotationColor(int index) {
		return (getShieldFactor() < getBaseShieldFactor()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
	}
	
	@Override
	public int getRecognitionOrder() {
		return 1100;
	}
	
}
