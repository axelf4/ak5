/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.Graphics2D;
import java.awt.image.BufferedImage;

import org.gamelib.backend.Color;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;

/**
 * @author pwnedary
 * @see java.awt.Graphics
 */
public class Java2DGraphics implements Graphics {

	Graphics2D g;
	int width, height;
	protected float deltaX, deltaY, deltaZ;
	protected Color currentColor;

	public Java2DGraphics(java.awt.Graphics g, int width, int height) {
		this.g = (Graphics2D) g;
		this.width = width;
		this.height = height;
	}

	/** {@inheritDoc} */
	@Override
	public void setColor(Color color) {
		g.setColor((this.currentColor = color).toAWT());
	}

	/** {@inheritDoc} */
	@Override
	public Color getColor() {
		return currentColor;
	}

	/** {@inheritDoc} */
	@Override
	public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
		BufferedImage bufferedImage = ((Java2DImage) img).bufferedImage;
		g.drawImage(bufferedImage, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
	}

	/** {@inheritDoc} */
	@Override
	public void fillRect(int x, int y, int width, int height) {
		g.fillRect(x, y, width, height);
	}

	/** {@inheritDoc} */
	@Override
	public void drawRect(int x, int y, int width, int height) {
		g.drawRect(x, y, width, height);
	}

	/** {@inheritDoc} */
	@Override
	public void drawLine(int x1, int y1, int x2, int y2) {
		g.drawLine(x1, y1, x2, y2);
	}

	/** {@inheritDoc} */
	@Override
	public void dispose() {
		g.dispose();
	}

	/** @return the awt {@link Graphics2D} used to draw */
	public Graphics2D getGraphics2D() {
		return g;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Graphics#clear()
	 */
	@Override
	public void clear() {
		// g.clearRect(0, 0, width, height);
		g.fillRect(0, 0, width, height);
	}

	/** {@inheritDoc} */
	@Override
	public void drawCube(int x, int y, int z, int width, int height, int depth) {
		throw new UnsupportedOperationException();
	}

	/** {@inheritDoc} */
	@Override
	public void translate(float x, float y, float z) {
		g.translate((int) Math.floor(x), (int) Math.floor(y));
		this.deltaX += x;
		this.deltaY += y;
	}

	/** {@inheritDoc} */
	@Override
	public void translate(float x, float y, float z, int flag) {
		if ((flag & ADD) == ADD) translate(x, y, z);
		else if ((flag & SET) == SET) translate(-deltaX + x, -deltaY + y, -deltaZ + z);
	}

	/** {@inheritDoc} */
	@Override
	public void scale(float sx, float sy, float sz) {
		g.scale(sx, sy);
	}

	/** {@inheritDoc} */
	@Override
	public void rotate(double theta) {
		g.rotate(theta);
	}

	@Override
	public void begin() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void end() {
		// TODO Auto-generated method stub
		
	}
}
