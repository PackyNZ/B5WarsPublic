package net.b5gamer.map.hex;

import java.awt.Dimension;
import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;

/**
 * This class is used to determine the metrics of hexagons with a specified radius in pixels
 * 
 * @author Alex Packwood (aka PackyNZ)
 */
public class HexMetrics {
	
	protected static final int NUMBER_OF_SIDES = 6;
	
	protected final double     hexRadius; // radius of a hex in pixels
	protected       double     hexSide;   // length of a hex side in pixels
	protected       double     hexWidth;  // width of a hex in pixels
	protected       double     hexHeight; // height of a hex in pixels
	protected       double[][] points;    // points comprising a hex, based on a center of 0,0
		
	/**
	 * calculate the metrics of a hex with the given radius in pixels
	 * 
	 * @param hexRadius radius of a hex in pixels
	 */
	public HexMetrics(double hexRadius) {
        if (!(hexRadius > 0.0)) {
            throw new IllegalArgumentException("hexRadius must be a positive number");
        } 		
		
		this.hexRadius = hexRadius;
		
		calculateHexMetrics();
	}
	
	/**
	 * calculate the metrics of a hex with the given radius
	 */	
	protected void calculateHexMetrics() {	
		double[][] points = new double[NUMBER_OF_SIDES][NUMBER_OF_SIDES];
        
        double increment = 2.0 * Math.PI / NUMBER_OF_SIDES;
        double angle = increment;
        
        for(int i = 0; i < NUMBER_OF_SIDES; i++) {
            points[0][i] = getHexRadius() * Math.cos(angle);
            points[1][i] = getHexRadius() * Math.sin(angle);
            
            angle += increment;
        }

		this.hexSide   = points[0][0] * 2.0;
		this.hexWidth  = points[0][5] * 2.0;
		this.hexHeight = points[1][0] * 2.0;
        this.points    = points;
	}
	
	/**
	 * the radius of a hex in pixels
	 * 
	 * @return the radius of a hex in pixels
	 */
	public double getHexRadius() {
		return hexRadius;
	}

	/**
	 * the length of a hex side in pixels
	 * 
	 * @return the length of a hex side in pixels
	 */	
	public double getHexSide() {
		return hexSide;
	}

	/**
	 * the width of a hex in pixels
	 * 
	 * @return width of a hex in pixels
	 */	
	public double getHexWidth() {
		return hexWidth;
	}

	/**
	 * the height of a hex in pixels
	 * 
	 * @return the height of a hex in pixels
	 */	
	public double getHexHeight() {
		return hexHeight;
	}

	/**
	 * the points comprising a hex, based on a center of 0,0
	 * 
	 * @return the points comprising a hex, based on a center of 0,0
	 */	
	public double[][] getPoints() {
		return points;
	}
	
	/**
	 * calculate the size in pixels of a hex grid with the specified number of columns and rows
	 * 
	 * @param  columns number of columns in the grid
	 * @param  rows    number of rows in the grid
	 * @return         size in pixels of a hex grid with the specified number of columns and rows
	 */	
	public Dimension getGridSize(int columns, int rows) {
        if (!(columns > 0)) {
            throw new IllegalArgumentException("columns must be a positive number");
        } 		
        if (!(rows > 0)) {
            throw new IllegalArgumentException("rows must be a positive number");
        } 		

        double width  = ((getHexWidth() + getHexSide()) / 2.0) * columns + ((getHexWidth() - getHexSide()) / 2.0);
        double height = (rows > 1.0) ? getHexHeight() * rows + getHexHeight() / 2.0 : getHexHeight();

        Dimension dimension = new Dimension();
        dimension.setSize(width, height);
		return dimension;
	}
	
	/**
	 * create a shape representing a hex at the specified position
	 * 
	 * @param  column column of the hex to create
	 * @param  row    row of the hex to create
	 * @return        shape representing a hex at the specified position
	 */	
	public Shape createHexAsShape(int column, int row) {
		return createHexAsShape(column, row, 0, 0);
	}
	
	/**
	 * create a shape representing a hex at the specified position
	 * 
	 * @param  column  column of the hex to create
	 * @param  row     row of the hex to create
	 * @param  xOffset number of pixels to offset the shape along the x axis 
	 * @param  yOffset number of pixels to offset the shape along the y axis 
	 * @return         shape representing a hex at the specified position
	 */		
	public Shape createHexAsShape(int column, int row, double xOffset, double yOffset) {
		GeneralPath hex = new GeneralPath();
		
		Point2D point = getCenter(new Hex(column, row));

    	hex.moveTo((float) (getPoints()[0][0] + point.getX() + xOffset), (float) (getPoints()[1][0] + point.getY() + yOffset));
		for(int i = 1; i < 6; i++) {
        	hex.lineTo((float) (getPoints()[0][i] + point.getX() + xOffset), (float) (getPoints()[1][i] + point.getY() + yOffset));
        }
    	hex.closePath();
        
        return hex;
	}
	
	/**
	 * calculate the center point of the specified hex
	 * 
	 * @param  hex hex to return the center of
	 * @return     the center point of the specified hex
	 */		
	public Point2D getCenter(Hex hex) {
        if (hex == null) {
            throw new IllegalArgumentException("hex cannot be null");
        } 		
		
		double x = ((getHexWidth() + getHexSide()) / 2.0) * (hex.getColumn() - 1.0) + (getHexWidth() / 2.0);
		double y = getHexHeight() * (hex.getRow() - 1.0) + (getHexHeight() / 2.0);
		y = (hex.getColumn() % 2.0 == 0.0) ? y + (getHexHeight() / 2.0) : y;
        
		return new Point2D.Double(x, y);
	}
	
	/**
	 * determine the hex at the specified point
	 * 
	 * @param  x x coordinate to search at
	 * @param  y y coordinate to search at
	 * @return   the hex at the specified point
	 */		
	public Hex getHexAtPoint(int x, int y) {
		// determine column
		double columnWidth = getHexSide() + ((getHexWidth() - getHexSide()) / 2.0);
		int column = (int) (x / columnWidth + 1.0);
		
		// determine row
		int row = (int) ((y - ((column % 2 == 0.0) ? getHexHeight() / 2.0 : 0.0)) / getHexHeight() + 1.0);

		// determine which of the 3 possible hexes contains the point
		if (createHexAsShape(column, row).contains(x, y)) { return new Hex(column, row); }
		if (createHexAsShape(column - 1, row).contains(x, y)) { return new Hex(column - 1, row); }
		if (column % 2 == 0) {
			if (createHexAsShape(column - 1, row + 1).contains(x, y)) { return new Hex(column - 1, row + 1); }
		} else {
			if (createHexAsShape(column - 1, row - 1).contains(x, y)) { return new Hex(column - 1, row - 1); }
		}
		
		return null;
	}
	
}
