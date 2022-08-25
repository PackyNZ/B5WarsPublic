package net.b5gamer.b5wars.io.unit;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.SAXException;

import net.b5gamer.b5wars.unit.MobileUnit;
import net.b5gamer.b5wars.unit.PeriodOfService;
import net.b5gamer.b5wars.unit.Trait;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.small.SmallUnit;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocation;
import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocationChart;
import net.b5gamer.b5wars.unit.structural.hitlocation.MultiHitLocation;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.section.SectionCollection;
import net.b5gamer.b5wars.unit.structural.system.GenericSystem;
import net.b5gamer.b5wars.unit.structural.system.PoweredSystem;
import net.b5gamer.b5wars.unit.structural.system.Structure;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.b5wars.unit.structural.system.Weapon;
import net.b5gamer.icon.DamageBoxStringSerializer;
import net.b5gamer.util.Properties;

public final class UnitXMLWriter {

	private UnitXMLWriter() {
	}
	
	public static final void write(Unit unit, OutputStream output) throws Exception {
		try {
			Document     document = createDocument(unit);
			DOMSource    source   = new DOMSource(document);
			StreamResult result   = new StreamResult(new OutputStreamWriter(output, "UTF-8"));
			
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			transformerFactory.setAttribute("indent-number", Integer.valueOf(4));
			Transformer transformer = transformerFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.transform(source, result);
		} finally {
			if (output != null) {
				try {
					output.close();
				} catch (IOException e) { }
			}
		}
	}
	
	public static final Document createDocument(Unit unit) throws ParserConfigurationException, SAXException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
//		factory.setSchema(SchemaFactory.newInstance(XMLConstants.W3C_XML_SCHEMA_NS_URI).newSchema(new StreamSource(UnitXMLWriter.class.getResourceAsStream("unit.xsd"))));
//		factory.setNamespaceAware(true);
//		factory.setValidating(true);
//		factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaLanguage", 
//                   "http://www.w3.org/2001/XMLSchema");
//		factory.setAttribute("http://java.sun.com/xml/jaxp/properties/schemaSource", 
//                   "file:unit.xsd");
		// TODO validation
		
	    DocumentBuilder builder = factory.newDocumentBuilder();
//	    builder.setErrorHandler(new UnitXMLErrorHandler());
	    Document document = builder.newDocument();

	    // unit-definition
        Element rootElement = document.createElement("unit-definition");
        document.appendChild(rootElement);
        // TODO kinda naff, but it works for now...
        Attr attribute = document.createAttribute("xmlns");
        attribute.setNodeValue("http://www.b5gamer.net/B5Wars");
        rootElement.getAttributes().setNamedItem(attribute);
        attribute = document.createAttribute("xmlns:xsi");
        attribute.setNodeValue("http://www.w3.org/2001/XMLSchema-instance");
        rootElement.getAttributes().setNamedItem(attribute);
        attribute = document.createAttribute("xsi:schemaLocation");
        attribute.setNodeValue("http://www.b5gamer.net/B5Wars unit.xsd");
        rootElement.getAttributes().setNamedItem(attribute);
        
        // unit
        Element unitElement = document.createElement("unit");
        rootElement.appendChild(unitElement);
         attribute = document.createAttribute("name");
        attribute.setNodeValue(unit.getName());
        unitElement.getAttributes().setNamedItem(attribute);
        if ((unit.getModel() != null) && (unit.getModel().length() > 0)) {
	        attribute = document.createAttribute("model");
	        attribute.setNodeValue(unit.getModel());
	        unitElement.getAttributes().setNamedItem(attribute);
        }
        attribute = document.createAttribute("hull");
        attribute.setNodeValue(unit.getHull());
        unitElement.getAttributes().setNamedItem(attribute);
        attribute = document.createAttribute("version");
        attribute.setNodeValue(String.valueOf(unit.getVersion()));
        unitElement.getAttributes().setNamedItem(attribute);
        if ((unit.getAuthor() != null) && (unit.getAuthor().length() > 0)) {
	        attribute = document.createAttribute("author");
	        attribute.setNodeValue(unit.getAuthor());
	        unitElement.getAttributes().setNamedItem(attribute);
        }
        if ((unit.getSource() != null) && (unit.getSource().length() > 0)) {
	        attribute = document.createAttribute("source");
	        attribute.setNodeValue(unit.getSource());
	        unitElement.getAttributes().setNamedItem(attribute);
        }

        // specs
        Element specsElement = document.createElement("specs");
        unitElement.appendChild(specsElement);
        attribute = document.createAttribute("type");
        attribute.setNodeValue(unit.getType());
        specsElement.getAttributes().setNamedItem(attribute);
        attribute = document.createAttribute("pointValue");
        attribute.setNodeValue(String.valueOf(unit.getPointValue()));
        specsElement.getAttributes().setNamedItem(attribute);
        attribute = document.createAttribute("rammingFactor");
        attribute.setNodeValue(String.valueOf(unit.getRammingFactor()));
        specsElement.getAttributes().setNamedItem(attribute);
        
        // maneuvering
		if (unit instanceof MobileUnit) {
			MobileUnit mobileUnit = (MobileUnit) unit;

			Element maneuveringElement = document.createElement("maneuvering");
	        unitElement.appendChild(maneuveringElement);
	        attribute = document.createAttribute("turnCost");
	        attribute.setNodeValue(mobileUnit.getTurnCostFormatted());
	        maneuveringElement.getAttributes().setNamedItem(attribute);
	        attribute = document.createAttribute("turnDelay");
	        attribute.setNodeValue(mobileUnit.getTurnDelayFormatted());
	        maneuveringElement.getAttributes().setNamedItem(attribute);
	        attribute = document.createAttribute("accelDecelCost");
	        attribute.setNodeValue(String.valueOf(mobileUnit.getAccelDecelCost()));
	        maneuveringElement.getAttributes().setNamedItem(attribute);   
	        attribute = document.createAttribute("pivotCost");
	        attribute.setNodeValue(mobileUnit.isPivotCapable() ? mobileUnit.getPivotCostFormatted() : "N/A");
	        maneuveringElement.getAttributes().setNamedItem(attribute);
	        attribute = document.createAttribute("rollCost");
	        attribute.setNodeValue(mobileUnit.isRollCapable() ? mobileUnit.getRollCostFormatted() : "N/A");
	        maneuveringElement.getAttributes().setNamedItem(attribute);
		}
		 
        // combat-stats
        Element combatStatsElement = document.createElement("combat-stats");
        unitElement.appendChild(combatStatsElement);
        attribute = document.createAttribute("fwdAftDefense");
        attribute.setNodeValue(String.valueOf(unit.getFwdAftDefense()));
        combatStatsElement.getAttributes().setNamedItem(attribute);
        attribute = document.createAttribute("stbPortDefense");
        attribute.setNodeValue(String.valueOf(unit.getStbPortDefense()));
        combatStatsElement.getAttributes().setNamedItem(attribute);
        attribute = document.createAttribute("initiativeModifier");
        attribute.setNodeValue(String.valueOf(unit.getInitiativeModifier()));
        combatStatsElement.getAttributes().setNamedItem(attribute);
        
        // service-history
		if ((unit.getServiceHistory() != null) && (unit.getServiceHistory().getPeriodOfServiceCount() > 0)) {
	        Element serviceHistoryElement = document.createElement("service-history");
	        unitElement.appendChild(serviceHistoryElement);

			for (Iterator<PeriodOfService> iterator = unit.getServiceHistory().getPeriodOfServiceIterator(); iterator.hasNext();) {
				PeriodOfService periodOfService = iterator.next();
				
				Element periodOfServiceElement = document.createElement("period-of-service");
		        serviceHistoryElement.appendChild(periodOfServiceElement);
		        
		        attribute = document.createAttribute("faction");
		        attribute.setNodeValue(periodOfService.getFaction());
		        periodOfServiceElement.getAttributes().setNamedItem(attribute);
		        if (periodOfService.getInService() != 0) {
			        attribute = document.createAttribute("inService");
			        attribute.setNodeValue(String.valueOf(periodOfService.getInService()));
			        periodOfServiceElement.getAttributes().setNamedItem(attribute);
		        }
		        if (periodOfService.getEndedService() != 0) {
			        attribute = document.createAttribute("endedService");
			        attribute.setNodeValue(String.valueOf(periodOfService.getEndedService()));
			        periodOfServiceElement.getAttributes().setNamedItem(attribute);
		        }
		        if ((periodOfService.getDatePrefix() != null) && (periodOfService.getDatePrefix().length() > 0)) {
			        attribute = document.createAttribute("datePrefix");
			        attribute.setNodeValue(periodOfService.getDatePrefix());
			        periodOfServiceElement.getAttributes().setNamedItem(attribute);
		        }
		        if ((periodOfService.getDateSuffix() != null) && (periodOfService.getDateSuffix().length() > 0)) {
			        attribute = document.createAttribute("dateSuffix");
			        attribute.setNodeValue(periodOfService.getDateSuffix());
			        periodOfServiceElement.getAttributes().setNamedItem(attribute);
		        }
		        if (periodOfService.getAvailability() != null) {
			        attribute = document.createAttribute("availability");
			        attribute.setNodeValue(String.valueOf(periodOfService.getAvailability().getName()));
			        periodOfServiceElement.getAttributes().setNamedItem(attribute);
		        }
		        if (periodOfService.getMaximumAvailable() != 0) {
			        attribute = document.createAttribute("maxAvailable");
			        attribute.setNodeValue(String.valueOf(periodOfService.getMaximumAvailable()));
			        periodOfServiceElement.getAttributes().setNamedItem(attribute);
		        }
		        if (periodOfService.getDeployment() != null) {
			        attribute = document.createAttribute("deployment");
			        attribute.setNodeValue(String.valueOf(periodOfService.getDeployment().getName()));
			        periodOfServiceElement.getAttributes().setNamedItem(attribute);
		        }
		        if (periodOfService.getDeploymentPercentage() != 0) {
			        attribute = document.createAttribute("deploymentPercentage");
			        attribute.setNodeValue(String.valueOf(periodOfService.getDeploymentPercentage()) + "%");
			        periodOfServiceElement.getAttributes().setNamedItem(attribute);
		        }
		        if (periodOfService.getOnlyOnePer() != 0) {
			        attribute = document.createAttribute("onlyOnePer");
			        attribute.setNodeValue(String.valueOf(periodOfService.getOnlyOnePer()));
			        periodOfServiceElement.getAttributes().setNamedItem(attribute);
		        }
		        if (periodOfService.getEconomicCostPoints() != 0) {
			        attribute = document.createAttribute("economicCost");
			        attribute.setNodeValue(String.valueOf(periodOfService.getEconomicCostPoints()));
			        periodOfServiceElement.getAttributes().setNamedItem(attribute);
		        } else if (periodOfService.getEconomicCostPercentage() != 0) {
			        attribute = document.createAttribute("economicCost");
			        attribute.setNodeValue(String.valueOf(periodOfService.getEconomicCostPercentage()) + "%");
			        periodOfServiceElement.getAttributes().setNamedItem(attribute);
		        }
			}
		}
        
        // traits
		if (unit.getTraitCount() > 0) {
	        Element traitsElement = document.createElement("traits");
	        unitElement.appendChild(traitsElement);

	        for (Iterator<Trait> iterator = unit.getTraitIterator(); iterator.hasNext();) {
	        	Trait trait = iterator.next();
				
				Element traitElement = document.createElement("trait");
				traitsElement.appendChild(traitElement);
	
		        attribute = document.createAttribute("name");
		        attribute.setNodeValue(trait.getName());
		        traitElement.getAttributes().setNamedItem(attribute);	
		        // TODO
//		        if ((trait.getValue() != null) && (trait.getValue().length() > 0)) {
//			        attribute = document.createAttribute("value");
//			        attribute.setNodeValue(trait.getValue()); 
//			        traitElement.getAttributes().setNamedItem(attribute);	
//		        }
	        }
		}

		// adaptive-armor TODO
		
		
		
		
		if (unit instanceof StructuralUnit) {
			StructuralUnit ship = (StructuralUnit) unit;
			
			// ship-definition
	        Element shipElement = document.createElement("ship-definition");
	        unitElement.appendChild(shipElement);

	        // hit-location-charts
	        Element hitLocationChartsElement = document.createElement("hit-location-charts");
	        shipElement.appendChild(hitLocationChartsElement);
	        
			for (Iterator<HitLocationChart> iterator = ship.getHitLocationCharts().iterator(); iterator.hasNext();) {
				HitLocationChart chart = iterator.next();
			
		        Element hitLocationChartElement = document.createElement("hit-location-chart");
		        hitLocationChartsElement.appendChild(hitLocationChartElement);
		        attribute = document.createAttribute("name");
		        attribute.setNodeValue(chart.getName());
		        hitLocationChartElement.getAttributes().setNamedItem(attribute);
				
				for (Iterator<HitLocation> locationIter = chart.getHitLocationIterator(); locationIter.hasNext();) {
					addHitLocation(document, hitLocationChartElement, locationIter.next());
				}				
			}
	                
	        // sections
	        Element sectionsElement = document.createElement("sections");
	        shipElement.appendChild(sectionsElement);
	        if (ship.getGeneralHitLocationChart() != null) {
		        attribute = document.createAttribute("generalHitLocationChart");
		        attribute.setNodeValue(ship.getGeneralHitLocationChart().getName());
		        sectionsElement.getAttributes().setNamedItem(attribute);
	        } 		
	        addSections(document, sectionsElement, ship);		
		} else if (unit instanceof SmallUnit) {
			// fighter-definition TODO
		
		
		
		}
        
		return document;
	}
	
	private static final void addHitLocation(Document document, Element parent, HitLocation hitLocation) {
		// hit-location
        Element hitLocationElement = document.createElement("hit-location");
        parent.appendChild(hitLocationElement);
        Attr attribute = document.createAttribute("class");
        attribute.setNodeValue(hitLocation.getClass().getSimpleName());
        hitLocationElement.getAttributes().setNamedItem(attribute);
        attribute = document.createAttribute("from");
        attribute.setNodeValue(String.valueOf(hitLocation.getFrom()));
        hitLocationElement.getAttributes().setNamedItem(attribute);
        attribute = document.createAttribute("to");
        attribute.setNodeValue(String.valueOf(hitLocation.getTo()));
        hitLocationElement.getAttributes().setNamedItem(attribute);
	                
        // properties
        addProperties(document, hitLocationElement, hitLocation.getProperties());      
        
        // child hit-locations
        if (hitLocation instanceof MultiHitLocation) {
			for (Iterator<HitLocation> iterator = ((MultiHitLocation) hitLocation).getHitLocationIterator(); iterator.hasNext();) {
				addHitLocation(document, hitLocationElement, iterator.next());
			}
        }
	}

	private static final void addSections(Document document, Element parent, SectionCollection sections) {
		for (Iterator<Section> iterator = sections.getSectionIterator(); iterator.hasNext();) {
			Section section = iterator.next();
		
	        Element sectionElement = document.createElement("section");
	        parent.appendChild(sectionElement);
	        Attr attribute = document.createAttribute("name");
	        attribute.setNodeValue(section.getName());
	        sectionElement.getAttributes().setNamedItem(attribute);
	        attribute = document.createAttribute("class");
	        attribute.setNodeValue(section.getClass().getSimpleName());
	        sectionElement.getAttributes().setNamedItem(attribute);
	        if (section.getArc() != null) {
		        attribute = document.createAttribute("arc");
		        attribute.setNodeValue(section.getArc().toString());
		        sectionElement.getAttributes().setNamedItem(attribute);
	        }
	        if (section.getHitLocationChart() != null) {
		        attribute = document.createAttribute("hitLocationChart");
		        attribute.setNodeValue(section.getHitLocationChart().getName());
		        sectionElement.getAttributes().setNamedItem(attribute);
	        }
	        
	        // properties
	        addProperties(document, sectionElement, section.getProperties());      
			
			// systems
	        Element systemsElement = document.createElement("systems");
	        sectionElement.appendChild(systemsElement);

	        for (Iterator<System> iter = section.getSystemIterator(); iter.hasNext();) {
				System system = iter.next();
				
				Element systemElement = document.createElement((system instanceof Weapon) ? "weapon" : "system");
		        systemsElement.appendChild(systemElement);

		        if (system instanceof Weapon) {
			        attribute = document.createAttribute("type");
			        attribute.setNodeValue(system.getType());
			        systemElement.getAttributes().setNamedItem(attribute);
				} else {
			        attribute = document.createAttribute("class");
			        attribute.setNodeValue((system instanceof GenericSystem) ? system.getType() : system.getClass().getSimpleName());
			        systemElement.getAttributes().setNamedItem(attribute);					
				}

		        if (!system.isDamageBoxesFixed()) {
			        attribute = document.createAttribute("damageBoxes");
			        attribute.setNodeValue(String.valueOf(system.getBaseDamageBoxes()));
			        systemElement.getAttributes().setNamedItem(attribute);					
		        }

		        if ((system instanceof Weapon) || (system.getBaseArmor() != 0)) {
			        attribute = document.createAttribute("armor");
			        attribute.setNodeValue(String.valueOf(system.getBaseArmor()));
			        systemElement.getAttributes().setNamedItem(attribute);							        	
		        }

		        if ((system instanceof Weapon) || (system.getArc() != null)) {
			        attribute = document.createAttribute("arc");
			        attribute.setNodeValue(system.getArc().toString());
			        systemElement.getAttributes().setNamedItem(attribute);							        	
		        }

		        if ((system.getName() != null) && (system.getName().length() > 0)) {
			        attribute = document.createAttribute((system instanceof Weapon) ? "number" : "name");
			        attribute.setNodeValue(system.getName());
			        systemElement.getAttributes().setNamedItem(attribute);
		        }
		        
		        if ((system instanceof PoweredSystem) && !(system instanceof Weapon)) {
			        attribute = document.createAttribute("power");
			        attribute.setNodeValue(String.valueOf(((PoweredSystem) system).getPowerRequirement()));
			        systemElement.getAttributes().setNamedItem(attribute);							        	
		        }
		        
		        // properties
		        addProperties(document, systemElement, system.getInitProperties());      				

		        // icons
				Element systemIconsElement = document.createElement((system instanceof Weapon) ? "weapon-icons" : "system-icons");

		        if (((system.getIconPosition().getX() != 0) && (system.getIconPosition().getY() != 0)) ||
		        		(system instanceof Structure)) {
			        Element iconElement = document.createElement(
			        		(system instanceof Weapon) ? "weapon-icon" : (system instanceof Structure) ? "structure-icon" : "system-icon");
			        systemIconsElement.appendChild(iconElement);
			        
			        attribute = document.createAttribute("x");
			        attribute.setNodeValue(String.valueOf((int) system.getIconPosition().getX()));
			        iconElement.getAttributes().setNamedItem(attribute);
			        attribute = document.createAttribute("y");
			        attribute.setNodeValue(String.valueOf((int) system.getIconPosition().getY()));
			        iconElement.getAttributes().setNamedItem(attribute);
			        
			        if (system.getIconPosition().isMirror()) {
				        attribute = document.createAttribute("mirror");
				        attribute.setNodeValue(String.valueOf(system.getIconPosition().isMirror()));
				        iconElement.getAttributes().setNamedItem(attribute);
			        }
			        
			        if (system.getIconPosition().getRotation() != 0) {
				        attribute = document.createAttribute("rotation");
				        attribute.setNodeValue(String.valueOf(system.getIconPosition().getRotation()));
				        iconElement.getAttributes().setNamedItem(attribute);
			        }

			        if (!(system instanceof Weapon) && !(system instanceof Structure) && (system.getIconPosition().getIndex() != 1)) {
				        attribute = document.createAttribute("index");
				        attribute.setNodeValue(String.valueOf(system.getIconPosition().getIndex()));
				        iconElement.getAttributes().setNamedItem(attribute);
			        }
			        
			        if (system instanceof Structure) {
				        attribute = document.createAttribute("definition");
				        attribute.setNodeValue(DamageBoxStringSerializer.serialize(system.getIcon().getDamageBoxes()));
				        iconElement.getAttributes().setNamedItem(attribute);
			        }			        
		        }
		        
		        if ((system.getArmorIconPosition().getX() != 0) && (system.getArmorIconPosition().getY() != 0)) {
			        Element iconElement = document.createElement("armor-icon");
			        systemIconsElement.appendChild(iconElement);
			        
			        attribute = document.createAttribute("x");
			        attribute.setNodeValue(String.valueOf((int) system.getArmorIconPosition().getX()));
			        iconElement.getAttributes().setNamedItem(attribute);
			        attribute = document.createAttribute("y");
			        attribute.setNodeValue(String.valueOf((int) system.getArmorIconPosition().getY()));
			        iconElement.getAttributes().setNamedItem(attribute);
		        }

		    	if ((system instanceof PoweredSystem) && 
		    			(((PoweredSystem) system).getPowerIconPosition().getX() != 0) && 
		    			(((PoweredSystem) system).getPowerIconPosition().getY() != 0)) {
			        Element iconElement = document.createElement("power-icon");
			        systemIconsElement.appendChild(iconElement);
			        
			        attribute = document.createAttribute("x");
			        attribute.setNodeValue(String.valueOf((int) ((PoweredSystem) system).getPowerIconPosition().getX()));
			        iconElement.getAttributes().setNamedItem(attribute);
			        attribute = document.createAttribute("y");
			        attribute.setNodeValue(String.valueOf((int) ((PoweredSystem) system).getPowerIconPosition().getY()));
			        iconElement.getAttributes().setNamedItem(attribute);
		        }
		        
		    	if ((system.getArc() != null) && (system.getArcIconPosition().getX() != 0) && 
		    			(system.getArcIconPosition().getY() != 0)) {
			        Element iconElement = document.createElement("arc-icon");
			        systemIconsElement.appendChild(iconElement);
			        
			        attribute = document.createAttribute("x");
			        attribute.setNodeValue(String.valueOf((int) system.getArcIconPosition().getX()));
			        iconElement.getAttributes().setNamedItem(attribute);
			        attribute = document.createAttribute("y");
			        attribute.setNodeValue(String.valueOf((int) system.getArcIconPosition().getY()));
			        iconElement.getAttributes().setNamedItem(attribute);
		        }
		        
		    	if ((system instanceof Weapon) && (system.getName() != null) && 
		    			(((Weapon) system).getWeaponNumberPosition().getX() != 0) && 
		    			(((Weapon) system).getWeaponNumberPosition().getY() != 0)) {
			        Element iconElement = document.createElement("number-icon");
			        systemIconsElement.appendChild(iconElement);
			        
			        attribute = document.createAttribute("x");
			        attribute.setNodeValue(String.valueOf((int) ((Weapon) system).getWeaponNumberPosition().getX()));
			        iconElement.getAttributes().setNamedItem(attribute);
			        attribute = document.createAttribute("y");
			        attribute.setNodeValue(String.valueOf((int) ((Weapon) system).getWeaponNumberPosition().getY()));
			        iconElement.getAttributes().setNamedItem(attribute);
		        }
		    	
		        if (systemIconsElement.hasChildNodes()) {
		        	systemElement.appendChild(systemIconsElement);
		        }
			}			
			
			// child sections
			if (section instanceof SectionCollection) {
		        addSections(document, sectionElement, (SectionCollection) section);						
			}
		}       
	}
	
	private static final void addProperties(Document document, Element parent, Properties properties) {
        if (properties != null) {
            for (Iterator<String> iterator = properties.keySet().iterator(); iterator.hasNext();) {
                String key = iterator.next();            	
                addProperty(document, parent, key, properties.getProperty(key));
            }
        }
    }
	
	private static final void addProperty(Document document, Element parent, String name, String value) {
        Element propertyElement = document.createElement("property");
        parent.appendChild(propertyElement);
        Attr attribute = document.createAttribute("name");
        attribute.setNodeValue(name);
        propertyElement.getAttributes().setNamedItem(attribute);
        attribute = document.createAttribute("value");
        attribute.setNodeValue(value);
        propertyElement.getAttributes().setNamedItem(attribute);
	}

}
