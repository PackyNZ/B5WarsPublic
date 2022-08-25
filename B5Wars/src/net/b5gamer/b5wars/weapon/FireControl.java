/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.weapon;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import net.b5gamer.b5wars.unit.Size;

/**
 * FireControl represents whether a specific weapon can be used to attack vessels of a
 * specific {@link net.b5gamer.b5wars.unit.Size}, and if so the attack modifier when doing so 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class FireControl implements Serializable {
	
	private static final long serialVersionUID = 7209760791961781836L;

	protected final Map<Size, Integer> toHitModifiers = new HashMap<Size, Integer>(); // all to hit modifiers
	protected final boolean            notApplicable; // whether the fire control is not applicable (e.g. n/a or --/--/--)

	/**
	 * create a fire control that is not applicable (e.g. n/a or --/--/--)
	 */
	public FireControl() {
		this(null, null, null);
	}
	
	/**
	 * create a fire control with the specified to hit modifiers
	 * 
	 * @param largeModifier  to hit modifier vs large/enormous units, may be null for cannot be targetted
	 * @param mediumModifier to hit modifier vs medium units, may be null for cannot be targetted
	 * @param smallModifier  to hit modifier vs small units, may be null for cannot be targetted
	 */
	public FireControl(final Integer largeModifier, final Integer mediumModifier, final Integer smallModifier) {
		toHitModifiers.put(Size.ENORMOUS, largeModifier);
		toHitModifiers.put(Size.LARGE,    largeModifier);
		toHitModifiers.put(Size.MEDIUM,   mediumModifier);
		toHitModifiers.put(Size.SMALL,    smallModifier);
		
		notApplicable = (largeModifier == null) && (mediumModifier == null) && (smallModifier == null);
	}

	/**
	 * all to hit modifiers
	 * 
	 * @return all to hit modifiers
	 */
	protected Map<Size, Integer> getToHitModifiers() {
		return toHitModifiers;
	}

	/**
	 * whether the fire control is not applicable (e.g. n/a or --/--/--)
	 * 
	 * @return whether the fire control is not applicable (e.g. n/a or --/--/--)
	 */
	public boolean isNotApplicable() {
		return notApplicable;
	}

	/**
	 * whether a unit of a given size can be targeted using this fire control
	 * 
	 * @param  size the size of unit to check against
	 * @return      whether a unit of the given size can be targeted using this fire control
	 */
	public final boolean isValidTarget(final Size size) {
		return !isNotApplicable() && getToHitModifiers().get(size) != null;
	}
	
	/**
	 * the to hit modifier against a unit of the specified size
	 * 
	 * @param  size the size of unit to obtain the modifier for 
	 * @return      the to hit modifier against a unit of the specified size
	 */
	public final int getToHitModifier(final Size size) {
		if (isValidTarget(size)) {
			return getToHitModifiers().get(size).intValue();
		} else {
			throw new IllegalArgumentException("Fire Control cannot target a unit of the specified size");
		}
	}
	
	@Override
	public String toString() {	
		if (isNotApplicable()) {
			return "n/a";
		} else {
			StringBuilder description = new StringBuilder();
			
			description.append(getFormattedModifier(getToHitModifiers().get(Size.LARGE)));
			description.append("/");
			description.append(getFormattedModifier(getToHitModifiers().get(Size.MEDIUM)));
			description.append("/");
			description.append(getFormattedModifier(getToHitModifiers().get(Size.SMALL)));

			return description.toString();
		}	
	}
	
	/**
	 * return a formatted String representation of a to hit modifier (e.g. +4, -2, --)
	 * 
	 * @param  modifier the to hit modifier to format
	 * @return          formatted String representation of the to hit modifier (e.g. +4, -2, --)
	 */
	protected String getFormattedModifier(final Integer modifier) {
		if (modifier == null) {
			return "--";
		} else {
			return (modifier.intValue() >= 0) ? "+" + String.valueOf(modifier.intValue()) : String.valueOf(modifier.intValue());
		}
	}
	
}
