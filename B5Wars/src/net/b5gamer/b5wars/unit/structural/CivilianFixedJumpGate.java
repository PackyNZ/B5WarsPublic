/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural;

import java.util.ArrayList;
import java.util.List;

import net.b5gamer.b5wars.unit.Size;
import net.b5gamer.b5wars.unit.Trait;
import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocationChart;
import net.b5gamer.b5wars.unit.structural.hitlocation.SectionHitLocation;
import net.b5gamer.b5wars.unit.structural.hitlocation.SystemHitLocation;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.section.StructureSection;
import net.b5gamer.b5wars.unit.structural.system.CommandAndControl;
import net.b5gamer.b5wars.unit.structural.system.Hangar;
import net.b5gamer.b5wars.unit.structural.system.JumpEngine;
import net.b5gamer.b5wars.unit.structural.system.Reactor;
import net.b5gamer.b5wars.unit.structural.system.Sensors;
import net.b5gamer.b5wars.unit.structural.system.Structure;
import net.b5gamer.map.Arc;

/**
 * Fixed jump gates (normally simply referred to as jump gates) are large structures with
 * a simple reactor capable of charging, opening, and holding a jump point on demand
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class CivilianFixedJumpGate extends StructuralUnit {

	private static final long serialVersionUID = 2145097880220149272L;

	public CivilianFixedJumpGate() {
		super("Civilian Fixed Jump Gate", "Generic", "Fixed Jump Gate", 2, "AOG", "SF", 1, 800, 20, 20, 0, null, null);
	
		// traits
		getTraits().add(new Trait("Does Not Block LOS"));
		
		// hit location charts
		List<HitLocationChart> hitLocationCharts = new ArrayList<HitLocationChart>(4);
		HitLocationChart forwardHits = new HitLocationChart("FORWARD HITS");
		forwardHits.addHitLocation(new SystemHitLocation(  1, 18, "Structure", null, null));
		forwardHits.addHitLocation(new SectionHitLocation(19, 20, "PRIMARY"));
		hitLocationCharts.add(forwardHits);
		
		HitLocationChart sideHits = new HitLocationChart("SIDE HITS");
		sideHits.addHitLocation(new SystemHitLocation(  1, 18, "Structure", null, null));
		sideHits.addHitLocation(new SectionHitLocation(19, 20, "PRIMARY"));
		hitLocationCharts.add(sideHits);

		HitLocationChart aftHits = new HitLocationChart("AFT HITS");
		aftHits.addHitLocation(new SystemHitLocation(  1, 18, "Structure", null, null));
		aftHits.addHitLocation(new SectionHitLocation(19, 20, "PRIMARY"));
		hitLocationCharts.add(aftHits);

		HitLocationChart primaryHits = new HitLocationChart("PRIMARY HITS");
		primaryHits.addHitLocation(new SystemHitLocation( 1, 10, "Structure",   null, null));
		primaryHits.addHitLocation(new SystemHitLocation(11, 13, "Sensors",     null, null));
		primaryHits.addHitLocation(new SystemHitLocation(14, 17, "Reactor",     null, null));
		primaryHits.addHitLocation(new SystemHitLocation(18, 18, "Hangar",      null, null));
		primaryHits.addHitLocation(new SystemHitLocation(19, 19, "Jump Engine", null, null));
		primaryHits.addHitLocation(new SystemHitLocation(20, 20, "C & C",       null, null));
		hitLocationCharts.add(primaryHits);
		setHitLocationCharts(hitLocationCharts);

		// sections
		StructureSection forwardSection = new StructureSection(Section.FORWARD, Arc.FWD_60, 
				forwardHits, new Structure(200, 2, null, null));
		addSection(forwardSection);
		
		StructureSection portSection = new StructureSection(Section.PORT, Arc.PORT_120, 
				sideHits, new Structure(240, 2, null, null));
		addSection(portSection);

		StructureSection primarySection = new StructureSection(Section.PRIMARY, null, 
				primaryHits, new Structure(160, 3, null, null));
		primarySection.addSystem(new CommandAndControl(8,  6, null, null));
		primarySection.addSystem(new Sensors(          6,  3, null, null, 0, 2));
		primarySection.addSystem(new JumpEngine(       10, 8, null, null, 0, 20));
		primarySection.addSystem(new Reactor(          50, 4, null, null, 0));
		primarySection.addSystem(new Hangar(           1,  3, null, null, 1));
		addSection(primarySection);
		
		StructureSection starboardSection = new StructureSection(Section.STARBOARD, Arc.STB_120, 
				sideHits, new Structure(240, 2, null, null));
		addSection(starboardSection);

		StructureSection aftSection = new StructureSection(Section.AFT, Arc.AFT_60, 
				aftHits, new Structure(200, 2, null, null));
		addSection(aftSection);		
	}

	public String getType() {
		return "Enormous Unit";
	}

	public Size getSize() {
		return Size.ENORMOUS;
	}	
	
	@Override
	public boolean getBlocksLineOfSight() {
		return false;
	}
	
}
