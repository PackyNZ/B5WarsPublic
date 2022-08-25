/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.section;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocationChart;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.b5wars.unit.structural.system.Weapon;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * A MultiSection is a section on a vessel that can contain systems and also sections 
 * as sub-sections, which can be used for grouping various systems or sharing a hit 
 * location chart
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class MultiSection extends Section implements SectionCollection {
	
	private static final long serialVersionUID = 2878910808631285292L;

	private final List<Section> subSections = new ArrayList<Section>(0); // all sub-sections

	/**
	 * @param name             name of the section
	 * @param arc              arc this section covers for incoming fire purposes, may be null
	 * @param hitLocationChart hit location chart for this section, may be null
	 * @param properties       additional properties 
	 */
	public MultiSection(final String name, final Arc arc, final HitLocationChart hitLocationChart,
			final Properties properties) {
		this(name, arc, hitLocationChart);
	}
	
	/**
	 * @param name             name of the section
	 * @param arc              arc this section covers for incoming fire purposes, may be null
	 * @param hitLocationChart hit location chart for this section, may be null
	 */
	public MultiSection(final String name, final Arc arc, final HitLocationChart hitLocationChart) {
		super(name, arc, hitLocationChart);
	}
		
	/**
	 * a list of all systems in this section, child sections, and sub-sections
	 * 
	 * @return a list of all systems in this section, child sections, and sub-sections
	 */
	@Override
	protected List<System> getAllSystems() {
		List<System> result = new ArrayList<System>(0);
		result.addAll(super.getAllSystems());
		
        for (Iterator<Section> iterator = getSubSections().iterator(); iterator.hasNext();) {
            for (Iterator<System> iterator2 = iterator.next().getAllSystems().iterator(); iterator2.hasNext();) {
    			System system = iterator2.next();
    			
            	if (!result.contains(system)) {
            		result.add(system);
            	}
    		}
    	} 
		
		return result;
	}
	
	@Override
	public List<System> getSystemsOfClass(final Class<?> systemClass, final boolean includeDestroyed) {
		List<System> result = new ArrayList<System>(0);
		
        if (systemClass == null) {
            throw new IllegalArgumentException("systemClass cannot be null");
        } 

        result.addAll(super.getSystemsOfClass(systemClass, includeDestroyed));
        for (Iterator<Section> iterator = getSubSections().iterator(); iterator.hasNext();) {
        	result.addAll(iterator.next().getSystemsOfClass(systemClass, includeDestroyed));
    	} 
		
		return result;
	}
	
	@Override
	public List<System> getSystemsOfType(final String systemType, final boolean includeDestroyed) {
		List<System> result = new ArrayList<System>(0);
		
        if ((systemType == null) || !(systemType.length() > 0)) {
            throw new IllegalArgumentException("systemType cannot be null or blank");
        } 

        result.addAll(super.getSystemsOfType(systemType, includeDestroyed));
        for (Iterator<Section> iterator = getSubSections().iterator(); iterator.hasNext();) {
        	result.addAll(iterator.next().getSystemsOfType(systemType, includeDestroyed));
    	} 
		
		return result;
	}

	@Override
	public List<Weapon> getWeaponsOfClass(final Class<?> weaponClass, final boolean includeDestroyed) {
		List<Weapon> result = new ArrayList<Weapon>(0);
		
        if (weaponClass == null) {
            throw new IllegalArgumentException("weaponClass cannot be null");
        } 

        result.addAll(super.getWeaponsOfClass(weaponClass, includeDestroyed));
        for (Iterator<Section> iterator = getSubSections().iterator(); iterator.hasNext();) {
        	result.addAll(iterator.next().getWeaponsOfClass(weaponClass, includeDestroyed));
    	} 
		
		return result;
	}
		
	@Override
	public List<Weapon> getWeaponsOfType(final String weaponType, final boolean includeDestroyed) {
		List<Weapon> result = new ArrayList<Weapon>(0);
		
        if ((weaponType == null) || !(weaponType.length() > 0)) {
            throw new IllegalArgumentException("weaponType cannot be null or blank");
        } 

        result.addAll(super.getWeaponsOfType(weaponType, includeDestroyed));
        for (Iterator<Section> iterator = getSubSections().iterator(); iterator.hasNext();) {
        	result.addAll(iterator.next().getWeaponsOfType(weaponType, includeDestroyed));
    	} 
		
		return result;
	}
	
	/**
	 * a list of all sub-sections
	 * 
	 * @return a list of all sub-sections
	 */
	protected List<Section> getSubSections() {
		return subSections;
	}
	
	/**
	 * a list of all sections including this section and all sub-sections
	 * 
	 * @return a list of all sections including this section and all sub-sections
	 */	
	public List<Section> getAllSections() {
		List<Section> result = new ArrayList<Section>(0);

		result.add(this);

		for (Iterator<Section> iterator = getSubSections().iterator(); iterator.hasNext();) {
			Section section = iterator.next();

			if (section instanceof MultiSection) {
	            for (Iterator<Section> iter = ((MultiSection) section).getAllSections().iterator(); iter.hasNext();) {
	            	Section subSection = iter.next();
	    			
	            	if (!result.contains(subSection)) {
	            		result.add(subSection);
	            	}
	    		}
			} else {
				result.add(section);
			}		
		} 
		
		return result;
	}
	
	public int getSectionCount() {
		int result = 0;
		
        for (Iterator<Section> iterator = getSubSections().iterator(); iterator.hasNext();) {
        	result += iterator.next().getSectionCount();
    	} 
		
		return result;
	}
	
	public Iterator<Section> getSectionIterator() {
		return getSubSections().iterator();
	}
	
	/**
	 * add a section
	 * 
	 * @param section the section to add
	 */
	public void addSection(final Section section) {
        if (section == null) {
            throw new IllegalArgumentException("section cannot be null");
        } 
	        
        getSubSections().add(section);
	}	
	
	public Section getSection(final String name) {
		Section result = null;
	
        if ((name == null) || !(name.length() > 0)) {
            throw new IllegalArgumentException("name cannot be null or blank");
        } 
		
		for (Iterator<Section> iterator = getAllSections().iterator(); iterator.hasNext();) {
			Section section = iterator.next();
			
			if (section.getName().equalsIgnoreCase(name)) {
				result = section;
				break;
			}
    	} 
		
		return result;
	}
	
	public List<Section> getSectionsOfClass(final Class<?> sectionClass) {
		List<Section> result = new ArrayList<Section>(0);
		
        if (sectionClass == null) {
            throw new IllegalArgumentException("sectionClass cannot be null");
        } 
		
		for (Iterator<Section> iterator = getAllSections().iterator(); iterator.hasNext();) {
			Section section = iterator.next();

			if (sectionClass.isInstance(section)) {
				result.add(section);
			}
		} 
		
		return result;
	}
	
	public List<Section> getSectionsWithinArc(final int angle) {
		List<Section> result = new ArrayList<Section>(0);
		
		for (Iterator<Section> iterator = getAllSections().iterator(); iterator.hasNext();) {
			Section section = iterator.next();

			if (section.isWithinArc(angle)) {
				result.add(section);
			}
		} 
		
		return result;
	}	
	
	public Section getPrimarySection() {
		return getSection(Section.PRIMARY);
	}

	/**
	 * whether the section is structurally intact (true) or is going to fall off, which 
	 * is only true if all sections contained within this section are structurally unsound
	 * 
	 * @return whether the sections structure is destroyed meaning the section will fall off
	 */
	@Override
	public boolean isStructurallySound() {
		boolean result = false;
		
		for (Iterator<Section> iterator = getSubSections().iterator(); iterator.hasNext();) {
            if (iterator.next().isStructurallySound()) {
            	result = true;
            	break;
            }
    	} 		
		
		return result;
	}
	
	/**
	 *  destroy the section, all systems within it, and all sub-sections 
	 */	
	@Override
	public void destroy() {
		for (Iterator<Section> iterator = getSubSections().iterator(); iterator.hasNext();) {
            iterator.next().destroy();
    	} 
		
		super.destroy();			
	}
	
	@Override
	public void handleEndOfTurnActions() {
		for (Iterator<Section> iterator = getSubSections().iterator(); iterator.hasNext();) {
            iterator.next().handleEndOfTurnActions();
    	} 
		
		super.handleEndOfTurnActions();		
	}

}
