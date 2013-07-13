/**
 * 
 */
package org.gamelib.util;

import java.awt.Rectangle;

import org.gamelib.backend.Graphics;
import org.gamelib.backend.Image;

/**
 * An image divided into animated sprites.
 * @author pwnedary
 */
public class SpriteSheet {

	private Image image;
	/** the array containing all sprites */
	private Sprite[] spriteSheet;
	/** the current sprite index */
	private int sprite;
	private boolean flippedHorizontal;

	/**
	 * creates a new spritesheet
	 */
	public SpriteSheet(Image image, Sprite... spriteSheet) {
		this.image = image;
		this.spriteSheet = spriteSheet;
	}

	public void draw(Graphics g, float delta, int x, int y) {
		Sprite sprite2 = spriteSheet[sprite];
		sprite2.setHorizontalFlip(flippedHorizontal);

		spriteSheet[sprite].draw(g, delta, image, x, y);
	}

	/**
	 * @param sprite the sprite to add
	 * @return the identifier of the added sprite
	 */
	public int addSprite(Sprite sprite) {
		Sprite[] tmp = spriteSheet;
		spriteSheet = new Sprite[tmp.length + 1];
		int i;
		for (i = 0; i < tmp.length; i++)
			spriteSheet[i] = tmp[i];
		spriteSheet[i] = sprite;
		return i;
	}

	public void setSprite(int id) {
		if (id < 0 || id >= spriteSheet.length) throw new ArrayIndexOutOfBoundsException("No sprite with id: " + id);
		this.sprite = id;
	}

	/** @return the sprite with the <code>id</code> */
	public Sprite getSprite(int id) {
		return spriteSheet[id];
	}

	/** @return the current sprite */
	public Sprite getSprite() {
		return getSprite(sprite);
	}
	
	/* OLD METHODS */
	
	@Deprecated
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

	@Deprecated
	public void flipHorizontal() {
		flippedHorizontal = !flippedHorizontal;
	}

	@Deprecated
	public void setHorizontalFlip(boolean flippedHorizontal) {
		this.flippedHorizontal = flippedHorizontal;
	}
	
	public Sprite addSpriteOld(Sprite sprite) {
		Sprite[] tmp = spriteSheet;
		spriteSheet = new Sprite[tmp.length + 1];
		int i;
		for (i = 0; i < tmp.length; i++) {
			spriteSheet[i] = tmp[i];
		}
		return (spriteSheet[i] = sprite);
	}

	public void setSpriteOld(String string) {
		for (int i = 0; i < spriteSheet.length; i++)
			if (spriteSheet[i].name.equalsIgnoreCase(string)) {
				/*
				 * if (i != sprite) frame = 0;
				 */
				sprite = i;
				return;
			}
		throw new RuntimeException("No sprite called: " + string);
	}

	public void setSpriteOld(int i) {
		sprite = i;
	}

	public Sprite getSpriteOld(String string) {
		for (int i = 0; i < spriteSheet.length; i++)
			if (spriteSheet[i].name.equalsIgnoreCase(string)) {
				return spriteSheet[i];
			}
		throw new RuntimeException("No sprite called: " + string);
	}

}
