/**
 * 
 */
package org.gamelib.ui;

import org.gamelib.Registry;
import org.gamelib.backend.Graphics;
import org.gamelib.backend.Input;
import org.gamelib.util.Color;
import org.gamelib.util.geom.Point;
import org.gamelib.util.geom.Rectangle;

/**
 * @author Axel
 */
public class Button extends Widget {
	
	public Button(ButtonStyle style) {
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Handler#handle(org.gamelib.Handler.Event)
	 */
	@Override
	public boolean handle(Event event) {
		if (event instanceof Event.Draw) {
			invalidate();
			validate();
			Graphics g = ((Event.Draw) event).graphics;
			g.setColor(Color.BLACK);
			g.drawRect(x, y, width, height);
		} else if (event instanceof Event.Mouse) {
			Input input = ((Event.Control<?>) event).input;
			int id = ((Event.Mouse) event).id;
			int button = ((Event.Mouse) event).button;
			if (id == Input.MOUSE_RELEASED && button == Input.BUTTON1) {
				Rectangle bounds = new Rectangle(x, y, width, height);
				Point mouse = new Point(input.getMouseX(), input.getMouseY());
				if (bounds.collides(mouse)) {
					System.out.println("button click!");
				}
			}
		} else return false;
		return true;
	}

	@Override
	public void layout() {
		setWidth(100);
		setHeight(100);
	}

	@Override
	public int getPrefferedWidth() {
		return 100;
	}

	@Override
	public int getPrefferedHeight() {
		return 100;
	}
	
	public static class ButtonStyle implements Style {
		
	}

}
