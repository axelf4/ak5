/**
 * 
 */
package org.gamelib;

import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;

import org.gamelib.backend.Graphics;
import org.gamelib.backend.Input;

/**
 * Captures registered {@link Event}s.
 * @author pwnedary
 */
public interface Handler {
	/**
	 * Called when one of the listened events are fired.
	 * @param event the triggered event
	 * @return whether this handler is listening for the event
	 */
	public boolean handle(Event event);

	public interface Event {
		/**
		 * Returns whether this {@link Event} has been cancelled and won't continue to be dispatched.
		 * @return whether cancelled
		 */
		public boolean cancelled();

		/** Stops the next handlers from receiving this event. */
		public void cancel();

		/**
		 * Returns optionally an instance related to the cause of this {@link Event}.
		 * @return object caused this event
		 */
		public Object source();

		public static abstract class EventImpl implements Event {
			/** If stopped notifying next handlers. */
			public boolean cancelled;
			/** Optionally the object on which the Event first occurred */
			public Object source;

			@Override
			public boolean cancelled() {
				return cancelled;
			}

			@Override
			public void cancel() {
				cancelled = true;
			}

			@Override
			public Object source() {
				return source;
			}
		}

		/** Event triggered each tick. */
		public static class Tick extends EventImpl {
			public float delta;

			public Tick(float delta) {
				this.delta = delta;
			}
		}

		/** Event triggered when the screen redraws. */
		public static final class Draw extends EventImpl {
			public Graphics graphics;
			public float delta;

			public Draw(Graphics graphics, float delta) {
				this.graphics = graphics;
				this.delta = delta;
			}
		}

		/** Abstract event for input actions. */
		public abstract static class Control<T extends InputEvent> extends EventImpl {
			public Input input;

			public Control(Input input) {
				this.input = input;
			}
		}

		public static final class Key extends Control<KeyEvent> {
			public int id;
			public int keyCode;

			public Key(Input input, int id, int keyCode) {
				super(input);
				this.id = id;
				this.keyCode = keyCode;
			}
		}

		public static final class Mouse extends Control<MouseEvent> {
			public int id;
			public int button;

			public Mouse(Input input, int id, int button) {
				super(input);
				this.id = id;
				this.button = button;
			}
		}

		public static final class MouseWheel extends Control<MouseWheelEvent> {
			public double scrollAmount;

			public MouseWheel(Input input, double scrollAmount) {
				super(input);
				this.scrollAmount = scrollAmount;
			}
		}
	}
}
