/**
 * 
 */
package org.gamelib.util;

import java.awt.Rectangle;

import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;

/**
 * A two-dimensional animation integrated into an larger image.
 * @author pwnedary
 */
public class Sprite {

	public Rectangle[] subImages;
	/** the current frame */
	int frame;
	/** the duration of each frame */
	public int duration;
	float step_counter;
	boolean flipped = false;
	public double rotation;
	@Deprecated
	public String name;
	
	public Sprite(int duration, Rectangle... subImages) {
		this.subImages = subImages;
		this.duration = duration;
	}

	public Sprite(Rectangle... subImages) {
		this(5, subImages);
	}

	/** Draws the current sprite. */
	public void draw(Graphics g, float delta, Image image, int x, int y) {
		Rectangle rectangle = subImages[frame];
		int sx = rectangle.x;
		int sy = rectangle.y;
		int width = rectangle.width;
		int height = rectangle.height;
		int flip = (flipped ? width : 0);

		// g.rotate(rotation, x + width / 2, y + height / 2);
		g.drawImage(image, x + flip, y, x + width - flip, y + height, sx, sy, sx + width, sy + height);
		// g.rotate(-rotation, x + width / 2, y + height / 2);

		if ((step_counter += 35f * delta) > duration && (step_counter = 0) == 0 && ++frame >= subImages.length) frame = 0;
	}

	/**
	 * loads an image animation
	 * @param nFrames amount of frames
	 * @param x start x coordinate
	 * @param y start y coordinate
	 * @param width width of each subimage
	 * @param height height of each subimage
	 * @param row amount on each row
	 * @param spacing pixels between the subimages
	 * @return rectangles as subimages
	 */
	public static final Rectangle[] load(int nFrames, int x, int y, int width, int height, int row, int spacing) {
		Rectangle[] subImages = new Rectangle[nFrames];
		for (int i = 0; i < nFrames; i++)
			subImages[i] = new Rectangle(x + (width + spacing) * (i % row), y + (height + spacing) * (i / row), width, height);
		return subImages;
	}

	/** @return the current frame */
	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}
	
	public void setHorizontalFlip(boolean flipped) {
		this.flipped = flipped;
	}
	
	/* OLD DEPRECATED METHODS */
	
	@Deprecated
	public Sprite(String name, int duration, Rectangle... subImages) {
		this.name = name;
		this.subImages = subImages;
		this.duration = duration;
	}

	@Deprecated
	public Sprite(String name, Rectangle... subImages) {
		this(name, 5, subImages);
	}
	
	@Deprecated
	public void draw(Graphics g, Image image, int x, int y, int flip) {
		Rectangle rectangle = subImages[frame];
		int sx = rectangle.x;
		int sy = rectangle.y;
		int width = rectangle.width;
		int height = rectangle.height;

		// g.rotate(rotation, x + width / 2, y + height / 2);
		g.drawImage(image, x + flip, y, x + width - flip, y + height, sx, sy, sx + width, sy + height);
		// g.rotate(-rotation, x + width / 2, y + height / 2);

		if (++step_counter > duration) {
			step_counter = 0;
			if (++frame >= subImages.length) frame = 0;
		}
	}

}
