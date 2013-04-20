/**
 * 
 */
package org.gamelib;

import java.awt.AWTException;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Timer;

import org.gamelib.Handler.Event;

/**
 * @author Axel
 * 
 */
public abstract class Input {

	@SuppressWarnings("unused")
	private final int MAX_REPEAT_RATE = 100; // Hz

	/** The "key pressed" event. */
	public static final int KEY_PRESSED = KeyEvent.KEY_PRESSED;
	/** The "key released" event. */
	public static final int KEY_RELEASED = KeyEvent.KEY_RELEASED;

	public static final int MOUSE_MOVED = 0;
	public static final int MOUSE_DRAGGED = 1;
	public static final int MOUSE_PRESSED = 2;
	public static final int MOUSE_RELEASED = 3;
	public static final int MOUSE_CLICKED = 4;

	protected HashMap<Integer, Boolean> pressedKeys;
	Timer keyRepeatTimer;
	public Point mousePosition;
	protected boolean[] pressedMouseButtons;

	/**
	 * 
	 */
	public Input() {
		pressedKeys = new HashMap<Integer, Boolean>();
		mousePosition = new Point(0, 0);
		pressedMouseButtons = new boolean[3];
	}

	/**
	 * Returns whether the key is pressed.
	 * 
	 * @param key The key code as found in {@link Input.Key}.
	 * @return true or false.
	 */
	public boolean isKeyPressed(int key) {
		return pressedKeys.containsKey(key) && pressedKeys.get(key);
	}

	public boolean isMouseButtonPressed(int button, boolean repetive) {
		boolean b = pressedMouseButtons[button - 1];
		// if (!repetive)
		// pressedMouseButtons[button - 1] = false;
		return b;
	}
	
	public abstract void mouseMove(int x, int y);

	/** Checks for queued input states. */
	public abstract void poll();

	protected void keyEvent(int id, int keyCode) {
		keyCode = translateKeyCode(keyCode);
		if (id == KeyEvent.KEY_PRESSED) {
			pressedKeys.put(keyCode, true);
			if (keyCode == Key.KEY_ESCAPE)
				System.exit(0);
		} else if (id == KeyEvent.KEY_RELEASED)
			pressedKeys.put(keyCode, false);
		// pressedKeys.put(e.getKeyCode(), e.getID() == KeyEvent.KEY_PRESSED);
		HandlerRegistry.instance().invokeHandlers(new Event.Key(this));
	}

	protected void mouseEvent(int id, int button, Point p) {
		mousePosition = p;
		switch (id) {
		case MOUSE_PRESSED:
			pressedMouseButtons[button] = true;
			System.out.println("x: " + p.getX() + " y: " + p.getY());
			break;
		case MOUSE_RELEASED:
			pressedMouseButtons[button] = false;
			break;
		default:
			break;
		}
		HandlerRegistry.instance().invokeHandlers(new Event.Mouse(this, id));
	}

	protected void mouseWheelEvent(double scrollAmount) {
		HandlerRegistry.instance().invokeHandlers(new Event.MouseWheel(this, scrollAmount));
	}

	/*5* Constants for keyboard hardware. */
	/* public static final class Keyboard {
	 * 
	 * } */

	/**
	 * Constants for keyboard hardware. Virtual key codes.
	 * 
	 * @see java.awt.event.KeyEvent
	 */
	public static final class Key {
		public static final int KEY_UNDEFINED = 0;
		public static final int KEY_ESCAPE = 1;
		public static final int KEY_1 = 2;
		public static final int KEY_2 = 3;
		public static final int KEY_3 = 4;
		public static final int KEY_4 = 5;
		public static final int KEY_5 = 6;
		public static final int KEY_6 = 7;
		public static final int KEY_7 = 8;
		public static final int KEY_8 = 9;
		public static final int KEY_9 = 10;
		public static final int KEY_0 = 11;
		public static final int KEY_MINUS = 12;
		public static final int KEY_EQUALS = 13;
		public static final int KEY_BACK = 14;
		public static final int KEY_TAB = 15;
		public static final int KEY_Q = 16;
		public static final int KEY_W = 17;
		public static final int KEY_E = 18;
		public static final int KEY_R = 19;
		public static final int KEY_T = 20;
		public static final int KEY_Y = 21;
		public static final int KEY_U = 22;
		public static final int KEY_I = 23;
		public static final int KEY_O = 24;
		public static final int KEY_P = 25;
		public static final int KEY_LBRACKET = 26;
		public static final int KEY_RBRACKET = 27;
		public static final int KEY_RETURN = 28;
		public static final int KEY_LCONTROL = 29;
		public static final int KEY_A = 30;
		public static final int KEY_S = 31;
		public static final int KEY_D = 32;
		public static final int KEY_F = 33;
		public static final int KEY_G = 34;
		public static final int KEY_H = 35;
		public static final int KEY_J = 36;
		public static final int KEY_K = 37;
		public static final int KEY_L = 38;
		public static final int KEY_SEMICOLON = 39;
		public static final int KEY_APOSTROPHE = 40;
		public static final int KEY_GRAVE = 41;
		public static final int KEY_LSHIFT = 42;
		public static final int KEY_BACKSLASH = 43;
		public static final int KEY_Z = 44;
		public static final int KEY_X = 45;
		public static final int KEY_C = 46;
		public static final int KEY_V = 47;
		public static final int KEY_B = 48;
		public static final int KEY_N = 49;
		public static final int KEY_M = 50;
		public static final int KEY_COMMA = 51;
		public static final int KEY_PERIOD = 52;
		public static final int KEY_SLASH = 53;
		public static final int KEY_RSHIFT = 54;
		public static final int KEY_MULTIPLY = 55;
		public static final int KEY_LMENU = 56;
		public static final int KEY_SPACE = 57;
		public static final int KEY_CAPITAL = 58;
		public static final int KEY_F1 = 59;
		public static final int KEY_F2 = 60;
		public static final int KEY_F3 = 61;
		public static final int KEY_F4 = 62;
		public static final int KEY_F5 = 63;
		public static final int KEY_F6 = 64;
		public static final int KEY_F7 = 65;
		public static final int KEY_F8 = 66;
		public static final int KEY_F9 = 67;
		public static final int KEY_F10 = 68;
		public static final int KEY_NUMLOCK = 69;
		public static final int KEY_SCROLL = 70;
		public static final int KEY_NUMPAD7 = 71;
		public static final int KEY_NUMPAD8 = 72;
		public static final int KEY_NUMPAD9 = 73;
		public static final int KEY_SUBTRACT = 74;
		public static final int KEY_NUMPAD4 = 75;
		public static final int KEY_NUMPAD5 = 76;
		public static final int KEY_NUMPAD6 = 77;
		public static final int KEY_ADD = 78;
		public static final int KEY_NUMPAD1 = 79;
		public static final int KEY_NUMPAD2 = 80;
		public static final int KEY_NUMPAD3 = 81;
		public static final int KEY_NUMPAD0 = 82;
		public static final int KEY_DECIMAL = 83;
		public static final int KEY_F11 = 87;
		public static final int KEY_F12 = 88;
		public static final int KEY_F13 = 100;
		public static final int KEY_F14 = 101;
		public static final int KEY_F15 = 102;
		public static final int KEY_KANA = 112;
		public static final int KEY_CONVERT = 121;
		public static final int KEY_NOCONVERT = 123;
		public static final int KEY_YEN = 125;
		public static final int KEY_NUMPADEQUALS = 141;
		public static final int KEY_CIRCUMFLEX = 144;
		public static final int KEY_AT = 145;
		public static final int KEY_COLON = 146;
		public static final int KEY_UNDERLINE = 147;
		public static final int KEY_KANJI = 148;
		public static final int KEY_STOP = 149;
		public static final int KEY_AX = 150;
		public static final int KEY_UNLABELED = 151;
		public static final int KEY_NUMPADENTER = 156;
		public static final int KEY_RCONTROL = 157;
		public static final int KEY_NUMPADCOMMA = 179;
		public static final int KEY_DIVIDE = 181;
		public static final int KEY_SYSRQ = 183;
		public static final int KEY_RMENU = 184;
		public static final int KEY_PAUSE = 197;
		public static final int KEY_HOME = 199;
		public static final int KEY_UP = 200;
		public static final int KEY_PRIOR = 201;
		public static final int KEY_LEFT = 203;
		public static final int KEY_RIGHT = 205;
		public static final int KEY_END = 207;
		public static final int KEY_DOWN = 208;
		public static final int KEY_NEXT = 209;
		public static final int KEY_INSERT = 210;
		public static final int KEY_DELETE = 211;
	}

	/**
	 * Translates the keyCode to match the ones in {@link Input.Key}. The normal
	 * user should never need to use this method.
	 * 
	 * @param keyCode the key to translate
	 * @return the translated key
	 */
	public abstract int translateKeyCode(int keyCode);

	/**
	 * Constants for mouse hardware.
	 * 
	 * @author Axel
	 * @see java.awt.event.MouseEvent
	 */
	public static final class Mouse {
		/** Indicates no mouse buttons; used by {@link #getButton}. */
		public static final int NOBUTTON = 0;
		/** Indicates mouse button #1; used by {@link #getButton}. */
		public static final int BUTTON1 = 1;
		/** Indicates mouse button #2; used by {@link #getButton}. */
		public static final int BUTTON2 = 2;
		/** Indicates mouse button #3; used by {@link #getButton}. */
		public static final int BUTTON3 = 3;
	}
}
