/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.section;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocationChart;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.b5wars.unit.structural.system.Weapon;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * A Section represents a section on a vessel that can contain systems, however the loss of 
 * any structure blocks contained within the section does not constitute the destruction of 
 * the section 
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Section implements Serializable {
	
	private static final long serialVersionUID = 5709012191108988430L;

	// typical section names
	public static final String PRIMARY   = "PRIMARY";
	public static final String FORWARD   = "FORWARD";
	public static final String AFT       = "AFT";
	public static final String PORT      = "PORT";
	public static final String FWD_PORT  = "FWD PORT";
	public static final String AFT_PORT  = "AFT PORT";
	public static final String STARBOARD = "STARBOARD";
	public static final String FWD_STB   = "FWD STBD";
	public static final String AFT_STB   = "AFT STBD";
	
	private       StructuralUnit   parentUnit    = null;  // the unit this section is contained within
	private final String           name;                // name of the section
	private       Arc              arc           = null;  // arc this section covers for incoming fire purposes
	private       HitLocationChart hitLocationChart;    // hit location chart for this section
	private final List<System>     systems       = new ArrayList<System>(0); // all systems in this section
	private       boolean          destroyed     = false; // whether the section is destroyed and has fallen off
	private final List<Section>    childSections = new ArrayList<Section>(0); // all child sections
	
	/**
	 * @param name             name of the section
	 * @param arc              arc this section covers for incoming fire purposes
	 * @param hitLocationChart hit location chart for this section
	 * @param properties       additional properties 
	 */
	public Section(final String name, final Arc arc, final HitLocationChart hitLocationChart,
			final Properties properties) {
		this(name, arc, hitLocationChart);
	}
	
	/**
	 * @param name             name of the section
	 * @param arc              arc this section covers for incoming fire purposes
	 * @param hitLocationChart hit location chart for this section
	 */
	public Section(final String name, final Arc arc, final HitLocationChart hitLocationChart) {
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 

		this.name             = name;
        this.arc              = arc;
        this.hitLocationChart = hitLocationChart;
	}
	
	/**
	 * the properties for the section
	 * 
	 * @return the properties for the section
	 */
	public Properties getProperties() {
		return null;
	}
	
	/**
	 * the unit this section is contained within
	 * 
	 * @return the unit this section is contained within
	 */
	public StructuralUnit getParentUnit() {
		return parentUnit;
	}

	/**
	 * the unit this section is contained within
	 * 
	 * @param parent the unit this section is contained within
	 */
	public void setParentUnit(final StructuralUnit parent) {
		this.parentUnit = parent;
	}
	
	/**
	 * the name of the section
	 * 
	 * @return the name of the section
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * the arc this section covers for incoming fire purposes
	 * 
	 * @return the arc this section covers for incoming fire purposes
	 */
	public Arc getArc() {
		return arc;
	}
	
	/**
	 * the arc this section covers for incoming fire purposes
	 * 
	 * @param arc the arc this section covers for incoming fire purposes
	 */
	protected void setArc(final Arc arc) {
		this.arc = arc;
	}

	/**
	 * whether the sections arc contains the given angle
	 * 
	 * @return whether the sections arc contains the given angle
	 */
	public boolean isWithinArc(final int angle) {
		return ((getArc() != null) && (getArc().contains(angle)));
	}

	/**
	 * the hit location chart for this section
	 * 
	 * @return the hit location chart for this section
	 */
	public HitLocationChart getHitLocationChart() {
		return hitLocationChart;
	}
	
	/**
	 * the hit location chart for this section
	 * 
	 * @param hitLocationChart the hit location chart for this section
	 */	
	public void setHitLocationChart(HitLocationChart hitLocationChart) {
		this.hitLocationChart = hitLocationChart;
	}

	/**
	 * a list of all systems in this section
	 * 
	 * @return a list of all systems in this section
	 */
	protected List<System> getSystems() {
		return systems;
	}
	
	/**
	 * a list of all systems in this section and child sections
	 * 
	 * @return a list of all systems in this section and child sections
	 */
	protected List<System> getAllSystems() {
		List<System> result = new ArrayList<System>(0);
		result.addAll(getSystems());
		
        for (Iterator<Section> iterator = getChildSections().iterator(); iterator.hasNext();) {
            for (Iterator<System> iterator2 = iterator.next().getAllSystems().iterator(); iterator2.hasNext();) {
    			System system = iterator2.next();
    			
            	if (!result.contains(system)) {
            		result.add(system);
            	}
    		}
    	} 
		        
		return result;
	}
	
	/**
	 * an iterator for all systems
	 * 
	 * @return an iterator for all systems
	 */
	public Iterator<System> getSystemIterator() {
		return getSystems().iterator();
	}	
	
	/**
	 * add a system to the section
	 * 
	 * @param system the system to add
	 */
	public void addSystem(final System system) {
        if (system == null) {
            throw new IllegalArgumentException("system cannot be null");
        } 
        
        if (!getSystems().contains(system)) {
        	getSystems().add(system);
        	system.setParent(this);
        }
	}
	
	/**
	 * all systems in the section of a given class that are not destroyed
	 * 
	 * @param  systemClass the class of systems to return
	 * @return             list of all systems in the section of the given class that are not destroyed
	 */
	public List<System> getSystemsOfClass(final Class<?> systemClass) {
		return getSystemsOfClass(systemClass, false);
	}
	
	/**
	 * all systems in the section of a given class
	 * 
	 * @param  systemClass      the class of systems to return
	 * @param  includeDestroyed whether to include destroyed systems
	 * @return                  list of all systems in the section of the given class
	 */
	public List<System> getSystemsOfClass(final Class<?> systemClass, final boolean includeDestroyed) {
		List<System> result = new ArrayList<System>(0);
		
        if (systemClass == null) {
            throw new IllegalArgumentException("systemClass cannot be null");
        } 

        for (Iterator<System> iterator = getSystems().iterator(); iterator.hasNext();) {
			System system = iterator.next();
			
			if ((includeDestroyed || !system.isDestroyed()) && (systemClass.isInstance(system))) {
				result.add(system);
			}
		}
		
		return result;
	}
	
	/**
	 * all potentially targeted systems in the section of a given class
	 * 
	 * @param  systemClass the class of systems to return
	 * @return             list of all potentially targeted systems in the section of a given 
	 *                     class 
	 */
	public List<System> getTargetSystemsOfClass(final Class<?> systemClass) {
		List<System> result = new ArrayList<System>(0);
		
        if (systemClass == null) {
            throw new IllegalArgumentException("systemClass cannot be null");
        } 

        for (Iterator<System> iterator = getAllSystems().iterator(); iterator.hasNext();) {
			System system = iterator.next();
			
			if (system.isValidTarget() && (systemClass.isInstance(system))) {
				result.add(system);
			}
		}
		
		return result;	
	}
	
	/**
	 * all potentially targeted systems in this section of a given class, and are potentially within 
	 * arc. If mandatory is true, only those systems whose arc contains the given angle are returned. 
	 * If mandatory is false, systems within arc are returned as a priority, but if none exist then
	 * systems not in arc are returned instead 
	 * 
	 * @param  systemClass the class of systems to return
	 * @param  angle       angle to check whether it's contained with a systems arc
	 * @param  mandatory   whether it's mandatory for systems to be within arc
	 * @return             list of all potentially targeted systems in this section of the given class
	 *                     and are potentially within arc
	 */
	public List<System> getTargetSystemsWithinArc(final Class<?> systemClass, final int angle, 
			final boolean mandatory) {
        if (systemClass == null) {
            throw new IllegalArgumentException("systemClass cannot be null");
        } 

        return filterSystemsByArc(getTargetSystemsOfClass(systemClass), angle, mandatory);		
	}	
	
	/**
	 * all systems in the section of a given type that are not destroyed
	 * 
	 * @param  systemType the type of systems to return
	 * @return            list of all systems in the section of the given type that are not destroyed
	 */
	public List<System> getSystemsOfType(final String systemType) {
		return getSystemsOfType(systemType, false);
	}
	
	/**
	 * all systems in the section of a given type
	 * 
	 * @param  systemType       the type of systems to return
	 * @param  includeDestroyed whether to include destroyed systems
	 * @return                  list of all systems in the section of the given type
	 */
	public List<System> getSystemsOfType(final String systemType, final boolean includeDestroyed) {
		List<System> result = new ArrayList<System>(0);
		
        if ((systemType == null) || !(systemType.length() > 0)) {
            throw new IllegalArgumentException("systemType cannot be null or blank");
        } 

        for (Iterator<System> iterator = getSystems().iterator(); iterator.hasNext();) {
			System system = iterator.next();
			
			if ((includeDestroyed || !system.isDestroyed()) && (system.getType().equals(systemType) ||
					((Weapon.class.getSimpleName().equals(systemType)) && (system instanceof Weapon)))) {
				result.add(system);
			}
		}
		
		return result;
	}
	
	/**
	 * all potentially targeted systems in the section of a given type
	 * 
	 * @param  systemType the type of systems to return
	 * @return            list of all potentially targeted systems in the section of a given 
	 *                    type 
	 */
	public List<System> getTargetSystemsOfType(final String systemType) {
		List<System> result = new ArrayList<System>(0);
		
        if ((systemType == null) || !(systemType.length() > 0)) {
            throw new IllegalArgumentException("systemType cannot be null or blank");
        } 

        for (Iterator<System> iterator = getAllSystems().iterator(); iterator.hasNext();) {
			System system = iterator.next();
			
			if (system.isValidTarget() && (system.getType().equals(systemType) ||
					((Weapon.class.getSimpleName().equals(systemType)) && (system instanceof Weapon)))) {
				result.add(system);
			}
		}
		
		return result;	
	}
	
	/**
	 * all potentially targeted systems in this section of a given type, and are potentially within 
	 * arc. If mandatory is true, only those systems whose arc contains the given angle are returned. 
	 * If mandatory is false, systems within arc are returned as a priority, but if none exist then
	 * systems not in arc are returned instead 
	 * 
	 * @param  systemType the type of systems to return
	 * @param  angle      angle to check whether it's contained with a systems arc
	 * @param  mandatory  whether it's mandatory for systems to be within arc
	 * @return            list of all potentially targeted systems in this section of the given type
	 *                    and are potentially within arc
	 */
	public List<System> getTargetSystemsWithinArc(final String systemType, final int angle, 
			final boolean mandatory) {
        if ((systemType == null) || !(systemType.length() > 0)) {
            throw new IllegalArgumentException("systemType cannot be null or blank");
        } 

        return filterSystemsByArc(getTargetSystemsOfType(systemType), angle, mandatory);		
	}	
	
	/**
	 * filter systems by those whose arc potentially contain a given angle. If mandatory is true, only 
	 * those systems whose arc contains the given angle are returned. If mandatory is false, systems 
	 * within arc are returned as a priority, but if none exist then systems not in arc are returned 
	 * instead 
	 * 
	 * @param  systems    list of systems to filter
	 * @param  angle      angle to check whether it's contained with a systems arc
	 * @param  mandatory  whether it's mandatory for systems to be within arc
	 * @return            list of all systems that are potentially within arc
	 */
	protected static List<System> filterSystemsByArc(List<System> systems, final int angle, 
			final boolean mandatory) {
		List<System> result = new ArrayList<System>(0);
		
        if (systems == null) {
            throw new IllegalArgumentException("systems cannot be null");
        } 

        for (Iterator<System> iterator = systems.iterator(); iterator.hasNext();) {
			System system = iterator.next();
			
			if ((system.getArc() != null) && (system.getArc().contains(angle))) {
				result.add(system);
			}
		}
		
		if ((result.size() > 0) || (mandatory)) {
			return result;
		} else {
			return systems;
		}
	}
	
	/**
	 * all weapons in the section of a given class
	 * 
	 * @param  weaponClass      the class of weapons to return
	 * @param  includeDestroyed whether to include destroyed weapons
	 * @return                  list of all weapons in the section of the given class
	 */
	public List<Weapon> getWeaponsOfClass(final Class<?> weaponClass, final boolean includeDestroyed) {
		List<Weapon> result = new ArrayList<Weapon>(0);
		
        if (weaponClass == null) {
            throw new IllegalArgumentException("weaponClass cannot be null");
        } 

        for (Iterator<System> iterator = getSystems().iterator(); iterator.hasNext();) {
			System system = iterator.next();
			
			if ((includeDestroyed || !system.isDestroyed()) && (system instanceof Weapon) && 
					(weaponClass.isInstance(system) || weaponClass.isInstance(((Weapon) system).getWeapon()))) {
				result.add((Weapon) system);
			}
		}
		
		return result;
	}
	
	/**
	 * all potentially targeted weapons in the section of a given class
	 * 
	 * @param  weaponClass the class of weapons to return
	 * @return             list of all potentially targeted weapons in the section of a given 
	 *                     class 
	 */
	public List<Weapon> getTargetWeaponsOfClass(final Class<?> weaponClass) {
		List<Weapon> result = new ArrayList<Weapon>(0);
		
        if (weaponClass == null) {
            throw new IllegalArgumentException("weaponClass cannot be null");
        } 

        for (Iterator<System> iterator = getAllSystems().iterator(); iterator.hasNext();) {
			System system = iterator.next();
			
			if (system.isValidTarget() && (system instanceof Weapon) && 
					(weaponClass.isInstance(system) || weaponClass.isInstance(((Weapon) system).getWeapon()))) {
				result.add((Weapon) system);
			}
		}
		
		return result;
	}
	
	/**
	 * all potentially targeted weapons in this section of a given class, and are potentially within 
	 * arc. If mandatory is true, only those weapons whose arc contains the given angle are returned. 
	 * If mandatory is false, weapons within arc are returned as a priority, but if none exist then
	 * weapons not in arc are returned instead 
	 * 
	 * @param  weaponClass the class of weapons to return
	 * @param  angle       angle to check whether it's contained with a weapons arc
	 * @param  mandatory   whether it's mandatory for weapons to be within arc
	 * @return             list of all potentially targeted weapons in this section of the given class
	 *                     and are potentially within arc
	 */
	public List<Weapon> getTargetWeaponsWithinArc(final Class<?> weaponClass, final int angle, 
			final boolean mandatory) {
        if (weaponClass == null) {
            throw new IllegalArgumentException("weaponClass cannot be null");
        } 

        return filterWeaponsByArc(getTargetWeaponsOfClass(weaponClass), angle, mandatory);		
	}
	
	/**
	 * all weapons in the section of a given type
	 * 
	 * @param  weaponType       the type of weapons to return
	 * @param  includeDestroyed whether to include destroyed weapons
	 * @return                  list of all weapons in the section of the given type
	 */
	public List<Weapon> getWeaponsOfType(final String weaponType, final boolean includeDestroyed) {
		List<Weapon> result = new ArrayList<Weapon>(0);
		
        if ((weaponType == null) || !(weaponType.length() > 0)) {
            throw new IllegalArgumentException("weaponType cannot be null or blank");
        } 

        for (Iterator<System> iterator = getSystems().iterator(); iterator.hasNext();) {
			System system = iterator.next();
			
			if ((includeDestroyed || !system.isDestroyed()) && (system instanceof Weapon) && 
					(system.getType().equals(weaponType))) {
				result.add((Weapon) system);
			}
		}
		
		return result;
	}
	
	/**
	 * all potentially targeted weapons in the section of a given type
	 * 
	 * @param  weaponType the type of weapons to return
	 * @return            list of all potentially targeted weapons in the section of a given 
	 *                    type 
	 */
	public List<Weapon> getTargetWeaponsOfType(final String weaponType) {
		List<Weapon> result = new ArrayList<Weapon>(0);
		
        if ((weaponType == null) || !(weaponType.length() > 0)) {
            throw new IllegalArgumentException("weaponType cannot be null or blank");
        } 

        for (Iterator<System> iterator = getAllSystems().iterator(); iterator.hasNext();) {
			System system = iterator.next();
			
			if (system.isValidTarget() && (system instanceof Weapon) && (system.getType().equals(weaponType))) {
				result.add((Weapon) system);
			}
		}
		
		return result;
	}
	
	/**
	 * all potentially targeted weapons in this section of a given type, and are potentially within 
	 * arc. If mandatory is true, only those weapons whose arc contains the given angle are returned. 
	 * If mandatory is false, weapons within arc are returned as a priority, but if none exist then
	 * weapons not in arc are returned instead 
	 * 
	 * @param  weaponType the type of weapons to return
	 * @param  angle      angle to check whether it's contained with a weapons arc
	 * @param  mandatory  whether it's mandatory for weapons to be within arc
	 * @return            list of all potentially targeted weapons in this section of the given type
	 *                    and are potentially within arc
	 */
	public List<Weapon> getTargetWeaponsWithinArc(final String weaponType, final int angle, 
			final boolean mandatory) {
        if ((weaponType == null) || !(weaponType.length() > 0)) {
            throw new IllegalArgumentException("weaponType cannot be null or blank");
        } 

        return filterWeaponsByArc(getTargetWeaponsOfType(weaponType), angle, mandatory);		
	}
	
	/**
	 * filter weapons by those whose arc potentially contain a given angle. If mandatory is true, only 
	 * those weapons whose arc contains the given angle are returned. If mandatory is false, weapons 
	 * within arc are returned as a priority, but if none exist then weapons not in arc are returned 
	 * instead 
	 * 
	 * @param  weapons    list of weapons to filter
	 * @param  angle      angle to check whether it's contained with a systems arc
	 * @param  mandatory  whether it's mandatory for systems to be within arc
	 * @return            list of all systems that are potentially within arc
	 */
	protected static List<Weapon> filterWeaponsByArc(List<Weapon> weapons, final int angle, 
			final boolean mandatory) {
		List<Weapon> result = new ArrayList<Weapon>(0);
		
        if (weapons == null) {
            throw new IllegalArgumentException("weapons cannot be null");
        } 

        for (Iterator<Weapon> iterator = weapons.iterator(); iterator.hasNext();) {
        	Weapon weapon = iterator.next();
			
			if ((weapon.getArc() != null) && (weapon.getArc().contains(angle))) {
				result.add(weapon);
			}
		}
		
		if ((result.size() > 0) || (mandatory)) {
			return result;
		} else {
			return weapons;
		}
	}
	
	/**
	 * the number of sections contained in this section
	 * 
	 * @return the number of sections contained in this section
	 */
	public int getSectionCount() {
		return 1;
	}
	
	/**
	 * list of all child sections
	 * 
	 * @return list of all child sections
	 */	
	protected List<Section> getChildSections() {
		return childSections;
	}

	/**
	 * add a child section
	 * 
	 * @param section the child section to add
	 */
	public void addChildSection(final ChildSection section){
        if (section == null) {
            throw new IllegalArgumentException("section cannot be null");
        } 
	        
        if (!getChildSections().contains(section)) {
        	getChildSections().add(section);
        	section.addParentSection(this);
        }
	}
	
	/**
	 * whether the section is structurally intact (true) or is going to fall off
	 * 
	 * @return whether the section is structurally intact (true) or is going to fall off
	 */
	public boolean isStructurallySound() {
		return true;
	}
	
	/**
	 * whether the section is destroyed and has fallen off
	 * 
	 * @return whether the section is destroyed and has fallen off
	 */
	public boolean isDestroyed() {
		return destroyed;
	}

	/**
	 *  whether the section is destroyed and has fallen off
	 *  
	 * @param destroyed whether the section is destroyed and has fallen off
	 */
	protected void setDestroyed(final boolean destroyed) {
		this.destroyed = destroyed;
	}
			
	/**
	 *  destroy the section and all systems within it 
	 */
	public void destroy() {
		if (!isDestroyed()) {
			List<System> intactSystems = getSystems();
			for (Iterator<System> iterator = intactSystems.iterator(); iterator.hasNext();) {
				System system = iterator.next();
				
				if (!system.isDestroyed()) {
					system.setDestroyed(true);
				}
			}
		}
		
		setDestroyed(true);			
	}
	
	/**
	 * handle any end of turn actions
	 */
	public void handleEndOfTurnActions() {
		for (Iterator<System> iterator = getSystems().iterator(); iterator.hasNext();) {
			iterator.next().handleEndOfTurnActions();
		}

		// destroy all systems attached to destroyed structure blocks
		if (!isDestroyed() && !isStructurallySound()) {
			destroy();
		}
	}
	
	@Override
	public String toString() {
		return getName();
	}
	
}
