package net.b5gamer.b5wars.ui.fleet;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.JSplitPane;

import net.b5gamer.b5wars.game.Fleet;
import net.b5gamer.swing.JPaintingPanel;

public class FleetViewerInterface extends JPaintingPanel {

	private static final long serialVersionUID = -6240647606593765148L;

	private final Fleet                   fleet;
	private final FleetUnitListPanel      unitListPanel;
	private final ControlSheetDesktopPane desktopPane;
	
	public FleetViewerInterface(final Fleet fleet) {
		super(new BorderLayout());	

        if (fleet == null) {
            throw new IllegalArgumentException("fleet cannot be null");
        }
        
        this.fleet = fleet;
        
		unitListPanel = new FleetUnitListPanel(fleet);
		unitListPanel.setPreferredSize(new Dimension(250, getPreferredSize().height));

		desktopPane = new ControlSheetDesktopPane(fleet.getUnits());
		desktopPane.setBackground(Color.gray);			
			
		JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, unitListPanel, desktopPane);
		add(splitPane, BorderLayout.CENTER);
	}

	public Fleet getFleet() {
		return fleet;
	}

	protected FleetUnitListPanel getUnitListPanel() {
		return unitListPanel;
	}

	protected ControlSheetDesktopPane getDesktopPane() {
		return desktopPane;
	}
	
}
