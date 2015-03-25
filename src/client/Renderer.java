package client;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;

public class Renderer
{
	public enum Direction
	{
		NORTH,
		EAST,
		SOUTH,
		WEST
	}
	
	public static void renderRoad(Graphics g, int x, int y, int length, int width, Direction direction)
	{
		int offsetX = 0;
		int offsetY = 0;
		int blockX  = 0;
		int blockY  = 0;
		int blockWidth = width / 2;
		
		switch(direction) {
		case NORTH:
			y -= length;
		case SOUTH:
			blockY = blockWidth;
			offsetX = width;
			offsetY = length;
			break;
		case WEST:
			x -= length;
		case EAST:
			blockX = blockWidth;
			offsetX = length;
			offsetY = width;
			break;		
		default:
			break;
		}
		
		
		/* Render Ends */
		g.setColor(Color.BLACK);
		g.fillRect(x - blockX, y - blockY, offsetX + blockX*2, offsetY + blockY*2);
		
		/* Render Road. */
		Color old = g.getColor();
		g.setColor(Color.GRAY);
		g.fillRect(x, y, offsetX, offsetY);
		
		/* Render White. */
		g.setColor(Color.WHITE);
		g.fillRect(x, y, offsetX, offsetY);
		g.drawLine(x + 30, y + 30, offsetX, offsetY);
		
		g.setColor(old);
	}
}
