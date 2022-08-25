/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural;

import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.unit.ServiceHistory;
import net.b5gamer.b5wars.unit.Trait;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocationChart;
import net.b5gamer.b5wars.unit.structural.section.MultiSection;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.section.SectionCollection;
import net.b5gamer.b5wars.unit.structural.section.StructuralSection;
import net.b5gamer.b5wars.unit.structural.section.ChildSection;
import net.b5gamer.b5wars.unit.structural.system.Structure;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.b5wars.unit.structural.system.Weapon;

/**
 * A StructuralUnit represents larger craft such as ships, bases, and orbital satellites
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public abstract class StructuralUnit extends Unit implements SectionCollection {
	
	private static final long serialVersionUID = -7720608033830243103L;

	private final MultiSection           sectionCollection;        // collection of all sections
	private       List<HitLocationChart> hitLocationCharts = null; // all hit location charts
	
	/**
	 * @param name               unit name
	 * @param model              unit model
	 * @param hull               base hull the unit is built on
	 * @param version            version number
	 * @param author             author of the unit
	 * @param source             source of the unit
	 * @param pointValue         combat point cost of the unit
	 * @param rammingFactor      factor for ramming other units
	 * @param fwdAftDefense      defense when attacked from fore or aft
	 * @param stbPortDefense     defense when attacked from starboard or port
	 * @param initiativeModifier modifier to initiative
	 * @param serviceHistory     the units service history
	 * @param traits             the units traits
	 */
	protected StructuralUnit(final String name, final String model, final String hull, final int version, 
			final String author, final String source, final int pointValue, final int rammingFactor, 
			final int fwdAftDefense, final int stbPortDefense, final int initiativeModifier, 
			final ServiceHistory serviceHistory, final Trait[] traits) {
		super(name, model, hull, version, author, source, pointValue, rammingFactor, fwdAftDefense, 
				stbPortDefense, initiativeModifier, serviceHistory, traits);
		
		this.sectionCollection = new MultiSection(getName(), null, null);
	}
	
	/**
	 * collection of all sections
	 * 
	 * @return collection of all sections
	 */
	protected MultiSection getSectionCollection() {
		return sectionCollection;
	}
	
	/**
	 * all hit location charts
	 * 
	 * @return all hit location charts
	 */		
	public List<HitLocationChart> getHitLocationCharts() {
		return hitLocationCharts;
	}

	/**
	 * all hit location charts
	 * 
	 * @param hitLocationCharts all hit location charts
	 */	
	public void setHitLocationCharts(List<HitLocationChart> hitLocationCharts) {
        this.hitLocationCharts = hitLocationCharts;
	}

	/**
	 * the general hit location chart for the unit
	 * 
	 * @return the general hit location chart for the unit
	 */	
	public HitLocationChart getGeneralHitLocationChart() {
		return getSectionCollection().getHitLocationChart();
	}

	/**
	 * the general hit location chart for the unit
	 * 
	 * @param hitLocationChart the general hit location chart for the unit
	 */	
	public void setGeneralHitLocationChart(HitLocationChart hitLocationChart) {
		getSectionCollection().setHitLocationChart(hitLocationChart);
	}
		
	/**
	 * the maximum number of allowed sections for the unit, including all sections contained within a 
	 * {@link net.b5gamer.b5wars.unit.structural.section.SectionCollection} (although not counting 
	 * the {@link net.b5gamer.b5wars.unit.structural.section.SectionCollection} itself)
	 * 
	 * @return the maximum number of allowed sections for the unit
	 */
	protected int getMaximumNumberOfSections() {
		return 10; // e.g. Vree Xonn Dreadnought
	}
	
	public int getSectionCount() {
		return getSectionCollection().getSectionCount();
	}
	
	public Iterator<Section> getSectionIterator() {
		return getSectionCollection().getSectionIterator();
	}		
		
	/**
	 * add a section to the unit, which must be a 
	 * {@link net.b5gamer.b5wars.unit.structural.section.StructuralSection} if it is the
	 * Primary section (denoted as such by being named PRIMARY)
	 * 
	 * @param section the section to add
	 */
	public void addSection(final Section section) {
        if (section == null) {
            throw new IllegalArgumentException("section cannot be null");
        } 
        if (getSectionCollection().getAllSections().contains(section)) {
            throw new IllegalArgumentException(section.getName() + " section already added!");
        }
        if (getSection(section.getName()) != null) {
            throw new IllegalArgumentException(section.getName() + " section already exists!");
        }
        if ((section instanceof StructuralSection) && (((StructuralSection) section).getStructure() == null)) {
            throw new IllegalArgumentException(Structure.class.getSimpleName() + " cannot be null for " + 
            		section.getName() + " " + Section.class.getSimpleName());
        } 
        if (getSectionCount() >= getMaximumNumberOfSections()) {
            throw new IllegalArgumentException("Maximum of " + getMaximumNumberOfSections() + " sections for " + 
            		getType() + " exceeded");
        } 
        if (Section.PRIMARY.equalsIgnoreCase(section.getName())) {
        	if (!(section instanceof StructuralSection)) {
                throw new IllegalArgumentException(Section.PRIMARY + " " + Section.class.getSimpleName() + 
                		" must be a " + StructuralSection.class.getSimpleName());
        	}
        	if (section.getHitLocationChart() == null) {
        		throw new IllegalArgumentException(section.getName() + " " + Section.class.getSimpleName() + 
        				" " + HitLocationChart.class.getSimpleName() + " cannot be null");
        	}
        } else {
//	        if (hasTrait(Trait.SAUCER)) {
	        	// TODO validate general hit location chart?
	        	// TODO validate arc of systems?
//	        } else {
	        	// TODO validate arc of SectionCollection?
	        	// TODO reinstate?
//	        	if ((!(section instanceof SectionCollection)) && (section.getArc() == null)) {
//	        		throw new IllegalArgumentException(section.getName() + " section Arc cannot be null");
//		        } 
	        	// TODO validate hit location chart of SectionCollection?
	        	// TODO reinstate?
//	        	if ((!(section instanceof SectionCollection)) && (section.getHitLocationChart() == null)) {
//	        		throw new IllegalArgumentException(section.getName() + " section " + HitLocationChart.class.getSimpleName() + " cannot be null");
//	        	}
//	        } 
        }
	        		
        getSectionCollection().addSection(section);
        section.setParentUnit(this);
	}
	
	public Section getSection(final String name) {
		return getSectionCollection().getSection(name);
	}
	
	public List<Section> getSectionsOfClass(final Class<?> sectionClass) {
		return getSectionCollection().getSectionsOfClass(sectionClass);
	}

	public List<Section> getSectionsWithinArc(final int angle) {
		return getSectionCollection().getSectionsWithinArc(angle);
	}
	
	public Section getPrimarySection() {
		return getSection(Section.PRIMARY);
	}

	/**
	 * all systems in the unit of a given class that are not destroyed
	 * 
	 * @param  systemClass the class of systems to return
	 * @return             list of all systems in the unit of the given class that are not destroyed
	 */
	public List<System> getSystemsOfClass(final Class<?> systemClass) {
		return getSectionCollection().getSystemsOfClass(systemClass);
	}
	
	/**
	 * all systems in the unit of a given class
	 * 
	 * @param  systemClass      the class of systems to return
	 * @param  includeDestroyed whether to include destroyed systems
	 * @return                  list of all systems in the unit of the given class
	 */
	public List<System> getSystemsOfClass(final Class<?> systemClass, final boolean includeDestroyed) {
		return getSectionCollection().getSystemsOfClass(systemClass, includeDestroyed);
	}
	
	/**
	 * all systems in the unit of a given type that are not destroyed
	 * 
	 * @param  systemType the type of systems to return
	 * @return            list of all systems in the unit of the given type that are not destroyed
	 */
	public List<System> getSystemsOfType(final String systemType) {
		return getSectionCollection().getSystemsOfType(systemType);
	}
	
	/**
	 * all systems in the unit of a given type
	 * 
	 * @param  systemType       the type of systems to return
	 * @param  includeDestroyed whether to include destroyed systems
	 * @return                  list of all systems in the unit of the given type
	 */
	public List<System> getSystemsOfType(final String systemType, final boolean includeDestroyed) {
		return getSectionCollection().getSystemsOfType(systemType, includeDestroyed);
	}

	/**
	 * all weapons in the unit of a given class
	 * 
	 * @param  weaponClass      the class of weapons to return
	 * @param  includeDestroyed whether to include destroyed weapons
	 * @return                  list of all weapons in the unit of the given class
	 */
	public List<Weapon> getWeaponsOfClass(final Class<?> weaponClass, final boolean includeDestroyed) {
		return getSectionCollection().getWeaponsOfClass(weaponClass, includeDestroyed);
	}
	
	/**
	 * all weapons in the unit of a given type
	 * 
	 * @param  weaponType       the type of weapons to return
	 * @param  includeDestroyed whether to include destroyed weapons
	 * @return                  list of all weapons in the unit of the given type
	 */
	public List<Weapon> getWeaponsOfType(final String weaponType, final boolean includeDestroyed) {
		return getSectionCollection().getWeaponsOfType(weaponType, includeDestroyed);
	}

	public boolean isDestroyed() {
		return (getSectionCollection().getSection(Section.PRIMARY).isDestroyed());
	}

	public void handleEndOfTurnActions() {
		if (!isDestroyed()) {
			getSectionCollection().handleEndOfTurnActions();

			// check whether child sections fall off
			for (Iterator<Section> iterator = getSectionsOfClass(ChildSection.class).iterator(); iterator.hasNext();) {
				ChildSection section = (ChildSection) iterator.next();
				
				if (!section.isStructurallySound()) {
					section.destroy();
				}
	    	} 
			
			// destroy unit if the PRIMARY section was destroyed
			if (isDestroyed()) {
				for (Iterator<Section> iterator = getSectionIterator(); iterator.hasNext();) {
		            iterator.next().destroy();
		    	} 
			}			
		}
	}	
	
}
