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
 * Callback SAM (Single Abstract Method)-interface for subscribed {@link Event}s. At registration all {@link Event}s will be subscribed to, but excluded as {@link #handle(Event)} denies.
 * 
 * @author pwnedary
 */
public interface Handler {
	/**
	 * Handles the published {@code event}. Returns <tt>false</tt> if this {@link Handler} isn't subscribing to {@link Event}s of {@linkplain Event#getClass() <code>event</code>'s class} anymore.
	 * 
	 * @param event the {@link Event} being published
	 * @return <tt>true</tt> if subscribing to event
	 */
	public boolean handle(Event event);

	/** An Event that occurred; may hold additional information. */
	public interface Event {
		/**
		 * Returns whether this {@link Event} has been cancelled and won't get published to underlying handlers.
		 * 
		 * @return <tt>true</tt> if cancelled
		 */
		public boolean cancelled();

		/** Stops the underlying handlers from receiving this event. */
		public void cancel();

		/**
		 * Returns the object that caused this {@link Event} <i>(optional operation)</i>.
		 * 
		 * @return the object that caused this Event
		 * @throws UnsupportedOperationException If the cause was unknown/specified.
		 */
		public Object source();

		public static abstract class EventImpl implements Event {
			/** If to stop notifying underlying handlers. */
			public boolean cancelled;
			/** Optionally the object that caused this Event. */
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
				if (source == null) throw new UnsupportedOperationException("No source was declared.");
				return source;
			}
		}

		/** Event triggered each tick. */
		public static class Tick extends EventImpl implements Event {
			public float delta;

			public Tick(float delta) {
				this.delta = delta;
			}
		}

		/** Event triggered when the screen redraws. */
		public static final class Draw extends EventImpl implements Event {
			public Graphics graphics;
			public float delta;

			public Draw(Graphics graphics, float delta) {
				this.graphics = graphics;
				this.delta = delta;
			}
		}

		/** Abstract event for input actions. */
		public abstract static class Control<T extends InputEvent> extends EventImpl implements Event {
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

		public static final class Resize extends EventImpl implements Event {}

		public static final class Stop extends EventImpl implements Event {}
	}
}
