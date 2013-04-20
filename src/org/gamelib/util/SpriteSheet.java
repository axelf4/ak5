/**
 * 
 */
package org.gamelib.util;

import java.awt.Rectangle;
import java.awt.image.ImageObserver;

import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;

/**
 * An image divided into animated sprites.
 * 
 * @author pwnedary
 * 
 */
public class SpriteSheet {

	/**
	 * 
	 */
	private Image image;
	/**
	 * All sprites
	 */
	private Sprite[] spriteSheet;

	private int sprite;
	private boolean flippedHorizontal;

	/**
	 * creates a new spritesheet
	 */
	public SpriteSheet(Image image, Sprite... spriteSheet) {
		this.image = image;
		this.spriteSheet = spriteSheet;
	}
	
	public void draw(Graphics g, int x, int y) {
		Sprite sprite2 = spriteSheet[sprite];
		Rectangle rectangle = spriteSheet[sprite].subImages[sprite2.frame];
		int sx = rectangle.x;
		int sy = rectangle.y;
		int width = rectangle.width;
		int height = rectangle.height;
		
		int flip = (flippedHorizontal ? width : 0);
		spriteSheet[sprite].draw(g, image, x, y, flip);
	}
	
	public Sprite addSprite(Sprite sprite) {
		Sprite[] tmp = spriteSheet;
		spriteSheet = new Sprite[tmp.length + 1];
		int i;
		for (i = 0; i < tmp.length; i++) {
			spriteSheet[i] = tmp[i];
		}
		return (spriteSheet[i] = sprite);
	}

	public void setSprite(String string) {
		for (int i = 0; i < spriteSheet.length; i++)
			if (spriteSheet[i].name.equalsIgnoreCase(string)) {
				/*if (i != sprite)
					frame = 0;*/
				sprite = i;
				return;
			}
		throw new RuntimeException("No sprite called: " + string);
	}

	public void setSprite(int i) {
		sprite = i;
	}
	
	public Sprite getSprite(String string) {
		for (int i = 0; i < spriteSheet.length; i++)
			if (spriteSheet[i].name.equalsIgnoreCase(string)) {
				return spriteSheet[i];
			}
		throw new RuntimeException("No sprite called: " + string);
	}

	public void flipHorizontal() {
		flippedHorizontal = !flippedHorizontal;
	}

	public void setHorizontalFlip(boolean flippedHorizontal) {
		this.flippedHorizontal = flippedHorizontal;
	}

}
