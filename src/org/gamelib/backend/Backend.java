/**
 * 
 */
package org.gamelib.backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.gamelib.Drawable;
import org.gamelib.FixedTimestepLoop;
import org.gamelib.Handler;
import org.gamelib.Handler.Event;
import org.gamelib.Loop.LoopListener;
import org.gamelib.backend.Input.Key;
import org.gamelib.util.Disposable;
import org.gamelib.util.geom.Rectangle;

/**
 * The class responsible for the technical stuff, such as collecting input and processing it.
 * 
 * @author pwnedary
 */
public interface Backend extends Disposable {
	/** Starts every aspect of this {@link Backend}. */
	void start(Configuration configuration, Handler handler);

	/** Stops every used resource. */
	void stop();

	/** Prepares a draw to the canvas, with <code>callback</code> and completes it. */
	void draw(Drawable callback, float delta);

	/** @return system time in milliseconds */
	long getTime();

	/** @return whether the user hasn't clicked the 'X' */
	boolean keepRunning();

	/** @param title the new window title */
	void setTitle(String title);

	/** @return the size of the canvas drawn on. */
	Rectangle getSize();

	/** @return the width of the canvas */
	int getWidth();

	/** @return the width of the canvas */
	int getHeight();

	/** @return a {@link Graphics} context to draw on */
	Graphics getGraphics();

	/** @return a {@link Graphics} context to draw on {@code image} */
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

	public abstract class BackendImpl implements Backend {
		protected Configuration configuration;

		/* * The {@link Thread} running in the background * / private Thread thread; */
		private boolean running;
		private Handler handler;

		@Override
		public void start(Configuration configuration, Handler handler) {
			this.configuration = configuration;
			this.handler = handler;

			running = true;
			new Thread(configuration.getProperty("loop", new FixedTimestepLoop(new DefaultLoopListener())), handler.toString()).start();
		}

		protected abstract void start();

		@Override
		public void stop() {
			this.running = false;
		}

		@Override
		public boolean keepRunning() {
			return running;
		}

		@Override
		public InputStream getResourceAsStream(String name) {
			return Thread.currentThread().getContextClassLoader().getResourceAsStream(name);
		}

		public class DefaultLoopListener implements LoopListener {
			@Override
			public void start() {
				BackendImpl.this.start();
				handler.handle(new Event.Create()); // Initialize game lastly
			}

			@Override
			public void stop() {
				handler.handle(new Event.Dispose());
				dispose();
			}

			@Override
			public void tick(float delta) {
				if (getInput().keyPressed(Key.KEY_ESCAPE)) BackendImpl.this.stop();
				handler.handle(new Event.Tick(delta));
			}

			@Override
			public void draw(float delta) {
				BackendImpl.this.draw(new Drawable() {
					@Override
					public void draw(Graphics g, float delta) {
						handler.handle(new Event.Draw(g, delta));
					}
				}, delta);
			}

			@Override
			public boolean keepRunning() {
				return BackendImpl.this.keepRunning();
			}
		}
	}
}
