package net.b5gamer.b5wars.ui.fleet;

import java.awt.Dimension;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.beans.PropertyVetoException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JDesktopPane;
import javax.swing.JInternalFrame;

import net.b5gamer.b5wars.ui.scs.ControlSheetInternalFrame;
import net.b5gamer.b5wars.ui.scs.UsageMode;
import net.b5gamer.b5wars.unit.Unit;

public class ControlSheetDesktopPane extends JDesktopPane implements ComponentListener {

	private static final long serialVersionUID = 4124696223863769661L;
	
	private List<Unit> units  = null;
	private boolean    tiled  = true;
	private boolean    tiling = true;
	
	public ControlSheetDesktopPane(List<Unit> units) {
		super();
		setDoubleBuffered(true);
		addComponentListener(this);
		
		setUnits(units);		
	}

	protected List<Unit> getUnits() {
		return units;
	}

	protected void setUnits(List<Unit> units) {
		this.units = (units != null) ? new ArrayList<Unit>(units) : new ArrayList<Unit>(0);
		
		// setup frames
		removeAll();
		for (Iterator<Unit> iterator = getUnits().iterator(); iterator.hasNext();) {
			ControlSheetInternalFrame frame = new ControlSheetInternalFrame(iterator.next(), UsageMode.PLAY);	
			frame.addComponentListener(this);
			frame.setVisible(true);
			
			add(frame);
		}
		setTiled(true);
	}

	public boolean isTiled() {
		return tiled;
	}

	public void setTiled(boolean tiled) {
		this.tiled = tiled;
		
		System.out.println("tiled=" + tiled);
		
		if (tiled) {
			tileFrames();
		}
	}
	
    private boolean isTiling() {
		return tiling;
	}

    private void setTiling(boolean tiling) {
		this.tiling = tiling;
	}

	public void tileFrames() { 
		setTiling(true);
		
		System.out.println("tileFrames()");

		JInternalFrame[] allFrames = getAllFrames();
        int count = allFrames.length;
        if (count > 0) {
	        // determine the necessary grid size
	        int sqrt = (int) Math.sqrt(count);
	        int rows = sqrt;
	        int cols = sqrt;
	        if (rows * cols < count) {
	            cols++;
	            if (rows * cols < count) {
	                rows++;
	            }
	        }
	        
	        // define initial values for size & location.
	        Dimension size = getSize();
	        
	        int w = size.width / cols;
	        int h = size.height / rows;
	        int x = 0;
	        int y = 0;
	        
	        // iterate over the frames, deiconifying any iconified frames and then
	        // relocating & resizing each.
	        for (int i = 0; i < rows; i++) {
	            for (int j = 0; j < cols && ((i * cols) + j < count); j++) {
	                JInternalFrame f = allFrames[(i * cols) + j];
	                
	                if (!f.isClosed() && f.isIcon()) {
	                    try {
	                        f.setIcon(false);
	                    } catch (PropertyVetoException ignored) {}
	                }
	                
	                getDesktopManager().resizeFrame(f, x, y, w, h);
                
	                x += w;
	            }
	            y += h; 
	            x = 0;
	        }
        }
        
        setTiling(false);
    }

	public void componentHidden(ComponentEvent e) {
	}

	public void componentMoved(ComponentEvent e) {
	}

	public void componentResized(ComponentEvent e) {
		System.out.println("componentResized");
		
		if (e.getSource() == this) {
			System.out.println("1");

			setTiled(isTiled());
			repaint();		
		} else if (!isTiling()) {
			System.out.println("2");

//			setTiled(false);
		}
	}

	public void componentShown(ComponentEvent e) {
	}
    
}
