/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.image.BufferedImage;

import org.gamelib.backend.Image;

/**
 * @author Axel
 */
public class Java2DImage implements Image {

	public BufferedImage bufferedImage;
	private int width, height;

	/**
	 * 
	 */
	public Java2DImage(BufferedImage img) {
		this.bufferedImage = img;
		setWidth(bufferedImage.getWidth());
		setHeight(bufferedImage.getHeight());
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Image#getWidth()
	 */
	@Override
	public int getWidth() {
		return width;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Image#setWidth(int)
	 */
	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Image#getHeight()
	 */
	@Override
	public int getHeight() {
		return height;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.backend.Image#setHeight(int)
	 */
	@Override
	public void setHeight(int height) {
		this.height = height;
	}

}
