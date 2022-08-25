package net.b5gamer.b5wars.ui.fleet;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.util.Iterator;

import javax.swing.JCheckBoxMenuItem;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;

import net.b5gamer.b5wars.game.Fleet;
import net.b5gamer.b5wars.game.InitiativeHandler;
import net.b5gamer.b5wars.ui.B5WarsJFrame;
import net.b5gamer.b5wars.unit.Unit;

public class FleetViewerFrame extends B5WarsJFrame implements ActionListener {

	private static final long serialVersionUID = -6102261536107924072L;

	private Fleet                fleet;
	private FleetViewerInterface fleetViewerInterface;
	private boolean              orderUnitsByInitiative = false;
	
	public FleetViewerFrame(final Fleet fleet) {
		super(TITLE + " Fleet Viewer" + ((fleet != null) ? " - " + fleet.toString() : ""), 1024, 768);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);
        
        this.fleet = fleet;
        fleetViewerInterface = new FleetViewerInterface(fleet);
        
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setPreferredSize(new Dimension(getPreferredSize().width, 20));
        menuBar.setBorderPainted(true);
        setJMenuBar(menuBar);
        
        JMenu actionsMenu = new JMenu("Actions");
        actionsMenu.setMnemonic(KeyEvent.VK_A);
        menuBar.add(actionsMenu);

        JMenu initiativeMenu = new JMenu("Initiative");
        initiativeMenu.setMnemonic(KeyEvent.VK_I);
        actionsMenu.add(initiativeMenu);
        
        JMenuItem rollInitiativeItem = new JMenuItem("Roll Initiative");
        rollInitiativeItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_R, ActionEvent.CTRL_MASK));
        rollInitiativeItem.setActionCommand("Roll Initiative");
        rollInitiativeItem.addActionListener(this);
        initiativeMenu.add(rollInitiativeItem);

        JCheckBoxMenuItem orderItem = new JCheckBoxMenuItem("Order By Initiative", isOrderUnitsByInitiative());
	    orderItem.setActionCommand("Order By Initiative");
	    orderItem.addActionListener(this);
	    initiativeMenu.add(orderItem);

	    JMenuItem endTurnMenuItem = new JMenuItem("End Of Turn");
        endTurnMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        endTurnMenuItem.addActionListener(this);
        actionsMenu.add(endTurnMenuItem);
        
	    JMenu csMenu = new JMenu("Control Sheets");
        csMenu.setMnemonic(KeyEvent.VK_C);
        menuBar.add(csMenu);

        JMenuItem tileItem = new JMenuItem(new TileAction("Tile", fleetViewerInterface.getDesktopPane()));
        tileItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_T, ActionEvent.CTRL_MASK));
        csMenu.add(tileItem);
        
        
        
        setContentPane(fleetViewerInterface);
        
        pack();
        setVisible(true);
	}

	public Fleet getFleet() {
		return fleet;
	}

	public FleetViewerInterface getFleetViewerInterface() {
		return fleetViewerInterface;
	}

	protected boolean isOrderUnitsByInitiative() {
		return orderUnitsByInitiative;
	}

	protected void setOrderUnitsByInitiative(boolean orderUnitsByInitiative) {
		this.orderUnitsByInitiative = orderUnitsByInitiative;

		if (orderUnitsByInitiative) {
			getFleetViewerInterface().getUnitListPanel().orderUnitsByInitiative();
		}
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand() == "Roll Initiative") {
			InitiativeHandler.determineInitiative(getFleet().getUnits());
			setOrderUnitsByInitiative(isOrderUnitsByInitiative());
		} else if (e.getActionCommand() == "Order By Initiative") {
			setOrderUnitsByInitiative(!isOrderUnitsByInitiative());
		} else if (e.getActionCommand() == "End Of Turn") {
			for (Iterator<Unit> iterator = getFleet().getUnits().iterator(); iterator.hasNext();) {
				iterator.next().handleEndOfTurnActions();
			}
			getFleetViewerInterface().getDesktopPane().repaint();
		}
	}

}
