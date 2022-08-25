/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.b5wars.ui.render;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Arc2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

import net.b5gamer.map.Arc;
import net.b5gamer.util.ImageUtil;

/**
 * The ArcRenderer is used to render an image containing a graphical representation of an arc
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public final class ArcRenderer {
	
	private static final Color DEFAULT_ARC_COLOR = Color.gray;
	
	private static Image hexClusterImage  = null; // image used to overlay the arc
	private static int   hexClusterWidth  = 0;    // width of the overlay image
	private static int   hexClusterHeight = 0;    // height of the overlay image
	
	private ArcRenderer() {
	}
	
	static {
		try {
			hexClusterImage  = ImageUtil.centerWithinNewImage(HexRenderer.renderHexCluster(6, Color.black), 50, 50);
			hexClusterWidth  = (hexClusterImage == null) ? 0 : hexClusterImage.getWidth(null);
			hexClusterHeight = (hexClusterImage == null) ? 0 : hexClusterImage.getHeight(null);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * the width of the overlay image
	 * 
	 * @return the width of the overlay image
	 */
	public static final int getOverlayWidth() {
		return hexClusterWidth;
	}

	/**
	 * the height of the overlay image
	 * 
	 * @return the height of the overlay image
	 */
	public static final int getOverlayHeight() {
		return hexClusterHeight;
	}

	/**
     * render an image containing a graphical representation of the given arc
     * 
     * @param  arc arc to render an image of
     * @return     an image representing the arc
     */
	public static final BufferedImage render(final Arc arc) {
		return render(arc, DEFAULT_ARC_COLOR);
	}

	/**
     * render an image containing a graphical representation of the given arc using the overlay image
     * 
     * @param  arc   arc to render an image of
     * @param  color color to render the arc in
     * @return       an image representing the arc
     */
	public static final BufferedImage render(final Arc arc, final Color color) {
		if (arc == null) {
            throw new IllegalArgumentException("Arc cannot be null");
		}
		
        BufferedImage arcImage    = new BufferedImage(getOverlayWidth(), getOverlayHeight(), BufferedImage.TYPE_INT_ARGB);       
        Graphics2D    arcGraphics = arcImage.createGraphics();
		arcGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		arcGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // convert angle from Arc to Graphic2D.fillArc()
        // Arc: angle 0 facing top, positive degrees clockwise
        // Graphic2D.fillArc(): angle 0 facing right, positive degrees counterclockwise
        int angle = 360 - (arc.getFromAngle() + arc.getDegrees() - 90); 
        
        // render arc
        arcGraphics.setColor((color != null) ? color : DEFAULT_ARC_COLOR);
        arcGraphics.fillArc(2, 2, getOverlayWidth() - 4, getOverlayHeight() - 4, angle, arc.getDegrees());
        
        // render overlay image
    	arcGraphics.drawImage(hexClusterImage, 0, 0, null);
        
		arcGraphics.dispose();
				
		return arcImage;
	}
	
	/**
     * render an image containing a graphical representation of the given arc
     * 
     * @param  arc    arc to render an image of
     * @param  width  whether to use the overlay image
     * @param  height whether to use the overlay image
     * @return        an image representing the arc
     */
	public static final BufferedImage render(final Arc arc, final int width, final int height) {
		return render(arc, DEFAULT_ARC_COLOR, width, height);
	}

	/**
	 * render an image containing a graphical representation of the given arc at a given size
     * 
     * @param  arc    arc to render an image of
     * @param  color  color to render the arc in
     * @param  width  whether to use the overlay image
     * @param  height whether to use the overlay image
     * @return        an image representing the arc
     */
	public static final BufferedImage render(final Arc arc, final Color color, final int width, final int height) {
		if (arc == null) {
            throw new IllegalArgumentException("Arc cannot be null");
		}
        if (!(width > 0)) {
            throw new IllegalArgumentException("Width must be a positive number");
        } 
        if (!(height > 0)) {
            throw new IllegalArgumentException("Height must be a positive number");
        } 
		
        BufferedImage arcImage    = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);       
        Graphics2D    arcGraphics = arcImage.createGraphics();
		arcGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		arcGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // convert angle from Arc to Graphic2D.fillArc()
        // Arc: angle 0 facing top, positive degrees clockwise
        // Graphic2D.fillArc(): angle 0 facing right, positive degrees counterclockwise
        int angle = 360 - (arc.getFromAngle() + arc.getDegrees() - 90); 
        
        // render arc
        arcGraphics.setColor((color != null) ? color : DEFAULT_ARC_COLOR);
        arcGraphics.fillArc(4, 4, width - 8, height - 8, angle, arc.getDegrees());
                
		arcGraphics.dispose();
				
		return arcImage;
	}
	
	/**
	 * render an image containing a graphical representation of the given arc at a given size
     * 
     * @param  arc    arc to render an image of
     * @param  fill   color to render the arc in
     * @param  width  whether to use the overlay image
     * @param  height whether to use the overlay image
     * @return        an image representing the arc
     */
	public static final BufferedImage renderOutline(final Arc arc, final Color fill, final Color outline, 
			final Color text, final int width, final int height, final String label) {
		if (arc == null) {
            throw new IllegalArgumentException("Arc cannot be null");
		}
        if (!(width > 0)) {
            throw new IllegalArgumentException("Width must be a positive number");
        } 
        if (!(height > 0)) {
            throw new IllegalArgumentException("Height must be a positive number");
        } 
		
        BufferedImage arcImage    = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);       
        Graphics2D    arcGraphics = arcImage.createGraphics();
		arcGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		arcGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        // convert angle from Arc to Graphic2D.fillArc()
        // Arc: angle 0 facing top, positive degrees clockwise
        // Graphic2D.fillArc(): angle 0 facing right, positive degrees counterclockwise
        // int angle = 360 - (arc.getFromAngle() + arc.getDegrees() - 90); 
        
        // render arc
        arcGraphics.setColor((fill != null) ? fill : DEFAULT_ARC_COLOR);
        Arc2D shape = arc.toShape(width - 8, height - 8);
//        arcGraphics.fillArc(4, 4, width - 8, height - 8, angle, arc.getDegrees());

        arcGraphics.setColor(outline);
        arcGraphics.draw(shape);
        Rectangle2D bounds = shape.getBounds2D();
        
        arcGraphics.setColor(text);
        Font font = new Font("Verdana", Font.PLAIN, 14);	        
        FontMetrics fontMetrics = arcGraphics.getFontMetrics(font);
        Rectangle2D labelBounds = fontMetrics.getStringBounds(label, arcGraphics);
        arcGraphics.setFont(font);
        
        int fudgeFactor = 15;
        int x = (int) (bounds.getX() + (bounds.getWidth() / 2));
        int y = (int) (bounds.getY() + (bounds.getHeight() / 2));
        if (!(((x + fudgeFactor < width / 2) || (x - fudgeFactor > width / 2)) && 
        		((y + fudgeFactor < height / 2) || (y - fudgeFactor > height / 2)))) {
	        if (x + fudgeFactor < width / 2) {
	        	x -= width / 12;
	        } else if (x - fudgeFactor > width / 2) {
	        	x += width / 12;
	        }
	        if (y + fudgeFactor < height / 2) {
	        	y -= height / 12;
	        } else if (y - fudgeFactor > height / 2) {
	        	y += height / 12;
	        }
        }
        arcGraphics.drawString(label, (int) (x - labelBounds.getWidth() / 2), (int) (y + labelBounds.getHeight() / 2));
        
		arcGraphics.dispose();
				
		return arcImage;
	}	

}
