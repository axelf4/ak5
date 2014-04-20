/**
 * 
 */
package org.gamelib.util;

import org.gamelib.graphics.Batch;
import org.gamelib.graphics.Texture;
import org.gamelib.util.math.geom.Rectangle;

/** A two-dimensional animation integrated into an larger image.
 * 
 * @author pwnedary */
public class Sprite {
	public Texture image;
	Rectangle[] subImages;
	/** the current frame */
	int frame;
	/** the duration of each frame */
	int duration;
	float step_counter;
	boolean flipped = false;
	public double rotation;

	public Sprite(int duration, Rectangle... subImages) {
		this.subImages = subImages;
		this.duration = duration;
	}

	public Sprite(Texture image, int duration, Rectangle... subImages) {
		this(duration, subImages);
		this.image = image;
	}

	public Sprite(Rectangle... subImages) {
		this(5, subImages);
	}

	/** Draws the current sprite. */
	public void draw(Batch batch, float delta, int x, int y) {
		Rectangle rectangle = subImages[frame];
		int sx = rectangle.getX();
		int sy = rectangle.getY();
		int width = rectangle.getWidth();
		int height = rectangle.getHeight();
		int flip = (flipped ? width : 0);

		// g.rotate(rotation, x + width / 2, y + height / 2);
		batch.draw(image, x + flip, y, x + width - flip, y + height, sx, sy, sx + width, sy + height);
		// g.rotate(-rotation, x + width / 2, y + height / 2);

		if ((step_counter += 35f * delta) > duration && (step_counter = 0) == 0 && ++frame >= subImages.length) frame = 0;
	}

	/** loads an image animation
	 * 
	 * @param nFrames amount of frames
	 * @param x start x coordinate
	 * @param y start y coordinate
	 * @param width width of each subimage
	 * @param height height of each subimage
	 * @param row amount on each row
	 * @param spacing pixels between the subimages
	 * @return rectangles as subimages */
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

}
