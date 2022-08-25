/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.section;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocationChart;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * A ChildSection is a section on a vessel that is structurally supported by one of more other 
 * sections. Systems in this section are considered part of all parent sections, and will be
 * destroyed if all parent sections are destroyed
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class ChildSection extends Section {

	private static final long serialVersionUID = -3870681383618578830L;

	private final List<Section> parentSections = new ArrayList<Section>(0); // all parent sections

	/**
	 * @param name             name of the section
	 * @param arc              not used
	 * @param hitLocationChart not used
	 * @param properties       additional properties 
	 */
	public ChildSection(final String name, final Arc arc, final HitLocationChart hitLocationChart,
			final Properties properties) {
		this(name);
	}
	
	/**
	 * @param name name of the section
	 */
	public ChildSection(final String name) {
		super(name, null, null);
	}
	
	@Override
	public Properties getProperties() {
		StringBuilder parentSections = new StringBuilder();
		for (Iterator<Section> iterator = getParentSections().iterator(); iterator.hasNext();) {
			parentSections.append(iterator.next().getName());
			
			if (iterator.hasNext()) {
				parentSections.append(",");
			}
		}
		
		Properties properties = new Properties();
		properties.setProperty("parentSections", parentSections.toString());
		
		return properties;
	}
	
	@Override
	public int getSectionCount() {
		return 0;
	}
	
	/**
	 * list of all parent sections
	 * 
	 * @return list of all parent sections
	 */	
	protected List<Section> getParentSections() {
		return parentSections;
	}	
	
	/**
	 * add a parent section
	 * 
	 * @param section the parent section to add
	 */
	public void addParentSection(final Section section){
        if (section == null) {
            throw new IllegalArgumentException("section cannot be null");
        } 
	      
        if (!getParentSections().contains(section)) {
        	getParentSections().add(section);
        }
	}	
	
	/**
	 * whether the section is structurally intact (true) or is going to fall off, which 
	 * is only true if all parent sections are structurally unsound
	 * 
	 * @return whether the sections structure is destroyed meaning the section will fall off
	 */
	@Override
	public boolean isStructurallySound() {
		boolean result = false;
		
		for (Iterator<Section> iterator = getParentSections().iterator(); iterator.hasNext();) {
            if (iterator.next().isStructurallySound()) {
            	result = true;
            	break;
            }
    	} 		
		
		return result;
	}	
	
}
