/**
 * 
 */
package org.gamelib.backend.lwjgl;

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
			keyEvent(Keyboard.getEventKeyState() ? KEY_PRESSED : KEY_RELEASED, translateKeyCode(Keyboard.getEventKey())); // poll key events

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
			mouseEvent(pressed ? MOUSE_PRESSED : MOUSE_RELEASED, button, mouseX, mouseY); // pressed or released
			if (Mouse.getEventDX() != 0 || Mouse.getEventDY() != 0) mouseEvent(pressed ? MOUSE_DRAGGED : MOUSE_MOVED, button, mouseX, mouseY); // mouse moved
			// if (!pressed) mouseEvent(MOUSE_CLICKED, button, mouseX, mouseY); // stupid clicked event
		}
	}

	@Override
	public boolean keyPressed(int keycode) {
		return Keyboard.isKeyDown(keycode);
	}

	@Override
	public boolean mousePressed(int button) {
		return Mouse.isButtonDown(button);
	}

	@Override
	public int getMouseX() {
		return Mouse.getX();
	}

	@Override
	public int getMouseY() {
		return Display.getHeight() - Mouse.getY();
	}

	/** {@inheritDoc} */
	@Override
	public int translateKeyCode(int keyCode) {
		if (keyCode == Keyboard.KEY_LSHIFT || keyCode == Keyboard.KEY_RSHIFT) return Key.KEY_SHIFT;
		if (keyCode == Keyboard.KEY_LCONTROL || keyCode == Keyboard.KEY_RCONTROL) return Key.KEY_CONTROL;
		if (keyCode == Keyboard.KEY_LMENU || keyCode == Keyboard.KEY_RMENU) return Key.KEY_MENU;
		return keyCode;
	}

	/** {@inheritDoc} */
	@Override
	public void mouseMove(int x, int y) {
		org.lwjgl.input.Mouse.setCursorPosition(x, Display.getHeight() - y);
	}

	/** {@inheritDoc} */
	@Override
	public void setGrabbed(boolean grabbed) {
		org.lwjgl.input.Mouse.setGrabbed(true);
	}

}
