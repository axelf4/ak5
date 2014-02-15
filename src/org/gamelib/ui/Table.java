/**
 * 
 */
package org.gamelib.ui;

import java.util.ArrayList;
import java.util.List;

import org.gamelib.Group;
import org.gamelib.Handler;
import org.gamelib.backend.Color;
import org.gamelib.backend.Graphics;
import org.gamelib.ui.Table.TableToolkit.DebugRect;
import org.gamelib.util.Pool;
import org.gamelib.util.geom.Rectangle;

import com.esotericsoftware.tablelayout.BaseTableLayout;
import com.esotericsoftware.tablelayout.BaseTableLayout.Debug;
import com.esotericsoftware.tablelayout.Cell;
import com.esotericsoftware.tablelayout.Toolkit;

/**
 * @author pwnedary
 */
public class Table extends WidgetGroup {
	private final TableLayout layout;

	public Table() {
		layout = new TableLayout();
		layout.setTable(this);
		layout.debug();
	}

	@Override
	public void layout() {
		layout.layout();
	}

	@Override
	public boolean handle(Event event) {
		boolean value = super.handle(event);
		if (event instanceof Event.Draw) layout.drawDebug(((Event.Draw) event).graphics);
		return value || event instanceof Event.Draw;
	}

	@Override
	public int getPrefferedWidth() {
		return (int) layout.getPrefWidth();
	}

	@Override
	public int getPrefferedHeight() {
		return (int) layout.getPrefHeight();
	}

	@Override
	public int getMinimumWidth() {
		return (int) layout.getMinWidth();
	}

	@Override
	public int getMinimumHeight() {
		return (int) layout.getMinHeight();
	}

	public Cell<?> add(Widget widget) {
		return layout.add(widget);
	}

	/**
	 * Indicates that subsequent cells should be added to a new row and returns the cell values that will be used as the defaults for all cells in the new row.
	 */
	public Cell<?> row() {
		return layout.row();
	}

	public static class TableLayout extends BaseTableLayout<Handler, Table, TableLayout, TableToolkit> {
		static {
			if (Toolkit.instance == null) Toolkit.instance = new TableToolkit();
		}
		List<DebugRect> debugRects;

		public TableLayout() {
			super((TableToolkit) Toolkit.instance);
		}

		@SuppressWarnings("unchecked")
		public void layout() {
			Table table = getTable();
			int width = table.getWidth();
			int height = table.getHeight();

			super.layout(0, 0, width, height);

			for (Cell<Widget> c : getCells()) {
				if (c.getIgnore()) continue;
				int widgetWidth = Math.round(c.getWidgetWidth());
				int widgetHeight = Math.round(c.getWidgetHeight());
				int widgetX = Math.round(c.getWidgetX());
				int widgetY = height - Math.round(c.getWidgetY()) - widgetHeight;
				c.setWidgetBounds(widgetX, widgetY, widgetWidth, widgetHeight);
				Widget widget = c.getWidget();
				if (widget != null) {
					widget.setX(widgetX);
					widget.setY(widgetY);
					widget.setWidth(widgetWidth);
					widget.setHeight(widgetHeight);
				}
			}
			// Validate children separately from sizing actors to ensure actors without a cell are validated.
			for (Handler child : table.getChildren())
				if (child instanceof Widget) ((Widget) child).validate();
		}

		@Override
		public void invalidateHierarchy() {
			super.invalidate();
			for (Group parent = getTable(); parent != null; parent = parent.getParent())
				if (parent instanceof Widget) ((Widget) parent).invalidate();
				else break;
		}

		public void drawDebug(Graphics g) {
			if (getDebug() == Debug.none || debugRects == null) return;
			int x = 0, y = 0;
			Widget parent = getTable();
			while (true) {
				x += parent.getX();
				y += parent.getY();
				if (parent.getParent() instanceof Widget) parent = (Widget) parent.getParent();
				else break;
			}

			g.begin();
			for (int i = 0, n = debugRects.size(); i < n; i++) {
				DebugRect rect = debugRects.get(i);
				Color color = new Color(rect.type == Debug.cell ? 255 : 0, rect.type == Debug.widget ? 255 : 0, rect.type == Debug.table ? 255 : 0);

				g.setColor(color);
				g.drawRect(x + rect.getX(), y + rect.getY() - rect.getHeight(), rect.getWidth(), rect.getHeight());
			}
			g.end();
		}
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
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
			if (layout.debugRects != null) layout.debugRects.clear();
		}

		@Override
		public void addDebugRectangle(TableLayout layout, com.esotericsoftware.tablelayout.BaseTableLayout.Debug type, float x, float y, float w, float h) {
			if (layout.debugRects == null) layout.debugRects = new ArrayList<>();
			layout.debugRects.add(new DebugRect(type, x, layout.getTable().getHeight() - y, w, h));
		}

		static class DebugRect extends Rectangle {
			final Debug type;

			public DebugRect(Debug type, float x, float y, float width, float height) {
				super((int) x, (int) y, (int) width, (int) height);
				this.type = type;
			}
		}
	}
}
