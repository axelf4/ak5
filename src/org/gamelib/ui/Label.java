/**
 * 
 */
package org.gamelib.ui;

import org.gamelib.backend.Graphics;
import org.gamelib.ui.Widget.WidgetImpl;
import org.gamelib.util.Font;
import org.gamelib.util.geom.Rectangle;

/**
 * @author pwnedary
 */
public class Label extends WidgetImpl {
	/** The text to be displayed. */
	private String text, show;
	private final Font font;
	/** Text's AABB */
	Rectangle bounds = new Rectangle();
	/** TODO shorten if needed */
	private boolean ellipsis = false;

	public Label(String text, Font font) {
		this.show = this.text = text;
		this.font = font;
		setWidth(getPrefferedWidth());
		setHeight(getPrefferedHeight());
	}

	private void computeSizes() {
		bounds.setWidth(font.getWidth(text));
		bounds.setHeight(font.getHeight());
	}

	@Override
	public void layout() {
		computeSizes();

		if (ellipsis && getWidth() < bounds.getWidth()) {
			int ellipsisWidth = font.getWidth("...");
			if (getWidth() >= ellipsisWidth) show = text.substring(0, font.visibleChars(text, getWidth() - ellipsisWidth)) + "...";
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.show = this.text = text;
		invalidate();
	}

	@Override
	public int getPrefferedWidth() {
		if (!valid) computeSizes();
		return bounds.getWidth();
	}

	@Override
	public int getPrefferedHeight() {
		if (!valid) computeSizes();
		return bounds.getHeight();
	}

	@Override
	public boolean handle(Event event) {
		super.handle(event);
		if (event instanceof Event.Draw) {
			Graphics g = ((Event.Draw) event).graphics;
			font.drawString(g, show, getX(), getY());
		} else return false;
		return true;
	}

	public void setEllipsis(boolean ellipsis) {
		this.ellipsis = ellipsis;
		invalidate();
	}
}
