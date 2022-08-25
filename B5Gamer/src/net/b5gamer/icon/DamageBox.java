/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.icon;

import java.awt.geom.GeneralPath;
import java.awt.geom.PathIterator;
import java.awt.geom.Point2D;
import java.util.ArrayList;

import net.b5gamer.awt.geom.GeneralShape;

public class DamageBox implements Cloneable {

	public static final float SIZE = 6.75f;
	
	private GeneralShape shape;
	
	public DamageBox(GeneralShape shape) {
		setShape(shape);
	}
	
	public GeneralShape getShape() {
		return shape;
	}

	protected void setShape(GeneralShape shape) {
        if (shape == null) {
            throw new IllegalArgumentException("shape cannot be null");
        }
        
        this.shape = shape;
	}

	public Point2D[] getPoints() {
		ArrayList<Point2D> points = new ArrayList<Point2D>(4);
		
		PathIterator pathIterator = getShape().getPathIterator(null);
		while (!pathIterator.isDone()) {
			float[] coordinates = new float[6];
			int segmentType = pathIterator.currentSegment(coordinates);
			
			if (segmentType == PathIterator.SEG_LINETO) {
				points.add((points.size() < 3) ? points.size() : 0, new Point2D.Double(coordinates[0], coordinates[1]));					
			}
			
			pathIterator.next();
		}
		
		return points.toArray(new Point2D[points.size()]);
	}
	
	public int getHits() {
		return 1;
	}
	
	@Override
	protected Object clone() {
		return new DamageBox((GeneralShape) getShape().clone());
	}
	
	public static int getGridColumn(double x) {
		int column = (int) ((x - Icon.OFFSET_X) / SIZE);
		column = (x < Icon.OFFSET_X) ? column - 1 : column;

		return column;
	}
	
	public static int getGridRow(double y) {
		int row = (int) ((y - Icon.OFFSET_Y) / SIZE);
		row = (y < Icon.OFFSET_Y) ? row - 1 : row;

		return row;
	}

	public static DamageBox createAtGridCoordinates(double x, double y) {
		return createAtGridLocation(getGridColumn(x), getGridRow(y));
	}	
	
	public static DamageBox createAtGridLocation(int column, int row) {
		return createAtPoint(
				(float) Icon.OFFSET_X + ((float) column * SIZE),
				(float) Icon.OFFSET_Y + ((float) row * SIZE));
	}
	
	public static DamageBox createAtPoint(float x, float y) {
		GeneralPath damageBox = new GeneralPath();
		damageBox.moveTo(x, y);
		damageBox.lineTo(x + SIZE, y);
		damageBox.lineTo(x + SIZE, y + SIZE);
		damageBox.lineTo(x, y + SIZE);
		damageBox.lineTo(x, y);
		damageBox.closePath();

		return new DamageBox(new GeneralShape(damageBox));
	}
	
}
