package net.b5gamer.b5wars.ui.menu;

import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;

import net.b5gamer.b5wars.ui.B5WarsJFrame;
import net.b5gamer.b5wars.ui.B5WarsJPanel;

public class MenuFrame extends B5WarsJFrame implements ActionListener {

	private static final long serialVersionUID = -262594949451909461L;

	public MenuFrame() {
        super(400, 400);
        setResizable(false);
        
        setContentPane(new B5WarsJPanel(null));
              
		JButton unitsButton = new JButton("Unit Viewer");
		unitsButton.setSize(new Dimension(140, 25));
		unitsButton.setLocation(49, 241);
//		unitsButton.setForeground(new Color(0, 0, 32));
//		unitsButton.setFont(new Font("Arial", Font.BOLD, 12));
		unitsButton.addActionListener(this);
		unitsButton.setActionCommand("UnitViewer");
		getContentPane().add(unitsButton);
				
		JButton scsButton = new JButton("SCS Editor");
		scsButton.setSize(new Dimension(140, 25));
		scsButton.setLocation(49, 274);
//		scsButton.setForeground(new Color(0, 0, 32));
//		scsButton.setFont(new Font("Arial", Font.BOLD, 12));
		scsButton.addActionListener(this);
		scsButton.setActionCommand("SCSEditor");
		getContentPane().add(scsButton);

		JButton fleetsButton = new JButton("Fleet Builder");
		fleetsButton.setToolTipText("Not yet implemented");
		fleetsButton.setSize(new Dimension(140, 25));
		fleetsButton.setLocation(49, 307);
//		fleetsButton.setForeground(new Color(0, 0, 32));
//		fleetsButton.setFont(new Font("Arial", Font.BOLD, 12));
		fleetsButton.setEnabled(false);
		getContentPane().add(fleetsButton);

		
		JButton mapsButton = new JButton("Map Designer");
		mapsButton.setToolTipText("Not yet implemented");
		mapsButton.setSize(new Dimension(140, 25));
		mapsButton.setLocation(209, 241);
//		mapsButton.setForeground(new Color(0, 0, 32));
//		mapsButton.setFont(new Font("Arial", Font.BOLD, 12));
		mapsButton.setEnabled(false);
		getContentPane().add(mapsButton);

		JButton scenariosButton = new JButton("Scenario Designer");
		scenariosButton.setToolTipText("Not yet implemented");
		scenariosButton.setSize(new Dimension(140, 25));
		scenariosButton.setLocation(209, 274);
//		scenariosButton.setForeground(new Color(0, 0, 32));
//		scenariosButton.setFont(new Font("Arial", Font.BOLD, 12));
		scenariosButton.setEnabled(false);
		getContentPane().add(scenariosButton);

		JButton gamesButton = new JButton("Sample Game");
		gamesButton.setSize(new Dimension(140, 25));
		gamesButton.setLocation(209, 307);
//		gamesButton.setForeground(new Color(0, 0, 32));
//		gamesButton.setFont(new Font("Arial", Font.BOLD, 12));
		gamesButton.addActionListener(this);
		gamesButton.setActionCommand("SampleGame");
		getContentPane().add(gamesButton);

        
		JButton creditsButton = new JButton("Credits");
		creditsButton.setSize(new Dimension(104, 25));
		creditsButton.setLocation(147, 340);
//		creditsButton.setForeground(new Color(0, 0, 32));
//		creditsButton.setFont(new Font("Arial", Font.BOLD, 12));
		creditsButton.setEnabled(false);
		getContentPane().add(creditsButton);

		
//		JLabel version = new JLabel("Version " + VERSION, JLabel.CENTER);
//		version.setSize(new Dimension(100, 20));
//		version.setLocation(149, 85);
//		version.setForeground(new Color(170, 0, 0));
//		version.setFont(new Font("Arial", Font.BOLD, 16));
//		getContentPane().add(version);
		
        try {
			JLabel backdrop = new JLabel(new ImageIcon(ImageIO.read(this.getClass().getResourceAsStream("menu_background.png"))));
			backdrop.setSize(new Dimension(400, 400));
			backdrop.setLocation(0, 0);
			getContentPane().add(backdrop);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
        pack();
        setVisible(true);		
	}
	
	public void actionPerformed(ActionEvent e) {
	    try {
	    	new ProcessBuilder(e.getActionCommand() + ".bat").start();
		} catch (IOException er) {
			er.printStackTrace();
		}
	}
	
}
