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
 * An aggregate of handlers.<br />
 * TODO make groups handlers and have handle iterate downwards
 * @author pwnedary
 */
public class Group implements Handler {
	public Map<Class<? extends Event>, List<Handler>> handlers = new HashMap<>();
	protected Group parent;

	/** Whether the handlers should receive events. */
	private boolean active = true;
	private boolean alwaysActive = false;

	public Group(Group parent) {
		handlers.put(Event.class, new ArrayList<Handler>());
		if ((this.parent = parent) != null) parent.handlers.get(Event.class).add(this);

		// if ((this.parent = parent) != null || (parent = EventBus.MAIN_GROUP) != null) parent.children.add(this);
		// if ((this.parent = parent) != null) parent.children.add(this);

		// if (this instanceof Handler) register((Handler) this);
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
	 * @return whether active
	 */
	public boolean isActive() {
		return active;
	}

	/** Sets this and every underlying group to <code>active</code>. */
	public void setActive(boolean active) {
		this.active = active;
		// for (Iterator<Group> iterator = children.iterator(); iterator.hasNext();) { Group group = (Group) iterator.next(); group.setActive(active); }
	}

	public Group setAlwaysActive(boolean b) {
		this.alwaysActive = b;
		return this;
	}

	/* Group utility */

	/** Deactivates every other group except for this one and it's children. */
	public void focus() {
		for (Handler handler : EventBus.instance().main.getChildren())
			if (handler instanceof Group && !((Group) handler).alwaysActive) ((Group) handler).setActive(false);
		setActive(true);
	}

	/* DEPRECATED */

	// TODO remove:
	@Deprecated
	protected List<Group> children = new ArrayList<Group>();

	@Deprecated
	public void add(Group group) {
		if (group == null) throw new IllegalArgumentException("group cannot be null");
		children.add(group);
	}
}