/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & © Warner Bros.
 * Java design and implementation © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.render;

import java.util.Iterator;

import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.section.SectionCollection;
import net.b5gamer.b5wars.unit.structural.system.System;

public class SectionHTMLRenderer {

	private StringBuilder html;
	
	public SectionHTMLRenderer() {
	}
	
	public String renderAsHtml(final StructuralUnit unit) {		
		if (unit == null) {
			throw new IllegalArgumentException("unit cannot be null");
		}
		
		html = new StringBuilder("<html><body>");
		outputSections(unit);
		html.append("</body></html>");
	
		return html.toString();
	}

	public void outputSections(final SectionCollection sections) {
		for (Iterator<Section> iterator = sections.getSectionIterator(); iterator.hasNext();) {
			Section section = iterator.next();
			
			html.append("<table class=\"section\" cellpadding=\"0\" cellspacing=\"0\">");
			
			outputSection(section);
			
			html.append("</table>");
		}
	}
	
	private void outputSection(final Section section) {
		startSection(section);
		outputSystems(section.getSystemIterator());
		
		if (section instanceof SectionCollection) {
			outputSections((SectionCollection) section);
		}
	}
	
	private void startSection(final Section section) {
		html.append("<tr class=\"section\"><td class=\"section\">");
		html.append("<font class=\"");
		
		if (section.isDestroyed()) {
			html.append("section-destroyed");
		} else {
			html.append("section");				
		}
		
		html.append("\">").append(section.getName()).append("</font>");				
		html.append("</td></tr>");
	}

	private void outputSystems(final Iterator<System> systemIterator) {
		for (; systemIterator.hasNext();) {
			System system = systemIterator.next();

			html.append("<tr class=\"system\"><td class=\"system\">");
			html.append("<font class=\"");
			
			if (system.isDestroyed()) {
				html.append("system-destroyed");
			} else if (system.getDamageSuffered() > 0) {
				html.append("system-damaged");
			} else {
				html.append("system");				
			}
			
			html.append("\">").append(system.getDescription()).append("</font>");				
			html.append("</td></tr>");
		}			
	}
	
}
