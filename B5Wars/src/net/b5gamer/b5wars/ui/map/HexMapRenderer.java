package net.b5gamer.b5wars.ui.map;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.text.NumberFormat;

import javax.imageio.ImageIO;

import net.b5gamer.map.BoundaryType;
import net.b5gamer.map.hex.HexMap;
import net.b5gamer.map.hex.HexMetrics;

/**
 * A HexMapRenderer is responsible for rendering a graphical representation of a
 * {@link net.b5gamer.b5wars.map.hex.HexMap}
 *  
 * @author Alex Packwood (aka PackyNZ)
 */
public class HexMapRenderer extends MapRenderer {

	private static final double[] scales     = {1.0, 0.75, 0.5, 0.25};
	private static final int[]    hex_sizes  = {60,  45,   30,  15};
	private static final int[]    font_sizes = {14,  11,   8,   0};
	
	private static final NumberFormat formatter = NumberFormat.getInstance();

	static {
		formatter.setMinimumIntegerDigits(2);
	}
	
	private Image backdropImage  = null;
	private int   backdropWidth  = 0;   
	private int   backdropHeight = 0;  
			
	/**
	 * @param map the map to render
	 */		
	public HexMapRenderer(final HexMap map) {
		super(map, scales);		
		
		try {
			setBackdropImage(ImageIO.read(HexMapRenderer.class.getResourceAsStream("265763main_carina07_hst_big_full.jpg")));
//			setBackdropImage(ImageIO.read(HexMapRenderer.class.getResourceAsStream("space.png")));
//			setBackdropImage(ImageIO.read(HexMapRenderer.class.getResourceAsStream("284895main_witchhead_nebula_full.jpg")));

		
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public HexMap getMap() {
		return (HexMap) super.getMap();
	}

	public Image getBackdropImage() {
		return backdropImage;
	}

	public void setBackdropImage(Image backdropImage) {
		this.backdropImage = backdropImage;

		setBackdropWidth((backdropImage == null)  ? 0 : backdropImage.getWidth(null));
		setBackdropHeight((backdropImage == null) ? 0 : backdropImage.getHeight(null));			
	}

	protected int getBackdropWidth() {
		return backdropWidth;
	}

	protected void setBackdropWidth(int backdropWidth) {
		this.backdropWidth = backdropWidth;
	}

	protected int getBackdropHeight() {
		return backdropHeight;
	}

	protected void setBackdropHeight(int backdropHeight) {
		this.backdropHeight = backdropHeight;
	}

	public RenderedMap render(int margin) {
		return renderAtScale(getScales()[0], margin);		
	}

	public RenderedMap renderAtScale(double scale, int margin) {
		int index = -1;
		for (int i = 0; i < getScales().length; i++) {
			if (scale == scales[i]) {
				index = i;
				break;
			}
		}

		if (index == -1) {
			throw new IllegalArgumentException("Unsupported scale " + scale);
		}
		
		RenderedMap map = new RenderedHexMap(getMap(), scale, margin, new HexMetrics(hex_sizes[index]));
		map.setAnnotationImage(renderAnnotationImage(index, margin));
		map.setGridImage(renderGridImage(index, margin));
		map.setBackdropImage(renderBackdropImage(index, margin));
		
		return map;
	}

	public RenderedMap[] renderAtAllScales(int margin) {
		RenderedMap[] maps = new RenderedMap[getScales().length];
		
		for (int i = 0; i < getScales().length; i++) {
			maps[i] = renderAtScale(getScales()[i], margin);	
			
//			try {
//				ImageIO.write((BufferedImage) maps[i].getAnnotationImage(), "png", new File("c:\\Temp\\Map_" + i + "_Annotation.png"));
//				ImageIO.write((BufferedImage) maps[i].getGridImage(),       "png", new File("c:\\Temp\\Map_" + i + "_Grid.png"));
//				ImageIO.write((BufferedImage) maps[i].getBackdropImage(),   "png", new File("c:\\Temp\\Map_" + i + "_Backdrop.png"));
//			} catch (IOException e) {
//				e.printStackTrace();
//			}
		}
		
		return maps;
	}
	
	private BufferedImage renderAnnotationImage(int index, int margin) {
		HexMetrics metrics = new HexMetrics(hex_sizes[index]);
		int hexPadding = ((getMap().getExtents().getBoundaryType() == BoundaryType.FLOATING) || 
				(getMap().getExtents().getBoundaryType() == BoundaryType.ANCHORED)) ? 2 : 0;
		
		// create the image
		Dimension hexGridSize = metrics.getGridSize(getMap().getExtents().getWidth() + hexPadding, getMap().getExtents().getHeight() + hexPadding);
		int width  = margin * 2 + hexGridSize.width;
		int height = margin * 2 + hexGridSize.height;
		
        BufferedImage mapImage    = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);       
        Graphics2D    mapGraphics = mapImage.createGraphics();
		mapGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		mapGraphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		mapGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        Font font = new Font("Verdana", Font.PLAIN, font_sizes[index]);	        
        mapGraphics.setFont(font);
 
	
		// tile backdrop image
//		int x = 0;
//		int y = 0;
//		while (x < width) {
//			y = 0;
//			
//			while (y < height) {
//				mapGraphics.drawImage(backdropImage, x, y, null);
//				
//				y += backgroundHeight;
//			}			
//			
//			x += backgroundWidth;
//		}
	
        
        // draw padding hexes
//		mapGraphics.setColor(Color.darkGray);
//		int offsetX = margin + halfWidth;
//		int offsetY = margin + halfHeight;
//		
//		for (int column = 0 - hexPadding; column < getMap().getExtents().getWidth() + hexPadding; column++) {
//			for (int row = 0 - hexPadding; row < getMap().getExtents().getHeight() + hexPadding; row++) {
//					drawHex(mapGraphics, points, offsetX, offsetY);
//				
//				offsetY += halfHeight * 2;
//			}
//
//			offsetX += halfWidth + halfSide;
//			offsetY = margin + halfHeight;
//			if (column % 2 == 0) {
//				offsetY += halfHeight;
//			}
//		}
		
        // draw regular hexes
		mapGraphics.setColor(getAnnotationColor());		
        FontMetrics fontMetrics = mapGraphics.getFontMetrics(mapGraphics.getFont());

        double offsetX = margin + (metrics.getHexWidth() / 2.0);
		double offsetY = margin + (metrics.getHexHeight() / 2.0);
		
		for (int column = 0; column < getMap().getExtents().getWidth(); column++) {
			for (int row = 0; row < getMap().getExtents().getHeight(); row++) {
//				drawHex(mapGraphics, metrics.getPoints(), offsetX, offsetY);
				drawNumber(mapGraphics, fontMetrics, metrics.getPoints(), offsetX, offsetY, formatter.format(column + 1) + formatter.format(row + 1));
				
				offsetY += metrics.getHexHeight();
			}

			offsetX += (metrics.getHexWidth() + metrics.getHexSide()) / 2.0;
			offsetY = margin + (metrics.getHexHeight() / 2.0);
			if (column % 2 == 0) {
				offsetY += (metrics.getHexHeight() / 2.0);
			}
		}
		
		mapGraphics.dispose();			
		return mapImage;
	}

	private BufferedImage renderGridImage(int index, int margin) {
		HexMetrics metrics = new HexMetrics(hex_sizes[index]);
		int hexPadding = ((getMap().getExtents().getBoundaryType() == BoundaryType.FLOATING) || 
				(getMap().getExtents().getBoundaryType() == BoundaryType.ANCHORED)) ? 2 : 0;
		
		// create the image
		Dimension hexGridSize = metrics.getGridSize(getMap().getExtents().getWidth() + hexPadding, getMap().getExtents().getHeight() + hexPadding);
		int width  = margin * 2 + hexGridSize.width;
		int height = margin * 2 + hexGridSize.height;
		
        BufferedImage mapImage    = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);        
        Graphics2D    mapGraphics = mapImage.createGraphics();
		mapGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		mapGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        Font font = new Font("Verdana", Font.PLAIN, font_sizes[index]);	        
        mapGraphics.setFont(font);
 
	
		// tile backdrop image
//		int x = 0;
//		int y = 0;
//		while (x < width) {
//			y = 0;
//			
//			while (y < height) {
//				mapGraphics.drawImage(backgroundImage, x, y, null);
//				
//				y += backgroundHeight;
//			}			
//			
//			x += backgroundWidth;
//		}
	
        
        // draw padding hexes
//		mapGraphics.setColor(Color.darkGray);
//		int offsetX = margin + halfWidth;
//		int offsetY = margin + halfHeight;
//		
//		for (int column = 0 - hexPadding; column < getMap().getExtents().getWidth() + hexPadding; column++) {
//			for (int row = 0 - hexPadding; row < getMap().getExtents().getHeight() + hexPadding; row++) {
//					drawHex(mapGraphics, points, offsetX, offsetY);
//				
//				offsetY += halfHeight * 2;
//			}
//
//			offsetX += halfWidth + halfSide;
//			offsetY = margin + halfHeight;
//			if (column % 2 == 0) {
//				offsetY += halfHeight;
//			}
//		}

        // draw regular hexes
		mapGraphics.setColor(getGridColor());			
		for (int column = 1; column <= getMap().getExtents().getWidth(); column++) {
			for (int row = 1; row <= getMap().getExtents().getHeight(); row++) {
				mapGraphics.draw(metrics.createHexAsShape(column, row, margin, margin));
			}
		}
		
		mapGraphics.dispose();			
		return mapImage;
	}
	
	private BufferedImage renderBackdropImage(int index, int margin) {
		HexMetrics metrics = new HexMetrics(hex_sizes[index]);
		int hexPadding = ((getMap().getExtents().getBoundaryType() == BoundaryType.FLOATING) || 
				(getMap().getExtents().getBoundaryType() == BoundaryType.ANCHORED)) ? 2 : 0;
		
		// create the image
		Dimension hexGridSize = metrics.getGridSize(getMap().getExtents().getWidth() + hexPadding, getMap().getExtents().getHeight() + hexPadding);
		int width  = margin * 2 + hexGridSize.width;
		int height = margin * 2 + hexGridSize.height;
		
        BufferedImage mapImage    = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);        
        Graphics2D    mapGraphics = mapImage.createGraphics();
		mapGraphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
		mapGraphics.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);

        Font font = new Font("Verdana", Font.PLAIN, font_sizes[index]);	        
        mapGraphics.setFont(font);
 
	
		// tile backdrop image
		int x = 0;
		int y = 0;
		while (x < width) {
			y = 0;
			
			while (y < height) {
				mapGraphics.drawImage(backdropImage, x, y, (int) (backdropWidth * scales[index]), 
						(int) (backdropHeight * scales[index]), null);
				
				y += backdropHeight * scales[index];
			}			
			
			x += backdropWidth * scales[index];
		}

//		// stretch backdrop image
//		mapGraphics.drawImage(backgroundImage, 0, 0, width, height, null);

//		// centre backdrop image
//		mapGraphics.drawImage(backgroundImage, 
//				(int) ((width - getBackgroundWidth() * scales[index]) / 2), 
//				(int) ((height - getBackgroundHeight() * scales[index]) / 2), 
//				(int) (getBackgroundWidth() * scales[index]), 
//				(int) (getBackgroundHeight() * scales[index]), null);
        
        // draw padding hexes
//		mapGraphics.setColor(Color.darkGray);
//		int offsetX = margin + halfWidth;
//		int offsetY = margin + halfHeight;
//		
//		for (int column = 0 - hexPadding; column < getMap().getExtents().getWidth() + hexPadding; column++) {
//			for (int row = 0 - hexPadding; row < getMap().getExtents().getHeight() + hexPadding; row++) {
//					drawHex(mapGraphics, points, offsetX, offsetY);
//				
//				offsetY += halfHeight * 2;
//			}
//
//			offsetX += halfWidth + halfSide;
//			offsetY = margin + halfHeight;
//			if (column % 2 == 0) {
//				offsetY += halfHeight;
//			}
//		}
		
        // draw regular hexes
//		mapGraphics.setColor(Color.white);		
//		int offsetX = margin + halfWidth;
//		int offsetY = margin + halfHeight;
//		
//		for (int column = 0; column < getMap().getExtents().getWidth(); column++) {
//			for (int row = 0; row < getMap().getExtents().getHeight(); row++) {
//				drawHex(mapGraphics, points, offsetX, offsetY);
//				drawNumber(mapGraphics, points, offsetX, offsetY, formatter.format(column + 1) + formatter.format(row + 1));
//				
//				offsetY += halfHeight * 2;
//			}
//
//			offsetX += halfWidth + halfSide;
//			offsetY = margin + halfHeight;
//			if (column % 2 == 0) {
//				offsetY += halfHeight;
//			}
//		}

		mapGraphics.dispose();			
		return mapImage;
	}
	
	public void drawNumber(Graphics2D g, FontMetrics fontMetrics, double[][] points, double offsetX, double offsetY, String number) {
		// draw label
        Rectangle2D labelBounds = fontMetrics.getStringBounds(number, g);
        float x = (float) (offsetX - labelBounds.getWidth() / 2.0);
        float y = (float) ((offsetY - points[1][0]) + g.getFont().getSize() * 1.5);
       
        g.drawString(number, x, y);
	}	
		
}
