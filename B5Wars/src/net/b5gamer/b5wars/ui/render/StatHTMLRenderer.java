/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & © Warner Bros.
 * Java design and implementation © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.render;

import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.unit.Availability;
import net.b5gamer.b5wars.unit.MobileUnit;
import net.b5gamer.b5wars.unit.PeriodOfService;
import net.b5gamer.b5wars.unit.Trait;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.small.FighterFlight;
import net.b5gamer.b5wars.unit.small.SmallUnit;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.system.DefensiveSystem;
import net.b5gamer.b5wars.unit.structural.system.Engine;
import net.b5gamer.b5wars.unit.structural.system.JumpEngine;
import net.b5gamer.b5wars.unit.structural.system.PowerProvidingSystem;
import net.b5gamer.b5wars.unit.structural.system.PowerRequiringSystem;
import net.b5gamer.b5wars.unit.structural.system.System;

public class StatHTMLRenderer {

	private StringBuilder html;
	
	public StatHTMLRenderer() {
	}
	
	public String renderAsHtml(final Unit unit) {		
		if (unit == null) {
			throw new IllegalArgumentException("unit cannot be null");
		}
		
		html = new StringBuilder();
		html.append("<html><body>");
		html.append("<table class=\"stats\" cellpadding=\"0\" cellspacing=\"0\">");
		html.append("<tr class=\"version\"><td class=\"version\">");
		html.append("<font class=\"version\">").append(unit.getVersionInfo()).append("</font>");				
		html.append("</td></tr>");
		html.append("<tr class=\"name\"><td class=\"name\">");
		html.append("<font class=\"name\">").append(unit.getFullName()).append("</font>");				
		html.append("</td></tr>");

		if ((unit.getPeriodOfService() != null) && (unit.getPeriodOfService().getAvailability() != Availability.BASE)) {
			html.append("<tr class=\"availability\"><td class=\"availability\">");
			html.append("<font class=\"availability\">").append(unit.getAvailabilityInfo()).append("</font>");				
			html.append("</td></tr>");
		}

		
		// SERVICE HISTORY
		if ((unit.getServiceHistory() != null) && (unit.getServiceHistory().getPeriodOfServiceCount() > 1)) {
			html.append("<tr class=\"stat-heading\"><td class=\"stat-heading\">");
			html.append("<font class=\"stat-heading\">SERVICE HISTORY</font>");				
			html.append("</td></tr>");

			for (Iterator<PeriodOfService> iterator = unit.getServiceHistory().getPeriodOfServiceIterator(); iterator.hasNext();) {
				html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
				html.append("<font class=\"stat-value-value\">").append(iterator.next().getDescription()).append("</font>");
				html.append("</td></tr>");
			}
		}
		
				
		// SPECS
		html.append("<tr class=\"stat-heading\"><td class=\"stat-heading\">");
		html.append("<font class=\"stat-heading\">SPECS</font>");				
		html.append("</td></tr>");
		html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
		html.append("<font class=\"stat-value-name\">Class: </font>");				
		html.append("<font class=\"stat-value-value\">").append(unit.getType()).append("</font>");				
		html.append("</td></tr>");
		
		if (unit.getPeriodOfService() != null) {
			html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
			html.append("<font class=\"stat-value-name\">In Service: </font>");				
			html.append("<font class=\"stat-value-value\">");
			
			if ((unit.getPeriodOfService().getInService() == 0) && (unit.getPeriodOfService().getEndedService() == 0)) {
				html.append("Any");
			} else {
				html.append(unit.getPeriodOfService().getRange());
			}

			html.append("</font></td></tr>");
		}
		
		html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
		html.append("<font class=\"stat-value-name\">Point Value: </font>");				
		html.append("<font class=\"stat-value-value\">").append(unit.getPointValue());				

		if (unit instanceof SmallUnit) {
			html.append(" each");
		}

		html.append("</font>");
		html.append("</td></tr>");
		html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
		html.append("<font class=\"stat-value-name\">Ramming Factor: </font>");				
		html.append("<font class=\"stat-value-value\">").append(unit.getRammingFactor()).append("</font>");				
		html.append("</td></tr>");
		
		if (unit instanceof StructuralUnit) {
			// TODO change to use generic drive interface
			 List<System> jumpEngines = ((StructuralUnit) unit).getSystemsOfClass(JumpEngine.class, true);

			html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
			html.append("<font class=\"stat-value-name\">Jump Delay: </font>");				
			html.append("<font class=\"stat-value-value\">");
			if (jumpEngines.size() > 0) {				
				html.append(((JumpEngine) jumpEngines.get(0)).getJumpDelay()).append(" Turns");
			} else {
				html.append("N/A");				
			}
			html.append("</font>");				
			html.append("</td></tr>");
		}

		if (unit instanceof FighterFlight) {
			html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
			html.append("<font class=\"stat-value-name\">Jinking Limit: </font>");				
			html.append("<font class=\"stat-value-value\">").append(((FighterFlight) unit).getJinkingLimit()).append(" Levels</font>");				
			html.append("</td></tr>");
		}
		
		
		// MANEUVERING
		MobileUnit mobileUnit = null;
		if (unit instanceof MobileUnit) {
			mobileUnit = (MobileUnit) unit;
		}
		html.append("<tr class=\"stat-heading\"><td class=\"stat-heading\">");
		html.append("<font class=\"stat-heading\">MANEUVERING</font>");				
		html.append("</td></tr>");
		html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
		html.append("<font class=\"stat-value-name\">Turn Cost: </font>");				
		html.append("<font class=\"stat-value-value\">");
		if (mobileUnit != null) {				
			html.append(mobileUnit.getTurnCostFormatted());
			if (mobileUnit.getTurnCost() % 1 == 0) {
				html.append(" x");
			}
			html.append(" Speed");
		} else {
			html.append("N/A");				
		}
		html.append("</font>");				
		html.append("</td></tr>");
		html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
		html.append("<font class=\"stat-value-name\">Turn Delay: </font>");				
		html.append("<font class=\"stat-value-value\">");
		if (mobileUnit != null) {				
			html.append(mobileUnit.getTurnDelayFormatted());
			if (mobileUnit.getTurnDelay() % 1 == 0) {
				html.append(" x");
			}
			if (mobileUnit.getTurnDelay() != 0) {
				html.append(" Speed");
			}
		} else {
			html.append("N/A");				
		}
		html.append("</font>");				
		html.append("</td></tr>");
		html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
		html.append("<font class=\"stat-value-name\">Accel/Decel Cost: </font>");				
		html.append("<font class=\"stat-value-value\">");
		if (mobileUnit != null) {				
			html.append(mobileUnit.getAccelDecelCost()).append(" Thrust");
		} else {
			html.append("N/A");				
		}
		html.append("</font>");				
		html.append("</td></tr>");
		html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
		
		// TODO pivot cost for Shadow Regeneration Outpost
		
		html.append("<font class=\"stat-value-name\">Pivot Cost: </font>");				
		html.append("<font class=\"stat-value-value\">");
		if ((mobileUnit != null) && (mobileUnit.isPivotCapable())) {
			html.append(mobileUnit.getPivotStartCost());
			if (mobileUnit.getPivotStopCost() > 0) {
				html.append("+");
				html.append(mobileUnit.getPivotStopCost());
			}
			html.append(" Thrust");				
		} else {
			html.append("N/A");				
		}
		html.append("</font>");				
		html.append("</td></tr>");
		html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
		html.append("<font class=\"stat-value-name\">Roll Cost: </font>");				
		html.append("<font class=\"stat-value-value\">");
		if ((mobileUnit != null) && (mobileUnit.isRollCapable())) {
			html.append(mobileUnit.getRollStartCost());
			if (mobileUnit.getRollStopCost() > 0) {
				html.append("+");
				html.append(mobileUnit.getRollStopCost());
			}
			html.append(" Thrust");				
		} else {
			html.append("N/A");				
		}
		html.append("</font>");				
		html.append("</td></tr>");
				
		
		// COMBAT STATS
		html.append("<tr class=\"stat-heading\"><td class=\"stat-heading\">");
		html.append("<font class=\"stat-heading\">COMBAT STATS</font>");				
		html.append("</td></tr>");
		
		int defenseBonus = 0;
		if (unit instanceof StructuralUnit) {
			defenseBonus = getDefenseBonus((StructuralUnit) unit);
		}
		html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
		html.append("<font class=\"stat-value-name\">Fwd/Aft Defense: </font>");				
		html.append("<font class=\"stat-value-value\">").append(unit.getFwdAftDefense());		
		if (defenseBonus > 0) {
			html.append(" (").append(unit.getFwdAftDefense() - defenseBonus).append(")");	
		}
		html.append("</font>");				
		html.append("</td></tr>");
		
		html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
		html.append("<font class=\"stat-value-name\">Stb/Port Defense: </font>");				
		html.append("<font class=\"stat-value-value\">").append(unit.getStbPortDefense());				
		if (defenseBonus > 0) {
			html.append(" (").append(unit.getStbPortDefense() - defenseBonus).append(")");	
		}
		html.append("</font>");				
		html.append("</td></tr>");
		
		if (unit instanceof StructuralUnit) {
			// TODO change to use generic engine interface
			 List<System> engines = ((StructuralUnit) unit).getSystemsOfClass(Engine.class, true);

			html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
			html.append("<font class=\"stat-value-name\">Engine Efficiency: </font>");				
			html.append("<font class=\"stat-value-value\">");
			if (engines.size() > 0) {
				html.append(((Engine) engines.get(0)).getEfficiency());
			} else {
				html.append("N/A");
			}
			html.append("</font>");				
			html.append("</td></tr>");
		}
		
		if (unit instanceof StructuralUnit) {
			int extraPower = getExtraPower((StructuralUnit) unit);
			html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
			html.append("<font class=\"stat-value-name\">");
			html.append(extraPower < 0 ? "Power Shortage" : "Extra Power").append(": </font>");				
			html.append("<font class=\"stat-value-value\">");
			if (extraPower > 0) {
				html.append("+");
			}
			html.append(extraPower).append("</font>");				
			html.append("</td></tr>");
		}
		
		if (unit instanceof FighterFlight) {
			html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
			html.append("<font class=\"stat-value-name\">Free Thrust: </font>");				
			html.append("<font class=\"stat-value-value\">").append(((FighterFlight) unit).getFreeThrust()).append("</font>");				
			html.append("</td></tr>");
		}
		
		if (unit instanceof FighterFlight) {
			int offensiveModifier = ((FighterFlight) unit).getOffensiveModifier();
			html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
			html.append("<font class=\"stat-value-name\">Offensive ");
			html.append(offensiveModifier < 0 ? "Penalty" : "Bonus").append(": </font>");				
			html.append("<font class=\"stat-value-value\">");
			if (offensiveModifier > 0) {
				html.append("+");
			}
			html.append(offensiveModifier).append("</font>");				
			html.append("</td></tr>");
		}
		
		html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
		html.append("<font class=\"stat-value-name\">Initiative ");
		html.append(unit.getInitiativeModifier() < 0 ? "Penalty" : "Bonus").append(": </font>");				
		html.append("<font class=\"stat-value-value\">");
		if (unit.getInitiativeModifier() > 0) {
			html.append("+");
		}
		html.append(unit.getInitiativeModifier()).append("</font>");				
		html.append("</td></tr>");

		
		// SPECIAL NOTES
		if (((unit.getPeriodOfService() != null) && ((unit.getPeriodOfService().getMaximumAvailable() > 0) ||
				(unit.getPeriodOfService().isDeploymentLimited()) || (unit.getPeriodOfService().isEconomicCost()))) || 
				(unit.getTraitCount() > 0)) {
			html.append("<tr class=\"stat-heading\"><td class=\"stat-heading\">");
			html.append("<font class=\"stat-heading\">SPECIAL NOTES</font>");				
			html.append("</td></tr>");

			if (unit.getPeriodOfService() != null) {
				if (unit.getPeriodOfService().getMaximumAvailable() > 0) {
					html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
					html.append("<font class=\"stat-value-value\">Special Availability (");
					html.append("Only ").append(unit.getPeriodOfService().getMaximumAvailable()).append(" Exist"); 
					html.append(unit.getPeriodOfService().getMaximumAvailable() > 1 ? ")" : "s)").append("</font>");
					html.append("</td></tr>");
				}
				
				if (unit.getPeriodOfService().isDeploymentLimited()) {
					html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
					html.append("<font class=\"stat-value-value\">").append(unit.getPeriodOfService().getDeploymentLimitation()).append("</font>");
					html.append("</td></tr>");
				}
				
				if (unit.getPeriodOfService().isEconomicCost()) {
					html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
					html.append("<font class=\"stat-value-value\">").append(unit.getPeriodOfService().getEconomicCost()).append("</font>");
					html.append("</td></tr>");
				}
			}
			
			for (Iterator<Trait> iterator = unit.getTraitIterator(); iterator.hasNext();) {
				html.append("<tr class=\"stat-value\"><td class=\"stat-value\">");
				html.append("<font class=\"stat-value-value\">").append(iterator.next()).append("</font>");
				html.append("</td></tr>");
			}
		}
		
		
		// TURN CHART
		if (mobileUnit != null) {				
			html.append("<tr><td>");
			html.append("<table class=\"speed\" cellpadding=\"0\" cellspacing=\"0\">");
			html.append("<tr class=\"speed-heading\">");
			html.append("<td class=\"speed-heading-name\">");
			html.append("<font class=\"speed-heading-name\">Speed</font>");				
			html.append("</td>");
			for (int index = 1; index <= 12; index++) {
				html.append("<td class=\"speed-heading-value\">");
				html.append("<font class=\"speed-heading-value\">").append(index).append("</font>");				
				html.append("</td>");			
			}	
			html.append("</tr>");
			html.append("<tr class=\"speed-value\">");
			html.append("<td class=\"speed-value-name\">");
			html.append("<font class=\"speed-value-name\">Turn Cost</font>");				
			html.append("</td>");
			for (int index = 1; index <= 12; index++) {
				html.append("<td class=\"speed-value-value\">");
				html.append("<font class=\"speed-value-value\">").append(roundUp(index * mobileUnit.getTurnCost())).append("</font>");				
				html.append("</td>");			
			}	
			html.append("</tr>");
			html.append("<tr class=\"speed-value\">");
			html.append("<td class=\"speed-value-name\">");
			html.append("<font class=\"speed-value-name\">Turn Delay</font>");				
			html.append("</td>");
			for (int index = 1; index <= 12; index++) {
				html.append("<td class=\"speed-value-value\">");
				html.append("<font class=\"speed-value-value\">").append(roundUp(index * mobileUnit.getTurnDelay())).append("</font>");				
				html.append("</td>");			
			}	
			html.append("</tr>");
			html.append("</table>");
			html.append("</td></tr>");
		}
		
		
		html.append("</table>");
		html.append("</body></html>");
		
		return html.toString();
	}
	
	private int getDefenseBonus(final StructuralUnit unit) {
		int defenseBonus = 0;
		
		List<System> defensiveSystems = unit.getSystemsOfClass(DefensiveSystem.class, true);
		
		for (Iterator<System> iterator = defensiveSystems.iterator(); iterator.hasNext();) {
			DefensiveSystem defensiveSystem = (DefensiveSystem) iterator.next();
			if (defensiveSystem.getDefensiveBonus() > defenseBonus) {
				defenseBonus = defensiveSystem.getDefensiveBonus();
			}
		}
		
		return defenseBonus;
	}
	
	private int getExtraPower(final StructuralUnit unit) {
		int availablePower = 0;
		int powerRequirement = 0;
		
		List<System> poweringSystems = unit.getSystemsOfClass(PowerProvidingSystem.class, true);
		for (Iterator<System> iterator = poweringSystems.iterator(); iterator.hasNext();) {
			PowerProvidingSystem system = ((PowerProvidingSystem) iterator.next());
			availablePower += system.getAvailablePower();
		}		
		
		List<System> poweredSystems = unit.getSystemsOfClass(PowerRequiringSystem.class, true);
		for (Iterator<System> iterator = poweredSystems.iterator(); iterator.hasNext();) {
			PowerRequiringSystem system = ((PowerRequiringSystem) iterator.next());
			powerRequirement += system.getPowerRequirement();
		}		
		
		return availablePower - powerRequirement;
	}
	
	private int roundUp(final double value) {
		int result = (int) value;
		
		if (value % 1 > 0) {
			result++;
		}
		
		return result;
	}
	
}
