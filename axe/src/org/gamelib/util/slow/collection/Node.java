/**
 * 
 */
package org.gamelib.util.slow.collection;

import java.util.ArrayList;
import java.util.List;

/**
 * An element with a {@link #parent} and {@link #children}s.
 * 
 * @author pwnedary
 */
public interface Node<E> {

	Node<E> getParent();

	List<Node<E>> getChildren();

	class BinaryNode<E> implements Node<E> {
		private E element;
		private final BinaryNode<E> parent;
		private BinaryNode<E> next;
		private BinaryNode<E> prev;

		// private final List<Node<E>> children = new ArrayList<Node<E>>();

		public BinaryNode(E element, BinaryNode<E> parent, BinaryNode<E> next, BinaryNode<E> prev) {
			this.element = element;
			this.parent = parent;
			this.next = next;
			this.prev = prev;
		}

		public BinaryNode(E e) {
			this.parent = null;
			this.element = e;
		}

		public BinaryNode() {
			this(null);
		}

		public void add(Node<E> node) {
			// node.parent = this;
			// children.add(node);
		}

		/**
		 * @return This node's element and all of it's child's in a list.
		 */
		public List<E> asList() {
			List<E> list = new ArrayList<E>();
			list.add(element);
			// for (int i = 0; i < children.size(); i++)
			// list.addAll(children.get(i).asList());
			return list;
		}

		@Override
		public boolean equals(Object obj) {
			throw new UnsupportedOperationException();
			// return element.equals(((Node<?>) obj).element) && (parent == null && ((Node<?>) obj).parent == null) || parent.equals(((Node<?>) obj).parent) && children.equals(((Node<?>) obj).children);
		}

		/*
		 * (non-Javadoc)
		 * @see java.lang.Object#toString()
		 */
		@Override
		public String toString() {
			// return element + (children.size() > 0 ? ", " + children : "");
			return element.toString();
		}

		/* Getters and Setters */

		/**
		 * @return the element
		 */
		public E get() {
			return element;
		}

		/**
		 * @param element the element to set
		 */
		public void set(E element) {
			this.element = element;
		}

		/**
		 * @return the parent
		 */
		public BinaryNode<E> getParent() {
			return parent;
		}

		public BinaryNode<E> getNext() {
			return next;
		}

		public void setNext(BinaryNode<E> next) {
			this.next = next;
		}

		public BinaryNode<E> getPrev() {
			return prev;
		}

		public void setPrev(BinaryNode<E> prev) {
			this.prev = prev;
		}

		/**
		 * @return the children
		 */
		public List<Node<E>> getChildren() {
			// return children;
			return null;
		}
	}
}
