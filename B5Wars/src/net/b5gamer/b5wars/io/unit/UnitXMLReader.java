/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.io.unit;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.xml.XMLConstants;
import javax.xml.transform.stream.StreamSource;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.digester.Digester;

import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.b5wars.unit.Availability;
import net.b5gamer.b5wars.unit.Deployment;
import net.b5gamer.b5wars.unit.PeriodOfService;
import net.b5gamer.b5wars.unit.ServiceHistory;
import net.b5gamer.b5wars.unit.Trait;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.small.FighterFlight;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocation;
import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocationChart;
import net.b5gamer.b5wars.unit.structural.hitlocation.MultiHitLocation;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.section.ChildSection;
import net.b5gamer.b5wars.unit.structural.system.GenericSystem;
import net.b5gamer.b5wars.unit.structural.system.PoweredSystem;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.b5wars.unit.structural.system.Weapon;
import net.b5gamer.icon.IconPosition;
import net.b5gamer.io.FilenameExtensionFilter;
import net.b5gamer.map.Arc;
import net.b5gamer.util.ClassLocator;
import net.b5gamer.util.Properties;

/**
 * This class is used for reading and validating xml Unit definitions and instantiating the 
 * appropriate classes
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public final class UnitXMLReader {

	public static final String DEFAULT_DIRECTORY = "units"; // default directory for unit resources
	
	private static final FilenameExtensionFilter XML_FILE_FILTER = new FilenameExtensionFilter("xml"); 
	
	private UnitXMLReader() {
	}
	
	/**
	 * read all units in the default directory 
	 * 
	 * @return list of all units in the default directory 
	 * 
	 * @throws FileNotFoundException the default directory cannot be found
	 * @throws LoadUnitException 
	 */
	public static final List<Unit> read() throws FileNotFoundException, LoadUnitException {
		return read(DEFAULT_DIRECTORY);
	}
	
	/**
	 * read all units in the given file or directory, and if a directory is provided then recurse 
	 * through all sub-directories as well  
	 * 
	 * @param  filename name of the file or directory to read the units from
	 * @return          list of all units in the file or directory 
	 * 
	 * @throws FileNotFoundException the file or directory cannot be found
	 * @throws LoadUnitException 
	 */	
	public static final List<Unit> read(final String filename) throws FileNotFoundException, LoadUnitException {
		return read(new File(filename));
	}
	
	/**
	 * read all units in the given file or directory, and if a directory is provided then recurse 
	 * through all sub-directories as well  
	 * 
	 * @param  file file or directory to read the units from
	 * @return      list of all units in the file or directory 
	 * 
	 * @throws FileNotFoundException the file or directory cannot be found
	 * @throws LoadUnitException 
	 */	
	public static final List<Unit> read(final File file) throws FileNotFoundException, LoadUnitException {
		if (file.isFile()) {
			List<Unit> units = new ArrayList<Unit>(0);
			units.add(read(new BufferedInputStream(new FileInputStream(file))));
		    return units;
		} else if (file.isDirectory()) {
			List<Unit> units = new ArrayList<Unit>(0);

		    File[] files = file.listFiles();		    
		    for (int i = 0; i < files.length; i++) {
		    	File newFile = files[i];
		    	if (newFile.isDirectory() || (newFile.isFile() && XML_FILE_FILTER.accept(newFile.getParentFile(), newFile.getName()))) {
			    	units.addAll(read(files[i]));
		    	}
		    }
			
		    return units;
		} else {
			return null;
		}
	}

	/**
	 * read all units from the given InputStream 
	 * 
	 * @param  input InputStream to read the units from
	 * @return       list of all units from the InputStream 
	 * @throws LoadUnitException 
	 */	
	public static final Unit read(final InputStream input) throws LoadUnitException {
		Unit result = null;
		
		List<UnitBean> unitBeans = digest(input);
		
		if (unitBeans == null) {
			Logger.debug("No units found");
		} else if (unitBeans.size() == 1) {
			Unit unit = processUnit(unitBeans.get(0));
			
			if (unit != null) {
				result = unit;
			}
		} else {
			Logger.debug("Too many (" + unitBeans.size() + ") units found");
		}
		
		return result;
	}
	
	/**
	 * parse the given InputStream and return a UnitBean for each unit found
	 * 
	 * @param  input InputStream to read the units from
	 * @return       list consisting of a UnitBean for each unit found
	 * @throws LoadUnitException 
	 */
	private static final List<UnitBean> digest(final InputStream input) throws LoadUnitException {
		List<UnitBean> result = null;

		try {
			List<UnitBean> unitBeans = new ArrayList<UnitBean>(0);

			Digester digester = new Digester();
//			digester.setValidating(true);
			digester.setXMLSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new StreamSource(UnitXMLReader.class.getResourceAsStream("unit.xsd"))));
			digester.setNamespaceAware(true);
			digester.setErrorHandler(new UnitXMLErrorHandler());
	        digester.push(unitBeans);
	        
	        // add common unit rules
			digester.addObjectCreate("unit-definition/unit", UnitBean.class);
			digester.addSetProperties("unit-definition/unit");
			digester.addSetProperties("unit-definition/unit/specs");
			digester.addSetProperties("unit-definition/unit/maneuvering");
			digester.addSetProperties("unit-definition/unit/combat-stats");
			digester.addSetProperties("unit-definition/unit/adaptive-armor");
	        digester.addSetNext("unit-definition/unit", "add");

			digester.addObjectCreate("unit-definition/unit/service-history/period-of-service", PeriodOfServiceBean.class);
			digester.addSetProperties("unit-definition/unit/service-history/period-of-service");
			// workaround as deploymentPercentage and economicCost fields are not Strings
			digester.addCallMethod("unit-definition/unit/service-history/period-of-service", "setDeploymentPercentage", 1);        
			digester.addCallParam("unit-definition/unit/service-history/period-of-service", 0, "deploymentPercentage");      
			digester.addCallMethod("unit-definition/unit/service-history/period-of-service", "setEconomicCost", 1);        
			digester.addCallParam("unit-definition/unit/service-history/period-of-service", 0, "economicCost");     
			digester.addCallMethod("unit-definition/unit/service-history/period-of-service", "setNotes", 0);
	        digester.addSetNext("unit-definition/unit/service-history/period-of-service", "addPeriodOfService");
	        	        
			digester.addCallMethod("unit-definition/unit/traits/trait", "addTrait", 1);        
			digester.addCallParam("unit-definition/unit/traits/trait", 0, "name");        
			
			// add structural unit rules
			digester.addObjectCreate("unit-definition/unit/ship-definition/hit-location-charts/hit-location-chart", HitLocationChartBean.class);
			digester.addSetProperties("unit-definition/unit/ship-definition/hit-location-charts/hit-location-chart");
	        digester.addSetNext("unit-definition/unit/ship-definition/hit-location-charts/hit-location-chart", "addHitLocationChart");

	        StringBuilder pattern = new StringBuilder("unit-definition/unit/ship-definition/hit-location-charts/hit-location-chart");
	        int numberOfLevels = 2;
	        for (int index = 0; index < numberOfLevels; index++) {
	        	pattern.append("/hit-location");
	        	
				digester.addObjectCreate(pattern.toString(), HitLocationBean.class);
				digester.addSetProperties(pattern.toString());
				digester.addCallMethod(pattern.toString(), "setClassName", 1);        
				digester.addCallParam(pattern.toString(), 0, "class");      
				digester.addCallMethod(pattern.toString() + "/property", "addProperty", 2);        
				digester.addCallParam(pattern.toString() + "/property", 0, "name");        
				digester.addCallParam(pattern.toString() + "/property", 1, "value");
				digester.addSetNext(pattern.toString(), "addHitLocation");
	        }
	        
			digester.addCallMethod("unit-definition/unit/ship-definition/sections", "setGeneralHitLocationChart", 1);        
			digester.addCallParam("unit-definition/unit/ship-definition/sections", 0, "generalHitLocationChart");        
	        
	        pattern = new StringBuilder("unit-definition/unit/ship-definition/sections");
	        numberOfLevels = 6;
	        for (int index = 0; index < numberOfLevels; index++) {
	        	pattern.append("/section");
	        	
				digester.addObjectCreate(pattern.toString(), SectionBean.class);
				digester.addSetProperties(pattern.toString());
				digester.addCallMethod(pattern.toString(), "setClassName", 1);        
				digester.addCallParam(pattern.toString(), 0, "class");      
				// workaround as arc field is not a String
				digester.addCallMethod(pattern.toString(), "setArc", 1);        
				digester.addCallParam(pattern.toString(), 0, "arc");      
				digester.addCallMethod(pattern.toString() + "/property", "addProperty", 2);        
				digester.addCallParam(pattern.toString() + "/property", 0, "name");        
				digester.addCallParam(pattern.toString() + "/property", 1, "value");
				digester.addSetNext(pattern.toString(), "addSection");

				digester.addObjectCreate(pattern.toString() + "/systems/system", SystemBean.class);
				digester.addSetProperties(pattern.toString() + "/systems/system");
				digester.addCallMethod(pattern.toString() + "/systems/system", "setClassName", 1);        
				digester.addCallParam(pattern.toString() + "/systems/system", 0, "class");      
				// workaround as arc field is not a String
				digester.addCallMethod(pattern.toString() + "/systems/system", "setArc", 1);        
				digester.addCallParam(pattern.toString() + "/systems/system", 0, "arc");      
				digester.addCallMethod(pattern.toString() + "/systems/system/property", "addProperty", 2);        
				digester.addCallParam(pattern.toString() + "/systems/system/property", 0, "name");        
				digester.addCallParam(pattern.toString() + "/systems/system/property", 1, "value");

				digester.addObjectCreate(pattern.toString() + "/systems/system/system-icons/system-icon", IconBean.class);
				digester.addSetProperties(pattern.toString() + "/systems/system/system-icons/system-icon");
				digester.addSetNext(pattern.toString() + "/systems/system/system-icons/system-icon", "setSystemIcon");
				digester.addObjectCreate(pattern.toString() + "/systems/system/system-icons/structure-icon", IconBean.class);
				digester.addSetProperties(pattern.toString() + "/systems/system/system-icons/structure-icon");
				digester.addSetNext(pattern.toString() + "/systems/system/system-icons/structure-icon", "setSystemIcon");
				digester.addObjectCreate(pattern.toString() + "/systems/system/system-icons/armor-icon", IconBean.class);
				digester.addSetProperties(pattern.toString() + "/systems/system/system-icons/armor-icon");
				digester.addSetNext(pattern.toString() + "/systems/system/system-icons/armor-icon", "setArmorIcon");
				digester.addObjectCreate(pattern.toString() + "/systems/system/system-icons/power-icon", IconBean.class);
				digester.addSetProperties(pattern.toString() + "/systems/system/system-icons/power-icon");
				digester.addSetNext(pattern.toString() + "/systems/system/system-icons/power-icon", "setPowerIcon");
				digester.addObjectCreate(pattern.toString() + "/systems/system/system-icons/arc-icon", IconBean.class);
				digester.addSetProperties(pattern.toString() + "/systems/system/system-icons/arc-icon");
				digester.addSetNext(pattern.toString() + "/systems/system/system-icons/arc-icon", "setArcIcon");
								
		        digester.addSetNext(pattern.toString() + "/systems/system", "addSystem");

				digester.addObjectCreate(pattern.toString() + "/systems/weapon", SystemBean.class);
				digester.addCallMethod(pattern.toString() + "/systems/weapon", "setClassName", 1);        
				digester.addObjectParam(pattern.toString() + "/systems/weapon", 0, Weapon.class.getSimpleName());      
				digester.addCallMethod(pattern.toString() + "/systems/weapon", "setWeaponType", 1);        
				digester.addCallParam(pattern.toString() + "/systems/weapon", 0, "type");      
				digester.addCallMethod(pattern.toString() + "/systems/weapon", "setDamageBoxes", 1);        
				digester.addCallParam(pattern.toString() + "/systems/weapon", 0, "damageBoxes");      
				digester.addCallMethod(pattern.toString() + "/systems/weapon", "setArmor", 1);        
				digester.addCallParam(pattern.toString() + "/systems/weapon", 0, "armor");      
				digester.addCallMethod(pattern.toString() + "/systems/weapon", "setArc", 1);        
				digester.addCallParam(pattern.toString() + "/systems/weapon", 0, "arc");      
				digester.addCallMethod(pattern.toString() + "/systems/weapon", "setName", 1);        
				digester.addCallParam(pattern.toString() + "/systems/weapon", 0, "number");      
				digester.addCallMethod(pattern.toString() + "/systems/weapon/property", "addProperty", 2);        
				digester.addCallParam(pattern.toString() + "/systems/weapon/property", 0, "name");        
				digester.addCallParam(pattern.toString() + "/systems/weapon/property", 1, "value");
				
				digester.addObjectCreate(pattern.toString() + "/systems/weapon/weapon-icons/weapon-icon", IconBean.class);
				digester.addSetProperties(pattern.toString() + "/systems/weapon/weapon-icons/weapon-icon");
				digester.addSetNext(pattern.toString() + "/systems/weapon/weapon-icons/weapon-icon", "setSystemIcon");
				digester.addObjectCreate(pattern.toString() + "/systems/weapon/weapon-icons/armor-icon", IconBean.class);
				digester.addSetProperties(pattern.toString() + "/systems/weapon/weapon-icons/armor-icon");
				digester.addSetNext(pattern.toString() + "/systems/weapon/weapon-icons/armor-icon", "setArmorIcon");
				digester.addObjectCreate(pattern.toString() + "/systems/weapon/weapon-icons/power-icon", IconBean.class);
				digester.addSetProperties(pattern.toString() + "/systems/weapon/weapon-icons/power-icon");
				digester.addSetNext(pattern.toString() + "/systems/weapon/weapon-icons/power-icon", "setPowerIcon");
				digester.addObjectCreate(pattern.toString() + "/systems/weapon/weapon-icons/arc-icon", IconBean.class);
				digester.addSetProperties(pattern.toString() + "/systems/weapon/weapon-icons/arc-icon");
				digester.addSetNext(pattern.toString() + "/systems/weapon/weapon-icons/arc-icon", "setArcIcon");				
				digester.addObjectCreate(pattern.toString() + "/systems/weapon/weapon-icons/number-icon", IconBean.class);
				digester.addSetProperties(pattern.toString() + "/systems/weapon/weapon-icons/number-icon");
				digester.addSetNext(pattern.toString() + "/systems/weapon/weapon-icons/number-icon", "setNumberIcon");				
				
		        digester.addSetNext(pattern.toString() + "/systems/weapon", "addSystem");
	        }
						
			// add small unit rules
			digester.addSetProperties("unit-definition/unit/fighter-stats/flight-stats");
			// TODO	implementation		
	        
			digester.parse(input);
			result = unitBeans;
		} catch (Exception e) {
			throwException(null, e);
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) { }
			}
		}
		
		return result;
	}
	
	/**
	 * create a Unit based on th
	 * @param  unitBean the raw unit data to process
	 * @return          the unit
	 * @throws LoadUnitException 
	 */
	private static final Unit processUnit(final UnitBean unitBean) throws LoadUnitException {
		Unit result = null;
		Unit unit   = null; 
		
		try {		
			Class<?> unitClass = ClassLocator.find(unitBean.getType().replaceAll(" ", ""), Unit.class,
					new String[] {"structural", "small"});
						
			if (unitClass == null) {
				throw new IllegalArgumentException(unitBean.getType() + " is not a valid " + Unit.class.getName());
			} else {
				Constructor<?> constructor = unitClass.getConstructor(
						new Class[] {String.class, String.class, String.class, int.class, String.class, String.class, 
								int.class, int.class, int.class, int.class, int.class, ServiceHistory.class,
								Trait[].class, Properties.class});
							
				// process service history
				ServiceHistory serviceHistory = null;
				if ((unitBean.getServiceHistory() != null) && (unitBean.getServiceHistory().size() > 0)) {
					serviceHistory = new ServiceHistory();
					for (Iterator<PeriodOfServiceBean> iterator = unitBean.getServiceHistory().iterator(); iterator.hasNext();) {
						PeriodOfServiceBean periodOfServiceBean = iterator.next();
						serviceHistory.addPeriodOfService(new PeriodOfService(periodOfServiceBean.getFaction(), 
								periodOfServiceBean.getInService(), periodOfServiceBean.getEndedService(), 
								periodOfServiceBean.getDatePrefix(), periodOfServiceBean.getDateSuffix(),
								Availability.getAvailability(periodOfServiceBean.getAvailability()), 
								periodOfServiceBean.getMaxAvailable(), 
								Deployment.getDeployment(periodOfServiceBean.getDeployment()),
								periodOfServiceBean.getDeploymentPercentage(),	
								periodOfServiceBean.getOnlyOnePer(),
								periodOfServiceBean.getEconomicCostPoints(),
								periodOfServiceBean.getEconomicCostPercentage(),
								periodOfServiceBean.getNotes()));
					}
				}				
				
				// process traits
				Trait[] traits = null;
				if ((unitBean.getTraits() != null) && (unitBean.getTraits().size() > 0)) {
					List<Trait> validTraits = new ArrayList<Trait>(0);
					for (Iterator<String> iterator = unitBean.getTraits().iterator(); iterator.hasNext();) {
						Trait trait = Trait.getTrait(iterator.next());

						if (trait != null) {
							validTraits.add(trait);
						}
					}
					
					if (validTraits.size() > 0) {
						traits = validTraits.toArray(new Trait[validTraits.size()]);
					}
				}
				
				// create unit
				unit = (Unit) constructor.newInstance(unitBean.getName(), unitBean.getModel(), unitBean.getHull(),
						unitBean.getVersion(), unitBean.getAuthor(), unitBean.getSource(), unitBean.getPointValue(), 
						unitBean.getRammingFactor(), unitBean.getFwdAftDefense(), unitBean.getStbPortDefense(), 
						unitBean.getInitiativeModifier(), serviceHistory, traits, unitBean.getProperties());
				
				// load counter image
//				Image counter = null;
//		        try {
//					if (unitBean.getCounter().toLowerCase().startsWith("http")) {
//						counter = ImageIO.read(new URL(unitBean.getCounter()));
//					} else if (unitBean.getCounter().toLowerCase().startsWith("file")) {
//						counter = ImageIO.read(new File(unitBean.getCounter()));
//					} else {
//						if (directory != null) {
//				        	counter = ImageIO.read(new File(directory + File.separator + unitBean.getCounter()));						
//						} else {
//				        	counter = ImageIO.read(new File(unitBean.getCounter()));
//						}
//					}		
//				} catch (IOException e) {
//					Logger.error("Failed to load counter " + unitBean.getCounter() + " for " + Unit.class.getSimpleName() + 
//							" " + unitBean.getType() + ": " + e.getMessage());
//				}
//				if (counter == null) {
//					throw new IllegalArgumentException("Failed to load counter " + unitBean.getCounter() + " for " + 
//							Unit.class.getSimpleName() + " " + unitBean.getType());
//				} else {
//					unit.setCounter(counter);
//				}
				
				// process specific unit class
				if (unit instanceof StructuralUnit) {
					result = processStructuralUnit(unitBean, (StructuralUnit) unit);
				} else if (unit instanceof FighterFlight) {
					result = processFighterFlight(unitBean, (FighterFlight) unit);
				} else {
					throw new IllegalArgumentException("Unknown " + Unit.class.getSimpleName() + 
							" type for " + unit.getClass().getName());
				}				
			}
		} catch (Exception e) {
			throwException(null, e);
		}		
		
		return result;
	}
	
	private static final Unit processStructuralUnit(final UnitBean unitBean, StructuralUnit unit) throws Exception {
		try {
			// process hit location charts
			List<HitLocationChart>        hitLocationCharts   = new ArrayList<HitLocationChart>(0);
			Map<String, HitLocationChart> hitLocationChartMap = new HashMap<String, HitLocationChart>();
			
			for (Iterator<HitLocationChartBean> iterator = unitBean.getHitLocationCharts().iterator(); iterator.hasNext();) {
				HitLocationChartBean hitLocationChartBean = iterator.next();
				HitLocationChart hitLocationChart = new HitLocationChart(hitLocationChartBean.getName());
				
				for (Iterator<HitLocationBean> iter = hitLocationChartBean.getHitLocations().iterator(); iter.hasNext();) {
					hitLocationChart.addHitLocation(processHitLocation(iter.next()));						
				}
				
				if (hitLocationChart.getHitLocationCount() > 0) {
					hitLocationCharts.add(hitLocationChart);
					hitLocationChartMap.put(hitLocationChart.getName(), hitLocationChart);
				}
			}		
			if ((hitLocationCharts != null) && (hitLocationCharts.size() > 0)) {
				unit.setHitLocationCharts(hitLocationCharts);
			}
			
			// set general hit location chart
			if ((unitBean.getGeneralHitLocationChart() != null) && (unitBean.getGeneralHitLocationChart().length() > 0)) {
				HitLocationChart hitLocationChart = hitLocationChartMap.get(unitBean.getGeneralHitLocationChart());
				
				if (hitLocationChart != null) {
					unit.setGeneralHitLocationChart(hitLocationChart);
				} else {
					throw new IllegalArgumentException(HitLocationChart.class.getSimpleName() + " \"" + 
							unitBean.getGeneralHitLocationChart() + "\" is undefined");
				}
			}
			
			// process sections & systems
			for (Iterator<SectionBean> iterator = unitBean.getSections().iterator(); iterator.hasNext();) {
				unit.addSection(processSection(iterator.next(), hitLocationChartMap));				
			}		
			
			// link child sections
			for (Iterator<SectionBean> iterator = unitBean.getSections().iterator(); iterator.hasNext();) {
				SectionBean sectionBean = iterator.next();
				Section section = unit.getSection(sectionBean.getName());
								
				if ((section != null) && (section instanceof ChildSection)) {
					try {
						String parentSections = sectionBean.getProperties().getProperty("parentSections");
						
						String[] sectionNames = parentSections.split(",");
						for (int index = 0; index < sectionNames.length; index++) {
							unit.getSection(sectionNames[index]).addChildSection((ChildSection) section);
						}
					} catch (Exception e) {
						throw new IllegalArgumentException("Invalid property parentSections for " + ChildSection.class.getName() +
								" " + sectionBean.getName());
					}		
				}
			}				
			
			return unit;
		} catch (Exception e) {
			throwException(null, e);
		}
		
		return null;
	}
		
	private static final HitLocation processHitLocation(HitLocationBean hitLocationBean) throws Exception {
		Class<?>    hitLocationClass = null;
		HitLocation hitLocation      = null;
			
		try {
			hitLocationClass = ClassLocator.find(hitLocationBean.getClassName(), HitLocation.class, null);
			
			if (hitLocationClass == null) {
				throw new IllegalArgumentException(hitLocationBean.getClassName() + " is not a valid " + HitLocation.class.getName());
			} else {
				Constructor<?> constructor = hitLocationClass.getConstructor(
						new Class[] {int.class, int.class, Properties.class});
				hitLocation = (HitLocation) constructor.newInstance(
						hitLocationBean.getFrom(), hitLocationBean.getTo(), hitLocationBean.getProperties());
	
				// process sub-hit locations
				if (hitLocationBean.getHitLocations().size() > 0) {
			        if (hitLocation instanceof MultiHitLocation) {
						for (Iterator<HitLocationBean> iter = hitLocationBean.getHitLocations().iterator(); iter.hasNext();) {
							((MultiHitLocation) hitLocation).addHitLocation(processHitLocation(iter.next()));						
						}
					} else {
						throw new IllegalArgumentException(hitLocation.getClass().getSimpleName() + " does not support child " + 
								HitLocation.class.getSimpleName() + "s");
					}		
				}
				
				return hitLocation;
			}	
		} catch (Exception e) {
			throwException((hitLocation != null) ? hitLocation.getLocationDescription() : 
				(hitLocationClass != null) ? hitLocationClass.getSimpleName() : null, e);
		}
		
		return null;
	}

	private static final Section processSection(SectionBean sectionBean, Map<String, HitLocationChart> hitLocationCharts) throws Exception {
		Class<?> sectionClass = null; 
		Section  section      = null;
			
		try {
			// get hit location chart
			HitLocationChart hitLocationChart = null;
			if ((sectionBean.getHitLocationChart() != null) && (sectionBean.getHitLocationChart().length() > 0)) {
				hitLocationChart = hitLocationCharts.get(sectionBean.getHitLocationChart());
				
				if (hitLocationChart == null) {
					throw new IllegalArgumentException(HitLocationChart.class.getSimpleName() + " \"" + 
							sectionBean.getHitLocationChart() + "\" is undefined");
				}
			}
			
			// process systems
			List<System> systems = new ArrayList<System>(0);			
			for (Iterator<SystemBean> iterator = sectionBean.getSystems().iterator(); iterator.hasNext();) {
				System system = processSystem(iterator.next());
				
				if (system != null) {
					systems.add(system);	
				}
			}
	
			// process section
			sectionClass = ClassLocator.find(sectionBean.getClassName(), Section.class, null);
			
			if (sectionClass == null) {
				throw new IllegalArgumentException(sectionBean.getClassName() + " is not a valid " + Section.class.getName());
			} else {
				Constructor<?> constructor = sectionClass.getConstructor(
						new Class[] {String.class, Arc.class, HitLocationChart.class, Properties.class});
				section = (Section) constructor.newInstance(
						sectionBean.getName(), sectionBean.getArc(), hitLocationChart,
						sectionBean.getProperties());
	
				for (Iterator<System> iterator = systems.iterator(); iterator.hasNext();) {
					section.addSystem(iterator.next());
				}			
				
				// process sub-sections
				if (sectionBean.getSections().size() > 0) {
					Method method = null;
					
					try {		
						method = sectionClass.getDeclaredMethod("addSection", new Class[] {Section.class});
					} catch (Exception e) {
						throw new IllegalArgumentException(section.getClass().getSimpleName() + " does not support sub-" + 
								Section.class.getSimpleName() + "s");
					}		
	
					if (method != null) {
						for (Iterator<SectionBean> iter = sectionBean.getSections().iterator(); iter.hasNext();) {
							method.invoke(section, processSection(iter.next(), hitLocationCharts));						
						}
					}
				}
				
				return section;
			}	
		} catch (Exception e) {
			throwException((section != null) ? section.getName() + " " + Section.class.getSimpleName(): 
				(sectionClass != null) ? sectionClass.getSimpleName() : null, e);
		}
		
		return null;
	}
	
	private static final System processSystem(SystemBean systemBean) throws Exception {
		Class<?> systemClass = null;
		System   system      = null;
		
		try {
			// try and find class of special weapons (e.g. shields) before using generic Weapon class
			if (Weapon.class.getSimpleName().equals(systemBean.getClassName())) {
				try {
					String weaponType = systemBean.getProperties().getProperty("weaponType");					
					systemClass	= ClassLocator.find(weaponType.replaceAll(" ", ""), Weapon.class, null);			
				} catch (Exception e) {}
			}
			if (systemClass == null) {
				try {
					systemClass	= ClassLocator.find(systemBean.getClassName(), System.class, null);
				} catch (Exception e) {}
			}			
			if (systemClass == null) {
				system = new GenericSystem(systemBean.getClassName(), systemBean.getDamageBoxes(), 
						systemBean.getArmor(), systemBean.getArc(), systemBean.getName(), systemBean.getProperties());
			} else {
				Constructor<?> constructor = systemClass.getConstructor(
						new Class[] {int.class, int.class, Arc.class, String.class, Properties.class});
				system = (System) constructor.newInstance(
						systemBean.getDamageBoxes(), systemBean.getArmor(), systemBean.getArc(), 
						systemBean.getName(), systemBean.getProperties());				
			}

			if (systemBean.getSystemIcon() != null) {
				system.setIconPosition(new IconPosition(systemBean.getSystemIcon().getX(), 
						systemBean.getSystemIcon().getY(), systemBean.getSystemIcon().isMirror(), 
						systemBean.getSystemIcon().getRotation(), systemBean.getSystemIcon().getIndex(),
						systemBean.getSystemIcon().getDefinition()));
			}
			if (systemBean.getArmorIcon() != null) {
				system.setArmorIconPosition(new IconPosition(systemBean.getArmorIcon().getX(), 
						systemBean.getArmorIcon().getY(), systemBean.getArmorIcon().isMirror(), 
						systemBean.getArmorIcon().getRotation(), systemBean.getArmorIcon().getIndex(),
						systemBean.getArmorIcon().getDefinition()));
			}
			if ((systemBean.getPowerIcon() != null) && (system instanceof PoweredSystem)) {
				((PoweredSystem) system).setPowerIconPosition(new IconPosition(systemBean.getPowerIcon().getX(), 
						systemBean.getPowerIcon().getY(), systemBean.getPowerIcon().isMirror(), 
						systemBean.getPowerIcon().getRotation(), systemBean.getPowerIcon().getIndex(),
						systemBean.getPowerIcon().getDefinition()));
			}
			if (systemBean.getArcIcon() != null) {
				system.setArcIconPosition(new IconPosition(systemBean.getArcIcon().getX(), 
						systemBean.getArcIcon().getY(), systemBean.getArcIcon().isMirror(), 
						systemBean.getArcIcon().getRotation(), systemBean.getArcIcon().getIndex(),
						systemBean.getArcIcon().getDefinition()));
			}				
			if ((systemBean.getNumberIcon() != null) && (system instanceof Weapon)) {
				((Weapon) system).setWeaponNumberPosition(new IconPosition(systemBean.getNumberIcon().getX(), 
						systemBean.getNumberIcon().getY(), systemBean.getNumberIcon().isMirror(), 
						systemBean.getNumberIcon().getRotation(), systemBean.getNumberIcon().getIndex(),
						systemBean.getNumberIcon().getDefinition()));
			}
			
			return system;
		} catch (Exception e) {
			throwException((system != null) ? system.getFullName() : 
				(systemClass != null) ? systemClass.getSimpleName() + ((systemClass.equals(Weapon.class)) ? " \"" + systemBean.getProperties().getProperty("weaponType") + "\"" : "") : null, e);
		}
		
		return null;
	}
	
	private static final Unit processFighterFlight(final UnitBean unitBean, FighterFlight unit) throws LoadUnitException {
		Unit result = null;
		
			// TODO	implementation		
			
			result = unit;
		
		return result;
	}
	
	private static final void throwException(String message, Exception e) throws LoadUnitException {
		if (e == null) {
			throw new IllegalArgumentException("Exception cannot be null");
		} else {	
			String detail = (message != null) ? message + " - " : "";
			if (((e.getMessage() == null) || !(e.getMessage().trim().length() > 0)) && (e.getCause() != null)) {
				throw new LoadUnitException(detail + e.getCause().getMessage(), e.getCause());
			} else {
				throw new LoadUnitException(detail + e.getMessage(), e);				
			}		
		}
	}
	
}
