package net.b5gamer.b5wars.ui.map;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.geom.Rectangle2D;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class MapViewPortal extends JPanel implements MouseWheelListener {

	private static final long serialVersionUID = 8916503413585622566L;

	private static final int MAX_OFFSET = 0;
    private static int           mapViewMouseClickX1 = 0;
    private static int           mapViewMouseClickY1 = 0;
	
    private Image  mapSurface = null;
    private int    offsetX    = 0;
    private int    offsetY    = 0;
    private double scale      = 1.0;

    
    Image shipImage = null;
    Image shipImage2 = null;
    

    public MapViewPortal() {
        super(true);
        
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
			public void mouseDragged(MouseEvent e) {
				MapView_mouseDragged(e);
			}

			public void mouseMoved(MouseEvent e) {
				MapView_mouseMoved(e);
			}
		});

		MouseAdapter ma = new java.awt.event.MouseAdapter() {
			public void mouseClicked(MouseEvent e) {
				MapView_mouseClicked(e);
			}

			public void mousePressed(MouseEvent e) {
				MapView_mousePressed(e);
			}

			public void mouseReleased(MouseEvent e) {
				MapView_mouseReleased(e);
			}
		};
		
		addMouseListener(ma);
        addMouseWheelListener(this);

        
		try {
			shipImage  = ImageIO.read(this.getClass().getResourceAsStream("ship4.png"));
			shipImage2 = ImageIO.read(this.getClass().getResourceAsStream("ship5.png"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
    } 

    public Image getMapSurface() {
        return mapSurface;
    }

    public void setMapSurface(final Image mapSurface) {
        if (mapSurface == null) {
            throw new IllegalArgumentException("MapSurface cannot be null");
        } 

        this.mapSurface = mapSurface;
    } 

    public int getOffsetX() {
		return offsetX;
	}

	public void setOffsetX(int offset) {
		offset = (offset > MAX_OFFSET) ? MAX_OFFSET : offset;
		offset = (int) ((offset < (MAX_OFFSET + mapSurface.getWidth(this) * getScale() - this.getWidth()) * -1) ? (MAX_OFFSET + mapSurface.getWidth(this) * getScale() - this.getWidth()) * -1 : offset);
			
		this.offsetX = offset;
	}

	public int getOffsetY() {
		return offsetY;
	}

	public void setOffsetY(int offset) {
        offset = (offset > MAX_OFFSET) ? MAX_OFFSET : offset;
		offset = (int) ((offset < (MAX_OFFSET + mapSurface.getHeight(this) * getScale() - this.getHeight()) * -1) ? (MAX_OFFSET + mapSurface.getHeight(this) * getScale() - this.getHeight()) * -1 : offset);

        this.offsetY = offset;
	}

    public void setOffset(final int offsetX, final int offsetY) {
    	setOffsetX(offsetX);
    	setOffsetY(offsetY);
    } 

    public double getScale() {
		return scale;
	}

    public void setScale(double scale) {
    	double oldScale = this.scale;
    	
    	if (scale < 0.1) {
    		this.scale = 0.1;
    	} else if (scale > 2.0) {
    		this.scale = 2.0;
    	} else {
    		this.scale = scale;
    	}
    	
    	// 42x30
    	
    	// adjust offset for new scale
    	// TODO fix
    	int centerX = this.getWidth() / 2 - getOffsetX();
    	int centerY = this.getHeight() / 2 - getOffsetY();
    	double change = (oldScale - this.scale);
    	setOffset((int) ((centerX * change)), (int) ((centerY * change)));
	}

	public void paint(final Graphics g) {
		Graphics2D g2D = (Graphics2D) g;
		g2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		g2D.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
        if (mapSurface != null) {
        	g2D.setColor(Color.black);
        	g2D.fillRect(0, 0, getBounds().width, getBounds().height);

//        	g2D.drawImage(shipImage, 
//        			(int) (getOffsetX() + 260 - shipImage.getWidth(this) * 1.0 / 2.0), 
//        			(int) (getOffsetY() + 275 - shipImage.getHeight(this) * 1.0 / 2.0), 
//        			(int) (shipImage.getWidth(this) * 1.0), 
//        			(int) (shipImage.getHeight(this) * 1.0), this);

//            g.drawImage(mapSurface, getOffsetX(), getOffsetY(), this);
        	g2D.drawImage(mapSurface, getOffsetX(), getOffsetY(), 
            		(int) (mapSurface.getWidth(this) * getScale()), (int) (mapSurface.getHeight(this) * getScale()), this);
//        	g2D.drawImage(mapSurface, 0, 0, getWidth(), getHeight(), 
//        			getOffsetX() * -1, getOffsetY() * -1, 
//            		(int) ((getOffsetX() * -1 + getWidth()) * getScale()), 
//            		(int) (getOffsetY() * -1 + getHeight() * getScale()), this);
            
            
//        	g2D.drawImage(hexImage, getOffsetX(), getOffsetY(), 
//        		  (int) (hexImage.getWidth(this) * getScale())),
//        		  (int) (hexImage.getHeight(this) * getScale()), this);
//        	g2D.drawImage(shipImage, 
//        		  (int) (getOffsetX() + (hexImage.getWidth(this) / 2 - shipImage.getWidth(this) / 2) * getScale()), 
//        		  (int) (getOffsetY() + (hexImage.getHeight(this) / 2 - shipImage.getHeight(this) / 2) * getScale()), 
//        		  (int) (shipImage.getWidth(this) * getScale()),
//        		  (int) (shipImage.getHeight(this) * getScale()), this);
           
        	g2D.drawImage(shipImage, 
        			(int) (getOffsetX() + (260 - shipImage.getWidth(this) / 2.0) * getScale()), 
        			(int) (getOffsetY() + (275 - shipImage.getHeight(this) / 2.0) * getScale()), 
        			(int) (shipImage.getWidth(this) * getScale()), 
        			(int) (shipImage.getHeight(this) * getScale()), this);
        	g2D.drawImage(shipImage2, 
        			(int) (getOffsetX() + (2510 - shipImage2.getWidth(this) / 2.0) * getScale()), 
        			(int) (getOffsetY() + (1142 - shipImage2.getHeight(this) / 2.0) * getScale()), 
        			(int) (shipImage2.getWidth(this) * getScale()), 
        			(int) (shipImage2.getHeight(this) * getScale()), this);
        	g2D.drawImage(shipImage2, 
        			(int) (getOffsetX() + (2690 - shipImage2.getWidth(this) / 2.0) * getScale()), 
        			(int) (getOffsetY() + (734 - shipImage2.getHeight(this) / 2.0) * getScale()), 
        			(int) (shipImage2.getWidth(this) * getScale()), 
        			(int) (shipImage2.getHeight(this) * getScale()), this);
        	
//        	g2D.drawImage(shipImage, 
//        			(int) (260 - shipImage.getWidth(this) * 0.2 / 2.0), 
//        			(int) (275 - shipImage.getHeight(this) * 0.2 / 2.0), 
//        			(int) (shipImage.getWidth(this) * 0.2), 
//        			(int) (shipImage.getHeight(this) * 0.2), this);
//        	g2D.rotate(60.0 * Math.PI / 180.0, 260, 275);
//        	g2D.drawImage(shipImage, 
//        			(int) (260 - shipImage.getWidth(this) * 1.0 / 2.0), 
//        			(int) (375 - shipImage.getHeight(this) * 1.0 / 2.0), 
//        			(int) (shipImage.getWidth(this) * 1.0), 
//        			(int) (shipImage.getHeight(this) * 1.0), this);
        	
//        	try {
//				g2D.drawImage(rotate(ImageIO.read(this.getClass().getResourceAsStream("ship4.png")), 60), 
//						(int) (260 - shipImage.getWidth(this) * 1.0 / 2.0), 
//						(int) (375 - shipImage.getHeight(this) * 1.0 / 2.0), 
//						(int) (shipImage.getWidth(this) * 1.0), 
//						(int) (shipImage.getHeight(this) * 1.0), this);
//			} catch (IOException e) {
//				e.printStackTrace();
//			}

        	// draw overview map
        	int width  = (mapSurface.getWidth(this) > mapSurface.getHeight(this)) ? 160 : 160 * mapSurface.getWidth(this) / mapSurface.getHeight(this);
        	int height = (mapSurface.getWidth(this) > mapSurface.getHeight(this)) ? 160 * mapSurface.getHeight(this) / mapSurface.getWidth(this) : 160;
        	g2D.setColor(Color.black);
        	g2D.fillRect(4, 4, width + 2, height + 2);

        	g2D.drawImage(shipImage, 
        			5 + (int) ((260 - shipImage.getWidth(this) / 2.0) * width / mapSurface.getWidth(this)), 
        			5 + (int) ((275 - shipImage.getHeight(this) / 2.0) * height / mapSurface.getHeight(this)), 
        			(int) (shipImage.getWidth(this) * width / mapSurface.getWidth(this)), 
        			(int) (shipImage.getHeight(this) * height / mapSurface.getHeight(this)), this);        	
        	g2D.drawImage(shipImage2, 
        			5 + (int) ((2510 - shipImage2.getWidth(this) / 2.0) * width / mapSurface.getWidth(this)), 
        			5 + (int) ((1142 - shipImage2.getHeight(this) / 2.0) * height / mapSurface.getHeight(this)), 
        			(int) (shipImage2.getWidth(this) * width / mapSurface.getWidth(this)), 
        			(int) (shipImage2.getHeight(this) * height / mapSurface.getHeight(this)), this);        	
        	g2D.drawImage(shipImage2, 
        			5 + (int) ((2690 - shipImage2.getWidth(this) / 2.0) * width / mapSurface.getWidth(this)), 
        			5 + (int) ((734 - shipImage2.getHeight(this) / 2.0) * height / mapSurface.getHeight(this)), 
        			(int) (shipImage2.getWidth(this) * width / mapSurface.getWidth(this)), 
        			(int) (shipImage2.getHeight(this) * height / mapSurface.getHeight(this)), this);        	
        	
//        	g2D.setColor(Color.red);
        	g2D.setColor(new Color(170, 0, 0));
        	int x1 = (int) (width * ((getOffsetX() * -1.0) / (mapSurface.getWidth(this) * getScale())));
        	int y1 = (int) (height * ((getOffsetY() * -1.0) / (mapSurface.getHeight(this) * getScale())));
        	int x2 = (int) (width * (getOffsetX() * -1.0 + getWidth()) / (mapSurface.getWidth(this) * getScale())) - x1;
        	int y2 = (int) (height * (getOffsetY() * -1.0 + getHeight()) / (mapSurface.getHeight(this) * getScale())) - y1;
        	g2D.drawRect(((x1 < 1) ? 1 : x1) + 5, ((y1 < 1) ? 1 : y1) + 5, (x2 >= width) ? width - 1 : x2, (y2 >= height) ? height - 1 : y2);        			

//        	g2D.setColor(Color.white);
        	g2D.setColor(new Color(64, 64, 128));
        	g2D.drawRect(5, 5, width + 1, height + 1);        			
        	
//        	g2D.rotate(Math.toRadians(60), getOffsetX() + 170, getOffsetY() + 326);
//        	g2D.drawImage(shipImage, 
//        			(int) (getOffsetX() + 170 - shipImage.getWidth(this) * 1.0 / 2.0), 
//        			(int) (getOffsetY() + 326 - shipImage.getHeight(this) * 1.0 / 2.0),
//        			(int) (shipImage.getWidth(this) * 1.0), 
//        			(int) (shipImage.getHeight(this) * 1.0), this);
        } else {
        	g2D.setColor(Color.black);
        	g2D.fillRect(0, 0, getBounds().width, getBounds().height);
            
        	g2D.setColor(Color.white);
            Font font = new Font("Verdana", Font.PLAIN, 14);
            g2D.setFont(font);
            FontMetrics fontMetrics = g2D.getFontMetrics(font);
            Rectangle2D labelBounds = fontMetrics.getStringBounds("Map Unavailable", g2D);
            g2D.drawString("Map Unavailable", (int) ((getBounds().width - labelBounds.getWidth()) / 2), 
            		(int) ((getBounds().height + labelBounds.getHeight()) / 2));
        } 
    } 

//    BufferedImage baseImage  = ImageIO.read(new URL(getIWSUrl(iwsLayer.getServer(), iwsLayer.getLayer(), getWidth(), getHeight(), MapUtils.getMapBounds(getMapJ()))));
//    BufferedImage transImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_ARGB);  
//    Graphics2D g = transImage.createGraphics();  
//    g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float) (getTargetOpacity() / 100.0)));  
//    g.drawImage(baseImage, null, 0, 0);  
//    g.dispose();  
//    iwsImage.add(new MapImage(transImage));                	
	
//	public static BufferedImage rotate(BufferedImage img, int angle) {
//		int w = img.getWidth();
//		int h = img.getHeight();
//		BufferedImage dimg = new BufferedImage(w, h, img.getType());
//		Graphics2D g = dimg.createGraphics();
//		g.rotate(Math.toRadians(angle), w/2, h/2);
//		g.drawImage(img, null, 0, 0);
//		return dimg;
//	}
	
    /**
     * Mouse dragged on the map view
     */
    private void MapView_mouseDragged(MouseEvent e) {
        int mapViewMouseClickX2 = e.getX();
        int mapViewMouseClickY2 = e.getY();

        setOffset(mapViewMouseClickX2 - mapViewMouseClickX1, mapViewMouseClickY2 - mapViewMouseClickY1);
        repaint();
    }

    /** 
     * Mouse is moved over the map view
     */
    private void MapView_mouseMoved(MouseEvent e) {
    } 
    
    /** 
     * Mouse clicked on the map view
     */
    private void MapView_mouseClicked(MouseEvent e) {
    }

    /**
     * Mouse pressed on the map view
     */
    private void MapView_mousePressed(MouseEvent e) {
        mapViewMouseClickX1 = e.getX() - getOffsetX();
        mapViewMouseClickY1 = e.getY() - getOffsetY();
    } 

    /**
     * Mouse released from the map view
     */
    private void MapView_mouseReleased(MouseEvent e) {
    }

    /**
     * Mouse wheel used on the map view
     */    
	public void mouseWheelMoved(MouseWheelEvent e) {
		setScale(getScale() + ((e.getWheelRotation() / 10.0) * -1.0));
		repaint();
	}
	
} 