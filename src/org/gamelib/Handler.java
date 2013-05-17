/**
 * 
 */
package org.gamelib;

import java.awt.Graphics2D;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.List;

import org.gamelib.backend.Graphics;
import org.gamelib.ui.Component;

/**
 * Captures registered {@link Event}s.
 * @author Axel
 */
public interface Handler {
	/**
	 * Called when one of the registered events are fired.
	 * @param event the triggered event
	 */
	public void handle(Event event);

	/**
	 * Used at registration to determine which events to register to.
	 * @param list which events to capture
	 */
	public void handlers(List<Class<? extends Event>> list);

	public static abstract class Event {
		protected static final boolean DEFAULT_CANCELABLE = true;
		public boolean cancelled;

		/** Stops the next handlers from receiving this event. */
		public void cancel() {
			if (!cancelable())
				throw new UnsupportedOperationException();
			cancelled = true;
		}

		protected boolean cancelable() {
			return DEFAULT_CANCELABLE;
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
			public Graphics2D graphics2d;
			public Graphics graphics;
			public float delta;

			public Draw(Graphics graphics, float delta) {
				this.graphics = graphics;
				this.delta = delta;
			}

			public Draw(Graphics2D graphics2d, float delta) {
				this.graphics2d = graphics2d;
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

		public static final class AdvancedTick extends Tick {
			public static long counter = 0;

			public AdvancedTick(float delta) {
				super(delta);
				counter = counter >= Long.MAX_VALUE ? 0 : counter + 1;
			}
		}

		/** Event triggered by a layout component. */
		public static final class Layout extends Event {
			public Component id;
			public Event event;

			public Layout(Component id, Event event) {
				this.id = id;
			}
		}
	}
}
