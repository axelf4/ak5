/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.image.BufferedImage;

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

}
