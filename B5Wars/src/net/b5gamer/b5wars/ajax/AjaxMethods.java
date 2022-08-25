package net.b5gamer.b5wars.ajax;

import java.io.ByteArrayInputStream;

import jakarta.servlet.http.HttpSession;

import net.b5gamer.b5wars.io.DataManager;
import net.b5gamer.b5wars.io.ExportServlet;
import net.b5gamer.b5wars.io.unit.LoadUnitException;
import net.b5gamer.b5wars.io.unit.UnitXMLReader;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.UnitID;
import net.b5gamer.b5wars.unit.small.SmallUnit;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;

import org.apache.log4j.Logger;

public class AjaxMethods {

	private static final Logger logger = Logger.getLogger(AjaxMethods.class.getName());

	public AjaxMethods() {
	}
	
	public UnitBean getUnit(final String name, final String model, final int version) {
		if (logger.isDebugEnabled()) {
			logger.debug("getUnit(name=" + name + ", model=" + model + ", version=" + version + ")");
		}

		try {
			Unit unit = DataManager.getUnitDao().load(new UnitID(name, model, version));
			
			if (logger.isDebugEnabled()) {
				if (unit != null) {
					logger.debug("getUnit() unit = " + unit.getFullName());
				} else {
					logger.debug("getUnit() unit = null");
				}
			}
					    
		    return (unit instanceof StructuralUnit) ? new StructuralUnitBean(((StructuralUnit) unit)) : new SmallUnitBean(((SmallUnit) unit));
		} catch (Exception e) {
			logger.error("Failed to load Unit '" + name + " " + model + " " + version + "'", e);
			e.printStackTrace();

			return null;
		}
	}

	public UnitBean getUnit(final String xml) {
		if (logger.isDebugEnabled()) {
			logger.debug("getUnit() xml=" + xml);
		}

		try {
			Unit unit = UnitXMLReader.read(new ByteArrayInputStream(xml.getBytes()));
			
			if (logger.isDebugEnabled()) {
				if (unit != null) {
					logger.debug("getUnit() unit = " + unit.getFullName());
				} else {
					logger.debug("getUnit() unit = null");
				}
			}
					    
		    return (unit instanceof StructuralUnit) ? new StructuralUnitBean(((StructuralUnit) unit)) : new SmallUnitBean(((SmallUnit) unit));
		} catch (LoadUnitException e) {
			logger.error("Failed to load Unit from xml", e);
			e.printStackTrace();

			return null;
		}
	}

	public UnitBean saveForExport(final String xml, final HttpSession session) {
		if (logger.isDebugEnabled()) {
			logger.debug("saveForExport() xml=" + xml);
		}

		try {
			Unit unit = UnitXMLReader.read(new ByteArrayInputStream(xml.getBytes()));
			
			if (logger.isDebugEnabled()) {
				if (unit != null) {
					logger.debug("saveForExport() unit = " + unit.getFullName());
				} else {
					logger.debug("saveForExport() unit = null");
				}
			}
			
			session.setAttribute(ExportServlet.SESSION_KEY, unit);
					    
		    return (unit instanceof StructuralUnit) ? new StructuralUnitBean(((StructuralUnit) unit)) : new SmallUnitBean(((SmallUnit) unit));
		} catch (LoadUnitException e) {
			logger.error("Failed to load Unit from xml", e);
			e.printStackTrace();

			return null;
		}		
	}
	
}
