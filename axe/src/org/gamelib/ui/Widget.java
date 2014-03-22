/**
 * 
 */
package org.gamelib.ui;

import org.gamelib.Event;
import org.gamelib.Group;
import org.gamelib.Handler;

/** @author pwnedary */
public interface Widget extends Handler {
	void layout();

	/** Lays out the widget if it has been {@link #invalidate() invalidated}. */
	void validate();

	void invalidate();

	int getX();

	void setX(int x);

	int getY();

	void setY(int y);

	int getWidth();

	void setWidth(int width);

	int getHeight();

	void setHeight(int height);

	int getMinimumWidth();

	int getMinimumHeight();

	int getPrefferedWidth();

	int getPrefferedHeight();

	int getMaximumWidth();

	int getMaximumHeight();

	Group getParent();

	void setParent(Group parent);

	public static abstract class WidgetImpl implements Widget {
		Group parent;
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
			if (event instanceof Event.Draw) validate();
			else return false;
			return true;
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
			return getPrefferedHeight();
		}

		@Override
		public int getMaximumWidth() {
			return Integer.MAX_VALUE;
		}

		@Override
		public int getMaximumHeight() {
			return Integer.MAX_VALUE;
		}

		@Override
		public Group getParent() {
			return parent;
		}

		@Override
		public void setParent(Group parent) {
			this.parent = parent;
		}
	}
}
