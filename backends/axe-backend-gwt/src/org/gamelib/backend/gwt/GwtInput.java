/**
 * 
 */
package org.gamelib.backend.gwt;

import org.gamelib.Handler;
import org.gamelib.backend.Input;
import org.gamelib.backend.Input.InputImpl;
import org.gamelib.graphics.Texture;

import com.google.gwt.canvas.client.Canvas;
import com.google.gwt.event.dom.client.KeyCodes;
import com.google.gwt.event.dom.client.KeyDownEvent;
import com.google.gwt.event.dom.client.KeyDownHandler;
import com.google.gwt.event.dom.client.KeyPressEvent;
import com.google.gwt.event.dom.client.KeyPressHandler;
import com.google.gwt.event.dom.client.KeyUpEvent;
import com.google.gwt.event.dom.client.KeyUpHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;

/** @author pwnedary */
public class GwtInput extends InputImpl implements Input, KeyDownHandler, KeyUpHandler, KeyPressHandler, MouseDownHandler, MouseUpHandler, MouseMoveHandler, MouseWheelHandler {
	private final Canvas canvas;

	public GwtInput(Canvas canvas, Handler handler) {
		super(handler);
		this.canvas = canvas;
		canvas.addKeyDownHandler(this);
		canvas.addKeyUpHandler(this);
		canvas.addKeyPressHandler(this);
		canvas.addMouseDownHandler(this);
		canvas.addMouseUpHandler(this);
		canvas.addMouseMoveHandler(this);
		canvas.addMouseWheelHandler(this);
	}

	@Override
	public void poll() {}

	@Override
	public void mouseMove(int x, int y) {
		throw new UnsupportedOperationException();
	}

	@Override
	public native void setGrabbed(boolean grabbed) /*-{
		(grabbed ? (canvas.requestPointerLock || canvas.mozRequestPointerLock || canvas.webkitRequestPointerLock)
				: (document.exitPointerLock || document.mozExitPointerLock || document.webkitExitPointerLock))
				();
	}-*/;

	@Override
	public void setCursor(Texture texture) {
		throw new UnsupportedOperationException();
	}

	@Override
	public int translateKeyCode(int keyCode) {
		switch (keyCode) {
		//		case KeyCodes.KEY_ALT:
		//			return Key.KEY_ALT_LEFT;
		//		case KeyCodes.KEY_BACKSPACE:
		//			return Key.KEY_BACKSPACE;
		case KeyCodes.KEY_CTRL:
			return Key.KEY_CONTROL;
		case KeyCodes.KEY_DELETE:
			return Key.KEY_DELETE;
		case KeyCodes.KEY_DOWN:
			return Key.KEY_DOWN;
		case KeyCodes.KEY_END:
			return Key.KEY_END;
			//		case KeyCodes.KEY_ENTER:
			//			return Key.KEY_ENTER;
		case KeyCodes.KEY_ESCAPE:
			return Key.KEY_ESCAPE;
		case KeyCodes.KEY_HOME:
			return Key.KEY_HOME;
		case KeyCodes.KEY_LEFT:
			return Key.KEY_LEFT;
			//		case KeyCodes.KEY_PAGEDOWN:
			//			return Key.KEY_PAGE_DOWN;
			//		case KeyCodes.KEY_PAGEUP:
			//			return Key.KEY_PAGE_UP;
		case KeyCodes.KEY_RIGHT:
			return Key.KEY_RIGHT;
		case KeyCodes.KEY_SHIFT:
			return Key.KEY_SHIFT;
		case KeyCodes.KEY_TAB:
			return Key.KEY_TAB;
		case KeyCodes.KEY_UP:
			return Key.KEY_UP;
		case KEY_PAUSE:
			return Key.KEY_PAUSE;
			//		case KEY_CAPS_LOCK:
			//			return Key.KEY_UNKNOWN; // FIXME
		case KEY_SPACE:
			return Key.KEY_SPACE;
		case KEY_INSERT:
			return Key.KEY_INSERT;
		case KEY_0:
			return Key.KEY_0;
		case KEY_1:
			return Key.KEY_1;
		case KEY_2:
			return Key.KEY_2;
		case KEY_3:
			return Key.KEY_3;
		case KEY_4:
			return Key.KEY_4;
		case KEY_5:
			return Key.KEY_5;
		case KEY_6:
			return Key.KEY_6;
		case KEY_7:
			return Key.KEY_7;
		case KEY_8:
			return Key.KEY_8;
		case KEY_9:
			return Key.KEY_9;
		case KEY_A:
			return Key.KEY_A;
		case KEY_B:
			return Key.KEY_B;
		case KEY_C:
			return Key.KEY_C;
		case KEY_D:
			return Key.KEY_D;
		case KEY_E:
			return Key.KEY_E;
		case KEY_F:
			return Key.KEY_F;
		case KEY_G:
			return Key.KEY_G;
		case KEY_H:
			return Key.KEY_H;
		case KEY_I:
			return Key.KEY_I;
		case KEY_J:
			return Key.KEY_J;
		case KEY_K:
			return Key.KEY_K;
		case KEY_L:
			return Key.KEY_L;
		case KEY_M:
			return Key.KEY_M;
		case KEY_N:
			return Key.KEY_N;
		case KEY_O:
			return Key.KEY_O;
		case KEY_P:
			return Key.KEY_P;
		case KEY_Q:
			return Key.KEY_Q;
		case KEY_R:
			return Key.KEY_R;
		case KEY_S:
			return Key.KEY_S;
		case KEY_T:
			return Key.KEY_T;
		case KEY_U:
			return Key.KEY_U;
		case KEY_V:
			return Key.KEY_V;
		case KEY_W:
			return Key.KEY_W;
		case KEY_X:
			return Key.KEY_X;
		case KEY_Y:
			return Key.KEY_Y;
		case KEY_Z:
			return Key.KEY_Z;
			//		case KEY_LEFT_WINDOW_KEY:
			//			return Key.KEY_UNKNOWN; // FIXME
			//		case KEY_RIGHT_WINDOW_KEY:
			//			return Key.KEY_UNKNOWN; // FIXME
			// case KEY_SELECT_KEY: return Key.KEY_SELECT_KEY;
		case KEY_NUMPAD0:
			return Key.KEY_NUMPAD0;
		case KEY_NUMPAD1:
			return Key.KEY_NUMPAD1;
		case KEY_NUMPAD2:
			return Key.KEY_NUMPAD2;
		case KEY_NUMPAD3:
			return Key.KEY_NUMPAD3;
		case KEY_NUMPAD4:
			return Key.KEY_NUMPAD4;
		case KEY_NUMPAD5:
			return Key.KEY_NUMPAD5;
		case KEY_NUMPAD6:
			return Key.KEY_NUMPAD6;
		case KEY_NUMPAD7:
			return Key.KEY_NUMPAD7;
		case KEY_NUMPAD8:
			return Key.KEY_NUMPAD8;
		case KEY_NUMPAD9:
			return Key.KEY_NUMPAD9;
		case KEY_MULTIPLY:
			return Key.KEY_MULTIPLY; // FIXME
		case KEY_ADD:
			return Key.KEY_ADD;
		case KEY_SUBTRACT:
			return Key.KEY_MINUS;
		case KEY_DECIMAL_POINT_KEY:
			return Key.KEY_PERIOD;
		case KEY_DIVIDE:
			return Key.KEY_DIVIDE; // FIXME
		case KEY_F1:
			return Key.KEY_F1;
		case KEY_F2:
			return Key.KEY_F2;
		case KEY_F3:
			return Key.KEY_F3;
		case KEY_F4:
			return Key.KEY_F4;
		case KEY_F5:
			return Key.KEY_F5;
		case KEY_F6:
			return Key.KEY_F6;
		case KEY_F7:
			return Key.KEY_F7;
		case KEY_F8:
			return Key.KEY_F8;
		case KEY_F9:
			return Key.KEY_F9;
		case KEY_F10:
			return Key.KEY_F10;
		case KEY_F11:
			return Key.KEY_F11;
		case KEY_F12:
			return Key.KEY_F12;
		case KEY_NUM_LOCK:
			return Key.KEY_NUMLOCK;
			//		case KEY_SCROLL_LOCK:
			//			return Key.KEY_SCROLL; // FIXME
		case KEY_SEMICOLON:
			return Key.KEY_SEMICOLON;
		case KEY_EQUALS:
			return Key.KEY_EQUALS;
		case KEY_COMMA:
			return Key.KEY_COMMA;
		case KEY_DASH:
			return Key.KEY_MINUS;
		case KEY_PERIOD:
			return Key.KEY_PERIOD;
		case KEY_FORWARD_SLASH:
			return Key.KEY_SLASH;
			//		case KEY_GRAVE_ACCENT:
			//			return Key.KEY_UNKNOWN; // FIXME
		case KEY_OPEN_BRACKET:
			return Key.KEY_LBRACKET;
		case KEY_BACKSLASH:
			return Key.KEY_BACKSLASH;
		case KEY_CLOSE_BRACKET:
			return Key.KEY_RBRACKET;
		case KEY_SINGLE_QUOTE:
			return Key.KEY_APOSTROPHE;
		default:
			return Key.KEY_UNDEFINED;
		}
	}

	private static final int KEY_PAUSE = 19;
	private static final int KEY_CAPS_LOCK = 20;
	private static final int KEY_SPACE = 32;
	private static final int KEY_INSERT = 45;
	private static final int KEY_0 = 48;
	private static final int KEY_1 = 49;
	private static final int KEY_2 = 50;
	private static final int KEY_3 = 51;
	private static final int KEY_4 = 52;
	private static final int KEY_5 = 53;
	private static final int KEY_6 = 54;
	private static final int KEY_7 = 55;
	private static final int KEY_8 = 56;
	private static final int KEY_9 = 57;
	private static final int KEY_A = 65;
	private static final int KEY_B = 66;
	private static final int KEY_C = 67;
	private static final int KEY_D = 68;
	private static final int KEY_E = 69;
	private static final int KEY_F = 70;
	private static final int KEY_G = 71;
	private static final int KEY_H = 72;
	private static final int KEY_I = 73;
	private static final int KEY_J = 74;
	private static final int KEY_K = 75;
	private static final int KEY_L = 76;
	private static final int KEY_M = 77;
	private static final int KEY_N = 78;
	private static final int KEY_O = 79;
	private static final int KEY_P = 80;
	private static final int KEY_Q = 81;
	private static final int KEY_R = 82;
	private static final int KEY_S = 83;
	private static final int KEY_T = 84;
	private static final int KEY_U = 85;
	private static final int KEY_V = 86;
	private static final int KEY_W = 87;
	private static final int KEY_X = 88;
	private static final int KEY_Y = 89;
	private static final int KEY_Z = 90;
	private static final int KEY_LEFT_WINDOW_KEY = 91;
	private static final int KEY_RIGHT_WINDOW_KEY = 92;
	private static final int KEY_SELECT_KEY = 93;
	private static final int KEY_NUMPAD0 = 96;
	private static final int KEY_NUMPAD1 = 97;
	private static final int KEY_NUMPAD2 = 98;
	private static final int KEY_NUMPAD3 = 99;
	private static final int KEY_NUMPAD4 = 100;
	private static final int KEY_NUMPAD5 = 101;
	private static final int KEY_NUMPAD6 = 102;
	private static final int KEY_NUMPAD7 = 103;
	private static final int KEY_NUMPAD8 = 104;
	private static final int KEY_NUMPAD9 = 105;
	private static final int KEY_MULTIPLY = 106;
	private static final int KEY_ADD = 107;
	private static final int KEY_SUBTRACT = 109;
	private static final int KEY_DECIMAL_POINT_KEY = 110;
	private static final int KEY_DIVIDE = 111;
	private static final int KEY_F1 = 112;
	private static final int KEY_F2 = 113;
	private static final int KEY_F3 = 114;
	private static final int KEY_F4 = 115;
	private static final int KEY_F5 = 116;
	private static final int KEY_F6 = 117;
	private static final int KEY_F7 = 118;
	private static final int KEY_F8 = 119;
	private static final int KEY_F9 = 120;
	private static final int KEY_F10 = 121;
	private static final int KEY_F11 = 122;
	private static final int KEY_F12 = 123;
	private static final int KEY_NUM_LOCK = 144;
	private static final int KEY_SCROLL_LOCK = 145;
	private static final int KEY_SEMICOLON = 186;
	private static final int KEY_EQUALS = 187;
	private static final int KEY_COMMA = 188;
	private static final int KEY_DASH = 189;
	private static final int KEY_PERIOD = 190;
	private static final int KEY_FORWARD_SLASH = 191;
	private static final int KEY_GRAVE_ACCENT = 192;
	private static final int KEY_OPEN_BRACKET = 219;
	private static final int KEY_BACKSLASH = 220;
	private static final int KEY_CLOSE_BRACKET = 221;
	private static final int KEY_SINGLE_QUOTE = 222;

	@Override
	public void onKeyDown(KeyDownEvent event) {
		keyEvent(KEY_PRESSED, event.getNativeKeyCode());
	}

	@Override
	public void onKeyUp(KeyUpEvent event) {
		keyEvent(KEY_RELEASED, event.getNativeKeyCode());
	}

	@Override
	public void onKeyPress(KeyPressEvent event) {
		keyEvent(KEY_TYPED, event.getCharCode());
	}

	@Override
	public void onMouseDown(MouseDownEvent event) {
		mouseEvent(MOUSE_PRESSED, event.getNativeButton() - 1, mouseX, mouseY);
	}

	@Override
	public void onMouseUp(MouseUpEvent event) {
		mouseEvent(MOUSE_RELEASED, event.getNativeButton() - 1, mouseX, mouseY);
	}

	@Override
	public void onMouseMove(MouseMoveEvent event) {
		mouseEvent(mousePressed(event.getNativeButton() - 1) ? MOUSE_DRAGGED : MOUSE_MOVED, event.getNativeButton() - 1, event.getClientX(), event.getClientY());
	}

	@Override
	public void onMouseWheel(MouseWheelEvent event) {
		mouseWheelEvent(event.getDeltaY());
	}
}
