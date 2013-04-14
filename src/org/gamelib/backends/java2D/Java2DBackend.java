/**
 * 
 */
package org.gamelib.backends.java2D;

import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import javax.swing.JApplet;
import javax.swing.JFrame;

import org.gamelib.DisplayMode;
import org.gamelib.Game;
import org.gamelib.Input;
import org.gamelib.backends.Backend;
import org.gamelib.graphics.Graphics;
import org.gamelib.graphics.Image;

/**
 * @author pwnedary
 * 
 */
public class Java2DBackend implements Backend {

	private Container container;
	private Java2dPanel panel;

	private Java2DGraphics graphics;

	/**
	 * 
	 */
	public Java2DBackend(Container container) {
		(this.container = container).add(panel = new Java2dPanel());
	}

	public void start(Game instance, DisplayMode mode) {
		if (container instanceof JFrame) {
			((JFrame) container).setTitle(instance.toString());
			((JFrame) container).setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setFullscreen((JFrame) container, mode.isFullscreen());
			if (!mode.isFullscreen())
				container.setSize(new Dimension(mode.getWidth(), mode.getHeight()));
		}
		if (container instanceof JApplet)
			((JApplet) container).resize(new Dimension(mode.getWidth(), mode.getHeight()));
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

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.backends.Backend#getGraphics() */
	@Override
	public Graphics getGraphics() {
		return graphics == null ? graphics = new Java2DGraphics(panel) : graphics;
	}

	public Input getInput() {
		return new Java2DInput(panel);
	}

	private class Java2DInput extends Input implements KeyEventDispatcher, MouseListener, MouseMotionListener, MouseWheelListener {
		public Java2DInput(Component component) {
			KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
			component.addMouseListener(this);
			component.addMouseMotionListener(this);
			component.addMouseWheelListener(this);
		}

		/* (non-Javadoc)
		 * 
		 * @see
		 * java.awt.KeyEventDispatcher#dispatchKeyEvent(java.awt.event.KeyEvent) */
		@Override
		public boolean dispatchKeyEvent(KeyEvent e) {
			assert EventQueue.isDispatchThread();
			keyEvent(e.getID(), e.getKeyCode());
			return false;
		}

		/* Mouse listeners */

		@Override
		public void mouseMoved(java.awt.event.MouseEvent e) {
			mouseEvent(MOUSE_MOVED, e.getButton() - 1, e.getPoint());
		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
			mouseEvent(MOUSE_PRESSED, e.getButton() - 1, e.getPoint());
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {
			mouseEvent(MOUSE_RELEASED, e.getButton() - 1, e.getPoint());
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			mouseEvent(MOUSE_DRAGGED, e.getButton() - 1, e.getPoint());
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			mouseEvent(MOUSE_CLICKED, e.getButton() - 1, e.getPoint());
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			mouseWheelEvent(e.getPreciseWheelRotation());
		}

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Input#poll() */
		@Override
		public void poll() {
		}

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Input#translateKeyCode(int) */
		@Override
		public int translateKeyCode(int keyCode) {
			switch (keyCode) {
			case KeyEvent.VK_ESCAPE:
				return Key.KEY_ESCAPE;
			default:
				return Key.KEY_UNDEFINED;
			}
		}
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.backends.Backend#screenUpdate() */
	@Override
	public void screenUpdate() {
		panel.repaint();
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.backends.Backend#getTime() */
	@Override
	public long getTime() {
		return System.nanoTime() / 1000000;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.backends.Backend#shouldClose() */
	@Override
	public boolean shouldClose() {
		return false;
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.backends.Backend#setTitle(java.lang.String) */
	@Override
	public void setTitle(String s) {
		if (container instanceof JFrame) ((JFrame) container).setTitle(s);
	}

	/* (non-Javadoc)
	 * @see org.gamelib.backends.Backend#getGraphics(org.gamelib.graphics.Image)
	 */
	@Override
	public Graphics getGraphics(Image img) {
		// TODO Auto-generated method stub
		return null;
	}

}
