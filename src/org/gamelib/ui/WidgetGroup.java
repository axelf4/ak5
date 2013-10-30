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
public abstract class WidgetGroup extends Widget {
	
	protected final LinkedList<Widget> children = new LinkedList<>();

	public WidgetGroup() {
		// TODO Auto-generated constructor stub
	}

	/** {@inheritDoc} */
	@Override
	public boolean handle(Event event) {
		boolean value = false;
		for (Iterator<Widget> iterator = children.iterator(); iterator.hasNext();) {
			Widget widget = iterator.next();
			widget.handle(event);
			value = true;
		}
		return value;
	}

	/** {@inheritDoc} */
	@Override
	protected void layout() {
		// TODO Auto-generated method stub

	}

}
