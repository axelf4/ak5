/**
 * 
 */
package org.gamelib.backend.java2D;

import java.awt.Component;
import java.awt.EventQueue;
import java.awt.KeyEventDispatcher;
import java.awt.KeyboardFocusManager;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import org.gamelib.Input;

/**
 * @author Axel
 *
 */
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
