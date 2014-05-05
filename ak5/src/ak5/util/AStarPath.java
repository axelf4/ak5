/**
 * 
 */
package ak5.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;
import java.util.Queue;

import ak5.util.Path.PathImpl;
import ak5.util.Path.Node.NodeImpl;

/**
 * @author pwnedary
 */
public class AStarPath extends PathImpl {
	private static final long serialVersionUID = 5511462095605636220L;
	private Heuristic heuristic = Heuristic.MANHATTAN;

	@Override
	public Path find(int x1, int y1, int x2, int y2, boolean[][] grid) {
		Queue<AStarNode> openSet = new PriorityQueue<>(); // The set of tentative nodes to be evaluated, initially containing the start node
		openSet.add(new AStarNode(x1, y1));
		List<AStarNode> closedSet = new ArrayList<>();
		AStarNode goal = new AStarNode(x2, y2);

		while (!openSet.isEmpty()) {
			AStarNode current = openSet.poll();
			if (current.equals(goal)) { // reconstruct path
				clear();
				for (add(current); current.parent != null; current = current.parent)
					add(current);
				Collections.reverse(this);
				return this;
			}
			closedSet.add(current);

			for (AStarNode neighbor : neighbors(current, grid)) {
				if (closedSet.contains(neighbor)) continue;
				int ng = current.g + ((neighbor.getX() == current.getX() || neighbor.getY() == current.getY()) ? 2 : 3);

				boolean opened = openSet.contains(neighbor);
				if (!opened || ng < neighbor.g) {
					neighbor.f = (neighbor.g = ng) + heuristic.h(neighbor.getX() - goal.getX(), neighbor.getY() - goal.getY());
					neighbor.parent = current;
					if (!opened) openSet.add(neighbor);
				}
			}
		}
		throw new RuntimeException("Unreachable: (" + x2 + "," + y2 + ")");
	}

	private List<AStarNode> neighbors(Node node, boolean[][] grid) {
		List<AStarNode> list = new ArrayList<>(allowDiagonal ? 8 : 4);

		for (Direction dir : Direction.getDirections(allowDiagonal)) {
			int x = node.getX() + dir.xpos, y = node.getY() + dir.ypos;
			if (x >= 0 && x < grid.length && y >= 0 && y < grid[0].length && grid[x][y]) {
				if (!crossCorners && dir.isDiagonal() && !grid[x][node.getY()] || !grid[node.getX()][y]) continue;
				AStarNode add = new AStarNode(x, y);
				list.add(add);
			}
		}
		return list;
	}

	public static class AStarNode extends NodeImpl implements Comparable<AStarNode> {
		private static final long serialVersionUID = -1606098651266863255L;
		public int g, f;
		public AStarNode next, parent;

		/**
		 * @param x
		 * @param y
		 */
		public AStarNode(int x, int y) {
			super(x, y);
		}

		/** Returns 1 if f is less than o.f, 1 if bigger and 0 otherwise. */
		@Override
		public int compareTo(AStarNode o) {
			return f < o.f ? -1 : f > o.f ? 1 : 0; // return f < o.f ? 1 : f > o.f ? -1 : 0; // LONGEST POSSIBLE HEURISTICS!
		}
	}

	public enum Heuristic {
		/** Manhattan distance. */
		MANHATTAN, /** Euclidean distance. */
		EUCLIDEAN, /** Chebyshev distance. */
		CHEBYSHEV;

		/**
		 * @param dx Difference in x.
		 * @param dy Difference in y.
		 * @throws Error if {@link #ordinal()} doesn't return 0-2
		 */
		public int h(int dx, int dy) {
			switch (ordinal()) {
			case 0:
				return dx + dy;
			case 1:
				return (int) Math.sqrt(dx * dx + dy * dy);
			case 2:
				return Math.max(dx, dy);
			default:
				throw new Error("I dunno LOL ¯\\(°_o)/¯");
			}
		}
	}

}
