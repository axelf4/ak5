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
	private int rows, columns;

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
		int[] columnPrefWidth = new int[columns];
		int[] columnMinWidth = new int[columns];
		int[] rowPrefHeight = new int[rows];
		int[] rowMinHeight = new int[rows];
		for (int i = 0; i < cells.size(); i++) {
			Cell c = cells.get(i);
			Widget widget = c.widget;
			int prefWidth = widget.getPrefferedWidth();
			int prefHeight = widget.getPrefferedHeight();
			int minWidth = widget.getMinimumWidth();
			int minHeight = widget.getMinimumHeight();
			int maxWidth = widget.getMaximumWidth();
			int maxHeight = widget.getMaximumHeight();
			// TODO include padding

			columnPrefWidth[c.column] = Math.max(columnPrefWidth[c.column], prefWidth);
			columnMinWidth[c.column] = Math.max(columnMinWidth[c.column], minWidth);

			rowPrefHeight[c.row] = Math.max(rowPrefHeight[c.row], prefHeight);
			rowMinHeight[c.row] = Math.max(rowMinHeight[c.row], minHeight);
		}

		valid = true;
	}

	@Override
	public int getPrefferedWidth() {
		return 0;
	}

	@Override
	public int getPrefferedHeight() {
		return 0;
	}

	@Override
	public boolean handle(Event event) {
		// TODO Auto-generated method stub
		return super.handle(event);
	}

	public void add(Widget widget) {
		Cell cell = new Cell();
		cell.widget = widget;
		// TODO set cell row and column
		cells.add(cell);
	}

	public void row() {
		if (cells.size() > 0) {
			cells.get(cells.size() - 1).endRow = true;
			rows++;
		}
	}

	@Override
	public void register(Handler handler) {
		super.register(handler);
		if (handler instanceof Widget) add((Widget) handler);
	}

	private static class Cell {
		private boolean endRow;
		public Widget widget;
		public int colspan = 1;
		public int row, column;
		public boolean fill;
	}

}
