package net.b5gamer.b5wars.weapon.type;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.unit.structural.system.Weapon;

/**
 * !!--- work in progress ---!!
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public abstract class WeaponType {

	// -!-

	private static final ArrayList<WeaponType> weaponTypes = new ArrayList<WeaponType>(0); // all weapon types
	
	// TODO enum??
	public static final WeaponType ANTIMATTER      = new Antimatter();
	public static final WeaponType BALLISTIC       = new Ballistic();
	public static final WeaponType ELECTROMAGNETIC = new Electromagnetic();
	public static final WeaponType GRAVITIC        = new Gravitic();
	public static final WeaponType IONIC           = new Ionic();
	public static final WeaponType LASER           = new Laser();
	public static final WeaponType MATTER          = new Matter();
	public static final WeaponType MOLECULAR       = new Molecular();
	public static final WeaponType PARTICLE        = new Particle();
	public static final WeaponType PASMA           = new Plasma();
		
	private final String name; // name of the weapon type
	private final String code; // code of the weapon type
	
	/**
	 * a list of all weapon types
	 * 
	 * @return a list of all weapon types
	 */
	protected static final List<WeaponType> getWeaponTypes() {
		return weaponTypes;
	}
	
    /**
     * an iterator of all weapon types
     *  
	 * @return an iterator of all weapon types
	 */
	public static final Iterator<WeaponType> getWeaponTypeIterator() {
		return getWeaponTypes().iterator();
	}
	
    /**
     * find and return a weapon type with a given name
     *
     * @param  name name of the weapon type to return
     * @return      the weapon type, or null if a weapon type with the given name doesn't exist
     */
    public static final WeaponType getWeaponTypeByName(final String name) {
    	WeaponType result = null;

    	for (Iterator<WeaponType> iterator = getWeaponTypes().iterator(); iterator.hasNext();) {
    		WeaponType type = iterator.next();

            if (type.getName().equalsIgnoreCase(name)) {
                result = type;
                break;
            }                
    	} 

        return result;
    }
    
    /**
     * find and return a weapon type with a given code
     *
     * @param  code code of the weapon type to return
     * @return      the weapon type, or null if a weapon type with the given code doesn't exist
     */
    public static final WeaponType getWeaponTypeByCode(final String code) {
    	WeaponType result = null;

    	for (Iterator<WeaponType> iterator = getWeaponTypes().iterator(); iterator.hasNext();) {
    		WeaponType type = iterator.next();

            if (type.getCode().equalsIgnoreCase(code)) {
                result = type;
                break;
            }                
    	} 

        return result;
    }    
    
	/**
	 * @param name name of the weapon type
	 * @param code code of the weapon type
	 */
	protected WeaponType(final String name, final String code) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 
        if (getWeaponTypeByName(name) != null) {
            throw new IllegalArgumentException("Weapon Type with same name already exists!");
        }
        if ((code == null) || !(code.length() > 0)) {
            throw new IllegalArgumentException("code cannot be null or blank");
        } 
        if (getWeaponTypeByCode(code) != null) {
            throw new IllegalArgumentException("Weapon Type with same code already exists!");
        }

		this.name = name;
		this.code = code;

		weaponTypes.add(this);		
	}
	
	/**
	 * the name
	 * 
	 * @return the name
	 */
	public final String getName() {
		return name;
	}

	/**
	 * the code
	 * 
	 * @return the code
	 */
	public final String getCode() {
		return code;
	}

	/**
	 * whether the weapon type is interceptable
	 * 
	 * @return whether the weapon type is interceptable
	 */
	public abstract boolean isInterceptable();	
	
	/**
	 * ...
	 * 
	 * @param weapon    weapon to resolve fire of against the target
	 * @param direction direction of weapon fire
	 * @param target    the unit that was hit
	 */
	public abstract void resolveHit(final Weapon weapon, final System target);
	
}
