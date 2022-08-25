/**
 * Copyright © Alex Packwood   
 */
package net.b5gamer.icon;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.AffineTransform;
import java.awt.geom.GeneralPath;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.swing.JPanel;

public class DamageableIconEditorPanel extends JPanel implements MouseListener, MouseMotionListener {

	private static final long serialVersionUID = 1371853730628611792L;

	private static final double SCALE = 1.5;
	
	private static final Color  BACKGROUND_COLOR      = Color.white;
	private static final Color  GRID_BACKGROUND_COLOR = Color.lightGray;
	private static final Color  GRID_COLOR            = Color.gray;
	private static final Stroke GRID_STROKE           = new BasicStroke(0.5f);
	private static final Color  DAMAGE_BOX_COLOR      = Color.black;
	private static final Color  DAMAGE_BOX_FILL_COLOR = Color.white;
	private static final Stroke DAMAGE_BOX_STROKE     = new BasicStroke(1.5f);
	private static final Font   MEGA_BOX_FONT         = new Font("Arial", Font.PLAIN, 5);
	
	protected int            rows;
	protected int            columns;
	protected DamageableIcon icon;
	protected float          iconOffsetX;
	protected float          iconOffsetY;
	protected int            maximumHits;
	protected DragMode       dragMode = DragMode.NONE;
	protected List<DamageBoxListener> damageBoxListeners = new ArrayList<DamageBoxListener>(0);
	
	public DamageableIconEditorPanel(int rows, int columns, DamageableIcon icon, int maximumHits) {
		super();
		addMouseListener(this);
		addMouseMotionListener(this);
		
		setRows(rows);
		setColumns(columns);
		setIcon(icon);	
		setIconOffsetX();
		setIconOffsetY();
		setMaximumHits(maximumHits);

		Dimension size = new Dimension((int) ((20 + getGridWidth()) * SCALE), (int) ((20 + getGridHeight()) * SCALE));
		setPreferredSize(size);
		setSize(size);		
	}

	public int getRows() {
		return rows;
	}

	private void setRows(int rows) {
        if (!(rows > 0)) {
            throw new IllegalArgumentException("rows must be a positive number");
        } 
        
        this.rows = rows;
	}
	
	public int getColumns() {
		return columns;
	}

	private void setColumns(int columns) {
        if (!(columns > 0)) {
            throw new IllegalArgumentException("columns must be a positive number");
        } 
        
        this.columns = columns;
	}

	public DamageableIcon getIcon() {
		return icon;
	}

	private void setIcon(DamageableIcon icon) {
        if (icon == null) {
            throw new IllegalArgumentException("icon cannot be null");
        } 
        
		this.icon = (DamageableIcon) icon.clone();
	}
	
	protected float getIconOffsetX() {
		return getGridOffsetX() + iconOffsetX;
	}

	protected void setIconOffsetX() {
		int column = (int) ((getColumns() - (getIcon().getSize().getWidth() / DamageBox.SIZE)) / 2);
		this.iconOffsetX = (column * DamageBox.SIZE) - getIcon().getBounds().x;
	}

	protected float getIconOffsetY() {
		return getGridOffsetY() + iconOffsetY;
	}

	protected void setIconOffsetY() {
		int row = (int) ((getRows() - (getIcon().getSize().getHeight() / DamageBox.SIZE)) / 2);
		this.iconOffsetY = (row * DamageBox.SIZE) - getIcon().getBounds().y;
	}

	public int getMaximumHits() {
		return maximumHits;
	}

	protected void setMaximumHits(int maximumHits) {
		this.maximumHits = maximumHits;
	}

	protected DragMode getDragMode() {
		return dragMode;
	}

	protected void setDragMode(DragMode dragMode) {
		this.dragMode = dragMode;
	}

	private List<DamageBoxListener> getDamageBoxListeners() {
		return damageBoxListeners;
	}

	public void addDamageBoxListener(DamageBoxListener listener) {
		if ((listener != null) && !(getDamageBoxListeners().contains(listener))) {
			getDamageBoxListeners().add(listener);
		}
	}
	
	public void removeDamageBoxListener(DamageBoxListener listener) {
		getDamageBoxListeners().remove(listener);
	}
	
	protected void notifyDamageBoxListeners(String key, int value) {
		if (getDamageBoxListeners().size() > 0) {
			HitsEvent e = new HitsEvent(this, key, value);

			for (Iterator<DamageBoxListener> iterator = getDamageBoxListeners().iterator(); iterator.hasNext();) {
				iterator.next().hitsValueChanged(e);
			}
		}
	}
	
	public float getGridWidth() {
		return getColumns() * DamageBox.SIZE;
	}

	public float getGridHeight() {
		return getRows() * DamageBox.SIZE;
	}

	protected int getGridOffsetX() {
		return (int) (((getSize().getWidth() / SCALE) - getGridWidth()) / 2.0);
	}
	
	protected int getGridOffsetY() {
		return (int) (((getSize().getHeight() / SCALE) - getGridHeight()) / 2.0);
	}
	
	@Override
	public final void paint(Graphics g) {
		Graphics2D graphics = (Graphics2D) g;
		graphics.setRenderingHint(RenderingHints.KEY_RENDERING,         RenderingHints.VALUE_RENDER_QUALITY);
		graphics.setRenderingHint(RenderingHints.KEY_ANTIALIASING,      RenderingHints.VALUE_ANTIALIAS_ON);
		graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
		
		graphics.setColor(BACKGROUND_COLOR);
		graphics.fillRect(0, 0, getWidth(), getHeight());

		graphics.transform(AffineTransform.getScaleInstance(SCALE, SCALE));
		
		// paint grid
		graphics.setColor(GRID_BACKGROUND_COLOR);
		graphics.fillRect(getGridOffsetX(), getGridOffsetY(), (int) getGridWidth() + 1, (int) getGridHeight() + 1);

		graphics.setColor(GRID_COLOR);
		graphics.setStroke(GRID_STROKE);
		
		int gridOffsetX = getGridOffsetX();
		int gridOffsetY = getGridOffsetY();
		
		GeneralPath line = new GeneralPath();
		line.moveTo(gridOffsetX, gridOffsetY);
		line.lineTo(gridOffsetX, gridOffsetY + getGridHeight());
		AffineTransform transform = AffineTransform.getTranslateInstance(DamageBox.SIZE, 0);
		for (int index = 0; index <= getColumns(); index++) {
			graphics.draw(line);
			line.transform(transform);
		}
		line = new GeneralPath();
		line.moveTo(gridOffsetX, gridOffsetY);
		line.lineTo(gridOffsetX + getGridWidth(), gridOffsetY);
		transform = AffineTransform.getTranslateInstance(0, DamageBox.SIZE);
		for (int index = 0; index <= getRows(); index++) {
			graphics.draw(line);
			line.transform(transform);
		}
		
		// paint damage boxes
		graphics.transform(AffineTransform.getTranslateInstance(getIconOffsetX(), getIconOffsetY()));

		graphics.setColor(DAMAGE_BOX_FILL_COLOR);
		
		for (Iterator<DamageBox> iterator = getIcon().getDamageBoxes().iterator(); iterator.hasNext();) {
			graphics.fill(iterator.next().getShape());
		}		
		
		graphics.setColor(DAMAGE_BOX_COLOR);
		graphics.setStroke(DAMAGE_BOX_STROKE);
		graphics.setFont(MEGA_BOX_FONT);
		FontMetrics fontMetrics = null;

        for (Iterator<DamageBox> iterator = getIcon().getDamageBoxes().iterator(); iterator.hasNext();) {
        	DamageBox damageBox = iterator.next();
        	
			graphics.draw(damageBox.getShape());
			
			if (damageBox instanceof MegaBox) {
				if (fontMetrics == null) {
					fontMetrics = graphics.getFontMetrics(MEGA_BOX_FONT);
				}
				
		        Rectangle2D textBounds = fontMetrics.getStringBounds(String.valueOf(damageBox.getHits()), graphics);
				graphics.drawString(String.valueOf(damageBox.getHits()), 
						(float) (damageBox.getShape().getBounds2D().getX() + 1.0),
						(float) (damageBox.getShape().getBounds2D().getY() + textBounds.getHeight()));	
			}
		}		
    }

	public void mouseClicked(MouseEvent e) {
	}

	public void mouseEntered(MouseEvent e) {
	}

	public void mouseExited(MouseEvent e) {
	}

	public void mousePressed(MouseEvent e) {
		int x = (int) ((e.getX() / SCALE));
		int y = (int) ((e.getY() / SCALE));
		
		if ((x > getGridOffsetX()) && (x < getGridOffsetX() + getGridWidth()) && 
				(y > getGridOffsetY()) && (y < getGridOffsetY() + getGridHeight())) {
			if (e.getClickCount() == 1) {
				setDragMode((e.getButton() == MouseEvent.BUTTON1) ? DragMode.DAMAGE_BOX : DragMode.REMOVE);
				toggleDamageBox((int) (x - getIconOffsetX()), (int) (y - getIconOffsetY()), getDragMode().equals(DragMode.DAMAGE_BOX));
			} else {
				if (e.getButton() == MouseEvent.BUTTON1) {					
					setDragMode(DragMode.MEGA_BOX);

					if ((x + DamageBox.SIZE < getGridOffsetX() + getGridWidth()) && 
							(y + DamageBox.SIZE < getGridOffsetY() + getGridHeight())) {
						createMegaBox((int) (x - getIconOffsetX()), (int) (y - getIconOffsetY()), true);		
					}
				}
			}
		}
	}

	public void mouseReleased(MouseEvent e) {
		setDragMode(DragMode.NONE);
	}
	
	public void mouseDragged(MouseEvent e) {
		if (!getDragMode().equals(DragMode.NONE)) {
			int x = (int) ((e.getX() / SCALE));
			int y = (int) ((e.getY() / SCALE));
			
			if ((x > getGridOffsetX()) && (x < getGridOffsetX() + getGridWidth()) && 
					(y > getGridOffsetY()) && (y < getGridOffsetY() + getGridHeight())) {
				if ((getDragMode().equals(DragMode.DAMAGE_BOX)) || (getDragMode().equals(DragMode.REMOVE))) {
					toggleDamageBox((int) (x - getIconOffsetX()), (int) (y - getIconOffsetY()), getDragMode().equals(DragMode.DAMAGE_BOX));					
				} else if (getDragMode().equals(DragMode.MEGA_BOX)) {
					if ((x + DamageBox.SIZE < getGridOffsetX() + getGridWidth()) && 
							(y + DamageBox.SIZE < getGridOffsetY() + getGridHeight())) {
						createMegaBox((int) (x - getIconOffsetX()), (int) (y - getIconOffsetY()), false);		
					}					
				}
			}
		}
	}

	public void mouseMoved(MouseEvent e) {
	}

	private void toggleDamageBox(int x, int y, boolean adding) {
		// check for existing damage boxes
		List<DamageBox> damageBoxes = new ArrayList<DamageBox>(0);
        for (Iterator<DamageBox> iterator = getIcon().getDamageBoxes().iterator(); iterator.hasNext();) {
        	DamageBox damageBox = iterator.next();
			
			if (damageBox.getShape().contains(x, y)) {
				damageBoxes.add(damageBox);
			}
		}
        
		if (adding) {
			// check if should add damage box
			if (damageBoxes.size() == 0) {
				getIcon().getDamageBoxes().add(DamageBox.createAtGridCoordinates(x, y));
				notifyDamageBoxListeners("Hits", getIcon().getTotalHits());
				repaint();
			}					
		} else {
			// check if should remove damage box
			if (damageBoxes.size() > 0) {
		        for (Iterator<DamageBox> iterator = damageBoxes.iterator(); iterator.hasNext();) {
					getIcon().getDamageBoxes().remove(iterator.next());
		        }			

				notifyDamageBoxListeners("Hits", getIcon().getTotalHits());
				repaint();
			}					
		}
	}
		
	private void createMegaBox(int x, int y, boolean remove) {
		// create mega box
		MegaBox megaBox = MegaBox.createAtGridCoordinates(x, y);
		Rectangle2D bounds = (Rectangle2D) megaBox.getShape().getBounds2D().clone();
		Rectangle2D searchBounds = new Rectangle2D.Double(
				bounds.getX() + 0.1, bounds.getY() + 0.1, bounds.getWidth() - 0.2, bounds.getHeight() - 0.2);
		
		// check for overlapping damage boxes
		List<DamageBox> damageBoxes = new ArrayList<DamageBox>(0);
		boolean foundMegaBox = false;
        for (Iterator<DamageBox> iterator = getIcon().getDamageBoxes().iterator(); iterator.hasNext();) {
        	DamageBox damageBox = iterator.next();
			
			if (damageBox.getShape().intersects(searchBounds)) {
				damageBoxes.add(damageBox);
				
				if (damageBox instanceof MegaBox) {
					foundMegaBox = true;
				}
			}
		}	

        // remove overlapping damage boxes
        if ((remove) || !(foundMegaBox)) {
	        for (Iterator<DamageBox> iterator = damageBoxes.iterator(); iterator.hasNext();) {
				getIcon().getDamageBoxes().remove(iterator.next());
	        }			
	
			// add megabox
			getIcon().getDamageBoxes().add(megaBox);
			
			notifyDamageBoxListeners("Hits", getIcon().getTotalHits());
			repaint();
        }
	}

	private enum DragMode {
		NONE,
		DAMAGE_BOX,
		MEGA_BOX,
		REMOVE;	
	}
	
}
