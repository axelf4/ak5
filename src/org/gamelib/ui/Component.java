/**
 * 
 */
package org.gamelib.ui;

import java.awt.Rectangle;

import org.gamelib.Handler;
import org.gamelib.Registry;
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
		Registry.instance().register(this, room);
	}
	
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

}
