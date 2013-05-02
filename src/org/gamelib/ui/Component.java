/**
 * 
 */
package org.gamelib.ui;

import java.awt.Rectangle;

import org.gamelib.Handler;
import org.gamelib.HandlerRegistry;
import org.gamelib.Room;

/**
 * @author Axel
 *
 */
public abstract class Component implements Handler {
	
	// public int x, y, width, height;
	public Rectangle rectangle;
	
	protected Room room;

	/**
	 * 
	 */
	public Component(Room room) {
		this.room = room;
		HandlerRegistry.instance().register(this, room);
	}
	
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

}
