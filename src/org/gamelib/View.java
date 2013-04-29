/**
 * 
 */
package org.gamelib;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

import org.gamelib.Handler.Event;
import org.gamelib.ui.Component;

/**
 * possible names: View, Scene, Room, 
 * @author Axel
 * 
 */
public class View {
	public Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers = new HashMap<Class<? extends Event>, CopyOnWriteArrayList<Handler>>(1);
	private Rectangle rectangle = null;
	/** Whether the handler should receive events */
	public boolean active = true;
	private boolean alwaysActive = false;

	// private List<Scene> views = HandlerRegistry.instance().views;

	public View() {
		// HandlerRegistry.instance().addScene(this);
	}

	public View(Rectangle rectangle) {
		this();
		this.rectangle = rectangle;
	}

	/**
	 * @return the rectangle
	 */
	public Rectangle getRectangle() {
		return rectangle;
	}

	/**
	 * Sets the rectangle to the specified.
	 * 
	 * @param rectangle
	 * @return the existing rectangle if the given is null
	 */
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	public View setAlwaysActive(boolean b) {
		this.alwaysActive = b;
		return this;
	}

	/**
	 * Registers an handler
	 * 
	 * @param handler {@link Handler} instance
	 */
	public void register(Handler handler) {
		ArrayList<Class<? extends Event>> list = new ArrayList<Class<? extends Event>>();
		handler.handlers(list);
		for (Iterator<Class<? extends Event>> iterator = list.iterator(); iterator.hasNext();) {
			Class<? extends Event> type = iterator.next();
			if (!handlers.containsKey(type))
				handlers.put(type, new CopyOnWriteArrayList<Handler>());
			handlers.get(type).add(handler);
		}
	}

	/* View utility */

	public void switchTo() {
		for (View toCheck : HandlerRegistry.instance().views) {
			if (!toCheck.alwaysActive) toCheck.active = false;
		}
		active = true;
	}

	public static class UIPane extends View {
		private List<Component> components = new ArrayList<Component>();

		/**
		 * 
		 */
		public UIPane() {
			// TODO Auto-generated constructor stub
		}

		public void add(Component component) {
			components.add(component);
		}
	}
}