/**
 * Based on the Babylon 5 Wars game system by Agents of Gaming TM & � Warner Bros.
 * Java design and implementation � Alex Packwood   
 */
package net.b5gamer.b5wars.ui.unitviewer;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.KeyStroke;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

import net.b5gamer.b5wars.io.unit.UnitXMLReader;
import net.b5gamer.b5wars.ui.render.HitLocationHTMLRenderer;
import net.b5gamer.b5wars.ui.render.SectionHTMLRenderer;
import net.b5gamer.b5wars.ui.render.StatHTMLRenderer;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.small.FighterFlight;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.hitlocation.HitLocation;
import net.b5gamer.b5wars.unit.structural.hitlocation.SectionHitLocation;
import net.b5gamer.b5wars.unit.structural.hitlocation.SystemHitLocation;
import net.b5gamer.b5wars.unit.structural.hitlocation.WeaponHitLocation;
import net.b5gamer.b5wars.unit.structural.section.Section;
import net.b5gamer.b5wars.unit.structural.section.StructuralSection;
import net.b5gamer.b5wars.unit.structural.system.DefensiveSystem;
import net.b5gamer.b5wars.unit.structural.system.System;
import net.b5gamer.b5wars.unit.structural.system.Weapon;
import net.b5gamer.dice.Dice;
import net.b5gamer.ui.selection.InteractiveSelectionHandler;
import net.b5gamer.ui.selection.SelectionHandler;

/**
 * !!--- work in progress ---!!
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class UnitViewer implements ActionListener {
	
	private static final Color BACKGROUND_COLOR = new Color(Integer.parseInt("000020", 16));
	private static final Color FOREGROUND_COLOR = new Color(Integer.parseInt("CCCCCC", 16));
	private static final Color BORDER_COLOR     = new Color(Integer.parseInt("404080", 16));
	private static final Font  FONT             = new Font("Verdana", Font.PLAIN, 11);	
	
	private static SelectionHandler sectionSelectionHandler;
	private static SelectionHandler systemSelectionHandler;
	
	private static JMenuItem importMenuItem;
	private static JMenuItem exportMenuItem;
	private static JMenuItem exitMenuItem;
	
	private static JComboBox<Unit> unitList;
	private static JTextField  numberOfVolleys;
    private static JTextField  damagePerVolley;
    private static JTextField  angleOfAttack;
    private static JButton     fireButton;
    private static JButton     endTurnButton;
	private static JComboBox<String> displayTypes;
	private static JComboBox<String> displayTypesList;
	private static JCheckBox   drawHex;
	private static JCheckBox   drawShip;
	private static UnitCanvas  shipIcon;
	private static JTabbedPane tabs;
	private static JEditorPane stats;
	private static JEditorPane hitLocationCharts;
	private static JEditorPane sections;
	private static JTextArea   log = new JTextArea("");
	
	private static Unit selectedUnit;
	
	public UnitViewer() {
	}
	
	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		
		if (source == exitMenuItem) {
			java.lang.System.exit(0);
		} else if (source == unitList) {
			selectedUnit = (Unit) unitList.getSelectedItem();
	    	shipIcon.setUnit(selectedUnit);

			if (selectedUnit instanceof StructuralUnit) {
		    	populateDisplayItemList((StructuralUnit) selectedUnit, shipIcon.getDisplayType());
				Object item = displayTypesList.getSelectedItem();
				displayTypes.setVisible(true);
				displayTypesList.setVisible(true);
				
				shipIcon.setDisplayItem(((item == null) || (item.toString().equals("<All>"))) ? null : item.toString());
			} else {
				displayTypes.setVisible(false);
				displayTypesList.setVisible(false);
			}
	    	
			stats.setText(new StatHTMLRenderer().renderAsHtml(selectedUnit));
			if (selectedUnit instanceof StructuralUnit) {
				hitLocationCharts.setText(new HitLocationHTMLRenderer().renderAsHtml((StructuralUnit) selectedUnit)); 	
				sections.setText(new SectionHTMLRenderer().renderAsHtml((StructuralUnit) selectedUnit)); 	
			} else if (selectedUnit instanceof FighterFlight) {
				// TODO
				hitLocationCharts.setText("");
				sections.setText("");
			} else {
				sections.setText("");
			}
			log.setText("");
//	    	tabs.setSelectedIndex(0);
		} else if (source == displayTypes) {
			Object type = displayTypes.getSelectedItem();
			shipIcon.setDisplayType((type == null) ? null : type.toString());				
			populateDisplayItemList((StructuralUnit) selectedUnit, shipIcon.getDisplayType());
			Object item = displayTypesList.getSelectedItem();
			shipIcon.setDisplayItem(((item == null) || (item.toString().equals("<All>"))) ? null : item.toString());
		} else if (source == displayTypesList) {
			Object item = displayTypesList.getSelectedItem();
			shipIcon.setDisplayItem(((item == null) || (item.toString().equals("<All>"))) ? null : item.toString());
		} else if (source == drawHex) {
			shipIcon.setDrawHex(drawHex.isSelected());
		} else if (source == drawShip) {
			shipIcon.setDrawShipIcon(drawShip.isSelected());		
		} else if (source == fireButton) {
	    	// TODO
    		int volleyCount  = Integer.parseInt(numberOfVolleys.getText());
    		int damageAmount = Integer.parseInt(damagePerVolley.getText());
	    	int angle        = Integer.parseInt(angleOfAttack.getText());

	    	shipIcon.setAngle(angle);
	    	
//	    	if (selectedUnit.hasTrait(Trait.SAUCER)) {
//	    		shipIcon.setArc(null);
//	    	} else {
	    		List<Section> sectionsWithinArc = ((StructuralUnit) selectedUnit).getSectionsWithinArc(angle);
	    		Section section = (Section) sectionSelectionHandler.select(sectionsWithinArc);
				
	    		shipIcon.setArc(section.getArc());
	    		shipIcon.repaint();
	    		
//		    	tabs.setSelectedIndex(1);
				for (int index = 0; index < volleyCount; index++) {
					outputLine(log, "Volley " + (index + 1) + " of " + damageAmount + " damage...");
					hitLocation((StructuralUnit) selectedUnit, section, damageAmount);
					
					if (selectedUnit.isDestroyed()) {
						outputLine(log, "");
						break;
					}
					
					outputLine(log, "");
				}	    	
//	    	}

	    	sections.setText(new SectionHTMLRenderer().renderAsHtml((StructuralUnit) selectedUnit)); 		    	
		} else if (source == endTurnButton) {
			shipIcon.setAngle(-1);
			selectedUnit.handleEndOfTurnActions();
	    	sections.setText(new SectionHTMLRenderer().renderAsHtml((StructuralUnit) selectedUnit)); 		    	
		}

		shipIcon.repaint();
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

    private static void createAndShowGUI() {
//		Logger.setAppender(new WriterAppender(Logger.DEFAULT_LAYOUT, new JTextAreaWriter(log)));
		
		UnitViewer listener = new UnitViewer();
		
        JFrame frame = new JFrame("B5Wars Unit Viewer v0.61");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
    	sectionSelectionHandler = new InteractiveSelectionHandler(frame, "Select Section", "Select a target Section");
    	systemSelectionHandler  = new InteractiveSelectionHandler(frame, "Select System", "Select a target System");
        
        JMenuBar menuBar = new JMenuBar();
        menuBar.setOpaque(true);
        menuBar.setPreferredSize(new Dimension(600, 20));
        menuBar.setBackground(BACKGROUND_COLOR);
        menuBar.setForeground(FOREGROUND_COLOR);
        menuBar.setBorderPainted(false);
        JMenu menu = new JMenu("File");
        menu.setMnemonic(KeyEvent.VK_F);
        menu.setBackground(BACKGROUND_COLOR);
        menu.setForeground(FOREGROUND_COLOR);
        menuBar.add(menu);

        importMenuItem = new JMenuItem("Import...");
        importMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, ActionEvent.CTRL_MASK));
        importMenuItem.addActionListener(listener);
		menu.add(importMenuItem);

		exportMenuItem = new JMenuItem("Export...");
		exportMenuItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		exportMenuItem.addActionListener(listener);
		menu.add(exportMenuItem);

		menu.addSeparator();
        exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.addActionListener(listener);
        menu.add(exitMenuItem);
        
        JPanel mainPanel = new JPanel(new BorderLayout()); 
        mainPanel.setBorder(new EmptyBorder(2, 2, 2, 2));
        mainPanel.setBackground(BACKGROUND_COLOR);
        mainPanel.setForeground(FOREGROUND_COLOR);
        mainPanel.setFont(FONT);
        
        JPanel leftPanel = new JPanel(new BorderLayout()); 
        leftPanel.setOpaque(false);
        JPanel settingsPanel1 = new JPanel(); 
        settingsPanel1.setOpaque(false);
        JLabel label1 = new JLabel("Target Unit");
        label1.setForeground(FOREGROUND_COLOR);
        settingsPanel1.add(label1);
        unitList = new JComboBox<Unit>();
        unitList.setSelectedIndex(-1);
        unitList.addActionListener(listener);
        settingsPanel1.add(unitList);        
        leftPanel.add(settingsPanel1, BorderLayout.NORTH);
        
        JPanel settingsPanel = new JPanel(new BorderLayout()); 
        settingsPanel.setOpaque(false);
        JPanel settingsPanel2 = new JPanel(); 
        settingsPanel2.setOpaque(false);
        JLabel label2 = new JLabel("Resolve");
        label2.setForeground(FOREGROUND_COLOR);
        settingsPanel2.add(label2);
        numberOfVolleys = new JTextField("1");
        numberOfVolleys.setPreferredSize(new Dimension(30, 20));
        settingsPanel2.add(numberOfVolleys);        
        JLabel label3 = new JLabel("volley(s) of");
        label3.setForeground(FOREGROUND_COLOR);
        settingsPanel2.add(label3);
        damagePerVolley = new JTextField("10");
        damagePerVolley.setPreferredSize(new Dimension(30, 20));
        settingsPanel2.add(damagePerVolley);        
        JLabel label4 = new JLabel("damage from a");
        label4.setForeground(FOREGROUND_COLOR);
        settingsPanel2.add(label4);
        angleOfAttack = new JTextField("0");
        angleOfAttack.setPreferredSize(new Dimension(30, 20));
        settingsPanel2.add(angleOfAttack);               
        JLabel label5 = new JLabel("degree angle");
        label5.setForeground(FOREGROUND_COLOR);
        settingsPanel2.add(label5);
        settingsPanel.add(settingsPanel2, BorderLayout.NORTH);
        
        JPanel settingsPanel3 = new JPanel();   
        settingsPanel3.setOpaque(false);
        fireButton = new JButton("Fire");
        fireButton.addActionListener(listener);
        settingsPanel3.add(fireButton);
        endTurnButton = new JButton("End Turn");
        endTurnButton.addActionListener(listener);
        settingsPanel3.add(endTurnButton);
        settingsPanel.add(settingsPanel3, BorderLayout.CENTER);
        leftPanel.add(settingsPanel, BorderLayout.CENTER);

        JPanel displayPanel = new JPanel(new BorderLayout()); 
        displayPanel.setOpaque(false);
        JPanel displayTypePanel = new JPanel(); 
        displayTypePanel.setOpaque(false);
//        JLabel label6 = new JLabel("Display:");
//        label6.setForeground(FOREGROUND_COLOR);
//        displayTypePanel.add(label6);
        displayTypes = new JComboBox<String>();
        displayTypes.addItem(UnitCanvas.DISPLAY_TYPE_SECTIONS);
        displayTypes.addItem(UnitCanvas.DISPLAY_TYPE_WEAPONS);
        displayTypes.addItem(UnitCanvas.DISPLAY_TYPE_DEFENSES);        
        displayTypes.setSelectedIndex(0);
        displayTypes.addActionListener(listener);
        displayTypePanel.add(displayTypes);
        displayTypesList = new JComboBox<String>();
//        sectionList.setSelectedIndex(-1);
        displayTypesList.addActionListener(listener);
        displayTypePanel.add(displayTypesList);
    	drawHex = new JCheckBox("Hexes");
    	drawHex.setOpaque(false);
    	drawHex.setForeground(FOREGROUND_COLOR);
    	drawHex.setSelected(true);
    	drawHex.addActionListener(listener);    	
    	displayTypePanel.add(drawHex);
        drawShip = new JCheckBox("Ship");
        drawShip.setOpaque(false);
        drawShip.setForeground(FOREGROUND_COLOR);
        drawShip.setSelected(true);
        drawShip.addActionListener(listener);    	
        displayTypePanel.add(drawShip);
        displayPanel.add(displayTypePanel,  BorderLayout.NORTH);
        JPanel shipPanel = new JPanel(new BorderLayout()); 
        shipPanel.setOpaque(false);
        shipPanel.setBorder(BorderFactory.createLineBorder(BORDER_COLOR, 2));
        shipIcon = new UnitCanvas();
        Object type = displayTypes.getSelectedItem();
		shipIcon.setDisplayType((type == null) ? null : type.toString());	
        shipPanel.add(shipIcon,  BorderLayout.CENTER);        
        displayPanel.add(shipPanel, BorderLayout.SOUTH);
        leftPanel.add(displayPanel, BorderLayout.SOUTH);
        mainPanel.add(leftPanel, BorderLayout.WEST);
        
        
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setOpaque(false);
        rightPanel.setPreferredSize(new Dimension(450, 650));
        tabs = new JTabbedPane();
    	File directory = new File(".");
        try {
        	stats = new JEditorPane("file:///" + directory.getCanonicalPath() + File.separator + "unit.htm");
		} catch (IOException e) {
			java.lang.System.out.println(e);
			e.printStackTrace();
		}
        stats.setEditable(false);
        stats.setBackground(BACKGROUND_COLOR);
        stats.setForeground(FOREGROUND_COLOR);
        JScrollPane scrollPane1 = new JScrollPane(stats);   
        tabs.addTab("Stats", scrollPane1);
        try {
			hitLocationCharts = new JEditorPane("file:///" + directory.getCanonicalPath() + File.separator + "unit.htm");
		} catch (IOException e) {
			java.lang.System.out.println(e);
			e.printStackTrace();
		}
		hitLocationCharts.setEditable(false);
		hitLocationCharts.setBackground(BACKGROUND_COLOR);
		hitLocationCharts.setForeground(FOREGROUND_COLOR);
        JScrollPane scrollPane2 = new JScrollPane(hitLocationCharts);   
        tabs.addTab("Hit Locations", scrollPane2);
        try {
			sections = new JEditorPane("file:///" + directory.getCanonicalPath() + File.separator + "unit.htm");
		} catch (IOException e) {
			java.lang.System.out.println(e);
			e.printStackTrace();
		}
        sections.setEditable(false);
        sections.setBackground(BACKGROUND_COLOR);
        sections.setForeground(FOREGROUND_COLOR);
        JScrollPane scrollPane3 = new JScrollPane(sections);   
        tabs.addTab("Sections", scrollPane3);
        log.setEditable(false);
        log.setBackground(BACKGROUND_COLOR);
        log.setForeground(FOREGROUND_COLOR);
        log.setFont(FONT);
        JScrollPane scrollPane4 = new JScrollPane(log);   
        tabs.addTab("Log", scrollPane4);
        rightPanel.add(tabs, BorderLayout.CENTER);
        mainPanel.add(rightPanel, BorderLayout.EAST);
        
        frame.setJMenuBar(menuBar);
        frame.getRootPane().setDefaultButton(fireButton);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
        
        List<Unit> importedUnits = loadUnits(UnitXMLReader.DEFAULT_DIRECTORY);
        for (Iterator<Unit> iterator = importedUnits.iterator(); iterator.hasNext();) {
        	unitList.addItem(iterator.next());
        }
    }
	
    private final void populateDisplayItemList(final StructuralUnit unit, final String displayType) {
		displayTypesList.removeAllItems();
		displayTypesList.addItem("<All>");

		if (UnitCanvas.DISPLAY_TYPE_SECTIONS.equalsIgnoreCase(displayType)) {
	        for (Iterator<Section> iterator = unit.getSectionIterator(); iterator.hasNext();) {
	        	Section section = iterator.next();
				
				if ((section.getArc() != null) || (Section.PRIMARY.equalsIgnoreCase(section.getName()))) {
		        	displayTypesList.addItem(section.getName());
				}
	        }
		} else if (UnitCanvas.DISPLAY_TYPE_WEAPONS.equalsIgnoreCase(displayType)) {
			for (Iterator<System> iterator = unit.getSystemsOfClass(Weapon.class, true).iterator(); iterator.hasNext();) {
	        	displayTypesList.addItem(iterator.next().getFullName());				
			}
		} else if (UnitCanvas.DISPLAY_TYPE_DEFENSES.equalsIgnoreCase(displayType)) {
			for (Iterator<System> iterator = unit.getSystemsOfClass(DefensiveSystem.class, true).iterator(); iterator.hasNext();) {
				System system = iterator.next();
				
				if (((DefensiveSystem) system).getDefensiveBonus() > 0) {
					displayTypesList.addItem(system.getFullName());	
				}
			}			
		}
		
		displayTypesList.setSelectedIndex(0);
    }
    
	private static final void outputLine(JTextArea output, final String text) {
		output.append(text + "\n");
	}

	public static final void hitLocation(final StructuralUnit targetUnit, final Section targetSection, int damage) {
		int roll = Dice.d20.roll();
		HitLocation hitLocation = targetSection.getHitLocationChart().determineLocationHit(roll);
		outputLine(log, "Location " + roll + " rolled for " + targetSection.getName() + ", " + hitLocation.toString() + " hit");

		if (hitLocation instanceof SystemHitLocation) {
			List<System> targetSystems = targetSection.getTargetSystemsOfType(((SystemHitLocation) hitLocation).getSystemType());
			// damage system
			if ((targetSystems != null && targetSystems.size() > 0)) {
				damage = applyDamage((System) systemSelectionHandler.select(targetSystems), damage);
			} else {
				outputLine(log, "No " + hitLocation.getLocationDescription() + " remains!");
			}
			
			// damage sections structure
			if ((damage > 0) && (targetSection instanceof StructuralSection)) {
				System sectionStructure = ((StructuralSection) targetSection).getStructure();
				if (sectionStructure.isValidTarget()) {
					outputLine(log, "Applying overkill to " + targetSection.getName() + " structure");
					damage = applyDamage(sectionStructure, damage);
	
					if (sectionStructure.isDestroyed()) {
						outputLine(log, targetSection.getName() + " destroyed!");
					}
				}
			}
			
			// damage primary structure
			if (damage > 0) {
				// TODO check primary hit or overkill
				System primaryStructure = ((StructuralSection) targetUnit.getSection(Section.PRIMARY)).getStructure();
				outputLine(log, "Applying overkill to " + Section.PRIMARY + " structure");
				applyDamage(primaryStructure, damage);
				
				if (primaryStructure.isDestroyed()) {
					outputLine(log, targetUnit.getGivenName() + " destroyed!");
				}
			}				
		} else if (hitLocation instanceof WeaponHitLocation) {
			List<Weapon> targetSystems = targetSection.getTargetWeaponsOfType(((WeaponHitLocation) hitLocation).getWeaponType());
			// damage weapon system
			if ((targetSystems != null && targetSystems.size() > 0)) {
				damage = applyDamage((System) systemSelectionHandler.select(targetSystems), damage);
			} else {
				outputLine(log, "No " + hitLocation.getLocationDescription() + " remains!");
			}
			
			// damage sections structure
			if ((damage > 0) && (targetSection instanceof StructuralSection)) {
				System sectionStructure = ((StructuralSection) targetSection).getStructure();
				if (sectionStructure.isValidTarget()) {
					outputLine(log, "Applying overkill to " + targetSection.getName() + " structure");
					damage = applyDamage(sectionStructure, damage);
	
					if (sectionStructure.isDestroyed()) {
						outputLine(log, targetSection.getName() + " destroyed!");
					}
				}
			}
			
			// damage primary structure
			if (damage > 0) {
				// TODO check primary hit or overkill
				System primaryStructure = ((StructuralSection) targetUnit.getSection(Section.PRIMARY)).getStructure();
				outputLine(log, "Applying overkill to " + Section.PRIMARY + " structure");
				applyDamage(primaryStructure, damage);
				
				if (primaryStructure.isDestroyed()) {
					outputLine(log, targetUnit.getGivenName() + " destroyed!");
				}
			}				
		} else if (hitLocation instanceof SectionHitLocation) {
			Section section = targetUnit.getSection(((SectionHitLocation) hitLocation).getSectionName());
			if (section != null) {
				hitLocation(targetUnit, section, damage);				 
			} else {
				outputLine(log, "Unknown section");
			}			
		} else {
			outputLine(log, "Unknown hit location class");
		}		
	}
	
	public static final int applyDamage(final System system, final int damage) {
		int result = 0;
		
        if (system == null) {
            throw new IllegalArgumentException("System cannot be null");
        } 
        
        if (system.isDestroyed()) {
        	result = damage;
        } else {
        	int damageTaken = damage - system.getArmor();
        	damageTaken = (damageTaken < 0) ? 0 : damageTaken;
			result = system.applyDamage(damageTaken);

        	outputLine(log, system.getFullName() + " takes " + damageTaken + " damage (Armor " + system.getArmor() + "/" + system.getBaseArmor() + ", Damage " + system.getDamageBoxes() + "/" + system.getBaseDamageBoxes() + ")" + (system.isValidTarget() ? ", " : " and is destroyed! ") + result + " overkill damage");
        }		
        
        return result;
	}
	
	public static final List<Unit> loadUnits(final String path) {
//		List<Unit> result = new ArrayList<Unit>(0);
//		
//		File directory = new File(path);
//	    File[] xmlFiles = directory.listFiles(new FilenameExtensionFilter("xml"));
//	    
//	    for (int i = 0; i < xmlFiles.length; i++) {
//	        try {
//	    		result.addAll(UnitXMLReader.read(xmlFiles[i]));
//			} catch (FileNotFoundException e) {
//				Logger.error("Failed to load file " + xmlFiles[i].getName(), e);
//			}
//	    }
//	    
//	    return result;
	    
        try {
        	return UnitXMLReader.read();
        } catch (Exception e) {
        	java.lang.System.out.println("Failed to load units");
        	e.printStackTrace();
        	return null;
        }
	}
	
//	public static final List<Unit> loadUnits(final String path) {
//		List<Unit> result = new ArrayList<Unit>(0);
//		
//		File directory = new File(path);
//	    File[] xmlFiles = directory.listFiles(new FilenameExtensionFilter("ufo"));
//
//	    for (int i = 0; i < xmlFiles.length; i++) {
//	        try {
//	        	Unit unit = (Unit) new ObjectInputStream(new FileInputStream(xmlFiles[i])).readObject();
//	        	// TODO load counter image
//	    		result.add(unit);
//			} catch (Exception e) {
//				Logger.error("Failed to load file " + xmlFiles[i].getName(), e);
//			}
//	    }
//	    
//	    return result;
//	}
	
}
