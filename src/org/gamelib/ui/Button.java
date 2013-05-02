/**
 * 
 */
package org.gamelib.ui;

import java.awt.Graphics2D;
import java.util.List;

import org.gamelib.HandlerRegistry;
import org.gamelib.Input;
import org.gamelib.Room;

/**
 * @author Axel
 *
 */
public class Button extends Component {

	/**
	 * 
	 */
	public Button(Room room) {
		super(room);
	}

	/* (non-Javadoc)
	 * @see org.gamelib.Handler#handle(org.gamelib.Handler.Event)
	 */
	@Override
	public void handle(Event event) {
		if (event instanceof Event.Draw) {
			Graphics2D g2d = ((Event.Draw) event).graphics2d;
			g2d.draw(rectangle);
		} else if (event instanceof Event.Mouse) {
			Input input = ((Event.Control) event).input;
			if (rectangle.contains(input.mousePosition)) {
				HandlerRegistry.instance().invokeHandlers(new Event.Layout(this, event), room);
			}
		}
	}

	/* (non-Javadoc)
	 * @see org.gamelib.Handler#handlers(java.util.List)
	 */
	@Override
	public void handlers(List<Class<? extends Event>> list) {
		list.add(Event.Draw.class);
		list.add(Event.Mouse.class);
	}

}
