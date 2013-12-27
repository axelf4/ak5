/**
 * 
 */
package org.gamelib.gui;

import java.util.ArrayList;
import java.util.List;

import org.gamelib.Game;
import org.gamelib.Group;
import org.gamelib.Handler;

/**
 * @author pwnedary
 */
public class Table extends WidgetGroup {
	private final List<Cell> cells = new ArrayList<>(4);

	@Override
	public void layout() {
		int width = parent instanceof WidgetGroup ? ((Widget) parent).getWidth() : Game.getBackend().getWidth();
		int height = parent instanceof WidgetGroup ? ((Widget) parent).getHeight() : Game.getBackend().getHeight();

		if (!valid) computeSize();

		for (int i = 0; i < cells.size(); i++) {
			Cell c = cells.get(i);
			int widgetX = c.widget.getX();
			int widgetY = c.widget.getY();
			int widgetWidth = c.widget.getWidth();
			int widgetHeight = c.widget.getHeight();
		}
		for (Group group : getHierarchy())
			for (Handler handler : group.handlers.get(Event.class))
				if (handler instanceof Widget) ((Widget) handler).validate();
	}

	private void computeSize() {
		valid = true;
		// TODO
	}

	@Override
	public int getPrefferedWidth() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getPrefferedHeight() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean handle(Event event) {
		// TODO Auto-generated method stub
		return super.handle(event);
	}

	public void add(Widget widget) {

	}

	@Override
	public void register(Handler handler) {
		super.register(handler);
		if (handler instanceof Widget) add((Widget) handler);
	}

	private static class Cell {
		public boolean endRow;
		public Widget widget;
		public int colspan = 1;
		public int row, column;
		public boolean fill;
	}

}
