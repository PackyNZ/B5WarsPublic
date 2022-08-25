package net.b5gamer.b5wars.ui.game;

import java.awt.Dimension;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JToggleButton;

public class ToolBar extends JPanel {

	private static final long serialVersionUID = -1888318572358008891L;
	
	private final MapControl mapControl;
	
	public ToolBar(MapControl mapControl) throws IOException {
		super();
//		setPreferredSize(new Dimension(getParent().getPreferredSize().width, 30));

		this.mapControl = mapControl;
		
		JToggleButton annotationButton = new JToggleButton();
		annotationButton.setIcon(new ImageIcon(ImageIO.read(ToolBar.class.getResourceAsStream("btn_annotation_off.png"))));
		annotationButton.setSelectedIcon(new ImageIcon(ImageIO.read(ToolBar.class.getResourceAsStream("btn_annotation_on.png"))));
		annotationButton.setSelected(mapControl.isDispayAnnotation());
		annotationButton.setPreferredSize(new Dimension(30, 30));
		annotationButton.setBorderPainted(false);
		annotationButton.setToolTipText("Toggle Numbers");
		annotationButton.addActionListener(mapControl);
		annotationButton.setActionCommand(MapControl.ACTION_TOGGLE_ANNOTATION);
		add(annotationButton);
		
		JToggleButton gridButton = new JToggleButton();
		gridButton.setIcon(new ImageIcon(ImageIO.read(ToolBar.class.getResourceAsStream("btn_grid_off.png"))));
		gridButton.setSelectedIcon(new ImageIcon(ImageIO.read(ToolBar.class.getResourceAsStream("btn_grid_on.png"))));
		gridButton.setSelected(mapControl.isDispayGrid());
		gridButton.setPreferredSize(new Dimension(30, 30));
		gridButton.setBorderPainted(false);
		gridButton.setToolTipText("Toggle Hexes");
		gridButton.addActionListener(mapControl);
		gridButton.setActionCommand(MapControl.ACTION_TOGGLE_GRID);
		add(gridButton);
		
		JToggleButton backdropButton = new JToggleButton();
		backdropButton.setIcon(new ImageIcon(ImageIO.read(ToolBar.class.getResourceAsStream("btn_backdrop_off.png"))));
		backdropButton.setSelectedIcon(new ImageIcon(ImageIO.read(ToolBar.class.getResourceAsStream("btn_backdrop_on.png"))));
		backdropButton.setSelected(mapControl.isDispayBackdrop());
		backdropButton.setPreferredSize(new Dimension(30, 30));
		backdropButton.setBorderPainted(false);
		backdropButton.setToolTipText("Toggle Backdrop");
		backdropButton.addActionListener(mapControl);
		backdropButton.setActionCommand(MapControl.ACTION_TOGGLE_BACKDROP);
		add(backdropButton);
		
		JToggleButton overviewButton = new JToggleButton();
		overviewButton.setIcon(new ImageIcon(ImageIO.read(ToolBar.class.getResourceAsStream("btn_overview_off.png"))));
		overviewButton.setSelectedIcon(new ImageIcon(ImageIO.read(ToolBar.class.getResourceAsStream("btn_overview_on.png"))));
		overviewButton.setSelected(mapControl.isDispayOverview());
		overviewButton.setPreferredSize(new Dimension(30, 30));
		overviewButton.setBorderPainted(false);
		overviewButton.setToolTipText("Toggle Overview");
		overviewButton.addActionListener(mapControl);
		overviewButton.setActionCommand(MapControl.ACTION_TOGGLE_OVERVIEW);
		add(overviewButton);
	}

	public MapControl getMapControl() {
		return mapControl;
	}

}
