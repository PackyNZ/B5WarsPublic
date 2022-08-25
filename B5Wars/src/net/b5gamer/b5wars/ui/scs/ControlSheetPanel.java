/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.b5wars.ui.scs;

import java.awt.Adjustable;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.RenderingHints;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.font.FontRenderContext;
import java.awt.font.GlyphVector;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.swing.JComponent;
import javax.swing.JMenu;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.JScrollBar;

import net.b5gamer.b5wars.io.DataManager;
import net.b5gamer.b5wars.unit.Unit;
import net.b5gamer.swing.SVGDocumentComponent;
import net.b5gamer.util.StatusEvent;
import net.b5gamer.util.StatusListener;

public abstract class ControlSheetPanel extends JPanel implements ComponentListener, MouseListener, MouseMotionListener, 
		MouseWheelListener, AdjustmentListener {
	
	private static final long serialVersionUID = 5510322398112236L;

	public    static final double SCS_WIDTH  = 612;
	public    static final double SCS_HEIGHT = 792;
	public    static final double SCS_HEADER = 138;
	protected static final int    SCS_GUTTER = 2; 

	private static final Font COPYRIGHT_FONT =(new Font("Verdana", Font.PLAIN, 6)).deriveFont(AffineTransform.getScaleInstance(0.75, 1.0));
		
	private Unit        unit                = null;
	private UsageMode   mode                = null;
	private SVGDocumentComponent logo       = null;
	private GlyphVector copyrightLine1      = null;
	private GlyphVector copyrightLine2      = null;
	private boolean     setupComponents     = true;
	private List<JComponent> controlSheetComponents = new ArrayList<JComponent>(0);
	private Map<Class<?>, JPopupMenu> popupMenus = new HashMap<Class<?>, JPopupMenu>();
	private JScrollBar  verticalScrollBar   = null;
	private JScrollBar  horizontalScrollBar = null;
	private boolean     showData            = true;
	private double      scale               = 1.0; 
	private boolean     scaling             = false;
	private boolean     fitToScreen         = true; 
	private int         offsetX             = 0;
	private int         offsetY             = 0;
	private int         mouseX              = 0;
	private int         mouseY              = 0;
	private boolean     dragging            = false;
	private final List<StatusListener> statusListeners = new ArrayList<StatusListener>(0);
		
	public ControlSheetPanel(final Unit unit) {
		this(unit, null);
	}
	
	public ControlSheetPanel(final Unit unit, final UsageMode mode) {
		super(new BorderLayout(), true);
		
		setUnit(unit);        
		setUsageMode(mode);

		addComponentListener(this);
        addMouseListener(this);
        addMouseMotionListener(this);
        addMouseWheelListener(this);
	}
	
    public Unit getUnit() {
		return unit;
	}

    public void setUnit(final Unit unit) {
		this.unit = unit;		
		
        if ((getLogo() == null) && (getUnit() != null) && (getUnit().isOfficial())) {
	    	try {
	    		setLogo(new SVGDocumentComponent(DataManager.getResourceDao().load("B5WLogo.svg")));
	    	} catch (Exception e) {
	    		setLogo(null);
	    		e.printStackTrace();
			}        
        }
        
		setSetupComponents(true);
		setFitToScreen(true);
    }

    public final UsageMode getUsageMode() {
		return mode;
	}

    public synchronized void setUsageMode(final UsageMode mode) {
		this.mode = (mode == null) ? UsageMode.VIEW : mode;
	}
    
	protected SVGDocumentComponent getLogo() {
		return logo;
	}

	protected void setLogo(SVGDocumentComponent logo) {
		this.logo = logo;
	}

	protected GlyphVector getCopyrightLine1() {
		return copyrightLine1;
	}

	private void setCopyrightLine1(GlyphVector copyrightLine1) {
		this.copyrightLine1 = copyrightLine1;
	}

	protected GlyphVector getCopyrightLine2() {
		return copyrightLine2;
	}

	private void setCopyrightLine2(GlyphVector copyrightLine2) {
		this.copyrightLine2 = copyrightLine2;
	}

	protected boolean isSetupComponents() {
		return setupComponents;
	}

	private void setSetupComponents(boolean setupComponents) {
		this.setupComponents = setupComponents;
	}

	protected final List<JComponent> getControlSheetComponents() {
		return controlSheetComponents;
	}

    protected JPopupMenu getPopupMenu() {
    	return getPopupMenus().get(this.getClass());
    }

    protected final Map<Class<?>, JPopupMenu> getPopupMenus() {
		return popupMenus;
	}
    
    protected void setPopupMenus() {
		// create menu items
        JMenu viewMenu = new JMenu("View");
        viewMenu.add(ControlSheetMenuBar.createToggleDataMenuItem(this));
	    JMenu zoomToMenu = ControlSheetMenuBar.createZoomToMenu(this);
		
		// create menus
		JPopupMenu popupMenu = new JPopupMenu();
		popupMenu.add(viewMenu);
		popupMenu.addSeparator();
		popupMenu.add(zoomToMenu);
	    getPopupMenus().put(this.getClass(), popupMenu);
		
		if (getUsageMode() == UsageMode.ALL) {
		} else if (getUsageMode() == UsageMode.EDIT) {
		} else if (getUsageMode() == UsageMode.VIEW) {
		} else if (getUsageMode() == UsageMode.PLAY) {
		}
	}     
    
	protected final JScrollBar getVerticalScrollBar() {
		return verticalScrollBar;
	}

	protected final void setVerticalScrollBar(final JScrollBar scrollBar) {
		this.verticalScrollBar = scrollBar;
		
		scrollBar.addAdjustmentListener(this);
	}

	protected final JScrollBar getHorizontalScrollBar() {
		return horizontalScrollBar;
	}

	protected final void setHorizontalScrollBar(final JScrollBar scrollBar) {
		this.horizontalScrollBar = scrollBar;

		scrollBar.addAdjustmentListener(this);
	}

	public final boolean isShowData() {
		return showData;
	}

    public final void setShowData(final boolean showData) {
		this.showData = showData;
		
		if (isFitToScreen()) {
			fitToScreen();
		} else {
			if (isShowData()) {
				setOffsetY((int) (getOffsetY() - (SCS_HEADER * getScale())));
			} else {
				setOffsetY((int) (getOffsetY() + (SCS_HEADER * getScale())));					
			}
		}
	}

	protected final double getScale() {
		return scale;
	}

	protected final void setScale(double scale) {
		scale = (scale < ZoomToAction.ZOOM_LEVELS[0] / 100.0) ? ZoomToAction.ZOOM_LEVELS[0] / 100.0 : scale;
		scale = (scale > ZoomToAction.ZOOM_LEVELS[ZoomToAction.ZOOM_LEVELS.length - 1] / 100.0) ? ZoomToAction.ZOOM_LEVELS[ZoomToAction.ZOOM_LEVELS.length - 1] / 100.0 : scale;
		
		this.scale = scale;

		notifyStatusListeners("Zoom", String.valueOf((int) (scale * 100.0)) + "%");
		
		setScaling(true);
		if (getVerticalScrollBar() != null) {
			getVerticalScrollBar().setValue(0);
			getVerticalScrollBar().setMinimum(1);
			
			if (getScaledSCSHeight() > getSize().getHeight()) {
				getVerticalScrollBar().setMaximum((int) getScaledSCSHeight() + (2 * SCS_GUTTER));
				getVerticalScrollBar().setVisibleAmount((int) getSize().getHeight());
			} else {
				getVerticalScrollBar().setMaximum((int) getSize().getHeight());
				getVerticalScrollBar().setVisibleAmount((int) getScaledSCSHeight() + (2 * SCS_GUTTER));				
			}
		}
		if (getHorizontalScrollBar() != null) {
			getHorizontalScrollBar().setValue(0);
			getHorizontalScrollBar().setMinimum(1);

			if (getScaledSCSWidth() > getSize().getWidth()) {
				getHorizontalScrollBar().setMaximum((int) getScaledSCSWidth() + (2 * SCS_GUTTER));
				getHorizontalScrollBar().setVisibleAmount((int) getSize().getWidth());
			} else {
				getHorizontalScrollBar().setMaximum((int) getSize().getWidth());
				getHorizontalScrollBar().setVisibleAmount((int) getScaledSCSWidth() + (2 * SCS_GUTTER));
			}
		}
		setScaling(false);

		setOffsetX(getOffsetX());
		setOffsetY(getOffsetY());		
	}

	private final boolean isScaling() {
		return scaling;
	}

	private final void setScaling(final boolean scaling) {
		this.scaling = scaling;
	}

	protected final boolean isFitToScreen() {
		return fitToScreen;
	}

	protected final void setFitToScreen(final boolean fitToScreen) {
		this.fitToScreen = fitToScreen;
		
		if (getVerticalScrollBar() != null) {
			getVerticalScrollBar().setVisible(!fitToScreen);
		}
		if (getHorizontalScrollBar() != null) {
			getHorizontalScrollBar().setVisible(!fitToScreen);
		}			
		
		if (fitToScreen) {
			fitToScreen();
		}
	}
	
	private final void fitToScreen() {
		if (getSize().getWidth() / getSCSWidth() > getSize().getHeight() / getSCSHeight()) {
			setScale((getSize().getHeight() - (2 * SCS_GUTTER)) / getSCSHeight());
		} else {
			setScale((getSize().getWidth() - (2 * SCS_GUTTER)) / getSCSWidth());
		}

		setOffsetX((int) ((getSize().getWidth() - getScaledSCSWidth()) / 2.0));			
		setOffsetY((int) ((getSize().getHeight() - getScaledSCSHeight()) / 2.0));
	}
	
	protected final int getOffsetX() {
		return offsetX;
	}

	protected final void setOffsetX(int offset) {
		setOffsetX(offset, true);
	}
	
	protected final void setOffsetX(int offset, boolean updateScrollBar) {
		int scsWidth = (int) getScaledSCSWidth();
		int width    = getWidth();
		
		if (scsWidth < width - (2 * SCS_GUTTER)) {
			offset = (offset < SCS_GUTTER) ? SCS_GUTTER : offset;
			offset = (offset > (width - (2 * SCS_GUTTER)) - scsWidth) ? (width - (2 * SCS_GUTTER)) - scsWidth : offset;
		} else {
			offset = (offset > SCS_GUTTER) ? SCS_GUTTER : offset;
			offset = (offset < (width - (2 * SCS_GUTTER)) - scsWidth) ? (width - (2 * SCS_GUTTER)) - scsWidth : offset;
		}
		
		this.offsetX = offset;

		if ((updateScrollBar) && (getHorizontalScrollBar() != null)) {
			if (getScaledSCSWidth() > getSize().getWidth()) {
				getHorizontalScrollBar().setValue(offset - getHorizontalScrollBar().getVisibleAmount());
			} else {
				getHorizontalScrollBar().setValue((int) getSize().getWidth() - offset - getHorizontalScrollBar().getVisibleAmount());
			}
		}
	}

	protected final int getOffsetY() {
		return offsetY;
	}

	protected final void setOffsetY(int offset) {
		setOffsetY(offset, true);
	}
	
	protected final void setOffsetY(int offset, boolean updateScrollBar) {
		int scsHeight = (int) getScaledSCSHeight();
		int height    = getHeight();
		
		if (scsHeight < height - (2 * SCS_GUTTER)) {
			offset = (offset < SCS_GUTTER) ? SCS_GUTTER : offset;
			offset = (offset > (height - (2 * SCS_GUTTER)) - scsHeight) ? (height - (2 * SCS_GUTTER)) - scsHeight : offset;
		} else {
			offset = (offset > SCS_GUTTER) ? SCS_GUTTER : offset;
			offset = (offset < (height - (2 * SCS_GUTTER)) - scsHeight) ? (height - (2 * SCS_GUTTER)) - scsHeight : offset;
		}
		
		this.offsetY = offset;
 
		if ((updateScrollBar) && (getVerticalScrollBar() != null)) {
			if (getScaledSCSHeight() > getSize().getHeight()) {
				getVerticalScrollBar().setValue(offset - getVerticalScrollBar().getVisibleAmount());
			} else {
				getVerticalScrollBar().setValue((int) getSize().getHeight() - offset - getVerticalScrollBar().getVisibleAmount());
			}
		}
	}

	protected final int getMouseX() {
		return mouseX;
	}

	protected final void setMouseX(final int mouseX) {
		this.mouseX = mouseX;
	}

	protected final int getMouseY() {
		return mouseY;
	}

	protected final void setMouseY(final int mouseY) {
		this.mouseY = mouseY;
	}
	
	protected final boolean isDragging() {
		return dragging;
	}

	private final void setDragging(boolean dragging) {
		this.dragging = dragging;
	}	
    
	private List<StatusListener> getStatusListeners() {
		return statusListeners;
	}

	public void addStatusListener(StatusListener listener) {
		if ((listener != null) && !(getStatusListeners().contains(listener))) {
			getStatusListeners().add(listener);
		}
	}
	
	public void removeStatusListener(StatusListener listener) {
		getStatusListeners().remove(listener);
	}
	
	protected void notifyStatusListeners(String key, String value) {
		if (getStatusListeners().size() > 0) {
			StatusEvent e = new StatusEvent(this, key, value);

			for (Iterator<StatusListener> iterator = getStatusListeners().iterator(); iterator.hasNext();) {
				iterator.next().statusValueChanged(e);
			}
		}
	}	
        
	protected final double getSCSWidth() {
    	return SCS_WIDTH;
    }
    
    protected final double getScaledSCSWidth() {
    	return getSCSWidth() * getScale();
    }

    protected final double getSCSHeight() {
    	return isShowData() ? SCS_HEIGHT : SCS_HEIGHT - SCS_HEADER;
    }

    protected final double getScaledSCSHeight() {
    	return getSCSHeight() * getScale();
    }

	protected Point convertCoordsFromScreenToControlSheet(int x, int y) {
		x = (int) ((x - getOffsetX()) / getScale());
		y = (int) ((y - getOffsetY()) / getScale());
		y = (isShowData()) ? y : (int) (y + SCS_HEADER);
		
		return new Point(x, y);
	}
	    
	@Override
	public final void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if (isSetupComponents()) {
			setupComponents(graphics);
		}
		
		graphics.setColor(Color.darkGray);
		graphics.fillRect(0, 0, getWidth(), getHeight());
		
		if (getUnit() != null) {
			graphics.transform(AffineTransform.getTranslateInstance(getOffsetX(), getOffsetY()));
//			graphics.transform(AffineTransform.getRotateInstance(60 * Math.PI / 180.0,
//					getOffsetX() + (getSCSWidth() / 2.0), getOffsetY() + (getSCSHeight() / 2.0)));
			graphics.transform(AffineTransform.getScaleInstance(getScale(), getScale()));
	
//			graphics.setColor(Color.darkGray);
//			graphics.fillRect(4, 4, (int) getSCSWidth(), (int) getSCSHeight());	
	
			paintControlSheet(graphics, false);
		}
    }

	public final void paintControlSheet(Graphics2D graphics) {
		paintControlSheet(graphics, true);
	}
	
	public abstract void paintControlSheet(Graphics2D graphics, boolean exporting);
	
	protected final void paint(Graphics2D graphics, JComponent component) {
		if (component != null) {
			AffineTransform transform = graphics.getTransform();
			graphics.transform(AffineTransform.getTranslateInstance(component.getX(), component.getY()));
			component.paint(graphics);
			graphics.setTransform(transform);
		}		
	}
	
	protected final void setupComponents(final Graphics2D graphics) {
		setSetupComponents(false);

		for (Iterator<JComponent> iterator = getControlSheetComponents().iterator(); iterator.hasNext();) {
			JComponent component = iterator.next();
			
			if (component instanceof ControlSheetComponent) {
				((ControlSheetComponent) component).setup(graphics);
			}
		}
		
        if ((getUnit() != null) && (getUnit().isOfficial())) {
			FontRenderContext fontRenderContext = graphics.getFontRenderContext();
			setCopyrightLine1(COPYRIGHT_FONT.createGlyphVector(fontRenderContext, "TM & � WARNER BROS."));
			setCopyrightLine2(COPYRIGHT_FONT.createGlyphVector(fontRenderContext, "PERMISSION GRANTED TO PHOTOCOPY FOR PERSONAL USE"));
        }
	}
	
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		if (isFitToScreen()) {
			fitToScreen();			
		} else {
			setOffsetX(getOffsetX());
			setOffsetY(getOffsetY());
		}
		
		repaint();
	}

	public void componentShown(ComponentEvent e) {
	}

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		setMouseX(e.getX());
		setMouseY(e.getY());
	
		if (e.getButton() == MouseEvent.BUTTON1) {
			if (!isFitToScreen()) {
				setDragging(true);
			}
		}

		checkIfShowPopup(e);
	}	
	
	public void mouseReleased(MouseEvent e) {
		setDragging(false);
		
		checkIfShowPopup(e);
	}

	public void mouseDragged(MouseEvent e) {
		if ((getUnit() != null) && (isDragging())) {
			if ((getMouseX() != e.getX()) || (getMouseY() != e.getY())) {	
				setOffsetX(getOffsetX() + (e.getX() - getMouseX()));
				setOffsetY(getOffsetY() + (e.getY() - getMouseY()));
			
				setMouseX(e.getX());
				setMouseY(e.getY());

				repaint();
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		if (getUnit() != null) {
//			double scale = getScale();
			setScale(getScale() - (e.getWheelRotation() * (getScale() / 2.0)));
					
			setFitToScreen(false);
		
		// TODO
////		if (getScale() > scale) {
//			System.out.println("");
//			System.out.println("screen width=" + getWidth());
//			System.out.println("old offset x=" + getOffsetX());
//			System.out.println("offset x=" + (int) (getOffsetX() + ((getSCSWidth()  * scale) - getScaledSCSWidth()) / 2.0));
//			System.out.println("old width=" + (getSCSWidth()  * scale));
//			System.out.println("new width=" + getScaledSCSWidth());
//			setOffsetX((int) (getOffsetX() + ((getSCSWidth()  * scale) - getScaledSCSWidth()) / 2.0));
//			System.out.println("new offset x=" + getOffsetX());
//	
//			System.out.println("screen height=" + getHeight());
//			System.out.println("old offset y=" + getOffsetY());
//			System.out.println("offset y=" + (int) (getOffsetY() + ((getSCSHeight()  * scale) - getScaledSCSHeight()) / 2.0));
//			System.out.println("old height=" + (getSCSHeight()  * scale));
//			System.out.println("new height=" + getScaledSCSHeight());
//			setOffsetY((int) (getOffsetY() + ((getSCSHeight() * scale) - getScaledSCSHeight()) / 2.0));
//			System.out.println("new offset y=" + getOffsetY());
////		} else {
////			System.out.println("");
////			System.out.println("screen width=" + getWidth());
////			System.out.println("old offset x=" + getOffsetX());
////			System.out.println("offset x=" + (int) ((getOffsetX() / scale) * getScale()));
////			System.out.println("old width=" + (getSCSWidth()  * scale));
////			System.out.println("new width=" + getScaledSCSWidth());
////			setOffsetX((int) ((getOffsetX() / scale) * getScale()));
////			System.out.println("new offset x=" + getOffsetX());
////	
////			System.out.println("screen height=" + getHeight());
////			System.out.println("old offset y=" + getOffsetY());
////			System.out.println("offset y=" + (int) ((getOffsetY() / scale) * getScale()));
////			System.out.println("old height=" + (getSCSHeight()  * scale));
////			System.out.println("new height=" + getScaledSCSHeight());
////			setOffsetY((int) ((getOffsetY() / scale) * getScale()));
////			System.out.println("new offset y=" + getOffsetY());			
////		}
		
			repaint();
		}
	}
	
	public void adjustmentValueChanged(AdjustmentEvent e) {
		
		// TODO SCS_GUTTER

		if (!isScaling() && !isDragging()) {
			if (e.getAdjustable().getOrientation() == Adjustable.VERTICAL) {
				if (getScaledSCSHeight() > getSize().getHeight()) {		
					JScrollBar scrollBar = (JScrollBar) e.getSource();					
					setOffsetY((int) (scrollBar.getValue() + (scrollBar.getVisibleAmount() - getSize().getHeight())) * -1, false);
				} else {
					setOffsetY((int) (getSize().getHeight() - (e.getValue() + getScaledSCSHeight())), false);
				}
				
				repaint();
			} else if (e.getAdjustable().getOrientation() == Adjustable.HORIZONTAL) {
				if (getScaledSCSWidth() > getSize().getWidth()) {
					JScrollBar scrollBar = (JScrollBar) e.getSource();
					setOffsetX((int) (scrollBar.getValue() + (scrollBar.getVisibleAmount() - getSize().getWidth())) * -1, false);				
				} else {
					setOffsetX((int) (getSize().getWidth() - (e.getValue() + getScaledSCSWidth())), false);				
				}
			
				repaint();
			}
		}
	}	

    private void checkIfShowPopup(MouseEvent e) {
        if ((getUnit() != null) && (e.isPopupTrigger())) {
        	JPopupMenu popupMenu = getPopupMenu();

        	if (popupMenu != null) {
        		popupMenu.show(this, e.getX(), e.getY());
    		}
        }
    }

}
