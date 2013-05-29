/**
 * 
 */
package org.gamelib.util;

import java.util.LinkedList;
import java.util.List;

/**
 * @author pwnedary
 *
 */
public class AStarPath implements Path {
	
	/** Array containing nodes not visited but adjacent to visited nodes. */
    // private boolean[][] openList;
	private List<AStarStep> openList;
    /** Array containing nodes already visited/taken care of. */
    // private boolean[][] closedList;
	private List<AStarStep> closedList;

	/**
	 * 
	 */
	public AStarPath(boolean[][] map, int x1, int y1, int x2, int y2) {
		openList = new LinkedList<>();
        // closedList = new boolean[map.length][map[0].length];
        closedList = new LinkedList<>();
        openList.add(getStep(openList, x1, y1, map[x1 ][y1]));
        
        boolean done = false;
        AStarStep current;
        while (!done) {
        	
        }
        
        throw new RuntimeException("unreachable");
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.Path#getLength()
	 */
	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.Path#getStep(int)
	 */
	@Override
	public Step getStep(int i) {
		// TODO Auto-generated method stub
		return null;
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.Path#appendStep(org.gamelib.util.Path.Step)
	 */
	@Override
	public void appendStep(Step step) {
		// TODO Auto-generated method stub

	}
	
	List<AStarStep> getList(boolean[][] map) {
		LinkedList<AStarStep> list = new LinkedList<>();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				AStarStep step = new AStarStep(i, j);
				step.setWalkable(map[i][j]);
				step.setPrevious(list.get(i * j - 1)); // null for first
				list.add(step);
			}
		}
		return list;
	}
	
	AStarStep getStep(List<AStarStep> list, int x, int y, boolean walkable) {
		AStarStep step = new AStarStep(x, y);
		step.setWalkable(walkable);
		step.setPrevious(list.get(x * y - 1)); // null for first
		return step;
	}
	
	private static class AStarStep extends Step {
		
		boolean walkable;
		AStarStep previous;
		
		/** Optional extra penalty. */
		int penalty;
		
		/** calculated costs from start AbstractNode to this AbstractNode. */
	    private int gCosts;

	    /** estimated costs to get from this AbstractNode to end AbstractNode. */
	    private int hCosts;

		public AStarStep(int x, int y) {
			super(x, y);
		}
		
		/**
	     * @return the walkable
	     */
	    public boolean isWalkable() {
	        return walkable;
	    }

	    /**
	     * @param walkable the walkable to set
	     */
	    public void setWalkable(boolean walkable) {
	        this.walkable = walkable;
	    }

	    /**
	     * returns the node set as previous node on the current path.
	     *
	     * @return the previous
	     */
	    public AStarStep getPrevious() {
	        return previous;
	    }

	    /**
	     * @param previous the previous to set
	     */
	    public void setPrevious(AStarStep previous) {
	        this.previous = previous;
	    }
		
	}

}
