package net.b5gamer.b5wars.io.unit;

import java.util.ArrayList;
import java.util.List;

import net.b5gamer.util.Properties;

/**
 * Bean used for reading units from xml
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class UnitBean {

	// common fields
	private String name               = null;
	private String model              = null;
	private String hull               = null;
	private int    version            = 0;
	private String author             = null;
	private String source             = null;
	private String type               = null;
	private int    pointValue         = 0;
	private int    rammingFactor      = 0;
	private int    fwdAftDefense      = 0;
	private int    stbPortDefense     = 0;
	private int    initiativeModifier = 0;	
	private int    adaptiveArmor      = 0;
	private int    maxPerWeaponType   = 0;
	private int    pointsPreAssigned  = 0;	
	private List<PeriodOfServiceBean> serviceHistory = new ArrayList<PeriodOfServiceBean>(0);
	private List<String> traits = new ArrayList<String>(0);
	private Properties properties = new Properties(); 
	
	// structural unit specific fields
	private List<HitLocationChartBean> hitLocationCharts = new ArrayList<HitLocationChartBean>(0);
	private String generalHitLocationChart = null;
	private List<SectionBean> sections = new ArrayList<SectionBean>(0);
	
	// small unit specific fields

	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public String getHull() {
		return hull;
	}

	public void setHull(String hull) {
		this.hull = hull;
	}

	public int getVersion() {
		return version;
	}

	public void setVersion(int version) {
		this.version = version;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}
	
	public int getPointValue() {
		return pointValue;
	}

	public void setPointValue(int pointValue) {
		this.pointValue = pointValue;
	}

	public int getRammingFactor() {
		return rammingFactor;
	}

	public void setRammingFactor(int rammingFactor) {
		this.rammingFactor = rammingFactor;
	}

	public void setTurnCost(String turnCost) {
		getProperties().setProperty("turnCost", turnCost);
	}

	public void setTurnDelay(String turnDelay) {
		getProperties().setProperty("turnDelay", turnDelay);
	}

	public void setAccelDecelCost(String accelDecelCost) {
		getProperties().setProperty("accelDecelCost", accelDecelCost);
	}

	public void setPivotCost(String pivotCost) {
		getProperties().setProperty("pivotCost", pivotCost);
	}

	public void setRollCost(String rollCost) {
		getProperties().setProperty("rollCost", rollCost);
	}

	public int getFwdAftDefense() {
		return fwdAftDefense;
	}

	public void setFwdAftDefense(int fwdAftDefense) {
		this.fwdAftDefense = fwdAftDefense;
	}

	public int getStbPortDefense() {
		return stbPortDefense;
	}

	public void setStbPortDefense(int stbPortDefense) {
		this.stbPortDefense = stbPortDefense;
	}

	public int getInitiativeModifier() {
		return initiativeModifier;
	}

	public void setInitiativeModifier(int initiativeModifier) {
		this.initiativeModifier = initiativeModifier;
	}

	public int getAdaptiveArmor() {
		return adaptiveArmor;
	}

	public void setAdaptiveArmor(int adaptiveArmor) {
		this.adaptiveArmor = adaptiveArmor;
	}

	public int getMaxPerWeaponType() {
		return maxPerWeaponType;
	}

	public void setMaxPerWeaponType(int maxPerWeaponType) {
		this.maxPerWeaponType = maxPerWeaponType;
	}

	public int getPointsPreAssigned() {
		return pointsPreAssigned;
	}

	public void setPointsPreAssigned(int pointsPreAssigned) {
		this.pointsPreAssigned = pointsPreAssigned;
	}

	public List<PeriodOfServiceBean> getServiceHistory() {
		return serviceHistory;
	}
	
	public void addPeriodOfService(PeriodOfServiceBean periodOfService) {
		serviceHistory.add(periodOfService);
	}
	
	public List<String> getTraits() {
		return traits;
	}
	
	public void addTrait(String trait) {
		traits.add(trait);
	}	
		
	public Properties getProperties() {
		return properties;
	}
	
	public void addProperty(String name, String value) {
		properties.setProperty(name, value);
	}
	
	public List<HitLocationChartBean> getHitLocationCharts() {
		return hitLocationCharts;
	}
	
	public void addHitLocationChart(HitLocationChartBean hitLocationChart) {
		hitLocationCharts.add(hitLocationChart);
	}

	public String getGeneralHitLocationChart() {
		return generalHitLocationChart;
	}

	public void setGeneralHitLocationChart(String hitLocationChart) {
		this.generalHitLocationChart = hitLocationChart;
	}
		
	public List<SectionBean> getSections() {
		return sections;
	}
	
	public void addSection(SectionBean section) {
		sections.add(section);
	}	
	
	public void setFreeThrust(String freeThrust) {
		getProperties().setProperty("freeThrust", freeThrust);
	}
	
	public void setOffensiveModifier(String offensiveModifier) {
		getProperties().setProperty("offensiveModifier", offensiveModifier);
	}
	
	public void setDropoutModifier(String dropoutModifier) {
		getProperties().setProperty("dropoutModifier", dropoutModifier);
	}
	
}
