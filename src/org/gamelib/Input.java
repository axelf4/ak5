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

	public void mouseMove(Point p) {
		GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
		GraphicsDevice[] gs = ge.getScreenDevices();
		// Search the devices for the one that draws the specified point.
		for (GraphicsDevice device : gs) {
			GraphicsConfiguration[] configurations = device.getConfigurations();
			for (GraphicsConfiguration config : configurations) {
				Rectangle bounds = config.getBounds();
				if (bounds.contains(p)) {
					// Set point to screen coordinates.
					Point b = bounds.getLocation();
					Point s = new Point(p.x - b.x, p.y - b.y);
					try {
						Robot r = new Robot(device);
						r.mouseMove(s.x, s.y);
					} catch (AWTException e) {
						e.printStackTrace();
					}
					return;
				}
			}
		}
		// Couldn't move to the point, it may be off screen.
		return;
	}

	protected void keyEvent(int id, int keyCode) {
		if (id == KeyEvent.KEY_PRESSED) {
			pressedKeys.put(keyCode, true);
			switch (keyCode) {
			case KeyEvent.VK_ESCAPE:
				System.exit(0);
				break;
			default:
				break;
			}
		} else if (id == KeyEvent.KEY_RELEASED)
			pressedKeys.put(keyCode, false);
		// pressedKeys.put(e.getKeyCode(), e.getID() == KeyEvent.KEY_PRESSED);
		HandlerRegistry.instance().invokeHandlers(new Event.Key(this));
	}
	
	protected void mouseEvent(int id, int button, Point p) {
		mousePosition = p;
		switch (id) {
		case MOUSE_PRESSED:
			pressedMouseButtons[button - 1] = true;
			System.out.println("x: " + p.getX() + " y: " + p.getY());
			break;
		case MOUSE_RELEASED:
			pressedMouseButtons[button - 1] = false;
			break;
		default:
			break;
		}
		HandlerRegistry.instance().invokeHandlers(new Event.Mouse(this, id));
	}
	
	protected void mouseWheelEvent(double scrollAmount) {
		HandlerRegistry.instance().invokeHandlers(new Event.MouseWheel(this, scrollAmount));
	}

	/**
	 * Virtual key codes.
	 * 
	 * @author Axel
	 * @see java.awt.event.KeyEvent
	 */
	public static final class Key {
		/* Virtual key codes. */

		public static final int VK_ENTER = '\n';
		public static final int VK_BACK_SPACE = '\b';
		public static final int VK_TAB = '\t';
		public static final int VK_CANCEL = 0x03;
		public static final int VK_CLEAR = 0x0C;
		public static final int VK_SHIFT = 0x10;
		public static final int VK_CONTROL = 0x11;
		public static final int VK_ALT = 0x12;
		public static final int VK_PAUSE = 0x13;
		public static final int VK_CAPS_LOCK = 0x14;
		public static final int VK_ESCAPE = 0x1B;
		public static final int VK_SPACE = 0x20;
		public static final int VK_PAGE_UP = 0x21;
		public static final int VK_PAGE_DOWN = 0x22;
		public static final int VK_END = 0x23;
		public static final int VK_HOME = 0x24;
		/** Constant for the non-numpad <b>left</b> arrow key. */
		public static final int VK_LEFT = 0x25;
		/** Constant for the non-numpad <b>up</b> arrow key. */
		public static final int VK_UP = 0x26;
		/** Constant for the non-numpad <b>right</b> arrow key. */
		public static final int VK_RIGHT = 0x27;
		/** Constant for the non-numpad <b>down</b> arrow key. */
		public static final int VK_DOWN = 0x28;
		/** Constant for the comma key, "," */
		public static final int VK_COMMA = 0x2C;
		/** Constant for the minus key, "-" */
		public static final int VK_MINUS = 0x2D;
		/** Constant for the period key, "." */
		public static final int VK_PERIOD = 0x2E;
		/** Constant for the forward slash key, "/" */
		public static final int VK_SLASH = 0x2F;
		/** VK_0 thru VK_9 are the same as ASCII '0' thru '9' (0x30 - 0x39) */
		public static final int VK_0 = 0x30;
		public static final int VK_1 = 0x31;
		public static final int VK_2 = 0x32;
		public static final int VK_3 = 0x33;
		public static final int VK_4 = 0x34;
		public static final int VK_5 = 0x35;
		public static final int VK_6 = 0x36;
		public static final int VK_7 = 0x37;
		public static final int VK_8 = 0x38;
		public static final int VK_9 = 0x39;
		/** Constant for the semicolon key, ";" */
		public static final int VK_SEMICOLON = 0x3B;
		/** Constant for the equals key, "=" */
		public static final int VK_EQUALS = 0x3D;
		/** VK_A thru VK_Z are the same as ASCII 'A' thru 'Z' (0x41 - 0x5A) */
		public static final int VK_A = 0x41;
		public static final int VK_B = 0x42;
		public static final int VK_C = 0x43;
		public static final int VK_D = 0x44;
		public static final int VK_E = 0x45;
		public static final int VK_F = 0x46;
		public static final int VK_G = 0x47;
		public static final int VK_H = 0x48;
		public static final int VK_I = 0x49;
		public static final int VK_J = 0x4A;
		public static final int VK_K = 0x4B;
		public static final int VK_L = 0x4C;
		public static final int VK_M = 0x4D;
		public static final int VK_N = 0x4E;
		public static final int VK_O = 0x4F;
		public static final int VK_P = 0x50;
		public static final int VK_Q = 0x51;
		public static final int VK_R = 0x52;
		public static final int VK_S = 0x53;
		public static final int VK_T = 0x54;
		public static final int VK_U = 0x55;
		public static final int VK_V = 0x56;
		public static final int VK_W = 0x57;
		public static final int VK_X = 0x58;
		public static final int VK_Y = 0x59;
		public static final int VK_Z = 0x5A;

		/**
		 * Constant for the open bracket key, "["
		 */
		public static final int VK_OPEN_BRACKET = 0x5B;

		/**
		 * Constant for the back slash key, "\"
		 */
		public static final int VK_BACK_SLASH = 0x5C;

		/**
		 * Constant for the close bracket key, "]"
		 */
		public static final int VK_CLOSE_BRACKET = 0x5D;

		public static final int VK_NUMPAD0 = 0x60;
		public static final int VK_NUMPAD1 = 0x61;
		public static final int VK_NUMPAD2 = 0x62;
		public static final int VK_NUMPAD3 = 0x63;
		public static final int VK_NUMPAD4 = 0x64;
		public static final int VK_NUMPAD5 = 0x65;
		public static final int VK_NUMPAD6 = 0x66;
		public static final int VK_NUMPAD7 = 0x67;
		public static final int VK_NUMPAD8 = 0x68;
		public static final int VK_NUMPAD9 = 0x69;
		public static final int VK_MULTIPLY = 0x6A;
		public static final int VK_ADD = 0x6B;

		/**
		 * This constant is obsolete, and is included only for backwards
		 * compatibility.
		 * 
		 * @see #VK_SEPARATOR
		 */
		public static final int VK_SEPARATER = 0x6C;

		/**
		 * Constant for the Numpad Separator key.
		 * 
		 * @since 1.4
		 */
		public static final int VK_SEPARATOR = VK_SEPARATER;

		public static final int VK_SUBTRACT = 0x6D;
		public static final int VK_DECIMAL = 0x6E;
		public static final int VK_DIVIDE = 0x6F;
		public static final int VK_DELETE = 0x7F; /* ASCII DEL */
		public static final int VK_NUM_LOCK = 0x90;
		public static final int VK_SCROLL_LOCK = 0x91;

		/** Constant for the F1 function key. */
		public static final int VK_F1 = 0x70;

		/** Constant for the F2 function key. */
		public static final int VK_F2 = 0x71;

		/** Constant for the F3 function key. */
		public static final int VK_F3 = 0x72;

		/** Constant for the F4 function key. */
		public static final int VK_F4 = 0x73;

		/** Constant for the F5 function key. */
		public static final int VK_F5 = 0x74;

		/** Constant for the F6 function key. */
		public static final int VK_F6 = 0x75;

		/** Constant for the F7 function key. */
		public static final int VK_F7 = 0x76;

		/** Constant for the F8 function key. */
		public static final int VK_F8 = 0x77;

		/** Constant for the F9 function key. */
		public static final int VK_F9 = 0x78;

		/** Constant for the F10 function key. */
		public static final int VK_F10 = 0x79;

		/** Constant for the F11 function key. */
		public static final int VK_F11 = 0x7A;

		/** Constant for the F12 function key. */
		public static final int VK_F12 = 0x7B;

		/**
		 * Constant for the F13 function key.
		 * 
		 * @since 1.2
		 */
		/* F13 - F24 are used on IBM 3270 keyboard; use random range for
		 * constants. */
		public static final int VK_F13 = 0xF000;

		/**
		 * Constant for the F14 function key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_F14 = 0xF001;

		/**
		 * Constant for the F15 function key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_F15 = 0xF002;

		/**
		 * Constant for the F16 function key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_F16 = 0xF003;

		/**
		 * Constant for the F17 function key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_F17 = 0xF004;

		/**
		 * Constant for the F18 function key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_F18 = 0xF005;

		/**
		 * Constant for the F19 function key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_F19 = 0xF006;

		/**
		 * Constant for the F20 function key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_F20 = 0xF007;

		/**
		 * Constant for the F21 function key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_F21 = 0xF008;

		/**
		 * Constant for the F22 function key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_F22 = 0xF009;

		/**
		 * Constant for the F23 function key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_F23 = 0xF00A;

		/**
		 * Constant for the F24 function key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_F24 = 0xF00B;

		public static final int VK_PRINTSCREEN = 0x9A;
		public static final int VK_INSERT = 0x9B;
		public static final int VK_HELP = 0x9C;
		public static final int VK_META = 0x9D;

		public static final int VK_BACK_QUOTE = 0xC0;
		public static final int VK_QUOTE = 0xDE;

		/**
		 * Constant for the numeric keypad <b>up</b> arrow key.
		 * 
		 * @see #VK_UP
		 * @since 1.2
		 */
		public static final int VK_KP_UP = 0xE0;

		/**
		 * Constant for the numeric keypad <b>down</b> arrow key.
		 * 
		 * @see #VK_DOWN
		 * @since 1.2
		 */
		public static final int VK_KP_DOWN = 0xE1;

		/**
		 * Constant for the numeric keypad <b>left</b> arrow key.
		 * 
		 * @see #VK_LEFT
		 * @since 1.2
		 */
		public static final int VK_KP_LEFT = 0xE2;

		/**
		 * Constant for the numeric keypad <b>right</b> arrow key.
		 * 
		 * @see #VK_RIGHT
		 * @since 1.2
		 */
		public static final int VK_KP_RIGHT = 0xE3;

		/* For European keyboards */
		/** @since 1.2 */
		public static final int VK_DEAD_GRAVE = 0x80;
		/** @since 1.2 */
		public static final int VK_DEAD_ACUTE = 0x81;
		/** @since 1.2 */
		public static final int VK_DEAD_CIRCUMFLEX = 0x82;
		/** @since 1.2 */
		public static final int VK_DEAD_TILDE = 0x83;
		/** @since 1.2 */
		public static final int VK_DEAD_MACRON = 0x84;
		/** @since 1.2 */
		public static final int VK_DEAD_BREVE = 0x85;
		/** @since 1.2 */
		public static final int VK_DEAD_ABOVEDOT = 0x86;
		/** @since 1.2 */
		public static final int VK_DEAD_DIAERESIS = 0x87;
		/** @since 1.2 */
		public static final int VK_DEAD_ABOVERING = 0x88;
		/** @since 1.2 */
		public static final int VK_DEAD_DOUBLEACUTE = 0x89;
		/** @since 1.2 */
		public static final int VK_DEAD_CARON = 0x8a;
		/** @since 1.2 */
		public static final int VK_DEAD_CEDILLA = 0x8b;
		/** @since 1.2 */
		public static final int VK_DEAD_OGONEK = 0x8c;
		/** @since 1.2 */
		public static final int VK_DEAD_IOTA = 0x8d;
		/** @since 1.2 */
		public static final int VK_DEAD_VOICED_SOUND = 0x8e;
		/** @since 1.2 */
		public static final int VK_DEAD_SEMIVOICED_SOUND = 0x8f;

		/** @since 1.2 */
		public static final int VK_AMPERSAND = 0x96;
		/** @since 1.2 */
		public static final int VK_ASTERISK = 0x97;
		/** @since 1.2 */
		public static final int VK_QUOTEDBL = 0x98;
		/** @since 1.2 */
		public static final int VK_LESS = 0x99;

		/** @since 1.2 */
		public static final int VK_GREATER = 0xa0;
		/** @since 1.2 */
		public static final int VK_BRACELEFT = 0xa1;
		/** @since 1.2 */
		public static final int VK_BRACERIGHT = 0xa2;

		/**
		 * Constant for the "@" key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_AT = 0x0200;

		/**
		 * Constant for the ":" key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_COLON = 0x0201;

		/**
		 * Constant for the "^" key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_CIRCUMFLEX = 0x0202;

		/**
		 * Constant for the "$" key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_DOLLAR = 0x0203;

		/**
		 * Constant for the Euro currency sign key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_EURO_SIGN = 0x0204;

		/**
		 * Constant for the "!" key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_EXCLAMATION_MARK = 0x0205;

		/**
		 * Constant for the inverted exclamation mark key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_INVERTED_EXCLAMATION_MARK = 0x0206;

		/**
		 * Constant for the "(" key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_LEFT_PARENTHESIS = 0x0207;

		/**
		 * Constant for the "#" key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_NUMBER_SIGN = 0x0208;

		/**
		 * Constant for the "+" key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_PLUS = 0x0209;

		/**
		 * Constant for the ")" key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_RIGHT_PARENTHESIS = 0x020A;

		/**
		 * Constant for the "_" key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_UNDERSCORE = 0x020B;

		/**
		 * Constant for the Microsoft Windows "Windows" key. It is used for both
		 * the left and right version of the key.
		 * 
		 * @see #getKeyLocation()
		 * @since 1.5
		 */
		public static final int VK_WINDOWS = 0x020C;

		/**
		 * Constant for the Microsoft Windows Context Menu key.
		 * 
		 * @since 1.5
		 */
		public static final int VK_CONTEXT_MENU = 0x020D;

		/**
		 * Constant for the Compose function key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_COMPOSE = 0xFF20;

		/**
		 * Constant for the AltGraph function key.
		 * 
		 * @since 1.2
		 */
		public static final int VK_ALT_GRAPH = 0xFF7E;

		/**
		 * Constant for the Begin key.
		 * 
		 * @since 1.5
		 */
		public static final int VK_BEGIN = 0xFF58;

		/**
		 * This value is used to indicate that the keyCode is unknown. KEY_TYPED
		 * events do not have a keyCode value; this value is used instead.
		 */
		public static final int VK_UNDEFINED = 0x0;
	}

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
