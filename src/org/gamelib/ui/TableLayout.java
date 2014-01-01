/**
 * 
 */
package org.gamelib.ui;

import org.gamelib.Group;
import org.gamelib.Handler;
import org.gamelib.ui.TableLayout.TableToolkit;
import org.gamelib.util.Pool;

import com.esotericsoftware.tablelayout.BaseTableLayout;
import com.esotericsoftware.tablelayout.Cell;
import com.esotericsoftware.tablelayout.Toolkit;

/**
 * @author pwnedary
 */
public class TableLayout extends BaseTableLayout<Handler, Table, TableLayout, TableToolkit> {
	static {
		if (Toolkit.instance == null) Toolkit.instance = new TableToolkit();
	}

	public TableLayout() {
		super((TableToolkit) Toolkit.instance);
	}

	public void layout() {
		Table table = getTable();
		float width = table.getWidth();
		float height = table.getHeight();

		super.layout(0, 0, width, height);

		java.util.List<Cell> cells = getCells();
		for (int i = 0, n = cells.size(); i < n; i++) {
			Cell c = cells.get(i);
			if (c.getIgnore()) continue;
			float widgetHeight = c.getWidgetHeight();
			float widgetY = height - c.getWidgetY() - widgetHeight;
			c.setWidgetY(widgetY);
			Handler handler = (Handler) c.getWidget();
			if (handler != null && handler instanceof Widget) {
				((Widget) handler).setX((int) c.getWidgetX());
				((Widget) handler).setY((int) widgetY);
				((Widget) handler).setHeight((int) c.getWidgetHeight());
				((Widget) handler).setHeight((int) widgetHeight);
			}
		}
		// Validate children separately from sizing actors to ensure actors without a cell are validated.
		for (Handler child : table.getChildren())
			if (child instanceof Widget) ((Widget) child).validate();
	}

	@Override
	public void invalidateHierarchy() {
		super.invalidate();
		for (Handler child : getTable().getChildren())
			if (child instanceof Widget) ((Widget) child).invalidate();
	}

	public static class TableToolkit extends Toolkit<Handler, Table, TableLayout> {
		static Pool<Cell> cellPool = new Pool() {
			protected Cell newObject() {
				return new Cell();
			}
		};

		@Override
		public Cell obtainCell(TableLayout layout) {
			Cell cell = cellPool.obtain();
			cell.setLayout(layout);
			return cell;
		}

		@Override
		public void freeCell(Cell cell) {
			cell.free();
			cellPool.free(cell);
		}

		@Override
		public void addChild(Handler parent, Handler child) {
			((Group) parent).register(child);
		}

		@Override
		public void removeChild(Handler parent, Handler child) {
			((Group) parent).unregister(child);
		}

		@Override
		public float getMinWidth(Handler widget) {
			if (widget instanceof Widget) return ((Widget) widget).getMinimumWidth();
			return 0;
		}

		@Override
		public float getMinHeight(Handler widget) {
			if (widget instanceof Widget) return ((Widget) widget).getMinimumHeight();
			return 0;
		}

		@Override
		public float getPrefWidth(Handler widget) {
			if (widget instanceof Widget) return ((Widget) widget).getPrefferedWidth();
			return 0;
		}

		@Override
		public float getPrefHeight(Handler widget) {
			if (widget instanceof Widget) return ((Widget) widget).getPrefferedHeight();
			return 0;
		}

		@Override
		public float getMaxWidth(Handler widget) {
			if (widget instanceof Widget) return ((Widget) widget).getMaximumWidth();
			return 0;
		}

		@Override
		public float getMaxHeight(Handler widget) {
			if (widget instanceof Widget) return ((Widget) widget).getMaximumHeight();
			return 0;
		}

		@Override
		public float getWidth(Handler widget) {
			if (widget instanceof Widget) return ((Widget) widget).getWidth();
			return 0;
		}

		@Override
		public float getHeight(Handler widget) {
			if (widget instanceof Widget) return ((Widget) widget).getHeight();
			return 0;
		}

		@Override
		public void clearDebugRectangles(TableLayout layout) {
			// TODO Auto-generated method stub
		}

		@Override
		public void addDebugRectangle(TableLayout layout, com.esotericsoftware.tablelayout.BaseTableLayout.Debug type, float x, float y, float w, float h) {
			// TODO Auto-generated method stub
		}
	}
}
