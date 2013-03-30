/**
 * 
 */
package org.gamelib.ui;

import java.awt.Rectangle;

import org.gamelib.Handler;
import org.gamelib.HandlerRegistry;
import org.gamelib.View;

/**
 * @author Axel
 *
 */
public abstract class Component implements Handler {
	
	// public int x, y, width, height;
	public Rectangle rectangle;
	
	protected View view;

	/**
	 * 
	 */
	public Component(View view) {
		this.view = view;
		HandlerRegistry.instance().register(this, view);
	}
	
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

}
