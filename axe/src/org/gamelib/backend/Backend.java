/**
 * 
 */
package org.gamelib.backend;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.gamelib.Drawable;
import org.gamelib.Handler;
import org.gamelib.Input;
import org.gamelib.Loop;
import org.gamelib.graphics.GL10;
import org.gamelib.graphics.Texture;
import org.gamelib.util.Configuration;
import org.gamelib.util.Disposable;
import org.gamelib.util.io.Asset;

/** The class responsible for the technical stuff, such as collecting input and processing it.
 * 
 * @author pwnedary */
public interface Backend extends Disposable {
	/** Starts every aspect of this {@link Backend}.
	 * 
	 * @param configuration the configuration matching the Backend
	 * @param handler Handler to be notified about Event.Create, Tick, Draw, etc. */
	void start(Configuration configuration, Handler handler);

	/** Stops every used resource. */
	void stop();

	/** Prepares a draw to the canvas, with <code>drawable</code> and completes it. */
	void draw(Drawable drawable, float delta);

	/** @return system time in milliseconds */
	long getTime();

	/** @return whether the user hasn't clicked the 'X' */
	boolean keepRunning();

	Loop getLoop();

	/** @param title the new window title */
	void setTitle(String title);

	/** @return the width of the canvas */
	int getWidth();

	/** @return the width of the canvas */
	int getHeight();

	/** @return the processor for input */
	Input getInput();

	/** @return an {@link InputStream} for the specified resource. */
	public InputStream getResourceAsStream(CharSequence name);
	
	Asset<?> getAsset(CharSequence path);

	/** @return an empty image */
	public Texture createTexture(int width, int height);

	/** @return the image from the file */
	public Texture getTexture(CharSequence name) throws IOException;

//	public Texture getTexture(BufferedImage img);

	/** @return the sound from the file */
	public Sound getSound(File file) throws IOException;

	GL10 getGL();
}
