/**
 * Copyright � Alex Packwood   
 */
package net.b5gamer.icon;

import java.awt.Shape;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import net.b5gamer.awt.geom.GeneralShape;
import net.b5gamer.awt.geom.ShapeUtil;
import net.b5gamer.awt.geom.SubPath;
import net.b5gamer.util.SVGUtil;

public final class IconUtil {

	private IconUtil() {
	}

	public static final Icon createIcon(final InputStream input, final int numberOfAnnotations) throws IOException {
		Shape shape = SVGUtil.readShape(input);
		List<SubPath> paths = ShapeUtil.parse(shape);

		// create outlines
		ArrayList<GeneralShape> outlineShapes = new ArrayList<GeneralShape>(paths.size());
		for (int index = 0; index < paths.size() - numberOfAnnotations; index++) {
			outlineShapes.add(ShapeUtil.construct(paths.get(index)));
		}
		
		// create annotation locations
		List<GeneralPath> annotationLocations = null;
		if (numberOfAnnotations > 0) {
			annotationLocations = new ArrayList<GeneralPath>(numberOfAnnotations);
			
			for (int index = paths.size() - numberOfAnnotations; index < paths.size(); index++) {
				Rectangle2D bounds = ShapeUtil.construct(paths.get(index)).getGeneralPath().getBounds2D();
				GeneralPath annotationLocation = new GeneralPath();
				annotationLocation.moveTo((float) bounds.getCenterX(), (float) bounds.getCenterY());
				annotationLocations.add(annotationLocation);
			}
		}
			
		Icon icon = new Icon((outlineShapes.size() > 0) ? outlineShapes : null, 
				((annotationLocations != null) && (annotationLocations.size() > 0)) ? annotationLocations : null);
		icon.transform(SVGUtil.RESCALE_TRANSFORM);
		icon.reposition();		
		
		return icon;		
	}
	
	public static final DamageableIcon createDamageableIcon(final InputStream input, final int numberOfAnnotations, 
			final int numberOfdamageBoxes) throws IOException {
		Shape shape = SVGUtil.readShape(input);
		List<SubPath> paths = ShapeUtil.parse(shape);
		int numOutlines = paths.size() - numberOfAnnotations - numberOfdamageBoxes;
		
		// create outlines
		ArrayList<GeneralShape> outlineShapes = new ArrayList<GeneralShape>(numOutlines);
		for (int index = 0; index < numOutlines; index++) {
			outlineShapes.add(ShapeUtil.construct(paths.get(index)));
		}

		// create annotation locations
		List<GeneralPath> annotationLocations = null;
		if (numberOfAnnotations > 0) {
			annotationLocations = new ArrayList<GeneralPath>(numberOfAnnotations);
			
			for (int index = numOutlines; index < numOutlines + numberOfAnnotations; index++) {
				Rectangle2D bounds = ShapeUtil.construct(paths.get(index)).getGeneralPath().getBounds2D();
				GeneralPath annotationLocation = new GeneralPath();
				annotationLocation.moveTo((float) bounds.getCenterX(), (float) bounds.getCenterY());
				annotationLocations.add(annotationLocation);
			}
		}
		
		// create damage boxes
		ArrayList<DamageBox> damageBoxes = null;
		if (numberOfdamageBoxes > 0) {
			damageBoxes = new ArrayList<DamageBox>(numberOfdamageBoxes);
			
			for (int index = paths.size() - numberOfdamageBoxes; index < paths.size(); index++) {
				damageBoxes.add(new DamageBox(ShapeUtil.construct(paths.get(index))));
			}
		}
		
		DamageableIcon icon = new DamageableIcon((outlineShapes.size() > 0) ? outlineShapes : null,
				((annotationLocations != null) && (annotationLocations.size() > 0)) ? annotationLocations : null,
				((damageBoxes != null) && (damageBoxes.size() > 0)) ? damageBoxes : null);
		icon.transform(SVGUtil.RESCALE_TRANSFORM);
		icon.reposition();		

		return icon;		
	}

	public static final DamageableIcon createCustomIcon(final int numberOfDamageBoxes) {
		int sqrt    = (int) Math.round(Math.sqrt(numberOfDamageBoxes));
		int columns = (sqrt * sqrt < numberOfDamageBoxes) ? sqrt + 1 : sqrt;
		int rows    = (sqrt * columns < numberOfDamageBoxes) ? sqrt + 1 : sqrt;

		ArrayList<DamageBox> damageBoxes = new ArrayList<DamageBox>(numberOfDamageBoxes);
		
		for (int row = 0; row < rows; row++) {
			int minColumn = 0;
			int maxColumn = columns;
			
			// setup for partial row
			if ((row == 0) && (rows * columns > numberOfDamageBoxes)) {
				minColumn = ((rows * columns) - numberOfDamageBoxes) / 2;
				maxColumn = columns - (((rows * columns) - numberOfDamageBoxes) - minColumn) - 1;
			}

			for (int column = 0; column < columns; column++) {
				if ((row > 0) || ((column >= minColumn) && (column <= maxColumn))) {
					DamageBox damageBox = DamageBox.createAtGridLocation(column, row);
					damageBoxes.add(damageBox);
				}
			}
		}
		
		return new DamageableIcon(null, damageBoxes);
	}
	
}
