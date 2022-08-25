/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Graphics2D;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;

import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JSplitPane;

import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.swing.JPaintingPanel;

public class ControlSheetInterface extends JPaintingPanel implements ComponentListener {

	private static final long serialVersionUID = -6177551692297967132L;

	private JPanel                scsPanel;
	private ShipControlSheetPanel controlSheetPanel;
	private IconListPanel         iconListPanel;
	private JSplitPane            splitPane;
	private JPanel                cornerPanel;
	private JScrollBar            verticalScrollBar;
	private JScrollBar            horizontalScrollBar;
	private ControlSheetStatusBar statusBar;
	
	public ControlSheetInterface(final Unit unit, final UsageMode mode) {
		super(new BorderLayout());	

		scsPanel = new JPanel(new BorderLayout());

		// TODO use factory
		controlSheetPanel = new ShipControlSheetPanel((StructuralUnit) unit, mode);
		scsPanel.add(controlSheetPanel, BorderLayout.CENTER);
		
		if (((mode == UsageMode.EDIT) || (mode == UsageMode.ALL)) && (unit instanceof StructuralUnit)) {
			iconListPanel = new IconListPanel((StructuralUnit) unit, controlSheetPanel);
			iconListPanel.setPreferredSize(new Dimension(250, getPreferredSize().height));
			controlSheetPanel.setUnitIconListPanel(iconListPanel);

			splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, iconListPanel, scsPanel);					
			add(splitPane, BorderLayout.CENTER);			
		} else {
			add(scsPanel, BorderLayout.CENTER);			
		}
		
		verticalScrollBar = new JScrollBar(Adjustable.VERTICAL);
		verticalScrollBar.setVisible(!controlSheetPanel.isFitToScreen());
		verticalScrollBar.addComponentListener(this);
		scsPanel.add(verticalScrollBar, BorderLayout.EAST);
		controlSheetPanel.setVerticalScrollBar(verticalScrollBar);

		JPanel bottomPanel = new JPanel(new BorderLayout());
		scsPanel.add(bottomPanel, BorderLayout.SOUTH);
		
		horizontalScrollBar = new JScrollBar(Adjustable.HORIZONTAL);
		horizontalScrollBar.setVisible(!controlSheetPanel.isFitToScreen());
		horizontalScrollBar.addComponentListener(this);
		bottomPanel.add(horizontalScrollBar, BorderLayout.CENTER);
		controlSheetPanel.setHorizontalScrollBar(horizontalScrollBar);
		
		cornerPanel = new JPanel(null);
		cornerPanel.setPreferredSize(new Dimension(17, 17));
		cornerPanel.setVisible(!controlSheetPanel.isFitToScreen());
		bottomPanel.add(cornerPanel, BorderLayout.EAST);		
		
		if (((mode == UsageMode.EDIT) || (mode == UsageMode.ALL)) && (unit instanceof StructuralUnit)) {
			statusBar = new ControlSheetStatusBar();
			statusBar.setPreferredSize(new Dimension(getPreferredSize().width, 18));
			scsPanel.add(statusBar, BorderLayout.SOUTH);	
			controlSheetPanel.addStatusListener(statusBar);
		}
	}

	public Unit getUnit() {
		return getControlSheetPanel().getUnit();
	}

	public void setUnit(Unit unit) {
		controlSheetPanel.setUnit(unit);
	
		if (((controlSheetPanel.getUsageMode() == UsageMode.EDIT) || 
				(controlSheetPanel.getUsageMode() == UsageMode.ALL)) && 
				(unit instanceof StructuralUnit)) {
			if (iconListPanel == null) {
				iconListPanel = new IconListPanel((StructuralUnit) unit, controlSheetPanel);
				iconListPanel.setPreferredSize(new Dimension(250, getPreferredSize().height));
				controlSheetPanel.setUnitIconListPanel(iconListPanel);
	
				splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, iconListPanel, scsPanel);
				remove(scsPanel);
				add(splitPane, BorderLayout.CENTER);	

				revalidate();
			} else {
				iconListPanel.setUnit((StructuralUnit) unit);
			}
		} else {
			if (iconListPanel != null) {
				add(scsPanel, BorderLayout.CENTER);			
				remove(splitPane);
				
				splitPane = null;
				iconListPanel = null;
				controlSheetPanel.setUnitIconListPanel(null);
				
				revalidate();
			}
		}		
		
		if (((controlSheetPanel.getUsageMode() == UsageMode.EDIT) || 
				(controlSheetPanel.getUsageMode() == UsageMode.ALL)) && 
				(unit instanceof StructuralUnit)) {
			if (statusBar == null) {
				statusBar = new ControlSheetStatusBar();
				statusBar.setPreferredSize(new Dimension(getPreferredSize().width, 18));
				scsPanel.add(statusBar, BorderLayout.SOUTH);	
				controlSheetPanel.addStatusListener(statusBar);
				scsPanel.revalidate();
			}
		} else {
			if (statusBar != null) {
				scsPanel.remove(statusBar);
				controlSheetPanel.removeStatusListener(statusBar);
				statusBar = null;
				scsPanel.revalidate();
			}
		}
	}
	
	public UsageMode getUsageMode() {
		return getControlSheetPanel().getUsageMode();
	}

	public ControlSheetPanel getControlSheetPanel() {
		return controlSheetPanel;
	}

	public void componentHidden(ComponentEvent e) {
		updateVisibility();
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
	}

	public void componentShown(ComponentEvent e) {
		updateVisibility();
	} 

	private final void updateVisibility() {
		cornerPanel.setVisible(verticalScrollBar.isVisible() && horizontalScrollBar.isVisible());
	}

	public final void paintControlSheet(Graphics2D graphics) {
		controlSheetPanel.paintControlSheet(graphics);
	}
	
}
