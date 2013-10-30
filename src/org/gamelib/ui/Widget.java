/**
 * 
 */
package org.gamelib.ui;

import org.gamelib.Handler;
import org.gamelib.util.geom.Rectangle;

/**
 * @author pwnedary
 */
public abstract class Widget implements Handler {

	// TODO add container class with add method
	private WidgetGroup parent;
	protected boolean needsLayout;
	protected final Rectangle bounds = new Rectangle();

	protected Widget() {}

	protected void validate() {
		if (needsLayout) layout();
		needsLayout = false;
	}

	protected abstract void layout();

}
