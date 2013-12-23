/**
 * 
 */
package org.gamelib.ui;

import java.util.Iterator;

import org.gamelib.Group;
import org.gamelib.Handler;

/**
 * @author pwnedary
 */
public abstract class WidgetGroup extends Group implements Widget {

	@Override
	public boolean handle(Event event) {
		boolean value = false;
		for (Iterator<Group> iterator1 = getHierarchy().iterator(); iterator1.hasNext();)
			for (Iterator<Handler> iterator2 = iterator1.next().handlers.get(event.getClass()).iterator(); iterator2.hasNext();)
				value |= iterator2.next().handle(event);
		return value;
	}

}
