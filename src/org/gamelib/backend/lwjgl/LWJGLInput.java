/**
 * 
 */
package org.gamelib.backend.lwjgl;

import java.awt.Point;

import org.gamelib.Input;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.Display;

/**
 * @author pwnedary
 */
public class LWJGLInput extends Input {

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Input#poll()
	 */
	@Override
	public void poll() {
		while (Keyboard.next()) // poll key events
			keyEvent(Keyboard.getEventKeyState() ? KEY_PRESSED : KEY_RELEASED, translateKeyCode(Keyboard.getEventKey()));
		
		// Point p = new Point(org.lwjgl.input.Mouse.getX(), Display.getHeight() - org.lwjgl.input.Mouse.getY());
		Point p = mousePosition;
		if (!org.lwjgl.input.Mouse.isGrabbed())
			p = translateMouse(org.lwjgl.input.Mouse.getX(), org.lwjgl.input.Mouse.getY());
		while (org.lwjgl.input.Mouse.next()) { // poll mouse events
			boolean pressed = org.lwjgl.input.Mouse.getEventButtonState();
			int button = org.lwjgl.input.Mouse.getEventButton();
			button = button == 1 ? BUTTON3 : button;
			if (org.lwjgl.input.Mouse.getEventDX() != 0 || org.lwjgl.input.Mouse.getEventDY() != 0) // moved
				mouseEvent(pressed ? MOUSE_DRAGGED : MOUSE_MOVED, button, p);
			else {
				mouseEvent(pressed ? MOUSE_PRESSED : MOUSE_RELEASED, button, p);
				if (!pressed)
					mouseEvent(MOUSE_CLICKED, button, p);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Input#translateBackendKeyCode(int)
	 */
	@Override
	public int translateKeyCode(int keyCode) {
		return keyCode;
	}

	public Point translateMouse(int x, int y) {
		return new Point(x, Display.getHeight() - y);
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Input#mouseMove(java.awt.Point)
	 */
	@Override
	public void mouseMove(int x, int y) {
		Point p = translateMouse(x, y);
		org.lwjgl.input.Mouse.setCursorPosition(p.x, p.y);
	}

}
