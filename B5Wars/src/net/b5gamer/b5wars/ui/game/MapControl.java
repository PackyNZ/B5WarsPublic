package net.b5gamer.b5wars.ui.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.Point2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import net.b5gamer.b5wars.ui.map.MapRenderer;
import net.b5gamer.b5wars.ui.map.MapRendererFactory;
import net.b5gamer.b5wars.ui.map.RenderedHexMap;
import net.b5gamer.b5wars.ui.map.RenderedMap;
import net.b5gamer.map.Map;
import net.b5gamer.map.hex.Hex;
import net.b5gamer.util.StatusEvent;
import net.b5gamer.util.StatusListener;

public class MapControl extends JPanel implements ComponentListener, MouseListener, MouseMotionListener, MouseWheelListener, ActionListener {

	private static final long serialVersionUID = -6182714849504166749L;

	private static final int MARGIN = 20;
	
	public static final String ACTION_TOGGLE_ANNOTATION = "Toggle Annotation";
	public static final String ACTION_TOGGLE_GRID       = "Toggle Grid";
	public static final String ACTION_TOGGLE_BACKDROP   = "Toggle Backdrop";
	public static final String ACTION_TOGGLE_OVERVIEW   = "Toggle Overview";
	
	public static final String STATUS_TURN     = "Turn";
	public static final String STATUS_LOCATION = "Location";
	
	protected Map                  map                 = null; 
	protected RenderedMap[]        renderedMaps        = null;        
	protected int                  renderedMapIndex    = 0;
	protected RenderedMap          renderedMap         = null;        
	protected int                  mapWidth            = 0;     // width of the map image
	protected int                  mapHeight           = 0;     // height of the map image
	protected int                  mapOffsetX          = 0;
	protected int                  mapOffsetY          = 0;
	protected int                  mapPressX           = 0;     // x position where the user pressed on the map image (includes map offset)
	protected int                  mapPressY           = 0;     // y position where the user pressed on the map image (includes map offset)
	protected int                  screenPressX        = 0;     // x position where the user pressed on the map control
	protected int                  screenPressY        = 0;     // y position where the user pressed on the map control
	protected boolean              dispayAnnotation    = true;
	protected boolean              dispayGrid          = true;
	protected boolean              dispayBackdrop      = false;
	protected boolean              dispayOverview      = false;
	protected Color                annotationColor     = Color.white; // color to render map annotations in
	protected Color                gridColor           = Color.white; // color to render the map grid in
	protected Color                backgroundColor     = Color.black; // color for the map background
	protected Color                borderColor         = Color.white;
	protected int                  overviewOffset      = 5;	
	protected int                  overviewMaxSize     = 140;
	protected int                  overviewBorderSize  = 1;
	protected Color                overviewBoxColor    = Color.red;
	protected Rectangle            overviewBounds      = null;
	protected List<StatusListener> statusListeners     = new ArrayList<StatusListener>(0);
    
	
	
	
	Hex   hexAtCursor = null;
    Image draziSunhawk   = null;
    Image draziWarbird   = null;
    Image draziStarSnake = null;
    Image brakiriAvioki = null;
    Image asteroids1    = null;
    Image asteroids2    = null;
    Image asteroids3    = null;
    BufferedImage arc = null;

    
    
	public MapControl() {
		this(null);
	}

	public MapControl(Map map) {
		super(new BorderLayout(), true);
		addComponentListener(this);
		addMouseListener(this);
		addMouseMotionListener(this);
		addMouseWheelListener(this);
//		setToolTipText("<html>"+"This is a"+"<br>"+"tool tip"+"</html>");
		
		setMap(map);
		
		
		
		
		try {
			draziSunhawk  = ImageIO.read(RenderedMap.class.getResourceAsStream("Drazi Sunhawk.png"));
			draziWarbird  = ImageIO.read(RenderedMap.class.getResourceAsStream("Drazi Warbird.png"));
			draziStarSnake = ImageIO.read(RenderedMap.class.getResourceAsStream("Drazi Star Snake.png"));
			brakiriAvioki = ImageIO.read(RenderedMap.class.getResourceAsStream("Brakiri Avioki.png"));
			asteroids1 = ImageIO.read(RenderedMap.class.getResourceAsStream("asteroids1.png"));
			asteroids2 = ImageIO.read(RenderedMap.class.getResourceAsStream("asteroids2.png"));
			asteroids3 = ImageIO.read(RenderedMap.class.getResourceAsStream("asteroids3.png"));
			
//			arc = ArcRenderer.render(Arc.FWD_60, new Color(Integer.parseInt("000020", 16)), 8000, 8000);
		} catch (Exception e) {
			e.printStackTrace();
		}


		
	}

	public Map getMap() {
		return map;
	}

	public void setMap(Map map) {
		this.map = map;
		
		if (map != null) {
			MapRenderer renderer = new MapRendererFactory().getMapRenderer(map);
			renderer.setAnnotationColor(getAnnotationColor());
			renderer.setGridColor(getGridColor());
			setRenderedMaps(renderer.renderAtAllScales(MARGIN));
			setRenderedMapIndex(0);
//			setRenderedMap(renderer.renderAtScale(1.0, MARGIN));
		} else {
			setRenderedMaps(null);
		}
		
		setMapOffset(0, 0);
		calculateOverviewBounds();
	}

	protected RenderedMap[] getRenderedMaps() {
		return renderedMaps;
	}

	protected void setRenderedMaps(RenderedMap[] renderedMaps) {
		this.renderedMaps = renderedMaps;
	}
	
	protected int getRenderedMapIndex() {
		return renderedMapIndex;
	}

	protected void setRenderedMapIndex(int renderedMapIndex) {
		if (getRenderedMaps() != null) {
			renderedMapIndex = (renderedMapIndex < 0) ? 0 : renderedMapIndex;
			renderedMapIndex = (renderedMapIndex >= getRenderedMaps().length) ? getRenderedMaps().length - 1 : renderedMapIndex;
			setRenderedMap(getRenderedMaps()[renderedMapIndex]);
		} else {
			renderedMapIndex = 0;
		}

		this.renderedMapIndex = renderedMapIndex;
	}

	protected RenderedMap getRenderedMap() {
		return renderedMap;
	}

	protected void setRenderedMap(RenderedMap renderedMap) {
		this.renderedMap = renderedMap;
		setMapWidth(renderedMap.getGridImage().getWidth(this));
		setMapHeight(renderedMap.getGridImage().getHeight(this));
	}
	
	protected int getMapWidth() {
		return mapWidth;
	}

	protected void setMapWidth(int mapWidth) {
		this.mapWidth = mapWidth;
	}

	protected int getMapHeight() {
		return mapHeight;
	}

	protected void setMapHeight(int mapHeight) {
		this.mapHeight = mapHeight;
	}

	protected int getMapOffsetX() {
		return mapOffsetX;
	}

	protected void setMapOffsetX(int offset) {
		if (getMapWidth() > getWidth()) {
			offset = (offset > 0) ? 0 : offset;
			offset = (int) ((offset < (getMapWidth() - getWidth()) * -1) ? (getMapWidth() - getWidth()) * -1 : offset);
		} else {			
			offset = (offset > getWidth() - getMapWidth()) ? getWidth() - getMapWidth() : offset;
			offset = (offset < 0) ? 0 : offset;
		}
		
		this.mapOffsetX = offset;
	}

	protected int getMapOffsetY() {
		return mapOffsetY;
	}

	protected void setMapOffsetY(int offset) {
		if (getMapHeight() > getHeight()) {
	        offset = (offset > 0) ? 0 : offset;
			offset = (int) ((offset < (getMapHeight() - getHeight()) * -1) ? (getMapHeight() - getHeight()) * -1 : offset);
		} else {			
			offset = (offset > getHeight() - getMapHeight()) ? getHeight() - getMapHeight() : offset;
			offset = (offset < 0) ? 0 : offset;
		}
		
        this.mapOffsetY = offset;
	}

	protected void setMapOffset(final int xOffset, final int yOffset) {
    	setMapOffsetX(xOffset);
    	setMapOffsetY(yOffset);
        repaint();
    } 

	protected void setMapOffsetFromOverview(int clickX, int clickY) {
		double x = clickX - (getOverviewOffset() + getOverviewBorderSize());
		double y = clickY - (getOverviewOffset() + getOverviewBorderSize());
	
		setMapOffset((int) ((x / getOverviewBounds().width) * (getMapWidth() * -1.0) + (getWidth() / 2.0)), 
				(int) ((y / getOverviewBounds().height) * (getMapHeight() * -1.0) + (getHeight() / 2.0)));
	}
	
	protected int getMapPressX() {
		return mapPressX;
	}

	protected void setMapPressX(int mapPressX) {
		this.mapPressX = mapPressX;
	}

	protected int getMapPressY() {
		return mapPressY;
	}

	protected void setMapPressY(int mapPressY) {
		this.mapPressY = mapPressY;
	}
    
	protected int getScreenPressX() {
		return screenPressX;
	}

	protected void setScreenPressX(int screenPressX) {
		this.screenPressX = screenPressX;
	}

	protected int getScreenPressY() {
		return screenPressY;
	}

	protected void setScreenPressY(int screenPressY) {
		this.screenPressY = screenPressY;
	}

	public boolean isDispayAnnotation() {
		return dispayAnnotation;
	}

	public void setDispayAnnotation(boolean dispayAnnotation) {
		this.dispayAnnotation = dispayAnnotation;
	}

	public boolean isDispayGrid() {
		return dispayGrid;
	}

	public void setDispayGrid(boolean dispayGrid) {
		this.dispayGrid = dispayGrid;
	}

	public boolean isDispayBackdrop() {
		return dispayBackdrop;
	}

	public void setDispayBackdrop(boolean dispayBackdrop) {
		this.dispayBackdrop = dispayBackdrop;
	}

	public boolean isDispayOverview() {
		return dispayOverview;
	}

	public void setDispayOverview(boolean dispayOverview) {
		this.dispayOverview = dispayOverview;
	}
    
	public Color getAnnotationColor() {
		return annotationColor;
	}

	public void setAnnotationColor(Color annotationColor) {
		this.annotationColor = annotationColor;
	}

	public Color getGridColor() {
		return gridColor;
	}

	public void setGridColor(Color gridColor) {
		this.gridColor = gridColor;
	}
	
	public Color getBackgroundColor() {
		return backgroundColor;
	}

	public void setBackgroundColor(Color backgroundColor) {
		this.backgroundColor = backgroundColor;
	}

	public Color getBorderColor() {
		return borderColor;
	}

	public void setBorderColor(Color borderColor) {
		this.borderColor = borderColor;
	}

	public int getOverviewOffset() {
		return overviewOffset;
	}

	public void setOverviewOffset(int overviewOffset) {
		this.overviewOffset = overviewOffset;
	}

	public int getOverviewMaxSize() {
		return overviewMaxSize;
	}

	public void setOverviewMaxSize(int overviewMaxSize) {
		this.overviewMaxSize = overviewMaxSize;
	}

	public int getOverviewBorderSize() {
		return overviewBorderSize;
	}

	public void setOverviewBorderSize(int overviewBorderSize) {
		this.overviewBorderSize = (overviewBorderSize < 1) ? 1 : overviewBorderSize;
	}

	public Color getOverviewBoxColor() {
		return overviewBoxColor;
	}

	public void setOverviewBoxColor(Color overviewBoxColor) {
		this.overviewBoxColor = overviewBoxColor;
	}

	protected Rectangle getOverviewBounds() {
		return overviewBounds;
	}

	protected void setOverviewBounds(Rectangle overviewBounds) {
		this.overviewBounds = overviewBounds;
	}

	protected void calculateOverviewBounds() {
    	int width  = getOverviewMaxSize();
    	int height = getOverviewMaxSize();

    	if ((getMapWidth() != 0) && (getMapHeight() != 0)) {
    		width  = (getMapWidth() > getMapHeight()) ? getOverviewMaxSize() : getOverviewMaxSize() * getMapWidth() / getMapHeight();
    		height = (getMapWidth() > getMapHeight()) ? getOverviewMaxSize() * getMapHeight() / getMapWidth() : getOverviewMaxSize();
    	}
    	
    	setOverviewBounds(new Rectangle(
    			getOverviewOffset() + getOverviewBorderSize(), 
    			getOverviewOffset() + getOverviewBorderSize(), 
    			width, 
    			height));
	}
	
	public void paint(final Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
		// fill map
		graphics.setColor(getBackgroundColor());
		graphics.fillRect(0, 0, getBounds().width, getBounds().height);
		
		if ((getMap() != null) && (getRenderedMap() != null)) {
			// draw map backdrop
			if (isDispayBackdrop() && getRenderedMap().getBackdropImage() != null) {
				graphics.drawImage(getRenderedMap().getBackdropImage(), getMapOffsetX(), getMapOffsetY(), this);
//				graphics.drawImage(getRenderedMap().getBackdropImage(), 0, 0, getWidth() - 1, getHeight() - 1,
//						getMapOffsetX() * -1, getMapOffsetY() * -1, getMapOffsetX() * -1 + getWidth() - 1, 
//						getMapOffsetY() * -1 + getHeight() - 1, this);
			}
			

			
			
			
//			if (isDispayBackdrop() && getRenderedMap().getBackdropImage() != null) {
//				Point2D point = ((RenderedHexMap) getRenderedMap()).getHexMetrics().getCenter(new Hex(15, 9));
//				
//				AffineTransform transform = graphics.getTransform();
//				graphics.rotate(60 * Math.PI / 180.0, 
//						getMapOffsetX() + getRenderedMap().getMargin() + point.getX(), 
//						getMapOffsetY() + getRenderedMap().getMargin() + point.getY());
//						
////				RescaleOp transparencyOp = new RescaleOp(new float[] {1f, 1f, 1f, 0.5f}, new float[4], null);
//	    		graphics.drawImage(arc, 
////	    				transparencyOp,
//	    				(int) (getMapOffsetX() + getRenderedMap().getMargin() + point.getX() - 4000.0), 
//	    				(int) (getMapOffsetY() + getRenderedMap().getMargin() + point.getY() - 4000.0), null);
////	    		graphics.drawImage(ArcRenderer.renderOutline(Arc.FWD_60, new Color(Integer.parseInt("000020", 16)), 
////	    				new Color(Integer.parseInt("8080CF", 16)), new Color(Integer.parseInt("8080CF", 16)), 1000, 1000, ""),
////	//    				transparencyOp,
////	    				(int) (getMapOffsetX() + getRenderedMap().getMargin() + point.getX() - 500.0), 
////	    				(int) (getMapOffsetY() + getRenderedMap().getMargin() + point.getY() - 500.0), this);
//	
//				graphics.setTransform(transform);
//			}
			
			
			
			
    		
    		
			
			// draw map grid
			if (isDispayGrid() && getRenderedMap().getGridImage() != null) {
				graphics.drawImage(getRenderedMap().getGridImage(), getMapOffsetX(), getMapOffsetY(), this);
//				graphics.drawImage(getRenderedMap().getGridImage(), 0, 0, getWidth() - 1, getHeight() - 1,
//						getMapOffsetX() * -1, getMapOffsetY() * -1, getMapOffsetX() * -1 + getWidth() - 1, 
//						getMapOffsetY() * -1 + getHeight() - 1, this);
			}
			
			// draw map annotations
			if (isDispayAnnotation() && getRenderedMap().getAnnotationImage() != null) {
				graphics.drawImage(getRenderedMap().getAnnotationImage(), getMapOffsetX(), getMapOffsetY(), this);
//				graphics.drawImage(getRenderedMap().getAnnotationImage(), 0, 0, getWidth() - 1, getHeight() - 1,
//						getMapOffsetX() * -1, getMapOffsetY() * -1, getMapOffsetX() * -1 + getWidth() - 1, 
//						getMapOffsetY() * -1 + getHeight() - 1, this);
			}
			
			// draw units on map
	
			
	        if (this.hexAtCursor != null) {
	        	Shape hex = ((RenderedHexMap) getRenderedMap()).getHexMetrics().createHexAsShape(
	        			this.hexAtCursor.getColumn(), this.hexAtCursor.getRow(), 
	        			getMapOffsetX() + MARGIN, getMapOffsetY() + MARGIN);
	            graphics.setColor(Color.red);
	        	graphics.draw(hex);
	        }
			
			
			
//			Point2D point = ((RenderedHexMap) getRenderedMap()).getHexMetrics().getCenter(new Hex(15, 9));
//			
//			AffineTransform transform = graphics.getTransform();
//			graphics.rotate(60 * Math.PI / 180.0, 
//					getMapOffsetX() + getRenderedMap().getMargin() + point.getX(), 
//					getMapOffsetY() + getRenderedMap().getMargin() + point.getY());
//		
//			graphics.drawImage(arc, 
//					(int) (getMapOffsetX() + getRenderedMap().getMargin() + point.getX() - 500.0), 
//					(int) (getMapOffsetY() + getRenderedMap().getMargin() + point.getY() - 500.0), this);
//			
//////			RescaleOp transparencyOp = new RescaleOp(new float[] {1f, 1f, 1f, 0.5f}, new float[4], null);
//////    		graphics.drawImage(arc, 
//////    				transparencyOp,
//////    				(int) (getMapOffsetX() + getRenderedMap().getMargin() + point.getX() - 500.0), 
//////    				(int) (getMapOffsetY() + getRenderedMap().getMargin() + point.getY() - 500.0));
////
//			graphics.setTransform(transform);
			
			
			drawFeature(graphics, draziSunhawk,   new Hex(15, 9), 60);
			drawFeature(graphics, draziSunhawk,   new Hex(16, 11), 60);
			drawFeature(graphics, draziStarSnake, new Hex(15, 10), 60);
			drawFeature(graphics, draziWarbird,   new Hex(17, 13), 60);
			drawFeature(graphics, draziWarbird,   new Hex(21, 14));
			drawFeature(graphics, draziStarSnake, new Hex(25, 12));
		    
			drawFeature(graphics, brakiriAvioki, new Hex(21, 8), 300);
			drawFeature(graphics, brakiriAvioki, new Hex(25, 10), 300);

		    drawFeature(graphics, asteroids1, new Hex(16, 9));
			drawFeature(graphics, asteroids2, new Hex(18, 11));
			drawFeature(graphics, asteroids3, new Hex(22, 12));
			

			
        	
			
		}
		
    	// draw overview map
        if (isDispayOverview()) {
        	Rectangle overviewBounds = getOverviewBounds();
        	
        	// draw overview background
        	graphics.setColor(getBackgroundColor());
        	graphics.fillRect(
        			overviewBounds.x, 
        			overviewBounds.y, 
        			overviewBounds.width + 1, 
        			overviewBounds.height + 1);
        	
        	// draw units in overview map
        	
        	
        	
        	
        	
        	drawFeatureOverview(graphics, draziSunhawk,   new Hex(15, 9));
        	drawFeatureOverview(graphics, draziSunhawk,   new Hex(16, 11));
        	drawFeatureOverview(graphics, draziStarSnake, new Hex(15, 10));
        	drawFeatureOverview(graphics, draziWarbird,   new Hex(17, 13));
        	drawFeatureOverview(graphics, draziWarbird,   new Hex(21, 14));
        	drawFeatureOverview(graphics, draziStarSnake, new Hex(25, 12));
		    
        	drawFeatureOverview(graphics, brakiriAvioki, new Hex(21, 8));
        	drawFeatureOverview(graphics, brakiriAvioki, new Hex(25, 10));

        	drawFeatureOverview(graphics, asteroids1, new Hex(16, 9));
        	drawFeatureOverview(graphics, asteroids2, new Hex(18, 11));
        	drawFeatureOverview(graphics, asteroids3, new Hex(22, 12));
			
        	
        	
        	
        	
        	
        	// draw overview view area
        	graphics.setColor(getOverviewBoxColor());
        	int x1 = (int) (overviewBounds.width * ((getMapOffsetX() * -1.0) / getMapWidth()));
        	x1 = (x1 < 0) ? 0 : x1;
        	int y1 = (int) (overviewBounds.height * ((getMapOffsetY() * -1.0) / getMapHeight()));
        	y1 = (y1 < 0) ? 0 : y1;
        	int x2 = (int) (overviewBounds.width * (getMapOffsetX() * -1.0 + getWidth()) / getMapWidth()) - x1;
        	x2 = (x2 > overviewBounds.width) ? overviewBounds.width : x2;
        	int y2 = (int) (overviewBounds.height * (getMapOffsetY() * -1.0 + getHeight()) / getMapHeight()) - y1;
        	y2 = (y2 > overviewBounds.height) ? overviewBounds.height : y2;
        	graphics.drawRect(x1 + overviewBounds.x, y1 + overviewBounds.y, x2, y2);        			

        	// draw overview border
        	graphics.setColor(getBorderColor());
        	for (int i = 1; i <= getOverviewBorderSize(); i++) {
	        	graphics.drawRect(
	        			overviewBounds.x - i, 
	        			overviewBounds.y - i, 
	        			overviewBounds.width + i * 2, 
	        			overviewBounds.height + i * 2);   
        	}       	
        }
        
        if (this.hexAtCursor != null) {
        	Point2D point = ((RenderedHexMap) getRenderedMap()).getHexMetrics().getCenter(this.hexAtCursor);
            graphics.setColor(Color.red);
            graphics.drawLine((int) (MARGIN + point.getX() + getMapOffsetX() - 2.0), (int) (MARGIN + point.getY() + getMapOffsetY()),       (int) (MARGIN + point.getX() + getMapOffsetX() + 2.0), (int) (MARGIN + point.getY() + getMapOffsetY()));        	
            graphics.drawLine((int) (MARGIN + point.getX() + getMapOffsetX()),       (int) (MARGIN + point.getY() + getMapOffsetY() - 2.0), (int) (MARGIN + point.getX() + getMapOffsetX()),       (int) (MARGIN + point.getY() + getMapOffsetY() + 2.0));        	
        }
        
		// draw border
        graphics.setColor(getBorderColor());
        graphics.drawRect(0, 0, getWidth() - 1, getHeight() - 1);
	}
	
	public void addStatusListener(StatusListener listener) {
		if ((listener != null) && !(statusListeners.contains(listener))) {
			statusListeners.add(listener);
		}
	}
	
	public void removeStatusListener(StatusListener listener) {
		statusListeners.remove(listener);
	}
	
	private void notifyStatusListeners(String key, String value) {
		if (statusListeners.size() > 0) {
			StatusEvent e = new StatusEvent(this, key, value);

			for (Iterator<StatusListener> iterator = statusListeners.iterator(); iterator.hasNext();) {
				iterator.next().statusValueChanged(e);
			}
		}
	}
	
	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		setMapOffset(getMapOffsetX(), getMapOffsetY());
		repaint();
	}

	public void componentShown(ComponentEvent e) {
	}
	
	public void mouseClicked(MouseEvent e) {
		if ((isDispayOverview()) && (getOverviewBounds().contains(e.getX(), e.getY()))) {
			setMapOffsetFromOverview(e.getX(), e.getY());
		}
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
        setScreenPressX(e.getX());
        setScreenPressY(e.getY());
        setMapPressX(e.getX() - getMapOffsetX());
        setMapPressY(e.getY() - getMapOffsetY());
	}

	public void mouseReleased(MouseEvent e) {
	}	

	public void mouseDragged(MouseEvent e) {
		if ((isDispayOverview()) && (getOverviewBounds().contains(getScreenPressX(), getScreenPressY()))) {
			setMapOffsetFromOverview(e.getX(), e.getY());
		} else {
	        setMapOffset(e.getX() - getMapPressX(), e.getY() - getMapPressY());
		}
		
        repaint();
	}

	public void mouseMoved(MouseEvent e) {
		determineMouseLocation(e.getX(), e.getY());
	}

	public void mouseWheelMoved(MouseWheelEvent e) {
		int oldMapWidth  = getMapWidth();
		int oldMapHeight = getMapHeight();
		int oldMargin    = getRenderedMap().getMargin();
		int oldCenterX   = (int) (getMapOffsetX() + oldMargin - (getWidth() / 2.0));
		int oldCenterY   = (int) (getMapOffsetY() + oldMargin - (getHeight() / 2.0));
		
		setRenderedMapIndex(getRenderedMapIndex() + e.getWheelRotation());
		
		int offsetX = (int) (oldCenterX * ((getMapWidth()  - (2.0 * getRenderedMap().getMargin()))  / (oldMapWidth  - (2.0 * oldMargin))) - getRenderedMap().getMargin() + (getWidth() / 2.0));
		int offsetY = (int) (oldCenterY * ((getMapHeight() - (2.0 * getRenderedMap().getMargin()))  / (oldMapHeight - (2.0 * oldMargin))) - getRenderedMap().getMargin() + (getHeight() / 2.0));
		setMapOffset(offsetX, offsetY);
		
		determineMouseLocation(e.getX(), e.getY());

		repaint();
	}

	public void actionPerformed(ActionEvent e) {
		if (e.getActionCommand().equals(ACTION_TOGGLE_ANNOTATION)) {
			setDispayAnnotation(!isDispayAnnotation());
		} else if (e.getActionCommand().equals(ACTION_TOGGLE_GRID)) {
			setDispayGrid(!isDispayGrid());
		} else if (e.getActionCommand().equals(ACTION_TOGGLE_BACKDROP)) {
			setDispayBackdrop(!isDispayBackdrop());
		} else if (e.getActionCommand().equals(ACTION_TOGGLE_OVERVIEW)) {
			setDispayOverview(!isDispayOverview());
		}
		
		repaint();
	}
	
	private void determineMouseLocation(int x, int y) {
		if (getRenderedMap() != null) {
			hexAtCursor = ((RenderedHexMap) getRenderedMap()).getHexMetrics().getHexAtPoint(x - getMapOffsetX() - MARGIN, y - getMapOffsetY() - MARGIN);
			repaint();
			
			if (hexAtCursor != null) {
				notifyStatusListeners(STATUS_LOCATION, hexAtCursor.toString());
			}
		}
	}
	
	// TODO
	private void drawFeature(Graphics2D graphics, Image image, Hex hex) {
		drawFeature(graphics, image, hex, 0.0);		
	}
	
	private void drawFeature(Graphics2D graphics, Image image, Hex hex, double angle) {	
		Point2D point = ((RenderedHexMap) getRenderedMap()).getHexMetrics().getCenter(hex);
		int halfWidth  = (int) (image.getWidth(this) * getRenderedMap().getScale() / 2.0);
		int halfHeight = (int) (image.getHeight(this) * getRenderedMap().getScale() / 2.0);
		
		AffineTransform transform = null;
		if (angle != 0.0) {
			transform = graphics.getTransform();
			graphics.rotate(angle * Math.PI / 180.0, 
					getMapOffsetX() + getRenderedMap().getMargin() + point.getX(), 
					getMapOffsetY() + getRenderedMap().getMargin() + point.getY());
		}
		
		graphics.drawImage(image, 
    			(int) (getMapOffsetX() + getRenderedMap().getMargin() + point.getX() - halfWidth), 
    			(int) (getMapOffsetY() + getRenderedMap().getMargin() + point.getY() - halfHeight), 
    			halfWidth * 2, 
    			halfHeight * 2, this);	
		
		if (transform != null) {
			graphics.setTransform(transform);
		}
	}
	
	// TODO
	private void drawFeatureOverview(Graphics graphics, Image image, Hex hex) {
    	int offset = getOverviewOffset() + getOverviewBorderSize();
		Point2D point = ((RenderedHexMap) getRenderedMap()).getHexMetrics().getCenter(hex);
		double factor = overviewBounds.getWidth() / getMapWidth();
		graphics.drawImage(image, 
				(int) (offset + (getRenderedMap().getMargin() + point.getX() - (image.getWidth(this) * getRenderedMap().getScale()) / 2.0) * factor), 
				(int) (offset + (getRenderedMap().getMargin() + point.getY() - (image.getHeight(this) * getRenderedMap().getScale()) / 2.0) * factor), 
    			(int) (image.getWidth(this) * getRenderedMap().getScale() * factor), 
    			(int) (image.getHeight(this) * getRenderedMap().getScale() * factor), this);		
	}
	
}
