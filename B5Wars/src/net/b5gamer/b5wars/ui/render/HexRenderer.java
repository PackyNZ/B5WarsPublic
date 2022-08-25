package net.b5gamer.b5wars.ui.render;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

import net.b5gamer.map.hex.HexMetrics;

public class HexRenderer {

	private HexRenderer() {
	}
	
	/**
	 * render an image of a hex cluster, which is 6 hexes surrounding a central hex
	 * 
	 * @param size
	 * @return
	 */
	public static Image renderHexCluster(int hexRadius, Color hexColor) {
		// determine size of hexes and resulting image
		HexMetrics metrics = new HexMetrics(hexRadius);
		int width  = (int) ((metrics.getHexWidth() * 2) + metrics.getHexSide() + 1) + 2;
		int height = (int) (metrics.getHexHeight() * 3 + 1) + 2;

		// create image
        BufferedImage hexCluster = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);        
        Graphics2D    graphics   = hexCluster.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
		
        // draw hexes
		graphics.setColor(hexColor);
		double offset = (metrics.getHexHeight() / 2.0) + 1.0;
		graphics.draw(metrics.createHexAsShape(1, 1, 1, offset));
        graphics.draw(metrics.createHexAsShape(1, 2, 1, offset));
        graphics.draw(metrics.createHexAsShape(2, 0, 1, offset));
        graphics.draw(metrics.createHexAsShape(2, 1, 1, offset));
        graphics.draw(metrics.createHexAsShape(2, 2, 1, offset));
        graphics.draw(metrics.createHexAsShape(3, 1, 1, offset));
        graphics.draw(metrics.createHexAsShape(3, 2, 1, offset));
        graphics.dispose();
        
        return hexCluster;        
	}
	
}
