/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.b5gamer.b5wars.unit.Availability;
import net.b5gamer.b5wars.unit.MobileUnit;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.b5wars.unit.small.FighterFlight;
import net.b5gamer.b5wars.unit.small.SmallUnit;
import net.b5gamer.b5wars.unit.structural.StructuralUnit;
import net.b5gamer.b5wars.unit.structural.system.DefensiveSystem;
import net.b5gamer.b5wars.unit.structural.system.Engine;
import net.b5gamer.b5wars.unit.structural.system.HyperspaceDrive;
import net.b5gamer.b5wars.unit.structural.system.PowerProvidingSystem;
import net.b5gamer.b5wars.unit.structural.system.PowerRequiringSystem;
import net.b5gamer.b5wars.unit.structural.system.System;

public class DatacardComponent extends ControlSheetComponent {

	private static final long serialVersionUID = -6747682891094457225L;

	public  static final int    DEFAULT_X            = 214;
	public  static final int    DEFAULT_Y            = 23;	
	public  static final int    DEFAULT_WIDTH        = 284;	
	public  static final int    DEFAULT_HEIGHT       = 110;	
	public  static final int    DEFAULT_HEIGHT_SPEED = 134;	
	private static final Stroke STROKE               = new BasicStroke(1.0f);
	private static final Stroke NAME_STROKE          = new BasicStroke(0.5f);
	private static final Font   VARIANT_FONT         = (new Font("Arial", Font.PLAIN, 11)).deriveFont(AffineTransform.getScaleInstance(0.85, 1.0));
	private static final Font   VERSION_FONT         = (new Font("Arial", Font.PLAIN, 7)).deriveFont(AffineTransform.getScaleInstance(0.85, 1.0));
	private static final Font   GIVENNAME_FONT       = (new Font("Arial", Font.PLAIN, 11)).deriveFont(AffineTransform.getScaleInstance(0.6, 1.0));
	private static final int    NAME_HEIGHT          = 30;
	private static final Font   NAME_FONT            = new Font("Arial", Font.PLAIN, 30);
	private static final Font   MODEL_FONT           = new Font("Arial", Font.PLAIN, 18);
	private static final AffineTransform NAME_TRANSFORM = AffineTransform.getScaleInstance(0.7, 1.0);
	private static final AffineTransform MODEL_TRANSFORM = AffineTransform.getScaleInstance(0.7, 1.0);
	private static final AffineTransform PARENTHESES_TRANSFORM = AffineTransform.getScaleInstance(1.0, 1.0);
	private static final Font   HEADING_FONT         = (new Font("Arial", Font.PLAIN, 13)).deriveFont(AffineTransform.getScaleInstance(0.8, 1.0));
	private static final Font   VALUE_FONT           = (new Font("Arial", Font.PLAIN, 9)).deriveFont(AffineTransform.getScaleInstance(0.9, 1.0));
	private static final Font   TURN_CHART_FONT      = new Font("Arial", Font.PLAIN, 8);
	
	protected Unit              unit;
	private   GlyphVector       variant           = null;
	private   GlyphVector       version           = null;
	private   GlyphVector       givenName         = null;
	private   GlyphVector       counter           = null;
	private   GlyphVector       unitName          = null;
	private   double            unitNameBaseline  = 0.0;
	private   GlyphVector       model             = null;
	private   GlyphVector       leftParentheses   = null;
	private   GlyphVector       rightParentheses  = null;
	private   GlyphVector       specs             = null;
	private   List<GlyphVector> specsValues       = null;
	private   GlyphVector       maneuvering       = null;
	private   List<GlyphVector> maneuveringValues = null;
	private   GlyphVector       combatStats       = null;
	private   List<GlyphVector> combatStatsValues = null;
	private   GlyphVector       extraPower        = null;
	private   GlyphVector       powerShortage     = null;	
	
	public DatacardComponent(final Unit unit) {
		setUnit(unit);
		setLocation(DEFAULT_X, DEFAULT_Y);
		
		Dimension size = new Dimension(DEFAULT_WIDTH, 
				((getUnit() instanceof StructuralUnit) && (getUnit() instanceof MobileUnit)) ? DEFAULT_HEIGHT_SPEED : DEFAULT_HEIGHT);
		setPreferredSize(size);
		setSize(size);
	}

	protected Unit getUnit() {
		return unit;
	}

	protected void setUnit(Unit unit) {
        if (unit == null) {
            throw new IllegalArgumentException("unit cannot be null");
        }

        this.unit = unit;
        
        setUnitName(null);
	}

	private GlyphVector getVariant() {
		return variant;
	}

	private void setVariant(GlyphVector variant) {
		this.variant = variant;
	}
	
	private GlyphVector getVersion() {
		return version;
	}

	private void setVersion(GlyphVector version) {
		this.version = version;
	}
	
	private GlyphVector getGivenName() {
		return givenName;
	}

	private void setGivenName(GlyphVector givenName) {
		this.givenName = givenName;
	}

	private GlyphVector getCounter() {
		return counter;
	}

	private void setCounter(GlyphVector counter) {
		this.counter = counter;
	}
	
	private GlyphVector getUnitName() {
		return unitName;
	}

	private void setUnitName(GlyphVector unitName) {
		this.unitName = unitName;
	}

	private double getUnitNameBaseline() {
		return unitNameBaseline;
	}

	private void setUnitNameBaseline(double unitNameBaseline) {
		this.unitNameBaseline = unitNameBaseline;
	}

	private GlyphVector getModel() {
		return model;
	}

	private void setModel(GlyphVector model) {
		this.model = model;
	}

	private GlyphVector getLeftParentheses() {
		return leftParentheses;
	}

	private void setLeftParentheses(GlyphVector leftParentheses) {
		this.leftParentheses = leftParentheses;
	}

	private GlyphVector getRightParentheses() {
		return rightParentheses;
	}

	private void setRightParentheses(GlyphVector rightParentheses) {
		this.rightParentheses = rightParentheses;
	}

	private GlyphVector getSpecs() {
		return specs;
	}

	private void setSpecs(GlyphVector specs) {
		this.specs = specs;
	}

	private List<GlyphVector> getSpecsValues() {
		return specsValues;
	}

	private void setSpecsValues(List<GlyphVector> specsValues) {
		this.specsValues = specsValues;
	}

	private GlyphVector getManeuvering() {
		return maneuvering;
	}

	private void setManeuvering(GlyphVector maneuvering) {
		this.maneuvering = maneuvering;
	}

	private List<GlyphVector> getManeuveringValues() {
		return maneuveringValues;
	}

	private void setManeuveringValues(List<GlyphVector> maneuveringValues) {
		this.maneuveringValues = maneuveringValues;
	}

	private GlyphVector getCombatStats() {
		return combatStats;
	}

	private void setCombatStats(GlyphVector combatStats) {
		this.combatStats = combatStats;
	}

	private List<GlyphVector> getCombatStatsValues() {
		return combatStatsValues;
	}

	private void setCombatStatsValues(List<GlyphVector> combatStatsValues) {
		this.combatStatsValues = combatStatsValues;
	}
	
	private GlyphVector getExtraPower() {
		return extraPower;
	}

	private void setExtraPower(GlyphVector extraPower) {
		this.extraPower = extraPower;
	}

	private GlyphVector getPowerShortage() {
		return powerShortage;
	}

	private void setPowerShortage(GlyphVector powerShortage) {
		this.powerShortage = powerShortage;
	}

	public synchronized void setup(Graphics2D graphics) {
		if (getUnitName() == null) {
			FontRenderContext fontRenderContext = graphics.getFontRenderContext();
	
			// variant info
			if ((getUnit().getPeriodOfService() != null) && (getUnit().getPeriodOfService().getAvailability() != Availability.BASE)) {
				setVariant(VARIANT_FONT.createGlyphVector(fontRenderContext, getUnit().getAvailabilityInfo()));
			}
	
			// version info
			setVersion(VERSION_FONT.createGlyphVector(fontRenderContext, getUnit().getVersionInfo()));
	
			// given name and counter
			setGivenName(GIVENNAME_FONT.createGlyphVector(fontRenderContext, "Name:"));
			setCounter(GIVENNAME_FONT.createGlyphVector(fontRenderContext,   "Counter:"));
	
			// name and model
			GlyphVector unitNameGlyph = NAME_FONT.deriveFont(NAME_TRANSFORM).createGlyphVector(fontRenderContext, getUnit().getName());
			
			if ((getUnit().getModel() != null) && (getUnit().getModel().length() > 0)) {
				double nameWidth = 4 + unitNameGlyph.getVisualBounds().getWidth() + 6 + 4;
				Font modelFont = MODEL_FONT;
				GlyphVector leftParenthesesGlyph  = modelFont.deriveFont(PARENTHESES_TRANSFORM).createGlyphVector(fontRenderContext, "(");
				GlyphVector modelGlyph            = modelFont.deriveFont(MODEL_TRANSFORM).createGlyphVector(fontRenderContext, getUnit().getModel());
				GlyphVector rightParenthesesGlyph = modelFont.deriveFont(PARENTHESES_TRANSFORM).createGlyphVector(fontRenderContext, ")");
				double modelWidth = leftParenthesesGlyph.getVisualBounds().getWidth() + 1 + modelGlyph.getVisualBounds().getWidth() + 1 + rightParenthesesGlyph.getVisualBounds().getWidth();

				while ((modelFont.getSize() >= 14) && (nameWidth + modelWidth > DEFAULT_WIDTH)) {
					modelFont = new Font(modelFont.getFamily(), modelFont.getStyle(), modelFont.getSize() - 1);

					leftParenthesesGlyph  = modelFont.deriveFont(PARENTHESES_TRANSFORM).createGlyphVector(fontRenderContext, "(");
					modelGlyph            = modelFont.deriveFont(MODEL_TRANSFORM).createGlyphVector(fontRenderContext, getUnit().getModel());
					rightParenthesesGlyph = modelFont.deriveFont(PARENTHESES_TRANSFORM).createGlyphVector(fontRenderContext, ")");
					modelWidth = leftParenthesesGlyph.getVisualBounds().getWidth() + 1 + modelGlyph.getVisualBounds().getWidth() + 1 + rightParenthesesGlyph.getVisualBounds().getWidth();
				}

				setLeftParentheses(leftParenthesesGlyph);
				setModel(modelGlyph);
				setRightParentheses(rightParenthesesGlyph);
			}
			
			double modelWidth = 4 + ((getModel() != null) ? 6 + getLeftParentheses().getVisualBounds().getWidth() + 1 + getModel().getVisualBounds().getWidth() + 1 + getRightParentheses().getVisualBounds().getWidth() : 0) + 4;
			Font nameFont = NAME_FONT;

			while ((nameFont.getSize() >= 14) && (unitNameGlyph.getVisualBounds().getWidth() + modelWidth > DEFAULT_WIDTH)) {
				nameFont = new Font(nameFont.getFamily(), nameFont.getStyle(), nameFont.getSize() - 1);
				unitNameGlyph = nameFont.deriveFont(NAME_TRANSFORM).createGlyphVector(fontRenderContext, getUnit().getName());					
			}
			
			setUnitName(unitNameGlyph);
			
			Rectangle bounds = getUnitName().getPixelBounds(fontRenderContext, 0, 0);
			double y = (NAME_HEIGHT - bounds.getY()) / 2.0;
			double subscript = bounds.getHeight() + bounds.getY();
			setUnitNameBaseline((y + subscript >= NAME_HEIGHT) ? NAME_HEIGHT - subscript - 1 : y);

			// SPECS
			setSpecs(HEADING_FONT.createGlyphVector(fontRenderContext, "SPECS"));
			setSpecsValues(new ArrayList<GlyphVector>(5));
			getSpecsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Class: " + abbreviateClass(getUnit().getType())));

			// In Service
			if (getUnit().getPeriodOfService() != null) {		
				if ((getUnit().getPeriodOfService().getInService() == 0) && (getUnit().getPeriodOfService().getEndedService() == 0)) {
					getSpecsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "In Service: Any"));
				} else {
					getSpecsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "In Service: " + getUnit().getPeriodOfService().getRange()));
				}
			} else {
				getSpecsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "In Service: "));				
			}
			
			// Point Value
			if (getUnit() instanceof SmallUnit) {
				getSpecsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Point Value: " + getUnit().getPointValue() + " each"));
			} else {			
				getSpecsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Point Value: " + getUnit().getPointValue()));
			}

			getSpecsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Ramming Factor: " + getUnit().getRammingFactor()));			
			
			if (getUnit() instanceof StructuralUnit) {	
				// Jump Delay
				String jumpDelay;
				if (getUnit() instanceof StructuralUnit) {
					List<System> hyperspaceDrives = ((StructuralUnit) getUnit()).getSystemsOfClass(HyperspaceDrive.class, true);
					if (hyperspaceDrives.size() > 0) {	
						int delay = 999;
			
						for (Iterator<System> iterator = hyperspaceDrives.iterator(); iterator.hasNext();) {
							HyperspaceDrive hyperspaceDrive = (HyperspaceDrive) iterator.next();
							
							if (hyperspaceDrive.getJumpDelay() < delay) {
								delay = hyperspaceDrive.getJumpDelay();
							}
						}
							
						jumpDelay = delay + " Turns";
					} else {
						jumpDelay = "N/A";				
					}
				} else {
					jumpDelay = "N/A";
				}
				
				getSpecsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Jump Delay: " + jumpDelay));			
			} else if (getUnit() instanceof FighterFlight) {
				getSpecsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Jinking Limit: " + ((FighterFlight) getUnit()).getJinkingLimit()));			
			}
			
			// MANEUVERING
			setManeuvering(HEADING_FONT.createGlyphVector(fontRenderContext, "MANEUVERING"));
			setManeuveringValues(new ArrayList<GlyphVector>(5));

			if (getUnit() instanceof MobileUnit) {
				MobileUnit mobileUnit = (MobileUnit) getUnit();
			
				// Turn Cost
				StringBuilder turnCost = new StringBuilder();
				turnCost.append(mobileUnit.getTurnCostFormatted());
				if (mobileUnit.getTurnCost() % 1 == 0) {
					turnCost.append(" x");
				}
				turnCost.append(" Speed");
				getManeuveringValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Turn Cost: " + turnCost.toString()));			
	
				// Turn Delay	
				StringBuilder turnDelay = new StringBuilder();
				turnDelay.append(mobileUnit.getTurnDelayFormatted());
				if (mobileUnit.getTurnDelay() % 1 == 0) {
					turnDelay.append(" x");
				}
				if (mobileUnit.getTurnDelay() != 0) {
					turnDelay.append(" Speed");
				}
				getManeuveringValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Turn Delay: " + turnDelay.toString()));			
				
				// Accel/Decel Cost
				getManeuveringValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Accel/Decel Cost: " + mobileUnit.getAccelDecelCost() + " Thrust"));			
				
				// Pivot Cost
				// TODO pivot cost for Shadow Regeneration Outpost
				StringBuilder pivotCost = new StringBuilder();	
				if (mobileUnit.isPivotCapable()) {
					pivotCost.append(mobileUnit.getPivotStartCost());
					if (mobileUnit.getPivotStopCost() > 0) {
						pivotCost.append("+");
						pivotCost.append(mobileUnit.getPivotStopCost());
					}
					pivotCost.append(" Thrust");		
				} else {
					pivotCost.append("N/A");
				}
				getManeuveringValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Pivot Cost: " + pivotCost.toString()));			
				
				// Roll Cost
				StringBuilder rollCost = new StringBuilder();	
				if (mobileUnit.isRollCapable()) {
					rollCost.append(mobileUnit.getRollStartCost());
					if (mobileUnit.getRollStopCost() > 0) {
						rollCost.append("+");
						rollCost.append(mobileUnit.getRollStopCost());
					}
					rollCost.append(" Thrust");		
				} else {
					rollCost.append("N/A");					
				}
				getManeuveringValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Roll Cost: " + rollCost.toString()));			
			} else {
				getManeuveringValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Turn Cost: N/A"));			
				getManeuveringValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Turn Delay: N/A"));			
				getManeuveringValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Accel/Decel Cost: N/A"));			
				getManeuveringValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Pivot Cost: N/A"));			
				getManeuveringValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Roll Cost: N/A"));			
			}			
			
			// COMBAT STATS
			setCombatStats(HEADING_FONT.createGlyphVector(fontRenderContext, "COMBAT STATS"));
			setCombatStatsValues(new ArrayList<GlyphVector>(5));

			// Fwd/Aft Defense
			int defenseBonus = 0;
			if (getUnit() instanceof StructuralUnit) {
				List<System> defensiveSystems = ((StructuralUnit) getUnit()).getSystemsOfClass(DefensiveSystem.class, true);
				
				for (Iterator<System> iterator = defensiveSystems.iterator(); iterator.hasNext();) {
					DefensiveSystem defensiveSystem = (DefensiveSystem) iterator.next();
					if (defensiveSystem.getDefensiveBonus() > defenseBonus) {
						defenseBonus = defensiveSystem.getDefensiveBonus();
					}
				}
			}
	
			StringBuilder fwdAftDefense = new StringBuilder();		
			fwdAftDefense.append(getUnit().getFwdAftDefense());		
			if (defenseBonus > 0) {
				fwdAftDefense.append(" (").append(getUnit().getFwdAftDefense() - defenseBonus).append(")");	
			}
			getCombatStatsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Fwd/Aft Defense: " + fwdAftDefense.toString()));			
			
			// Stb/Port Defense
			StringBuilder stbPortDefense = new StringBuilder();		
			stbPortDefense.append(getUnit().getStbPortDefense());				
			if (defenseBonus > 0) {
				stbPortDefense.append(" (").append(getUnit().getStbPortDefense() - defenseBonus).append(")");	
			}
			getCombatStatsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Stb/Port Defense: " + stbPortDefense.toString()));			
			
			if (getUnit() instanceof StructuralUnit) {	
				// Engine Efficiency
				if (getUnit() instanceof StructuralUnit) {
					List<System> engines = ((StructuralUnit) getUnit()).getSystemsOfClass(Engine.class, true);
		
					if (engines.size() > 0) {
						Engine mostEfficientEngine = null;
						
						for (Iterator<System> iterator = engines.iterator(); iterator.hasNext();) {
							Engine engine = (Engine) iterator.next();
							
							if ((mostEfficientEngine == null) || (engine.getEfficiencyRatio() > mostEfficientEngine.getEfficiencyRatio())) {
								mostEfficientEngine = engine;
							}
						}
							
						getCombatStatsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Engine Efficiency: " + mostEfficientEngine.getEfficiency()));			
					} else {
						getCombatStatsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Engine Efficiency: N/A"));			
					}
				} else {
					getCombatStatsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Engine Efficiency: N/A"));			
				}		
				
//				int extraPower = calculateExtraPower();
//				if (extraPower >= 0) {
//					getCombatStatsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Extra Power: +" + extraPower));			
//				} else {
//					getCombatStatsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Power Shortage: " + extraPower));			
//				}
				setExtraPower(VALUE_FONT.createGlyphVector(fontRenderContext, "Extra Power:"));
				setPowerShortage(VALUE_FONT.createGlyphVector(fontRenderContext, "Power Shortage:"));
				getCombatStatsValues().add(getExtraPower());				
			} else if (getUnit() instanceof FighterFlight) {
				getCombatStatsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Free Thrust: " + ((FighterFlight) getUnit()).getFreeThrust()));			

				if (((FighterFlight) getUnit()).getInitiativeModifier() >= 0) {
					getCombatStatsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Offensive Bonus: +" + ((FighterFlight) getUnit()).getInitiativeModifier()));			
				} else {
					getCombatStatsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Offensive Penalty: " + ((FighterFlight) getUnit()).getInitiativeModifier()));			
				}
			}

			if (getUnit().getInitiativeModifier() >= 0) {
				getCombatStatsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Initiative Bonus: +" + getUnit().getInitiativeModifier()));			
			} else {
				getCombatStatsValues().add(VALUE_FONT.createGlyphVector(fontRenderContext, "Initiative Penalty: " + getUnit().getInitiativeModifier()));			
			}
		}
	}
	
	protected String abbreviateClass(String text) {
		text = text.replaceAll("Light",             "Lt");
		text = text.replaceAll("Heavy",             "Hvy");
		text = text.replaceAll("Vessel",            "Vsl");
		text = text.replaceAll("Orbital Satellite", "OSAT");
		
		return text;
	}
	
	@Override
	public void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		if (getUnitName() == null) {
			setup(graphics);
		}
		
		graphics.setStroke(STROKE);
		graphics.setColor(Color.white);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		// variant info
		if (getVariant() != null) {
			graphics.setColor(Color.black);
			
			if (getUnit() instanceof StructuralUnit) {
				graphics.drawGlyphVector(getVariant(), 1, 0);
			} else if (getUnit() instanceof FighterFlight) {
				graphics.drawGlyphVector(getVariant(), 1, 10);
			}
		}
		
		// version info
		graphics.setColor(Color.black);
		if (getUnit() instanceof StructuralUnit) {
			graphics.drawGlyphVector(getVersion(), 2, 10);
		} else if (getUnit() instanceof FighterFlight) {
			graphics.drawGlyphVector(getVersion(), (int) (282 - getVersion().getLogicalBounds().getWidth()), 10);
		}

		// given name and counter
		if (getUnit() instanceof StructuralUnit) {
			graphics.drawGlyphVector(getGivenName(), 118, 9);
			graphics.drawGlyphVector(getCounter(),   232, 9);

			graphics.setStroke(NAME_STROKE);
			graphics.drawLine(140, 10, 226, 10);
			graphics.drawLine(260, 10, 284, 10);
		}
		
		// name and model
		graphics.setStroke(STROKE);
		graphics.setColor(Color.black);
		graphics.fillRect(0, 14, DEFAULT_WIDTH, NAME_HEIGHT);
		graphics.drawRect(0, 14, DEFAULT_WIDTH, NAME_HEIGHT);
		graphics.setColor(Color.white);
		
		float x = 4;
		float y = (float) (14.0 + getUnitNameBaseline());
		graphics.drawGlyphVector(getUnitName(), x, y);
		if (getModel() != null) {
			x += getUnitName().getVisualBounds().getWidth() + 6;
			graphics.drawGlyphVector(getLeftParentheses(), x, y);
			x += getLeftParentheses().getVisualBounds().getWidth() + 1;
			graphics.drawGlyphVector(getModel(), x, y);
			x += 1 + getModel().getVisualBounds().getWidth();
			graphics.drawGlyphVector(getRightParentheses(), x, y);
		}
		
		// datacard
		graphics.setStroke(STROKE);
		graphics.setColor(Color.black);
		
		// specs
		graphics.drawRect(0, 46, 83, 64);
		graphics.drawGlyphVector(getSpecs(), 2, 57);
		
		for (int i = 0; i < getSpecsValues().size(); i++) {
			graphics.drawGlyphVector(getSpecsValues().get(i), 2, 66 + (i * 10));			
		}
		
		// maneuvering
		graphics.drawRect(85, 46, 101, 64);
		graphics.drawGlyphVector(getManeuvering(), 87, 57);
		
		for (int i = 0; i < getManeuveringValues().size(); i++) {
			graphics.drawGlyphVector(getManeuveringValues().get(i), 87, 66 + (i * 10));			
		}
		
		// combat stats
		graphics.setFont(VALUE_FONT);
		graphics.drawRect(188, 46, 96, 64);
		graphics.drawGlyphVector(getCombatStats(), 190, 57);
		
		for (int i = 0; i < getCombatStatsValues().size(); i++) {
			if (getCombatStatsValues().get(i) == getExtraPower()) {
				int extraPower = calculateExtraPower();
				GlyphVector heading = (extraPower >= 0) ? getExtraPower() : getPowerShortage();
				graphics.drawGlyphVector(heading, 190, 66 + (i * 10));	
				
				graphics.setColor(availablePowerChanged() ? System.ANNOTATION_CHANGED_COLOR : Color.black);
				graphics.drawString((extraPower >= 0) ? "+" + extraPower : String.valueOf(extraPower), 190 + heading.getOutline().getBounds().width + 2, 66 + (i * 10));					
				graphics.setColor(Color.black);
			} else {
				graphics.drawGlyphVector(getCombatStatsValues().get(i), 190, 66 + (i * 10));		
			}
		}
		
		// turn chart
		if ((getUnit() instanceof StructuralUnit) && (getUnit() instanceof MobileUnit)) {
			MobileUnit mobileUnit = (MobileUnit) getUnit();
			
			graphics.setStroke(STROKE);
			graphics.setColor(Color.black);
			graphics.fillRect(0, 108, 284, 9);
			graphics.drawRect(0, 108, 284, 26);	
		
			graphics.setFont(TURN_CHART_FONT);			
			graphics.setColor(Color.white);
			graphics.drawString("Speed", 2, 115);	
			graphics.setColor(Color.black);
			graphics.drawString("Turn Cost", 2, 124);	
			graphics.drawString("Turn Delay", 2, 132);	

			FontMetrics fontMetrics = graphics.getFontMetrics(TURN_CHART_FONT);
			
			for (int index = 1; index <= 12; index++) {
				graphics.setColor(Color.white);

				String speed = String.valueOf(index);
				Rectangle2D textBounds = fontMetrics.getStringBounds(speed, graphics);
				graphics.drawString(speed, 49f + (19f * (float) index) - (float) textBounds.getWidth(), 115f);	

				graphics.setColor(Color.black);

				String turnCost = String.valueOf(roundUp(index * mobileUnit.getTurnCost()));
				textBounds = fontMetrics.getStringBounds(turnCost, graphics);
				graphics.drawString(turnCost, 49f + (19f * (float) index) - (float) textBounds.getWidth(), 124f);	

				String turnDelay = String.valueOf(roundUp(index * mobileUnit.getTurnDelay()));
				textBounds = fontMetrics.getStringBounds(turnDelay, graphics);
				graphics.drawString(turnDelay, 49f + (19f * (float) index) - (float) textBounds.getWidth(), 132f);	
			}
		}
	}
	
	protected int calculateExtraPower() {
		int availablePower   = 0;
		int powerRequirement = 0;
		
		if (getUnit() instanceof StructuralUnit) {
			List<System> poweringSystems = ((StructuralUnit) getUnit()).getSystemsOfClass(PowerProvidingSystem.class, true);
			for (Iterator<System> iterator = poweringSystems.iterator(); iterator.hasNext();) {
				PowerProvidingSystem system = ((PowerProvidingSystem) iterator.next());
				availablePower += system.getAvailablePower();
			}		
			
			List<System> poweredSystems = ((StructuralUnit) getUnit()).getSystemsOfClass(PowerRequiringSystem.class, true);
			for (Iterator<System> iterator = poweredSystems.iterator(); iterator.hasNext();) {
				PowerRequiringSystem system = ((PowerRequiringSystem) iterator.next());
				powerRequirement += system.getPowerRequirement();
			}		
		}
		
		return availablePower - powerRequirement;
	}
	
	protected boolean availablePowerChanged() {
		boolean availablePowerChanged = false;
		
		if (getUnit() instanceof StructuralUnit) {
			List<System> poweringSystems = ((StructuralUnit) getUnit()).getSystemsOfClass(PowerProvidingSystem.class, true);
			for (Iterator<System> iterator = poweringSystems.iterator(); iterator.hasNext();) {
				PowerProvidingSystem system = ((PowerProvidingSystem) iterator.next());
				if (system.getAvailablePower() < system.getBaseAvailablePower()) {
					availablePowerChanged = true;
					break;
				}
			}		
		}
		
		return availablePowerChanged;
	}
	
	protected int roundUp(final double value) {
		int result = (int) value;
		
		if (value % 1 > 0) {
			result++;
		}
		
		return result;
	}
	
}
