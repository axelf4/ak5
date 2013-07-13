/**
 * 
 */
package org.gamelib;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

import org.gamelib.Handler.Event;
import org.gamelib.ui.Referenced;

/**
 * An aggregate of handlers. TODO: change CopyOnWriteArrayList to a LinkedHashSet.
 * @author Axel
 */
public class Group {
	public Map<Class<? extends Event>, CopyOnWriteArrayList<Handler>> handlers = new WeakHashMap<Class<? extends Event>, CopyOnWriteArrayList<Handler>>(1);
	private Rectangle size = null;
	/** Whether the handlers should receive events. */
	boolean active = true;
	private boolean alwaysActive = false;
	Group parent;
	List<Group> children = new ArrayList<Group>();

	// private List<Scene> groups = Registry.instance().views;

	public Group(Group parent) {
		if ((this.parent = parent) == null && (parent = Registry.DEFAULT_VIEW) == null) return;
		if (this instanceof Handler) register((Handler) this);
		parent.children.add(this);
	}

	/** Creates a new {@link Group} at top-level. */
	public Group() {
		this(null);
	}

	/** @return the size */
	public Rectangle getRectangle() {
		return size;
	}

	/**
	 * Sets the rectangle to the specified.
	 * @param rectangle
	 * @return the existing rectangle if the given is null
	 */
	public void setSize(Rectangle size) {
		this.size = size;
	}

	public Group setAlwaysActive(boolean b) {
		this.alwaysActive = b;
		return this;
	}

	/** @return if active */
	public boolean isActive() {
		return active;
	}

	/** Sets this and every underlying group to <code>active</code>. */
	public void setActive(boolean active) {
		this.active = active;
		for (Iterator<Group> iterator = children.iterator(); iterator.hasNext();) {
			Group group = (Group) iterator.next();
			group.setActive(active);
		}
	}

	/**
	 * Registers an {@link Handler}.
	 * @param handler an {@link Handler} instance
	 */
	@SuppressWarnings("unchecked")
	public void register(Handler handler) {
		/*
		 * List<Group> groups = Registry.instance().rooms; if (!groups.contains(this)) groups.add(this);
		 */

		if (handler instanceof Referenced<?>) ((Referenced<Group>) handler).setReference(this);
		if (handler instanceof Createable) ((Createable) handler).create();

		ArrayList<Class<? extends Event>> list = new ArrayList<Class<? extends Event>>();
		handler.handlers(list);
		for (Iterator<Class<? extends Event>> iterator = list.iterator(); iterator.hasNext();) {
			Class<? extends Event> type = iterator.next();
			if (!handlers.containsKey(type)) handlers.put(type, new CopyOnWriteArrayList<Handler>());
			handlers.get(type).add(handler);
		}
	}

	List<Group> getHierarchy() {
		List<Group> list = new ArrayList<>();
		list.add(this);
		for (int i = 0; i < children.size(); i++)
			list.addAll(children.get(i).getHierarchy());
		return list;
	}

	/* Group utility */

	/** Deactivates every other group except for this one and it's children. */
	public void focus() {
		for (Group toCheck : Registry.DEFAULT_VIEW.getHierarchy())
			if (!toCheck.alwaysActive) toCheck.setActive(false);
		setActive(true);
	}
}