/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.unit;

import java.io.Serializable;

/**
 * This class represents a period of service for a particular unit being employed by
 * a specific faction, and it's availability/deployment limitations during that time
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class PeriodOfService implements Serializable {

	private static final long serialVersionUID = -2975214549724931338L;

	private final String       faction;                // name of the faction employing the unit
	private final int          inService;              // start date for this period of service 
	private final int          endedService;           // end date for this period of service 	
	private final String       datePrefix;             // prefix for service dates
	private final String       dateSuffix;             // suffix for service dates
	private final Availability availability;           // availability of the base hull or variant during this period
	private final int          maximumAvailable;       // maximum number of this unit that were available during this period 	
	private final Deployment   deployment;             // deployment limitation during this period
	private final double       deploymentPercentage;   // maximum percentage of combat points that can be spent on this unit 
	private final int          onlyOnePer;             // limitation of only one unit per the specified amount of combat points available  
	private final int          economicCostPoints;     // point modifier to units economic cost during this period
	private final double       economicCostPercentage; // percentage modifier to units economic cost during this period
	private final String       notes;                  // special notes for this period of service
	
	/**
	 * @param faction                name of the faction employing the unit
	 * @param inService              start date for this period of service  
	 * @param endedService           end date for this period of service 	
	 * @param datePrefix             prefix for service dates	
	 * @param dateSuffix             suffix for service dates	
	 * @param availability           availability of the base hull or variant during this period
	 * @param maximumAvailable       maximum number of this unit that were available during this period
	 * @param deployment             deployment limitation during this period
	 * @param deploymentPercentage   maximum percentage of combat points that can be spent on this unit 
	 * @param onlyOnePer             limitation of only one unit per the specified amount of combat points available
	 * @param economicCostPoints     point modifier to units economic cost during this period
	 * @param economicCostPercentage percentage modifier to units economic cost during this period
	 * @param notes                  special notes for this period of service
	 */
	public PeriodOfService(final String faction, final int inService, final int endedService, 
			final String datePrefix, final String dateSuffix, final Availability availability, 
			final int maximumAvailable, final Deployment deployment, final double deploymentPercentage,
			final int onlyOnePer, final int economicCostPoints, final double economicCostPercentage,
			final String notes) {
        if ((faction == null) || !(faction.length() > 0)) {
            throw new IllegalArgumentException("faction cannot be null or blank");
        } 
        if ((inService != 0) && (endedService != 0) && (endedService < inService)) {
            throw new IllegalArgumentException("endedService must be equal to or greater than inService");
        } 
        if (availability == null) {
            throw new IllegalArgumentException("availability cannot be null");
        } 
        if ((deploymentPercentage != 0) && (!(deploymentPercentage > 0) || (deploymentPercentage > 100))) {
            throw new IllegalArgumentException("deploymentPercentage must be between 1 and 100 inclusive");
        } 
        if (onlyOnePer < 0) {
            throw new IllegalArgumentException("onlyOnePer cannot be a negative number");
        } 
        if ((deployment == null) && (deploymentPercentage == 0) && (onlyOnePer == 0)) {
            throw new IllegalArgumentException("Either deployment, deploymentPercentage, or onlyOnePer must be specified");
        }
        if ((deployment != null && deploymentPercentage != 0) || 
        		(deployment != null && onlyOnePer != 0) ||
        		(deploymentPercentage != 0 && onlyOnePer != 0)) {
            throw new IllegalArgumentException("Only one of deployment, deploymentPercentage, or onlyOnePer can be specified");
        }
        if (economicCostPoints != 0 && economicCostPercentage != 0) {
            throw new IllegalArgumentException("Only one of economicCostPoints or economicCostPercentage can be specified");
        }
        
		this.faction                = faction;
		this.inService              = inService;
		this.endedService           = (endedService == inService) ? 0 : endedService;
		this.datePrefix             = ((datePrefix == null) || !(datePrefix.trim().length() > 0)) ? null : datePrefix;
		this.dateSuffix             = ((dateSuffix == null) || !(dateSuffix.trim().length() > 0)) ? null : dateSuffix;
		this.availability           = availability;
		this.maximumAvailable       = maximumAvailable;
		this.deployment             = deployment;
		this.deploymentPercentage   = deploymentPercentage;
		this.onlyOnePer             = onlyOnePer;
		this.economicCostPoints     = economicCostPoints;
		this.economicCostPercentage = economicCostPercentage;
		this.notes                  = notes;
	}	
	
	/**
	 * the name of the faction employing the unit
	 * 
	 * @return the name of the faction employing the unit
	 */
	public String getFaction() {
		return faction;
	}

	/**
	 * the start date for this period of service  
	 * 
	 * @return the start date for this period of service  
	 */
	public int getInService() {
		return inService;
	}

	/**
	 * the end date for this period of service 	
	 * 
	 * @return the end date for this period of service 	
	 */
	public int getEndedService() {
		return endedService;
	}

	/**
	 * the prefix for service dates	
	 * 
	 * @return the prefix for service dates		 	
	 */	
	public String getDatePrefix() {
		return datePrefix;
	}

	/**
	 * the suffix for service dates	
	 * 
	 * @return the suffix for service dates		 	
	 */	
	public String getDateSuffix() {
		return dateSuffix;
	}

	/**
	 * the availability of the base hull or variant during this period
	 * 
	 * @return the availability of the base hull or variant during this period
	 */
	public Availability getAvailability() {
		return availability;
	}

	/**
	 * the maximum number of this unit that were available during this period
	 * 
	 * @return the maximum number of this unit that were available during this period
	 */
	public int getMaximumAvailable() {
		return maximumAvailable;
	}

	/**
	 * the deployment limitation during this period
	 * 
	 * @return the deployment limitation during this period
	 */
	public Deployment getDeployment() {
		return deployment;
	}
	
	/**
	 * the maximum percentage of combat points that can be spent on this unit 
	 * 
	 * @return the maximum percentage of combat points that can be spent on this unit 
	 */
	public double getDeploymentPercentage() {
		return deploymentPercentage;
	}
	
	/**
	 * the limitation of only one unit per the specified amount of combat points available
	 * 
	 * @return the limitation of only one unit per the specified amount of combat points available
	 */	
	public int getOnlyOnePer() {
		return onlyOnePer;
	}

	/**
	 * whether deployment of the unit is limited
	 * 
	 * @return whether deployment of the unit is limited
	 */
	public boolean isDeploymentLimited() {
		return ((getDeployment() != null && getDeployment().getPercentage() < 100) ||
				(getDeploymentPercentage() != 0 && getDeploymentPercentage() < 100) ||
				(getOnlyOnePer() != 0));
	}
	
	/**
	 * the point modifier to units economic cost during this period
	 * 
	 * @return the point modifier to units economic cost during this period
	 */		
	public int getEconomicCostPoints() {
		return economicCostPoints;
	}

	/**
	 * the percentage modifier to units economic cost during this period
	 * 
	 * @return the percentage modifier to units economic cost during this period
	 */		
	public double getEconomicCostPercentage() {
		return economicCostPercentage;
	}

	/**
	 * whether there is an economic cost
	 * 
	 * @return whether there is an economic cost
	 */
	public boolean isEconomicCost() {
		return ((getEconomicCostPoints() != 0) || (getEconomicCostPercentage() != 0));
	}
	
	/**
	 * special notes for this period of service
	 * 
	 * @return special notes for this period of service
	 */
	public String getNotes() {
		return notes;
	}

	/**
	 * return whether the range of the specified period of service overlaps with the 
	 * range of this period of service
	 * 
	 * @param  periodOfService period of service to check the range of
	 * @return                 whether the range of the specified period of service
	 *                         overlaps with the range of this period of service
	 */	
	public boolean overlaps(final PeriodOfService periodOfService) {
		if (periodOfService == null) {
            throw new IllegalArgumentException("periodOfService cannot be null");
        } 

        if ((getInService() == 0 && getEndedService() == 0) || (periodOfService.getInService() == 0 && periodOfService.getEndedService() == 0)) {
        	return false;
        } else {
        	if (getInService() == 0) {
        		return (periodOfService.getInService() == 0 || periodOfService.getInService() <= getEndedService());
        	} else if (getEndedService() == 0) {
        		return (periodOfService.getEndedService() == 0 || periodOfService.getEndedService() >= getInService());
        	} else {
	        	return ((periodOfService.getInService() >= getInService() && periodOfService.getInService() <= getEndedService()) ||
					(periodOfService.getEndedService() >= getInService() && periodOfService.getEndedService() <= getEndedService()) ||
					(periodOfService.getInService() < getInService() && periodOfService.getEndedService() > getEndedService()));
        	}
        }
	}	
	
	/**
	 * formatted range for the period of service
	 * 
	 * @return formatted range for the period of service
	 */	
	public String getRange() {
		StringBuilder range = new StringBuilder();

		if (getInService() != 0) {
			if (getDatePrefix() != null) {
				range.append(getDatePrefix()).append(" ");
			}

			range.append(getInService());

			if (getEndedService() != 0) {
				range.append("-").append(getEndedService());
			} else {
				range.append("+");				
			}

			if (getDateSuffix() != null) {
				range.append(" ").append(getDateSuffix());
			}
		} else if (getEndedService() != 0) {
			if (getDatePrefix() != null) {
				range.append(getDatePrefix()).append(" ");
			}

			range.append("Until ").append(getEndedService());

			if (getDateSuffix() != null) {
				range.append(" ").append(getDateSuffix());
			}
		}
				
		return range.toString();
	}	

	/**
	 * formatted deployment limitation for the period of service
	 * 
	 * @return formatted deployment limitation for the period of service
	 */	
	public String getDeploymentLimitation() {
		StringBuilder deploymentLimitation = new StringBuilder();
	
		if (getDeployment() != null) {
			deploymentLimitation.append(getDeployment());
		} else if (getDeploymentPercentage() != 0) {
			deploymentLimitation.append("Limited Deployment (").append(getDeploymentPercentage()).append("%)");
		} else if (getOnlyOnePer() != 0) {
			deploymentLimitation.append("Limited Deployment (1 per ").append(getOnlyOnePer()).append(" Points)");
		}
		
		return deploymentLimitation.toString();
	}
	
	/**
	 * formatted economic cost for the period of service
	 * 
	 * @return formatted economic cost for the period of service
	 */	
	public String getEconomicCost() {
		StringBuilder economicCost = new StringBuilder();
	
		if (getEconomicCostPoints() != 0) {
			economicCost.append("Economic Cost ").append(getEconomicCostPoints() > 0 ? "+" : "").append(getEconomicCostPoints());
		} else if (getEconomicCostPercentage() != 0) {
			economicCost.append("Economic Cost ").append(getEconomicCostPercentage() > 0 ? "+" : "").append(getEconomicCostPercentage()).append("%");
		}
		
		return economicCost.toString();
	}
	
	/**
	 * description of the period of service
	 * 
	 * @return description of the period of service
	 */
	public String getDescription() {
		StringBuilder description = new StringBuilder();
		description.append(toString());

		String availabilityInfo = getAvailability().toString();
		if ((availabilityInfo != null) && (availabilityInfo.length() > 0)) {
			description.append(", ").append(getAvailability());
		}
		
		if (getMaximumAvailable() > 0) {
			description.append(", Only ").append(getMaximumAvailable()).append(" Exist");
			description.append(getMaximumAvailable() > 1 ? "" : "s");
		}

		String deploymentLimitation = getDeploymentLimitation();
		if ((deploymentLimitation != null) && (deploymentLimitation.length() > 0)) {
			description.append(", ").append(deploymentLimitation);
		}
				
		String economicCost = getEconomicCost();
		if ((economicCost != null) && (economicCost.length() > 0)) {
			description.append(", ").append(economicCost);
		}
		
		return description.toString();
	}
	
	@Override
	public String toString() {
		StringBuilder description = new StringBuilder();

		description.append(getFaction());

		String range = getRange();
		if ((range != null) && (range.length() > 0)) {
			description.append(" ").append(range);
		}
		
		return description.toString();
	}
	
}
