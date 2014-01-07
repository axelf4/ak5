/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.Graphics2D;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;
import java.util.LinkedList;
import java.util.Queue;

import org.gamelib.backend.Color;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;

/**
 * @author pwnedary
 * @see java.awt.Graphics
 */
public class Java2DGraphics implements Graphics {
	protected Graphics2D g2d;
	int width, height;
	protected Color currentColor;
	private Queue<AffineTransform> transforms = new LinkedList<>();

	public Java2DGraphics(java.awt.Graphics g, int width, int height) {
		this.g2d = (Graphics2D) g;
		this.width = width;
		this.height = height;
	}

	@Override
	public void setColor(Color color) {
		g2d.setColor((this.currentColor = color).toAWT());
	}

	@Override
	public Color getColor() {
		return currentColor;
	}

	@Override
	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		BufferedImage bufferedImage = ((Java2DImage) img).bufferedImage;
		g2d.drawImage(bufferedImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}

	@Override
	public void fillRect(int x, int y, int width, int height) {
		g2d.fillRect(x, y, width, height);
	}

	@Override
	public void drawRect(int x, int y, int width, int height) {
		g2d.drawRect(x, y, width, height);
	}

	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		g2d.drawLine(x1, y1, x2, y2);
	}

	@Override
	public void dispose() {
		g2d.dispose();
	}

	/** @return the awt {@link Graphics2D} used to draw */
	public Graphics2D getGraphics2D() {
		return g2d;
	}

	@Override
	public void clear() {
		g2d.fillRect(0, 0, width, height); // g.clearRect(0, 0, width, height);
	}

	@Override
	public void drawCube(int x, int y, int z, int width, int height, int depth) {
		throw new UnsupportedOperationException();
	}

	@Override
	public void translate(float x, float y, float z) {
		g2d.translate((int) Math.floor(x), (int) Math.floor(y));
	}

	@Override
	public void scale(float sx, float sy, float sz) {
		g2d.scale(sx, sy);
	}

	@Override
	public void rotate(double theta) {
		g2d.rotate(theta);
	}

	@Override
	public void begin() {}

	@Override
	public void end() {}

	@Override
	public void push() {
		transforms.add(g2d.getTransform());
	}

	@Override
	public void pop() {
		if (transforms.isEmpty()) throw new IllegalStateException("No pushed transforms.");
		else g2d.setTransform(transforms.poll());
	}
}
