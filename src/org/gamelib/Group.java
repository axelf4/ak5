/**
 * 
 */
package org.gamelib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.gamelib.Handler.Event;

/**
 * An aggregate of handlers.
 * @author pwnedary
 */
public class Group {
	public Map<Class<? extends Event>, List<Handler>> handlers = new HashMap<>();
	protected Group parent;
	protected List<Group> children = new ArrayList<Group>();
	/** Whether the handlers should receive events. */
	private boolean active = true;
	private boolean alwaysActive = false;

	public Group(Group parent) {
		handlers.put(Event.class, new LinkedList<Handler>());
		if ((this.parent = parent) != null || (parent = EventBus.MAIN_GROUP) != null) parent.children.add(this);
		if (this instanceof Handler) register((Handler) this);
	}

	public Group() {
		this(EventBus.MAIN_GROUP);
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

	public Group setAlwaysActive(boolean b) {
		this.alwaysActive = b;
		return this;
	}

	/**
	 * Registers an {@link Handler}.
	 * @param handler an {@link Handler} instance
	 */
	public void register(Handler handler) {
		if (handler == null) throw new IllegalArgumentException("handler cannot be null");
		handlers.get(Event.class).add(handler);
	}

	public void add(Group group) {
		if (group == null) throw new IllegalArgumentException("group cannot be null");
		children.add(group);
	}

	public List<Group> getHierarchy() {
		List<Group> list = new ArrayList<>();
		list.add(this);
		for (int i = 0; i < children.size(); i++)
			list.addAll(children.get(i).getHierarchy());
		return list;
	}

	/* Group utility */

	/** Deactivates every other group except for this one and it's children. */
	public void focus() {
		for (Group toCheck : EventBus.MAIN_GROUP.getHierarchy())
			if (!toCheck.alwaysActive) toCheck.setActive(false);
		setActive(true);
	}
}