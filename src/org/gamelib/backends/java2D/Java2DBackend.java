/**
 * 
 */
package org.gamelib.backends.java2D;

import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
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
import org.gamelib.Graphics;
import org.gamelib.Input;
import org.gamelib.backends.Backend;

/**
 * @author pwnedary
 * 
 */
public class Java2DBackend extends Backend {

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
		return graphics == null ? graphics = new Java2DGraphics() : graphics;
	}

	public Input getInput() {
		return new Java2DInput(container);
	}

	private class Java2DGraphics implements Graphics {

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Graphics#setColor(java.awt.Color) */
		@Override
		public void setColor(Color c) {
			panel.graphics2d.setColor(c);
		}

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Graphics#drawImage(java.awt.Image, int, int, int,
		 * int, int, int, int, int) */
		@Override
		public void drawImage(Image img, int dx1, int dy1, int dx2, int dy2, int sx1, int sy1, int sx2, int sy2) {
			panel.graphics2d.drawImage(img, dx1, dy1, dx2, dy2, sx1, sy1, sx2, sy2, null);
		}

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Graphics#fillRect(int, int, int, int) */
		@Override
		public void fillRect(int x, int y, int width, int height) {
			panel.graphics2d.fillRect(x, y, width, height);
		}

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Graphics#drawString(java.lang.String, int, int) */
		@Override
		public void drawString(String str, int x, int y) {
			panel.graphics2d.drawString(str, x, y);
		}

		/* (non-Javadoc)
		 * 
		 * @see org.gamelib.Graphics#drawRect(int, int, int, int) */
		@Override
		public void drawRect(int x, int y, int width, int height) {
			panel.graphics2d.drawRect(x, y, width, height);
		}

	}

	public class Java2DInput extends Input implements KeyEventDispatcher, MouseListener, MouseMotionListener, MouseWheelListener {
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
			mouseEvent(MOUSE_MOVED, e.getButton(), e.getPoint());
		}

		@Override
		public void mousePressed(java.awt.event.MouseEvent e) {
			mouseEvent(MOUSE_PRESSED, e.getButton(), e.getPoint());
		}

		@Override
		public void mouseReleased(java.awt.event.MouseEvent e) {
			mouseEvent(MOUSE_RELEASED, e.getButton(), e.getPoint());
		}

		@Override
		public void mouseDragged(MouseEvent e) {
			mouseEvent(MOUSE_DRAGGED, e.getButton(), e.getPoint());
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			mouseEvent(MOUSE_CLICKED, e.getButton(), e.getPoint());
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
	}

	/* (non-Javadoc)
	 * 
	 * @see org.gamelib.backends.Backend#screenUpdate() */
	@Override
	public void screenUpdate() {
		panel.repaint();
	}

}
