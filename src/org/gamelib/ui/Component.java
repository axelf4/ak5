/**
 * 
 */
package org.gamelib.ui;

import org.gamelib.Createable;
import org.gamelib.Handler;
import org.gamelib.Registry;
import org.gamelib.Room;
import org.gamelib.util.geom.Rectangle;

/**
 * @author Axel
 */
public abstract class Component implements Handler, Createable, Referenced<Room> {

	// public int x, y, width, height;
	public Rectangle rectangle;

	protected Room room;

	/**
	 * 
	 */
	@Deprecated
	public Component(Room room) {
		this.room = room;
		Registry.instance().register(this, room);
	}

	public Component() {
	}

	@Override
	public void create() {
		Registry.instance().register(this, room);
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	@Override
	public Room getReference() {
		return room;
	}

	@Override
	public void setReference(Room reference) {
		this.room = reference;
	}

}
