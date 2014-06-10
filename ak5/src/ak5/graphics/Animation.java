/**
 * 
 */
package ak5.graphics;

import ak5.Drawable;
import ak5.util.Disposable;

/** A 2-D animation suitable for sprites and spritesheets.
 * 
 * @author pwnedary */
public class Animation implements Drawable, Disposable {
	/** Array of all keyframes. */
	private Texture[] frames;
	/** The duration between frames. */
	private float duration;
	/** Internal counter of how long the animation's come. */
	private float step;
	private Batch batch;

	public Animation(float duration, Texture... frames) {
		this.duration = duration;
		this.frames = frames;
	}

	/** Returns the current showing keyframe by taking notion of the delta time between the last time this method was
	 * called.
	 * 
	 * @param delta the delta time
	 * @return the current frame */
	public Texture getFrame(float delta) {
		return frames[(int) ((step = (step + delta) % (duration * frames.length)) / duration)];
	}

	/** Returns the duration between frames.
	 * 
	 * @return the duration between frames */
	public float getDuration() {
		return duration;
	}
	
	public void setBatch(QuadBatch batch) {
		this.batch = batch;
	}
	
	@Override
	public void draw(GL10 gl, float delta) {
		Texture texture = getFrame(delta);
		batch.draw(texture, 0, 0, texture.getWidth(), texture.getHeight());
	}

	@Override
	public void dispose() {
		for (int i = 0; i < frames.length; i++)
			frames[i].dispose();
	}

	/** Returns an array of animation compatible frames.
	 * 
	 * @param frameCount the number of frames
	 * @param x start x coordinate
	 * @param y start y coordinate
	 * @param width width of each frame
	 * @param height height of each frame
	 * @param row number of frames on each row
	 * @param spacing pixels between frames
	 * @return array of frames */
	public static Texture[] sheet(Texture texture, int frameCount, int x, int y, int width, int height, int row, int spacing) {
		Texture[] frames = new Texture[frameCount];
		for (int i = 0; i < frameCount; i++)
			frames[i] = texture.region(x + (width + spacing) * (i % row), y + (height + spacing) * (i / row), width, height);
		return frames;
	}
}
