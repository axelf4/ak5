/**
 * 
 */
package org.gamelib;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * An aggregate of handlers, whose {@linkplain #handle(org.gamelib.Handler.Event) handle} iterates downwards.
 * 
 * @author pwnedary
 */
public class Group implements Handler {
	/** Map of events to their subscribed handlers. */
	public Map<Class<? extends Event>, List<Handler>> handlers = new HashMap<>();
	/** The ancestor of this group. */
	protected Group parent;
	/** Whether the handlers should receive events. */
	private boolean active = true;

	public Group(Group parent) {
		handlers.put(Event.class, new ArrayList<Handler>());
		if ((this.parent = parent) != null) parent.register(this);
	}

	public Group() {
		this(null);
	}

	@Override
	public boolean handle(Event event) {
		if (!isActive()) return true;
		boolean value = false;
		Class<? extends Event> eventType = event.getClass();
		List<Handler> handlers;
		if (!this.handlers.containsKey(eventType)) this.handlers.put(eventType, handlers = new ArrayList<>(this.handlers.get(Event.class)));
		else handlers = this.handlers.get(eventType);

		for (int i = 0; i < handlers.size() && !event.cancelled(); i++)
			if (!(value |= handlers.get(i).handle(event))) handlers.remove(i);
		return value;
	}

	/**
	 * Registers the specified {@link Handler}, <code>handler</code>, for handling events.
	 * 
	 * @param handler the {@link Handler} to register
	 */
	public void register(Handler handler) {
		if (handler == null) throw new IllegalArgumentException("handler cannot be null");
		// handlers.get(Event.class).add(handler);
		for (List<Handler> handlers : this.handlers.values())
			handlers.add(handler);
	}

	public void unregister(Handler handler) {
		for (Iterator<Handler> iterator = handlers.get(Event.class).iterator(); iterator.hasNext();)
			if (iterator.next().equals(handler)) iterator.remove();
	}

	/**
	 * Returns this Group's ancestor.
	 * 
	 * @return this group's parent
	 */
	public Group getParent() {
		return parent;
	}

	/**
	 * Returns all handler even in lower in hierarchy.
	 */
	public List<Handler> getChildren() {
		List<Handler> list = new ArrayList<>();
		// list.add(this);
		// for (int i = 0; i < children.size(); i++) list.addAll(children.get(i).getHierarchy());
		for (Handler handler : handlers.get(Event.class)) {
			list.add(handler);
			if (handler instanceof Group) list.addAll(((Group) handler).getChildren());
		}
		return list;
	}

	/**
	 * Returns whether this {@link Group} is active.
	 * 
	 * @return whether active
	 */
	public boolean isActive() {
		return active;
	}

	/** Sets this group to <code>active</code>. */
	public void setActive(boolean active) {
		this.active = active; // for (Iterator<Group> iterator = children.iterator(); iterator.hasNext();) { Group group = (Group) iterator.next(); group.setActive(active); }
	}

	/* Group utility */

	/** Deactivates every other group except for this one and it's children. */
	public void focus() {
		if (getParent() != null) for (Handler handler : getParent().getChildren())
			if (handler instanceof Group) ((Group) handler).setActive(false);
		setActive(true);
	}
}