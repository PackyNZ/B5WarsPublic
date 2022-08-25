/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.system;

import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.b5wars.weapon.ShipWeapon;
import net.b5gamer.icon.IconPosition;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * !!--- work in progress ---!!
 * A Weapon represents a particular weapon mounted on a vessel
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
// TODO separate out DefensiveSystems??
public class Weapon extends PoweredSystem implements DirectionalSystem, DefensiveSystem {

	private static final long serialVersionUID = -2720211185729975826L;
	
	// TODO add fields
	private final ShipWeapon   weapon; // the weapon this system represents, may be null for special weapons such as shields
	private       IconPosition weaponNumberPosition = new IconPosition(); // position of the number icon

	/**
	 * @param damageBoxes not used
	 * @param armor       amount of armor protecting the weapon
	 * @param arc         arc for incoming fire and field of fire for the weapon
	 * @param number      the weapon number
	 * @param properties  additional properties 
	 */
	public Weapon(final int damageBoxes, final int armor, final Arc arc, final String number,
			final Properties properties) {
		this(properties.getProperty("weaponType"), armor, arc, 
				((number != null) && (number.trim().length() > 0)) ? Integer.parseInt(number) : 0);
	}
	
	/**
	 * @param weaponType the weapon type this system represents
	 * @param armor      amount of armor protecting the weapon
	 * @param arc        arc for incoming fire and field of fire for the weapon
	 * @param number     the weapon number
	 */
	public Weapon(final String weaponType, final int armor, final Arc arc, final int number) {
		this(ShipWeapon.getWeapon(weaponType), armor, arc, number);
	}

	/**
	 * @param weapon the weapon this system represents
	 * @param armor  amount of armor protecting the weapon
	 * @param arc    arc for incoming fire and field of fire for the weapon
	 * @param number the weapon number
	 */
	public Weapon(final ShipWeapon weapon, final int armor, final Arc arc, final int number) {
		super((weapon != null) ? weapon.getDamageBoxes() : 0, armor, arc, (number > 0) ? String.valueOf(number) : null, 
				(weapon != null) ? weapon.getPower() : 0);
		
        if (weapon == null) {
            throw new IllegalArgumentException("Unknown weapon");
        } 
        
		this.weapon = weapon;
	}
	
	/**
	 * @param damageBoxes amount of damage the weapon can sustain before being destroyed
	 * @param armor       amount of armor protecting the weapon
	 * @param arc         arc for incoming fire and/or outgoing effects, may be null??
	 * @param number      name or weapon number, may be null
	 * @param power       amount of power required for the shield to function
	 */
	public Weapon(final int damageBoxes, final int armor, final Arc arc, final String number,
			final int power) {
		super(damageBoxes, armor, arc, number, power);
		
		this.weapon = null;
	}
	
	public String getType() {
		return (getWeapon() != null) ? getWeapon().getType() : "Unknown Weapon";
	}

	public String getFullName() {
		return getName() != null ? getType() + " #" + getName() : getType();
	}
		
	@Override
	public boolean isDamageBoxesFixed() {
		return true;
	}
	
	/**
	 * the weapon this system represents
	 * 
	 * @return the weapon this system represents
	 */
	public ShipWeapon getWeapon() {
		return weapon;
	}

	public int getDefensiveBonus() {
		return (getWeapon() != null) ? getWeapon().getDefensiveBonus() : 0;
	}
	
	public int getDamageReducingCapacity() {
		return 0;
	}
	
	public int applyDamageReduction(final int damage) {
		return damage;		
	}
	
	protected void resolveCriticalHit(final int roll) {
		Logger.info(roll + " rolled for " + getFullName() + "...");

		if (roll <= 12) {
			// no critical hit
			Logger.info("    - no critical hit");
		} else if (roll <= 18) {
			// TODO range reduced, distance factor reduced by 1 hex
			Logger.info("    - range reduced, distance factor reduced by 1 hex");
		} else if (roll <= 24) {
			// TODO damage reduced, -2 penalty to damage per die
			Logger.info("    - damage reduced, -2 penalty to damage per die");
		} else {
			// TODO both above effects suffered
			Logger.info("    - range reduced, distance factor reduced by 1 hex");
			Logger.info("    - damage reduced, -2 penalty to damage per die");
		}
		
		// TODO antimatter weapon criticals
	}

	protected void adjustSystem() {
		// TODO
	}
	
	public IconPosition getWeaponNumberPosition() {
		return weaponNumberPosition;
	}

	public void setWeaponNumberPosition(IconPosition weaponNumberPosition) {
		this.weaponNumberPosition = (weaponNumberPosition != null) ? weaponNumberPosition : new IconPosition();
	}
	
	@Override
	public int getRecognitionOrder() {
		return 1000;
	}
	
}
