/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import net.b5gamer.b5wars.ui.B5WarsJFrame;
import net.b5gamer.b5wars.unit.Unit;

public class ControlSheetFrame extends B5WarsJFrame implements ActionListener {

	private static final long serialVersionUID = -3491653468696383321L;

	private Unit                  unit;
	private ControlSheetInterface controlSheetInterface;
	
	public ControlSheetFrame() throws FileNotFoundException {
		this(null, UsageMode.ALL);
	}
	
	public ControlSheetFrame(final Unit unit, final UsageMode mode) {
        super(TITLE + " SCS " + ((mode == UsageMode.ALL || mode == UsageMode.EDIT) ? "Editor" : "Viewer") + ((unit != null) ? " - " + unit.getFullName() : ""), 1024, 768);
//        super(TITLE + " SCS Editor", 1024, 768);
        
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH);

        this.unit = unit;
        this.controlSheetInterface = new ControlSheetInterface(unit, mode);
  
		ControlSheetMenuBar menuBar = new ControlSheetMenuBar(controlSheetInterface);
        menuBar.setOpaque(true);
        menuBar.setPreferredSize(new Dimension(getPreferredSize().width, 20));
        menuBar.setBorderPainted(true);
        setJMenuBar(menuBar);     

        setContentPane(controlSheetInterface);

        pack();
        setVisible(true);
	}

	public void actionPerformed(ActionEvent e) {
//		if (e.getSource() == openMenuItem) {
//        	fileChooser.setFileFilter(new FileExtensionFilter("xml", "XML Files (*.xml)"));
//			int returnVal = fileChooser.showOpenDialog(this);
//
//	        if (returnVal == JFileChooser.APPROVE_OPTION) {
//	        	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//
//	    		try {
//		        	unit = UnitXMLReader.read(new FileInputStream(fileChooser.getSelectedFile()));
//		        	controlSheetInterface.setUnit(unit);
//		        	setTitle(TITLE + " SCS Editor" + ((unit != null) ? " - " + unit.getFullName() : "")); 
//	    		} catch (Exception er) {
//		        	controlSheetInterface.setUnit(null);
//		        	setTitle(TITLE + " SCS Editor" + ""); 
//
//		        	er.printStackTrace();
//	    			showError("Open...", 
//	    					"Failed to open " + fileChooser.getSelectedFile().getName(),
//	    					er.getMessage());
//	    		}
//	    		
//	        	setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//	        }
//		} else if (e.getSource() == saveMenuItem) {
//        	fileChooser.setFileFilter(new FileExtensionFilter("xml", "XML Files (*.xml)"));
//			int returnVal = fileChooser.showSaveDialog(this);
//
//	        if (returnVal == JFileChooser.APPROVE_OPTION) {
//	        	setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
//
//	    		try {
//	        		UnitXMLWriter.saveUnit(unit, new FileOutputStream(fileChooser.getSelectedFile()));
//	    		} catch (Exception er) {
//	    			er.printStackTrace();
//	    			showError("Save As...", 
//	    					"Failed to save " + fileChooser.getSelectedFile().getName(),
//	    					er.getMessage());
//	    		}
//	    		
//	        	setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
//	        }	
//		}
	}
	
	protected void showError(String title, String message, String description) {
		JOptionPane.showMessageDialog(this, 
				message + System.getProperty("line.separator") + System.getProperty("line.separator") + description, 
				title, JOptionPane.ERROR_MESSAGE);	
	}
	
}
