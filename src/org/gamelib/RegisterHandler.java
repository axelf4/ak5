/**
 * 
 */
package org.gamelib;

import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseWheelEvent;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Axel
 * 
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RegisterHandler {
	// HandlerType handler();

	@Deprecated
	boolean shouldAutoRegist() default true;
	Class<? extends Scene> scene() default DefaultScene.class;

	/* public enum HandlerType { TICK(), RENDER(), KEY(), MOUSE(), MOUSEWHEEL();
	 * } */

	public abstract class Event {
		public static final boolean cancelable = true; // protected
		public boolean canceled; // canceled

		/**
		 * Stops the next handlers to receive this event.
		 */
		public void cancel() {
			if (!cancelable)
				throw new UnsupportedOperationException();
			canceled = true;
		}

		public static final class Tick extends Event {}

		public static final class Draw extends Event {
			public Graphics2D graphics2d;
			public float interpolation;

			public Draw(Graphics2D graphics2d, float interpolation) {
				this.graphics2d = graphics2d;
				this.interpolation = interpolation;
			}
		}

		/**
		 * 
		 * @author Axel
		 * 
		 * @param <T>
		 */
		public abstract static class Input<T> extends Event {
			public T event;
			public Input input;
			
			public Input(T event, Input input) {
				this.event = event;
				this.input = input;
			}
		}

		public static final class Key extends Input<KeyEvent> {
			public Key(KeyEvent event, Input input) {
				super(event, input);
			}
		}

		public static final class Mouse extends Input<MouseEvent> {
			public Mouse(MouseEvent event, Input input) {
				super(event, input);
			}
		}

		public static final class MouseWheel extends Input<MouseWheelEvent> {
			public MouseWheel(MouseWheelEvent event, Input input) {
				super(event, input);
			}
		}
	}
	
	public abstract class Scene {}
	public class DefaultScene extends Scene {}
}