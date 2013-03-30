/**
 * 
 */
package org.gamelib.util;

import java.util.ArrayList;
import java.util.List;

/**
 * @author pwnedary
 */
public class Node<E> {

	private E element;
	private Node<E> parent = null;
	private final List<Node<E>> children = new ArrayList<Node<E>>();

	/**
	 * 
	 */
	public Node(E e) {
		this.element = e;
	}

	/**
	 * 
	 */
	public Node() {
		// TODO Auto-generated constructor stub
	}

	/**
	 * @return the element
	 */
	public E getElement() {
		return element;
	}

	/**
	 * @param element the element to set
	 */
	public void setElement(E element) {
		this.element = element;
	}

	/**
	 * @return the parent
	 */
	public Node<E> getParent() {
		return parent;
	}

	/**
	 * @return the children
	 */
	public List<Node<E>> getChildren() {
		return children;
	}

	public void add(Node<E> node) {
		node.parent = this;
		children.add(node);
	}

	/**
	 * @return This node's element and all of it's child's in a list.
	 */
	public List<E> asList() {
		List<E> list = new ArrayList<E>();
		list.add(element);
		for (int i = 0; i < children.size(); i++)
			list.addAll(children.get(i).asList());
		return list;
	}

	public boolean contains(Object o) {
		return asList().contains(o);
	}

	/* (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString() */
	@Override
	public String toString() {
		return element + (children.size() >= 1 ? ", " + children : "");
	}

}
