/**
 * 
 */
package org.gamelib.gui;

import org.gamelib.Game;
import org.gamelib.Group;

/**
 * @author pwnedary
 */
public abstract class WidgetGroup extends Group implements Widget {
	protected int x;
	protected int y;
	protected int width;
	protected int height;
	protected boolean valid = false;

	public WidgetGroup() {
		super(null);
	}

	@Override
	public void validate() {
		boolean fillParent = true;
		if (fillParent) {
			int width = parent instanceof WidgetGroup ? ((Widget) parent).getWidth() : Game.getBackend().getWidth();
			int height = parent instanceof WidgetGroup ? ((Widget) parent).getHeight() : Game.getBackend().getHeight();
			if (width != getWidth() || height != getHeight()) {
				setWidth(width);
				setHeight(height);
				invalidate();
			}
		}

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
		if (event instanceof Event.Draw) validate();
		return super.handle(event) || (event instanceof Event.Draw);
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
