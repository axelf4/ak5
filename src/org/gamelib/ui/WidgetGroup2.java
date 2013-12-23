/**
 * 
 */
package org.gamelib.ui;

import java.util.Iterator;
import java.util.LinkedList;

/**
 * @author pwnedary
 *
 */
public abstract class WidgetGroup2 extends Widget2 {
	
	protected final LinkedList<Widget2> children = new LinkedList<>();

	public WidgetGroup2() {
		// TODO Auto-generated constructor stub
	}

	/** {@inheritDoc} */
	@Override
	public boolean handle(Event event) {
		boolean value = false;
		for (Iterator<Widget2> iterator = children.iterator(); iterator.hasNext();) {
			Widget2 widget2 = iterator.next();
			widget2.handle(event);
			value = true;
		}
		return value;
	}

	/** {@inheritDoc} */
	@Override
	public void layout() {
		// TODO Auto-generated method stub

	}
	
	protected void addChild(Widget2 widget2) {
		children.add(widget2);
	}

}
