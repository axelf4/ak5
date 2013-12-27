/**
 * 
 */
package org.gamelib.gui;

import java.util.Iterator;

import org.gamelib.Group;
import org.gamelib.Handler;

/**
 * @author pwnedary
 */
public abstract class WidgetGroup extends Group implements Widget {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean valid = false;

	@Override
	public void validate() {
		if (!valid) layout();
		valid = true;
	}

	@Override
	public void invalidate() {
		valid = false;
		if (getParent() instanceof Widget) ((Widget) getParent()).invalidate();
	}

	@Override
	public boolean handle(Event event) {
		boolean value = false;
		if (event instanceof Event.Draw) {
			value = true;
			validate();
		}
		for (Iterator<Group> iterator1 = getHierarchy().iterator(); iterator1.hasNext();)
			for (Iterator<Handler> iterator2 = iterator1.next().handlers.get(event.getClass()).iterator(); iterator2.hasNext();)
				value |= iterator2.next().handle(event);
		return value;
	}

	@Override
	public int getX() {
		return x;
	}

	@Override
	public void setX(int x) {
		this.x = x;
	}

	@Override
	public int getY() {
		return y;
	}

	@Override
	public void setY(int y) {
		this.y = y;
	}

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public void setWidth(int width) {
		this.width = width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public void setHeight(int height) {
		this.height = height;
	}

	@Override
	public int getMinimumWidth() {
		return getPrefferedWidth();
	}

	@Override
	public int getMinimumHeight() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getMaximumWidth() {
		return Integer.MAX_VALUE;
	}

	@Override
	public int getMaximumHeight() {
		return getPrefferedHeight();
	}

	@Override
	public Group getParent() {
		return parent;
	}

	// public void pack() {}

}
