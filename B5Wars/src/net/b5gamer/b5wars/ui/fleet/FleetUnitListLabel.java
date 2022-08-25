package net.b5gamer.b5wars.ui.fleet;

import javax.swing.JLabel;

import net.b5gamer.b5wars.ui.unit.CounterComponent;
import net.b5gamer.b5wars.unit.MobileUnit;
import net.b5gamer.b5wars.unit.Unit;

public class FleetUnitListLabel extends JLabel {

	private static final long serialVersionUID = 5339478981495311848L;

	private final Unit unit;
	
	public FleetUnitListLabel(final Unit unit, final int counterNumber) {
		super(new CounterComponent(unit, counterNumber));
		
        this.unit = unit;
	}

	public Unit getUnit() {
		return unit;
	}
	
	@Override
	public String getText() {
		if (getUnit() != null) {
			StringBuilder text = new StringBuilder("<html>");
			text.append("Initiative: ").append(getUnit().getInitiative()).append("<br>");
			text.append("Speed: ").append((getUnit() instanceof MobileUnit) ? ((MobileUnit) getUnit()).getSpeed() : "N/A").append("<br>");
			text.append("Location: ").append((getUnit().getLocation() != null) ? getUnit().getLocation() : "").append("<br>");
			text.append("Facing: ").append((getUnit().getFacing() != null) ? getUnit().getFacing() : "").append("<br>");
			text.append("Direction: ").append(((getUnit() instanceof MobileUnit) && (((MobileUnit) getUnit()).getDirection() != null)) ? ((MobileUnit) getUnit()).getDirection() : "");
			text.append("</html>");
			
			return text.toString();
		} else {
			return super.getText();
		}
	}
	
}
