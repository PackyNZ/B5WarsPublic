/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.icon;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class DamageableIconEditorDialog extends JDialog implements WindowListener {

	private static final long serialVersionUID = -2547349272367662265L;

	public static final int ROWS    = 30;
	public static final int COLUMNS = 50;
		
	private DamageableIconEditorPanel editorPanel;
	
	public DamageableIconEditorDialog(Frame owner, String title, DamageableIcon icon, int maxDamageBoxes) {
		this(owner, title, ROWS, COLUMNS, icon, maxDamageBoxes);
	}

	public DamageableIconEditorDialog(Frame owner, String title, int rows, int columns, DamageableIcon icon, int maximumHits) {
		super(owner, title, true);
		setLayout(new BorderLayout());
		setResizable(false);
		setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
		addWindowListener(this);
		
		DamageableIconEditorStatusBar statusBar = new DamageableIconEditorStatusBar(maximumHits);
		statusBar.setPreferredSize(new Dimension(getPreferredSize().width, 18));
		statusBar.hitsValueChanged(new HitsEvent(this, "Hits", icon.getTotalHits()));
		add(statusBar, BorderLayout.SOUTH);	
		
		setEditorPanel(new DamageableIconEditorPanel(rows, columns, icon, maximumHits));
		getEditorPanel().addDamageBoxListener(statusBar);
		add(getEditorPanel(), BorderLayout.CENTER);
		
		Dimension size = new Dimension(
				getEditorPanel().getPreferredSize().width + 16, 
				getEditorPanel().getPreferredSize().height + statusBar.getPreferredSize().height + 36);		
		setPreferredSize(size);
		setSize(size);
		setLocationRelativeTo(null);
	}
	
	protected DamageableIconEditorPanel getEditorPanel() {
		return editorPanel;
	}

	protected void setEditorPanel(DamageableIconEditorPanel editorPanel) {
		this.editorPanel = editorPanel;
	}

	public DamageableIcon getIcon() {
		DamageableIcon icon = getEditorPanel().getIcon();
		
		if (icon != null) {
			icon.orderDamageBoxes();
			icon.reposition();
		}
		
		return icon;
	}
	
	public void windowActivated(WindowEvent e) {
	}

	public void windowClosed(WindowEvent e) {
	}

	public void windowClosing(WindowEvent e) {
		if (getEditorPanel().getIcon().getTotalHits() != getEditorPanel().getMaximumHits()) {
			int option = JOptionPane.showOptionDialog(this, 
					"Incorrect number of damage boxes, save anyway?", "Icon Editor", 
					JOptionPane.OK_CANCEL_OPTION, JOptionPane.WARNING_MESSAGE, null, null, null);
			
			if (option == JOptionPane.OK_OPTION) {
				dispose();
			} else {
				getEditorPanel().repaint();
			}
		} else {
			dispose();
		}
	}

	public void windowDeactivated(WindowEvent e) {
	}

	public void windowDeiconified(WindowEvent e) {
	}

	public void windowIconified(WindowEvent e) {
	}

	public void windowOpened(WindowEvent e) {
	}
	
}
