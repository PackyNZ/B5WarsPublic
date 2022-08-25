package net.b5gamer.b5wars.ui.game;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.io.IOException;

import net.b5gamer.b5wars.game.Game;
import net.b5gamer.b5wars.ui.B5WarsJPanel;
import net.b5gamer.map.hex.HexMap;

public class GameInterface extends B5WarsJPanel {

	private static final long serialVersionUID = 3162833789459453799L;
	
	public static final String TITLE = "B5Wars v0.1";
	public static final Color  BACKGROUND_COLOR = new Color(0, 0, 32);
	public static final Color  FOREGROUND_COLOR = new Color(204, 204, 204);
	public static final Color  BORDER_COLOR     = new Color(64, 64, 128);
	public static final Font   FONT             = new Font("Verdana", Font.PLAIN, 11);	
	
	protected final Game game;
	
	public GameInterface(Game game) throws IOException {
		super(new BorderLayout());
		
		if (game == null) {
            throw new IllegalArgumentException("game cannot be null");
        } 		
		this.game = game;
		
		GameStatusBar statusBar = new GameStatusBar(BACKGROUND_COLOR, FOREGROUND_COLOR, FONT);
		statusBar.setPreferredSize(new Dimension(getPreferredSize().width, 18));
		add(statusBar, BorderLayout.SOUTH);	

		MapControl mapControl = new MapControl();
		mapControl.addStatusListener(statusBar);
        mapControl.setBorderColor(BORDER_COLOR);
        mapControl.setOverviewBoxColor(new Color(170, 0, 0));
        mapControl.setMap(new HexMap());
		add(mapControl, BorderLayout.CENTER);		

		ToolBar toolBar = new ToolBar(mapControl);
		toolBar.setLayout(new FlowLayout(FlowLayout.LEADING, 1, 1));
		toolBar.setPreferredSize(new Dimension(getPreferredSize().width, 32));
		toolBar.setBackground(BACKGROUND_COLOR);
		toolBar.setForeground(FOREGROUND_COLOR);
		toolBar.setFont(FONT);
		add(toolBar, BorderLayout.NORTH);		
	}
	
}
