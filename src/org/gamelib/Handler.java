/**
 * 
 */
package org.gamelib;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import org.gamelib.backend.Graphics;
import org.gamelib.ui.Component;

/**
 * Captures registered {@link Event}s.
 * @author Axel
 */
public interface Handler {
	/**
	 * Called when one of the listened events are fired.
	 * @param event the triggered event
	 * @return whether this handler is listening for the event
	 */
	public boolean handle(Event event);

	public static abstract class Event {
		/** If stopped notifying next handlers. */
		public boolean cancelled;
		/** Optionally the object on which the Event first occured */
		public Object source;

		/** Stops the next handlers from receiving this event. */
		public void cancel() {
			// if (!cancelable()) throw new UnsupportedOperationException();
			cancelled = true;
		}
		
		public boolean unregisterAfterNoInterrest() {
			return true;
		}

		/** Event triggered each tick. */
		public static class Tick extends Event {
			public float delta;

			public Tick(float delta) {
				this.delta = delta;
			}
		}

		/** Event triggered when the screen redraws. */
		public static final class Draw extends Event {
			public Graphics graphics;
			public float delta;

			public Draw(Graphics graphics, float delta) {
				this.graphics = graphics;
				this.delta = delta;
			}
		}

		/** Abstract event for input actions. */
		public abstract static class Control<T extends InputEvent> extends Event {
			public T event;
			public Input input;

			public Control(T event, Input input) {
				this.event = event;
				this.input = input;
			}

			public Control(Input input) {
				this.input = input;
			}
		}

		public static final class Key extends Control<KeyEvent> {
			public int id, keyCode;

			public Key(KeyEvent event, Input input) {
				super(event, input);
			}

			public Key(Input input, int id, int keyCode) {
				super(input);
				this.id = id;
				this.keyCode = keyCode;
			}
		}

		public static final class Mouse extends Control<MouseEvent> {
			public int id;
			public int button;

			public Mouse(MouseEvent event, Input input) {
				super(event, input);
			}

			public Mouse(Input input, int id, int button) {
				super(input);
				this.id = id;
				this.button = button;
			}
		}

		public static final class MouseWheel extends Control<MouseWheelEvent> {
			public double scrollAmount;

			public MouseWheel(MouseWheelEvent event, Input input) {
				super(event, input);
			}

			public MouseWheel(Input input, double scrollAmount) {
				super(input);
				this.scrollAmount = scrollAmount;
			}
		}

		/** Event triggered by a layout component. */
		public static final class Layout extends Event {
			public Component id;
			public Event event;

			public Layout(Component id, Event event) {
				this.id = id;
			}
			
			@Override
			public boolean unregisterAfterNoInterrest() {
				return false;
			}
		}
	}
}
