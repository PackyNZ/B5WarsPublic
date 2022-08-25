package net.b5gamer.b5wars.ui.map;

import java.awt.Color;

import net.b5gamer.map.Map;

/**
 * A MapRenderer is responsible for rendering a graphical representation of a
 * {@link net.b5gamer.b5wars.map.Map} at different scales
 *  
 * @author Alex Packwood (aka PackyNZ)
 */
public abstract class MapRenderer {

	protected final Map      map;                            // map to render
	protected       Color    annotationColor  = Color.white; // color to render annotations in
	protected       Color    gridColor        = Color.white; // color to render the grid in
	protected final double[] scales;                         // scales the map can be rendered at 
	
	
	/**
	 * @param map    the map to render
	 * @param scales scales the map can be rendered at 
	 */		
	public MapRenderer(final Map map, final double[] scales) {
        if (map == null) {
            throw new IllegalArgumentException("map cannot be null");
        } 
        if ((scales == null) || !(scales.length > 0)) {
            throw new IllegalArgumentException("scales cannot be null or empty");
        } 

        this.map    = map;	
        this.scales = scales;
	}

	/**
	 * the map to render
	 * 
	 * @return the map to render
	 */
	public Map getMap() {
		return map;
	}
	
	/**
	 * the color to render annotations in
	 * 
	 * @return the color to render annotations in
	 */
	public Color getAnnotationColor() {
		return annotationColor;
	}

	/**
	 * the color to render annotations in
	 * 
	 * @param annotationColor the color to render annotations in
	 */
	public void setAnnotationColor(Color annotationColor) {
		this.annotationColor = annotationColor;
	}

	/**
	 * the color to render the grid in
	 * 
	 * @return the color to render the grid in
	 */
	public Color getGridColor() {
		return gridColor;
	}

	/**
	 * the color to render the grid in
	 * 
	 * @param gridColor the color to render the grid in
	 */	
	public void setGridColor(Color gridColor) {
		this.gridColor = gridColor;
	}
	
	/**
	 * the scales the map can be rendered at 
	 * 
	 * @return the scales the map can be rendered at 
	 */	
	public double[] getScales() {
		return scales;
	}
	
	/**
	 * render the map at the default (first) scale
	 * 
	 * @param  margin number of pixels to keep clear around border of the map
	 * @return        the rendered map
	 */	
	public abstract RenderedMap render(int margin);

	/**
	 * render the map at the specified scale
	 * 
	 * @param  scale  scale to render the map at
	 * @param  margin number of pixels to keep clear around border of the map
	 * @return        the rendered map
	 */	
	public abstract RenderedMap renderAtScale(double scale, int margin);

	/**
	 * render the map at all supported scales
	 * 
	 * @param  margin number of pixels to keep clear around border of the map
	 * @return        the rendered map
	 */	
	public abstract RenderedMap[] renderAtAllScales(int margin);
	
}
