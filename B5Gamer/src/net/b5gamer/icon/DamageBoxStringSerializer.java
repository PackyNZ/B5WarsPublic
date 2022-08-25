/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.icon;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public final class DamageBoxStringSerializer {

	public static final String VALUE_SEPARATOR = ":";
	public static final String GROUP_SEPARATOR = ",";
	public static final String TYPE_DAMAGEBOX  = "D";
	public static final String TYPE_MEGABOX    = "M";
	
	private DamageBoxStringSerializer() {	
	}
	
	public static final String serialize(List<DamageBox> damageBoxes) {		
		Collections.sort(damageBoxes, new DamageBoxComparator());

		StringBuilder damageBoxString = null;
		int column = 0;
		int row    = 0;
		int count  = 0;
		Class<? extends DamageBox> damageBoxClass = null;
		
		for (Iterator<DamageBox> iterator = damageBoxes.iterator(); iterator.hasNext();) {
			DamageBox damageBox = iterator.next();
			
			int thisColumn = DamageBox.getGridColumn(damageBox.getShape().getBounds().getX() + (DamageBox.SIZE / 2.0)) + 1;
			int thisRow    = DamageBox.getGridRow(damageBox.getShape().getBounds().getY() + (DamageBox.SIZE / 2.0)) + 1;
						
			if ((damageBoxClass == damageBox.getClass()) && (row == thisRow) && 
					(thisColumn - column == ((damageBox.getClass() == MegaBox.class) ? 2 : 1) * count)) {
				count++;
			} else {
				if (count > 0) {
					damageBoxString = addDamageBoxGroup(damageBoxString, column, row, count, damageBoxClass);
				}
				
				column = thisColumn;
				row    = thisRow;
				count  = 1;
				damageBoxClass = damageBox.getClass();
			}
							
			if (!iterator.hasNext()) {
				damageBoxString = addDamageBoxGroup(damageBoxString, column, row, count, damageBoxClass);				
			}
		}		
		
		return (damageBoxString != null) ? damageBoxString.toString() : "";
	}

	private static final StringBuilder addDamageBoxGroup(StringBuilder damageBoxString, int column, int row,
			int count, Class<? extends DamageBox> damageBoxClass) {
		if (damageBoxString == null) {
			damageBoxString = new StringBuilder();	
		} else {
			damageBoxString.append(GROUP_SEPARATOR);
		}
		
		damageBoxString.append(column).append(VALUE_SEPARATOR).append(row);
		
		if ((count > 1) || (damageBoxClass != DamageBox.class)) {
			damageBoxString.append(VALUE_SEPARATOR).append(count);
			
			if (damageBoxClass != DamageBox.class) {
				damageBoxString.append(TYPE_MEGABOX);
			}
		}
		
		return damageBoxString;
	}
	
	public static final List<DamageBox> deserialize(final String damageBoxString) {
        if ((damageBoxString == null) || !(damageBoxString.trim().length() > 0)) {
            throw new IllegalArgumentException("damageBoxString cannot be null or blank");
        } 	
		
        String[] groups = damageBoxString.split(GROUP_SEPARATOR);
        List<DamageBox> damageBoxes = new ArrayList<DamageBox>(groups.length);
        
        for (int index = 0; index < groups.length; index++) {
        	String[] values = groups[index].split(VALUE_SEPARATOR);
        	
        	if ((values.length < 2) || (values.length > 3)) {
        		throw new IllegalArgumentException("damageBoxString \"" + damageBoxString + "\" is not in the format of \"c:r:cnt,c:r:cnt,...\"");
        	} else {
        		int    column = Integer.parseInt(values[0]);
        		int    row    = Integer.parseInt(values[1]);
        		int    count  = 0;
        		String type   = TYPE_DAMAGEBOX;
        		
        		if (values.length == 3) {
	        		if ((values[2].endsWith(TYPE_DAMAGEBOX)) || (values[2].endsWith(TYPE_MEGABOX))) {
	        			count = Integer.parseInt(values[2].substring(0, values[2].length() - 1));
	        			type  = (values[2].endsWith(TYPE_MEGABOX)) ? TYPE_MEGABOX : TYPE_DAMAGEBOX;
	        		} else {        			
	        			count = Integer.parseInt(values[2]);
	        		}
        		} else {        			
        			count = 1;
        		}
        		
    			for (int i = 0; i < count; i++) {
            		if (type == TYPE_DAMAGEBOX) {
            			damageBoxes.add(DamageBox.createAtGridLocation(column, row));
            			column++;
            		} else if (type == TYPE_MEGABOX) {
            			damageBoxes.add(MegaBox.createAtGridLocation(column, row));
            			column += 2;            			
            		}        		    				
    			}
        	}
        }
               
		return damageBoxes;
	}
		
}
