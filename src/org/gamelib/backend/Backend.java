/**
 * 
 */
package org.gamelib.backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.gamelib.Destroyable;
import org.gamelib.Drawable;
import org.gamelib.Game;
import org.gamelib.Input;
import org.gamelib.util.geom.Rectangle;

/**
 * The class responsible for the technical stuff, such as collecting input and processing it.
 * @author pwnedary
 */
public interface Backend extends Destroyable {
	/** Starts every aspect of this {@link Backend}. */
	void start(Game game);

	/** Stops every used resource. */
	void stop();

	/** Prepares a draw to the canvas, with <code>callback</code> and completes it. */
	void draw(Drawable callback, float delta);

	/** @return system time in milliseconds */
	long getTime();

	/** @return whether the user has clicked the 'X' */
	boolean shouldClose();

	/** @param title the new window title */
	void setTitle(String title);

	/** @return the size of the canvas drawn on. */
	Rectangle getSize();

	/** @return the width of the canvas */
	int getWidth();

	/** @return the width of the canvas */
	int getHeight();

	/** @return a {@link Graphics} context to draw on. */
	Graphics getGraphics();

	/** @return a {@link Graphics} context to draw on the specified image. */
	Graphics getGraphics(Image image);

	/** @return the processor for input */
	Input getInput();

	/** @return an {@link InputStream} for the specified resource. */
	public InputStream getResourceAsStream(String name);

	/** @return the image from the file */
	public Image getImage(File file) throws IOException;

	/** @return an empty image */
	public Image createImage(int width, int height);

	public Image getImage(BufferedImage img);

	/** @return the sound from the file */
	public Sound getSound(File file) throws IOException;
}
