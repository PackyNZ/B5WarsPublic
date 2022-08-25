/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & © Warner Bros.
 * Java design and implementation © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.render;

import java.util.Iterator;

import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocation;
import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocationChart;

public class HitLocationHTMLRenderer {

	private StringBuilder html;
	
	public HitLocationHTMLRenderer() {
	}
	
	public String renderAsHtml(final StructuralUnit unit) {		
		if (unit == null) {
			throw new IllegalArgumentException("unit cannot be null");
		}
		
		html = new StringBuilder("<html><body>");
		
		if (unit.getHitLocationCharts() != null) {
			for (Iterator<HitLocationChart> iterator = unit.getHitLocationCharts().iterator(); iterator.hasNext();) {
				outputHitLocationChart(iterator.next());
			}
		}

		html.append("</body></html>");
		
		return html.toString();
	}
	
	private void outputHitLocationChart(final HitLocationChart hitLocationChart) {
		if (hitLocationChart != null) {
			html.append("<table class=\"hit-location-chart\" cellpadding=\"0\" cellspacing=\"0\">");
			html.append("<tr class=\"hit-location-chart\"><td class=\"hit-location-chart\">");
			html.append("<font class=\"hit-location-chart\">").append(hitLocationChart.getName()).append("</font>");				
			html.append("</td></tr>");
			html.append("<table class=\"hit-locations\" cellpadding=\"0\" cellspacing=\"0\">");

			for (Iterator<HitLocation> locationIter = hitLocationChart.getHitLocationIterator(); locationIter.hasNext();) {
				HitLocation hitLocation = locationIter.next();
				
				html.append("<tr class=\"hit-location\">");
				html.append("<td class=\"hit-location-range\">");
				html.append("<font class=\"hit-location-range\">").append(hitLocation.getRange()).append(":</font>");
				html.append("</td>");				
				html.append("<td class=\"hit-location-value\">");
				html.append("<font class=\"hit-location-value\">").append(hitLocation.getLocationDescription()).append("</font>");
				html.append("</td>");				
				html.append("</tr>");
			}
			
			html.append("</table>");
			html.append("</table>");
		}
	}
	
}
