/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;

/**
 * @author pwnedary
 */
public class Java2DImage implements Image {
	public BufferedImage bufferedImage;
	private int width, height;

	public Java2DImage(BufferedImage img) {
		this.bufferedImage = img;
		setWidth(bufferedImage.getWidth());
		setHeight(bufferedImage.getHeight());
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public void draw(Graphics g, float delta) {
		g.drawImage(this, 0, 0, 0 + getWidth(), 0 + getHeight(), 0, 0, 0 + getWidth(), 0 + getHeight());
	}

	@Override
	public void dispose() {}

	@Override
	public void setFilter(Filter min, Filter mag) {}

	@Override
	public void setWrap(Wrap u, Wrap v) {}

	@Override
	public int[][] getPixels() {
		final byte[] pixels = ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
		final int[][] result = new int[getWidth()][getHeight()];
		final boolean hasAlpha = bufferedImage.getAlphaRaster() != null;
		final int bitsPerPixel = hasAlpha ? 4 : 3;
		for (int pixel = 0, row = 0, col = 0; pixel < pixels.length; pixel += bitsPerPixel) {
			int abgr = (hasAlpha ? (pixels[pixel] & 0xFF) << 24 : -16777216) | // alpha
			(pixels[pixel + 1] & 0xFF) << 16 | // blue
			(pixels[pixel + 2] & 0xFF) << 8 | // green
			(pixels[pixel + 3] & 0xFF); // red
			result[row][col] = abgr;
			if (++col == width) {
				col = 0;
				row++;
			}
		}
		return result;
	}

	@Override
	public int getPixel(int x, int y) {
		return bufferedImage.getRGB(x, y);
	}

	@Deprecated
	@Override
	public byte[] getData() {
		return ((DataBufferByte) bufferedImage.getRaster().getDataBuffer()).getData();
	}

	@Override
	public Image region(int x, int y, int width, int height) {
		return new Java2DImage(bufferedImage.getSubimage(x, y, width, height));
	}
}
