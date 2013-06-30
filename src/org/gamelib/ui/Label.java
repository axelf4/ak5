/**
 * 
 */
package org.gamelib.ui;

import java.awt.Graphics2D;
import java.util.List;

import org.gamelib.Group;
import org.gamelib.Handler.Event;

/**
 * @author pwnedary
 *
 */
public class Label extends Component {
	
	private String s;

	/**
	 * @param group
	 */
	public Label(Group group, String s) {
		super(group);
		this.s = s;
	}

	/* (non-Javadoc)
	 * @see org.gamelib.Handler#handle(org.gamelib.Handler.Event)
	 */
	@Override
	public void handle(Event event) {
		if (event instanceof Event.Draw) {
			Graphics2D g2d = ((Event.Draw) event).graphics2d;
			g2d.drawString(s, rectangle.x, rectangle.y);
		}
	}

	/* (non-Javadoc)
	 * @see org.gamelib.Handler#handlers(java.util.List)
	 */
	@Override
	public void handlers(List<Class<? extends Event>> list) {
		list.add(Event.Draw.class);
	}

}
