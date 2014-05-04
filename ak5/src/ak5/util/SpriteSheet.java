/**
 * 
 */
package ak5.util;

import ak5.graphics.Batch;
import ak5.graphics.Texture;

/** An image divided into animated sprites.
 * 
 * @author pwnedary */
public class SpriteSheet {
	private Texture image;
	/** the array containing all sprites */
	private Sprite[] spriteSheet;
	/** the current sprite index */
	private int sprite;
	private boolean flippedHorizontal;

	/** creates a new spritesheet */
	public SpriteSheet(Texture image, Sprite... spriteSheet) {
		this.image = image;
		this.spriteSheet = spriteSheet;
	}

	public void draw(Batch batch, float delta, int x, int y) {
		Sprite sprite2 = spriteSheet[sprite];
		sprite2.setHorizontalFlip(flippedHorizontal);

		spriteSheet[sprite].draw(batch, delta, x, y);
	}

	/** @param sprite the sprite to add
	 * @return the identifier of the added sprite */
	public int addSprite(Sprite sprite) {
		Sprite[] tmp = spriteSheet;
		spriteSheet = new Sprite[tmp.length + 1];
		int i;
		for (i = 0; i < tmp.length; i++)
			spriteSheet[i] = tmp[i];
		sprite.image = image;
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

	/** @param flippedHorizontal if flipped */
	public void setHorizontalFlip(boolean flippedHorizontal) {
		this.flippedHorizontal = flippedHorizontal;
	}

}
