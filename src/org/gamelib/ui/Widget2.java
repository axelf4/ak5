/**
 * 
 */
package org.gamelib.ui;

import org.gamelib.Game;
import org.gamelib.Handler;
import org.gamelib.backend.Backend;
import org.gamelib.util.geom.Rectangle;

/**
 * @author pwnedary
 */
public abstract class Widget2 implements Handler, Layout {

	// TODO add container class with add method
	protected WidgetGroup2 parent;
	protected boolean needsLayout;
	// protected final Rectangle bounds = new Rectangle();
	protected int x, y, width, height;

	protected Widget2() {
	}

	public void validate() {
		if (needsLayout) layout();
		needsLayout = false;
	}

	public void invalidate() {
		needsLayout = true;
	}

	public void setBounds(int x, int y, int width, int height) {
		this.x = x;
		this.y = y;
		this.width = width;
		this.height = height;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

	protected Rectangle getParentBounds() {
		// int layoutX, layoutY, layoutWidth, layoutHeight;
		if (parent != null)
			return new Rectangle(parent.getX(), parent.getY(), parent.getWidth(), parent.getHeight());
		else {
			Backend backend = Game.getBackend();
			return new Rectangle(0, 0, backend.getWidth(), backend.getHeight());
		}
	}

}
