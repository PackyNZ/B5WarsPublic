/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.util;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;

/**
 * This class provides utility methods for manipulating images
 *
 * @author Alex Packwood (aka PackyNZ)
 */
public final class ImageUtil {

	private ImageUtil() {
	}
	
	/**
	 * create a new image and center the specified image within it's extents
     *
     * @param  image  the image to center within the new image
     * @param  width  width of the new image
     * @param  height height of the new image
     * @return        a new image
	 */
	public final static Image centerWithinNewImage(final Image image, final int width, final int height) {
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);        
        Graphics2D    graphics = newImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING,    RenderingHints.VALUE_RENDER_QUALITY);
		
        graphics.drawImage(image, 
        		(int) ((width / 2.0) - (image.getWidth(null) / 2.0)),
        		(int) ((height / 2.0) - (image.getHeight(null) / 2.0)),
        		image.getWidth(null), image.getHeight(null), null);
       
        graphics.dispose();
        
        return newImage;
	}
	
	public final static Image resizeImage(final Image image, final int width, final int height) {
		BufferedImage newImage = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);        
        Graphics2D    graphics = newImage.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        graphics.setRenderingHint(RenderingHints.KEY_RENDERING,    RenderingHints.VALUE_RENDER_QUALITY);
		
        graphics.drawImage(image, 0, 0, width, height, null);
        
        graphics.dispose();
        
        return newImage;
	}
		
	/**
	 * make a specific colour of an image transparent, which must have an Alpha channel (e.g. be of
	 * type BufferedImage.TYPE_INT_ARGB)
     *
     * @param image  image to set the transparency of, must have an Alpha channel
     * @param colour Color to be made transparent
	 */
	public final static void setTransparentColour(BufferedImage image, final Color colour) throws IllegalArgumentException {
        if (image == null) {
            throw new IllegalArgumentException("image cannot be null");
        }
        if (colour == null) {
            throw new IllegalArgumentException("colour cannot be null");
        }
        
		for (int i = 0; i < image.getHeight(); i++) {
			for (int j = 0; j < image.getWidth(); j++) {
				if (image.getRGB(j, i) == colour.getRGB()) {
					image.setRGB(j, i, 0x8F1C1C);
				}
			}
		}
	}
		
}
