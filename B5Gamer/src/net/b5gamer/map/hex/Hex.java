package net.b5gamer.map.hex;

import java.io.Serializable;
import java.text.NumberFormat;

/**
 * This class represents a numbered hexagon in a hexagonal grid 
 *  
 * @author Alex Packwood (aka PackyNZ)
 */
public class Hex implements Serializable {

	private static final long serialVersionUID = 6043311881996439195L;
	
	private static final NumberFormat NUMBER_FORMATTER = NumberFormat.getInstance();
	
	protected final int column; // x position of the hex in a hexagonal grid
	protected final int row;    // y position of the hex in a hexagonal grid
	
	static {
		NUMBER_FORMATTER.setMinimumIntegerDigits(2);
	}
	
	/**
	 * @param column x position of the hex in a hexagonal grid
	 * @param row    y position of the hex in a hexagonal grid
	 */		
	public Hex(final int column, final int row) {
		this.column = column;
		this.row    = row;
	}

	/**
	 * the x position of the hex in a hexagonal grid
	 * 
	 * @return the x position of the hex in a hexagonal grid
	 */
	public int getColumn() {
		return column;
	}

	/**
	 * the y position of the hex in a hexagonal grid
	 * 
	 * @return the y position of the hex in a hexagonal grid
	 */
	public int getRow() {
		return row;
	}
	
	/**
	 * calculate the distance in hexes from the hex to a destination hex
	 * 
	 * @param  dest the destination hex
	 * @return      the distance in hexes from the hex to a destination hex
	 */	
    public int getDistance(final Hex dest) {
        return getDistance(this, dest); 
    }
    
	/**
	 * calculate the distance in hexes from a source hex to a destination hex 
	 * 
	 * thanks to Michael Power and http://www.planetxot.com/gml_hexdistance.htm
	 * 
	 * @param  source the source hex
	 * @param  dest   the destination hex
	 * @return        the distance in hexes from the source hex to a destination hex
	 */
    public static int getDistance(final Hex source, final Hex dest) {
        int dx = Math.abs(source.getColumn() - dest.getColumn());
        int yOff = dx / 2;
        if (((source.getColumn() % 2) == 1) && ((dest.getColumn() % 2) == 0)) yOff += 1;
        int yMin = source.getRow() - yOff;
        int yMax = yMin + dx;
        int yMod = 0;
        if (dest.getRow() < yMin) yMod = yMin - dest.getRow();
        if (dest.getRow() > yMax) yMod = dest.getRow() - yMax;

        return dx + yMod; 
    }
	
	@Override
	public String toString() {
		StringBuilder description = new StringBuilder();
		
		description.append(NUMBER_FORMATTER.format(getColumn()));
		description.append(NUMBER_FORMATTER.format(getRow()));
		
		return description.toString();
	}
    
}
