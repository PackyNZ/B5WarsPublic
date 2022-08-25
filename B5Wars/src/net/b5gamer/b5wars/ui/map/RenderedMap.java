package net.b5gamer.b5wars.ui.map;

import java.awt.image.BufferedImage;

import net.b5gamer.map.Feature;
import net.b5gamer.map.Map;

public abstract class RenderedMap {

	protected final Map           map;                    // map to render
	protected final double        scale;                  // scale the map was rendered at
	protected final int           margin;                 // number of pixels kept clear around border of the map
	protected       BufferedImage annotationImage = null;
	protected       BufferedImage gridImage       = null;
	protected       BufferedImage backdropImage   = null;

	public RenderedMap(final Map map, final double scale, final int margin) {
        if (map == null) {
            throw new IllegalArgumentException("map cannot be null");
        } 

        this.map    = map;	
        this.scale  = scale;
        this.margin = margin;        
	}

	public Map getMap() {
		return map;
	}

	public double getScale() {
		return scale;
	}
	
	public final int getMargin() {
		return margin;
	}

	public BufferedImage getAnnotationImage() {
		return annotationImage;
	}

	public void setAnnotationImage(BufferedImage annotationImage) {
		this.annotationImage = annotationImage;
	}

	public BufferedImage getGridImage() {
		return gridImage;
	}

	public void setGridImage(BufferedImage gridImage) {
		this.gridImage = gridImage;
	}

	public BufferedImage getBackdropImage() {
		return backdropImage;
	}

	public void setBackdropImage(BufferedImage backdropImage) {
		this.backdropImage = backdropImage;
	}
	
	public abstract Feature[] getFeaturesAtPoint(int x, int y);
	
}
