package net.b5gamer.b5wars.game;

import java.util.ArrayList;
import java.util.Iterator;

import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.section.StructuralSection;
import net.b5gamer.b5wars.unit.structural.system.Reactor;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.dice.Dice;

/**
 * !!--- work in progress ---!!
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public final class CombatSequence {

//	private static final Game game = new Game(false, 0);
	private static final Game game = new Game("TODO");

	/**
	 * INITIAL ACTIONS STEP
	 */
	public static void initialActionsStep() {
		// TODO Ship Power Segment
		// TODO     Resolve power deficiencies from shortages, EM weapon effects, and reactor criticals
		// TODO     Deactivate systems for additional power, if desired
		// TODO     Capacitors recharge at the appropriate rates
		// TODO     Transfer missiles to/from reload racks
		// TODO     Announce deactivated systems/shields as needed
		
		
		
		//     Roll for critical reactor detonation
		ArrayList<Unit> destroyedUnits = new ArrayList<Unit>(0);
		for (Iterator<Unit> unitIterator = game.getAllUnitsInPlay().iterator(); unitIterator.hasNext();) {
			Unit unit = unitIterator.next();

			if (unit instanceof StructuralUnit) {
				StructuralUnit structuralUnit = (StructuralUnit) unit;
				for (Iterator<System> systemIterator = structuralUnit.getSystemsOfClass(Reactor.class).iterator(); systemIterator.hasNext();) {
					Reactor reactor = (Reactor) systemIterator.next();
	
					if ((!reactor.isDestroyed()) && (reactor.isActivated()) && (reactor.isBreached())) {
						if (Dice.d100.roll() <= reactor.getDamageSuffered()) {
							((StructuralSection) structuralUnit.getSection(Section.PRIMARY)).getStructure().setDestroyed(true);
							destroyedUnits.add(unit);
						}
					}
				}
			}
		}
		for (Iterator<Unit> unitIterator = destroyedUnits.iterator(); unitIterator.hasNext();) {
			game.getAllUnitsInPlay().remove(unitIterator.next());
		}
		
		
		
//		// TODO Initiative Segment
//		// TODO     Hangar operations (reloading missiles, etc.) begin
//		
//		
//		
//		//     All units roll for initiative
//		for (Iterator<Unit> unitIterator = game.getAllUnitsInPlay().iterator(); unitIterator.hasNext();) {
//			Unit unit = unitIterator.next();
//			int initiative = Dice.D20.roll() + unit.getInitiativeModifier();
//
//			// TODO adjust for land/launch procedures and being launched
//			
//			if (unit instanceof MobileUnit) {
//				MobileUnit mobileUnit = (MobileUnit) unit;
//				int speed = mobileUnit.getSpeed() >= 0 ? mobileUnit.getSpeed() : mobileUnit.getSpeed() * -1;
//				if (speed < 5) { 
//					initiative -= 2 * (5 - speed);
//				}
//			}
//			
//			// TODO adjust for command and control
//			// TODO check active command and control
//			// TODO init penalty if all C&Cs destroyed?
//			CommandAndControl commandAndControl = ((StructuralUnit) unit).getSystemsOfClass(CommandAndControl.class);
//			if (commandAndControl != null) {
//				initiative -= commandAndControl.getInitiativePenalty();
//			}
//			
//			// TODO adjust for combat effects
//			// TODO adjust for voluntary penalty
//			
//			unit.setInitiative(initiative);
//		}
//		// order by init, lowest to highest, no ties highest modifier wins otherwise roll
		
		
		
		// TODO Electronic Warfare & Ballistic Launch Segment
		// TODO     All players secretly determine EW levels, adaptive armor allocation, ballistic weapon launch, and intention to reveal/hide concealed weapons
		// TODO     Tail gunners switch to/from navigator missions
		// TODO     Announce EW, adaptive armor and ballistic weapon launch (and target, if necessary)
		// TODO     Reveal or hide concealed weapons
		// TODO     ELINT ships announce which function(s) are in use and allocate which enemy OEW points to disrupt
		// TODO     Telepaths attack Shadow ships
		// TODO Jump Point Formation Segment
		// TODO     Announce/open jump points and activate phasing drives
	}

	/**
	 * MOVEMENT STEP
	 */
	public static void movementStep() {
		// TODO Pre-Movement Terrain Effects Segment
		// TODO     Determine hyperspace current changes
		// TODO     Perform other terrain-related movement as needed
		// TODO Movement Segment
		// TODO     Units which are rolling flip over
		// TODO     Ships which are pivoting change facing
		// TODO     Derelict units move
		// TODO     All other units move in initiative order
		// TODO         Shadow ships announce half-phasing
		// TODO         Resolve pulsar mine fire when fighters enter range
		// TODO         Resolve skin dancing attempts after movement
		// TODO         Previously attached breaching pods deposit Marines
		// TODO Post-Movement Terrain Effects Segment
		// TODO     Perform any remaining terrain-related movement
		// TODO Weapon-Based Movement Segment
		// TODO     Resolve fire and effects from weapons that move or turn a target (plasma net, gravitic shifter, etc.)
		// TODO Combat Pivot Segment
		// TODO     Fighters make combat pivots
		// TODO     Bases rotate
		// TODO Recovery Segment
		// TODO     Capture derelict fighters/shuttles
		// TODO     Recover escape pods
		// TODO     Breaching pods attempt to attach
		// TODO Close Combat EW Segment
		// TODO     All players secretly determine targets of close combat EW
		// TODO     Announce targets of CCEW
		// TODO Ramming Segment
		// TODO     Resolve all ramming attempts
	}

	/**
	 * COMBAT STEP
	 */
	public static void combatStep() {
		// TODO Fire Determination Segment
		// TODO     All players secretly determine all weapons fire, including weapons firing defensively
		// TODO     Declare all offensive fire, including called shots
		// TODO     Allocate defensive weapons against specific offensive shots
		// TODO     If using secret EW, announce all EW levels
		// TODO Fire Resolution Segment
		// TODO     Resolve ballistic weapon impact/explosions
		// TODO     Resolve all weapons fire from ships
		// TODO     Resolve all weapons fire from surviving fighters/shuttles at fighters/shuttles
		// TODO     Fighters damaged in the previous steps roll for drop-out
		// TODO     Resolve all other weapons fire, announcing special weapon modes as needed
		// TODO     Deploy fighters launched by fighter-bombs
	}

	/**
	 * END OF TURN ACTIONS STEP
	 */
	public static void endOfTurnActionsStep() {
		// TODO Marine Attack Segment
		// TODO     Determine and resolve all Marine attacks
		
		
		
		// Critical Hit Segment
		//     Determine and resolve all critical hits
		//     Reduce armor on systems damaged by armor-damaging weapons
		// TODO     missile rack criticals
		// Adjust Ship Systems Segment
		// TODO     Adaptive armor points are released due to damage received this turn <--OUT OF SEQUENCE-->
		// TODO     Adjust ship systems to account for damage - already done?? <--OUT OF SEQUENCE-->
		// Critical Hit Segment cont...
		//     Mark destroyed all systems attached to destroyed structure blocks <--OUT OF SEQUENCE-->
		for (Iterator<Unit> iterator = game.getAllUnitsInPlay().iterator(); iterator.hasNext();) {
			iterator.next().handleEndOfTurnActions();
		}
		
		
		
		// TODO Vortex Activation/Closure Segment
		// TODO     Jump points opened this turn become active
		// TODO     Ships which entered jump points on this turn are removed from play
		// TODO     Collapsing jump points close
		// TODO     Plasma web hexes created on the previous turn dissipate
		// TODO Hangar Operations Segment
		// TODO     Fighters/shuttles attempt to escape from destroyed ships
		// TODO     Launch/land fighters or shuttles
		// TODO     Hangar bay operations started earlier this turn are completed
		// TODO Link/Unlink Segment
		// TODO     Announce release of detachable cargo holds
		// TODO     Tractor beams attach/detach
		// TODO     Tugs attach/detach pods	
		// TODO Adjust Ship Systems Segment cont...
		// TODO     Self-repair systems perform repairs
		// TODO     Shadow ships complete phase-out/phase-in	
		
		
		
		game.setTurn(game.getTurn() + 1);
	}
	
}
