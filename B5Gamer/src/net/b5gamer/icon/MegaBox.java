/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.icon;

import java.awt.geom.GeneralPath;

import net.b5gamer.awt.geom.GeneralShape;

public class MegaBox extends DamageBox {
	
	public static final float SIZE = DamageBox.SIZE * 2;
	
	public MegaBox(GeneralShape shape) {
		super(shape);
	}
	
	@Override
	public int getHits() {
		return 10;
	}

	@Override
	protected Object clone() {
		return new MegaBox((GeneralShape) getShape().clone());
	}
	
	public static MegaBox createAtGridCoordinates(double x, double y) {
		return createAtGridLocation(getGridColumn(x), getGridRow(y));
	}	
	
	public static MegaBox createAtGridLocation(int column, int row) {
		return createAtPoint(
				(float) Icon.OFFSET_X + ((float) column * DamageBox.SIZE),
				(float) Icon.OFFSET_Y + ((float) row * DamageBox.SIZE));
	}
	
	public static MegaBox createAtPoint(float x, float y) {
		GeneralPath damageBox = new GeneralPath();
		damageBox.moveTo(x, y);
		damageBox.lineTo(x + SIZE, y);
		damageBox.lineTo(x + SIZE, y + SIZE);
		damageBox.lineTo(x, y + SIZE);
		damageBox.lineTo(x, y);
		damageBox.closePath();

		return new MegaBox(new GeneralShape(damageBox));
	}
	
}
