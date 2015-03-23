package client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

import javax.swing.JPanel;

/*
 * AM > This is the network for a straight road
 */
public class MapPanel extends JPanel {

	private static final long serialVersionUID = -5133592834815202789L;
	

	public MapPanel() {
		super();
	}
	
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		int panelWidth = (int) getSize().getWidth();
		int panelHeight = (int) getSize().getHeight();
		int roadHeight = 150;
		int destinationWidth = 75;
		
		//AM > Draw a straight road
		g.setColor(Color.BLACK);
		int roadStartX = 0 + destinationWidth;
		int roadStartY = panelHeight/2 - roadHeight/2;
		int roadWidth = panelWidth - destinationWidth*2;
		int roadEndX = roadStartX+roadWidth;
		int roadEndY = roadStartY;
 		g.fillRect(roadStartX, roadStartY, roadWidth, roadHeight);
		
		//AM > Draw destination A
 		int textOffsetX = 5;
		int textOffsetY = 5;
		g.setColor(Color.GRAY);
		g.fillRect(0,roadStartY, destinationWidth,roadHeight);
		g.setColor(Color.BLACK);
		g.drawString("A", destinationWidth/2 - textOffsetX, roadStartY + roadHeight/2 + textOffsetY);
		
		//AM > Draw destination B
		g.setColor(Color.GRAY);
		g.fillRect(roadEndX, roadEndY, destinationWidth, roadHeight);
		g.setColor(Color.BLACK);
		g.drawString("B", roadEndX +destinationWidth/2 - textOffsetX, roadStartY + roadHeight/2 + textOffsetY);
		
		//AM > Draw road divider
		g.setColor(Color.WHITE);
		g.drawLine(roadStartX , panelHeight/2, roadEndX -1, panelHeight/2);
		
		//AM > Draw lane separators
		Graphics2D g2d = (Graphics2D) g.create();
		Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0);
        g2d.setStroke(dashed);
        g2d.setColor(Color.WHITE);
        g2d.drawLine(roadStartX, panelHeight/2 - roadHeight/4, roadEndX -1, panelHeight/2 - roadHeight/4);
        g2d.drawLine(roadStartX, panelHeight/2 + roadHeight/4, roadEndX -1, panelHeight/2 + roadHeight/4);
	  }
}
