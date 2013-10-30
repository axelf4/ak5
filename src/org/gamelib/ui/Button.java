/**
 * 
 */
package org.gamelib.ui;

import org.gamelib.Input;
import org.gamelib.Registry;
import org.gamelib.backend.Graphics;
import org.gamelib.util.geom.Point;
import org.gamelib.util.geom.Rectangle;

/**
 * @author Axel
 */
public class Button extends Component {

	/**
	 * 
	 */
	public Button(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	/*
	 * (non-Javadoc)
	 * @see org.gamelib.Handler#handle(org.gamelib.Handler.Event)
	 */
	@Override
	public boolean handle(Event event) {
		if (event instanceof Event.Draw) {
			Graphics g = ((Event.Draw) event).graphics;
			rectangle.draw(g);
		} else if (event instanceof Event.Mouse) {
			Input input = ((Event.Control<?>) event).input;
			int id = ((Event.Mouse) event).id;
			if (id != Input.MOUSE_RELEASED) return true;
			if (rectangle.collides(new Point(input.getMouseX(), input.getMouseY()))) Registry.instance().dispatch(new Event.Layout(this, event));
		} else return false;
		return true;
	}

}
