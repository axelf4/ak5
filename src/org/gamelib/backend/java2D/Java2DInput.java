/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.AWTException;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.EventQueue;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.awt.image.BufferedImage;

import org.gamelib.backend.Image;
import org.gamelib.backend.Input;

/**
 * @author Axel
 */
public class Java2DInput extends Input implements KeyEventDispatcher, MouseListener, MouseMotionListener, MouseWheelListener {
	private Component component;
	private boolean grabbed;
	private int prevX, prevY;

	public Java2DInput(Component component) {
		this.component = component;
		KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(this);
		component.addMouseListener(this);
		component.addMouseMotionListener(this);
		component.addMouseWheelListener(this);
	}

	/** {@inheritDoc} */
	@Override
	public boolean dispatchKeyEvent(KeyEvent e) {
		assert EventQueue.isDispatchThread();
		keyEvent(e.getID(), e.getKeyCode());
		return false;
	}

	/* Mouse listeners */

	/** {@inheritDoc} */
	@Override
	public void mouseMoved(java.awt.event.MouseEvent e) {
		prevX = getMouseX();
		prevY = getMouseY();
		mouseEvent(MOUSE_MOVED, e.getButton() - 1, e.getX(), e.getY());
	}

	/** {@inheritDoc} */
	@Override
	public void mousePressed(java.awt.event.MouseEvent e) {
		mouseEvent(MOUSE_PRESSED, e.getButton() - 1, e.getX(), e.getY());
	}

	/** {@inheritDoc} */
	@Override
	public void mouseReleased(java.awt.event.MouseEvent e) {
		mouseEvent(MOUSE_RELEASED, e.getButton() - 1, e.getX(), e.getY());
	}

	/** {@inheritDoc} */
	@Override
	public void mouseDragged(MouseEvent e) {
		mouseEvent(MOUSE_DRAGGED, e.getButton() - 1, e.getX(), e.getY());
	}

	/** {@inheritDoc} */
	@Override
	public void mouseClicked(MouseEvent e) {
		// mouseEvent(MOUSE_CLICKED, e.getButton() - 1, e.getX(), e.getY());
	}

	/** {@inheritDoc} */
	@Override
	public void mouseEntered(MouseEvent e) {}

	/** {@inheritDoc} */
	@Override
	public void mouseExited(MouseEvent e) {
		Component component = this.component.getParent().getParent().getParent().getParent();
		if (grabbed) mouseMove(component.getX() + prevX, component.getY() + prevY);
	}

	/** {@inheritDoc} */
	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {
		mouseWheelEvent(e.getPreciseWheelRotation());
	}

	/** {@inheritDoc} */
	@Override
	public void poll() {}

	/** {@inheritDoc} */
	@Override
	public int translateKeyCode(int keyCode) {
		switch (keyCode) {
		case KeyEvent.VK_ESCAPE:
			return Key.KEY_ESCAPE;
			// numbers
		case KeyEvent.VK_1:
			return Key.KEY_1;
		case KeyEvent.VK_2:
			return Key.KEY_2;
		case KeyEvent.VK_3:
			return Key.KEY_3;
		case KeyEvent.VK_4:
			return Key.KEY_4;
		case KeyEvent.VK_5:
			return Key.KEY_5;
		case KeyEvent.VK_6:
			return Key.KEY_6;
		case KeyEvent.VK_7:
			return Key.KEY_7;
		case KeyEvent.VK_8:
			return Key.KEY_8;
		case KeyEvent.VK_9:
			return Key.KEY_9;
		case KeyEvent.VK_0:
			return Key.KEY_0;
			// letters
		case KeyEvent.VK_Q:
			return Key.KEY_Q;
		case KeyEvent.VK_W:
			return Key.KEY_W;
		case KeyEvent.VK_E:
			return Key.KEY_E;
		case KeyEvent.VK_R:
			return Key.KEY_R;
		case KeyEvent.VK_T:
			return Key.KEY_T;
		case KeyEvent.VK_Y:
			return Key.KEY_Y;
		case KeyEvent.VK_U:
			return Key.KEY_U;
		case KeyEvent.VK_I:
			return Key.KEY_I;
		case KeyEvent.VK_O:
			return Key.KEY_O;
		case KeyEvent.VK_P:
			return Key.KEY_P;
		case KeyEvent.VK_BRACELEFT:
			return Key.KEY_LBRACKET;
		case KeyEvent.VK_BRACERIGHT:
			return Key.KEY_RBRACKET;
		case KeyEvent.VK_ENTER:
			return Key.KEY_RETURN;
		case KeyEvent.VK_CONTROL:
			return Key.KEY_LCONTROL;
		case KeyEvent.VK_A:
			return Key.KEY_A;
		case KeyEvent.VK_S:
			return Key.KEY_S;
		case KeyEvent.VK_D:
			return Key.KEY_D;

		case KeyEvent.VK_SPACE:
			return Key.KEY_SPACE;

		case KeyEvent.VK_UP:
			return Key.KEY_UP;
		case KeyEvent.VK_LEFT:
			return Key.KEY_LEFT;
		case KeyEvent.VK_RIGHT:
			return Key.KEY_RIGHT;
		case KeyEvent.VK_END:
			return Key.KEY_END;
		case KeyEvent.VK_DOWN:
			return Key.KEY_DOWN;
		default:
			return Key.KEY_UNDEFINED;
		}
	}

	/** {@inheritDoc} */
	@Override
	public void mouseMove(int x, int y) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		// Search the devices for the one that draws the specified point.
		for (GraphicsDevice device : ge.getScreenDevices()) {
			for (GraphicsConfiguration config : device.getConfigurations()) {
				Rectangle bounds = config.getBounds();
				if (bounds.contains(x, y)) {
					try {
						Robot r = new Robot(device);
						r.mouseMove(x - (int) bounds.getX(), y - (int) bounds.getY()); // take advantage of screen coordinates
						return;
					} catch (AWTException e) {
						e.printStackTrace();
					}
				}
			}
		}
		throw new RuntimeException("couldn't move mouse"); // couldn't move to the point, it may be off screen.
	}

	@Override
	public void setGrabbed(boolean grabbed) {
		if (this.grabbed = grabbed) {
			setCursor(new Java2DImage(new BufferedImage(32, 32, BufferedImage.TYPE_INT_ARGB)));
		} else component.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void setCursor(Image image) {
		Toolkit toolkit = Toolkit.getDefaultToolkit();
		Cursor c = toolkit.createCustomCursor(((Java2DImage) image).bufferedImage, new Point(component.getX(), component.getY()), "img");
		component.setCursor(c);
	}
}
