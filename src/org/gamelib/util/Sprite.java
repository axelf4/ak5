/**
 * 
 */
package org.gamelib.util;

import java.awt.Rectangle;

import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;


/**
 * An two-dimensional animation integrated into an larger image.
 * 
 * @author pwnedary
 * 
 */
public class Sprite {

	public String name;
	public Rectangle[] subImages;
	/** The frame index. */
	public int frame;
	public int duration;
	private float step_counter;
	public double rotation;

	public Sprite(String name, int duration, Rectangle... subImages) {
		this.name = name;
		this.subImages = subImages;
		this.duration = duration;
	}

	public Sprite(String name, Rectangle... subImages) {
		this(name, 5, subImages);
	}

	public Sprite(Rectangle... subImages) {
		this("", subImages);
	}
	
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
			if (++frame >= subImages.length)
				frame = 0;
		}
	}
	
	public void draw(Graphics g, float delta, Image image, int x, int y, int flip) {
		Rectangle rectangle = subImages[frame];
		int sx = rectangle.x;
		int sy = rectangle.y;
		int width = rectangle.width;
		int height = rectangle.height;
		
		// g.rotate(rotation, x + width / 2, y + height / 2);
		g.drawImage(image, x + flip, y, x + width - flip, y + height, sx, sy, sx + width, sy + height);
		// g.rotate(-rotation, x + width / 2, y + height / 2);
		
		step_counter += 35.0 * delta;
		// System.out.println("counter: " + step_counter + " delta: " + delta);
		if (step_counter > duration) {
			step_counter = 0;
			if (++frame >= subImages.length)
				frame = 0;
			System.out.println("frame: " + frame);
		}
	}

	/**
	 * loads an image animation
	 * 
	 * @param nFrames amount of frames
	 * @param x start x coordinate
	 * @param y start y coordinate
	 * @param width width of each subimage
	 * @param height height of each subimage
	 * @param hAmount amount on each row
	 * @param spacing pixels between the subimages
	 * @return rectangles as subimages
	 */
	public static final Rectangle[] load(int nFrames, int x, int y, int width, int height, int hAmount, int spacing) {
		// TODO
		Rectangle[] subImages = new Rectangle[nFrames];
		for (int i = 0; i < nFrames; i++) {
			// System.out.println("x: " + 1 * (i % hAmount) + " y: " + 1 * (i / hAmount));
			subImages[i] = new Rectangle(x + (width + spacing) * (i % hAmount), y + (height + spacing) * (i / hAmount), width, height);
		}
		return subImages;
	}

	public int getFrame() {
		return frame;
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

}
