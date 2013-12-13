/**
 * 
 */
package org.gamelib.ui;

import java.util.ArrayList;
import java.util.List;

import org.gamelib.backend.Color;
import org.gamelib.backend.Graphics;

/**
 * @author pwnedary
 */
public class Table extends WidgetGroup {

	List<Cell> cells = new ArrayList<>(4);
	int rows;
	int columns;
	// int padding;
	int spacing;

	/**
	 * 
	 */
	public Table() {
		// TODO Auto-generated constructor stub
	}

	/** {@inheritDoc} */
	@Override
	public boolean handle(Event event) {
		if (event instanceof Event.Draw) {
			validate();
			drawDebug(((Event.Draw)event).graphics);
		} else return super.handle(event);
		return super.handle(event) || true;
	}

	/** {@inheritDoc} */
	@Override
	public void layout() {
		int maxWidth = 0;
		int maxHeight = 0;

		for (int i = 0; i < cells.size(); i++) {
			Cell c = cells.get(i);
			c.widget.validate();
			maxWidth = Math.max(maxWidth, c.widget.getPrefferedWidth());
			maxHeight = Math.max(maxHeight, c.widget.getPrefferedHeight());
		}

		int currentX = 0, currentY = 0;
		for (int i = 0; i < cells.size(); i++) {
			Cell c = cells.get(i);
			Widget w = c.widget;
			int widgetX = currentX + ((maxWidth / 2) - (w.getPrefferedWidth() / 2));
			int widgetY = currentY + ((maxHeight / 2) - (w.getPrefferedHeight() / 2));
			w.setBounds(widgetX, widgetY, w.width, w.height);
			currentX += maxWidth + spacing;
			if (c.endRow) {
				currentX = 0;
				currentY += maxHeight + spacing;
			}
		}
	}
	
	private void drawDebug(Graphics g) {
		int maxWidth = 0;
		int maxHeight = 0;

		for (int i = 0; i < cells.size(); i++) {
			Cell c = cells.get(i);
			c.widget.validate();
			maxWidth = Math.max(maxWidth, c.widget.getPrefferedWidth());
			maxHeight = Math.max(maxHeight, c.widget.getPrefferedHeight());
		}

		int currentX = 0, currentY = 0;
		for (int i = 0; i < cells.size(); i++) {
			Cell c = cells.get(i);
			Widget w = c.widget;
			int widgetX = currentX + ((maxWidth / 2) - (w.getPrefferedWidth() / 2));
			int widgetY = currentY + ((maxHeight / 2) - (w.getPrefferedHeight() / 2));
			
			g.setColor(Color.RED);
			g.drawRect(currentX, currentY, maxWidth, maxHeight);
			
			g.setColor(Color.GREEN);
			g.drawRect(widgetX, widgetY, w.getPrefferedWidth(), w.getPrefferedHeight());
			
			currentX += maxWidth + spacing;
			if (c.endRow) {
				currentX = 0;
				currentY += maxHeight + spacing;
			}
		}
	}

	public void row() {
		rows++;
		columns = 0;
		if (cells.size() > 0) {
			cells.get(cells.size() - 1).endRow = true;
		}
		invalidate();
	}

	public Cell add(Widget widget) {
		super.addChild(widget);
		invalidate();

		Cell cell = new Cell();
		cell.widget = widget;
		if (cells.size() > 0) {
			cell.row = rows;
			if (cells.get(cells.size() - 1).endRow) cell.column = 0;

			// cell.column = columms;
		} else {
			// cell.row = rows;
			cell.column = 0;
		}
		cells.add(cell);
		columns++;
		return cell;
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

	public int getSpacing() {
		return spacing;
	}

	public void setSpacing(int spacing) {
		this.spacing = spacing;
	}

}
