/**
 * 
 */
package org.gamelib.ui;

import org.gamelib.Handler;

/**
 * @author pwnedary
 */
public interface Widget extends Handler {
	void layout();

	/** If invalid, calls layout. */
	void validate();

	void invalidate();

	int getX();

	void setX(int x);

	int getY();

	void setY(int y);
	
	int getMinimumWidth();

	int getMinimumHeight();

	int getPrefferedWidth();

	int getPrefferedHeight();
	
	int getMaximumWidth();

	int getMaximumHeight();

	public static abstract class WidgetImpl implements Widget {

	}
}
