package net.b5gamer.b5wars.ui.map;

import java.awt.BorderLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.border.EmptyBorder;

/**
 * !!--- work in progress ---!!
 */
public class MapViewTest {

	private static MapViewPortal mapView;
//    private static int           mapViewMouseClickX1 = 0;
//    private static int           mapViewMouseClickY1 = 0;

	public MapViewTest() {
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
	}

    private static void createAndShowGUI() {
        JFrame frame = new JFrame("Map View Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
//        MapViewTest listener = new MapViewTest();

		JPanel mainPanel = new JPanel(new BorderLayout()); 
        mainPanel.setBorder(new EmptyBorder(0, 0, 0, 0));
        mapView = new MapViewPortal();        
//        mapView.addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
//			public void mouseDragged(MouseEvent e) {
//				MapView_mouseDragged(e);
//			}
//
//			public void mouseMoved(MouseEvent e) {
//				MapView_mouseMoved(e);
//			}
//		});
//
//		MouseAdapter ma = new java.awt.event.MouseAdapter() {
//			public void mouseClicked(MouseEvent e) {
//				MapView_mouseClicked(e);
//			}
//
//			public void mousePressed(MouseEvent e) {
//				MapView_mousePressed(e);
//			}
//
//			public void mouseReleased(MouseEvent e) {
//				MapView_mouseReleased(e);
//			}
//		};
//		
//		mapView.addMouseListener(ma);
//        mapView.addMouseWheelListener(listener);
//        try {
//			mapView.setMapSurface(ImageIO.read(MapViewTest.class.getResourceAsStream("testBackground.png")));
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
        // TODO
//		mapView.setMapSurface(new MapRendererFactory().getMapRenderer(new HexMap()).render(20));
//		mapView.setMapSurface(new MapRendererFactory().getMapRenderer(new HexMap(42, 30, ExtentType.FLOATING)).render(20));
		mapView.setPreferredSize(new Dimension(800, 600));
        
        mainPanel.add(mapView, BorderLayout.CENTER);        
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.pack();
        frame.setVisible(true);
        frame.setLocationRelativeTo(null);
    }
	
//    /**
//     * Mouse dragged on the map view
//     */
//    private static void MapView_mouseDragged(MouseEvent e) {
//        int mapViewMouseClickX2 = e.getX();
//        int mapViewMouseClickY2 = e.getY();
//
//        mapView.setOffset(mapViewMouseClickX2 - mapViewMouseClickX1, mapViewMouseClickY2 - mapViewMouseClickY1);
//        mapView.repaint();
//    }
//
//    /** 
//     * Mouse is moved over the map view
//     */
//    private static void MapView_mouseMoved(MouseEvent e) {
//    } 
//    
//    /** 
//     * Mouse clicked on the map view
//     */
//    private static void MapView_mouseClicked(MouseEvent e) {
//    }
//
//    /**
//     * Mouse pressed on the map view
//     */
//    private static void MapView_mousePressed(MouseEvent e) {
//        mapViewMouseClickX1 = e.getX() - mapView.getOffsetX();
//        mapViewMouseClickY1 = e.getY() - mapView.getOffsetY();
//    } 
//
//    /**
//     * Mouse released from the map view
//     */
//    private static void MapView_mouseReleased(MouseEvent e) {
//    }
//
//    /**
//     * Mouse wheel used on the map view
//     */    
//	public void mouseWheelMoved(MouseWheelEvent e) {
//		mapView.setScale(mapView.getScale() + ((e.getWheelRotation() / 10.0) * -1.0));
//		mapView.repaint();
//	}
   
}
