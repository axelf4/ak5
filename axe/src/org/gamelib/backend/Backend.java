/**
 * 
 */
package org.gamelib.backend;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.gamelib.Drawable;
import org.gamelib.Event;
import org.gamelib.FixedTimestepLoop;
import org.gamelib.Handler;
import org.gamelib.Loop;
import org.gamelib.Loop.LoopListener;
import org.gamelib.backend.Input.Key;
import org.gamelib.graphics.GL10;
import org.gamelib.graphics.Texture;
import org.gamelib.util.Configuration;
import org.gamelib.util.Disposable;
import org.gamelib.util.geom.Rectangle;

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

	/** @return the size of the canvas drawn on. */
	Rectangle getSize();

	/** @return the width of the canvas */
	int getWidth();

	/** @return the width of the canvas */
	int getHeight();

	/** @return the processor for input */
	Input getInput();

	/** @return an {@link InputStream} for the specified resource. */
	public InputStream getResourceAsStream(String name);

	/** @return an empty image */
	public Texture createTexture(int width, int height);

	/** @return the image from the file */
	public Texture getTexture(String name) throws IOException;

	public Texture getTexture(BufferedImage img);

	/** @return the sound from the file */
	public Sound getSound(File file) throws IOException;
	
	GL10 getGL();

	public abstract class BackendImpl implements Backend {
		protected Configuration configuration;

		/* * The {@link Thread} running in the background * / private Thread thread; */
		private boolean running;
		protected Handler handler;
		private Loop loop;

		@Override
		public void start(Configuration configuration, Handler handler) {
			this.configuration = configuration;
			this.handler = handler;

			running = true;
			new Thread(loop = configuration.getProperty("loop", new FixedTimestepLoop(new DefaultLoopListener())), handler.toString()).start();
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
		public Loop getLoop() {
			return loop;
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
				getInput().poll();
				if (getInput().keyPressed(Key.KEY_ESCAPE)) BackendImpl.this.stop();
				handler.handle(new Event.Tick(delta));
			}

			@Override
			public void draw(float delta) {
				BackendImpl.this.draw(new Drawable() {
					@Override
					public void draw(GL10 gl, float delta) {
						handler.handle(new Event.Draw(gl, delta));
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
