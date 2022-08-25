package net.b5gamer.b5wars.test;

import java.applet.Applet;
import java.awt.*;
import java.util.Iterator;

import net.b5gamer.b5wars.ui.render.ArcRenderer;
import net.b5gamer.map.Arc;

@SuppressWarnings("removal")
public class ArcTester extends Applet {

	private static final long serialVersionUID = 1065401478539873064L;

	private static final int   MAX_COLUMNS = 10;
	private static final Color ARC_COLOR   = Color.gray;

	public final void init() {
        try {
            int rows = (Arc.getArcCount() / MAX_COLUMNS);
            rows = (Arc.getArcCount() % MAX_COLUMNS) > 0 ? rows + 1 : rows;
        	this.setSize(new Dimension(ArcRenderer.getOverlayWidth() * MAX_COLUMNS, 
        			(ArcRenderer.getOverlayHeight() + 10) * rows));
            this.setLayout(null);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public final void paint(Graphics g) {
    	int column = 0;
    	int row    = 0;
    	for (Iterator<Arc> iterator = Arc.getArcIterator(); iterator.hasNext();) {
    		Arc arc = iterator.next();
    		
    		if (column >= MAX_COLUMNS) {
    			column = 0;    	
    			row++;
    		}
    	
    		g.setColor(Color.black);
    		g.drawString(arc.getName(), column * ArcRenderer.getOverlayWidth() + 2, 
    				row * (ArcRenderer.getOverlayHeight() + 10) + 11);
    		g.drawImage(ArcRenderer.render(arc, ARC_COLOR), column * ArcRenderer.getOverlayWidth(), 
    				row * (ArcRenderer.getOverlayHeight() + 10) + 10, this);
    		
//    		g.setColor(Color.red);
//    		g.drawRect(column * ArcRenderer.getImageWidth(), row * (ArcRenderer.getImageHeight() + 10), 
//    				ArcRenderer.getImageWidth() - 1, ArcRenderer.getImageHeight() + 10 - 1);
        
    		column++;
    	}    
    } 

}
