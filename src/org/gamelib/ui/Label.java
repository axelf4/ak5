/**
 * 
 */
package org.gamelib.ui;

import java.util.List;

import org.gamelib.backend.Graphics;
import org.gamelib.util.Font;
import org.gamelib.util.geom.Rectangle;

/**
 * @author pwnedary
 */
public class Label extends Widget {

	private String text;
	private final Rectangle bounds = new Rectangle();
	private LabelStyle style;

	public Label(String text, LabelStyle style) {
		this.text = text;
		this.style = style;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text == null ? "" : text;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Handler#handle(org.gamelib.Handler.Event)
	 */
	@Override
	public void handle(Event event) {
		if (event instanceof Event.Draw) {
			validate();
			Graphics g = ((Event.Draw) event).graphics;
			style.font.drawString(g, text, bounds.getX(), bounds.getY());
		}
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Handler#handlers(java.util.List)
	 */
	@Override
	public void handlers(List<Class<? extends Event>> list) {
		list.add(Event.Draw.class);
	}

	/** {@inheritDoc} */
	@Override
	protected void layout() {
		bounds.setWidth(style.font.getWidth(text));
		bounds.setHeight(style.font.getHeight());
	}

	public static class LabelStyle implements Style {
		public Font font;

		public LabelStyle(Font font) {
			this.font = font;
		}
	}

}
