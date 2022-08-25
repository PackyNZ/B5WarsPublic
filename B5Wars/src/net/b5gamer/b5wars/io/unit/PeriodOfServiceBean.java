package net.b5gamer.b5wars.io.unit;

/**
 * Bean used for reading periods of service from xml
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class PeriodOfServiceBean {

	private String faction                = null;      
	private int    inService              = 0;         
	private int    endedService           = 0;      
	private String datePrefix             = null; 
	private String dateSuffix             = null; 
	private String availability           = null;
	private int    maxAvailable           = 0;      
	private String deployment             = null;  
	private double deploymentPercentage   = 0.0;      
	private int    onlyOnePer             = 0;      
	private int    economicCostPoints     = 0;      
	private double economicCostPercentage = 0.0;      
	private String notes                  = null;
	
	public String getFaction() {
		return faction;
	}
	
	public void setFaction(String faction) {
		this.faction = faction;
	}
	
	public int getInService() {
		return inService;
	}
	
	public void setInService(int inService) {
		this.inService = inService;
	}
	
	public int getEndedService() {
		return endedService;
	}
	
	public void setEndedService(int endedService) {
		this.endedService = endedService;
	}
	
	public String getDatePrefix() {
		return datePrefix;
	}

	public void setDatePrefix(String datePrefix) {
		this.datePrefix = datePrefix;
	}

	public String getDateSuffix() {
		return dateSuffix;
	}

	public void setDateSuffix(String dateSuffix) {
		this.dateSuffix = dateSuffix;
	}

	public String getAvailability() {
		return availability;
	}
	
	public void setAvailability(String availability) {
		this.availability = availability;
	}
	
	public int getMaxAvailable() {
		return maxAvailable;
	}
	
	public void setMaxAvailable(int maxAvailable) {
		this.maxAvailable = maxAvailable;
	}
	
	public String getDeployment() {
		return deployment;
	}
	
	public void setDeployment(String deployment) {
		this.deployment = deployment;
	}
	
	public double getDeploymentPercentage() {
		return deploymentPercentage;
	}

	public void setDeploymentPercentage(double deploymentPercentage) {
		this.deploymentPercentage = deploymentPercentage;
	}
	
	/**
	 * parse a deployment percentage in the format of "33.3%" or "33.3" into a double
	 * 
	 * @param deploymentPercentage value to be parsed
	 */
	public void setDeploymentPercentage(String deploymentPercentage) {
		if ((deploymentPercentage != null) && (deploymentPercentage.length() > 0)) {
			deploymentPercentage = deploymentPercentage.trim();
			
			if (deploymentPercentage.endsWith("%")) {
		        try {
		        	this.deploymentPercentage = Double.valueOf(deploymentPercentage.substring(0, deploymentPercentage.indexOf("%")));
		        } catch (NumberFormatException e) {
		            throw new IllegalArgumentException("deploymentPercentage must be a number optionally suffixed with a %");
		        } 
			} else {			
		        try {
		        	this.deploymentPercentage = Double.valueOf(deploymentPercentage);
		        } catch (NumberFormatException e) {
		            throw new IllegalArgumentException("deploymentPercentage must be a number optionally suffixed with a %");
		        } 
			}
		}
	}

	public int getOnlyOnePer() {
		return onlyOnePer;
	}

	public void setOnlyOnePer(int onlyOnePer) {
		this.onlyOnePer = onlyOnePer;
	}
	
	public int getEconomicCostPoints() {
		return economicCostPoints;
	}

	public void setEconomicCostPoints(int economicCostPoints) {
		this.economicCostPoints = economicCostPoints;
	}

	public double getEconomicCostPercentage() {
		return economicCostPercentage;
	}

	public void setEconomicCostPercentage(double economicCostPercentage) {
		this.economicCostPercentage = economicCostPercentage;
	}
	
	/**
	 * parse an economic cost in the format of "4" or "25%" into an integer or double respectively
	 * 
	 * @param economicCost value to be parsed
	 */	
	public void setEconomicCost(String economicCost) {
		if ((economicCost != null) && (economicCost.length() > 0)) {
			economicCost = economicCost.trim();

			if (economicCost.endsWith("%")) {
		        try {
		        	this.economicCostPercentage = Double.valueOf(economicCost.substring(0, economicCost.indexOf("%")));
		        } catch (NumberFormatException e) {
		            throw new IllegalArgumentException("economicCost must either be an integer, or a number suffixed with a %");
		        } 
			} else {			
		        try {
		        	this.economicCostPoints = Integer.valueOf(economicCost);
		        } catch (NumberFormatException e) {
		            throw new IllegalArgumentException("economicCost must either be an integer, or a number suffixed with a %");
		        } 
			}
		}		
	}
	
	public String getNotes() {
		return notes;
	}
	
	public void setNotes(String notes) {
		this.notes = notes;
	}       
	
}
