/**
 * 
 */
package org.gamelib.util;

import org.gamelib.util.geom.Rectangle;

/**
 * @author pwnedary
 * @deprecated
 */
@Deprecated
public class QuadTree<E> {

	private Object[] elements;
	private Rectangle bounds;

	private QuadTree<E> topLeft;
	private QuadTree<E> topRight;
	private QuadTree<E> botLeft;
	private QuadTree<E> botRight;

	/**
	 * 
	 */
	public QuadTree(int size, int elemPerQuad) {
		this(0, 0, size, elemPerQuad);
	}

	private QuadTree(int x, int y, int size, int elemPerQuad) {
		bounds = new Rectangle(x, y, size, size); // create square
		elements = new Object[elemPerQuad];
	}

	public int maxElem() {
		return elements.length;
	}

	public boolean hasChildren() {
		return topLeft != null;
	}

	/**
	 * <p>
	 * Subdivides this Quadtree into 4 other quadtrees.
	 * </p>
	 * <p>
	 * This is usually used, when this Quadtree already has an Element.
	 * </p>
	 * 
	 * @return whether this Quadtree was subdivided, or didn't subdivide,
	 *         because it was already subdivided.
	 */
	protected boolean subdivide() {
		if (hasChildren()) { return false; }
		int hs = bounds.getWidth() / 2;
		topLeft = new QuadTree<E>(bounds.getX(), bounds.getY(), hs, maxElem());
		topRight = new QuadTree<E>(bounds.getX() + hs, bounds.getY(), hs, maxElem());
		botLeft = new QuadTree<E>(bounds.getX(), bounds.getY() + hs, hs, maxElem());
		botRight = new QuadTree<E>(bounds.getX() + hs, bounds.getY() + hs, hs, maxElem());
		return true;
	}

}
