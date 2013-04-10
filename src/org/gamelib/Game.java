/**
 * 
 */
package org.gamelib;

import static org.gamelib.util.Log.info;

import java.awt.Container;
import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.gamelib.backends.Backend;
import org.gamelib.backends.java2D.Java2DBackend;
import org.gamelib.resource.ResourceLoader;

/**
 * TODO distribute backend better
 * @author Axel
 * 
 */
public abstract class Game {

	protected static Game instance;

	protected Thread thread;
	public Container container;
	public Screen screen;
	@SuppressWarnings("unused")
	private HandlerRegistry handlerRegistry;
	public Input input;
	
	private Backend backend;

	/**
	 * 
	 */
	protected Game() {
		instance = instance == null ? this : instance;
	}

	protected void start(Container container) {
		// (this.container = container).add(screen = new Screen());
		DisplayMode mode = getDisplayMode();
		if (container instanceof JFrame) {
			((JFrame) container).setTitle(instance.toString());
			((JFrame) container).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setFullscreen((JFrame) container, mode.fullscreen);
			if (!mode.fullscreen)
				container.setSize(new Dimension(mode.width, mode.height));
		}
		if (container instanceof JApplet)
			((JApplet) container).resize(new Dimension(mode.width, mode.height));
		handlerRegistry = HandlerRegistry.instance();
		// input = new Input(screen);
		ResourceLoader.container = container;

		instance.initialize();
		info("Initialized " + instance.toString());
		(thread = new Thread(new FixedTimestepLoop(screen))).start();
	}
	
	protected void start(Backend backend) {
		// (this.backend = backend).start(this, getDisplayMode());
		this.backend = backend;
		backend.setTitle(instance.toString());
		
		screen = new Screen(getDisplayMode());
		System.out.println(screen.getWidth());
		handlerRegistry = HandlerRegistry.instance();
		input = backend.getInput();
		ResourceLoader.container = container;

		instance.initialize();
		info("Initialized " + instance.toString());
		(thread = new Thread(new FixedTimestepLoop(screen))).start();
	}

	protected void start() {
		// (container = new JFrame()).add(screen = new Screen());
		start(new Java2DBackend(new JFrame()));
	}

	/**
	 * TODO add java-doc
	 */
	protected abstract void initialize();

	public abstract String toString();

	protected DisplayMode getDisplayMode() {
		return DisplayMode.r800x600;
	}

	private void setFullscreen(JFrame frame, boolean fullscreen) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice graphicsDevice = ge.getDefaultScreenDevice();
		frame.setUndecorated(fullscreen);
		frame.setResizable(!fullscreen);
		if (fullscreen) {
			// Determine if full-screen mode is supported directly
			if (graphicsDevice.isFullScreenSupported()) {
				// Full-screen mode is supported
			} else {
				// Full-screen mode will be simulated
			}

			try {
				// Enter full-screen mode
				// gs.setFullScreenWindow(win);
				graphicsDevice.setFullScreenWindow(frame);
				frame.validate();
				// ...
			} finally {
				// Exit full-screen mode
				// graphicsDevice.setFullScreenWindow(null);
			}
		} else {
			graphicsDevice.setFullScreenWindow(null);
			frame.setVisible(true);
		}
	}
	
	public static Game getInstance() {
		return instance;
	}
	
	public static Backend getBackend() {
		return instance.backend;
	}
	
	public static DisplayMode getDisplayMode2() {
		return instance.getDisplayMode();
	}

}
