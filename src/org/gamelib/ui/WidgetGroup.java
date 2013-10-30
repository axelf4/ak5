/**
 * 
 */
package org.gamelib.ui;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

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
	public void handle(Event event) {
		for (Iterator<Widget> iterator = children.iterator(); iterator.hasNext();) {
			Widget widget = iterator.next();
			widget.handle(event);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void handlers(List<Class<? extends Event>> list) {
		Set<Class<? extends Event>> set = new HashSet<>();
		for (Iterator<Widget> iterator = children.iterator(); iterator.hasNext();) {
			Widget widget = (Widget) iterator.next();
			List<Class<? extends Event>> list2 = new LinkedList<>();
			widget.handlers(list2);
			set.addAll(list2);
		}
		list.addAll(set);
	}

	/** {@inheritDoc} */
	@Override
	protected void layout() {
		// TODO Auto-generated method stub

	}

}
