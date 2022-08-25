/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.section;

import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocationChart;
import net.b5gamer.b5wars.unit.structural.system.Structure;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * A StructureSection represents a structural section on a vessel that consists of a structure
 * block and can contain other systems. If the structure block is destroyed, the whole section 
 * is considered destroyed along with any systems within it
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class StructureSection extends Section implements StructuralSection {
	
	private static final long serialVersionUID = 1733318660079351358L;

	private Structure structure; // sections structure block

	/**
	 * @param name             name of the section
	 * @param arc              arc this section covers for incoming fire purposes
	 * @param hitLocationChart hit location chart for this section
	 * @param properties       additional properties 
	 */
	public StructureSection(final String name, final Arc arc, final HitLocationChart hitLocationChart,
			final Properties properties) {
		super(name, arc, hitLocationChart);
	}
	
	/**
	 * @param name             name of the section
	 * @param arc              arc this section covers for incoming fire purposes
	 * @param hitLocationChart hit location chart for this section
	 * @param structure        sections structure block
	 */
	public StructureSection(final String name, final Arc arc, final HitLocationChart hitLocationChart, 
			final Structure structure) {
		super(name, arc, hitLocationChart);

		if (structure == null) {
            throw new IllegalArgumentException("structure cannot be null");
        } 

		this.structure = structure;
		addSystem(structure);
	}
	
	public Structure getStructure() {
		return structure;
	}
	
	@Override
	public void addSystem(final System system) {
		super.addSystem(system);
		
		if ((getStructure() == null) && (system instanceof Structure)) {
			this.structure = (Structure) system;
		}        
	}
	
	@Override
	public boolean isStructurallySound() {
		return getStructure().isValidTarget();
	}
	
}
