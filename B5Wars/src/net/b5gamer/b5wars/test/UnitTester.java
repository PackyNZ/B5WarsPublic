package net.b5gamer.b5wars.test;

import java.util.Iterator;

import net.b5gamer.b5wars.io.DataManager;
import net.b5gamer.b5wars.ui.logging.Logger;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.UnitID;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.section.MultiSection;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.section.SectionCollection;
import net.b5gamer.b5wars.unit.structural.system.System;

public class UnitTester {
	
	public static void main(String[] args) {
		if (args.length < 1) {
			Logger.info("Usage: UnitTester <unit name>");
			java.lang.System.exit(0);
		}

		Unit unit = null;
		try {
//			Object object = Class.forName(args[0]).getDeclaredConstructor().newInstance();
//			if (object instanceof Unit) {
//				unit = (Unit) object;
//			} else {
//	            throw new IllegalArgumentException("Unit must be a valid " + Unit.class.getName());
//			}
			
			unit = DataManager.getUnitDao().load(new UnitID(args[0], null, 0));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (unit instanceof StructuralUnit) {
			outputUnit((StructuralUnit) unit);
		} else {
            throw new IllegalArgumentException("Unit is of an unsupported type");
		}
	}

	public static final void outputUnit(final StructuralUnit unit) {
		Logger.info(unit.toString());
		
		Logger.info("");
		
		outputSections(unit);
	}

	public static final void outputSections(final SectionCollection sections) {
		for (Iterator<Section> iterator = sections.getSectionIterator(); iterator.hasNext();) {
			outputSection(iterator.next());
		}
	}

	public static final void outputSection(final Section section) {
		if (section instanceof MultiSection) {
//			MultiSection multiSection = (MultiSection) section;
//			
//			if (DisplayStyle.PARENT_ONLY == multiSection.getDisplayStyle()) {
//				Logger.info(section.getName() + ":");
//				outputSystems(section.getSystemIterator());
//				
//				for (Iterator<Section> iterator = multiSection.getSectionIterator(); iterator.hasNext();) {
//					Section subSection = iterator.next();
//					
//					if (subSection instanceof SectionCollection) {
//						outputSection(subSection);						
//					} else {
//						outputSystems(subSection.getSystemIterator());						
//					}
//				}
//			} else if (DisplayStyle.ALL == multiSection.getDisplayStyle()) {
				Logger.info(section.getName() + ":");
				outputSystems(section.getSystemIterator());
				outputSections((SectionCollection) section);
//			} else if (DisplayStyle.SUBSECTIONS_ONLY == multiSection.getDisplayStyle()) {
//				boolean first = true;
//				for (Iterator<Section> iterator = multiSection.getSectionIterator(); iterator.hasNext();) {
//					Section subSection = iterator.next();
//										
//					if (subSection instanceof SectionCollection) {
//						if (first) {
//							Logger.info(multiSection.getName() + ":");
//							outputSystems(multiSection.getSystemIterator());
//							first = false;
//						}
//
//						outputSection(subSection);						
//					} else {
//						Logger.info(subSection.getName() + ":");
//
//						if (first) {
//							outputSystems(multiSection.getSystemIterator());
//							first = false;
//						}
//
//						outputSystems(subSection.getSystemIterator());						
//					}
//				}				
//			} else {
//				throw new IllegalArgumentException("Unknown " + DisplayStyle.class.getName() + " " + multiSection.getDisplayStyle());
//			}			
		} else if (section instanceof SectionCollection) {
			Logger.info(section.getName() + ":");
			outputSystems(section.getSystemIterator());
			outputSections((SectionCollection) section);
		} else {
			Logger.info(section.getName() + ":");
			outputSystems(section.getSystemIterator());
		}
	}
	
	public static final void outputSystems(final Iterator<? extends System> systemIterator) {
		for (; systemIterator.hasNext();) {
			System system = systemIterator.next();

			Logger.info("    " + system.getDescription());
		}			
	}
		
}
