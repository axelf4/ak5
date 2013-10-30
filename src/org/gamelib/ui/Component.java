/**
 * 
 */
package org.gamelib.ui;

import org.gamelib.Group;
import org.gamelib.Handler;
import org.gamelib.Registry;
import org.gamelib.util.geom.Rectangle;

/**
 * @author Axel
 */
public abstract class Component implements Handler, Createable, Referenced<Group> {

	// public int x, y, width, height;
	public Rectangle rectangle;

	protected Group group;

	/**
	 * 
	 */
	@Deprecated
	public Component(Group group) {
		this.group = group;
		Registry.instance().register(group, this);
	}

	public Component() {
	}

	@Override
	public void create() {
		Registry.instance().register(group, this);
	}

	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

	@Override
	public Group getReference() {
		return group;
	}

	@Override
	public void setReference(Group reference) {
		this.group = reference;
	}

}
