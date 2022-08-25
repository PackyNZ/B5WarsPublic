/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.section;

import java.util.Iterator;

import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocationChart;
import net.b5gamer.b5wars.unit.structural.system.Structure;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * A StructureMultiSection is a structural section on a vessel that can contain systems 
 * and also sections as sub-sections, which can be used for grouping various systems or
 * sharing a hit location chart. If the structure block is destroyed, the whole section
 * is considered destroyed along with any systems and sub-sections within it
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class StructureMultiSection extends MultiSection implements StructuralSection {
	
	private static final long serialVersionUID = 5384283118972094966L;

	private Structure structure; // sections structure block

	/**
	 * @param name             name of the section
	 * @param arc              arc this section covers for incoming fire purposes, may be null
	 * @param hitLocationChart hit location chart for this section, may be null
	 * @param properties       additional properties 
	 */
	public StructureMultiSection(final String name, final Arc arc, final HitLocationChart hitLocationChart,
			final Properties properties) {
		super(name, arc, hitLocationChart);
	}
	
	/**
	 * @param name             name of the section
	 * @param arc              arc this section covers for incoming fire purposes, may be null
	 * @param hitLocationChart hit location chart for this section, may be null
	 * @param structure        sections structure block
	 */
	public StructureMultiSection(final String name, final Arc arc, final HitLocationChart hitLocationChart, 
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
	public void addSection(final Section section) {
		super.addSection(section);
	}	
	
	@Override
	public boolean isStructurallySound() {
		return getStructure().isValidTarget();
	}
	
	@Override
	public void handleEndOfTurnActions() {
		super.handleEndOfTurnActions();

		if (!isDestroyed() && !isStructurallySound()) {
			for (Iterator<Section> iterator = getSubSections().iterator(); iterator.hasNext();) {
				iterator.next().destroy();
	    	} 
		}
	}

}
