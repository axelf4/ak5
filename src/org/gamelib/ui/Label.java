/**
 * 
 */
package org.gamelib.ui;

import org.gamelib.backend.Graphics;
import org.gamelib.util.Font;

/**
 * @author pwnedary
 */
public class Label extends Widget {

	private String text;
	private LabelStyle style;
	// TODO use, shorten text if needed with ...
	private boolean ellipsis = true;

	public Label(String text, LabelStyle style) {
		this.text = text;
		this.style = style;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		invalidate();
		this.text = text == null ? "" : text;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Handler#handle(org.gamelib.Handler.Event)
	 */
	@Override
	public boolean handle(Event event) {
		if (event instanceof Event.Draw) {
			validate();
			Graphics g = ((Event.Draw) event).graphics;
			style.font.drawString(g, text, getX(), getY());
		} else return false;
		return true;
	}

	/** {@inheritDoc} */
	@Override
	public void layout() {
		setWidth(style.font.getWidth(text));
		setHeight(style.font.getHeight());
	}

	public static class LabelStyle implements Style {
		public Font font;

		public LabelStyle(Font font) {
			this.font = font;
		}
	}

	@Override
	public int getPrefferedWidth() {
		return style.font.getWidth(text);
	}

	@Override
	public int getPrefferedHeight() {
		return style.font.getHeight();
	}
	
	@Override
	public String toString() {
		return text;
	}

}
