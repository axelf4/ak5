/**
 * 
 */
package org.gamelib.util;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.gamelib.util.Path.Node;

/**
 * A path determined by a pathfinding algorithm.
 * @author pwnedary
 */
public interface Path extends List<Node> {
	public Path find(int x1, int y1, int x2, int y2, boolean[][] grid); // TODO use Map instead of boolean[][]

	/**  */
	public void allowDiagonal(boolean allow);

	public void crossCorners(boolean cross);

	/** A single step within the path */
	public interface Node extends Serializable {

		/**
		 * The x-coordinate of the node.
		 * @return the x-coordinate
		 */
		int getX();

		/**
		 * The y-coordinate of the node.
		 * @return the y-coordinate
		 */
		int getY();

		public static abstract class NodeImpl implements Node {
			private static final long serialVersionUID = -4930032274620361780L;
			protected int x;
			protected int y;
			protected boolean walkable;

			public NodeImpl(int x, int y) {
				this.x = x;
				this.y = y;
			}

			@Override
			public int getX() {
				return x;
			}

			@Override
			public int getY() {
				return y;
			}

			@Override
			public String toString() {
				return "[" + getX() + "," + getY() + "]";
			}

			public int hashCode() {
				return x * y;
			}

			public boolean equals(Object other) {
				return other instanceof Node && ((Node) other).getX() == getX() && ((Node) other).getY() == getY();
			}
		}
	}

	public static abstract class PathImpl extends ArrayList<Node> implements Path {
		private static final long serialVersionUID = -8478168287245780437L;
		protected boolean allowDiagonal = true;
		protected boolean crossCorners = true;

		@Override
		public void allowDiagonal(boolean allow) {
			this.allowDiagonal = allow;
		}

		@Override
		public void crossCorners(boolean cross) {
			this.crossCorners = cross;
		}
	}

}
