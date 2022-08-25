/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import java.awt.Color;
import java.awt.Font;

import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;
import net.b5gamer.util.PropertyReader;

/**
 * A Tendril is used to absorb the energy of an incoming attack for later diffusion 
 * by an {@link net.b5gamer.b5wars.unit.structural.system.EnergyDiffuser} 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Tendril extends System implements DamageAbsorbingSystem {

	private static final long serialVersionUID = 3847647975240498340L;

	public static final Font ABSORBED_FONT = new Font("Arial", Font.PLAIN, 8);
	public static final Font RATING_FONT   = new Font("Arial", Font.BOLD,  8);
	
	private final int baseMaximumRating;  // base maximum amount of damage that can be absorbed at any one time
	private       int maximumRating;      // maximum amount of damage that can be absorbed at any one time
	private       int absorbedDamage = 0; // amount of damage absorbed into the tendril waiting to be discharged     
	
	/**
	 * @param damageBoxes not used
	 * @param armor       not used
	 * @param arc         not used
	 * @param name        not used
	 * @param properties  additional properties 
	 */
	public Tendril(final int damageBoxes, final int armor, final Arc arc, final String name,
			final Properties properties) {
		this(PropertyReader.getInteger(properties, "maximumRating")); 
	}
	
	/**
	 * @param maximumRating the maximum amount of damage that can be absorbed at any one time
	 */
	public Tendril(final int maximumRating) {
		super(0, 0, null, null);
		
        if (!(maximumRating > 0)) {
            throw new IllegalArgumentException("maximumRating must be a positive number");
        } 
        
        this.baseMaximumRating = maximumRating;		
        this.maximumRating     = maximumRating;
	}
		
	@Override
	public Properties getInitProperties() {
		Properties properties = new Properties();
		properties.setProperty("maximumRating", String.valueOf(getBaseMaximumRating()));
		
		return properties;
	}
	
	public String getType() {
		return "Tendril";
	}
	
	public boolean isValidTarget() {
		return false;
	}

	/**
	 * the base maximum amount of damage that can be absorbed at any one time
	 * 
	 * @return the base maximum amount of damage that can be absorbed at any one time
	 */
	public int getBaseMaximumRating() {
		return baseMaximumRating;
	}
	
	/**
	 * the maximum amount of damage that can be absorbed at any one time
	 * 
	 * @return the maximum amount of damage that can be absorbed at any one time
	 */
	public int getMaximumRating() {
		return maximumRating;
	}

	/**
	 * the maximum amount of damage that can be absorbed at any one time
	 * 
	 * @param maximumRating the maximum amount of damage that can be absorbed at any one time
	 */
	protected void setMaximumRating(final int maximumRating) {
		if (getAbsorbedDamage() > maximumRating) {
			setAbsorbedDamage(maximumRating);			
		}
		
		this.maximumRating = maximumRating < 0 ? 0 : maximumRating;
	}

	/**
	 * the amount of damage absorbed into the tendril waiting to be discharged
	 * 
	 * @return the amount of damage absorbed into the tendril waiting to be discharged
	 */
	public int getAbsorbedDamage() {
		return absorbedDamage;
	}

	/**
	 * the amount of damage absorbed into the tendril waiting to be discharged
	 * 
	 * @param storedDamage the amount of damage absorbed into the tendril waiting to be discharged
	 */
	protected void setAbsorbedDamage(final int absorbedDamage) {
        if (absorbedDamage > getMaximumRating()) {
            throw new IllegalArgumentException("absorbedDamage cannot exceed Maximum Rating");
        } 
		
		this.absorbedDamage = absorbedDamage < 0 ? 0 : absorbedDamage;
	}
	
	public boolean isTargetArmorApplied() {
		return true;
	}	
	
	public int getAbsorptionCapacity() {
		return getMaximumRating() - getAbsorbedDamage();
	}
	
	public int absorbDamage(final int damage) {
		if (damage < 0) {
			throw new IllegalArgumentException("damage cannot be less than 0");
		}
		
		// TODO flash damage
		int overkill = getAbsorbedDamage() + damage - getAbsorptionCapacity();
		overkill = (overkill < 0) ? 0 : overkill;
		setAbsorbedDamage(getAbsorbedDamage() + (damage - overkill));
		 
		return overkill;	
	}
		
	protected void resolveCriticalHit(final int roll) {
		// critical hits handled by energy diffuser
	}	

	protected void adjustSystem() {
	}

	@Override
	public String getDescription() {
		StringBuilder status = new StringBuilder(getFullName());
		status.append(" (Maximum Rating ").append(getMaximumRating()).append("/").append(getBaseMaximumRating());
		status.append(" Absorbed ").append(getAbsorbedDamage()).append("/").append(getMaximumRating()).append(")");
		
		return status.toString();
	}
	
	@Override
	public int getAnnotationCount() {
		return 2;
	}
	
	@Override
	public String getAnnotation(int index) {
		if ((index < 0) || (index >= getAnnotationCount())) {
			throw new IndexOutOfBoundsException();
		}
		
		if (index == 0) {
			return (getAbsorbedDamage() > 0) ? String.valueOf(getAbsorbedDamage()) : "";
		} else {
			return String.valueOf(getMaximumRating());
		}
	}
	
	@Override
	public Font getAnnotationFont(int index) {
		if ((index < 0) || (index >= getAnnotationCount())) {
			throw new IndexOutOfBoundsException();
		}
		
		return (index == 0) ? ABSORBED_FONT : RATING_FONT;
	}
	
	@Override
	public Color getAnnotationColor(int index) {
		if ((index < 0) || (index >= getAnnotationCount())) {
			throw new IndexOutOfBoundsException();
		}

		if (index == 0) {
			return ANNOTATION_COLOR;
		} else {
			return (getMaximumRating() < getBaseMaximumRating()) ? ANNOTATION_CHANGED_COLOR : ANNOTATION_COLOR;
		}
	}
	
}
