/**
 * 
 */
package org.gamelib;

import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.gamelib.ui.Component;
import org.gamelib.util.Log;

/**
 * @author Axel
 */
public interface Handler {
	/**
	 * Called when one of the registered events are fired.
	 * 
	 * @param event the triggered event
	 */
	public void handle(Event event);

	/**
	 * Used at registration to determine to which events this handler should
	 * bind to.
	 * 
	 * @return
	 */
	// public Class<? extends Event>[] handlers();
	public void handlers(List<Class<? extends Event>> list);

	public static abstract class Event {
		protected static final boolean DEFAULT_CANCELABLE = true;
		public boolean cancelled;

		/**
		 * Stops the next handlers from receiving this event.
		 */
		public void cancel() {
			if (!cancelable()) throw new UnsupportedOperationException();
			cancelled = true;
		}

		protected boolean cancelable() {
			return DEFAULT_CANCELABLE;
		}

		/** Event triggered each tick. */
		public static class Tick extends Event {}

		/** Event triggered when the screen redraws. */
		public static final class Draw extends Event {
			public Graphics2D graphics2d;
			public Graphics graphics;
			public float interpolation;

			public Draw(Graphics2D graphics2d, float interpolation) {
				this.graphics2d = graphics2d;
				this.interpolation = interpolation;
			}

			public Draw(Graphics graphics, float interpolation) {
				this.graphics = graphics;
				this.interpolation = interpolation;
			}
		}

		/** Abstract event for input actions. */
		public abstract static class Control<T> extends Event {
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
			public Key(KeyEvent event, Input input) {
				super(event, input);
			}

			public Key(Input input) {
				super(input);
			}
		}

		public static final class Mouse extends Control<MouseEvent> {
			public int id;

			public Mouse(MouseEvent event, Input input) {
				super(event, input);
			}

			public Mouse(Input input, int id) {
				super(input);
				this.id = id;
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

			public AdvancedTick() {
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