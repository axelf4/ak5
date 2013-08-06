/**
 * 
 */
package org.gamelib.backend.lwjgl;

import java.awt.Point;

import org.gamelib.Input;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/**
 * @author pwnedary
 */
public class LWJGLInput extends Input {

	/** {@inheritDoc} */
	@Override
	public void poll() {
		while (Keyboard.next())
			// poll key events
			keyEvent(Keyboard.getEventKeyState() ? KEY_PRESSED : KEY_RELEASED, translateKeyCode(Keyboard.getEventKey()));

		// Point p = new Point(org.lwjgl.input.Mouse.getX(), Display.getHeight() - org.lwjgl.input.Mouse.getY());
		int mouseX = 0, mouseY = 0; // mouse position
		if (!Mouse.isGrabbed()) {
			mouseX = Mouse.getX();
			mouseY = Display.getHeight() - Mouse.getY();
		}
		while (org.lwjgl.input.Mouse.next()) { // poll mouse events
			boolean pressed = Mouse.getEventButtonState();
			int button = Mouse.getEventButton();
			button = button == 1 ? BUTTON3 : button;
			if (Mouse.getEventDX() != 0 || Mouse.getEventDY() != 0) mouseEvent(pressed ? MOUSE_DRAGGED : MOUSE_MOVED, button, mouseX, mouseY); // mouse moved
			mouseEvent(pressed ? MOUSE_PRESSED : MOUSE_RELEASED, button, mouseX, mouseY); // pressed or released
			if (!pressed) mouseEvent(MOUSE_CLICKED, button, mouseX, mouseY); // stupid clicked event
		}
	}

	/** {@inheritDoc} */
	@Override
	public int translateKeyCode(int keyCode) {
		if (keyCode == Keyboard.KEY_LSHIFT || keyCode == Keyboard.KEY_RSHIFT) return Key.KEY_SHIFT;
		if (keyCode == Keyboard.KEY_LCONTROL || keyCode == Keyboard.KEY_RCONTROL) return Key.KEY_CONTROL;
		if (keyCode == Keyboard.KEY_LMENU || keyCode == Keyboard.KEY_RMENU) return Key.KEY_MENU;
		return keyCode;
	}

	@Deprecated
	Point translateMouse(int x, int y) {
		return new Point(x, Display.getHeight() - y);
	}

	/** {@inheritDoc} */
	@Override
	public void mouseMove(int x, int y) {
		Point p = translateMouse(x, y);
		org.lwjgl.input.Mouse.setCursorPosition(p.x, p.y);
	}

	/** {@inheritDoc} */
	@Override
	public void setGrabbed(boolean grabbed) {
		org.lwjgl.input.Mouse.setGrabbed(true);
	}

}
