/**
 * 
 */
package org.gamelib.backend.lwjgl;

import org.gamelib.Event;
import org.gamelib.Handler;
import org.gamelib.Input;
import org.gamelib.graphics.Texture;
import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;

/** @author pwnedary */
public class LWJGLInput implements Input {
	private final Handler handler;
	private int deltaX;
	private int deltaY;

	public LWJGLInput(Handler handler) {
		this.handler = handler;
	}

	@Override
	public void poll() {
		while (Keyboard.next())
			handler.handle(new Event.Key(this, Keyboard.getEventKeyState() ? KEY_PRESSED : KEY_RELEASED, translateKeyCode(Keyboard.getEventKey()))); // poll key events

		deltaX = 0;
		deltaY = 0;
		while (Mouse.next()) { // poll mouse events
			boolean pressed = Mouse.getEventButtonState();
			int button = Mouse.getEventButton();
			button = button == 1 ? BUTTON3 : button;
			deltaX = Mouse.getEventDX();
			deltaY = Mouse.getEventDY();

			if (button != -1) handler.handle(new Event.Mouse(this, pressed ? MOUSE_PRESSED : MOUSE_RELEASED, button)); // simple button event
			else if (deltaX != 0 || deltaY != 0) handler.handle(new Event.Mouse(this, pressed ? MOUSE_DRAGGED : MOUSE_MOVED, button)); // mouse's been moved
			else if (Mouse.getEventDWheel() == 0) handler.handle(new Event.Mouse(this, Mouse.getEventDWheel())); // wheel event
			// if (!pressed) mouseEvent(MOUSE_CLICKED, button, mouseX, mouseY);
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
		return Display.getHeight() - 1 - Mouse.getY();
	}

	@Override
	public int getDeltaX() {
		return deltaX; // Mouse.getDX()
	}

	@Override
	public int getDeltaY() {
		return deltaY; // Mouse.getDY();
	}

	@Override
	public int translateKeyCode(int keyCode) {
		if (keyCode == Keyboard.KEY_LSHIFT || keyCode == Keyboard.KEY_RSHIFT) return Key.KEY_SHIFT;
		if (keyCode == Keyboard.KEY_LCONTROL || keyCode == Keyboard.KEY_RCONTROL) return Key.KEY_CONTROL;
		if (keyCode == Keyboard.KEY_LMENU || keyCode == Keyboard.KEY_RMENU) return Key.KEY_MENU;
		return keyCode;
	}

	@Override
	public void mouseMove(int x, int y) {
		Mouse.setCursorPosition(x, Display.getHeight() - 1 - y);
	}

	@Override
	public void setGrabbed(boolean grabbed) {
		Mouse.setGrabbed(true);
	}

	@Override
	public void setCursor(Texture texture) {
		throw new UnsupportedOperationException();
	}

}
