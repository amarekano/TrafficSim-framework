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
		int offsetX    = 0;
		int offsetY    = 0;
		int blockX     = 0;
		int blockY     = 0;
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
		g.setColor(Color.GRAY);
		g.fillRect(x - blockX, y - blockY, offsetX + blockX*2, offsetY + blockY*2);

		/* Render Road. */
		Color old = g.getColor();
		g.setColor(Color.BLACK);
		g.fillRect(x, y, offsetX, offsetY);

		/* Render Divider. */
		g.setColor(Color.WHITE);
		g.drawLine(x + blockY, y + blockX, x + offsetX - blockY, y + offsetY - blockX);

		/* Render Stripes. */
		Graphics2D g2d = (Graphics2D) g.create();
		g2d.setStroke(new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{9}, 0));
		g2d.drawLine(x + blockY + (blockY / 2), y + blockX + (blockX / 2), x + offsetX - (blockY / 2), y + offsetY - (blockX / 2));
		g2d.drawLine(x + blockY - (blockY / 2), y + blockX - (blockX / 2), x + offsetX - 3 * (blockY / 2), y + offsetY - 3 * (blockX / 2));

		/* Render Letters. */
		g.setColor(Color.WHITE);
		g.drawString("A", x + blockY - (blockX / 2), y + blockX - (blockY / 2));
		g.drawString("B", x + offsetX + blockY + (blockX / 2), y + offsetY - blockX - (blockY / 2));

		/* Render Letters. */
		g.setColor(old);
	}
}
