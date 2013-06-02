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
	
	private static final boolean CANMOVEDIAGONALY = false;
	/** Array containing nodes not visited but adjacent to visited nodes. */
    // private boolean[][] openList;
	private List<AStarStep> openList;
    /** Array containing nodes already visited/taken care of. */
    // private boolean[][] closedList;
	private List<AStarStep> closedList;
	private List<AStarStep> path;
	
	private AStarStep[][] nodes;

	/**
	 * 
	 */
	public AStarPath(boolean[][] map, int x1, int y1, int x2, int y2) {
		nodes = getNodes(map);
		List<AStarStep> testList = getStep(map);
		openList = new LinkedList<>();
        // closedList = new boolean[map.length][map[0].length];
        closedList = new LinkedList<>();
        openList.add(getStep(testList, x1, y1, map[x1 ][y1]));
        
        boolean done = false;
        AStarStep current;
        AStarStep last = getStep(testList, x2, y2, map[x2 ][y2]);
        while (!done) {
        	current = lowestFInOpen(); // get node with lowest fCosts from openList
            closedList.add(current); // add current node to closed list
            openList.remove(current); // delete current node from open list
            
            if ((current.getX() == x2)
                    && (current.getY() == y2)) { // found goal
                // path = calcPath(getStep(openList, x1, y1, map[x1 ][y1]), current);
                path = calcPath(nodes[x1][y1], current);
                break;
            }
            
            // for all adjacent nodes:
            // TODO getAdjacent return null
            List<AStarStep> adjacentNodes = getAdjacent(current);
            for (int i = 0; i < adjacentNodes.size(); i++) {
            	AStarStep currentAdj = adjacentNodes.get(i);
                if (!openList.contains(currentAdj)) { // node is not in openList
                    currentAdj.setPrevious(current); // set current node as previous for this node
                    currentAdj.sethCosts(last); // set h costs of this node (estimated costs to goal)
                    currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
                    openList.add(currentAdj); // add node to openList
                } else { // node is in openList
                    if (currentAdj.gCosts > currentAdj.calculategCosts(current)) { // costs from current node are cheaper than previous costs
                        currentAdj.setPrevious(current); // set current node as previous for this node
                        currentAdj.setgCosts(current); // set g costs of this node (costs from start to this node)
                    }
                }
            }

            if (openList.isEmpty()) { // no path exists
                // return new LinkedList<T>(); // return empty list
            	throw new RuntimeException("unreachable");
            }
        }
	}

	/**
     * returns a LinkedList with nodes adjacent to the given node.
     * if those exist, are walkable and are not already in the closedList!
     */
    private List<AStarStep> getAdjacent(AStarStep node) {
        // TODO make loop
        int x = node.getX();
        int y = node.getY();
        List<AStarStep> adj = new LinkedList<>();

        AStarStep temp;
        if (x > 0) {
            temp = this.getNode((x - 1), y);
            if (temp.isWalkable() && !closedList.contains(temp)) {
                adj.add(temp);
            }
        }

        if (x < nodes.length) {
            temp = this.getNode((x + 1), y);
            if (temp.isWalkable() && !closedList.contains(temp)) {
                adj.add(temp);
            }
        }

        if (y > 0) {
            temp = this.getNode(x, (y - 1));
            if (temp.isWalkable() && !closedList.contains(temp)) {
                adj.add(temp);
            }
        }

        if (y < nodes[0].length) {
            temp = this.getNode(x, (y + 1));
            if (temp.isWalkable() && !closedList.contains(temp)) {
                adj.add(temp);
            }
        }


        // add nodes that are diagonaly adjacent too:
        /*if (CANMOVEDIAGONALY) {
            if (x < width && y < higth) {
                temp = this.getNode((x + 1), (y + 1));
                if (temp.isWalkable() && !closedList.contains(temp)) {
                    temp.setIsDiagonaly(true);
                    adj.add(temp);
                }
            }

            if (x > 0 && y > 0) {
                temp = this.getNode((x - 1), (y - 1));
                if (temp.isWalkable() && !closedList.contains(temp)) {
                    temp.setIsDiagonaly(true);
                    adj.add(temp);
                }
            }

            if (x > 0 && y < higth) {
                temp = this.getNode((x - 1), (y + 1));
                if (temp.isWalkable() && !closedList.contains(temp)) {
                    temp.setIsDiagonaly(true);
                    adj.add(temp);
                }
            }

            if (x < width && y > 0) {
                temp = this.getNode((x + 1), (y - 1));
                if (temp.isWalkable() && !closedList.contains(temp)) {
                    temp.setIsDiagonaly(true);
                    adj.add(temp);
                }
            }
        }*/
        return adj;
    }

	/* (non-Javadoc)
	 * @see org.gamelib.util.Path#getLength()
	 */
	@Override
	public int getLength() {
		// TODO Auto-generated method stub
		return path.size();
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.Path#getStep(int)
	 */
	@Override
	public Step getStep(int i) {
		return path.get(i);
	}

	/* (non-Javadoc)
	 * @see org.gamelib.util.Path#appendStep(org.gamelib.util.Path.Step)
	 */
	@Override
	public void appendStep(Step step) {
		throw new RuntimeException("unfinished method");
	}
	
	List<AStarStep> getStep(boolean[][] map) {
		List<AStarStep> list = new LinkedList<>();
		for (int i = 0; i < map.length; i++) {
			for (int j = 0; j < map[0].length; j++) {
				AStarStep step = new AStarStep(i, j);
				step.setWalkable(map[i][j]);
				step.setPrevious(i * j - 1 < 0 ? null : list.get(i * j - 1)); // null for first
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
	
	/**
     * returns the node with the lowest fCosts.
     *
     * @return
     */
    AStarStep lowestFInOpen() {
        // TODO currently, this is done by going through the whole openList!
    	AStarStep cheapest = openList.get(0);
        for (int i = 0; i < openList.size(); i++) {
            if (openList.get(i).getfCosts() < cheapest.getfCosts()) {
                cheapest = openList.get(i);
            }
        }
        return cheapest;
    }
    
    /**
     * calculates the found path between two points according to
     * their given <code>previousNode</code> field.
     *
     * @param start
     * @param goal
     * @return
     */
    List<AStarStep> calcPath(AStarStep start, AStarStep goal) {
     // TODO if invalid nodes are given (eg cannot find from
     // goal to start, this method will result in an infinite loop!)
        LinkedList<AStarStep> path = new LinkedList<AStarStep>();

        AStarStep curr = goal;
        boolean done = false;
        while (!done) {
            path.addFirst(curr);
            curr = (AStarStep) curr.getPrevious();

            if (curr.equals(start)) {
                done = true;
            }
        }
        return path;
    }
    
    /**
     * returns node at given coordinates.
     * <p>
     * x/y must be bigger or equal to 0 and smaller or equal to width/hight.
     *
     * @param x
     * @param y
     * @return node
     */
    public final AStarStep getNode(int x, int y) {
        // TODO check parameter.
        return nodes[x][y];
    }
    
    public AStarStep[][] getNodes(boolean[][] map) {
    	AStarStep[][] temp = new AStarStep[map.length][map[0].length];
    	AStarStep previous = null;
		for (int i = 0; i < temp.length; i++) {
			for (int j = 0; j < temp[0].length; j++) {
				AStarStep step = new AStarStep(i, j);
				step.setWalkable(map[i][j]);
				// step.setPrevious(list.get(i * j - 1)); // null for first
				step.setPrevious(previous); // null for first
				previous = step;
				temp[i][j] = step;
			}
		}
		return temp;
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
	     * returns <code>gCosts</code> + <code>hCosts</code>.
	     * <p>
	     *
	     *
	     * @return the fCosts
	     */
	    public int getfCosts() {
	        return gCosts + hCosts;
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
	    
	    private void setgCosts(int gCosts) {
	        this.gCosts = gCosts + penalty;
	    }
	    
	    public void setgCosts(AStarStep previousAbstractNode, int basicCost) {
	        setgCosts(previousAbstractNode.gCosts + basicCost);
	    }
	    
	    protected void sethCosts(int hCosts) {
	        this.hCosts = hCosts;
	    }
	    
	    public void sethCosts(AStarStep endNode) {
            this.sethCosts((Math.abs(this.getX() - endNode.getX())
                    + Math.abs(this.getY() - endNode.getY()))
                    * 10);
        }
	    
	    /**
	     * sets gCosts to <code>gCosts</code> plus <code>movementPanelty</code>
	     * for this AbstractNode given the previous AbstractNode.
	     * <p>
	     * It will assume <code>BASICMOVEMENTCOST</code> as the cost from
	     * <code>previousAbstractNode</code> to itself if the movement is not diagonally,
	     * otherwise it will assume <code>DIAGONALMOVEMENTCOST</code>.
	     * Weather or not it is diagonally is set in the Map class method which
	     * finds the adjacent AbstractNodes.
	     *
	     * @param previousAbstractNode
	     */
	    public void setgCosts(AStarStep previousAbstractNode) {
	            setgCosts(previousAbstractNode, 10);
	    }
	    
	    /**
	     * calculates - but does not set - g costs.
	     * <p>
	     * It will assume <code>BASICMOVEMENTCOST</code> as the cost from
	     * <code>previousAbstractNode</code> to itself if the movement is not diagonally,
	     * otherwise it will assume <code>DIAGONALMOVEMENTCOST</code>.
	     * Weather or not it is diagonally is set in the Map class method which
	     * finds the adjacent AbstractNodes.
	     *
	     * @param previousAbstractNode
	     * @return gCosts
	     */
	    public int calculategCosts(AStarStep previousAbstractNode) {
	            return (previousAbstractNode.gCosts
	                    + 10 + penalty);
	    }
		
	}

}
