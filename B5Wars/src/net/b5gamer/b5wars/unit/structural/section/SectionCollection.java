/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.section;

import java.util.Iterator;
import java.util.List;

/**
 * A SectionCollection represents a collection of sections on a vessel
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public interface SectionCollection {

	/**
	 * the number of sections in the collection
	 * 
	 * @return the number of sections in the collection
	 */
	public int getSectionCount();
	
	/**
	 * an iterator for all sections in the collection
	 * 
	 * @return an iterator for all sections in the collection
	 */
	public Iterator<Section> getSectionIterator();
	
	/**
	 * find and return the section with a given name, or null if it doesn't exist
	 * 
	 * @param  name name of the section to return
	 * @return      the section with the given name, or null if it doesn't exist 
	 */
	public Section getSection(final String name);
	
	/**
	 * find and return all sections of a given class
	 * 
	 * @param  sectionClass the class of sections to return
	 * @return              list of all sections of a given class
	 */
	public List<Section> getSectionsOfClass(final Class<?> sectionClass);
	
	/**
	 * find and return the sections whose arc contains a given angle
	 * 
	 * @param  angle incoming angle to whether it's contained with a sections arc
	 * @return       the sections whose arc contains the given angle 
	 */
	public List<Section> getSectionsWithinArc(final int angle);
		
	/**
	 * find and return the primary section, or null if it doesn't exist
	 * 
	 * @return the primary section, or null if it doesn't exist 
	 */
	public Section getPrimarySection();
	
}
