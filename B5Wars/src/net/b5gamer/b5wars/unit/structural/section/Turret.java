/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit.structural.section;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocationChart;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.b5wars.unit.structural.system.Weapon;
import net.b5gamer.dice.Dice;
import net.b5gamer.map.Arc;
import net.b5gamer.util.Properties;

/**
 * A Turret is a special section that represents a turret on a vessel, which confers a 360
 * degree arc to all systems contained within it and suffers it's own critical hit
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class Turret extends Section {
	
	private static final long serialVersionUID = -1211282947115418367L;
		
	/**
	 * @param name             name of the section
	 * @param arc              not used
	 * @param hitLocationChart hit location chart for this section
	 * @param properties       additional properties 
	 */
	public Turret(final String name, final Arc arc, final HitLocationChart hitLocationChart,
			final Properties properties) {
		this(name, arc, hitLocationChart);
	}
	
	/**
	 * @param name             name of the section
	 * @param arc              arc this section covers for incoming fire purposes
	 * @param hitLocationChart hit location chart for this section
	 */
	public Turret(final String name, final Arc arc, final HitLocationChart hitLocationChart) {
		super(name, arc, hitLocationChart);

        if (arc == null) {
            throw new IllegalArgumentException("arc cannot be null");
        } 
	}
	
	@Override
	public void addSystem(final System system) {
        super.addSystem(system);

//        if (system instanceof DirectionalSystem) {
        if (system.getArc() != null) {
        	system.setArc(getArc());
            // TODO hangar launch direction 360 degrees
            // TODO weapons only fire within 60 degrees of each other
        } 
	}
		
	@Override
	public List<System> getTargetSystemsOfClass(final Class<?> systemClass) {
		List<System> result = new ArrayList<System>(0);
		
        if (systemClass == null) {
            throw new IllegalArgumentException("systemClass cannot be null");
        } 

        for (Iterator<System> iterator = getAllSystems().iterator(); iterator.hasNext();) {
			System system = iterator.next();
			
			if (system.isValidTarget() && ((systemClass.isInstance(system)) || (systemClass == Weapon.class))) {
				result.add(system);
			}
		}
		
		return result;
	}	
		
	@Override
	public List<System> getTargetSystemsOfType(final String systemType) {
		List<System> result = new ArrayList<System>(0);
		
        if ((systemType == null) || !(systemType.length() > 0)) {
            throw new IllegalArgumentException("systemType cannot be null or blank");
        } 

        for (Iterator<System> iterator = getAllSystems().iterator(); iterator.hasNext();) {
			System system = iterator.next();
			
			if (system.isValidTarget() && ((system.getType().equals(systemType)) || (Weapon.class.getSimpleName().equals(systemType)))) {
				result.add(system);
			}
		}
		
		return result;
	}	

	@Override
	public void handleEndOfTurnActions() {
		determineAndResolveCriticalHits();
		super.handleEndOfTurnActions();
	}
	
	/**
	 * determine and resolve any critical hits to the turret
	 */
	protected void determineAndResolveCriticalHits() {
		for (Iterator<System> iterator = getSystems().iterator(); iterator.hasNext();) {
			if (iterator.next().isDamagedThisTurn()) {
				resolveCriticalHit();
				break;
			}
		}
	}
	
	/**
	 * resolve a critical hit to the turret
	 */
	protected void resolveCriticalHit() {
		int roll = Dice.d20.roll();
		Logger.info(roll + " rolled for " + getName() + "...");

		if (roll <= 16) {
			// no critical hit
			Logger.info("    - no critical hit");
		} else {
			// arc restricted to 60 degrees forward
			Logger.info("    - arc restricted to 60 degrees forward");
			
			// TODO restrict to 60 degrees within current arc, restrict to last arc fired??
			setArc(Arc.FWD_60);
			for (Iterator<System> iterator = getSystems().iterator(); iterator.hasNext();) {
				System system = iterator.next();

//		        if (system instanceof DirectionalSystem) {
		        if (system.getArc() != null) {
		            system.setArc(getArc());
		            // TODO restrict hangar launch direction
		        } 
			}
		}
	}
	
}
