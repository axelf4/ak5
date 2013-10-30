/**
 * 
 */
package org.gamelib;

import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gamelib.Handler.Event;

/**
 * An aggregate of handlers.
 * @author Axel
 */
public class Group {
	public Map<Class<? extends Event>, List<Handler>> handlers = new HashMap<>();
	private Rectangle size = null;
	/** Whether the handlers should receive events. */
	boolean active = true;
	private boolean alwaysActive = false;
	Group parent;
	List<Group> children = new ArrayList<Group>();

	public Group(Group parent) {
		handlers.put(Event.class, new LinkedList<Handler>());
		if ((this.parent = parent) != null || (parent = Registry.MAIN_GROUP) != null) parent.children.add(this);
		if (this instanceof Handler) register((Handler) this);
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
	 * Sets the size to the specified.
	 * @param size
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
	public void register(Handler handler) {
		if (handler == null) throw new IllegalArgumentException("handler cannot be null");
		handlers.get(Event.class).add(handler);
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
		for (Group toCheck : Registry.MAIN_GROUP.getHierarchy())
			if (!toCheck.alwaysActive) toCheck.setActive(false);
		setActive(true);
	}
}