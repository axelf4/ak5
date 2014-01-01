/**
 * 
 */
package org.gamelib.gui;

import com.esotericsoftware.tablelayout.Cell;

/**
 * @author pwnedary
 */
public class Table extends WidgetGroup {
	private final TableLayout layout;

	public Table() {
		layout = new TableLayout();
		layout.setTable(this);
	}

	@Override
	public void layout() {
		layout.layout();
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
}
