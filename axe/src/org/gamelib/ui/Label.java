/**
 * 
 */
package org.gamelib.ui;

import org.gamelib.Event;
import org.gamelib.graphics.Batch;
import org.gamelib.graphics.Color;
import org.gamelib.ui.Widget.WidgetImpl;
import org.gamelib.util.Font;
import org.gamelib.util.geom.Rectangle;

/** @author pwnedary */
public class Label extends WidgetImpl {
	/** The text to be displayed. */
	private String text, show;
	/** The font to draw in. */
	private final Font font;
	/** Text's AABB */
	private Rectangle bounds = new Rectangle();
	/** If truncating if needed. */
	private boolean ellipsis = false;
	/** Color to draw text in. */
	public Color fontColor = Color.BLACK;

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
		if (ellipsis && getWidth() < bounds.getWidth()) {
			int ellipsisWidth = font.getWidth("...");
			if (getWidth() >= ellipsisWidth) show = text.substring(0, font.visibleChars(text, getWidth() - ellipsisWidth)) + "...";
		}
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		if (text.equals(this.text)) return;
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
		if (event instanceof Event.Draw) {
			validate();
			Batch batch = null; // ((Event.Draw) event).batch;
//			g.setColor(fontColor);
//			font.drawString(g, show, getX(), getY());
		} else return false;
		return true;
	}

	/** Whether {@linkplain #text} should be truncated with an ellipsis punctuation mark if it's width exceeds the
	 * bounds.
	 * 
	 * @param ellipsis whether to truncate text */
	public void setEllipsis(boolean ellipsis) {
		this.ellipsis = ellipsis;
		invalidate();
	}
}
